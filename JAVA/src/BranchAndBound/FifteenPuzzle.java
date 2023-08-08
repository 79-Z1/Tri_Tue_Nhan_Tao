package BranchAndBound;

import java.util.*;

public class FifteenPuzzle {
    public static int N = 4; // Đổi N thành 4 cho 15-Puzzle

    public static class Node {
        Node nutCha;
        int maTran[][] = new int[N][N];
        int x, y;
        int soOSai;
        int soLanDiChuyen;
    }

    public static void inMaTran(int maTran[][]) {
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if (maTran[i][j] == 0) {
                    System.out.print("▢ ");
                } else {
                    System.out.print(maTran[i][j] + " ");
                }
            }
            System.out.println();
        }
    }

    public static Node newNode(
            int maTran[][], int x, int y,
            int newX, int newY,
            int soLanDiChuyen, Node nutCha
    ) {
        Node nut = new Node();
        nut.nutCha = nutCha;
        nut.maTran = new int[N][N];
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                nut.maTran[i][j] = maTran[i][j];
            }
        }

        int temp = nut.maTran[x][y];
        nut.maTran[x][y] = nut.maTran[newX][newY];
        nut.maTran[newX][newY] = temp;

        nut.soOSai = Integer.MAX_VALUE;
        nut.soLanDiChuyen = soLanDiChuyen;

        nut.x = newX;
        nut.y = newY;

        return nut;
    }

    // dưới, trái, trên, phải
    public static int row[] = { 1, 0, -1, 0 };
    public static int col[] = { 0, -1, 0, 1 };

    // Function to calculate the number of misplaced tiles
    public static int tinhSoOSai(int maTranNguon[][], int maTranDich[][]) {
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

    public static boolean toaDoHopLe(int x, int y) {
        return (x >= 0 && x < N && y >= 0 && y < N);
    }

    // Print path from root node to destination node
    public static void inDuongDi(Node root) {
        if (root == null) {
            return;
        }
        inDuongDi(root.nutCha);
        inMaTran(root.maTran);
        System.out.println();
    }

    // Comparison object to be used to order the heap
    public static class comp implements Comparator<Node> {
        @Override
        public int compare(Node lhs, Node rhs) {
            return Integer.compare(lhs.soOSai + lhs.soLanDiChuyen, rhs.soOSai + rhs.soLanDiChuyen);
        }
    }

    public static boolean kiemTraTrung(Node nut, Node nutCha) {
        while (nutCha != null) {
            if (Arrays.deepEquals(nut.maTran, nutCha.maTran)) {
                return true;
            }
            nutCha = nutCha.nutCha;
        }
        return false;
    }

    public static void thuatToanBranchAndBound(int maTranNguon[][], int x, int y, int maTranDich[][]) {
        PriorityQueue<Node> hangDoiUuTien = new PriorityQueue<>(new comp());

        Node nutGoc = newNode(maTranNguon, x, y, x, y, 0, null);
        nutGoc.soOSai = tinhSoOSai(maTranNguon, maTranDich);
        hangDoiUuTien.add(nutGoc);

        while (!hangDoiUuTien.isEmpty()) {
            Node nutCoGiaTriTotNhat = hangDoiUuTien.peek();
            hangDoiUuTien.poll();

            if (nutCoGiaTriTotNhat.soOSai == 0) {
                inDuongDi(nutCoGiaTriTotNhat);
                return;
            }
            for (int i = 0; i < 4; i++) {
                if (toaDoHopLe(nutCoGiaTriTotNhat.x + row[i], nutCoGiaTriTotNhat.y + col[i])) {
                    Node nutCon = newNode(
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

    public static void main(String[] args) {
        int[][] maTranNguon = {
                {1, 2, 3, 4},
                {5, 6, 10, 8},
                {0, 9, 14, 11},
                {15, 7, 12, 13}
        };

        int[][] maTranDich = {
                {1, 2, 3, 4},
                {5, 6, 7, 8},
                {9, 10, 11, 12},
                {13, 14, 15, 0},
        };

        int x = 2, y = 0;

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
