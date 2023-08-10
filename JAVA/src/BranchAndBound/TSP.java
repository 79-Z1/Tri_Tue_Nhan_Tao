package BranchAndBound;

import java.util.Arrays;

class TSP {

    static int N = 4;
    static int[] duongDiCuoiCung = new int[N + 1];
    static boolean[] visited = new boolean[N];
    static int chiPhiToiThieu = Integer.MAX_VALUE;

    static String[] cityLabels = {"A", "B", "C", "D"};

    static void copyToFinal(int[] curr_path) {
        if (N >= 0) System.arraycopy(curr_path, 0, duongDiCuoiCung, 0, N);
        duongDiCuoiCung[N] = curr_path[0];
    }

    static int firstMin(int[][] maTranKe, int i) {
        int min = Integer.MAX_VALUE;
        for (int k = 0; k < N; k++)
            if (maTranKe[i][k] < min && i != k)
                min = maTranKe[i][k];
        return min;
    }

    static int secondMin(int[][] adj, int i) {
        int first = Integer.MAX_VALUE, second = Integer.MAX_VALUE;
        for (int j = 0; j < N; j++) {
            if (i == j)
                continue;

            if (adj[i][j] <= first) {
                second = first;
                first = adj[i][j];
            } else if (adj[i][j] <= second && adj[i][j] != first)
                second = adj[i][j];
        }
        return second;
    }

    static void TSPDeQuy(int[][] maTranKe, int gioiHanDuoiNutGoc, int chiPhiDoanDuongHienTai, int level, int[] doanDuongHienTai) {
        if (level == N) {
            if (maTranKe[doanDuongHienTai[level - 1]][doanDuongHienTai[0]] != 0) {
                int tongChiPhi = chiPhiDoanDuongHienTai + maTranKe[doanDuongHienTai[level - 1]][doanDuongHienTai[0]];

                if (tongChiPhi < chiPhiToiThieu) {
                    copyToFinal(doanDuongHienTai);
                    chiPhiToiThieu = tongChiPhi;
                    System.out.print("Đường đi tốt hơn tìm thấy: ");
                    for (int i = 0; i <= N; i++) {
                        System.out.printf("%s ", cityLabels[duongDiCuoiCung[i]]);
                    }
                    System.out.printf(" - Chi phí: %d\n", chiPhiToiThieu);
                }
            }
            return;
        }

        for (int i = 0; i < N; i++) {
            if (maTranKe[doanDuongHienTai[level - 1]][i] != 0 && !visited[i]) {
                int tam = gioiHanDuoiNutGoc;
                chiPhiDoanDuongHienTai += maTranKe[doanDuongHienTai[level - 1]][i];

                if (level == 1)
                    gioiHanDuoiNutGoc -= ((firstMin(maTranKe, doanDuongHienTai[level - 1]) + firstMin(maTranKe, i)) / 2);
                else
                    gioiHanDuoiNutGoc -= ((secondMin(maTranKe, doanDuongHienTai[level - 1]) + firstMin(maTranKe, i)) / 2);

                if (gioiHanDuoiNutGoc + chiPhiDoanDuongHienTai < chiPhiToiThieu) {
                    doanDuongHienTai[level] = i;
                    visited[i] = true;

                    TSPDeQuy(maTranKe, gioiHanDuoiNutGoc, chiPhiDoanDuongHienTai, level + 1, doanDuongHienTai);
                }

                chiPhiDoanDuongHienTai -= maTranKe[doanDuongHienTai[level - 1]][i];
                gioiHanDuoiNutGoc = tam;

                Arrays.fill(visited, false);
                for (int j = 0; j <= level - 1; j++)
                    visited[doanDuongHienTai[j]] = true;
            }
        }
    }

    static void TSP(int[][] maTranKe) {
        int[] doanDuongHienTai = new int[N + 1];

        int gioiHanDuoiNutGoc = 0;
        Arrays.fill(doanDuongHienTai, -1);
        Arrays.fill(visited, false);

        for (int i = 0; i < N; i++)
            gioiHanDuoiNutGoc += (firstMin(maTranKe, i) + secondMin(maTranKe, i));

        gioiHanDuoiNutGoc = (gioiHanDuoiNutGoc == 1) ? 1 : gioiHanDuoiNutGoc / 2;

        visited[0] = true;
        doanDuongHienTai[0] = 0;

        TSPDeQuy(maTranKe, gioiHanDuoiNutGoc, 0, 1, doanDuongHienTai);
    }

    // Hàm main
    public static void main(String[] args) {
        // Ma trận kề cho đồ thị cụ thể
        int[][] banDo = {
                {0, 4, 12, 7},
                {5, 0, 0, 18},
                {11, 0, 0, 6},
                {10, 2, 3, 0}
        };

        System.out.println("Bản đồ:");
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if (banDo[i][j] == 0) {
                    System.out.print("# ");
                } else {
                    System.out.print(banDo[i][j] + " ");
                }
            }
            System.out.println();
        }

        TSP(banDo);

        System.out.printf("Chi phí tối thiểu : %d\n", chiPhiToiThieu);
        System.out.print("Đường đi : ");
        for (int i = 0; i <= N; i++) {
            System.out.printf("%s ", cityLabels[duongDiCuoiCung[i]]);
        }
    }
}
