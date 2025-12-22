package gui.history;

import javax.swing.*;

public class History {
    JFrame frame;

    public History() {
        frame = new JFrame("Screen 4 (Dummy)");
        frame.setSize(400, 300);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);



        frame.add(new JLabel("Holla, 4", SwingConstants.CENTER));
        frame.setVisible(true);
    }

    private int urutanHari(String hari) {
        switch (hari.toLowerCase()) {
            case "senin": return 1;
            case "selasa": return 2;
            case "rabu": return 3;
            case "kamis": return 4;
            case "jumat": return 5;
            case "sabtu": return 6;
            case "minggu": return 7;
            default: return 99;
        }
    }

    class Jadwal {
        String kode;
        String nama;
        String hari;
        String jamMulai;
        String jamSelesai;
        String ruangan;
        String dosen;

        public Jadwal(String kode, String nama, String hari,
                      String jamMulai, String jamSelesai,
                      String ruangan, String dosen) {
            this.kode = kode;
            this.nama = nama;
            this.hari = hari;
            this.jamMulai = jamMulai;
            this.jamSelesai = jamSelesai;
            this.ruangan = ruangan;
            this.dosen = dosen;
        }


    }


}
