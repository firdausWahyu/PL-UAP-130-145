package gui.history;

import gui.dashboard.Dashboard;
import helper.UIHelper;
import model.Jadwal;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.io.*;
import java.util.*;
import java.util.List;

public class History {

    private JFrame frame;
    private List<Jadwal> listJadwal = new ArrayList<>();

    public History() {
        initFrame();
        loadData();
        buildUI();
        frame.setVisible(true);
    }

    // Frame
    private void initFrame() {
        frame = new JFrame("Jadwal Mata Kuliah");
        frame.setSize(520, 500);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setLayout(new BorderLayout());
    }

    // UI
    private void buildUI() {

        JLabel title = new JLabel("Jadwal Mata Kuliah");
        UIHelper.labelTitle(title, 18, SwingConstants.CENTER, 15,10,15,10);

        JButton btnBack = new JButton("Kembali");
        UIHelper.styleButton(btnBack, Color.GRAY, 13);
        Dimension btnSize = new Dimension(120, 35);
        btnBack.setPreferredSize(btnSize);

        JPanel listPanel = new JPanel();
        listPanel.setLayout(new BoxLayout(listPanel, BoxLayout.Y_AXIS));
        listPanel.setBorder(new EmptyBorder(10, 10, 10, 10));

        for (Jadwal j : listJadwal) {
            listPanel.add(createCard(j));
            listPanel.add(Box.createVerticalStrut(10));
        }

        JScrollPane scrollPane = new JScrollPane(listPanel);
        scrollPane.setBorder(null);

        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        bottomPanel.setBorder(new EmptyBorder(5, 10, 10, 10));
        bottomPanel.add(btnBack);

        frame.add(title, BorderLayout.NORTH);
        frame.add(scrollPane, BorderLayout.CENTER);
        frame.add(bottomPanel, BorderLayout.SOUTH);

        btnBack.addActionListener(e -> {
            frame.dispose();
            new Dashboard();
        });
    }

    // Card
    private JPanel createCard(Jadwal j) {
        JPanel card = new JPanel();
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBackground(Color.WHITE);
        card.setBorder(new CompoundBorder(
                new LineBorder(Color.LIGHT_GRAY),
                new EmptyBorder(10, 10, 10, 10)
        ));

        JLabel lblHariJam = new JLabel(
                j.getHari() + " | " + j.getJamMulai() + " - " + j.getJamSelesai()
        );
        lblHariJam.setFont(new Font("Segoe UI", Font.PLAIN, 12));

        JLabel lblNama = new JLabel(
                j.getNama()
        );
        lblNama.setFont(new Font("Segoe UI", Font.BOLD, 13));

        JLabel lblInfo = new JLabel(
                j.getRuangan() + " | " + j.getDosen()
        );
        lblInfo.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        lblInfo.setForeground(Color.DARK_GRAY);

        card.add(lblNama);
        card.add(Box.createVerticalStrut(5));
        card.add(lblHariJam);
        card.add(Box.createVerticalStrut(5));
        card.add(lblInfo);

        return card;
    }

    // Data
    private void loadData() {
        readCSV();
        sortJadwal();
    }

    private void readCSV() {
        File file = new File("src/data/data.csv");
        if (!file.exists()) return;

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;

            while ((line = br.readLine()) != null) {
                if (line.trim().isEmpty()) continue;

                String[] d = line.split(";");
                if (d[0].equalsIgnoreCase("kode") || d.length < 7) continue;

                listJadwal.add(new Jadwal(
                        d[0], d[1], d[2],
                        d[3], d[4], d[5], d[6]
                ));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void sortJadwal() {
        listJadwal.sort((a, b) -> {
            int h1 = urutanHari(a.getHari());
            int h2 = urutanHari(b.getHari());
            return h1 != h2 ? h1 - h2
                    : a.getJamMulai().compareTo(b.getJamMulai());
        });
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
}
