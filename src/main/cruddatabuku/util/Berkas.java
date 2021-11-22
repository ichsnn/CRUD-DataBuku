/*
Berkas.java | digunakan untuk mengatur semua akses yang menyangkut data, seperti memuat data, menambah data baru menampilkan data, memperbarui data, serta mencari data dari penyimpanan yang berbentuk flat file (*txt).
Semua data yang disimpan dari file akan dimasukkan kedalam ArrayList sebagai penampung data ketika program berjalan yang berfungsi untuk mempermudah menampilkan isi data.
 */


package main.cruddatabuku.util;

import main.cruddatabuku.buku.Buku;

import java.io.*;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static main.cruddatabuku.util.Library.*;

public interface Berkas {

    // memuat data buku dari flat file kemudian disimpan kedalam memori berbentuk arrayList
    static boolean memuatDataBuku(File file, List<Buku> listBuku) {
        if (file.exists()) {
            if (file.length() != 0) {
                try {
                    if (validasiData(new Scanner(file))) {
                        Scanner readFile = new Scanner(file);
                        while (readFile.hasNextLine()) {
                            String data = readFile.nextLine();
                            Buku loadBuku = createBuku(data);
                            listBuku.add(loadBuku);
                            if (loadBuku == null) {
                                return false;
                            }
                        }
                        readFile.close();
                        return true;
                    } else {
                        System.out.println("Terjadi Kesalahan Didalam File!");
                        return false;
                    }
                } catch (FileNotFoundException e) {
                    System.out.println("Error : File Tidak Ditemukan!");
                    return false;
                }
            } else {
                System.out.println("Isi File Kosong!");
                return true;
            }
        } else System.out.println("File Tidak Ditemukan!");
        return false;
    }

    // menampilkan list data buku dari arrayList yang sudah dibuat
    static void tampilData(List<Buku> listBuku) {
        if (!listBuku.isEmpty()) {
            int i = 0;
            for (Buku buku : listBuku) {
                buku.tampil();
                if (i < listBuku.size() - 1) {
                    System.out.println();
                }
                i++;
            }
        } else {
            System.out.println("List Data Buku Kosong!");
        }
    }

    // menampilkan list data buku sesuai dengan apa yang dicari
    static void tampilData(List<Buku> listBuku, String jenisAtribut, String dicari) {
        List<Buku> temp = new ArrayList<>();
        Pattern p = Pattern.compile(dicari, Pattern.CASE_INSENSITIVE);
        Matcher m;
        jenisAtribut = jenisAtribut.toUpperCase();
        if (!listBuku.isEmpty()) {
            for (Buku buku : listBuku) {
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
            for (Buku buku : temp) {
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

    // menambah data buku dengan menuliskan kedalam flat file serta menyimpannya kedalam arrayList ketika program berjalan
    static void tambahData(File file, List<Buku> listBuku) {
        Scanner input = new Scanner(System.in);
        int lastID = 0;
        String kodeBuku;
        String judulBuku;
        String penulis;
        String penerbit;
        String tahunTerbit;

        if (!listBuku.isEmpty()) {
            lastID = listBuku.get(listBuku.size() - 1).getId();
        }

        System.out.println("Masukkan Data Buku");

        boolean sameValue = false;
        do {
            kodeBuku = validasiInput("Kode Buku", P_KODEBUKU);
            for (Buku buku : listBuku) {
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

        Buku buku = new Buku(++lastID, kodeBuku, judulBuku, penulis, penerbit, tahunTerbit);

        try {
            FileWriter fileWriter = new FileWriter(file, true);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            bufferedWriter.write((file.length() != 0 ? "\n" : "") + buku.getTxtFormat());
            bufferedWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        listBuku.add(buku);
    }

    // mengupdate data buku, sama seperti fungsi tambahData() untuk prosesnya dan mengupdate data buku sesuai jenis atribut apa yang ingin diupdate
    static void updateData(String fileName, List<Buku> listBuku, int id, String jenisAtribut, String isiData) {
        jenisAtribut = jenisAtribut.toUpperCase();
        for (Buku buku : listBuku) {
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
        update(fileName, listBuku);
    }

    // digunakan untuk fungsi updateData() dan juga pada UpdateBuku yang ada package gui
    // fungsi ini untuk menuliskan data yang diupdate pada flat file
    static void update(String fileName, List<Buku> listBuku) {
        File file = new File(fileName);
        try {
            FileWriter fileWriter = new FileWriter(file);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            int row = 0;
            for (Buku buku : listBuku) {
                bufferedWriter.write(
                        ((listBuku.size() > 0) && (row != 0) ? "\n" : "") + buku.getTxtFormat()
                );
                row++;
            }
            bufferedWriter.close();
            fileWriter.close();
            printUpdateSuccess();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // digunakan untuk menghapus data pada listBuku dan pada flat file sesuai nomor id yang ingin dihapus
    static void deleteData(String fileName, List<Buku> listBuku, int id) {
        int listIndex = 0;
        boolean ketemu = false;
        for (Buku buku : listBuku) {
            if (buku.getId() == id) {
                ketemu = true;
                break;
            }
            listIndex++;
        }

        if (ketemu) {
            listBuku.remove(listIndex);
            update(fileName, listBuku);
        } else {
            printIdNotFound();
        }
    }

    // digunakan untuk menghapus semua data.
    static void deleteSemuaData(String fileName, List<Buku> listBuku) {
        if (listBuku.isEmpty()) {
            System.out.print("Isi file kosong!");
            new Scanner(System.in).nextLine();
        } else {
            listBuku.clear();
            update(fileName, listBuku);
        }
    }

    // digunakan untuk membuat list sementara (temporary) yang menampung data yang sesuai dengan matcher nya
    static void addTempList(Matcher m, Buku buku, List<Buku> list) {
        if (m.find()) {
            list.add(createBuku(buku.getTxtFormat()));
        }
    }

    // digunakan untuk membuat objek buku dari flat file. lihat fungsi memuatDataBuku()
    static Buku createBuku(String data) {
        String[] dataBuku = data.split(String.valueOf(PEMBATAS));
        if (dataBuku.length == 6) {
            return (new Buku(dataBuku[0], dataBuku[1], dataBuku[2], dataBuku[3], dataBuku[4], dataBuku[5]));
        }
        return null;
    }

    // digunakan untuk memvalidasi data dari flat file jika ada data yang tidak sesuai baik pemisah nya atau jumlah data nya
    static boolean validasiData(Scanner readFile) {
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

    // digunakan pada tampilan console
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

    // digunakan untuk tampilan console, menggunakan pattern untuk menentukan inputan valid atau tidak
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
