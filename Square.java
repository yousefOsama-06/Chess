public class Square {
    int r, f;
    char file, rank;
    Piece piece;

    Square(int i, int j) {
        r = i;
        f = j;
        file = (char) ('a' + f);
        rank = (char) ('1' + r);
    }

    String getName() {
        return Character.toString(file) + Character.toString(rank);
    }

    char symbol() {
        if (piece == null)
            return ' ';
        return piece.symbol;
    }
}