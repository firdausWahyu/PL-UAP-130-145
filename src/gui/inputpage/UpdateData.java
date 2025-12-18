package gui.inputpage;

import javax.swing.*;
import java.awt.*;
import java.io.*;

public class UpdateData {
    JFrame frame;
    JTextField kodeMatkul, namaMatkul, ruangan;
    JComboBox<String> cbHari;
    JComboBox<JamSlot> cbJamMulai;
    JComboBox<JamSlot> cbJamSelesai;
    private int indexEdit;

    public UpdateData(String[] data, int index){
        this.indexEdit = index;
        frame = new JFrame("Update Data");
        frame.setSize(400, 300);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setLayout(new BoxLayout(frame.getContentPane(), BoxLayout.Y_AXIS));

        kodeMatkul = new JTextField(20);
        namaMatkul = new JTextField(20);
        ruangan = new JTextField(20);

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

        cbJamMulai = new JComboBox<>(new JamSlot[]{
                new JamSlot("Pilih Jam", ""),
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

        cbJamSelesai = new JComboBox<>(new JamSlot[]{
                new JamSlot("Pilih Jam", ""),
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

        frame.add(buatBaris("Pilih Kode Mata Kuliah ", kodeMatkul));
        frame.add(buatBaris("Nama Mata Kuliah   ", namaMatkul));
        frame.add(buatBaris("Pilih Jadwal Hari  ", cbHari));
        frame.add(buatBaris("Pilih Jam  Mulai   ", cbJamMulai));
        frame.add(buatBaris("Pilih Jam  Selesai ", cbJamSelesai));
        frame.add(buatBaris("Pilih Ruangan  ", ruangan));

        kodeMatkul.setText(data[0]);
        namaMatkul.setText(data[1]);
        cbHari.setSelectedItem(data[2]);
        ruangan.setText(data[5]);

        setJam(cbJamMulai, data[3]);
        setJam(cbJamSelesai, data[4]);

        JButton btnSave = new JButton("Simpan");
        JButton btnClear = new JButton("Clear");

        btnSave.addActionListener(e -> simpanData());
        btnClear.addActionListener(e -> clearForm());

        frame.add(buatPanelTombol(btnSave, btnClear));
        frame.setVisible(true);
    }

    private void simpanData() {
        String kode = kodeMatkul.getText();
        String nama = namaMatkul.getText();
        String ruang = ruangan.getText();
        String hari = cbHari.getSelectedItem().toString();
        JamSlot jm = (JamSlot) cbJamMulai.getSelectedItem();
        JamSlot js = (JamSlot) cbJamSelesai.getSelectedItem();

        if (kode.isEmpty() || nama.isEmpty() || ruang.isEmpty()
                || hari.equals("Pilih Hari")
                || jm.getValue().isEmpty()
                || js.getValue().isEmpty()) {
            JOptionPane.showMessageDialog(frame, "Data tidak boleh kosong!");
            return;
        }

        if (!jamValid(jm.getValue(), js.getValue())) {
            JOptionPane.showMessageDialog(frame, "Jam selesai harus setelah jam mulai!");
            return;
        }

        File file = new File("src/gui/data/data.txt");

        try {
            java.util.List<String> semuaData = new java.util.ArrayList<>();
            BufferedReader br = new BufferedReader(new FileReader(file));
            String line;

            while ((line = br.readLine()) != null) {
                semuaData.add(line);
            }
            br.close();

            String dataBaru = kode + "|" + nama + "|" + hari + "|"
                    + jm.getValue() + "|" + js.getValue() + "|" + ruang;

            semuaData.set(indexEdit, dataBaru);

            FileWriter fw = new FileWriter(file);
            for (String s : semuaData) {
                fw.write(s + System.lineSeparator());
            }
            fw.close();

            JOptionPane.showMessageDialog(frame, "Data berhasil diupdate");
            frame.dispose();

        } catch (IOException e) {
            JOptionPane.showMessageDialog(frame, "Gagal update data!");
            e.printStackTrace();
        }
    }


    private void setJam(JComboBox<JamSlot> combo, String value) {
        for (int i = 0; i < combo.getItemCount(); i++) {
            JamSlot slot = combo.getItemAt(i);
            if (slot.getValue().equals(value)) {
                combo.setSelectedIndex(i);
                break;
            }
        }
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
    }

    private boolean jamValid(String mulai, String selesai) {
        return mulai.compareTo(selesai) < 0;
    }

    private JPanel buatBaris(String label, JComponent comp) {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panel.add(new JLabel(label));
        panel.add(comp);
        return panel;
    }

    private JPanel buatPanelTombol(JButton btnSave, JButton btnClear) {
        JPanel panel = new JPanel();
        panel.add(btnSave);
        panel.add(btnClear);
        return panel;
    }

}
