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
            System.out.println("|   6. Keluar                       |");
            System.out.println("+-----------------------------------+");
            System.out.print("Pilihan Anda [1-6]: ");

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
                    case 6 -> {
                        System.out.println("Terima kasih! Keluar dari sistem...");
                        running = false;
                    }
                    default -> System.out.println("Menu tidak tersedia. Silakan pilih 0-5.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Input harus berupa angka! Silakan ulangi.");
                scanner.nextLine();
                delayClearScreen();
            }
        }
    }

    private static void tambahPelanggan() {
        int pilihan = 0;
        boolean validInput = false;

        while (!validInput) {
            try {
                System.out.println("\n==========[ TIPE MEMBERSHIPS ]==============");
                System.out.println("     [1] Biasa  [2] Reguler  [3] Premium");
                System.out.println("============================================");
                System.out.print("Pilih Jenis Pelanggan: ");
                String input = scanner.nextLine();
                if (isKembali(input)) return;
                pilihan = Integer.parseInt(input);

                if (pilihan >= 1 && pilihan <= 3) {
                    validInput = true;
                } else {
                    System.out.println("Pilihan hanya 1-3. Silakan ulangi.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Input harus berupa angka! Silakan ulangi.");
            }
        }

        String nama = inputTanpaKoma("Nama");
        if (nama == null) return;

        String alamat = inputTanpaKoma("Alamat");
        if (alamat == null) return;

        String noTelp = inputNoTelp();
        if (noTelp == null) return;

        String noKTP;
        while (true) {
            noKTP = inputNoKTP();
            if (noKTP == null) return;

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
                    String input = scanner.nextLine();
                    if (isKembali(input)) return;
                    poin = Integer.parseInt(input);
                } catch (NumberFormatException e) {
                    System.out.println("Input poin salah. Diisi 0 otomatis.");
                }
                dataUser.tambahPelanggan(nama, alamat, noTelp, noKTP, poin);
            }
            case 3 -> {
                double diskon = 0.0;
                try {
                    System.out.print("Masukkan Diskon (%): ");
                    String input = scanner.nextLine();
                    if (isKembali(input)) return;
                    diskon = Double.parseDouble(input);
                } catch (NumberFormatException e) {
                    System.out.println("Input diskon salah. Diisi 0 otomatis.");
                }
                dataUser.tambahPelanggan(nama, alamat, noTelp, noKTP, diskon);
            }
        }
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
        if (noKTPUpdate == null) return;

        Pelanggan pelanggan = dataUser.cariPelanggan(noKTPUpdate);
        if (pelanggan == null) {
            System.out.println("Pelanggan dengan No KTP tersebut tidak ditemukan.");
            return;
        }

        System.out.println("=== UPDATE DATA PELANGGAN ===");
        System.out.println("Ketik KEMBALI untuk membatalkan.");

        String nama = inputTanpaKoma("Nama Baru (Kosong jika tidak diubah)");
        if (nama == null) return;
        if (nama.isEmpty()) nama = pelanggan.getNama();

        String alamat = inputTanpaKoma("Alamat Baru (Kosong jika tidak diubah)");
        if (alamat == null) return;
        if (alamat.isEmpty()) alamat = pelanggan.getAlamat();

        System.out.print("Masukkan No Telp Baru: ");
        String noTelp = scanner.nextLine();
        if (isKembali(noTelp)) return;
        if (noTelp.isEmpty()) noTelp = pelanggan.getNoTelp();
        else while (!noTelp.matches("\\d+")) {
            System.out.print("No Telp harus angka. Masukkan lagi: ");
            noTelp = scanner.nextLine();
            if (isKembali(noTelp)) return;
        }

        System.out.println("Pilih Jenis Memberships Baru:");
        System.out.println("\n==========[ TIPE MEMBERSHIPS ]==============");
        System.out.println("     [1] Biasa  [2] Reguler  [3] Premium");
        System.out.println("============================================");
        int pilihan;
        while (true) {
            try {
                System.out.print("Pilihan (1-3): ");
                String input = scanner.nextLine();
                if (isKembali(input)) return;
                pilihan = Integer.parseInt(input);
                if (pilihan >= 1 && pilihan <= 3) break;
                else System.out.println("Pilihan hanya 1-3.");
            } catch (NumberFormatException e) {
                System.out.println("Input harus angka.");
            }
        }

        dataUser.hapusPelanggan(noKTPUpdate);
        switch (pilihan) {
            case 1 -> dataUser.tambahPelanggan(nama, alamat, noTelp, noKTPUpdate);
            case 2 -> {
                System.out.print("Masukkan Poin: ");
                int poin = 0;
                try {
                    String input = scanner.nextLine();
                    if (isKembali(input)) return;
                    poin = Integer.parseInt(input);
                } catch (NumberFormatException e) {
                    System.out.println("Input salah. Poin diset ke 0.");
                }
                dataUser.tambahPelanggan(nama, alamat, noTelp, noKTPUpdate, poin);
            }
            case 3 -> {
                System.out.print("Masukkan Diskon (%): ");
                double diskon = 0.0;
                try {
                    String input = scanner.nextLine();
                    if (isKembali(input)) return;
                    diskon = Double.parseDouble(input);
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
        if (noKTPHapus == null) return;

        if (!dataUser.cekKTP(noKTPHapus)) {
            System.out.println("Pelanggan tidak ditemukan.");
            return;
        }

        dataUser.hapusPelanggan(noKTPHapus);
        System.out.println("Pelanggan berhasil dihapus.");
    }

    private static void cariPelanggan() {
        String noKTP = inputNoKTP();
        if (noKTP == null) return;

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
            Thread.sleep(1500);
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
            if (isKembali(noKTP)) return null;
            if (noKTP.matches("\\d{16}")) break;
            else System.out.println("No KTP harus 16 digit angka. Silakan ulangi.");
        }
        return noKTP;
    }

    private static String inputNoTelp() {
        String noTelp;
        while (true) {
            System.out.print("Masukkan No Telp: ");
            noTelp = scanner.nextLine();
            if (isKembali(noTelp)) return null;
            if (noTelp.matches("\\d+")) break;
            else System.out.println("No Telp harus berupa angka. Silakan ulangi.");
        }
        return noTelp;
    }

     private static String inputTanpaKoma(String label) {
        String input;
        while (true) {
            System.out.print("Masukkan " + label + ": ");
            input = scanner.nextLine();
            if (isKembali(input)) return null;
            if (input.trim().isEmpty()) {
                System.out.println(label + " tidak boleh kosong atau spasi/tab. Silakan ulangi.");
            } else if (input.matches(".*[\\p{Cntrl}\\p{So}\\p{Sc}#@!$%^&*()_+=\\[\\]{};:'\"<>?/\\\\|`~].*")) {
                System.out.println(label + " tidak boleh mengandung karakter khusus atau emoji. Silakan ulangi.");
            } else if (input.contains(",")) {
                System.out.println(label + " tidak boleh mengandung koma (,). Silakan ulangi.");
            } else {
                return input;
            }
        }
    }

    private static boolean isKembali(String input) {
        return input != null && input.equalsIgnoreCase("KEMBALI");
    }
    
}