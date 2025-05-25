package entity;

public abstract class Pelanggan {
    private String nama, alamat, noTelp;
    private final String noKTP;

    public Pelanggan(String nama, String alamat, String noTelp, String noKTP){
        this.nama = nama;
        this.alamat = alamat;
        this.noTelp = noTelp;
        this.noKTP = noKTP;
    }

    public void setNama(String nama) { this.nama = nama; }
    public void setAlamat(String alamat) { this.alamat = alamat; }
    public void setNoTelp(String noTelp) { this.noTelp = noTelp; }

    public String getNama() { return nama; }
    public String getAlamat() { return alamat; }
    public String getNoTelp() { return noTelp; }
    public final String getNoKTP() { return noKTP; }

    public String getInfoPelanggan() {
        return "Tipe: " + getTipePelanggan() + "\nNama: " + nama + "\nAlamat: " + alamat +
                "\nNo Telp: " + noTelp + "\nNo KTP: " + noKTP;
    }

    public abstract String getTipePelanggan();
}
