import java.util.Scanner;

public class SupportSystem {
    private InputReader reader;
    private Responder responder;

    public SupportSystem() {
        reader = new InputReader();
        responder = new Responder();
    }

    public void start() {
        boolean finished = false;
        printWelcome();

        while (!finished) {
            System.out.print("\n> ");
            String input = reader.getInput().toLowerCase();

            if (input.equals("bye")) {
                finished = true;
            } else {
                String response = responder.generateResponse(input);
                System.out.println(response);
            }
        }

        printGoodbye();
    }

    private void printWelcome() {
        System.out.println("=== Sistem Bantuan Akademik ===");
        System.out.println("Selamat datang di sistem bantuan pemilihan kelas akademik ITS.");
        System.out.println("Ketik salah satu topik berikut untuk mendapatkan bantuan:");

        System.out.println("""
        - login
        - akun
        - kelas penuh
        - jadwal bentrok
        - tambah kelas
        - hapus kelas
        - ubah kelas
        - perwalian
        - frs
        - transkrip
        - nilai
        - ukt
        - pembayaran
        - error
        - bug
        - server
        - cuti
        - aktif kuliah
        - nrp
        """);

        System.out.println("Ketik 'bye' untuk keluar dari sistem.");
    }

    private void printGoodbye() {
        System.out.println("Terima kasih telah menggunakan sistem bantuan akademik. Sampai jumpa!");
    }
}