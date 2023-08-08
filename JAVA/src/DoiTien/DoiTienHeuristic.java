package DoiTien;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class DoiTienHeuristic {
    public static int heuristic(int soTien) {
        int[] menhGia = { 100000, 50000, 20000, 10000 };
        int result = 0;

        for (int giaTri : menhGia) {
            int soToTien = soTien / giaTri;
            result += soToTien;
            soTien -= giaTri * soToTien;
        }

        return result;
    }

    public static Map<Integer, Integer> doiTien(int soTienCanDoi) {
        int[] menhGia = { 100000, 50000, 20000, 10000 };
        Map<Integer, Integer> ketQua = new HashMap<>();

        while (soTienCanDoi > 0) {
            int minHeuristic = Integer.MAX_VALUE;
            int toToTien = 0;

            for (int giaTri : menhGia) {
                if (soTienCanDoi >= giaTri) {
                    int heuristicValue = heuristic(soTienCanDoi - giaTri);
                    if (heuristicValue < minHeuristic) {
                        minHeuristic = heuristicValue;
                        toToTien = giaTri;
                    }
                }
            }

            if (toToTien == 0) {
                break; // Không đổi được nữa, thoát khỏi vòng lặp
            }

            int soToTien = soTienCanDoi / toToTien;
            ketQua.put(toToTien, soToTien);
            soTienCanDoi -= toToTien * soToTien;
        }

        return ketQua;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Nhập số tiền cần đổi: ");
        int soTienCanDoi = scanner.nextInt();
        scanner.close();

        Map<Integer, Integer> ketQua = doiTien(soTienCanDoi);

        System.out.println("Kết quả đổi tiền: ");
        for (Map.Entry<Integer, Integer> entry : ketQua.entrySet()) {
            int giaTri = entry.getKey();
            int soToTien = entry.getValue();
            System.out.println(giaTri + " VND: " + soToTien);
        }
    }
}





