package gui.inputpage;

import javax.swing.*;
import java.awt.*;
import java.io.FileWriter;
import java.io.IOException;

public class CreateData {
    JFrame frame;
    JTextField kodeMatkul, namaMatkul, ruangan, dosen;
    JComboBox<String> cbHari;
    JComboBox<JamSlot> cbJamMulai;
    JComboBox<JamSlot> cbJamSelesai;

    //Form
    public CreateData(){
        frame = new JFrame("Tambah Data");
        frame.setSize(400, 350);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        // Label tittle
        JLabel lblTitle = new JLabel("Tambah Jadwal Mata Kuliah");
        lblTitle.setFont(new Font("Arial", Font.BOLD, 18));
        lblTitle.setAlignmentX(Component.LEFT_ALIGNMENT);
        lblTitle.setBorder(BorderFactory.createEmptyBorder(0, 15, 0, 15));

        JPanel panelHeader = new JPanel(new BorderLayout());
        panelHeader.setAlignmentX(Component.LEFT_ALIGNMENT);
        panelHeader.add(lblTitle, BorderLayout.WEST);

        kodeMatkul = new JTextField(20);
        namaMatkul = new JTextField(20);
        ruangan = new JTextField(20);
        dosen = new JTextField(20);

        cbHari();
        cbJamMulai();
        cbJamSelesai();

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
        buttonColor(btnSave, new Color(25, 135, 84));
        buttonColor(btnClear, new Color(108, 117, 125));

        btnSave.addActionListener(e -> simpanData());
        btnClear.addActionListener(e -> clearForm());

        frame.add(panelHeader, BorderLayout.NORTH);
        frame.add(panelForm, BorderLayout.CENTER);
        frame.add(buatPanelTombol(btnSave, btnClear), BorderLayout.SOUTH);
        frame.setVisible(true);
    }

    private void simpanData() {
        String data1 = kodeMatkul.getText();
        String data2 = namaMatkul.getText();
        String data3 = ruangan.getText();
        String data4 = dosen.getText();
        String checkBoxHari = cbHari.getSelectedItem().toString();
        JamSlot jamMulai = (JamSlot) cbJamMulai.getSelectedItem();
        JamSlot jamSelesai = (JamSlot) cbJamSelesai.getSelectedItem();

        String jamMulaiValue = jamMulai.getValue();
        String jamSelesaiValue = jamSelesai.getValue();


        // Validasi sederhana
        if (data1.isEmpty() || data2.isEmpty() || checkBoxHari.equals("Pilih Hari")
                || jamMulaiValue.equals("Pilih Jam") || jamSelesaiValue.equals("Pilih Jam")) {
            JOptionPane.showMessageDialog(frame, "Data tidak boleh kosong!");
            return;
        }

        if (jamMulaiValue.isEmpty() || jamSelesaiValue.isEmpty()) {
            JOptionPane.showMessageDialog(frame, "Jam mulai dan selesai wajib dipilih!");
            return;
        }

        if (!jamValid(jamMulaiValue, jamSelesaiValue)) {
            JOptionPane.showMessageDialog(frame, "Jam selesai harus setelah jam mulai!");
            return;
        }

        try (FileWriter fw = new FileWriter("src/gui/data/data.csv", true)) {

            fw.write(String.join(";",
                    data1,
                    data2,
                    checkBoxHari,
                    jamMulaiValue,
                    jamSelesaiValue,
                    data3,
                    data4
            ));
            fw.write(System.lineSeparator());

            JOptionPane.showMessageDialog(frame, "Data berhasil disimpan!");
            frame.dispose();

        } catch (IOException e) {
            JOptionPane.showMessageDialog(frame, "Gagal menyimpan data!");
        }

    }

    private void cbHari(){
        cbHari = new JComboBox<>(new String[]{
                "Pilih Hari",
                "Senin",
                "Selasa",
                "Rabu",
                "Kamis",
                "Jumat",
                "Sabtu",
                "Minggu"
        });
    }

    private void cbJamMulai(){
        cbJamMulai = new JComboBox<>(new JamSlot[]{
                new JamSlot("Pilih Jam Mulai", ""),
                new JamSlot("Jam ke-1 (07:00)", "07:00"),
                new JamSlot("Jam ke-2 (07:50)", "07:50"),
                new JamSlot("Jam ke-3 (08:40)", "08:40"),
                new JamSlot("Jam ke-4 (09:30)", "09:30"),
                new JamSlot("Jam ke-5 (10:20)", "10:20"),
                new JamSlot("Jam ke-6 (11:10)", "11:10"),
                new JamSlot("Jam ke-7 (12:00)", "12:00"),
                new JamSlot("Jam ke-8 (12:30)", "12:30"),
                new JamSlot("Jam ke-9 (14:10)", "14:10"),
                new JamSlot("Jam ke-10 (15:30)", "15:30"),
                new JamSlot("Jam ke-11 (16:20)", "16:20"),
                new JamSlot("Jam ke-12 (18:15)", "18:15"),
                new JamSlot("Jam ke-13 (19:05)", "19:05"),
                new JamSlot("Jam ke-14 (19:55)", "19:55"),
        });
    }

    private void cbJamSelesai(){
        cbJamSelesai = new JComboBox<>(new JamSlot[]{
                new JamSlot("Pilih Jam Selesai", ""),
                new JamSlot("Jam ke-1 (07:50)", "07:50"),
                new JamSlot("Jam ke-2 (08:40)", "08:40"),
                new JamSlot("Jam ke-3 (09:30)", "09:30"),
                new JamSlot("Jam ke-4 (10:20)", "10:20"),
                new JamSlot("Jam ke-5 (11:10)", "11:10"),
                new JamSlot("Jam ke-6 (12:00)", "12:00"),
                new JamSlot("Jam ke-7 (13:20)", "13:20"),
                new JamSlot("Jam ke-8 (14:10)", "14:10"),
                new JamSlot("Jam ke-9 (15:00)", "15:00"),
                new JamSlot("Jam ke-10 (16:20)", "16:20"),
                new JamSlot("Jam ke-11 (17:10)", "17:10"),
                new JamSlot("Jam ke-12 (19:05)", "19:05"),
                new JamSlot("Jam ke-13 (19:55)", "19:55"),
                new JamSlot("Jam ke-14 (20:45)", "20:45")
        });
    }

    public class JamSlot {
        private String label;
        private String value;

        public JamSlot(String label, String value) {
            this.label = label;
            this.value = value;
        }

        public String getValue() {
            return value;
        }

        @Override
        public String toString() {
            return label; // INI yang tampil di ComboBox
        }
    }

    private void clearForm() {
        kodeMatkul.setText("");
        namaMatkul.setText("");
        cbHari.setSelectedIndex(0);
        cbJamMulai.setSelectedIndex(0);
        cbJamSelesai.setSelectedIndex(0);
        ruangan.setText("");
    }

    private boolean jamValid(String mulai, String selesai) {
        return mulai.compareTo(selesai) < 0;
    }

    private JPanel buatBaris(String labelText, JComponent field) {
        JPanel row = new JPanel(new GridBagLayout());
        row.setAlignmentX(Component.LEFT_ALIGNMENT);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 0, 5, 15);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 0;
        gbc.anchor = GridBagConstraints.WEST;

        JLabel label = new JLabel(labelText);
        label.setPreferredSize(new Dimension(160, 25));
        row.add(label, gbc);


        gbc.gridx = 1;
        gbc.weightx = 1.0;
        row.add(field, gbc);

        return row;
    }


    private JPanel buatPanelTombol(JButton btnSave, JButton btnClear) {
        JPanel panel = new JPanel();
        panel.add(btnSave);
        panel.add(btnClear);
        return panel;
    }

    private void buttonColor(JButton btn, Color bg) {
        btn.setBackground(bg);
        btn.setForeground(Color.WHITE);
        btn.setFocusPainted(false);
        btn.setBorder(BorderFactory.createEmptyBorder(8, 16, 8, 16));
    }

}
