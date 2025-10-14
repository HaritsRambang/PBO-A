/**
 @Leyan Harits
 */

public class Stok {
    // Stok utama
    private int kopiBubuk, gula, susu, air, gelasKecil, gelasBesar;
    
    // Stok bahan baru untuk variasi menu
    private int coklatBubuk, matchaBubuk, sirupVanila, sirupKaramel;

    public Stok() {
        this.kopiBubuk = 200; this.gula = 200; this.susu = 500;
        this.air = 1000; this.gelasKecil = 20; this.gelasBesar = 20;
        this.coklatBubuk = 100; this.matchaBubuk = 100;
        this.sirupVanila = 100; this.sirupKaramel = 100;
    }

    public boolean cekStok(String ukuranGelas, int butuhKopi, int butuhGula, int butuhSusu, int butuhAir, 
                           int butuhCoklat, int butuhMatcha, int butuhVanila, int butuhKaramel) {
        if (kopiBubuk < butuhKopi || gula < butuhGula || susu < butuhSusu || air < butuhAir ||
            coklatBubuk < butuhCoklat || matchaBubuk < butuhMatcha ||
            sirupVanila < butuhVanila || sirupKaramel < butuhKaramel) return false;
        
        if (ukuranGelas.equalsIgnoreCase("Kecil") && gelasKecil < 1) return false;
        if (ukuranGelas.equalsIgnoreCase("Besar") && gelasBesar < 1) return false;
        
        return true;
    }

    public void kurangiStok(String ukuranGelas, int habisKopi, int habisGula, int habisSusu, int habisAir,
                            int habisCoklat, int habisMatcha, int habisVanila, int habisKaramel) {
        this.kopiBubuk -= habisKopi; this.gula -= habisGula; this.susu -= habisSusu;
        this.air -= habisAir; this.coklatBubuk -= habisCoklat; this.matchaBubuk -= habisMatcha;
        this.sirupVanila -= habisVanila; this.sirupKaramel -= habisKaramel;
        
        if (ukuranGelas.equalsIgnoreCase("Kecil")) this.gelasKecil--;
        else if (ukuranGelas.equalsIgnoreCase("Besar")) this.gelasBesar--;
    }

    public boolean cekStokKritis(String namaMinuman, String ukuran) {
        int batasKritisBubuk = 50; 
        int batasKritisGelas = 8;
        int porsi = ukuran.equalsIgnoreCase("Besar") ? 2 : 1;
        
        if (namaMinuman.equalsIgnoreCase("Hot Chocolate")) {
            if (coklatBubuk < (15 * porsi) + batasKritisBubuk) return true;
        } else if (namaMinuman.equalsIgnoreCase("Matcha Latte")) {
            if (matchaBubuk < (15 * porsi) + batasKritisBubuk) return true;
        } else {
            if (kopiBubuk < (10 * porsi) + batasKritisBubuk) return true;
        }
        
        if (ukuran.equalsIgnoreCase("Kecil") && gelasKecil < batasKritisGelas) return true;
        if (ukuran.equalsIgnoreCase("Besar") && gelasBesar < batasKritisGelas) return true;
        
        return false;
    }
    
    public void periksaDanCetakStokKritisAdmin() {
        boolean adaStokKritis = false;
        int batasKritisBubuk = 25, batasKritisCair = 100, batasKritisGelas = 5;

        if (kopiBubuk < batasKritisBubuk) { System.out.println("--> PERINGATAN ADMIN: Stok Bubuk Kopi hampir habis! Sisa: " + kopiBubuk); adaStokKritis = true; }
        if (gula < batasKritisBubuk) { System.out.println("--> PERINGATAN ADMIN: Stok Gula hampir habis! Sisa: " + gula); adaStokKritis = true; }
        if (coklatBubuk < batasKritisBubuk) { System.out.println("--> PERINGATAN ADMIN: Stok Bubuk Coklat hampir habis! Sisa: " + coklatBubuk); adaStokKritis = true; }
        if (matchaBubuk < batasKritisBubuk) { System.out.println("--> PERINGATAN ADMIN: Stok Bubuk Matcha hampir habis! Sisa: " + matchaBubuk); adaStokKritis = true; }
        if (susu < batasKritisCair) { System.out.println("--> PERINGATAN ADMIN: Stok Susu hampir habis! Sisa: " + susu); adaStokKritis = true; }
        if (air < batasKritisCair) { System.out.println("--> PERINGATAN ADMIN: Stok Air hampir habis! Sisa: " + air); adaStokKritis = true; }
        if (gelasKecil < batasKritisGelas) { System.out.println("--> PERINGATAN ADMIN: Stok Gelas Kecil hampir habis! Sisa: " + gelasKecil); adaStokKritis = true; }
        if (gelasBesar < batasKritisGelas) { System.out.println("--> PERINGATAN ADMIN: Stok Gelas Besar hampir habis! Sisa: " + gelasBesar); adaStokKritis = true; }
        
        if (adaStokKritis) {
            System.out.println("--> SEGERA LAKUKAN PENGISIAN ULANG!");
        }
    }

    public void tampilkanStokAdmin() {
        System.out.println("\n--- STATUS STOK INTERNAL (KHUSUS ADMIN) ---");
        System.out.println("- Bubuk Kopi: " + kopiBubuk + " gram");
        System.out.println("- Gula      : " + gula + " gram");
        System.out.println("- Susu      : " + susu + " ml");
        System.out.println("- Air       : " + air + " ml");
        System.out.println("- Bubuk Coklat: " + coklatBubuk + " gram");
        System.out.println("- Bubuk Matcha: " + matchaBubuk + " gram");
        System.out.println("- Sirup Vanila: " + sirupVanila + " ml");
        System.out.println("- Sirup Karamel: " + sirupKaramel + " ml");
        System.out.println("- Gelas Kecil: " + gelasKecil + " buah");
        System.out.println("- Gelas Besar: " + gelasBesar + " buah");
        System.out.println("-------------------------------------------");
    }
}
