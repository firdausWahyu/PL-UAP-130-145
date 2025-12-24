package gui.inputpage;

import helper.FormHelper;
import helper.UIHelper;
import model.JamSlot;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.FileWriter;
import java.io.IOException;

public class CreateData {

    private JFrame frame;
    private JTextField kodeMatkul, namaMatkul, ruangan, dosen;
    private JComboBox<String> cbHari;
    private JComboBox<JamSlot> cbJamMulai, cbJamSelesai;
    DefaultTableModel model;

    public CreateData(DefaultTableModel model) {
        this.model = model;
        initFrame();
        initComponents();
        frame.setVisible(true);
    }

    private void initFrame() {
        frame = new JFrame("Tambah Data");
        frame.setSize(400, 350);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setLayout(new BorderLayout());
    }

    private void initComponents() {

        JLabel lblTitle = new JLabel("Tambah Jadwal Mata Kuliah");
        UIHelper.labelTitle(lblTitle, 18, SwingConstants.LEFT, 10, 15, 10, 15);

        cbHari = FormHelper.createHariComboBox();
        cbJamMulai = FormHelper.createJamMulaiComboBox();
        cbJamSelesai = FormHelper.createJamSelesaiComboBox();

        kodeMatkul = new JTextField(20);
        namaMatkul = new JTextField(20);
        ruangan = new JTextField(20);
        dosen = new JTextField(20);

        JPanel panelForm = new JPanel();
        panelForm.setLayout(new BoxLayout(panelForm, BoxLayout.Y_AXIS));
        panelForm.setBorder(BorderFactory.createEmptyBorder(0, 15, 0, 15));

        panelForm.add(buatBaris("Kode Mata Kuliah", kodeMatkul));
        panelForm.add(buatBaris("Nama Mata Kuliah", namaMatkul));
        panelForm.add(buatBaris("Jadwal Hari", cbHari));
        panelForm.add(buatBaris("Jam Mulai", cbJamMulai));
        panelForm.add(buatBaris("Jam Selesai", cbJamSelesai));
        panelForm.add(buatBaris("Ruangan", ruangan));
        panelForm.add(buatBaris("Dosen", dosen));

        JButton btnSave = new JButton("Simpan");
        JButton btnClear = new JButton("Clear");

        UIHelper.styleButton(btnSave, new Color(25, 135, 84), 12);
        UIHelper.styleButton(btnClear, new Color(108, 117, 125), 12);
        Dimension btnSize = new Dimension(100, 31);
        btnSave.setPreferredSize(btnSize);
        btnClear.setPreferredSize(btnSize);

        btnSave.addActionListener(e -> simpanData());
        btnClear.addActionListener(e -> clearForm());

        JPanel panelButton = new JPanel();
        panelButton.add(btnSave);
        panelButton.add(btnClear);

        frame.add(lblTitle, BorderLayout.NORTH);
        frame.add(panelForm, BorderLayout.CENTER);
        frame.add(panelButton, BorderLayout.SOUTH);
    }

    private void simpanData() {

        String kode = kodeMatkul.getText();
        String nama = namaMatkul.getText();
        String hari = cbHari.getSelectedItem().toString();
        JamSlot mulai = (JamSlot) cbJamMulai.getSelectedItem();
        JamSlot selesai = (JamSlot) cbJamSelesai.getSelectedItem();

        // Validasi
        if (kode.isEmpty() || nama.isEmpty() || hari.equals("Pilih Hari")
                || mulai.getValue().isEmpty() || selesai.getValue().isEmpty()) {
            JOptionPane.showMessageDialog(frame, "Data tidak boleh kosong!");
            return;
        }

        if (!FormHelper.jamValid(mulai.getValue(), selesai.getValue())) {
            JOptionPane.showMessageDialog(frame, "Jam selesai harus setelah jam mulai!");
            return;
        }

        try (FileWriter fw = new FileWriter("src/data/data.csv", true)) {
            fw.write(String.join(";",
                    kode,
                    nama,
                    hari,
                    mulai.getValue(),
                    selesai.getValue(),
                    ruangan.getText(),
                    dosen.getText()
            ));
            fw.write(System.lineSeparator());

            model.addRow(new Object[]{
                    kode,
                    nama,
                    hari,
                    mulai.getValue() + " - " + selesai.getValue(),
                    ruangan.getText(),
                    dosen.getText()
            });

            JOptionPane.showMessageDialog(frame, "Data berhasil disimpan!");
            frame.dispose();

        } catch (IOException e) {
            JOptionPane.showMessageDialog(frame, "Gagal menyimpan data!");
        }
    }

    private void clearForm() {
        kodeMatkul.setText("");
        namaMatkul.setText("");
        ruangan.setText("");
        dosen.setText("");
        cbHari.setSelectedIndex(0);
        cbJamMulai.setSelectedIndex(0);
        cbJamSelesai.setSelectedIndex(0);
    }

    private JPanel buatBaris(String labelText, JComponent field) {
        JPanel row = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 0, 5, 15);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.gridx = 0;
        row.add(new JLabel(labelText), gbc);

        gbc.gridx = 1;
        gbc.weightx = 1;
        row.add(field, gbc);

        return row;
    }
}
