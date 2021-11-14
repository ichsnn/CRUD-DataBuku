package main.cruddatabuku.screen.console;

import main.cruddatabuku.buku.DataBuku;

import java.io.File;
import java.util.List;

import static main.cruddatabuku.util.Berkas.tambahData;

public class TambahBuku {
    public TambahBuku(List<DataBuku> listDataBuku, String fileName) {
        tambahData(new File(fileName), listDataBuku);
    }
}
