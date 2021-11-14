/*
Berkas.java | digunakan untuk mengatur semua akses yang menyangkut data, seperti memuat data, menambah data baru menampilkan data, memperbarui data, serta mencari data dari penyimpanan yang berbentuk flat file (*txt).
Semua data yang disimpan dari file akan dimasukkan kedalam ArrayList sebagai penampung data ketika program berjalan yang berfungsi untuk mempermudah menampilkan isi data.
 */


package main.cruddatabuku.util;

import main.cruddatabuku.buku.DataBuku;

import java.io.*;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static main.cruddatabuku.util.Kamus.*;

public interface Berkas {

    static boolean memuatDataBuku(File file, List<DataBuku> listDataBuku) {
        if (file.exists()) {
            if (file.length() != 0) {
                try {
                    if (validasiData(new Scanner(file))) {
                        Scanner readFile = new Scanner(file);
                        while (readFile.hasNextLine()) {
                            String data = readFile.nextLine();
                            listDataBuku.add(createBuku(data));
                        }
                        readFile.close();
                        return true;
                    } else {
                        System.out.println("Terjadi Kesalahan Didalam File!");
                    }
                } catch (FileNotFoundException e) {
                    System.out.println("Error : Terjadi File Tidak Ditemukan!");
                }
            } else {
                System.out.println("Isi File Kosong!");
                return true;
            }
        } else System.out.println("File Tidak Ditemukan!");
        return false;
    }

    static void tampilData(List<DataBuku> listDataBuku) {
        if (!listDataBuku.isEmpty()) {
            int i = 0;
            for (DataBuku buku : listDataBuku) {
                buku.tampil();
                if (i < listDataBuku.size() - 1) {
                    System.out.println();
                }
                i++;
            }
        } else {
            System.out.println("List Data Buku Kosong!");
        }
    }

    static void tampilData(List<DataBuku> listDataBuku, String jenisAtribut, String dicari) {
        List<DataBuku> temp = new ArrayList<>();
        Pattern p = Pattern.compile(dicari, Pattern.CASE_INSENSITIVE);
        Matcher m;
        jenisAtribut = jenisAtribut.toUpperCase();
        if (!listDataBuku.isEmpty()) {
            for (DataBuku buku : listDataBuku) {
                switch (jenisAtribut) {
                    case KODEBUKU:
                        m = p.matcher(buku.getKodeBuku());
                        addTempList(m, buku, temp);
                        break;
                    case JUDULBUKU:
                        m = p.matcher(buku.getJudulBuku());
                        addTempList(m, buku, temp);
                        break;
                    case PENULIS:
                        m = p.matcher(buku.getPenulis());
                        addTempList(m, buku, temp);
                        break;
                    case PENERBIT:
                        m = p.matcher(buku.getPenerbit());
                        addTempList(m, buku, temp);
                        break;
                    case TAHUNTERBIT:
                        m = p.matcher(buku.getTahunTerbit());
                        addTempList(m, buku, temp);
                        break;
                }
            }
        }
        if (!temp.isEmpty()) {
            int i = 0;
            for (DataBuku buku : temp) {
                buku.tampil();
                if (i < temp.size() - 1) {
                    System.out.println();
                }
                i++;
            }
        } else {
            System.out.println("Data Tidak Ditemukan!");
        }
    }

    static void tambahData(File file, List<DataBuku> listDataBuku) {
        Scanner input = new Scanner(System.in);
        int lastID = 0;
        String kodeBuku;
        String judulBuku;
        String penulis;
        String penerbit;
        String tahunTerbit;

        if (!listDataBuku.isEmpty()) {
            lastID = listDataBuku.get(listDataBuku.size() - 1).getId();
        }

        System.out.println("Masukkan Data Buku");

        boolean sameValue = false;
        do {
            kodeBuku = validasiInput("Kode Buku", P_KODEBUKU);
            for (DataBuku buku : listDataBuku) {
                if (buku.getKodeBuku().contentEquals(kodeBuku)) {
                    System.out.println("Kode buku sudah ada!");
                    sameValue = true;
                    break;
                } else {
                    sameValue = false;
                }
            }
        } while (sameValue);
        System.out.print("~ Judul Buku : ");
        judulBuku = input.nextLine();
        System.out.print("~ Penulis : ");
        penulis = input.nextLine();
        System.out.print("~ Penerbit : ");
        penerbit = input.nextLine();
        tahunTerbit = validasiInput("Tahun Terbit", P_TAHUNTERBIT);

        DataBuku dataBuku = new DataBuku(++lastID, kodeBuku, judulBuku, penulis, penerbit, tahunTerbit);

        try {
            FileWriter fileWriter = new FileWriter(file, true);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            bufferedWriter.write((file.length() != 0 ? "\n" : "") + dataBuku.getTxtFormat());
            bufferedWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        listDataBuku.add(dataBuku);
    }

    static void updateData(String fileName, List<DataBuku> listDataBuku, int id, String jenisAtribut, String isiData) {
        jenisAtribut = jenisAtribut.toUpperCase();
        for (DataBuku buku : listDataBuku) {
            if (buku.getId() == id) {
                switch (jenisAtribut) {
                    case KODEBUKU:
                        buku.setKodeBuku(isiData);
                        break;
                    case JUDULBUKU:
                        buku.setJudulBuku(isiData);
                        break;
                    case PENULIS:
                        buku.setPenulis(isiData);
                        break;
                    case PENERBIT:
                        buku.setPenerbit(isiData);
                        break;
                    case TAHUNTERBIT:
                        buku.setTahunTerbit(isiData);
                        break;
                }
            }
        }
        update(fileName, listDataBuku);
    }

    private static void update(String fileName, List<DataBuku> listDataBuku) {
        File file = new File(fileName);
        try {
            FileWriter fileWriter = new FileWriter(file);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            int row = 0;
            for (DataBuku buku : listDataBuku) {
                bufferedWriter.write(
                        ((listDataBuku.size() > 0) && (row != 0) ? "\n" : "") + buku.getTxtFormat()
                );
                row++;
            }
            bufferedWriter.close();
            fileWriter.close();
            printUpdateSuccess();
            new Scanner(System.in).nextLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void addTempList(Matcher m, DataBuku buku, List<DataBuku> list) {
        if (m.find()) {
            list.add(createBuku(buku.getTxtFormat()));
        }
    }

    private static DataBuku createBuku(String data) {
        String[] dataBuku = data.split(String.valueOf(PEMBATAS));
        return (new DataBuku(dataBuku[0], dataBuku[1], dataBuku[2], dataBuku[3], dataBuku[4], dataBuku[5]));
    }

    private static boolean validasiData(Scanner readFile) {
        int minPembatas = 0;
        int maxPembatas = 0;
        int counter;
        int row = 0;
        while (readFile.hasNextLine()) {
            counter = 0;
            String data = readFile.nextLine();
            for (int i = 0; i < data.length(); i++) {
                if (data.charAt(i) == PEMBATAS) {
                    counter++;
                }
            }
            row++;
            if (row == 1) minPembatas = counter;
            if (counter > maxPembatas) {
                maxPembatas = counter;
            } else minPembatas = counter;
            if (minPembatas != maxPembatas) break;
        }
        return (minPembatas == maxPembatas);
    }

    static String validasiInput(String namaInputan) {
        String stringValid = null;
        try {
            Scanner input = new Scanner(System.in);
            System.out.print("~ " + namaInputan + " : ");
            stringValid = input.nextLine();
        } catch (InputMismatchException e) {
            printError();
        }
        return stringValid;
    }

    static String validasiInput(String namaInputan, Pattern pattern) {
        Matcher matcher;
        String stringValid = null;
        Scanner input = new Scanner(System.in);
        try {
            do {
                System.out.print("~ " + namaInputan + " : ");
                stringValid = input.nextLine();
                matcher = pattern.matcher(stringValid);
                if (matcher.matches()) {
                    break;
                } else {
                    System.out.println(namaInputan + " Tidak Valid");
                }
            } while (true);
        } catch (InputMismatchException e) {
            printError();
        }
        return stringValid;
    }

}
