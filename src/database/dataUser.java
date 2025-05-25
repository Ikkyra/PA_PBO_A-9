package database;

import entity.*;
import java.util.ArrayList;
import java.util.List;

public class dataUser {
    private List<Pelanggan> pelangganList = new ArrayList<>();

    public List<Pelanggan> getPelangganList() {
        return pelangganList;
    }

    public void tambahPelanggan(String nama, String alamat, String noTelp, String noKTP) {
        pelangganList.add(new PelangganBiasa(nama, alamat, noTelp, noKTP));
    }

    public void tambahPelanggan(String nama, String alamat, String noTelp, String noKTP, int poin) {
        pelangganList.add(new PelangganReguler(nama, alamat, noTelp, noKTP, poin));
    }

    public void tambahPelanggan(String nama, String alamat, String noTelp, String noKTP, double diskon) {
        pelangganList.add(new PelangganPremium(nama, alamat, noTelp, noKTP, diskon));
    }

    public void updatePelanggan(String noKTP, String namaBaru, String alamatBaru, String noTelpBaru) {
        for (Pelanggan p : pelangganList) {
            if (p.getNoKTP().equals(noKTP)) {
                p.setNama(namaBaru);
                p.setAlamat(alamatBaru);
                p.setNoTelp(noTelpBaru);
                return;
            }
        }
    }

    public void hapusPelanggan(String noKTP) {
        pelangganList.removeIf(p -> p.getNoKTP().equals(noKTP));
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
}
