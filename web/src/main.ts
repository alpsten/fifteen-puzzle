import "./styles.css";

const GRID_SIZE = 4;
const SHUFFLE_MOVES = 400;
const BOARD_PADDING = 16;
const BOARD_GAP = 12;
const WIN_MESSAGE_PREFIX = "You solved the board in";
const ANIMATION_DURATION_MS = 170;

type TileValue = number;

type State = {
  tiles: TileValue[];
  moveCount: number;
  isAnimating: boolean;
};

type BoardMetrics = {
  cellSize: number;
  fontSize: number;
};

type CellRect = {
  x: number;
  y: number;
  size: number;
};

type Elements = {
  board: HTMLDivElement;
  goalGrid: HTMLDivElement;
  moveCount: HTMLParagraphElement;
  newGameButton: HTMLButtonElement;
  winBanner: HTMLDivElement;
  winMessage: HTMLParagraphElement;
  playAgainButton: HTMLButtonElement;
};

const state: State = {
  tiles: createSolvedTiles(),
  moveCount: 0,
  isAnimating: false
};

const elements: Elements = {
  board: requireElement<HTMLDivElement>("board"),
  goalGrid: requireElement<HTMLDivElement>("goal-grid"),
  moveCount: requireElement<HTMLParagraphElement>("move-count"),
  newGameButton: requireElement<HTMLButtonElement>("new-game-button"),
  winBanner: requireElement<HTMLDivElement>("win-banner"),
  winMessage: requireElement<HTMLParagraphElement>("win-message"),
  playAgainButton: requireElement<HTMLButtonElement>("play-again-button")
};

const tileElements = new Map<TileValue, HTMLButtonElement>();
const emptySlot = document.createElement("div");

initialize();

function initialize(): void {
  emptySlot.className = "tile empty-slot";
  emptySlot.setAttribute("aria-hidden", "true");

  buildGoalGrid();
  buildBoard();
  bindEvents();
  startNewGame();
}

function bindEvents(): void {
  elements.newGameButton.addEventListener("click", startNewGame);
  elements.playAgainButton.addEventListener("click", () => {
    hideWinBanner();
    startNewGame();
  });
  window.addEventListener("resize", () => positionTiles(false));
}

function buildGoalGrid(): void {
  const goalTiles = createSolvedTiles();

  goalTiles.forEach((value) => {
    const tile = document.createElement("div");
    tile.className = value === 0 ? "goal-tile empty" : "goal-tile";
    tile.textContent = value === 0 ? "" : String(value);
    elements.goalGrid.appendChild(tile);
  });
}

function buildBoard(): void {
  elements.board.appendChild(emptySlot);

  for (let value = 1; value < GRID_SIZE * GRID_SIZE; value += 1) {
    const button = document.createElement("button");
    button.type = "button";
    button.className = "tile";
    button.textContent = String(value);
    button.setAttribute("aria-label", `Move tile ${value}`);
    button.addEventListener("click", () => handleTileClick(value));
    tileElements.set(value, button);
    elements.board.appendChild(button);
  }
}

function startNewGame(): void {
  hideWinBanner();
  state.tiles = createSolvedTiles();
  shuffleTiles(state.tiles);
  state.moveCount = 0;
  updateMoveCount();
  positionTiles(false);
}

function handleTileClick(tileValue: TileValue): void {
  if (state.isAnimating) {
    return;
  }

  const tileIndex = state.tiles.indexOf(tileValue);
  const emptyIndex = state.tiles.indexOf(0);

  if (!isAdjacent(tileIndex, emptyIndex)) {
    return;
  }

  [state.tiles[tileIndex], state.tiles[emptyIndex]] = [state.tiles[emptyIndex], state.tiles[tileIndex]];
  state.moveCount += 1;
  updateMoveCount();
  positionTiles(true);

  window.setTimeout(() => {
    if (isSolved(state.tiles)) {
      showWinBanner();
    }
  }, ANIMATION_DURATION_MS);
}

function positionTiles(animate: boolean): void {
  const metrics = getBoardMetrics();
  state.isAnimating = animate;

  state.tiles.forEach((value, index) => {
    const rect = getCellRect(index, metrics);

    if (value === 0) {
      positionTileElement(emptySlot, rect, metrics.fontSize, animate);
      return;
    }

    const tile = tileElements.get(value);
    if (!tile) {
      throw new Error(`Missing button for tile ${value}.`);
    }

    positionTileElement(tile, rect, metrics.fontSize, animate);
    tile.disabled = false;
  });

  if (!animate) {
    return;
  }

  window.setTimeout(() => {
    state.isAnimating = false;
  }, ANIMATION_DURATION_MS);
}

function positionTileElement(
  element: HTMLElement,
  rect: CellRect,
  fontSize: number,
  animate: boolean
): void {
  element.classList.toggle("instant", !animate);
  element.style.width = `${rect.size}px`;
  element.style.height = `${rect.size}px`;
  element.style.transform = `translate(${rect.x}px, ${rect.y}px)`;
  element.style.fontSize = `${fontSize}px`;

  if (!animate) {
    window.requestAnimationFrame(() => element.classList.remove("instant"));
    return;
  }

  window.setTimeout(() => {
    element.classList.remove("instant");
  }, ANIMATION_DURATION_MS + 10);
}

function getBoardMetrics(): BoardMetrics {
  const size = Math.min(elements.board.clientWidth, elements.board.clientHeight);
  const usable = size - BOARD_PADDING * 2 - BOARD_GAP * (GRID_SIZE - 1);
  const cellSize = usable / GRID_SIZE;
  const fontSize = clamp(cellSize * 0.34, 18, 40);

  return {
    cellSize,
    fontSize
  };
}

function getCellRect(index: number, metrics: BoardMetrics): CellRect {
  const row = Math.floor(index / GRID_SIZE);
  const column = index % GRID_SIZE;

  return {
    x: BOARD_PADDING + column * (metrics.cellSize + BOARD_GAP),
    y: BOARD_PADDING + row * (metrics.cellSize + BOARD_GAP),
    size: metrics.cellSize
  };
}

function updateMoveCount(): void {
  elements.moveCount.textContent = String(state.moveCount);
}

function showWinBanner(): void {
  elements.winMessage.textContent = `${WIN_MESSAGE_PREFIX} ${state.moveCount} moves.`;
  elements.winBanner.hidden = false;
}

function hideWinBanner(): void {
  elements.winBanner.hidden = true;
}

function createSolvedTiles(): TileValue[] {
  const tiles: TileValue[] = [];

  for (let value = 1; value < GRID_SIZE * GRID_SIZE; value += 1) {
    tiles.push(value);
  }

  tiles.push(0);
  return tiles;
}

function shuffleTiles(tiles: TileValue[]): void {
  let emptyIndex = tiles.length - 1;
  let previousEmptyIndex = -1;

  for (let move = 0; move < SHUFFLE_MOVES; move += 1) {
    const candidates = getAdjacentIndices(emptyIndex).filter((index) => index !== previousEmptyIndex);
    const nextIndex = candidates[Math.floor(Math.random() * candidates.length)];

    [tiles[emptyIndex], tiles[nextIndex]] = [tiles[nextIndex], tiles[emptyIndex]];
    previousEmptyIndex = emptyIndex;
    emptyIndex = nextIndex;
  }

  if (isSolved(tiles)) {
    const candidates = getAdjacentIndices(emptyIndex);
    const nextIndex = candidates[Math.floor(Math.random() * candidates.length)];
    [tiles[emptyIndex], tiles[nextIndex]] = [tiles[nextIndex], tiles[emptyIndex]];
  }
}

function isSolved(tiles: TileValue[]): boolean {
  for (let index = 0; index < tiles.length - 1; index += 1) {
    if (tiles[index] !== index + 1) {
      return false;
    }
  }

  return tiles[tiles.length - 1] === 0;
}

function isAdjacent(index1: number, index2: number): boolean {
  const row1 = Math.floor(index1 / GRID_SIZE);
  const column1 = index1 % GRID_SIZE;
  const row2 = Math.floor(index2 / GRID_SIZE);
  const column2 = index2 % GRID_SIZE;

  return (Math.abs(row1 - row2) === 1 && column1 === column2)
    || (Math.abs(column1 - column2) === 1 && row1 === row2);
}

function getAdjacentIndices(index: number): number[] {
  const adjacent: number[] = [];
  const row = Math.floor(index / GRID_SIZE);
  const column = index % GRID_SIZE;

  if (row > 0) {
    adjacent.push(index - GRID_SIZE);
  }
  if (row < GRID_SIZE - 1) {
    adjacent.push(index + GRID_SIZE);
  }
  if (column > 0) {
    adjacent.push(index - 1);
  }
  if (column < GRID_SIZE - 1) {
    adjacent.push(index + 1);
  }

  return adjacent;
}

function clamp(value: number, min: number, max: number): number {
  return Math.min(max, Math.max(min, value));
}

function requireElement<T extends HTMLElement>(id: string): T {
  const element = document.getElementById(id);

  if (!(element instanceof HTMLElement)) {
    throw new Error(`Missing element with id "${id}".`);
  }

  return element as T;
}

