class Knight extends Piece {
    public Knight(Board board, Color color) {
        this.board = board;
        type = Type.Knight;
        this.color = color;
        symbol = color == Color.White ? 'N' : 'n';
    }

    @Override
    void updateMoves() {
        moves.clear();
        int[] dx = {-1, -1, 1, 1, -2, -2, 2, 2};
        int[] dy = {-2, 2, -2, 2, -1, 1, -1, 1};
        for (int i = 0; i < 8; i++) {
            int nx = square.r + dx[i];
            int ny = square.f + dy[i];
            if (nx >= 0 && ny >= 0 && nx < 8 && ny < 8 && (board.squares[nx][ny].piece == null || board.squares[nx][ny].piece.color != color)) {
                moves.addElement(board.squares[nx][ny]);
            }
        }
    }
}