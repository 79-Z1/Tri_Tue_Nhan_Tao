package DoiTien;

import java.util.Scanner;

public class DoiTienVetCan {
    public static int[] doiTien(int soTienCanDoi, int[] menhGia, int index) {
        if (index == menhGia.length) {
            return new int[menhGia.length];
        }

        int soToTien = soTienCanDoi / menhGia[index];
        int soTienConLai = soTienCanDoi % menhGia[index];

        int[] ketQua = doiTien(soTienConLai, menhGia, index + 1);
        ketQua[index] = soToTien;

        return ketQua;
    }

    public static void main(String[] args) {
        int[] menhGia = { 100000, 50000, 20000, 10000 };
        Scanner scanner = new Scanner(System.in);
        System.out.print("Nhập số tiền cần đổi (phải là bội số của 10.000): ");
        int soTienCanDoi = scanner.nextInt();
        scanner.close();

        if (soTienCanDoi % 10000 != 0) {
            System.out.println("Số tiền cần đổi phải là bội số của 10.000.");
            return;
        }

        int[] ketQua = doiTien(soTienCanDoi, menhGia, 0);

        System.out.println("Kết quả đổi tiền: ");
        System.out.println("100.000 VND: " + ketQua[0]);
        System.out.println("50.000 VND: " + ketQua[1]);
        System.out.println("20.000 VND: " + ketQua[2]);
        System.out.println("10.000 VND: " + ketQua[3]);
    }
}
