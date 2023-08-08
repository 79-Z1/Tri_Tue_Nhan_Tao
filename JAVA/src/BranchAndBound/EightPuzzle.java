package BranchAndBound;

import java.util.*;

public class EightPuzzle
{
    public static int N = 3;
    public static class Nut
    {
        Nut nutCha;
        int[][] maTran = new int[N][N];// stores matrix
        int x, y;
        int soOSai;
        int soLanDiChuyen;
    }

    // Hàm này dùng để in ma trận N x N đã cho.
    public static void inMaTran(int[][] maTran){
        for(int i = 0; i < N; i++){
            for(int j = 0; j < N; j++){
                if (maTran[i][j] == 0) {
                    System.out.print("▢ ");
                } else {
                    System.out.print(maTran[i][j] + " ");
                }
            }
            System.out.println();
        }
    }

    // Hàm này dùng để tạo một nút mới cho cây tìm kiếm của trò chơi.
    public static Nut newNut(
            int[][] matran, int x,
            int y, int newX, int newY,
            int soLanDiChuyen, Nut nutCha
    ) {
        Nut nut = new Nut();
        nut.nutCha = nutCha;
        nut.maTran = new int[N][N];

        for(int i = 0; i < N; i++){
            System.arraycopy(matran[i], 0, nut.maTran[i], 0, N);
        }

        int tam = nut.maTran[x][y];
        nut.maTran[x][y] = nut.maTran[newX][newY];
        nut.maTran[newX][newY]= tam;

        nut.soOSai = Integer.MAX_VALUE;
        nut.soLanDiChuyen = soLanDiChuyen;

        nut.x = newX;
        nut.y = newY;

        return nut;
    }

    // bottom, left, top, right
    public static int[] row = { 1, 0, -1, 0 };
    public static int[] col = { 0, -1, 0, 1 };

    // Hàm tính số ô sai vị trí (ô không nằm đúng vị trí)
    // giữa trạng thái nguồn và trạng thái đích.
    public static int tinhSoOSai(int[][] maTranNguon, int[][] maTranDich) {
        int dem = 0;
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if (maTranNguon[i][j] != 0 && maTranNguon[i][j] != maTranDich[i][j]) {
                    dem++;
                }
            }
        }
        return dem;
    }

    // Hàm kiểm tra xem tọa độ (x, y) cho trước có nằm trong phạm vi hợp lệ
    public static int toaDoHopLe(int x, int y)
    {
        return (x >= 0 && x < N && y >= 0 && y < N) ? 1 : 0;
    }

    // Hàm này in ra đường đi từ nút gốc đến nút đích.
    public static void inDuongDi(Nut nutGoc){
        if(nutGoc == null){
            return;
        }
        inDuongDi(nutGoc.nutCha);
        inMaTran(nutGoc.maTran);
        System.out.println();
    }

    public static class comp implements Comparator<Nut>{
        @Override
        public int compare(Nut lhs, Nut rhs){
            if ((lhs.soOSai + lhs.soLanDiChuyen) > (rhs.soOSai + rhs.soLanDiChuyen)) return 1;
            return -1;
        }
    }

    public static boolean kiemTraTrung(Nut nut, Nut nutCha) {
        while (nutCha != null) {
            if (Arrays.deepEquals(nut.maTran, nutCha.maTran)) {
                return true;
            }
            nutCha = nutCha.nutCha;
        }
        return false;
    }

    public static void thuatToanBranchAndBound(int[][] maTranNguon, int x, int y, int[][] maTranDich)
    {
        PriorityQueue<Nut> hangDoiUuTien = new PriorityQueue<>(new comp());
        Nut nutGoc = newNut(maTranNguon, x, y, x, y, 0, null);
        nutGoc.soOSai = tinhSoOSai(maTranNguon, maTranDich);
        hangDoiUuTien.add(nutGoc);

        while (!hangDoiUuTien.isEmpty()) {
            Nut nutCoGiaTriTotNhat = hangDoiUuTien.peek();
            hangDoiUuTien.poll();

            if (nutCoGiaTriTotNhat.soOSai == 0) {
                inDuongDi(nutCoGiaTriTotNhat);
                return;
            }
            for (int i = 0; i < 4; i++) {
                if (toaDoHopLe(nutCoGiaTriTotNhat.x + row[i], nutCoGiaTriTotNhat.y + col[i]) > 0) {
                    Nut nutCon = newNut(
                            nutCoGiaTriTotNhat.maTran,
                            nutCoGiaTriTotNhat.x,
                            nutCoGiaTriTotNhat.y,
                            nutCoGiaTriTotNhat.x + row[i],
                            nutCoGiaTriTotNhat.y + col[i],
                            nutCoGiaTriTotNhat.soLanDiChuyen + 1,
                            nutCoGiaTriTotNhat
                    );
                    if (!kiemTraTrung(nutCon, nutCoGiaTriTotNhat)) { // Kiểm tra không trùng với nút cha
                        nutCon.soOSai = tinhSoOSai(nutCon.maTran, maTranDich);
                        hangDoiUuTien.add(nutCon);
                    }
                }
            }
        }
    }

    // Hàm kiểm tra tính giải được của trạng thái ban đầu
    static boolean kiemTraGiaiDuoc(int[][] puzzle)
    {
        // Count inversions in given puzzle
        int[] arr = new int[N * N];
        int k = 0;
        for (int i = 0; i < N; i++)
            for (int j = 0; j < N; j++)
                arr[k++] = puzzle[i][j];

        int dem = soLanDaoNguoc(arr);

        if (N % 2 == 1)
            return dem % 2 == 0;
        else
        {
            int pos = timViTriX(puzzle);
            if (pos % 2 == 1)
                return dem % 2 == 0;
            else
                return dem % 2 == 1;
        }
    }

    static int soLanDaoNguoc(int[] arr)
    {
        int dem = 0;
        for (int i = 0; i < N * N - 1; i++) {
            for (int j = i + 1; j < N * N; j++) {
                if (arr[j] != 0 && arr[i] != 0
                        && arr[i] > arr[j])
                    dem++;
            }
        }
        return dem;
    }

    static int timViTriX(int[][] puzzle)
    {
        for (int i = N - 1; i >= 0; i--)
            for (int j = N - 1; j >= 0; j--)
                if (puzzle[i][j] == 0)
                    return N - i;
        return -1;
    }

    public static void main (String[] args)
    {
        int[][] maTranNguon =
            {
                    {1, 2, 3},
                    {5, 6, 0},
                    {7, 8, 4}
            };

        int[][] maTranDich =
            {
                    {1, 2, 3},
                    {5, 8, 6},
                    {0, 7, 4}
            };

        int x = 1, y = 2;

        if (kiemTraGiaiDuoc(maTranNguon)) {
            // Ghi lại thời điểm bắt đầu thực thi
            long startTime = System.currentTimeMillis();

            System.out.println("Bạn chờ chút nha có thể sẽ mất chút thời gian...😁😁😁");
            thuatToanBranchAndBound(maTranNguon, x, y, maTranDich);

            // Ghi lại thời điểm kết thúc thực thi
            long endTime = System.currentTimeMillis();

            // Tính thời gian giải bài toán
            long executionTime = endTime - startTime;
            System.out.println("Thời gian giải bài toán: " + executionTime + " ms");
        } else {
            System.out.println("Không giải được");
        }
    }
}


