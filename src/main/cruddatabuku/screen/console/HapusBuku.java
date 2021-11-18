package main.cruddatabuku.screen.console;

import main.cruddatabuku.buku.Buku;

import java.util.List;
import java.util.Scanner;

import static main.cruddatabuku.util.Berkas.deleteData;
import static main.cruddatabuku.util.Berkas.deleteSemuaData;
import static main.cruddatabuku.util.Library.clearScreen;

public class HapusBuku {
    public HapusBuku(List<Buku> listBuku, String fileName) {
        Scanner input = new Scanner(System.in);
        Scanner inputInt = new Scanner(System.in);
        String opsi;
        do {
            clearScreen();
            System.out.println("[1] Hapus Satu Buku");
            System.out.println("[2] Hapus Semua Buku");
            System.out.println("[0] Kembali");
            System.out.print("~ Opsi : ");
            opsi = input.nextLine();
            switch (opsi) {
                case "1":
                    System.out.println("Masukkan nomor ID buku");
                    System.out.print("~ ID : ");
                    deleteData(fileName, listBuku, inputInt.nextInt());
                    break;
                case "2":
                    deleteSemuaData(fileName, listBuku);
                    break;
            }
        } while (!"0".contentEquals(opsi));
    }
}
