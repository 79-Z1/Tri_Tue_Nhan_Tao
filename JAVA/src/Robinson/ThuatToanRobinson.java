package Robinson;

import java.util.ArrayList;
import java.util.List;

public class ThuatToanRobinson {

    static List<String> danhSachGiaThiet = new ArrayList<>();
    static List<String> danhSachKetLuan = new ArrayList<>();

    public static void main(String[] args) {
        String bieuThuc = "(xvz)^(yvz)>xvyvz";
        System.out.println("++++++++++++++++++ Thực hiện thuật toán Robinson ++++++++++++++++++");
        System.out.println("Biểu thức: " + bieuThuc);
        System.out.println("\n--- Kết quả ---\n");

        ChuanHoa(bieuThuc);
        System.out.println();
        ThucHienThuatToanRobinson();

        System.out.println("\nDanh sách các giả thiết còn lại:");
        for (String str : danhSachGiaThiet) {
            System.out.print(str + "\t");
        }
        System.out.println();

        System.out.println("Danh sách các giả thiết đối ngẫu:");
        for (String str : danhSachKetLuan) {
            System.out.print(str + "\t");
        }
        System.out.println();
    }

    static boolean KiemTraTrongHop(String str1, String str2) {
        int size_str1 = str1.length();
        int size_str2 = str2.length();
        if (size_str1 > size_str2) {
            str1 = str1.substring(1);
            return str1.equals(str2);
        } else if (size_str1 < size_str2) {
            str2 = str2.substring(1);
            return str2.equals(str1);
        }
        return false;
    }

    static boolean KiemTraTrongDanhSachKetLuan() {
        List<String> danhSachTam = new ArrayList<>(danhSachKetLuan);
        for (String s1 : danhSachKetLuan) {
            danhSachTam.remove(0);
            for (String s2 : danhSachTam) {
                if (KiemTraTrongHop(s1, s2)) {
                    return true;
                }
            }
        }
        return false;
    }

    static String GiaiQuyetQuyTac3(String str1, String str2) {
        String s = str1;
        String s1 = s.substring(1, s.indexOf("v"));
        s = s.substring(s.indexOf("v") + 1);
        String s2 = s.substring(0, s.indexOf(")"));
        if (KiemTraTrongHop(s1, str2)) {
            return s2;
        }
        if (KiemTraTrongHop(s2, str2)) {
            return s1;
        }
        return str1;
    }

    static String GiaiQuyetQuyTac4(String str1, String str2) {
        if (str1.length() < 4 || str2.length() < 4) {
            return "Không hợp lệ!!!";
        }

        String s1 = str1;
        int indexV1 = s1.indexOf("v");
        if (indexV1 == -1) {
            return "Không hợp lệ!!!";
        }
        String s1_1 = s1.substring(1, indexV1);
        s1 = s1.substring(indexV1 + 1);
        String s1_2 = s1.substring(0, s1.indexOf(")"));

        String s2 = str2;
        int indexV2 = s2.indexOf("v");
        if (indexV2 == -1) {
            return "Không hợp lệ!!!";
        }
        String s2_1 = s2.substring(1, indexV2);
        s2 = s2.substring(indexV2 + 1);
        String s2_2 = s2.substring(0, s2.indexOf(")"));

        if (KiemTraTrongHop(s1_1, s2_1)) {
            return "(" + s1_2 + " v " + s2_2 + ")";
        }
        if (KiemTraTrongHop(s1_1, s2_2)) {
            return "(" + s1_2 + " v " + s2_1 + ")";
        }
        if (KiemTraTrongHop(s1_2, s2_1)) {
            return "(" + s1_1 + " v " + s2_2 + ")";
        }
        if (KiemTraTrongHop(s1_2, s2_2)) {
            return "(" + s1_1 + " v " + s2_1 + ")";
        }
        return "Không hợp lệ!!!";
    }


    static void ChuanHoa(String bieuThuc) {
        try {
            String[] phan = bieuThuc.split(">");
            if (phan.length != 2) {
                System.out.println("Định dạng đầu vào không hợp lệ.");
                return;
            }

            String giaThiet = phan[0].trim();
            System.out.println("Giả thiết: " + giaThiet);

            danhSachGiaThiet = new ArrayList<>(List.of(giaThiet.split("\\^")));

            String ketLuan = phan[1].trim();
            System.out.println("Kết luận: " + ketLuan);

            danhSachKetLuan = new ArrayList<>(List.of(ketLuan.split("v")));

            for (int i = 0; i < danhSachKetLuan.size(); i++) {
                if (danhSachKetLuan.get(i).startsWith("-")) {
                    danhSachKetLuan.set(i, danhSachKetLuan.get(i).substring(1));
                } else {
                    danhSachKetLuan.set(i, "-" + danhSachKetLuan.get(i));
                }
            }

            // In ra danh sách
            System.out.println("Danh sách các mệnh đề");
            System.out.print("{");
            for (String s : danhSachGiaThiet) {
                System.out.print(s.toString() + ", ");
            }
            for (int i = 0; i < danhSachKetLuan.size(); i++) {
                if (i == danhSachKetLuan.size() - 1) {
                    System.out.print(danhSachKetLuan.get(i).toString());
                } else {
                    System.out.print(danhSachKetLuan.get(i).toString() + ", ");
                }
            }
            System.out.print("}");
        } catch (Exception e) {
            System.out.println("Lỗi: " + e.getMessage());
        }
    }

    static void ThucHienThuatToanRobinson() {
        while (!KiemTraTrongDanhSachKetLuan()) {
            String giaThiet1 = danhSachGiaThiet.get(0);
            String ketLuan1 = danhSachKetLuan.get(0);
            String ketQua3 = GiaiQuyetQuyTac3(giaThiet1, ketLuan1);
            String ketQua4 = GiaiQuyetQuyTac4(giaThiet1, ketLuan1);

            System.out.println(giaThiet1 + " ^ " + ketLuan1 + " -> " + ketQua3);
            System.out.println(giaThiet1 + " ^ " + ketLuan1 + " -> " + ketQua4);

            if (ketQua3.length() <= 2) {
                danhSachGiaThiet.remove(0);
                danhSachKetLuan.remove(0);
                danhSachKetLuan.add(ketQua3);
            } else {
                danhSachGiaThiet.add(danhSachGiaThiet.get(0));
                danhSachGiaThiet.remove(0);
            }

            if (ketQua4.length() <= 2) {
                danhSachGiaThiet.remove(0);
                danhSachKetLuan.remove(0);
                danhSachKetLuan.add(ketQua4);
            } else {
                danhSachGiaThiet.add(danhSachGiaThiet.get(0));
                danhSachGiaThiet.remove(0);
            }
        }
    }
}
