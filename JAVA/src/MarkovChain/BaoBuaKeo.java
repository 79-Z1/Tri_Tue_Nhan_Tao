package MarkovChain;

import java.util.Random;
import java.util.Scanner;

class BBKAI {
    private float[][] maTranMarkov;
    private int[] soLanDaChoi;

    private int nuocDiTruoc;
    private int nuocDiTruocTruoc;

    BBKAI() {
        maTranMarkov = new float[][] {{0.33f, 0.33f, 0.33f}, {0.33f, 0.33f, 0.33f}, {0.33f, 0.33f, 0.33f}};
        soLanDaChoi = new int[] {0, 0, 0};
    }

    public String taoNuocDi() {
        Random rand = new Random();
        float soNgauNhien = rand.nextFloat();
        if (soNgauNhien <= maTranMarkov[nuocDiTruoc][0]) {
            return "búa";
        } else if (soNgauNhien <= maTranMarkov[nuocDiTruoc][1] + maTranMarkov[nuocDiTruoc][0]) {
            return "bao";
        } else {
            return "kéo";
        }
    }

    public void capNhat(String nuocDiMoi) {
        nuocDiTruocTruoc = nuocDiTruoc;
        if (nuocDiMoi.equals("búa")) {
            nuocDiTruoc = 0;
        } else if (nuocDiMoi.equals("bao")) {
            nuocDiTruoc = 1;
        } else {
            nuocDiTruoc = 2;
        }

        for (int i = 0; i < 3; i++) {
            maTranMarkov[nuocDiTruocTruoc][i] *= soLanDaChoi[nuocDiTruocTruoc];
        }

        maTranMarkov[nuocDiTruocTruoc][nuocDiTruoc] += 1;
        soLanDaChoi[nuocDiTruocTruoc]++;

        for (int j = 0; j < 3; j++) {
            maTranMarkov[nuocDiTruocTruoc][j] /= soLanDaChoi[nuocDiTruocTruoc];
        }

        //tỷ lệ so với tổng số lần chơi của Bao/Búa/Kéo
        System.out.println("Ma trận xác suất chuỗi Markov mới:");
        System.out.println("Xác suất sau khi chơi " + soLanDaChoi[nuocDiTruocTruoc] + " lần:");
        System.out.println("Xác suất AI chon");
        System.out.println("Búa - Sau khi Bạn chọn Búa: " + (maTranMarkov[0][0] * 100) + "%   Búa - Sau khi Bạn chọn Bao: " + (maTranMarkov[0][1] * 100) + "%   Búa - Sau khi Bạn chọn Kéo: " + (maTranMarkov[0][2] * 100) + "%");
        System.out.println("Bao - Sau khi Bạn chọn Búa: " + (maTranMarkov[1][0] * 100) + "%   Bao - Sau khi Bạn chọn Bao: " + (maTranMarkov[1][1] * 100) + "%   Bao - Sau khi Bạn chọn Kéo: " + (maTranMarkov[1][2] * 100) + "%");
        System.out.println("Kéo - Sau khi Bạn chọn Búa: " + (maTranMarkov[2][0] * 100) + "%   Kéo - Sau khi Bạn chọn Bao: " + (maTranMarkov[2][1] * 100) + "%   Kéo - Sau khi Bạn chọn Kéo: " + (maTranMarkov[2][2] * 100) + "%");
    }
}

public class BaoBuaKeo {
    public static void main(String[] args) {
        boolean muonThoat = false;
        BBKAI doiThu = new BBKAI();
        Scanner banPhim = new Scanner(System.in);

        do {
            System.out.println("Chào mừng bạn đến với Thử thách Bao, Búa, Kéo AI!");
            System.out.println("Vui lòng chọn một tùy chọn số dưới đây:");
            System.out.println("1. Chơi Bao, Búa, Kéo với một AI mới");
            System.out.println("2. Thoát");
            System.out.print("Vui lòng nhập lựa chọn của bạn: ");

            int luaChon = Integer.parseInt(banPhim.nextLine());
            while (luaChon > 2 || luaChon < 1) {
                System.out.println("Lựa chọn không hợp lệ.");
                System.out.print("Vui lòng nhập lựa chọn hợp lệ: ");
                luaChon = Integer.parseInt(banPhim.nextLine());
            }

            switch (luaChon) {
                case 1:
                    choi(banPhim, doiThu);
                    break;
                case 2:
                    muonThoat = true;
                    break;
                default:
                    break;
            }

        } while (!muonThoat);
    }

    static void choi(Scanner banPhim, BBKAI doiThu) {
        System.out.println("Hãy chơi Đá, Búa, Kéo!");
        System.out.println("Nhập 'dừng' để thoát!!!");
        boolean dangChoi = true;

        do {
            System.out.print("Nhập 'bao', 'búa' 'kéo': ");
            String nuocDiNguoiChoi = banPhim.nextLine().trim().toLowerCase();
            if (nuocDiNguoiChoi.equalsIgnoreCase("dừng")) {
                dangChoi = false;
            }

            while (!nuocDiNguoiChoi.equals("búa") && !nuocDiNguoiChoi.equals("bao") && !nuocDiNguoiChoi.equals("kéo")) {
                System.out.println("Đây không phải nước đi hợp lệ!");
                System.out.print("Vui lòng nhập 'bao', 'búa' 'kéo': ");
                nuocDiNguoiChoi = banPhim.nextLine().trim().toLowerCase();
            }

            String nuocDiDoiThu = doiThu.taoNuocDi();
            int ketQua = ketQua(nuocDiNguoiChoi, nuocDiDoiThu);

            if (ketQua == 0) {
                System.out.println("Đó là một trận hòa! Cả hai đều chọn " + nuocDiNguoiChoi);
            } else if (ketQua == 1) {
                System.out.println("Bạn thắng! " + nuocDiNguoiChoi + " đánh bại " + nuocDiDoiThu);
            } else {
                System.out.println("Bạn thua! " + nuocDiDoiThu + " đánh bại " + nuocDiNguoiChoi);
            }

            doiThu.capNhat(nuocDiNguoiChoi);

        } while (dangChoi);
    }

    static int ketQua(String nuocDiNguoiChoi, String nuocDiDoiThu) {
        if (nuocDiNguoiChoi.equals(nuocDiDoiThu)) {
            return 0;
        } else if ((nuocDiNguoiChoi.equals("búa") && nuocDiDoiThu.equals("bao")) ||
                (nuocDiNguoiChoi.equals("bao") && nuocDiDoiThu.equals("kéo")) ||
                (nuocDiNguoiChoi.equals("kéo") && nuocDiDoiThu.equals("búa"))) {
            return -1;
        } else {
            return 1;
        }
    }
}