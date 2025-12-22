package gui.dashboard;

import gui.listdata.ListPage;

import javax.swing.*;
import java.awt.*;

public class Dashboard {

    JFrame frame;

    public Dashboard() {
        frame = new JFrame("Dashboard");
        frame.setSize(500, 320);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        JLabel lblTitle = new JLabel("Sistem Jadwal Mata Kuliah");
        lblTitle.setFont(new Font("Arial", Font.BOLD, 20));
        lblTitle.setHorizontalAlignment(SwingConstants.CENTER);
        lblTitle.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JPanel panelHeader = new JPanel(new BorderLayout());
        panelHeader.add(lblTitle, BorderLayout.CENTER);

        // ===== BUTTON (ATAS BAWAH) =====
        JButton btnData = new JButton("Lihat Data");
        JButton btnJadwal  = new JButton("Lihat Jadwal");

        styleButton(btnData, new Color(13, 110, 253));
        styleButton(btnJadwal, new Color(25, 135, 84));

        btnData.setPreferredSize(new Dimension(150, 40));
        btnJadwal.setPreferredSize(new Dimension(150, 40));

        JPanel panelButton = new JPanel();
        panelButton.setLayout(new GridLayout(2, 1, 0, 20));
        panelButton.setBorder(BorderFactory.createEmptyBorder(30, 80, 30, 80));
        panelButton.add(btnData);
        panelButton.add(btnJadwal);

        // action
        btnData.addActionListener(e -> {
            new ListPage();
        });

        btnJadwal.addActionListener(e -> {
            JOptionPane.showMessageDialog(frame, "Menu List");
        });
        frame.add(panelHeader, BorderLayout.NORTH);
        frame.add(panelButton, BorderLayout.CENTER);

        frame.setVisible(true);
    }

    private void styleButton(JButton btn, Color bg) {
        btn.setBackground(bg);
        btn.setForeground(Color.WHITE);
        btn.setFont(new Font("Arial", Font.BOLD, 15));
        btn.setFocusPainted(false);
        btn.setBorder(BorderFactory.createEmptyBorder(15, 30, 15, 30));
    }
}
