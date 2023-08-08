package DFS;

import java.util.LinkedList;

class TimKiemTheoChieuSau {
    private boolean[] daDuyet;
    private int[] dinhTruoc;
    private int dinhBatDau;

    public TimKiemTheoChieuSau(DoThi dothi, int dinhBatDau) {
        daDuyet = new boolean[dothi.soDinh()];
        dinhTruoc = new int[dothi.soDinh()];
        this.dinhBatDau = dinhBatDau;
        dfs(dothi, dinhBatDau);
    }

    private void dfs(DoThi dothi, int v) {
        daDuyet[v] = true;
        for (Integer i : dothi.danhSachKe(v))
            if (!daDuyet[i]) {
                dinhTruoc[i] = v;
                dfs(dothi, i);
            }
    }

    public boolean coDuongDiDen(int dinhDen) {
        return daDuyet[dinhDen];
    }

    public Iterable<Integer> duongDiDen(int dinhDen) {
        if (!coDuongDiDen(dinhDen))
            return null;
        LinkedList<Integer> duongDi = new LinkedList<>();
        for (int i = dinhDen; i != dinhBatDau; i = dinhTruoc[i])
            duongDi.addFirst(i);
        duongDi.addFirst(dinhBatDau);
        return duongDi;
    }

    public static void main(String[] args) {
        DoThi dothi = new DoThi(6);

        dothi.themCanh(0, 1);
        dothi.themCanh(0, 2);
        dothi.themCanh(0, 3);
        dothi.themCanh(1, 4);
        dothi.themCanh(2, 4);
        dothi.themCanh(2, 5);

        TimKiemTheoChieuSau timDuong = new TimKiemTheoChieuSau(dothi, 0);
        for (int i = 0; i < 6; i++) {
            Iterable<Integer> ketQua = timDuong.duongDiDen(i);
            System.out.println(ketQua);
        }
    }
}

class DoThi {
    private final int SO_DINH;
    private LinkedList<Integer>[] danhSachKe;

    public DoThi(int soDinh) {
        SO_DINH = soDinh;
        danhSachKe = new LinkedList[SO_DINH];
        for (int i = 0; i < SO_DINH; i++)
            danhSachKe[i] = new LinkedList<>();
    }

    public void themCanh(int dinhX, int dinhY) {
        danhSachKe[dinhX].add(dinhY);
        danhSachKe[dinhY].add(dinhX);
    }

    public Iterable<Integer> danhSachKe(int dinh) {
        return danhSachKe[dinh];
    }

    public int soDinh() {
        return SO_DINH;
    }
}
