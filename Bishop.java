class Bishop extends Piece {
    public Bishop(Board board, Color color) {
        this.board = board;
        type = Type.Bishop;
        this.color = color;
        symbol = color == Color.White ? 'B' : 'b';
    }

    @Override
    void updateMoves() {
        moves.clear();
        addBishopMoves();
    }
}