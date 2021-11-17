/*
Kamus.java | digunakan untuk menampung semua variable yang sering digunakan.
 */

package main.cruddatabuku.util;

import javax.swing.*;
import java.net.URL;
import java.util.Objects;
import java.util.regex.Pattern;

public interface Kamus {
    char PEMBATAS = '#';

    // daftar attribut yang ada pada databuku, digunakan untuk membanding value pada manajemen berkas
    String KODEBUKU = "KODEBUKU";
    String JUDULBUKU = "JUDULBUKU";
    String PENULIS = "PENULIS";
    String PENERBIT = "PENERBIT";
    String TAHUNTERBIT = "TAHUNTERBIT";

    // Regex | Pattern
    Pattern P_KODEBUKU = Pattern.compile("^KB[\\d][0]{2}\\d+$"); // Kode buku harus terdiri dari huruf KB dilanjutkan dengan 3 angka dengan angka pertama nya angka 0-9 dan dua angka selanjutnya angka 0, dan diiukuti oleh angka yang bebas
    Pattern P_TAHUNTERBIT = Pattern.compile("^[\\d]{4}$");  // Tahun terbit harus terdiri dari 4 angka

    // digunakan untuk menuliskan membuat keterangan jenis buku
    String[][] KODE_JENISBUKU = {
            {"^0[0]{2,2}$", "Karya Umum"},
            {"^1[0]{2,2}$", "Filsafat"},
            {"^2[0]{2,2}$", "Agama"},
            {"^3[0]{2,2}$", "Ilmu Sosial"},
            {"^4[0]{2,2}$", "Bahasa"},
            {"^5[0]{2,2}$", "Ilmu Murni"},
            {"^6[0]{2,2}$", "Pengetahuan Praktis"},
            {"^7[0]{2,2}$", "Kesenian dan Hiburan"},
            {"^8[0]{2,2}$", "Kesusatraan"},
            {"^9[0]{2,2}$", "Sejarah"},
    };

    static void printExist() {
        System.out.println("Data uku sudah ada!");
    }

    static void printError() {
        System.out.println("Error!");
    }

    static void printWrong() {
        System.out.println("Terjadi kesalahan!");
    }

    static void printUpdateSuccess() {
        System.out.println("Update data berhasil");
    }

    static void printIdNotFound() {
        System.out.println("ID tidak ditemukan!");
    }

    static void clearScreen() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    static void printPressEnter(){
        System.out.println("Tekan [Enter] untuk kemabali...");
    }

    // App Icon
    URL appIconURL = Kamus.class.getClassLoader().getResource("assets/icon.png");
    ImageIcon appIcon = new ImageIcon(Objects.requireNonNull(appIconURL));
}
