/*
Library.java | digunakan untuk menampung semua variable yang sering digunakan.
 */

package main.cruddatabuku.util;

import javax.swing.*;
import java.io.File;
import java.net.URL;
import java.util.Objects;
import java.util.regex.Pattern;

public interface Library {
    char PEMBATAS = ';';

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
            {"^0[0]{2,2}$", "Karya Umum"},              // 000
            {"^1[0]{2,2}$", "Filsafat"},                // 100
            {"^2[0]{2,2}$", "Agama"},                   // 200
            {"^3[0]{2,2}$", "Ilmu Sosial"},             // 300
            {"^4[0]{2,2}$", "Bahasa"},                  // 400
            {"^5[0]{2,2}$", "Ilmu Murni"},              // 500
            {"^6[0]{2,2}$", "Pengetahuan Praktis"},     // 600
            {"^7[0]{2,2}$", "Kesenian dan Hiburan"},    // 700
            {"^8[0]{2,2}$", "Kesusatraan"},             // 800
            {"^9[0]{2,2}$", "Sejarah"},                 // 900
    };
    // App Icon
    URL appIconURL = Library.class.getClassLoader().getResource("assets/icon.png");
    ImageIcon appIcon = new ImageIcon(Objects.requireNonNull(appIconURL));

    static void printError() {
        System.out.println("Error!");
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

    // digunakan untuk mencatat riwayat file yang pernah dibuka sebelumnya
    File recentFile = new File("recent-file.txt");
}
