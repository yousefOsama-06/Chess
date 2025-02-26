import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner scn = new Scanner(System.in);
        Map<String, Integer> repetition = new HashMap<>();
        Board board;
        System.out.println("1. Start from the standard position\n" +
                "2. Start from a custom position");
        while (true) {
            try {
                String start = scn.nextLine();
                if (Objects.equals(start, "1")) {
                    board = new Board(null);
                } else if (Objects.equals(start, "2")) {
                    System.out.println("Enter the custom position in FEN format");
                    String fen = scn.nextLine();
                    board = new Board(new FEN(fen));
                } else
                    throw new Exception();
                break;
            } catch (Exception e) {
                System.err.println("Invalid Input\n");
            }
        }
        board.display();
        String fen = (new FEN(board)).board;
        repetition.putIfAbsent(fen, 0);
        repetition.put(fen, repetition.get(fen) + 1);
        while (!board.gameOver() && repetition.get(fen) < 3) {
            while (true) {
                System.out.println(board.toPlay + " to move. Please enter the move: ");
                String move = scn.nextLine();
                try {
                    board.play(move);
                    break;
                } catch (Exception e) {
                    System.err.println("Illegal Move!");
                }
            }
            board.display();
            fen = (new FEN(board)).board;
            repetition.putIfAbsent(fen, 0);
            repetition.put(fen, repetition.get(fen) + 1);
        }
        if (board.inCheck(Piece.Color.White))
            System.out.println("Black wins by checkmate!");
        else if (board.inCheck(Piece.Color.Black))
            System.out.println("White wins by checkmate!");
        else if (board.halfMove == 100)
            System.out.println("Draw by 50 Move Rule");
        else if (repetition.get(fen) == 3)
            System.out.println("Draw By repetition\n");
        else
            System.out.println("Draw by stalemate!");
    }
}