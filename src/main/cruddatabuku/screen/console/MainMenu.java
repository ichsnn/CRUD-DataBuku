package main.cruddatabuku.screen.console;

import main.cruddatabuku.buku.DataBuku;

import java.util.List;
import java.util.Scanner;

import static main.cruddatabuku.util.Kamus.clearScreen;

public class MainMenu {

    public MainMenu(List<DataBuku> listDataBuku, String fileName) {
        Scanner input = new Scanner(System.in);
        String menu;
        do {
            clearScreen();
            System.out.println("Program Daftar Buku");
            System.out.println("[1] Lihat Daftar Buku");
            System.out.println("[2] Cari Buku");
            System.out.println("[3] Tambah Buku");
            System.out.println("[4] Update Buku");
            System.out.println("[0] Keluar");
            System.out.print("Menu : ");
            menu = input.nextLine();
            switch (menu) {
                case "1":
                    new LihatDaftarBuku(listDataBuku);
                    break;
                case "2":
                    new CariBuku(listDataBuku);
                    break;
                case "3":
                    new TambahBuku(listDataBuku, fileName);
                    break;
                case "4":
                    new UpdateBuku(listDataBuku, fileName);
                    break;
            }
        } while (!"0".contentEquals(menu));
    }

}