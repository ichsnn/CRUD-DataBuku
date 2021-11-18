package main.cruddatabuku.screen.console;

import main.cruddatabuku.buku.Buku;

import java.util.List;
import java.util.Scanner;

import static main.cruddatabuku.util.Berkas.tampilData;
import static main.cruddatabuku.util.Library.clearScreen;

public class LihatDaftarBuku {
    public LihatDaftarBuku(List<Buku> listBuku) {
        clearScreen();
        Scanner breaker = new Scanner(System.in);
        tampilData(listBuku);
        System.out.println();
        System.out.print("Tekan [Enter] untuk kembali...");
        breaker.nextLine();
    }
}
