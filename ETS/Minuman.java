/**
 @Leyan Harits
 */
public class Minuman {
    private String nama;
    private int hargaKecil;
    private int hargaBesar;

    public Minuman(String nama, int hargaKecil, int hargaBesar) {
        this.nama = nama;
        this.hargaKecil = hargaKecil;
        this.hargaBesar = hargaBesar;
    }

    public String getNama() {
        return nama;
    }

    public int getHarga(String ukuran) {
        if (ukuran.equalsIgnoreCase("Kecil")) {
            return hargaKecil;
        } else if (ukuran.equalsIgnoreCase("Besar")) {
            return hargaBesar;
        }
        return 0; // Mengembalikan 0 jika ukuran tidak valid
    }
}