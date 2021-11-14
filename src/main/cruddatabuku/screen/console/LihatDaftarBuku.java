package main.cruddatabuku.screen.console;

import main.cruddatabuku.buku.DataBuku;

import java.util.List;

import static main.cruddatabuku.util.Berkas.tampilData;

public class LihatDaftarBuku {
    public LihatDaftarBuku(List<DataBuku> listDataBuku) {
        tampilData(listDataBuku);
    }
}
