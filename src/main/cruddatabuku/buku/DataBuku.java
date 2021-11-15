package main.cruddatabuku.buku;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static main.cruddatabuku.util.Kamus.KODE_JENISBUKU;

public class DataBuku {
    private int id;
    private String kodeBuku;
    private String judulBuku;
    private String jenisBuku;
    private String penulis;
    private String penerbit;
    private String tahunTerbit;

    public DataBuku() {
    }

    public DataBuku(int id, String kodeBuku, String judulBuku, String penulis, String penerbit, String tahunTerbit) {
        this.id = id;
        this.kodeBuku = kodeBuku;
        this.judulBuku = judulBuku;
        this.penulis = penulis;
        this.penerbit = penerbit;
        this.tahunTerbit = tahunTerbit;
        setJenisBuku(kodeBuku);
    }

    public DataBuku(String id, String kodeBuku, String judulBuku, String penulis, String penerbit, String tahunTerbit) {
        this.id = Integer.parseInt(id);
        this.kodeBuku = kodeBuku;
        this.judulBuku = judulBuku;
        this.penulis = penulis;
        this.penerbit = penerbit;
        this.tahunTerbit = tahunTerbit;
        setJenisBuku(kodeBuku);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getKodeBuku() {
        return kodeBuku;
    }

    public void setKodeBuku(String kodeBuku) {
        this.kodeBuku = kodeBuku;
    }

    public String getJudulBuku() {
        return judulBuku;
    }

    public void setJudulBuku(String judulBuku) {
        this.judulBuku = judulBuku;
    }

    public String getJenisBuku() {
        return jenisBuku;
    }

    // Digunakan untuk menentukan jenis buku dari kode buku, Akfif ketika membuata buku baru, mengubah kode buku, atau ketika melakukan proses pengambilan data dari database (flat file.txt)
    private void setJenisBuku(String kodeBuku) {
        String kode = kodeBuku.substring(2, 5);
        Pattern p;
        Matcher m;
        for (String[] strings : KODE_JENISBUKU) {
            p = Pattern.compile(strings[0]);
            m = p.matcher(kode);
            if (m.matches()) {
                this.jenisBuku = strings[1];
                break;
            }
        }
    }

    public String getPenulis() {
        return penulis;
    }

    public void setPenulis(String penulis) {
        this.penulis = penulis;
    }

    public String getPenerbit() {
        return penerbit;
    }

    public void setPenerbit(String penerbit) {
        this.penerbit = penerbit;
    }

    public String getTahunTerbit() {
        return tahunTerbit;
    }

    public void setTahunTerbit(String tahunTerbit) {
        this.tahunTerbit = tahunTerbit;
    }

    public void tampil() {
        System.out.println("ID          \t: " + id);
        System.out.println("Kode Buku   \t: " + kodeBuku);
        System.out.println("Judul Buku  \t: " + judulBuku);
        System.out.println("Jenis Buku  \t: " + jenisBuku);
        System.out.println("Penulis     \t: " + penulis);
        System.out.println("Penerbit    \t: " + penerbit);
        System.out.println("Tahun Terbit\t: " + tahunTerbit);
    }

    // Digunakan untuk menulis data kedalam flat file (*.txt)
    public String getTxtFormat() {
        return (id + "#" + kodeBuku + "#" + judulBuku + "#" + penulis + "#" + penerbit + "#" + tahunTerbit);
    }

    public String[] getStringFormat() {
        return new String[]{String.valueOf(getId()), getKodeBuku(), getJudulBuku(), getJenisBuku(), getPenulis(), getPenerbit(), getTahunTerbit()};
    }
}
