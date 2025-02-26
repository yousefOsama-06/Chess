class King extends Piece {
    public King(Board board, Color color) {
        this.board = board;
        type = Type.King;
        this.color = color;
        symbol = color == Color.White ? 'K' : 'k';
    }

    @Override
    void updateMoves() throws Exception {
        moves.clear();
        int[] dx = {-1, -1, -1, 0, 0, 1, 1, 1};
        int[] dy = {-1, 0, 1, -1, 1, -1, 0, 1};
        for (int i = 0; i < 8; i++) {
            int nx = square.r + dx[i];
            int ny = square.f + dy[i];
            if (nx >= 0 && ny >= 0 && nx < 8 && ny < 8 && (board.squares[nx][ny].piece == null || board.squares[nx][ny].piece.color != color)) {
                moves.addElement(board.squares[nx][ny]);
            }
        }

        if (board.toPlay == color) {
            if (color == Color.White) {
                if (board.castle_K && !board.isAttacked(Color.White, board.squares[0][4]) && !board.isAttacked(Color.White, board.squares[0][5]) && !board.isAttacked(Color.White, board.squares[0][6])
                        && board.squares[0][5].piece == null && board.squares[0][6].piece == null) {
                    moves.addElement(board.squares[0][6]);
                }
                if (board.castle_Q && !board.isAttacked(Color.White, board.squares[0][4]) && !board.isAttacked(Color.White, board.squares[0][3]) && !board.isAttacked(Color.White, board.squares[0][2])
                        && board.squares[0][3].piece == null && board.squares[0][2].piece == null && board.squares[0][1].piece == null) {
                    moves.add(board.squares[0][2]);
                }
            } else {
                if (board.castle_k && !board.isAttacked(Color.Black, board.squares[7][4]) && !board.isAttacked(Color.Black, board.squares[7][5]) && !board.isAttacked(Color.Black, board.squares[7][6])
                        && board.squares[7][5].piece == null && board.squares[7][6].piece == null) {
                    moves.addElement(board.squares[7][6]);
                }
                if (board.castle_q && !board.isAttacked(Color.Black, board.squares[7][4]) && !board.isAttacked(Color.Black, board.squares[7][3]) && !board.isAttacked(Color.Black, board.squares[7][2])
                        && board.squares[7][3].piece == null && board.squares[7][2].piece == null && board.squares[7][1].piece == null) {
                    moves.add(board.squares[7][2]);
                }
            }
        }
    }
}