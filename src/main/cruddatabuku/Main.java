package main.cruddatabuku;

import main.cruddatabuku.screen.gui.Home;

import java.io.File;

public class Main {
    public static void main(String[] args) {
        // Membuat folder default untuk menampung file data
        File defaultFolder = new File("data");
        if (!defaultFolder.exists()) {
            boolean created = defaultFolder.mkdir();
            if (created) System.out.print("");
        }

        // Jika ingin meilhat tampilan GUI tuliskan :
        new Home();

        // jika ingin melihat tampilan console tuliskan :
        // new MainMenu();
    }
}
