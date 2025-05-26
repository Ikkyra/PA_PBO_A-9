import database.dataUser;
import entity.Pelanggan;
import java.util.InputMismatchException;
import java.util.Scanner;

public class App {
    private static dataUser dataUser = new dataUser();
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        dataUser.loadFromCSV(); // Load data from CSV when starting
        adminMenu();
    }

    private static void adminMenu() {
        clearScreen();
        System.out.println("+-----------------------------------+");
        System.out.println("|     MASUK SEBAGAI ADMIN           |");
        System.out.println("+-----------------------------------+");
        boolean running = true;

        while (running) {
            System.out.println("+-----------------------------------+");
            System.out.println("|          MENU UTAMA               |");
            System.out.println("+-----------------------------------+");
            System.out.println("|   1. Tambah Pelanggan             |");
            System.out.println("|   2. Lihat Pelanggan              |");
            System.out.println("|   3. Update Pelanggan             |");
            System.out.println("|   4. Hapus Pelanggan              |");
            System.out.println("|   5. Cari Pelanggan               |");
            System.out.println("|   0. Keluar                       |");
            System.out.println("+-----------------------------------+");
            System.out.print("Pilihan Anda [0-5]: ");

            try {
                int menu = scanner.nextInt();
                scanner.nextLine();

                switch (menu) {
                    case 1 -> {
                        clearScreen();
                        tambahPelanggan();
                        delayClearScreen();
                    }
                    case 2 -> {
                        clearScreen();
                        lihatPelanggan();
                    }
                    case 3 -> {
                        clearScreen();
                        updatePelanggan();
                        delayClearScreen();
                    }
                    case 4 -> {
                        clearScreen();
                        hapusPelanggan();
                        delayClearScreen();
                    }
                    case 5 -> {
                        clearScreen();
                        cariPelanggan();
                    }
                    case 0 -> {
                        System.out.println("Terima kasih! Keluar dari sistem...");
                        running = false;
                    }
                    default -> System.out.println("Menu tidak tersedia. Silakan pilih 1-6.");
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
            System.out.println("\n==========[ TIPE MEMBERSHIPS ]==============");
            System.out.println("     [1] Biasa  [2] Reguler  [3] Premium"    );
            System.out.println("============================================");
            System.out.print("Pilih Jenis Pelanggan: ");
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

    String noTelp = inputNoTelp();

    String noKTP;
    while (true) {
        noKTP = inputNoKTP();

        if (dataUser.cekKTP(noKTP)) {
            System.out.println("No KTP sudah terdaftar. Silakan masukkan KTP lain.");
        } else {
            break;
        }
    }

    switch (pilihan) {
        case 1 -> dataUser.tambahPelanggan(nama, alamat, noTelp, noKTP);
        case 2 -> {
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
        }
        case 3 -> {
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
        }
    }
    // dataUser.saveToCSV("dbUser.csv"); 
    System.out.println("Pelanggan berhasil ditambahkan.\n");
}


    private static void lihatPelanggan() {
        if (dataUser.getPelangganList().isEmpty()) {
            System.out.println("Tidak ada data pelanggan.");
            return;
        }

        System.out.println("\n========= DAFTAR PELANGGAN =========");
        for (Pelanggan pelanggan : dataUser.getPelangganList()) {
            System.out.println("-----------------------------------");
            System.out.println(pelanggan.getInfoPelanggan());
        }
    }

    private static void updatePelanggan() {
        String noKTPUpdate = inputNoKTP();
        Pelanggan pelanggan = dataUser.cariPelanggan(noKTPUpdate);

        if (pelanggan == null) {
            System.out.println("Pelanggan dengan No KTP tersebut tidak ditemukan.");
            return;
        }

        System.out.println("=== UPDATE DATA PELANGGAN ===");
        System.out.println("Biarkan kosong jika tidak ingin mengubah data tertentu.");

        System.out.print("Masukkan Nama Baru: ");
        String nama = scanner.nextLine();
        if (nama.isEmpty()) nama = pelanggan.getNama();

        System.out.print("Masukkan Alamat Baru: ");
        String alamat = scanner.nextLine();
        if (alamat.isEmpty()) alamat = pelanggan.getAlamat();

        System.out.print("Masukkan No Telp Baru: ");
        String noTelp = scanner.nextLine();
        if (noTelp.isEmpty()) noTelp = pelanggan.getNoTelp();
        else {
            while (!noTelp.matches("\\d+")) {
                System.out.print("No Telp harus angka. Masukkan lagi: ");
                noTelp = scanner.nextLine();
            }
        }

        System.out.println("Pilih Jenis Memberships Baru:");
        System.out.println("\n==========[ TIPE MEMBERSHIPS ]==============");
        System.out.println("     [1] Biasa  [2] Reguler  [3] Premium"    );
        System.out.println("============================================");
        int pilihan;
        while (true) {
            try {
                System.out.print("Pilihan (1-3): ");
                pilihan = scanner.nextInt();
                scanner.nextLine();
                if (pilihan >= 1 && pilihan <= 3) break;
                else System.out.println("Pilihan hanya 1-3.");
            } catch (InputMismatchException e) {
                System.out.println("Input harus angka.");
                scanner.nextLine();
            }
        }

        dataUser.hapusPelanggan(noKTPUpdate);

        switch (pilihan) {
            case 1 -> dataUser.tambahPelanggan(nama, alamat, noTelp, noKTPUpdate);
            case 2 -> {
                int poin = 0;
                System.out.print("Masukkan Poin: ");
                try {
                    poin = Integer.parseInt(scanner.nextLine());
                } catch (NumberFormatException e) {
                    System.out.println("Input salah. Poin diset ke 0.");
                }
                dataUser.tambahPelanggan(nama, alamat, noTelp, noKTPUpdate, poin);
            }
            case 3 -> {
                double diskon = 0.0;
                System.out.print("Masukkan Diskon (%): ");
                try {
                    diskon = Double.parseDouble(scanner.nextLine());
                } catch (NumberFormatException e) {
                    System.out.println("Input salah. Diskon diset ke 0.");
                }
                dataUser.tambahPelanggan(nama, alamat, noTelp, noKTPUpdate, diskon);
            }
        }

        System.out.println("Data pelanggan berhasil diperbarui & role berhasil diubah.\n");
    }

    private static void hapusPelanggan() {
        String noKTPHapus = inputNoKTP();

        if (!dataUser.cekKTP(noKTPHapus)) {
            System.out.println("Pelanggan tidak ditemukan.");
            return;
        }

        dataUser.hapusPelanggan(noKTPHapus);
        System.out.println("Pelanggan berhasil dihapus.");
    }

    private static void cariPelanggan() {
        String noKTP = inputNoKTP();
        Pelanggan pelanggan = dataUser.cariPelanggan(noKTP);

        if (pelanggan == null) {
            System.out.println("Pelanggan tidak ditemukan.");
        } else {
            System.out.println("\nDATA PELANGGAN:");
            System.out.println(pelanggan.getInfoPelanggan());
        }
    }

    private static void clearScreen() {
        try {
            if (System.getProperty("os.name").contains("Windows")) {
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            } else {
                System.out.print("\033[H\033[2J");
                System.out.flush();
            }
        } catch (Exception e) {
            System.out.println("Gagal membersihkan layar.");
        }
    }

    private static void delayClearScreen() {
        try {
            Thread.sleep(3000);
            clearScreen();
        } catch (InterruptedException e) {
            System.out.println("Terjadi gangguan saat menunggu.");
        }
    }

    private static String inputNoKTP() {
        String noKTP;
        while (true) {
            System.out.print("Masukkan No KTP (16 digit): ");
            noKTP = scanner.nextLine();
            if (noKTP.matches("\\d{16}")) {
                break;
            } else {
                System.out.println("No KTP harus 16 digit angka. Silakan ulangi.");
            }
        }
        return noKTP;
    }

    private static String inputNoTelp() {
        String noTelp;
        while (true) {
            System.out.print("Masukkan No Telp: ");
            noTelp = scanner.nextLine();
            if (noTelp.matches("\\d+")) {
                break;
            } else {
                System.out.println("No Telp harus berupa angka. Silakan ulangi.");
            }
        }
        return noTelp;
    }
}



