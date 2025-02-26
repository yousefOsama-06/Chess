import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class Board {
    Square[][] squares = new Square[8][8];
    Piece.Color toPlay;
    boolean castle_K, castle_Q, castle_k, castle_q;
    Square enPassant;
    int halfMove, fullMove;


    Board(FEN fen) throws Exception {
        if (fen == null)
            fen = new FEN();
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                squares[i][j] = new Square(i, j);
            }
        }
        int x = 7, y = 0;
        for (int i = 0; i < fen.board.length(); i++) {
            char symbol = fen.board.charAt(i);
            if (symbol == '/') {
                x--;
                y = 0;
            } else if (Character.isDigit(symbol)) {
                y += symbol - '0';
            } else {
                squares[x][y].piece = getPiece(symbol);
                squares[x][y].piece.square = squares[x][y];
                y++;
            }
        }
        toPlay = fen.toPlay.charAt(0) == 'w' ? Piece.Color.White : Piece.Color.Black;
        for (int i = 0; i < fen.castle.length(); i++) {
            char symbol = fen.castle.charAt(i);
            if (symbol == 'K')
                castle_K = true;
            else if (symbol == 'Q')
                castle_Q = true;
            else if (symbol == 'k')
                castle_k = true;
            else if (symbol == 'q')
                castle_q = true;
        }
        if (!Objects.equals(fen.enPassant, "-")) {
            int f = fen.enPassant.charAt(0) - 'a';
            int r = fen.enPassant.charAt(1) - '1';
            enPassant = squares[r][f];
        } else {
            enPassant = null;
        }
        halfMove = Integer.parseInt(fen.halfMove);
        fullMove = Integer.parseInt(fen.fullMove);
    }

    void updateMoves(Piece.Color color, boolean removeIllegal) throws Exception {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (squares[i][j].piece != null && squares[i][j].piece.color == color) {
                    squares[i][j].piece.updateMoves();
                    if (removeIllegal)
                        squares[i][j].piece.removeIllegal();
                }
            }
        }
    }

    boolean gameOver() throws Exception {
        if (halfMove == 100)
            return true;
        updateMoves(toPlay, true);
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (squares[i][j].piece != null) {
                    if (squares[i][j].piece.color == toPlay && !squares[i][j].piece.moves.isEmpty())
                        return false;
                }
            }
        }
        return true;
    }

    boolean isAttacked(Piece.Color defender, Square square) throws Exception {
        Piece.Color attacker = defender == Piece.Color.White ? Piece.Color.Black : Piece.Color.White;
        updateMoves(attacker, false);
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (squares[i][j].piece != null && squares[i][j].piece.color == attacker) {
                    for (Square move : squares[i][j].piece.moves) {
                        if (move == square)
                            return true;
                    }
                }
            }
        }
        return false;
    }

    boolean inCheck(Piece.Color defender) throws Exception {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (squares[i][j].piece != null && squares[i][j].piece.color == defender && squares[i][j].piece.type == Piece.Type.King)
                    return isAttacked(defender, squares[i][j]);
            }
        }
        return false;
    }

    FEN getFEN() {
        return new FEN(this);
    }

    void display() {
        Map<Character, String> pieces = new HashMap<>();
        pieces.put('P', "♙");
        pieces.put('N', "♘");
        pieces.put('B', "♗");
        pieces.put('R', "♖");
        pieces.put('Q', "♕");
        pieces.put('K', "♔");

        pieces.put('p', "♟");
        pieces.put('n', "♞");
        pieces.put('b', "♝");
        pieces.put('r', "♜");
        pieces.put('q', "♛");
        pieces.put('k', "♚");

        pieces.put(' ', "      ");
        for (int i = 7; i >= 0; i--) {
            System.out.print("  ");
            for (int j = 0; j < 8; j++) {
                System.out.print("+ ﹘ ﹘ ﹘ ﹘");
            }
            System.out.println('+');
            System.out.print(i + 1);
            System.out.print(' ');
            for (int j = 0; j < 8; j++) {
                System.out.print("|     ");
                System.out.print(pieces.get(squares[i][j].symbol()));
                System.out.print("     ");
            }
            System.out.println('|');
        }
        System.out.print("  ");
        for (int j = 0; j < 8; j++) {
            System.out.print("+ ﹘ ﹘ ﹘ ﹘");
        }
        System.out.println('+');
        System.out.print("  ");
        for (int j = 0; j < 8; j++) {
            System.out.print("        ");
            System.out.print((char) ('a' + j));
            System.out.print("        ");
        }
        System.out.println();
    }

    void move(Square a, Square b, Piece promote) {
        if (a.piece.type == Piece.Type.Pawn) {
            // En Passant Update
            if (Math.abs(a.r - b.r) == 2) {
                enPassant = squares[(a.r + b.r) / 2][a.f];
            }
            // En passant Check
            if (enPassant == b)
                squares[b.r + (a.piece.color == Piece.Color.White ? -1 : 1)][b.f].piece = null;
        } else {
            enPassant = null;
        }

        // Ability to Castle Update
        if (Objects.equals(a.getName(), "h1") || Objects.equals(a.getName(), "e1"))
            castle_K = false;
        if (Objects.equals(a.getName(), "a1") || Objects.equals(a.getName(), "e1"))
            castle_Q = false;
        if (Objects.equals(a.getName(), "h8") || Objects.equals(a.getName(), "e8"))
            castle_k = false;
        if (Objects.equals(a.getName(), "a8") || Objects.equals(a.getName(), "e8"))
            castle_q = false;

        if (Objects.equals(b.getName(), "h1") || Objects.equals(b.getName(), "e1"))
            castle_K = false;
        if (Objects.equals(b.getName(), "a1") || Objects.equals(b.getName(), "e1"))
            castle_Q = false;
        if (Objects.equals(b.getName(), "h8") || Objects.equals(b.getName(), "e8"))
            castle_k = false;
        if (Objects.equals(b.getName(), "a8") || Objects.equals(b.getName(), "e8"))
            castle_q = false;

        // Castle Check
        if (a.piece.type == Piece.Type.King) {
            if (a.file == 'e' && b.file == 'g') {
                move(squares[a.r][7], squares[a.r][5], null);
            } else if (a.file == 'e' && b.file == 'c') {
                move(squares[a.r][0], squares[a.r][3], null);
            }
        }

        if (promote == null)
            b.piece = a.piece;
        else
            b.piece = promote;
        b.piece.square = b;
        a.piece = null;
    }

    void play(String move) throws Exception {
        int f1 = move.charAt(0) - 'a';
        int r1 = move.charAt(1) - '1';
        int f2 = move.charAt(2) - 'a';
        int r2 = move.charAt(3) - '1';
        if (squares[r1][f1].piece != null && squares[r1][f1].piece.color == toPlay) {
            boolean valid = false;
            for (Square sq : squares[r1][f1].piece.moves) {
                if (sq == squares[r2][f2]) {
                    valid = true;
                    break;
                }
            }
            if (valid) {
                if (toPlay == Piece.Color.Black)
                    fullMove++;
                halfMove++;
                if (squares[r1][f1].piece.type == Piece.Type.Pawn || squares[r2][f2].piece != null)
                    halfMove = 0;
                if (squares[r1][f1].piece.type == Piece.Type.Pawn && (r2 == 7 || r2 == 0) && move.length() != 6)
                    throw new Exception();
                if (move.length() == 6) {
                    char symbol = move.charAt(5);
                    if (toPlay == Piece.Color.White)
                        symbol = Character.toUpperCase(symbol);
                    else
                        symbol = Character.toLowerCase(symbol);
                    Piece promote = getPiece(symbol);
                    if (promote.type == Piece.Type.King || promote.type == Piece.Type.Pawn)
                        throw new Exception();
                    move(squares[r1][f1], squares[r2][f2], promote);
                } else
                    move(squares[r1][f1], squares[r2][f2], null);
                toPlay = toPlay == Piece.Color.White ? Piece.Color.Black : Piece.Color.White;
            } else {
                throw new Exception();
            }
        } else {
            throw new Exception();
        }
    }

    private Piece getPiece(char symbol) throws Exception {
        Piece piece;
        Piece.Color color = Character.isUpperCase(symbol) ? Piece.Color.White : Piece.Color.Black;
        symbol = Character.toUpperCase(symbol);
        if (symbol == 'P')
            piece = new Pawn(this, color);
        else if (symbol == 'N')
            piece = new Knight(this, color);
        else if (symbol == 'B')
            piece = new Bishop(this, color);
        else if (symbol == 'R')
            piece = new Rook(this, color);
        else if (symbol == 'Q')
            piece = new Queen(this, color);
        else if (symbol == 'K')
            piece = new King(this, color);
        else
            throw new Exception();
        return piece;
    }
}