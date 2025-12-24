package gui.listdata;

import gui.dashboard.Dashboard;
import gui.inputpage.CreateData;
import gui.inputpage.UpdateData;
import helper.UIHelper;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.io.*;

public class ListPage {

    private JFrame frame;
    private JTable table;
    private DefaultTableModel model;
    private TableRowSorter<DefaultTableModel> sorter;

    public ListPage() {
        initFrame();
        initTable();
        initHeader();
        initSearch();
        initContent();
        initButton();
        loadData();

        frame.setVisible(true);
    }

    private void initFrame() {
        frame = new JFrame("List Data");
        frame.setSize(600, 400);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setLayout(new BoxLayout(frame.getContentPane(), BoxLayout.Y_AXIS));
    }

    private void initTable() {
        String[] kolom = {"Kode", "Nama Mata Kuliah", "Hari", "Jam", "Ruangan", "Dosen"};
        model = new DefaultTableModel(kolom, 0);
        table = new JTable(model);

        table.getTableHeader().setBackground(new Color(33, 37, 41));
        table.getTableHeader().setForeground(Color.WHITE);
        table.getTableHeader().setFont(new Font("Arial", Font.BOLD, 12));

        sorter = new TableRowSorter<>(model);
        table.setRowSorter(sorter);
    }

    private void initHeader() {
        JLabel lblTitle = new JLabel("Tabel Jadwal Mata Kuliah");
        UIHelper.labelTitle(lblTitle, 18, SwingConstants.LEFT, 15, 15, 5, 0);

        JPanel panelHeader = new JPanel(new BorderLayout());
        panelHeader.setMaximumSize(new Dimension(Integer.MAX_VALUE, 60));
        panelHeader.add(lblTitle, BorderLayout.WEST);

        frame.add(panelHeader);
    }

    private void initSearch() {
        JTextField tfSearch = new JTextField(20);

        tfSearch.getDocument().addDocumentListener(new DocumentListener() {
            public void insertUpdate(DocumentEvent e) { search(tfSearch); }
            public void removeUpdate(DocumentEvent e) { search(tfSearch); }
            public void changedUpdate(DocumentEvent e) { search(tfSearch); }
        });

        JPanel panelSearch = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelSearch.setBorder(BorderFactory.createEmptyBorder(0, 15, 0, 15));
        panelSearch.add(new JLabel("Cari:"));
        panelSearch.add(tfSearch);

        frame.add(panelSearch);
    }

    private void initContent() {
        JPanel panelTable = new JPanel(new BorderLayout());
        panelTable.setBorder(BorderFactory.createEmptyBorder(0, 15, 15, 15));
        panelTable.setPreferredSize(new Dimension(550, 250));
        panelTable.setMaximumSize(new Dimension(Integer.MAX_VALUE, 250));

        panelTable.add(new JScrollPane(table), BorderLayout.CENTER);
        frame.add(panelTable);
    }

    private void initButton() {

        JButton btnTambah = new JButton("Tambah");
        JButton btnRefresh = new JButton("Refresh");
        JButton btnEdit = new JButton("Edit");
        JButton btnHapus = new JButton("Hapus");
        JButton btnKembali = new JButton("Kembali");

        UIHelper.styleButton(btnTambah, new Color(25, 135, 84), 12);
        UIHelper.styleButton(btnRefresh, new Color(13, 110, 253), 12);
        UIHelper.styleButton(btnEdit, new Color(255, 193, 7), 12);
        UIHelper.styleButton(btnHapus, new Color(220, 53, 69), 12);
        UIHelper.styleButton(btnKembali, new Color(108, 117, 125), 12);
        Dimension btnSize = new Dimension(80, 35);
        btnTambah.setPreferredSize(btnSize);
        btnRefresh.setPreferredSize(btnSize);
        btnEdit.setPreferredSize(btnSize);
        btnHapus.setPreferredSize(btnSize);
        btnKembali.setPreferredSize(btnSize);

        btnTambah.addActionListener(e -> new CreateData(model));
        btnRefresh.addActionListener(e -> refreshTable());
        btnHapus.addActionListener(e -> hapusData());
        btnKembali.addActionListener(e -> {
            frame.dispose();
            new Dashboard();
        });

        btnEdit.addActionListener(e -> editData());

        JPanel panelLeft = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelLeft.add(btnTambah);
        panelLeft.add(btnRefresh);
        panelLeft.add(btnEdit);
        panelLeft.add(btnHapus);

        JPanel panelRight = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        panelRight.add(btnKembali);

        JPanel panelButton = new JPanel(new BorderLayout());
        panelButton.setBorder(BorderFactory.createEmptyBorder(0, 15, 0, 15));
        panelButton.add(panelLeft, BorderLayout.WEST);
        panelButton.add(panelRight, BorderLayout.EAST);

        frame.add(panelButton);
    }

    private void search(JTextField tfSearch) {
        String text = tfSearch.getText().trim();
        sorter.setRowFilter(text.isEmpty() ? null : RowFilter.regexFilter("(?i)" + text));
    }

    private void editData() {
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
                model.getValueAt(row, 3).toString().split(" - ")[0],
                model.getValueAt(row, 3).toString().split(" - ")[1],
                model.getValueAt(row, 4).toString(),
                model.getValueAt(row, 5).toString()
        };

        new UpdateData(data, row, model);
    }

    private void loadData() {
        model.setRowCount(0);
        File file = new File("src/data/data.csv");

        if (!file.exists()) return;

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            boolean header = true;

            while ((line = br.readLine()) != null) {
                if (header) { header = false; continue; }
                if (line.trim().isEmpty()) continue;

                String[] d = line.split(";");
                model.addRow(new Object[]{
                        d[0], d[1], d[2],
                        d[3] + " - " + d[4],
                        d[5], d[6]
                });
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(frame, "Gagal membaca data CSV!");
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

        int row = table.convertRowIndexToModel(viewRow);

        if (JOptionPane.showConfirmDialog(frame,
                "Yakin ingin menghapus data ini?",
                "Konfirmasi",
                JOptionPane.YES_NO_OPTION) != JOptionPane.YES_OPTION) return;

        File file = new File("src/data/data.csv");
        File temp = new File("src/data/temp.csv");

        try (
                BufferedReader br = new BufferedReader(new FileReader(file));
                FileWriter fw = new FileWriter(temp)
        ) {
            String line;
            int index = -1;

            while ((line = br.readLine()) != null) {
                if (index == -1 || index != row) {
                    fw.write(line + System.lineSeparator());
                }
                index++;
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(frame, "Gagal menghapus data!");
            return;
        }

        file.delete();
        temp.renameTo(file);
        refreshTable();
    }
}
