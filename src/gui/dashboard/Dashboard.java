package gui.dashboard;

import gui.history.History;
import gui.listdata.ListPage;
import helper.UIHelper;

import javax.swing.*;
import java.awt.*;

public class Dashboard {

    JFrame frame;

    public Dashboard() {
        initFrame();
        initComponents();
    }

    private void initFrame() {
        frame = new JFrame("Dashboard");
        frame.setSize(500, 320);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());
    }

    private void initComponents(){
        //Title
        JLabel lblTitle = new JLabel("Sistem Jadwal Mata Kuliah");
        UIHelper.labelTitle(lblTitle, 20, SwingConstants.CENTER, 20,20,20,20);

        // button
        JButton btnData = new JButton("Lihat Data");
        JButton btnJadwal  = new JButton("Lihat Jadwal");
        UIHelper.styleButton(btnData, new Color(13,110,253), 15);
        UIHelper.styleButton(btnJadwal, new Color(25, 135, 84), 15);

        //Layout
        btnData.setPreferredSize(new Dimension(150, 40));
        btnJadwal.setPreferredSize(new Dimension(150, 40));

        JPanel panelHeader = new JPanel(new BorderLayout());
        panelHeader.add(lblTitle, BorderLayout.CENTER);

        JPanel panelButton = new JPanel();
        panelButton.setLayout(new GridLayout(2, 1, 0, 20));
        panelButton.setBorder(BorderFactory.createEmptyBorder(30, 80, 30, 80));
        panelButton.add(btnData);
        panelButton.add(btnJadwal);

        // action
        btnData.addActionListener(e -> {frame.dispose(); new ListPage(); });
        btnJadwal.addActionListener(e -> {frame.dispose();new History();});
        frame.add(panelHeader, BorderLayout.NORTH);
        frame.add(panelButton, BorderLayout.CENTER);

        frame.setVisible(true);
    }
}
