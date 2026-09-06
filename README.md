# Chess

A Java-based command-line chess game that supports standard gameplay, move validation, castling, en passant, promotion, and custom starting positions via FEN.

## Features

- Full chess board representation using Java classes
- Legal move generation and move validation
- Check and checkmate detection
- Castling, en passant, and pawn promotion support
- Draw detection for repetition and the fifty-move rule
- Custom board setup from Forsyth-Edwards Notation (FEN)
- Interactive terminal gameplay

## Project structure

The project is made up of Java classes for the board, pieces, move rules, and FEN parsing:

- `Board.java` – board state, move execution, board display
- `Main.java` – CLI entry point and game loop
- `Piece.java` and piece-specific classes (`Pawn.java`, `Knight.java`, etc.) – movement logic
- `FEN.java` – FEN parsing and board generation
- `Square.java` – square representation and board coordinates

## Getting started

From the project root, compile the Java source files:

```bash
javac *.java
```

Then run the game:

```bash
java Main
```

When the program starts, you can choose:

1. Standard starting position
2. Custom position (FEN input)

## Move format

Moves are entered as a coordinate string without spaces:

- Standard move: `e2e4`
- Castling: `e1g1` or `e8c8`
- En passant: `e5d6`
- Promotion: `e7e8=Q`

### Promotion piece symbols

- Knight: `N`
- Bishop: `B`
- Rook: `R`
- Queen: `Q`

## FEN support

This project accepts FEN input when starting from a custom board position. FEN describes the board layout, side to move, castling rights, en passant target, halfmove clock, and fullmove number.

Useful references:

- https://en.wikipedia.org/wiki/Forsyth%E2%80%93Edwards_Notation
- https://www.chess.com/terms/fen-chess

## Example gameplay

```text
1. Start from the standard position
White to move. Please enter the move:
e2e4
```

## Notes

This project is intended as a playable terminal chess application rather than a full engine with AI or network play. It focuses on rules enforcement and board logic in a compact Java implementation.
