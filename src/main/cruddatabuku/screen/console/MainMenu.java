package main.cruddatabuku.screen.console;

import main.cruddatabuku.buku.Buku;

import java.util.List;
import java.util.Scanner;

import static main.cruddatabuku.util.Library.clearScreen;

public class MainMenu {

    public MainMenu(List<Buku> listBuku, String fileName) {
        Scanner input = new Scanner(System.in);
        String menu;
        do {
            clearScreen();
            System.out.println("PROGRAM DAFTAR BUKU");
            System.out.println("[1] Lihat Daftar Buku");
            System.out.println("[2] Cari Buku");
            System.out.println("[3] Tambah Buku");
            System.out.println("[4] Update Buku");
            System.out.println("[5] Hapus Buku");
            System.out.println("[0] Keluar");
            System.out.print("Menu : ");
            menu = input.nextLine();
            switch (menu) {
                case "1":
                    new LihatDaftarBuku(listBuku);
                    break;
                case "2":
                    new CariBuku(listBuku);
                    break;
                case "3":
                    new TambahBuku(listBuku, fileName);
                    break;
                case "4":
                    new UpdateBuku(listBuku, fileName);
                    break;
                case "5":
                    new HapusBuku(listBuku, fileName);
                    break;
            }
        } while (!"0".contentEquals(menu));
    }

}