package gui.inputpage;

import helper.FormHelper;
import helper.UIHelper;
import model.JamSlot;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class UpdateData {

    private JFrame frame;
    private JTextField kodeMatkul, namaMatkul, ruangan, dosen;
    private JComboBox<String> cbHari;
    private JComboBox<JamSlot> cbJamMulai, cbJamSelesai;
    private int indexEdit;
    DefaultTableModel model;


    public UpdateData(String[] data, int index, DefaultTableModel model) {
        this.indexEdit = index;
        this.model = model;

        initFrame();
        initComponent();
        setData(data);
    }

    private void initFrame(){
        frame = new JFrame("Update Data");
        frame.setSize(400, 350);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setLayout(new BorderLayout());
    }

    private void initComponent(){
        JLabel lblTitle = new JLabel("Update Jadwal Mata Kuliah");
        lblTitle.setFont(new Font("Arial", Font.BOLD, 18));
        lblTitle.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));

        kodeMatkul = new JTextField();
        namaMatkul = new JTextField();
        ruangan = new JTextField();
        dosen = new JTextField();

        cbHari = FormHelper.createHariComboBox();
        cbJamMulai = FormHelper.createJamMulaiComboBox();
        cbJamSelesai = FormHelper.createJamSelesaiComboBox();

        JPanel panelForm = new JPanel();
        panelForm.setLayout(new BoxLayout(panelForm, BoxLayout.Y_AXIS));
        panelForm.setBorder(BorderFactory.createEmptyBorder(0, 15, 0, 15));

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
        frame.setVisible(true);
    }

    private void setData(String[] data) {
        kodeMatkul.setText(data[0]);
        namaMatkul.setText(data[1]);
        cbHari.setSelectedItem(data[2]);
        setJam(cbJamMulai, data[3]);
        setJam(cbJamSelesai, data[4]);
        ruangan.setText(data[5]);
        dosen.setText(data[6]);
    }


    private void simpanData() {
        String kode = kodeMatkul.getText();
        String nama = namaMatkul.getText();
        String ruang = ruangan.getText();
        String dsn = dosen.getText();
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

        if (!FormHelper.jamValid(jm.getValue(), js.getValue())) {
            JOptionPane.showMessageDialog(frame, "Jam selesai harus setelah jam mulai!");
            return;
        }

        File file = new File("src/data/data.csv");
        List<String> lines = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                lines.add(line);
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(frame, "Gagal membaca data!");
            return;
        }

        int barisData = indexEdit + 1;
        lines.set(barisData, String.join(";",
                kode,
                nama,
                hari,
                jm.getValue(),
                js.getValue(),
                ruang,
                dsn
        ));

        try (FileWriter fw = new FileWriter(file)) {
            for (String s : lines) {
                fw.write(s + System.lineSeparator());
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(frame, "Gagal update data!");
            return;
        }

        model.setValueAt(kode, indexEdit, 0);
        model.setValueAt(nama, indexEdit, 1);
        model.setValueAt(hari, indexEdit, 2);
        model.setValueAt(jm.getValue() + " - " + js.getValue(), indexEdit, 3);
        model.setValueAt(ruang, indexEdit, 4);
        model.setValueAt(dsn, indexEdit, 5);

        JOptionPane.showMessageDialog(frame, "Data berhasil diupdate!");
        frame.dispose();
    }

    private void clearForm() {
        namaMatkul.setText("");
        ruangan.setText("");
        dosen.setText("");
        cbHari.setSelectedIndex(0);
        cbJamMulai.setSelectedIndex(0);
        cbJamSelesai.setSelectedIndex(0);
    }

    private void setJam(JComboBox<JamSlot> combo, String value) {
        for (int i = 0; i < combo.getItemCount(); i++) {
            if (combo.getItemAt(i).getValue().equals(value)) {
                combo.setSelectedIndex(i);
                break;
            }
        }
    }

    private JPanel buatBaris(String labelText, JComponent field) {
        JPanel row = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        gbc.insets = new Insets(5, 0, 5, 15);
        gbc.gridx = 0;
        gbc.anchor = GridBagConstraints.WEST;
        row.add(new JLabel(labelText), gbc);

        gbc.gridx = 1;
        gbc.weightx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        row.add(field, gbc);

        return row;
    }
}
