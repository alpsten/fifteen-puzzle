# Fifteen Puzzle

A 15-puzzle project with two playable versions:

- A Java Swing desktop app for local play.
- A Vite + TypeScript web app that can be built into `docs/` and hosted on GitHub Pages.

## Features

- **Structured source layout**: The code is organized under `src/fifteenpuzzle` with separate `app`, `controller`, `logic`, `model`, and `ui` packages.
- **Guaranteed solvable shuffling**: New games are generated from valid tile moves, so every board can be solved.
- **Separate board model**: Puzzle state and move counting live in `PuzzleBoard`, while Swing classes only render the board and handle input.
- **Refreshed Swing UI**: Colors, fonts, borders, and button styling live in a dedicated theme class instead of the application entry point.

## Project Structure

| Path | Responsibility |
|------|----------------|
| `src/fifteenpuzzle/app` | Application startup |
| `src/fifteenpuzzle/controller` | User-triggered actions |
| `src/fifteenpuzzle/logic` | Puzzle rules, shuffling, win checks |
| `src/fifteenpuzzle/model` | Board and tile state |
| `src/fifteenpuzzle/ui` | Swing rendering and styling |
| `web/` | Vite + TypeScript source for the web app |
| `docs/` | Generated static build output for GitHub Pages |

## Run The Java App

Compile:

```bash
find src -name '*.java' -exec javac -d out {} +
```

Run:

```bash
java -cp out fifteenpuzzle.app.FifteenPuzzleApp
```

## Run The Web App Locally

Install dependencies:

```bash
npm install
```

Start the Vite dev server:

```bash
npm run dev
```

Build the production site into `docs/`:

```bash
npm run build
```
