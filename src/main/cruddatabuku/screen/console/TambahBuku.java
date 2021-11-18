package main.cruddatabuku.screen.console;

import main.cruddatabuku.buku.Buku;

import java.io.File;
import java.util.List;
import java.util.Scanner;

import static main.cruddatabuku.util.Berkas.tambahData;
import static main.cruddatabuku.util.Library.clearScreen;

public class TambahBuku {
    public TambahBuku(List<Buku> listBuku, String fileName) {
        clearScreen();
        tambahData(new File(fileName), listBuku);
        System.out.println();
        System.out.print("Buku telah ditambahkan!");
        new Scanner(System.in).nextLine();
    }
}
