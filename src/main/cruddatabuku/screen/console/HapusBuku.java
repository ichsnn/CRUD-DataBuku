package main.cruddatabuku.screen.console;

import main.cruddatabuku.buku.DataBuku;

import java.util.List;
import java.util.Scanner;

import static main.cruddatabuku.util.Berkas.*;

public class HapusBuku {
    public HapusBuku(List<DataBuku> listDataBuku, String fileName) {
        Scanner input = new Scanner(System.in);
        Scanner inputInt = new Scanner(System.in);
        String opsi;
        do {
            System.out.println("[1] Hapus Satu Buku");
            System.out.println("[2] Hapus Semua Buku");
            System.out.println("[0] Kembali");
            System.out.print("~ Opsi : ");
            opsi = input.nextLine();
            switch (opsi) {
                case "1":
                    System.out.println("Masukkan nomor ID buku");
                    System.out.print("~ ID : ");
                    deleteData(fileName, listDataBuku, inputInt.nextInt());
                    break;
                case "2":
                    deleteSemuaData(fileName, listDataBuku);
                    break;
            }
        } while (!"0".contentEquals(opsi));
    }
}
