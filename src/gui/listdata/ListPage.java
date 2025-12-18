package gui.listdata;

import gui.dashboard.*;
import gui.inputpage.*;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.*;

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
        JButton btnKembali  = new JButton("Kembali");

        JPanel panelButton = new JPanel(new BorderLayout());

        JPanel panelLeft = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelLeft.add(btnTambah);
        panelLeft.add(btnRefresh);
        panelLeft.add(btnEdit);
        panelLeft.add(btnHapus);

        JPanel panelRight = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        panelRight.add(btnKembali);

        panelButton.add(panelLeft, BorderLayout.WEST);
        panelButton.add(panelRight, BorderLayout.EAST);

        panelButton.setMaximumSize(
                new Dimension(Integer.MAX_VALUE, panelButton.getPreferredSize().height)
        );

        frame.add(panelTabel);
        frame.add(panelButton);

        btnTambah.addActionListener(e -> new CreateData());
        btnRefresh.addActionListener(e -> refreshTable());
        btnKembali.addActionListener(e -> {
            frame.dispose();
            new Dashboard();
        });
        btnHapus.addActionListener(e -> hapusData());
        btnEdit.addActionListener(e -> {
            int row = table.getSelectedRow();
            if (row == -1) {
                JOptionPane.showMessageDialog(frame, "Pilih data terlebih dahulu!");
                return;
            }

            String[] data = {
                    model.getValueAt(row, 0).toString(),
                    model.getValueAt(row, 1).toString(),
                    model.getValueAt(row, 2).toString(),
                    // jam dipecah lagi
                    model.getValueAt(row, 3).toString().split(" - ")[0],
                    model.getValueAt(row, 3).toString().split(" - ")[1],
                    model.getValueAt(row, 4).toString()
            };

            new UpdateData(data, row);
        });



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

    private void hapusData() {
        int selectedRow = table.getSelectedRow();

        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(frame, "Pilih data yang ingin dihapus!");
            return;
        }

        int konfirmasi = JOptionPane.showConfirmDialog(
                frame,
                "Yakin ingin menghapus data ini?",
                "Konfirmasi Hapus",
                JOptionPane.YES_NO_OPTION
        );

        if (konfirmasi != JOptionPane.YES_OPTION) {
            return;
        }

        File file = new File("src/gui/data/data.txt");
        File tempFile = new File("src/gui/data/temp.txt");

        try (
                BufferedReader br = new BufferedReader(new FileReader(file));
                FileWriter fw = new FileWriter(tempFile)
        ) {
            String line;
            int index = 0;

            while ((line = br.readLine()) != null) {
                if (index != selectedRow) {
                    fw.write(line + System.lineSeparator());
                }
                index++;
            }

        } catch (IOException e) {
            JOptionPane.showMessageDialog(frame, "Gagal menghapus data!");
            e.printStackTrace();
            return;
        }

        // Ganti file lama
        if (file.delete()) {
            tempFile.renameTo(file);
        }

        JOptionPane.showMessageDialog(frame, "Data berhasil dihapus!");
        refreshTable();
    }

}
