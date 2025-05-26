package database;

import entity.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class dataUser {
    private List<Pelanggan> pelangganList = new ArrayList<>();
    private static final String CSV_FILE = "database/dbUser.csv";

    public List<Pelanggan> getPelangganList() {
        return pelangganList;
    }

    public void tambahPelanggan(String nama, String alamat, String noTelp, String noKTP) {
        pelangganList.add(new PelangganBiasa(nama, alamat, noTelp, noKTP));
        saveToCSV();
    }

    public void tambahPelanggan(String nama, String alamat, String noTelp, String noKTP, int poin) {
        pelangganList.add(new PelangganReguler(nama, alamat, noTelp, noKTP, poin));
        saveToCSV();
    }

    public void tambahPelanggan(String nama, String alamat, String noTelp, String noKTP, double diskon) {
        pelangganList.add(new PelangganPremium(nama, alamat, noTelp, noKTP, diskon));
        saveToCSV();
    }

    public void updatePelanggan(String noKTP, String namaBaru, String alamatBaru, String noTelpBaru) {
        for (Pelanggan p : pelangganList) {
            if (p.getNoKTP().equals(noKTP)) {
                p.setNama(namaBaru);
                p.setAlamat(alamatBaru);
                p.setNoTelp(noTelpBaru);
                saveToCSV();
                return;
            }
        }
    }

    public void hapusPelanggan(String noKTP) {
        pelangganList.removeIf(p -> p.getNoKTP().equals(noKTP));
        saveToCSV();
    }

    public Pelanggan cariPelanggan(String noKTP) {
        for (Pelanggan p : pelangganList) {
            if (p.getNoKTP().equals(noKTP)) {
                return p;
            }
        }
        return null;
    }

    public boolean cekKTP(String noKTP) {
        return pelangganList.stream().anyMatch(p -> p.getNoKTP().equals(noKTP));
    }

    public void saveToCSV() {
        try (PrintWriter writer = new PrintWriter(new FileWriter(CSV_FILE))) {
            writer.println("tipe,nama,alamat,noTelp,noKTP,poin/diskon");
            
            for (Pelanggan pelanggan : pelangganList) {
                String line = pelanggan.getTipePelanggan() + "," +
                             pelanggan.getNama() + "," +
                             pelanggan.getAlamat() + "," +
                             pelanggan.getNoTelp() + "," +
                             pelanggan.getNoKTP() + ",";
                
                if (pelanggan instanceof PelangganReguler) {
                    line += ((PelangganReguler) pelanggan).getPoin();
                } else if (pelanggan instanceof PelangganPremium) {
                    line += ((PelangganPremium) pelanggan).getDiskon();
                } else {
                    line += "0";
                }
                
                writer.println(line);
            }
        } catch (IOException e) {
            System.out.println("Gagal menyimpan data ke CSV: " + e.getMessage());
        }
    }

    public void loadFromCSV() {
        pelangganList.clear();
        try (BufferedReader reader = new BufferedReader(new FileReader(CSV_FILE))) {
            // Skip header
            reader.readLine();
            
            String line;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");
                if (data.length < 6) continue;
                
                String tipe = data[0];
                String nama = data[1];
                String alamat = data[2];
                String noTelp = data[3];
                String noKTP = data[4];
                String benefit = data[5];
                
                switch (tipe) {
                    case "Biasa":
                        tambahPelanggan(nama, alamat, noTelp, noKTP);
                        break;
                    case "Reguler":
                        int poin = Integer.parseInt(benefit);
                        tambahPelanggan(nama, alamat, noTelp, noKTP, poin);
                        break;
                    case "Premium":
                        double diskon = Double.parseDouble(benefit);
                        tambahPelanggan(nama, alamat, noTelp, noKTP, diskon);
                        break;
                }
            }
        } catch (IOException e) {
            System.out.println("Gagal memuat data dari CSV: " + e.getMessage());
        }
    }
}