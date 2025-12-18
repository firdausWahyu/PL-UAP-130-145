package gui.listdata;

import gui.inputpage.CreateData;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class ListPage {
    JFrame frame;
    JTable table;
    DefaultTableModel model;

    public ListPage() {
        //Frame
        frame = new JFrame("List Data");
        frame.setSize(600, 400);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setLocationRelativeTo(null);

        frame.setLayout(new BoxLayout(frame.getContentPane(), BoxLayout.Y_AXIS));

        createTable();
        loadData();

        JPanel panelTabel = new JPanel(new BorderLayout());
        panelTabel.add(new JScrollPane(table), BorderLayout.CENTER);
        panelTabel.setPreferredSize(new Dimension(550, 250));
        panelTabel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 250));
        panelTabel.setMinimumSize(new Dimension(550, 250));

        JButton btnTambah = new JButton("Tambah");
        JButton btnRefresh = new JButton("Refresh");
        JButton btnEdit = new JButton("Edit");
        JButton btnHapus  = new JButton("Hapus");

        JPanel panelButton = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelButton.add(btnTambah);
        panelButton.add(btnRefresh);
        panelButton.add(btnEdit);
        panelButton.add(btnHapus);
        panelButton.setMaximumSize(
                new Dimension(Integer.MAX_VALUE, panelButton.getPreferredSize().height)
        );

        frame.add(panelTabel);
        frame.add(panelButton);

        btnTambah.addActionListener(e -> new CreateData());
        btnRefresh.addActionListener(e -> refreshTable());

        frame.setVisible(true);
    }

    private void createTable(){
        String[] kolom = {"Kode", "Nama Mata Kuliah", "Hari", "Jam", "Ruangan"};
        model = new DefaultTableModel(kolom, 0);
        table = new JTable(model);
    }

    private void loadData() {
        File file = new File("src/gui/data/data.txt");
        model.setRowCount(0);

        if (!file.exists()) {
            return;
        }

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;

            while ((line = br.readLine()) != null) {
                String[] data = line.split("\\|");
                String jamGabung = data[3] + " - " + data[4];

                model.addRow(new Object[]{
                        data[0],   // kode
                        data[1],   // nama
                        data[2],   // hari
                        jamGabung, // jam mulai - jam selesai
                        data[5]    // ruangan
                });
            }


        } catch (IOException e) {
            JOptionPane.showMessageDialog(frame, "Gagal membaca data!");
            e.printStackTrace();
        }
    }

    private void refreshTable() {
        loadData();
    }


}
