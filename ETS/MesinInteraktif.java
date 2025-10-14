import java.util.Scanner;

public class MesinInteraktif {

    public static void main(String[] args) {
        VendingMachine mesin = new VendingMachine();
        Scanner input = new Scanner(System.in);
        
        while (true) {
            mesin.tampilkanMenu();
            
            System.out.println("\n----------------------------------------");
            System.out.print(">>> Silakan pilih nomor menu (atau ketik 'stok'): ");
            String pilihanInput = input.next();

            if (pilihanInput.equalsIgnoreCase("stok")) {
                mesin.tampilkanStokUntukAdmin();
                System.out.println("...Menampilkan kembali menu utama...");
                continue;
            }

            try {
                int pilihanMenu = Integer.parseInt(pilihanInput);
                
                System.out.print(">>> Pilih ukuran (Kecil/Besar): ");
                String ukuran = input.next();

                System.out.print(">>> Masukkan level gula (0-3): ");
                int levelGula = input.nextInt();
                
                System.out.print(">>> Masukkan level susu (0-3): ");
                int levelSusu = input.nextInt();
                
                String namaMinuman = "";
                switch (pilihanMenu) {
                    case 1: namaMinuman = "Espresso"; break;
                    case 2: namaMinuman = "Americano"; break;
                    case 3: namaMinuman = "Flat White"; break;
                    case 4: namaMinuman = "Latte"; break;
                    case 5: namaMinuman = "Cappuccino"; break;
                    case 6: namaMinuman = "Caramel Macchiato"; break;
                    case 7: namaMinuman = "Mochaccino"; break;
                    case 8: namaMinuman = "Kopi Tubruk"; break;
                    case 9: namaMinuman = "Hot Chocolate"; break;
                    case 10: namaMinuman = "Matcha Latte"; break;
                    default: System.out.println("Pilihan menu tidak valid!"); continue;
                }
                
                System.out.println("\n=====================================================");
                mesin.buatPesanan(namaMinuman, ukuran, levelGula, levelSusu);
                System.out.println("=====================================================");
                
                System.out.print("\nApakah Anda ingin memesan lagi? (y/n): ");
                String pesanLagi = input.next();
                if (pesanLagi.equalsIgnoreCase("n")) {
                    System.out.println("Terima kasih telah menggunakan Kopag Coffee Maker!");
                    break;
                }

            } catch (NumberFormatException e) {
                System.out.println("Input tidak valid! Harap masukkan nomor menu atau ketik 'stok'.");
            }
        }
        
        input.close();
    }
}
