package entity;

public class PelangganReguler extends Pelanggan implements MemberBenefit {
    private int poin;

    public PelangganReguler(String nama, String alamat, String noTelp, String noKTP, int poin) {
        super(nama, alamat, noTelp, noKTP);
        this.poin = poin;
    }

    @Override
    public String getTipePelanggan() {
        return "Reguler";
    }

    public int getPoin() { return poin; }
    public void setPoin(int poin) { this.poin = poin; }

    @Override
    public double hitungBenefit() {
        return poin * 0.1;
    }

    @Override
    public String getDeskripsiBenefit() {
        return "Potongan harga sebesar Rp " + hitungBenefit();
    }

    @Override
    public double hitungDiskon(double totalBayar) {
        return Math.min(hitungBenefit(), totalBayar);
    }

    @Override
    public String getInfoPelanggan() {
        return super.getInfoPelanggan() + "\nPoin: " + poin + "\nBenefit: " + getDeskripsiBenefit();
    }
}
