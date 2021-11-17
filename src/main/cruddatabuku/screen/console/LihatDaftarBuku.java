package main.cruddatabuku.screen.console;

import main.cruddatabuku.buku.DataBuku;

import java.util.List;
import java.util.Scanner;

import static main.cruddatabuku.util.Berkas.tampilData;
import static main.cruddatabuku.util.Library.clearScreen;

public class LihatDaftarBuku {
    public LihatDaftarBuku(List<DataBuku> listDataBuku) {
        clearScreen();
        Scanner breaker = new Scanner(System.in);
        tampilData(listDataBuku);
        System.out.println();
        System.out.print("Tekan [Enter] untuk kembali...");
        breaker.nextLine();
    }
}
