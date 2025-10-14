public class Simulasi {

    public static void main(String[] args) {
        // Inisialisasi objek Vending Machine
        VendingMachine mesin = new VendingMachine();
        
        // Menampilkan daftar menu yang tersedia saat pertama kali mesin aktif
        mesin.tampilkanMenu();
        
        System.out.println("\n=====================================================");
        System.out.println(">>> SIMULASI 1: Memesan Minuman Varian Matcha (Matcha Latte)");
        System.out.println("=====================================================");
        // Parameter: Nama Minuman, Ukuran, Level Gula, Level Susu
        mesin.buatPesanan("Matcha Latte", "Besar", 1, 0); 
        
        System.out.println("\n=====================================================");
        System.out.println(">>> SIMULASI 2: Memesan Varian Kopi Spesial (Caramel Macchiato)");
        System.out.println("=====================================================");
        mesin.buatPesanan("Caramel Macchiato", "Kecil", 2, 1);
        
        System.out.println("\n=====================================================");
        System.out.println(">>> SIMULASI 3: Memesan Minuman Klasik (Hot Chocolate)");
        System.out.println("=====================================================");
        mesin.buatPesanan("Hot Chocolate", "Besar", 0, 3); 
        
        System.out.println("\n=====================================================");
        System.out.println(">>> MENAMPILKAN ULANG MENU (Setelah Beberapa Pesanan)");
        System.out.println("=====================================================");
        mesin.tampilkanMenu();
    }
}