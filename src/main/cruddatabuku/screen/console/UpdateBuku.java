package main.cruddatabuku.screen.console;

import main.cruddatabuku.buku.Buku;

import java.util.List;
import java.util.Scanner;

import static main.cruddatabuku.util.Berkas.updateData;
import static main.cruddatabuku.util.Berkas.validasiInput;
import static main.cruddatabuku.util.Library.*;

public class UpdateBuku {
    private String jenisAtribut;
    private String isiData;
    private int id;

    public UpdateBuku(List<Buku> listBuku, String fileName) {
        String opsi;
        Scanner input = new Scanner(System.in);
        do {
            clearScreen();
            System.out.println("UPDATE BUKU");
            System.out.println("[1] Kode Buku");
            System.out.println("[2] Judul Buku");
            System.out.println("[3] Penulis");
            System.out.println("[4] Penerbit");
            System.out.println("[5] Tahun Terbit");
            System.out.println("[0] Kembali");
            System.out.print("~ Jenis atribut : ");
            opsi = input.nextLine();
            jenisAtribut = filter(opsi);
            switch (opsi) {
                case "1":
                case "2":
                case "3":
                case "4":
                case "5":
                    boolean idExeption = false;
                    String idValue;
                    do {
                        System.out.print("~ ID : ");
                        idValue = input.nextLine();
                    } while (!idValue.matches("^[\\d]+$"));
                    id = Integer.parseInt(idValue);
                    for (Buku buku : listBuku) {
                        if (buku.getId() == id) {
                            idExeption = true;
                            break;
                        }
                    }
                    if (!idExeption) {
                        printIdNotFound();
                        new Scanner(System.in).nextLine();
                        break;
                    }
                    validasiIsiData(listBuku);
                    updateData(fileName, listBuku, id, jenisAtribut, isiData);
                    break;
            }
        } while (!"0".contentEquals(opsi));
    }

    private String filter(String opsi) {
        String filtered = null;
        switch (opsi) {
            case "1":
                filtered = KODEBUKU;
                break;
            case "2":
                filtered = JUDULBUKU;
                break;
            case "3":
                filtered = PENULIS;
                break;
            case "4":
                filtered = PENERBIT;
                break;
            case "5":
                filtered = TAHUNTERBIT;
                break;
        }
        return filtered;
    }

    private void validasiIsiData(List<Buku> listBuku) {
        switch (jenisAtribut) {
            case KODEBUKU:
                validasiKodeBuku(listBuku);
                break;
            case JUDULBUKU:
                getJudulBukuSaatIni(listBuku);
                isiData = validasiInput("Judul Buku");
                break;
            case PENULIS:
                getPenulisSaatIni(listBuku);
                isiData = validasiInput("Penulis");
                break;
            case PENERBIT:
                getPenerbitSaatIni(listBuku);
                isiData = validasiInput("Penerbit");
                break;
            case TAHUNTERBIT:
                getTahunTerbitSaatIni(listBuku);
                validasiInput("Tahun Terbit", P_TAHUNTERBIT);
                break;
        }
    }

    private void validasiKodeBuku(List<Buku> listBuku) {
        boolean sameValue = false;
        String kodeBukuCurrent;
        do {
            kodeBukuCurrent = getKodeBukuIni(listBuku);
            isiData = validasiInput("Kode Buku", P_KODEBUKU);
            for (Buku buku : listBuku) {
                if (kodeBukuCurrent.contentEquals(isiData)) {
                    sameValue = false;
                } else
                if (buku.getKodeBuku().contentEquals(isiData)) {
                    System.out.println("Kode buku sudah ada!");
                    sameValue = true;
                    break;
                } else {
                    sameValue = false;
                }
            }
        } while (sameValue);
    }

    private String getKodeBukuIni(List<Buku> listBuku) {
        for (Buku buku : listBuku) {
            if (buku.getId() == id) {
                System.out.println("Isi data saat ini : " + buku.getKodeBuku());
                return buku.getKodeBuku();
            }
        }
        return "";
    }

    private void getJudulBukuSaatIni(List<Buku> listBuku) {
        for (Buku buku : listBuku) {
            if (buku.getId() == id) {
                System.out.println("Isi data saat ini : " + buku.getJudulBuku());
                break;
            }
        }
    }

    private void getPenulisSaatIni(List<Buku> listBuku) {
        for (Buku buku : listBuku) {
            if (buku.getId() == id) {
                System.out.println("Isi data saat ini : " + buku.getPenulis());
                break;
            }
        }
    }

    private void getPenerbitSaatIni(List<Buku> listBuku) {
        for (Buku buku : listBuku) {
            if (buku.getId() == id) {
                System.out.println("Isi data saat ini : " + buku.getPenerbit());
                break;
            }
        }
    }

    private void getTahunTerbitSaatIni(List<Buku> listBuku) {
        for (Buku buku : listBuku) {
            if (buku.getId() == id) {
                System.out.println("Isi data saat ini : " + buku.getTahunTerbit());
                break;
            }
        }
    }
}
