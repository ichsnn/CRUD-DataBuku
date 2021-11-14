package main.cruddatabuku.screen;

import main.cruddatabuku.buku.DataBuku;
import main.cruddatabuku.screen.console.MainMenu;
import main.cruddatabuku.util.Berkas;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static main.cruddatabuku.util.Berkas.memuatDataBuku;

public class ConsoleApp implements Berkas {
    protected List<DataBuku> listDataBuku = new ArrayList<>();
    protected String fileName = "dataBuku.txt";

    public ConsoleApp() {
        Scanner input = new Scanner(System.in);
        boolean sukses;
        String createNewFile;
        do {
            sukses = memuatDataBuku(new File(fileName), listDataBuku);
            if (!sukses) {
                System.out.println("Apakah Anda Ingin Membuat File Baru (Y/T) ?");
                System.out.print("~ ");
                createNewFile = input.nextLine();
                if (createNewFile.matches("^[y|Y]$")) {
                    System.out.println("Masukkan nama file baru");
                    System.out.print("~ ");
                    fileName = input.nextLine();
                    File newFile = new File(fileName);
                    try {
                        if (newFile.createNewFile()) {
                            System.out.println("File telah dibuat : " + newFile.getName());
                        } else {
                            System.out.println("File su5dah ada.");
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else if (createNewFile.matches("^[t|T]$")) {
                    break;
                } else {
                    System.out.println("Masukkan Salah!");
                }
            }
        } while (!sukses);
        new MainMenu(listDataBuku, fileName);
    }
}
