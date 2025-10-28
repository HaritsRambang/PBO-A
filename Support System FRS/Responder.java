import java.util.*;

public class Responder {
    private Map<String, String> responses;

    public Responder() {
        responses = new HashMap<>();

        responses.put("login",
            "Jika login gagal, pastikan NRP dan password benar. " +
            "Gunakan fitur 'Lupa Password' atau hubungi helpdesk untuk reset akun.");

        responses.put("akun", 
            "Cek kembali informasi data pribadi mahasiswa di menu profil " +
            "Silakan hubungi helpdesk bagian akademik.");

        responses.put("kelas penuh", 
            "Kelas sudah penuh. Coba pilih kelas lain atau hubungi dosen pengampu untuk penambahan kuota.");

        responses.put("jadwal bentrok", 
            "Periksa jadwal di menu FRS Anda dan pastikan tidak ada tabrakan jam kuliah.");

        responses.put("tambah kelas", 
            "Untuk menambah kelas, buka menu FRS → cari mata kuliah → klik 'Ambil'.");

        responses.put("hapus kelas", 
            "Untuk menghapus kelas, buka menu 'FRS Saya' lalu klik tombol 'Drop'.");

        responses.put("ubah kelas", 
            "Gunakan fitur ubah kelas di menu FRS selama periode pengisian masih dibuka.");

        responses.put("perwalian", 
            "Pastikan Anda sudah melakukan perwalian dengan dosen wali sebelum tanggal batas verifikasi.");

        responses.put("frs", 
            "Dosen wali perlu menyetujui FRS Anda. Pantau status verifikasi di halaman utama sistem akademik.");

        responses.put("transkrip", 
            "Transkrip nilai dapat diunduh setelah semua nilai FRS terisi. Hubungi bagian akademik jika data tidak muncul.");

        responses.put("nilai", 
            "Jika nilai belum muncul, dosen mungkin belum melakukan input. Coba cek lagi beberapa hari kemudian.");

        responses.put("ukt", 
            "Pastikan Anda sudah melakukan pembayaran UKT melalui bank mitra. " +
            "Status pembayaran akan otomatis terverifikasi di sistem setelah 1x24 jam.");

        responses.put("pembayaran", 
            "Jika pembayaran belum terverifikasi, simpan bukti transaksi dan laporkan ke bagian keuangan.");

        responses.put("error", 
            "Coba logout lalu login kembali. Jika error tetap terjadi, laporkan ke helpdesk akademik.");

        responses.put("bug", 
            "Terima kasih sudah melapor. Sertakan tangkapan layar error agar tim IT bisa menelusurinya.");

        responses.put("server", 
            "Server mungkin sedang sibuk. Silakan coba lagi setelah beberapa menit.");

        responses.put("cuti", 
            "Untuk mengajukan cuti, isi formulir cuti akademik dan ajukan ke bagian akademik fakultas.");

        responses.put("aktif kuliah", 
            "Status aktif kuliah dapat dicek di halaman profil akademik mahasiswa.");

        responses.put("nrp", 
            "NRP adalah nomor induk mahasiswa yang digunakan untuk login ke sistem akademik.");

        responses.put("default",
            "Terima kasih atas pertanyaannya. Silakan jelaskan lebih detail (misalnya: login, kelas penuh, jadwal bentrok, nilai, atau UKT).");
    }

    public String generateResponse(String input) {
        input = input.toLowerCase();

        for (String keyword : responses.keySet()) {
            if (input.contains(keyword)) {
                return responses.get(keyword);
            }
        }
        return responses.get("default");
    }
}