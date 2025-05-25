package entity;

public class PelangganPremium extends Pelanggan implements MemberBenefit {
    private double diskon;

    public PelangganPremium(String nama, String alamat, String noTelp, String noKTP, double diskon) {
        super(nama, alamat, noTelp, noKTP);
        this.diskon = diskon;
    }

    public double getDiskon() { return diskon; }
    public void setDiskon(double diskon) { this.diskon = diskon; }

    @Override
    public String getTipePelanggan() {
        return "Premium";
    }

    @Override
    public double hitungBenefit() {
        return diskon;
    }

    @Override
    public String getDeskripsiBenefit() {
        return "Diskon sebesar " + diskon + "% untuk semua transaksi.";
    }

    @Override
    public double hitungDiskon(double totalBayar) {
        return totalBayar * (diskon / 100);
    }

    @Override
    public String getInfoPelanggan() {
        return super.getInfoPelanggan() + "\nDiskon: " + diskon + "%" + "\nBenefit: " + getDeskripsiBenefit();
    }
}
