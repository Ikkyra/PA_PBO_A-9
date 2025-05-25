import java.util.InputMismatchException;
import java.util.Scanner;

import database.dataUser;
import entity.Pelanggan;

public class App {
    private static dataUser dataUser = new dataUser();
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        adminMenu();
    }

    private static void adminMenu() {
        System.out.println("Masuk sebagai Admin");
        boolean running = true;

        while (running) {
            System.out.println("\n===== Menu Admin =====");
            System.out.println("1. Tambah Pelanggan");
            System.out.println("2. Lihat List Pelanggan");
            System.out.println("3. Update Pelanggan");
            System.out.println("4. Hapus Pelanggan");
            System.out.println("5. Cari Pelanggan");
            System.out.println("6. Keluar");
            System.out.println("======================");
            System.out.print("Pilih menu: ");

            try {
                int menu = scanner.nextInt();
                scanner.nextLine(); 

                switch (menu) {
                    case 1:
                        tambahPelanggan();
                        break;
                    case 2:
                        lihatPelanggan();
                        break;
                    case 3:
                        updatePelanggan();
                        break;
                    case 4:
                        hapusPelanggan();
                        break;
                    case 5:
                        cariPelanggan();
                        break;
                    case 6:
                        System.out.println("Terima kasih! Keluar dari sistem...");
                        running = false;
                        break;
                    default:
                        System.out.println("Menu tidak tersedia. Silakan pilih 1-6.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Input harus berupa angka! Silakan ulangi.");
                scanner.nextLine(); 
            }
        }
    }

    private static void tambahPelanggan() {
        int pilihan = 0;
        boolean validInput = false;

        while (!validInput) {
            try {
                System.out.println("\nPilih Tipe Pelanggan:");
                System.out.println("1. Biasa");
                System.out.println("2. Reguler");
                System.out.println("3. Premium");
                System.out.print("Pilihan: ");
                pilihan = scanner.nextInt();
                scanner.nextLine();

                if (pilihan >= 1 && pilihan <= 3) {
                    validInput = true;
                } else {
                    System.out.println("Pilihan hanya 1-3. Silakan ulangi.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Input harus berupa angka! Silakan ulangi.");
                scanner.nextLine();
            }
        }

        System.out.print("Masukkan Nama: ");
        String nama = scanner.nextLine();
        while (nama.isEmpty()) {
            System.out.print("Nama tidak boleh kosong. Masukkan Nama: ");
            nama = scanner.nextLine();
        }

        System.out.print("Masukkan Alamat: ");
        String alamat = scanner.nextLine();
        while (alamat.isEmpty()) {
            System.out.print("Alamat tidak boleh kosong. Masukkan Alamat: ");
            alamat = scanner.nextLine();
        }

        System.out.print("Masukkan No Telp: ");
        String noTelp = scanner.nextLine();
        while (noTelp.isEmpty()) {
            System.out.print("No Telp tidak boleh kosong. Masukkan No Telp: ");
            noTelp = scanner.nextLine();
        }

        System.out.print("Masukkan No KTP: ");
        String noKTP = scanner.nextLine();
        while (noKTP.isEmpty()) {
            System.out.print("No KTP tidak boleh kosong. Masukkan No KTP: ");
            noKTP = scanner.nextLine();
        }


        switch (pilihan) {
            case 1:
                dataUser.tambahPelanggan(nama, alamat, noTelp, noKTP);
                break;
            case 2:
                int poin = 0;
                try {
                    System.out.print("Masukkan Poin Awal: ");
                    poin = scanner.nextInt();
                    scanner.nextLine();
                } catch (InputMismatchException e) {
                    System.out.println("Input poin harus angka. Diisi 0 otomatis.");
                    scanner.nextLine();
                }
                dataUser.tambahPelanggan(nama, alamat, noTelp, noKTP, poin);
                break;
            case 3:
                double diskon = 0.0;
                try {
                    System.out.print("Masukkan Diskon (%): ");
                    diskon = scanner.nextDouble();
                    scanner.nextLine();
                } catch (InputMismatchException e) {
                    System.out.println("Input diskon harus angka. Diisi 0 otomatis.");
                    scanner.nextLine();
                }
                dataUser.tambahPelanggan(nama, alamat, noTelp, noKTP, diskon);
                break;
        }

        System.out.println("Pelanggan berhasil ditambahkan.\n");
    }

    private static void lihatPelanggan() {
        if (dataUser.getPelangganList().isEmpty()) {
            System.out.println("Tidak ada data pelanggan.");
            return;
        }

        System.out.println("\n=== Daftar Pelanggan ===");
        for (Pelanggan pelanggan : dataUser.getPelangganList()) {
            System.out.println("-----------------------------------");
            System.out.println(pelanggan.getInfoPelanggan());
        }
    }

    private static void updatePelanggan() {
        System.out.print("Masukkan No KTP pelanggan yang ingin diubah: ");
        String noKTPUpdate = scanner.nextLine();

        Pelanggan pelanggan = dataUser.cariPelanggan(noKTPUpdate);
        if (pelanggan == null) {
            System.out.println("Pelanggan dengan No KTP tersebut tidak ditemukan.");
            return;
        }

        System.out.print("Masukkan Nama Baru (kosongkan jika tidak diubah): ");
        String nama = scanner.nextLine();
        if (!nama.isEmpty()) pelanggan.setNama(nama);

        System.out.print("Masukkan Alamat Baru (kosongkan jika tidak diubah): ");
        String alamat = scanner.nextLine();
        if (!alamat.isEmpty()) pelanggan.setAlamat(alamat);

        System.out.print("Masukkan No Telp Baru (kosongkan jika tidak diubah): ");
        String noTelp = scanner.nextLine();
        if (!noTelp.isEmpty()) pelanggan.setNoTelp(noTelp);


        
        if (pelanggan instanceof entity.PelangganReguler) {
            System.out.print("Masukkan Poin Baru: ");
            try {
                int poinBaru = scanner.nextInt();
                scanner.nextLine();
                ((entity.PelangganReguler) pelanggan).setPoin(poinBaru);
            } catch (InputMismatchException e) {
                System.out.println("Input poin harus berupa angka. Poin tidak diubah.");
                scanner.nextLine();
            }
        } else if (pelanggan instanceof entity.PelangganPremium) {
            System.out.print("Masukkan Diskon Baru (%): ");
            try {
                double diskonBaru = scanner.nextDouble();
                scanner.nextLine();
                ((entity.PelangganPremium) pelanggan).setDiskon(diskonBaru);
            } catch (InputMismatchException e) {
                System.out.println("Input diskon harus berupa angka. Diskon tidak diubah.");
                scanner.nextLine();
            }
        }

        
        dataUser.updatePelanggan(noKTPUpdate, nama, alamat, noTelp);
        System.out.println("Data pelanggan berhasil diperbarui.\n");
    }


    private static void hapusPelanggan() {
        System.out.print("Masukkan No KTP pelanggan yang ingin dihapus: ");
        String noKTPHapus = scanner.nextLine();

        if (!dataUser.cekKTP(noKTPHapus)) {
            System.out.println("Pelanggan tidak ditemukan.");
            return;
        }

        dataUser.hapusPelanggan(noKTPHapus);
        System.out.println("Pelanggan berhasil dihapus.");
    }

    private static void cariPelanggan() {
        System.out.print("Masukkan No KTP pelanggan: ");
        String noKTP = scanner.nextLine();

        Pelanggan pelanggan = dataUser.cariPelanggan(noKTP);
        if (pelanggan == null) {
            System.out.println("Pelanggan tidak ditemukan.");
        } else {
            System.out.println("\nData Pelanggan:");
            System.out.println(pelanggan.getInfoPelanggan());
        }
    }
}
