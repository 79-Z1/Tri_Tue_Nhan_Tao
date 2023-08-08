package Minimax;

import java.util.Scanner;

public class TicTacToe {
    private static final int SIZE = 3;
    private static final int BOARD_SIZE = SIZE * SIZE;
    private static final int PLAYER_X = -1;
    private static final int PLAYER_O = 1;

    private static void veBanCo(int[] board) {
        System.out.println("━━━━━━━━━━━━━━━");
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                int k = i * SIZE + j;
                System.out.print("│ ");
                if (board[k] == 0) {
                    System.out.print(k + 1 + " │");
                } else {
                    System.out.print((board[k] == PLAYER_X) ? "X │" : "\uD835\uDC0E │");
                }
            }
            System.out.println("\n━━━━━━━━━━━━━━━");
        }
    }

    private static int kiemTraThangThua(int[] board) {
        int[][] winCombinations =
        {
            {0, 1, 2}, {3, 4, 5}, {6, 7, 8}, {0, 3, 6},
            {1, 4, 7}, {2, 5, 8}, {0, 4, 8}, {2, 4, 6}
        };

        for (int i = 0; i < 8; i++) {
            int a = winCombinations[i][0];
            int b = winCombinations[i][1];
            int c = winCombinations[i][2];
            if (board[a] != 0 && board[a] == board[b] && board[b] == board[c]) {
                return board[c];
            }
        }
        return 0;
    }

    private static int minimax(int[] board, int player) {
        int winner = kiemTraThangThua(board);
        if (winner != 0) {
            return winner * player;
        }

        int move = -1;
        int score = -2;

        for (int i = 0; i < BOARD_SIZE; i++) {
            if (board[i] == 0) {
                board[i] = player;
                int thisScore = -minimax(board, player * -1);
                if (thisScore > score) {
                    score = thisScore;
                    move = i;
                }
                board[i] = 0; // Đặt lại bàn cờ sau khi thử
            }
        }
        if (move == -1) {
            return 0;
        }
        return score;
    }

    private static int computerMove(int[] board) {
        int move = -1;
        int score = -2;

        for (int i = 0; i < BOARD_SIZE; i++) {
            if (board[i] == 0) {
                board[i] = PLAYER_O;
                int tempScore = -minimax(board, PLAYER_X);
                board[i] = 0;
                if (tempScore > score) {
                    score = tempScore;
                    move = i;
                }
            }
        }
        return move;
    }

    public static void main(String[] args) {
        System.out.println("~~~~~~~~~~~~~ TIC TAC TOE ~~~~~~~~~~~~~");
        System.out.println("   BÀN CỜ:");
        veBanCo(new int[BOARD_SIZE]);

        System.out.println("Chỉ có thể di chuyển vào các số bạn thấy trên bàn cờ");
        int[] board = new int[BOARD_SIZE]; // Tất cả các giá trị = 0
        int moves = 0, k;

        Scanner scanner = new Scanner(System.in);
        while (moves < BOARD_SIZE) {
            int mv;
            System.out.print("Nhập lượt di chuyển của bạn: ");
            mv = scanner.nextInt();
            if (mv >= 1 && mv <= BOARD_SIZE && board[mv - 1] == 0) {
                board[mv - 1] = PLAYER_X;
                moves++;
                System.out.println("Bàn cờ sau lượt di chuyển của bạn:");
                veBanCo(board);
                int result = kiemTraThangThua(board);
                if (result == 0) {
                    k = computerMove(board);
                    if (k != -1) {
                        board[k] = PLAYER_O;
                        System.out.println("Bàn cờ sau lượt di chuyển của máy:");
                        veBanCo(board);
                        moves++;
                        result = kiemTraThangThua(board);
                        if (result != 0) {
                            break;
                        }
                    }
                } else {
                    break;
                }
            } else {
                System.out.println("************* NƯỚC ĐI KHÔNG HỢP LỆ!!! HÃY THỬ LẠI *************");
            }
        }
        switch (kiemTraThangThua(board)) {
            case 0:
                System.out.println("************* HÒA!!! CHÚC BẠN MAY MẮN LẦN SAU *************");
                break;
            case PLAYER_X:
                System.out.println("************* CHIẾN THẮNG!!! ĐIỀU NÀY THẬT TUYỆT VỜI *************");
                break;
            case PLAYER_O:
                System.out.println("************* BẠN ĐÃ THUA RỒI!!! ĐỪNG BUỒN NHÉ *************");
                break;
        }
    }
}








