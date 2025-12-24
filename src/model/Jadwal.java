package model;

public class Jadwal {

    private String kode;
    private String nama;
    private String hari;
    private String jamMulai;
    private String jamSelesai;
    private String ruangan;
    private String dosen;

    public Jadwal(String kode, String nama, String hari,
                  String jamMulai, String jamSelesai,
                  String ruangan, String dosen) {
        this.kode = kode;
        this.nama = nama;
        this.hari = hari;
        this.jamMulai = jamMulai;
        this.jamSelesai = jamSelesai;
        this.ruangan = ruangan;
        this.dosen = dosen;
    }

    // Getter (wajib, setter opsional)
    public String getKode() { return kode; }
    public String getNama() { return nama; }
    public String getHari() { return hari; }
    public String getJamMulai() { return jamMulai; }
    public String getJamSelesai() { return jamSelesai; }
    public String getRuangan() { return ruangan; }
    public String getDosen() { return dosen; }
}
