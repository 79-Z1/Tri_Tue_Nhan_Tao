package MarkovChain;

import java.util.Random;
import java.util.Scanner;

class rpsAI {
    private float[][] markovChain;
    private int[] timesPlayed;

    private int lastMove;
    private int moveBeforeLast;

    rpsAI() {
        markovChain = new float[][] {{0.33f, 0.33f, 0.33f}, {0.33f, 0.33f, 0.33f}, {0.33f, 0.33f, 0.33f}};
        timesPlayed = new int[] {0, 0, 0};
    }

    public String makeMove() {
        Random rand = new Random();
        float ranFloat = rand.nextFloat();
        if (ranFloat <= markovChain[lastMove][1]) {
            return "bao";
        } else if (ranFloat <= markovChain[lastMove][2] + markovChain[lastMove][1]) {
            return "búa";
        } else {
            return "kéo";
        }
    }

    public void update(String newMove) {
        moveBeforeLast = lastMove;
        if (newMove.equals("búa")) {
            lastMove = 0;
        } else if (newMove.equals("bao")) {

            lastMove = 1;
        } else {
            lastMove = 2;
        }

        for (int i = 0; i < 3; i++) {
            markovChain[moveBeforeLast][i] *= timesPlayed[moveBeforeLast];
        }

        markovChain[moveBeforeLast][lastMove] += 1;
        timesPlayed[moveBeforeLast]++;

        for (int j = 0; j < 3; j++) {
            markovChain[moveBeforeLast][j] /= timesPlayed[moveBeforeLast];
        }

        System.out.println("Ma trận xác suất chuỗi Markov mới");
        System.out.println("Búa - búa: " + markovChain[0][0] + " Búa - bao: " + markovChain[0][1] + " Búa - kéo: " + markovChain[0][2]);
        System.out.println("bao - búa: " + markovChain[1][0] + " bao - bao: " + markovChain[1][1] + " bao - kéo: " + markovChain[1][2]);
        System.out.println("Kéo - búa: " + markovChain[2][0] + " Kéo - bao: " + markovChain[2][1] + " Kéo - kéo: " + markovChain[2][2]);
    }

    private void setChain(int x, int y, float probability) {
        markovChain[x][y] = probability;
    }

    private void setTimesPlayed(int x, int y) {
        timesPlayed[x] = y;
    }
}

public class rock_paper_scissor {
    public static void main(String[] args) {
        boolean wannaQuit = false;
        rpsAI opponent = new rpsAI();
        Scanner keyboard = new Scanner(System.in);

        do {
            System.out.println("Chào mừng bạn đến với Thử thách Rock, Paper, Scissors AI!");
            System.out.println("Vui lòng chọn một tùy chọn số dưới đây:");
            System.out.println("1. Chơi RPS với một AI mới");
            System.out.println("2. Thoát");
            System.out.print("Vui lòng nhập lựa chọn của bạn: ");

            int choice = Integer.parseInt(keyboard.nextLine());
            while (choice > 3 || choice < 1) {
                System.out.println("Lựa chọn không hợp lệ.");
                System.out.print("Vui lòng nhập lựa chọn hợp lệ: ");
                keyboard.nextLine();
                choice = Integer.parseInt(keyboard.nextLine());
            }
            switch (choice) {
                case 1: play(keyboard, opponent);
                    break;
                case 2: wannaQuit = true;
                    break;
                default: break;
            }

        } while (!wannaQuit);
    }

    static void play(Scanner keyboard, rpsAI opponent) {
        System.out.println("Hãy chơi Rock, Paper, Scissors!");
        System.out.println("Nhập 'dừng' để thoát!!!");
        boolean playing = true;

        do {
            System.out.print("Nhập 'bao', 'búa' 'kéo': ");
            String playermove = keyboard.nextLine().trim().toLowerCase();
            if(playermove.equalsIgnoreCase("dừng")) {
                playing = false;
            }

            while (!playermove.equals("búa") && !playermove.equals("bao") && !playermove.equals("kéo")) {
                System.out.println("Đây không phải nước đi hợp lệ!");
                System.out.print("Vui lòng nhập 'bao', 'búa' 'kéo':");
                playermove = keyboard.nextLine().trim().toLowerCase();
            }

            String AIMove = opponent.makeMove();
            int result = results(playermove, AIMove);

            if (result == 0) {
                System.out.println("Đó là một trận hòa! Cả hai đều chọn " + playermove);
            } else if (result == 1) {
                System.out.println("Bạn thắng! " + playermove + " đánh bại " + AIMove);
            } else {
                System.out.println("Bạn thua! " + AIMove + " đánh bại " + playermove);
            }

            opponent.update(playermove);

        } while (playing);
    }

    static int results(String playerMove, String AIMove) {
        if (playerMove.equals("búa")) {
            if (AIMove.equals("búa")) {
                return 0;
            } else if (AIMove.equals("bao")) {
                return -1;
            } else {
                return 1;
            }
        } else if (playerMove.equals("bao")) {
            if (AIMove.equals("búa")) {
                return 1;
            } else if (AIMove.equals("bao")) {
                return 0;
            } else {
                return -1;
            }
        } else {
            if (AIMove.equals("búa")) {
                return -1;
            } else if (AIMove.equals("bao")) {
                return 1;
            } else {
                return 0;
            }
        }
    }
}
