package main.cruddatabuku.screen.console;

import main.cruddatabuku.buku.DataBuku;

import java.io.File;
import java.util.List;
import java.util.Scanner;

import static main.cruddatabuku.util.Berkas.tambahData;
import static main.cruddatabuku.util.Kamus.clearScreen;

public class TambahBuku {
    public TambahBuku(List<DataBuku> listDataBuku, String fileName) {
        clearScreen();
        tambahData(new File(fileName), listDataBuku);
        System.out.println();
        System.out.print("Buku telah ditambahkan!");
        new Scanner(System.in).nextLine();
    }
}
