import java.util.Objects;

class Pawn extends Piece {
    public Pawn(Board board, Color color) {
        this.board = board;
        type = Type.Pawn;
        this.color = color;
        symbol = color == Color.White ? 'P' : 'p';
    }


    @Override
    void updateMoves() {
        moves.clear();
        int startRank = color == Color.White ? 1 : 6;
        int direction = color == Color.White ? 1 : -1;
        if (board.squares[square.r + direction][square.f].piece == null) {
            moves.addElement(board.squares[square.r + direction][square.f]);
            if (square.r == startRank && board.squares[square.r + 2 * direction][square.f].piece == null)
                moves.addElement(board.squares[square.r + 2 * direction][square.f]);
        }
        if (square.f - 1 >= 0 && (board.squares[square.r + direction][square.f - 1].piece != null && board.squares[square.r + direction][square.f - 1].piece.color != color
                || Objects.equals(board.enPassant, board.squares[square.r + direction][square.f - 1]))) {
            moves.addElement(board.squares[square.r + direction][square.f - 1]);
        }
        if (square.f + 1 < 8 && (board.squares[square.r + direction][square.f + 1].piece != null && board.squares[square.r + direction][square.f + 1].piece.color != color
                || Objects.equals(board.enPassant, board.squares[square.r + direction][square.f + 1]))) {
            moves.addElement(board.squares[square.r + direction][square.f + 1]);
        }
    }
}