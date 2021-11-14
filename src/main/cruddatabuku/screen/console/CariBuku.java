package main.cruddatabuku.screen.console;

import main.cruddatabuku.buku.DataBuku;

import java.util.List;
import java.util.Scanner;

import static main.cruddatabuku.util.Berkas.tampilData;
import static main.cruddatabuku.util.Kamus.*;

public class CariBuku {
    int opsi;
    String jenisAtribut;
    String dicari;
    Scanner inputOpsi = new Scanner(System.in);
    Scanner search = new Scanner(System.in);

    public CariBuku(List<DataBuku> listDataBuku) {
        do {
            System.out.println("[1] Kode Buku");
            System.out.println("[2] Judul Buku");
            System.out.println("[3] Penulis");
            System.out.println("[4] Penerbit");
            System.out.println("[5] Tahun Terbit");
            System.out.println("[0] Kembali");
            System.out.print("Masukkan pilihan : ");
            opsi = inputOpsi.nextInt();
            jenisAtribut = filter(opsi);
            switch (opsi) {
                case 1:
                case 2:
                case 3:
                case 4:
                case 5:
                    System.out.print("Cari : ");
                    dicari = search.nextLine();
                    tampilData(listDataBuku, jenisAtribut, dicari);
                    break;
            }
        } while (opsi != 0);
    }

    private static String filter(int opsi) {
        String filtered;
        switch (opsi) {
            case 1:
                filtered = KODEBUKU;
                break;
            case 2:
                filtered = JUDULBUKU;
                break;
            case 3:
                filtered = PENULIS;
                break;
            case 4:
                filtered = PENERBIT;
                break;
            case 5:
                filtered = TAHUNTERBIT;
                break;
            default:
                filtered = null;
        }
        return filtered;
    }
}
