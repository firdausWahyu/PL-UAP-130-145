package gui.listdata;

import gui.dashboard.*;
import gui.inputpage.*;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.io.*;

public class ListPage {
    JFrame frame;
    JTable table;
    DefaultTableModel model;

    public ListPage() {
        // Main Frame
        frame = new JFrame("List Data");
        frame.setSize(600, 400);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setLocationRelativeTo(null);

        frame.setLayout(new BoxLayout(frame.getContentPane(), BoxLayout.Y_AXIS));

        createTable();
        loadData();

        //Header
        JPanel panelHeader = new JPanel();
        panelHeader.setLayout(new BorderLayout());
        panelHeader.setMaximumSize( new Dimension(Integer.MAX_VALUE, 60));
        JLabel lblTitle = new JLabel("Tabel Jadwal Mata Kuliah");
        lblTitle.setFont(new Font("Arial", Font.BOLD, 18));
        lblTitle.setBorder(BorderFactory.createEmptyBorder(15, 15, 5, 0));
        panelHeader.add(lblTitle, BorderLayout.WEST);

        //Layout panel Tabel
        JPanel panelTabel = new JPanel(new BorderLayout());
        panelTabel.setBorder(BorderFactory.createEmptyBorder(0, 15,15,15));
        panelTabel.add(new JScrollPane(table), BorderLayout.CENTER);
        panelTabel.setPreferredSize(new Dimension(550, 250));
        panelTabel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 250));
        panelTabel.setMinimumSize(new Dimension(550, 250));

        //Button
        JButton btnTambah = new JButton("Tambah");
        JButton btnRefresh = new JButton("Refresh");
        JButton btnEdit = new JButton("Edit");
        JButton btnHapus  = new JButton("Hapus");
        JButton btnKembali  = new JButton("Kembali");

        buttonColor(btnTambah, new Color(25, 135, 84)); // hijau
        buttonColor(btnRefresh, new Color(13, 110, 253)); // biru
        buttonColor(btnEdit, new Color(255, 193, 7)); // kuning
        buttonColor(btnHapus, new Color(220, 53, 69)); // merah
        buttonColor(btnKembali, new Color(108, 117, 125)); // abu

        //Search
        JTextField tfSearch = new JTextField(20);
        JPanel panelSearch = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelSearch.setBorder(BorderFactory.createEmptyBorder(0,15,0,15));
        panelSearch.add(new JLabel("Cari:"));
        panelSearch.add(tfSearch);

        //Sort Table
        TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>(model);
        table.setRowSorter(sorter);

        tfSearch.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                search();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                search();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                search();
            }

            private void search() {
                String text = tfSearch.getText();

                if (text.trim().length() == 0) {
                    sorter.setRowFilter(null);
                } else {
                    sorter.setRowFilter(RowFilter.regexFilter("(?i)" + text));
                }
            }
        });


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
        panelButton.setBorder(BorderFactory.createEmptyBorder(0,15,0,15));

        frame.add(panelHeader);
        frame.add(panelSearch);
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
            int viewRow = table.getSelectedRow();

            if (viewRow == -1) {
                JOptionPane.showMessageDialog(frame, "Pilih data terlebih dahulu!");
                return;
            }

            int row = table.convertRowIndexToModel(viewRow);

            String[] data = {
                    model.getValueAt(row, 0).toString(),
                    model.getValueAt(row, 1).toString(),
                    model.getValueAt(row, 2).toString(),
                    // jam dipecah lagi
                    model.getValueAt(row, 3).toString().split(" - ")[0],
                    model.getValueAt(row, 3).toString().split(" - ")[1],
                    model.getValueAt(row, 4).toString(),
                    model.getValueAt(row, 5).toString()
            };

            new UpdateData(data, row);
        });


        frame.setVisible(true);
    }

    private void createTable(){
        String[] kolom = {"Kode", "Nama Mata Kuliah", "Hari", "Jam", "Ruangan", "Dosen"};
        model = new DefaultTableModel(kolom, 0);
        table = new JTable(model);

        table.getTableHeader().setBackground(new Color(33, 37, 41)); // hitam keabu
        table.getTableHeader().setForeground(Color.WHITE);
        table.getTableHeader().setFont(new Font("Arial", Font.BOLD, 12));
        table.getTableHeader().setOpaque(false);

    }

    private void loadData() {
        File file = new File("src/data/data.csv");
        model.setRowCount(0);

        if (!file.exists()) {
            return;
        }

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            boolean isHeader = true;

            while ((line = br.readLine()) != null) {

                // lewati header CSV
                if (isHeader) {
                    isHeader = false;
                    continue;
                }

                if (line.trim().isEmpty()) continue;

                String[] data = line.split(";");

                String jamGabung = data[3] + " - " + data[4];

                model.addRow(new Object[]{
                        data[0],   // kode
                        data[1],   // nama
                        data[2],   // hari
                        jamGabung, // jam mulai - jam selesai
                        data[5],    // ruangan
                        data[6]    // Dosen
                });
            }

        } catch (IOException e) {
            JOptionPane.showMessageDialog(frame, "Gagal membaca data CSV!");
            e.printStackTrace();
        }
    }


    private void refreshTable() {
        loadData();
    }

    private void hapusData() {
        int viewRow = table.getSelectedRow();
        if (viewRow == -1) {
            JOptionPane.showMessageDialog(frame, "Pilih data yang ingin dihapus!");
            return;
        }

        int selectedRow = table.convertRowIndexToModel(viewRow);

        int konfirmasi = JOptionPane.showConfirmDialog(frame,
                "Yakin ingin menghapus data ini?",
                "Konfirmasi Hapus",
                JOptionPane.YES_NO_OPTION
        );

        if (konfirmasi != JOptionPane.YES_OPTION) return;

        File file = new File("src/data/data.csv");
        File tempFile = new File("src/data/temp.csv");

        try (
                BufferedReader br = new BufferedReader(new FileReader(file));
                FileWriter fw = new FileWriter(tempFile)
        ) {
            String line;
            int index = -1; // karena header

            while ((line = br.readLine()) != null) {
                if (index == -1) {
                    fw.write(line + System.lineSeparator()); // header
                    index++;
                    continue;
                }

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

        file.delete();
        tempFile.renameTo(file);

        JOptionPane.showMessageDialog(frame, "Data berhasil dihapus!");
        refreshTable();
    }


    private void buttonColor(JButton btn, Color bg) {
        btn.setBackground(bg);
        btn.setForeground(Color.WHITE);
        btn.setFocusPainted(false);
        btn.setBorder(BorderFactory.createEmptyBorder(8, 16, 8, 16));
    }

}
