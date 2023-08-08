package Minimax;

import java.util.Scanner;

class Nim {
    int n; // Số lượng viên sỏi ban đầu

    public Nim(int n) {
        this.n = n;
    }

    // Hàm để kiểm tra xem trò chơi đã kết thúc chưa
    public boolean isGameOver() {
        return n <= 0;
    }

    // Hàm để thực hiện nước đi của người chơi
    public void playerMove(int stones) {
        if (stones >= 1 && stones <= 3) {
            n -= stones;
            System.out.println("(🤷‍♂️) Người chơi lấy " + stones + " viên sỏi.");
        } else {
            System.out.println("❌ Vui lòng chọn từ 1 đến 3 viên sỏi.");
        }
    }

    // Hàm để thực hiện nước đi của AI bằng Minimax
    public void AIMove() {
        int move = findBestMove(n);
        n -= move;
        System.out.println("(🤖) AI lấy " + move + " viên sỏi.");
    }

    // Hàm để tìm nước đi tốt nhất cho AI bằng Minimax
    private int findBestMove(int stones) {
        int bestMove = 0;
        int bestValue = Integer.MIN_VALUE;

        for (int move = 1; move <= 3; move++) {
            int value = minimax(stones - move, false);
            if (value > bestValue) {
                bestValue = value;
                bestMove = move;
            }
        }

        return bestMove;
    }

    // Hàm Minimax
    private int minimax(int stones, boolean isMaximizingPlayer) {
        if (stones <= 0) {
            return (isMaximizingPlayer) ? Integer.MIN_VALUE : Integer.MAX_VALUE;
        }

        if (isMaximizingPlayer) {
            int bestValue = Integer.MIN_VALUE;
            for (int move = 1; move <= 3; move++) {
                int value = minimax(stones - move, false);
                bestValue = Math.max(bestValue, value);
            }
            return bestValue;
        } else {
            int bestValue = Integer.MAX_VALUE;
            for (int move = 1; move <= 3; move++) {
                int value = minimax(stones - move, true);
                bestValue = Math.min(bestValue, value);
            }
            return bestValue;
        }
    }
}

public class NimGame {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("🪨 Nhập số lượng viên sỏi ban đầu: ");
        int n = scanner.nextInt();

        Nim game = new Nim(n);

        while (!game.isGameOver()) {
            System.out.println("🪨 Số lượng viên sỏi còn lại: " + game.n);

            System.out.print("(🤷‍♂️) Người chơi lấy bao nhiêu viên sỏi? (1-3): ");
            int playerMove = scanner.nextInt();
            game.playerMove(playerMove);

            if (game.isGameOver()) {
                System.out.println("(🤷‍♂️) Người chơi thắng cuộc!");
                break;
            }

            game.AIMove();

            if (game.isGameOver()) {
                System.out.println("(🤖) AI thắng cuộc!");
            }
        }
    }
}






