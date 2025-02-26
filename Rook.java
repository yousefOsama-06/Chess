class Rook extends Piece {
    public Rook(Board board, Color color) {
        this.board = board;
        type = Type.Rook;
        this.color = color;
        symbol = color == Color.White ? 'R' : 'r';
    }

    @Override
    void updateMoves() {
        moves.clear();
        addRookMoves();
    }
}