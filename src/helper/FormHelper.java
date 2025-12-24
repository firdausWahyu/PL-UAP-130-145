package helper;

import model.JamSlot;

import javax.swing.*;

public class FormHelper {

    private FormHelper() {}

    public static JComboBox<String> createHariComboBox() {
        return new JComboBox<>(new String[]{
                "Pilih Hari",
                "Senin", "Selasa", "Rabu",
                "Kamis", "Jumat", "Sabtu", "Minggu"
        });
    }

    public static JComboBox<JamSlot> createJamMulaiComboBox() {
        return new JComboBox<>(new JamSlot[]{
                new JamSlot("Pilih Jam Mulai", ""),
                new JamSlot("Jam ke-1 07:00", "07:00"),
                new JamSlot("Jam ke-2 07:50", "07:50"),
                new JamSlot("Jam ke-3 08:40", "08:40"),
                new JamSlot("Jam ke-4 09:30", "09:30"),
                new JamSlot("Jam ke-5 10:20", "10:20"),
                new JamSlot("Jam ke-6 11:10", "11:10"),
                new JamSlot("Jam ke-7 12:30", "12:30"),
                new JamSlot("Jam ke-8 13:20", "13:20"),
                new JamSlot("Jam ke-9 14:10", "14:10"),
                new JamSlot("Jam ke-10 15:30", "15:30"),
                new JamSlot("Jam ke-11 16:20", "16:20"),
                new JamSlot("Jam ke-12 18:15", "18:15"),
                new JamSlot("Jam ke-13 19:05", "19:05"),
                new JamSlot("Jam ke-14 19:55", "19:55")
        });
    }

    public static JComboBox<JamSlot> createJamSelesaiComboBox() {
        return new JComboBox<>(new JamSlot[]{
                new JamSlot("Pilih Jam Selesai", ""),
                new JamSlot("Jam ke-1 07:50", "07:50"),
                new JamSlot("Jam ke-2 08:40", "08:40"),
                new JamSlot("Jam ke-3 09:30", "09:30"),
                new JamSlot("Jam ke-4 10:20", "10:20"),
                new JamSlot("Jam ke-5 11:10", "11:10"),
                new JamSlot("Jam ke-6 12:00", "12:00"),
                new JamSlot("Jam ke-7 13:20", "13:20"),
                new JamSlot("Jam ke-8 14:10", "14:10"),
                new JamSlot("Jam ke-9 15:00", "15:00"),
                new JamSlot("Jam ke-10 16:20", "16:20"),
                new JamSlot("Jam ke-11 17:10", "17:10"),
                new JamSlot("Jam ke-12 19:05", "19:05"),
                new JamSlot("Jam ke-13 19:55", "19:55"),
                new JamSlot("Jam ke-14 20:45", "20:45")
        });
    }

    public static boolean jamValid(String mulai, String selesai) {
        return mulai.compareTo(selesai) < 0;
    }
}
