package main.cruddatabuku.screen.console;

import main.cruddatabuku.buku.DataBuku;

import java.util.List;
import java.util.Scanner;

import static main.cruddatabuku.util.Berkas.tampilData;
import static main.cruddatabuku.util.Library.*;

public class CariBuku {
    String opsi;
    String jenisAtribut;
    String dicari;
    Scanner input = new Scanner(System.in);

    public CariBuku(List<DataBuku> listDataBuku) {
        do {
            clearScreen();
            System.out.println("CARI BUKU");
            System.out.println("[1] Kode Buku");
            System.out.println("[2] Judul Buku");
            System.out.println("[3] Penulis");
            System.out.println("[4] Penerbit");
            System.out.println("[5] Tahun Terbit");
            System.out.println("[0] Kembali");
            System.out.print("~ Jenis atribut : ");
            opsi = input.nextLine();
            jenisAtribut = filter(opsi);
            switch (opsi) {
                case "1":
                case "2":
                case "3":
                case "4":
                case "5":
                    System.out.print("~ Cari : ");
                    dicari = input.nextLine();
                    System.out.println();
                    tampilData(listDataBuku, jenisAtribut, dicari);
                    System.out.println();
                    System.out.println("Tekan [Enter] untuk kembali...");
                    System.out.println();
                    input.nextLine();
                    break;
            }
        } while (!"0".contentEquals(opsi));
    }

    // method untuk melakukan filter terhadap opsi merubahnya kedalam string nama jenisAtribut yang ada pada class Buku.java
    private static String filter(String opsi) {
        String filtered;
        switch (opsi) {
            case "1":
                filtered = KODEBUKU;
                break;
            case "2":
                filtered = JUDULBUKU;
                break;
            case "3":
                filtered = PENULIS;
                break;
            case "4":
                filtered = PENERBIT;
                break;
            case "5":
                filtered = TAHUNTERBIT;
                break;
            default:
                filtered = null;
        }
        return filtered;
    }
}
