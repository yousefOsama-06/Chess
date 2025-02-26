import java.util.Vector;

class Piece {
    enum Type {
        Pawn,
        Knight,
        Bishop,
        Rook,
        Queen,
        King
    }

    enum Color {
        White, Black
    }

    Color color;
    char symbol;
    protected Type type;
    Board board;
    Square square;
    Vector<Square> moves = new Vector<>();


    void updateMoves() throws Exception {
    }

    void removeIllegal() throws Exception {
        Vector<Square> temp_moves = new Vector<>();
        for (Square b : moves) {
            Board temp_board = new Board(new FEN(board));
            temp_board.move(temp_board.squares[square.r][square.f], temp_board.squares[b.r][b.f], null);
            if (!temp_board.inCheck(color)) {
                temp_moves.addElement(b);
            }
        }
        moves = temp_moves;
    }

    void addRookMoves() {
        for (int x = square.r + 1; x < 8; x++) {
            Square b = board.squares[x][square.f];
            if (b.piece == null || b.piece.color != color) {
                moves.addElement(b);
            }
            if (b.piece != null) {
                break;
            }
        }
        for (int x = square.r - 1; x >= 0; x--) {
            Square b = board.squares[x][square.f];
            if (b.piece == null || b.piece.color != color) {
                moves.addElement(b);
            }
            if (b.piece != null) {
                break;
            }
        }


        for (int y = square.f + 1; y < 8; y++) {
            Square b = board.squares[square.r][y];
            if (b.piece == null || b.piece.color != color) {
                moves.addElement(b);
            }
            if (b.piece != null) {
                break;
            }
        }
        for (int y = square.f - 1; y >= 0; y--) {
            Square b = board.squares[square.r][y];
            if (b.piece == null || b.piece.color != color) {
                moves.addElement(b);
            }
            if (b.piece != null) {
                break;
            }
        }
    }

    void addBishopMoves() {
        for (int x = square.r + 1, y = square.f + 1; x < 8 && y < 8; x++, y++) {
            Square b = board.squares[x][y];
            if (b.piece == null || b.piece.color != color) {
                moves.addElement(b);
            }
            if (b.piece != null) {
                break;
            }
        }
        for (int x = square.r - 1, y = square.f - 1; x >= 0 && y >= 0; x--, y--) {
            Square b = board.squares[x][y];
            if (b.piece == null || b.piece.color != color) {
                moves.addElement(b);
            }
            if (b.piece != null) {
                break;
            }
        }


        for (int x = square.r + 1, y = square.f - 1; x < 8 && y >= 0; x++, y--) {
            Square b = board.squares[x][y];
            if (b.piece == null || b.piece.color != color) {
                moves.addElement(b);
            }
            if (b.piece != null) {
                break;
            }
        }
        for (int x = square.r - 1, y = square.f + 1; x >= 0 && y < 8; x--, y++) {
            Square b = board.squares[x][y];
            if (b.piece == null || b.piece.color != color) {
                moves.addElement(b);
            }
            if (b.piece != null) {
                break;
            }
        }
    }
}