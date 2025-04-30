# Fifteen Puzzle

A Java-based implementation of the classic 15-puzzle game. The goal is to arrange 15 numbered tiles in order on a 4x4 grid by sliding them one at a time into the empty space.

## Features

- **Object-oriented design**: The code is split into multiple classes for better structure, clarity, and reusability.  
- **User interaction**: Tiles can be moved by clicking, making the game intuitive to play.  
- **Random shuffling**: The board is shuffled at the start to ensure a new challenge each game.  
- **Win detection**: The game detects when the puzzle is solved and displays a victory message.  

## Known Limitations

- **Solvability**: The current shuffling algorithm does not guarantee that every puzzle is solvable. A solution-checking algorithm could be added in the future.

## Class Overview

| Class Name                      | Description                                                  |
|--------------------------------|--------------------------------------------------------------|
| `FifteenPuzzle.java`           | Main class that starts the application.                      |
| `GameGrid.java`                | Manages the layout and tile placement on the board.          |
| `GameTile.java`                | Represents an individual tile in the puzzle.                 |
| `GameTileClickHandler.java`    | Handles user clicks on the tiles.                            |
| `GameTileShuffler.java`        | Shuffles the tiles at the beginning of the game.             |
| `NewGameHandler.java`          | Resets and starts a new game when triggered.                 |
| `WinnerWinnerChickenDinner.java` | Displays the victory message when the puzzle is solved.    |

