package entity;

public class PelangganBiasa extends Pelanggan {
    public PelangganBiasa(String nama, String alamat, String noTelp, String noKTP) {
        super(nama, alamat, noTelp, noKTP);
    }

    @Override
    public String getTipePelanggan() {
        return "Biasa";
    }
}
