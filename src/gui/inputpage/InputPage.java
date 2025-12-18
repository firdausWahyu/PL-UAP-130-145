package gui.inputpage;

import javax.swing.*;
import java.awt.*;
import java.io.FileWriter;
import java.io.IOException;

public class InputPage {
    JFrame frame;
    JTextField kodeMatkul, namaMatkul;
    JComboBox<String> cbHari;
    JComboBox<String> cbJam;

    public InputPage(){
        frame = new JFrame("Input Data");
        frame.setSize(400, 250);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        frame.setLayout(new BoxLayout(frame.getContentPane(), BoxLayout.Y_AXIS));

        kodeMatkul = new JTextField(20);
        namaMatkul = new JTextField(20);

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

        cbJam = new JComboBox<>(new String[]{
                "Pilih Jam",
                "08:00",
                "09:00",
                "10:00",
                "11:00",
                "12:00",
                "13:00",
                "14:00",
                "15:00",
                "16:00",
                "17:00"
        });

        frame.add(buatBaris("Pilih Kode Mata Kuliah ", kodeMatkul));
        frame.add(buatBaris("Nama Mata Kuliah   ", namaMatkul));
        frame.add(buatBaris("Pilih Jadwal Hari  ", cbHari));
        frame.add(buatBaris("Pilih Jam  ", cbJam));


        JButton btnSave = new JButton("Simpan");
        JButton btnClear = new JButton("Clear");

        btnSave.addActionListener(e -> simpanData());
        btnClear.addActionListener(e -> clearForm());

        frame.add(buatPanelTombol(btnSave, btnClear));
        frame.setVisible(true);
    }

    private void simpanData() {
        String data1 = kodeMatkul.getText();
        String data2 = namaMatkul.getText();
        String hari = cbHari.getSelectedItem().toString();
        String jam  = cbJam.getSelectedItem().toString();


        // Validasi sederhana
        if (data1.isEmpty() || data2.isEmpty() || hari.equals("Pilih Hari")
                || jam.equals("Pilih Jam")) {
            JOptionPane.showMessageDialog(frame, "Data tidak boleh kosong!");
            return;
        }

        try (FileWriter fw = new FileWriter("src/gui/data/data.txt", true)) {
            fw.write(data1 + "|" + data2 + "|" + hari + "|" + jam + "\n");
            JOptionPane.showMessageDialog(frame, "Data berhasil disimpan!");
            clearForm();
            System.out.println(new java.io.File(".").getAbsolutePath());
        } catch (IOException e) {
            JOptionPane.showMessageDialog(frame, "Gagal menyimpan data!");
        }


    }

    private void clearForm() {
        kodeMatkul.setText("");
        namaMatkul.setText("");
        cbHari.setSelectedIndex(0);
        cbJam.setSelectedIndex(0);
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
