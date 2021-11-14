/*
Kamus.java | digunakan untuk menampung semua variable yang sering digunakan.
 */

package main.cruddatabuku.util;

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
    Pattern P_KODEBUKU = Pattern.compile("^KB[\\d]{3}\\d+$");
    Pattern P_TAHUNTERBIT = Pattern.compile("^[\\d]{4}$");
    String[][] KODE_JENISBUKU = {
            {"0[0-9]{2,2}", "Karya Umum"},
            {"1[0-9]{2,2}", "Filsafat"},
            {"2[0-9]{2,2}", "Agama"},
            {"3[0-9]{2,2}", "Ilmu Sosial"},
            {"4[0-9]{2,2}", "Bahasa"},
            {"5[0-9]{2,2}", "Ilmu Murni"},
            {"6[0-9]{2,2}", "Pengetahuan Praktis"},
            {"7[0-9]{2,2}", "Kesenian dan Hiburan"},
            {"8[0-9]{2,2}", "Kesusatraan"},
            {"9[0-9]{2,2}", "Sejarah"},
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
        System.out.print("Update data berhasil");
    }

    static void printIdNotFound() {
        System.out.print("ID tidak ditemukan!");
    }

    static void clearScreen() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    static void printPressEnter(){
        System.out.println("Tekan [Enter] untuk kemabali...");
    }

}
