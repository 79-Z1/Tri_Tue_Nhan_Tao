package BranchAndBound;

import java.util.Arrays;

class TSP {

    static int N = 4;

    // lưu trữ lời giải cuối cùng, tức là đường đi của người bán hàng.
    static int[] duongDiCuoiCung = new int[N + 1];

    // theo dõi các đỉnh đã được thăm trong một đường đi cụ thể
    static boolean[] visited = new boolean[N];

    // Lưu trữ chi phí tối thiểu cuối cùng của đoạn đường ngắn nhất.
    static int chiPhiToiThieu = Integer.MAX_VALUE;

    // Hàm để sao chép lời giải tạm thời vào lời giải cuối cùng
    static void copyToFinal(int[] curr_path) {
        if (N >= 0) System.arraycopy(curr_path, 0, duongDiCuoiCung, 0, N);
        duongDiCuoiCung[N] = curr_path[0];
    }

    // Hàm tìm trọng số cạnh nhỏ nhất có đầu tại đỉnh i
    static int firstMin(int[][] maTranKe, int i) {
        int min = Integer.MAX_VALUE;
        for (int k = 0; k < N; k++)
            if (maTranKe[i][k] < min && i != k)
                min = maTranKe[i][k];
        return min;
    }

    // Hàm tìm trọng số cạnh nhỏ thứ hai có đầu tại đỉnh i
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
        int[][] maTranKe = {
                {0, 10, 15, 20},
                {10, 0, 35, 25},
                {15, 35, 0, 30},
                {20, 25, 30, 0}
        };

        TSP(maTranKe);

        System.out.printf("Chi phí tối thiểu : %d\n", chiPhiToiThieu);
        System.out.print("Đường đi : ");
        for (int i = 0; i <= N; i++) {
            System.out.printf("%d ", duongDiCuoiCung[i]);
        }
    }
}
