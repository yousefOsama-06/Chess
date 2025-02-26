public class FEN {
    String board, toPlay, castle, enPassant, halfMove, fullMove;

    FEN() {
        board = "rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR";
        toPlay = "w";
        castle = "KQkq";
        enPassant = "-";
        halfMove = "0";
        fullMove = "1";
    }

    FEN(String code) {
        String[] codes = code.split(" ");
        board = codes[0];
        toPlay = codes[1];
        castle = codes[2];
        enPassant = codes[3];
        halfMove = codes[4];
        fullMove = codes[5];
    }

    FEN(Board pos) {
        board = "";
        toPlay = "";
        castle = "";
        enPassant = "";
        halfMove = "";
        fullMove = "";
        for (int i = 7; i >= 0; i--) {
            for (int j = 0; j < 8; j++) {
                if (pos.squares[i][j].piece == null) {
                    if (board.isEmpty() || !Character.isDigit(board.charAt(board.length() - 1))) {
                        board += "1";
                    } else {
                        char last = board.charAt(board.length() - 1);
                        last = (char) (last + 1);
                        board = board.substring(0, board.length() - 1) + last;
                    }
                } else {
                    board += pos.squares[i][j].symbol();
                }
            }
            if (i != 0)
                board += "/";
        }
        toPlay = pos.toPlay == Piece.Color.White ? "w" : "b";
        if (pos.castle_K)
            castle += "K";
        if (pos.castle_Q)
            castle += "Q";
        if (pos.castle_k)
            castle += "k";
        if (pos.castle_q)
            castle += "q";
        if (castle.isEmpty())
            castle = "-";

        if (pos.enPassant == null)
            enPassant = "-";
        else
            enPassant = pos.enPassant.getName();
        halfMove = String.valueOf(pos.halfMove);
        fullMove = String.valueOf(pos.fullMove);
    }

    String get() {
        return board + " " + toPlay + " " + castle + " " + enPassant + " " + halfMove + " " + fullMove;
    }
}