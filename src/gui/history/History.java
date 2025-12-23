package gui.history;

import gui.dashboard.Dashboard;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.io.*;
import java.util.*;
import java.util.List;

public class History {
    JFrame frame;
    List<Jadwal> listJadwal = new ArrayList<>();

    public History() {
        frame = new JFrame("Jadwal Mata Kuliah");
        frame.setSize(520, 500);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        bacaCSV();
        sortJadwal();

        // header
        JLabel title = new JLabel("Jadwal Mata Kuliah", JLabel.CENTER);
        title.setFont(new Font("Segoe UI", Font.BOLD, 18));
        title.setBorder(new EmptyBorder(15, 10, 15, 10));
        frame.add(title, BorderLayout.NORTH);

        // btn
        JButton btnBack = new JButton("Kembali");
        styleButton(btnBack, Color.GRAY);
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        bottomPanel.setBorder(new EmptyBorder(5, 10, 10, 10));
        bottomPanel.add(btnBack);
        frame.add(bottomPanel, BorderLayout.SOUTH);


        // panel
        JPanel listPanel = new JPanel();
        listPanel.setLayout(new BoxLayout(listPanel, BoxLayout.Y_AXIS));
        listPanel.setBorder(new EmptyBorder(10, 10, 10, 10));

        // perulangan
        for (Jadwal j : listJadwal) {
            listPanel.revalidate();
            listPanel.repaint();
            listPanel.add(createCard(j));
            listPanel.add(Box.createVerticalStrut(10));
        }

        JScrollPane scrollPane = new JScrollPane(listPanel);
        scrollPane.setBorder(null);
        frame.add(scrollPane, BorderLayout.CENTER);

        btnBack.addActionListener(e -> {
            frame.dispose();
            new Dashboard();
        });


        frame.setVisible(true);
    }

    private JPanel createCard(Jadwal j) {
        JPanel card = new JPanel();
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBorder(new CompoundBorder(
                new LineBorder(Color.LIGHT_GRAY, 1),
                new EmptyBorder(10, 10, 10, 10)
        ));
        card.setBackground(Color.WHITE);

        JLabel lblHariJam = new JLabel(
                j.hari + " | " + j.jamMulai + " - " + j.jamSelesai
        );
        lblHariJam.setFont(new Font("Segoe UI", Font.BOLD, 13));

        JLabel lblNama = new JLabel(j.nama);
        lblNama.setFont(new Font("Segoe UI", Font.PLAIN, 13));

        JLabel lblInfo = new JLabel(j.ruangan + " | " + j.dosen);
        lblInfo.setFont(new Font("Segoe UI", Font.ITALIC, 12));
        lblInfo.setForeground(Color.DARK_GRAY);

        card.add(lblHariJam);
        card.add(Box.createVerticalStrut(5));
        card.add(lblNama);
        card.add(Box.createVerticalStrut(5));
        card.add(lblInfo);

        return card;
    }

    // CSV
    private void bacaCSV() {
        File file = new File("src/data/data.csv");
        if (!file.exists()) return;

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;

            while ((line = br.readLine()) != null) {
                if (line.trim().isEmpty()) continue;

                String[] data = line.split(";");
                if (data[0].equalsIgnoreCase("kode")) continue;
                if (data.length < 7) continue;

                listJadwal.add(new Jadwal(
                        data[0], data[1], data[2],
                        data[3], data[4], data[5], data[6]
                ));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
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
    // sorting
    private void sortJadwal() {
        Collections.sort(listJadwal, (a, b) -> {
            int hariA = urutanHari(a.hari);
            int hariB = urutanHari(b.hari);

            if (hariA != hariB) {
                return hariA - hariB;
            }
            return a.jamMulai.compareTo(b.jamMulai);
        });
    }

    // data
    class Jadwal {
        String kode, nama, hari, jamMulai, jamSelesai, ruangan, dosen;

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

    private void styleButton(JButton btn, Color bg) {
        btn.setBackground(bg);
        btn.setForeground(Color.WHITE);
        btn.setFont(new Font("Arial", Font.BOLD, 15));
        btn.setFocusPainted(false);
        btn.setBorder(BorderFactory.createEmptyBorder(15, 30, 15, 30));
    }
}
