class Queen extends Piece {
    public Queen(Board board, Color color) {
        this.board = board;
        type = Type.Queen;
        this.color = color;
        symbol = color == Color.White ? 'Q' : 'q';
    }

    @Override
    void updateMoves() {
        moves.clear();
        addRookMoves();
        addBishopMoves();
    }
}