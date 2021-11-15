/*
ConsoleApp.java | merupakan file untuk mengontrol jalannya program.
Panggil file ini di main file dengan syntax ' new ConsoleApp(); '
 */

package main.cruddatabuku.screen.console;

import main.cruddatabuku.buku.DataBuku;
import main.cruddatabuku.screen.console.MainMenu;
import main.cruddatabuku.util.Berkas;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static main.cruddatabuku.util.Berkas.memuatDataBuku;
import static main.cruddatabuku.util.Kamus.clearScreen;
import static main.cruddatabuku.util.Kamus.printError;

public class ConsoleApp implements Berkas {
    protected List<DataBuku> listDataBuku = new ArrayList<>();
    protected String fileName = ".\\data\\dataBuku.txt";

    public ConsoleApp() {
        clearScreen();
        Scanner input = new Scanner(System.in);
        boolean sukses;
        String createNewFile;

        if (!new File(fileName).exists()) {
            File file = new File(fileName);
            try {
                boolean ready = file.createNewFile();
                if (ready)
                    System.out.println("Default file created!");
            } catch (IOException e) {
                printError();
            }
        }

        // proses inisialisi nama file / file name, jika user memilih tidak menginputkan (T), maka akan menggunakan nama file default / bawaan
        String open;
        System.out.println("Masukkan nama file yang ingin dibuka");
        System.out.print("~ (default : dataBuku.txt) : ");
        open = input.nextLine();
        if(!open.isEmpty()) {
            fileName = "data\\" + open;
        }
        // load file sesuai dengan file name yang diinputkan, program akan memeriksa default file name (dataBuku.txt)
        // jika saat proses load data ada kesalahan maka program akan meminta membuat file baru
        do {
            sukses = memuatDataBuku(new File(fileName), listDataBuku);
            if (!sukses) {
                System.out.println("Apakah Anda Ingin Membuat File Baru (Y/T) ?");
                System.out.print("~ ");
                createNewFile = input.nextLine();
                if ("Y".contentEquals(createNewFile.toUpperCase())) {
                    System.out.println("Masukkan nama file baru");
                    System.out.print("~ ");
                    fileName = input.nextLine();
                    File newFile = new File(fileName);
                    try {
                        if (newFile.createNewFile()) {
                            System.out.println("File telah dibuat : " + newFile.getName());
                        } else {
                            System.out.println("File sudah ada.");
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else if ("T".contentEquals(createNewFile.toUpperCase())) {
                    break;
                } else {
                    System.out.println("Masukkan Salah!");
                }
            }
        } while (!sukses);

        // jalankan menu utama jika load data sukses jika tidak keluar program
        if (sukses) {
            new MainMenu(listDataBuku, fileName);
        }
        input.close();
        clearScreen();
    }
}
