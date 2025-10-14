import java.util.ArrayList;
import java.util.List;

public class VendingMachine {
    private Stok stok;
    private List<Minuman> daftarMinuman;
    
    private final int GULA_PER_LEVEL = 5;
    private final int SUSU_PER_LEVEL = 15;

    public VendingMachine() {
        this.stok = new Stok();
        this.daftarMinuman = new ArrayList<>();
        
        daftarMinuman.add(new Minuman("Espresso", 7000, 9000));
        daftarMinuman.add(new Minuman("Americano", 8000, 10000));
        daftarMinuman.add(new Minuman("Flat White", 12000, 14000));
        daftarMinuman.add(new Minuman("Latte", 10000, 12000));
        daftarMinuman.add(new Minuman("Cappuccino", 11000, 13000));
        daftarMinuman.add(new Minuman("Caramel Macchiato", 14000, 16000));
        daftarMinuman.add(new Minuman("Mochaccino", 12000, 14000));
        daftarMinuman.add(new Minuman("Kopi Tubruk", 5000, 7000));
        daftarMinuman.add(new Minuman("Hot Chocolate", 10000, 12000));
        daftarMinuman.add(new Minuman("Matcha Latte", 13000, 15000));
    }

    public void tampilkanMenu() {
        System.out.println("\n===== Selamat Datang di Kopag Coffee Maker =====");
        System.out.println("\nMENU TERSEDIA:");
        int i = 1;
        for (Minuman m : daftarMinuman) {
            System.out.println(i + ". " + m.getNama());
            
            String statusKecil;
            if (!cekStokDasar(m.getNama(), "Kecil")) statusKecil = "[Stok Habis]";
            else if (stok.cekStokKritis(m.getNama(), "Kecil")) statusKecil = "[Segera Habis]";
            else statusKecil = "[Tersedia]";
            System.out.println("   - Kecil: Rp " + m.getHarga("Kecil") + " " + statusKecil);
            
            String statusBesar;
            if (!cekStokDasar(m.getNama(), "Besar")) statusBesar = "[Stok Habis]";
            else if (stok.cekStokKritis(m.getNama(), "Besar")) statusBesar = "[Segera Habis]";
            else statusBesar = "[Tersedia]";
            System.out.println("   - Besar: Rp " + m.getHarga("Besar") + " " + statusBesar);
            i++;
        }
        System.out.println("\nOpsi Tambahan (GRATIS):");
        System.out.println("- Gula/Susu: Level 0 (Tidak), 1 (Sedikit), 2 (Sedang), 3 (Banyak)");
    }
    
    private boolean cekStokDasar(String namaMinuman, String ukuran) {
        if (namaMinuman.equalsIgnoreCase("Hot Chocolate")) return stok.cekStok(ukuran, 0, 0, 0, 0, 15, 0, 0, 0);
        else if (namaMinuman.equalsIgnoreCase("Matcha Latte")) return stok.cekStok(ukuran, 0, 0, 0, 0, 0, 15, 0, 0);
        else return stok.cekStok(ukuran, 10, 0, 0, 0, 0, 0, 0, 0);
    }

    public void buatPesanan(String namaMinuman, String ukuran, int levelGula, int levelSusu) {
        System.out.println("Memesan: " + namaMinuman + " (" + ukuran + ")");
        System.out.println("- Level Gula: " + levelGula + ", Level Susu: " + levelSusu);
        System.out.println("----------------------------------------");

        int butuhKopi = 0, butuhAir = 0, butuhCoklat = 0, butuhMatcha = 0, butuhVanila = 0, butuhKaramel = 0;
        int butuhGula = levelGula * GULA_PER_LEVEL, butuhSusuTambahan = levelSusu * SUSU_PER_LEVEL;
        int porsiDasar = ukuran.equalsIgnoreCase("Besar") ? 2 : 1;

        switch (namaMinuman.toLowerCase()) {
            case "espresso": case "kopi tubruk": butuhKopi = 10 * porsiDasar; butuhAir = 30 * porsiDasar; break;
            case "americano": butuhKopi = 10 * porsiDasar; butuhAir = 80 * porsiDasar; break;
            case "flat white": case "latte": case "cappuccino": butuhKopi = 10 * porsiDasar; butuhSusuTambahan += (40 * porsiDasar); break;
            case "caramel macchiato": butuhKopi = 10 * porsiDasar; butuhSusuTambahan += (30 * porsiDasar); butuhVanila = 5 * porsiDasar; butuhKaramel = 5 * porsiDasar; break;
            case "mochaccino": butuhKopi = 10 * porsiDasar; butuhSusuTambahan += (30 * porsiDasar); butuhCoklat = 10 * porsiDasar; break;
            case "hot chocolate": butuhCoklat = 15 * porsiDasar; butuhSusuTambahan += (40 * porsiDasar); break;
            case "matcha latte": butuhMatcha = 15 * porsiDasar; butuhSusuTambahan += (40 * porsiDasar); break;
            default: System.out.println("Minuman tidak dikenali."); return;
        }

        if (!stok.cekStok(ukuran, butuhKopi, butuhGula, butuhSusuTambahan, butuhAir, butuhCoklat, butuhMatcha, butuhVanila, butuhKaramel)) {
            System.out.println("Pemeriksaan stok... GAGAL. Maaf, bahan tidak mencukupi.");
            return;
        }
        System.out.println("Pemeriksaan stok... Sukses. Bahan tersedia.");
        
        Minuman minumanPilihan = daftarMinuman.stream().filter(m -> m.getNama().equalsIgnoreCase(namaMinuman)).findFirst().orElse(null);
        if (minumanPilihan == null) { System.out.println("Minuman tidak ditemukan."); return; }
        int totalHarga = minumanPilihan.getHarga(ukuran);
        System.out.println("Total harga: Rp " + totalHarga);
        System.out.println("Silakan pindai QRIS untuk membayar...");
        System.out.println("Pembayaran QRIS berhasil!");

        stok.kurangiStok(ukuran, butuhKopi, butuhGula, butuhSusuTambahan, butuhAir, butuhCoklat, butuhMatcha, butuhVanila, butuhKaramel);
        System.out.println("Minuman " + namaMinuman + " Anda sedang dibuat...");
        System.out.println("Silakan nikmati minuman Anda! ☕");
        System.out.println("LOG: Transaksi berhasil - Minuman: " + namaMinuman + ", Ukuran: " + ukuran + ", Total: Rp " + totalHarga);

        stok.periksaDanCetakStokKritisAdmin();
    }
}