import java.awt.*;
import java.awt.image.*;
import java.awt.print.*;
import java.text.MessageFormat; // Import untuk fitur Print/PDF
import java.text.NumberFormat;
import java.util.ArrayList; // Import untuk Header/Footer PDF
import java.util.Locale;
import javax.swing.*;

// --- Abstraction: Abstract class Orang ---
abstract class Orang {
    private String nama;
    private String nip;

    public Orang(String nama, String nip) {
        this.nama = nama;
        this.nip = nip;
    }

    public String getNama() {
        return nama;
    }

    public String getNip() {
        return nip;
    }

    public abstract double hitungGaji();
}

// --- Encapsulation: Class Jabatan ---
class Jabatan {
    private String namaJabatan;
    private double gajiPerJam;

    public Jabatan(String namaJabatan, double gajiPerJam) {
        this.namaJabatan = namaJabatan;
        this.gajiPerJam = gajiPerJam;
    }

    public String getNamaJabatan() {
        return namaJabatan;
    }

    public double getGajiPerJam() {
        return gajiPerJam;
    }
}

// --- Inheritance: Class Pegawai extends Orang ---
class Pegawai extends Orang {
    private Jabatan jabatan;
    private int jamKerja;
    private double pajak;

    public Pegawai(String nama, String nip, Jabatan jabatan, int jamKerja, double pajak) {
        super(nama, nip);
        this.jabatan = jabatan;
        this.jamKerja = jamKerja;
        this.pajak = pajak;
    }

    public Jabatan getJabatan() {
        return jabatan;
    }

    public int getJamKerja() {
        return jamKerja;
    }

    public double hitungNominalPajak() {
        double gajiKotor = jamKerja * jabatan.getGajiPerJam();
        return gajiKotor * pajak;
    }

    @Override
    public double hitungGaji() {
        double gajiKotor = jamKerja * jabatan.getGajiPerJam();
        return gajiKotor - hitungNominalPajak();
    }
}

// --- Main GUI Application ---
public class SistemGajiKaryawanGUI {
    private JFrame frame;
    private JTextArea txtTerminal;
    private JPanel panelTop;
    private JPanel panelButtons;
    private JLabel imageLabel; 
    private ArrayList<JButton> listTombol = new ArrayList<>(); 
    
    // Variabel Logo
    private ImageIcon iconLight; 
    private ImageIcon iconDark;  

    // Data
    private ArrayList<Jabatan> daftarJabatan = new ArrayList<>();
    private ArrayList<Pegawai> daftarPegawai = new ArrayList<>();
    private double pendapatanPerusahaan;

    public SistemGajiKaryawanGUI() {
        frame = new JFrame("Sistem Gaji Karyawan - PDF Support");
        frame.setSize(800, 700);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        // --- 1. SETUP OUTPUT AREA (Terminal) ---
        txtTerminal = new JTextArea(15, 50);
        txtTerminal.setEditable(false);
        txtTerminal.setFont(new Font("Monospaced", Font.PLAIN, 12));
        JScrollPane scrollPane = new JScrollPane(txtTerminal);

        // --- 2. MENU BAR ---
        JMenuBar menuBar = new JMenuBar();
        
        JMenu menuFile = new JMenu("File");
        
        // --- FITUR BARU: CETAK PDF ---
        JMenuItem itemCetak = new JMenuItem("Simpan Laporan (PDF)");
        itemCetak.addActionListener(e -> cetakLaporanPDF());
        
        JMenuItem itemKeluar = new JMenuItem("Keluar");
        itemKeluar.addActionListener(e -> System.exit(0));
        
        menuFile.add(itemCetak); // Tambahkan menu cetak
        menuFile.addSeparator(); // Garis pemisah
        menuFile.add(itemKeluar);

        JMenu menuEdit = new JMenu("Edit");
        JMenuItem itemClear = new JMenuItem("Bersihkan Output");
        JMenuItem itemReset = new JMenuItem("Reset Semua Data");
        itemClear.addActionListener(e -> clearOutput());
        itemReset.addActionListener(e -> resetData());
        menuEdit.add(itemClear);
        menuEdit.add(itemReset);

        JMenu menuTampilan = new JMenu("Tampilan");
        JMenuItem itemModeGelap = new JMenuItem("Mode Gelap");
        JMenuItem itemModeTerang = new JMenuItem("Mode Terang");
        
        itemModeGelap.addActionListener(e -> aturTema(true));
        itemModeTerang.addActionListener(e -> aturTema(false));
        
        menuTampilan.add(itemModeGelap);
        menuTampilan.add(itemModeTerang);

        JMenu menuBantuan = new JMenu("Bantuan");
        JMenuItem itemTentang = new JMenuItem("Tentang Aplikasi");
        itemTentang.addActionListener(e -> {
            JOptionPane.showMessageDialog(frame, 
                "Sistem Gaji Karyawan v2.0\nNRP: 5025231288\nKelas: PBO - A", 
                "Tentang", JOptionPane.INFORMATION_MESSAGE);
        });
        menuBantuan.add(itemTentang);

        menuBar.add(menuFile);
        menuBar.add(menuEdit);
        menuBar.add(menuTampilan);
        menuBar.add(menuBantuan);
        frame.setJMenuBar(menuBar);


        // --- 3. TOP PANEL (LOGO) ---
        panelTop = new JPanel(new BorderLayout());
        imageLabel = new JLabel();
        
        ImageIcon iconOriginal = new ImageIcon("Logo5.png"); 
        
        if (iconOriginal.getIconWidth() > 0) {
            Image srcImg = iconOriginal.getImage();
            Image imgLight = srcImg.getScaledInstance(-1, 150, java.awt.Image.SCALE_SMOOTH); 
            iconLight = new ImageIcon(imgLight);

            ImageFilter filter = new BlackToWhiteFilter();
            ImageProducer producer = new FilteredImageSource(srcImg.getSource(), filter);
            Image imgFiltered = Toolkit.getDefaultToolkit().createImage(producer);
            Image imgDark = imgFiltered.getScaledInstance(-1, 150, java.awt.Image.SCALE_SMOOTH);
            iconDark = new ImageIcon(imgDark);

            imageLabel.setIcon(iconLight);
        } else {
            imageLabel.setText("Logo GajiIn (Gambar tidak ditemukan)");
        }

        imageLabel.setHorizontalAlignment(SwingConstants.CENTER);
        imageLabel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        
        panelTop.add(imageLabel, BorderLayout.CENTER);


        // --- 4. BUTTONS PANEL ---
        panelButtons = new JPanel(new GridLayout(2, 3, 10, 10)); 
        panelButtons.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        JButton btn1 = new JButton("Set Pendapatan Perusahaan");
        JButton btn2 = new JButton("Tambah Jabatan");
        JButton btn3 = new JButton("Tambah Pegawai");
        JButton btn4 = new JButton("Lihat Gaji");
        JButton btn5 = new JButton("Clear Output");
        JButton btn6 = new JButton("Reset Data");

        btn1.addActionListener(e -> setPendapatanPerusahaan());
        btn2.addActionListener(e -> tambahJabatan());
        btn3.addActionListener(e -> tambahPegawai());
        btn4.addActionListener(e -> lihatGaji());
        btn5.addActionListener(e -> clearOutput());
        btn6.addActionListener(e -> resetData());

        panelButtons.add(btn1);
        panelButtons.add(btn2);
        panelButtons.add(btn3);
        panelButtons.add(btn4);
        panelButtons.add(btn5);
        panelButtons.add(btn6);

        listTombol.add(btn1);
        listTombol.add(btn2);
        listTombol.add(btn3);
        listTombol.add(btn4);
        listTombol.add(btn5);
        listTombol.add(btn6);

        frame.add(panelTop, BorderLayout.NORTH);
        frame.add(panelButtons, BorderLayout.CENTER);
        frame.add(scrollPane, BorderLayout.SOUTH);
        frame.setVisible(true);
    }

    // --- FITUR BARU: CETAK KE PDF ---
    private void cetakLaporanPDF() {
        // Cek apakah terminal kosong
        if (txtTerminal.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(frame, 
                "Tidak ada data untuk dicetak!\nSilakan klik 'Lihat Gaji' terlebih dahulu.", 
                "Gagal Mencetak", JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {
            // Header: Judul di atas setiap halaman
            MessageFormat header = new MessageFormat("Laporan Gaji Karyawan - PT. Sejahtera Makmur Abadi");
            // Footer: Nomor halaman di bawah
            MessageFormat footer = new MessageFormat("Halaman {0}");

            // Perintah Print bawaan Java Swing
            // Ini akan membuka dialog Print. User harus memilih "Microsoft Print to PDF"
            boolean complete = txtTerminal.print(header, footer, true, null, null, true);

            if (complete) {
                JOptionPane.showMessageDialog(frame, "Laporan berhasil disimpan/dicetak!", "Sukses", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(frame, "Pencetakan dibatalkan.", "Batal", JOptionPane.WARNING_MESSAGE);
            }
        } catch (PrinterException pe) {
            JOptionPane.showMessageDialog(frame, "Gagal mencetak: " + pe.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // --- METHOD TEMA ---
    private void aturTema(boolean gelap) {
        if (gelap) {
            Color darkBg = new Color(45, 45, 45);
            Color btnBg = new Color(70, 70, 70);
            Color textWhite = Color.WHITE;

            panelTop.setBackground(darkBg);
            panelButtons.setBackground(darkBg);
            frame.getContentPane().setBackground(darkBg);

            if (iconDark != null) imageLabel.setIcon(iconDark);
            imageLabel.setBackground(null);
            imageLabel.setOpaque(false); 

            txtTerminal.setBackground(Color.BLACK);
            txtTerminal.setForeground(Color.GREEN);
            txtTerminal.setFont(new Font("Monospaced", Font.BOLD, 14));

            for (JButton btn : listTombol) {
                btn.setBackground(btnBg);
                btn.setForeground(textWhite);
            }
        } else {
            panelTop.setBackground(null);
            panelButtons.setBackground(null);
            frame.getContentPane().setBackground(null);

            if (iconLight != null) imageLabel.setIcon(iconLight);
            imageLabel.setBackground(null);
            imageLabel.setOpaque(false);

            txtTerminal.setBackground(Color.WHITE);
            txtTerminal.setForeground(Color.BLACK);
            txtTerminal.setFont(new Font("Monospaced", Font.PLAIN, 12));

            for (JButton btn : listTombol) {
                btn.setBackground(null);
                btn.setForeground(Color.BLACK);
            }
        }
    }

    // --- LOGIC METHODS ---
    private void setPendapatanPerusahaan() {
        JTextField txtPendapatan = new JTextField();
        Object[] message = { "Pendapatan Perusahaan:", txtPendapatan };
        int option = JOptionPane.showConfirmDialog(frame, message, "Set Pendapatan", JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
            try {
                pendapatanPerusahaan = Double.parseDouble(txtPendapatan.getText());
                NumberFormat currencyFormat = NumberFormat.getCurrencyInstance(new Locale("id", "ID"));
                txtTerminal.append("[INFO] Pendapatan perusahaan: " + currencyFormat.format(pendapatanPerusahaan) + "\n");
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(frame, "Masukkan angka yang valid!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void tambahJabatan() {
        JTextField txtNamaJabatan = new JTextField();
        JTextField txtGajiPerJam = new JTextField();
        Object[] message = { "Nama Jabatan:", txtNamaJabatan, "Gaji Per Jam:", txtGajiPerJam };
        int option = JOptionPane.showConfirmDialog(frame, message, "Tambah Jabatan", JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
            try {
                String namaJabatan = txtNamaJabatan.getText();
                double gajiPerJam = Double.parseDouble(txtGajiPerJam.getText());
                daftarJabatan.add(new Jabatan(namaJabatan, gajiPerJam));
                txtTerminal.append("[SUKSES] Jabatan ditambahkan: " + namaJabatan + "\n");
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(frame, "Data tidak valid!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void tambahPegawai() {
        if (daftarJabatan.isEmpty()) {
            JOptionPane.showMessageDialog(frame, "Tambahkan jabatan terlebih dahulu!", "Peringatan", JOptionPane.WARNING_MESSAGE);
            return;
        }
        JTextField txtNamaPegawai = new JTextField();
        JTextField txtNIPPegawai = new JTextField();
        JTextField txtJamKerja = new JTextField();
        JComboBox<String> comboJabatan = new JComboBox<>();
        JTextField txtPajak = new JTextField();
        for (Jabatan jabatan : daftarJabatan) { comboJabatan.addItem(jabatan.getNamaJabatan()); }
        Object[] message = { "Nama Pegawai:", txtNamaPegawai, "NIP Pegawai:", txtNIPPegawai, "Jam Kerja:", txtJamKerja, "Pilih Jabatan:", comboJabatan, "Pajak (dalam persen %):", txtPajak };
        int option = JOptionPane.showConfirmDialog(frame, message, "Tambah Pegawai", JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
            try {
                String namaPegawai = txtNamaPegawai.getText();
                String nip = txtNIPPegawai.getText();
                int jamKerja = Integer.parseInt(txtJamKerja.getText());
                double pajak = Double.parseDouble(txtPajak.getText()) / 100;
                int selectedIndex = comboJabatan.getSelectedIndex();
                Jabatan jabatanDipilih = daftarJabatan.get(selectedIndex);
                Pegawai pegawai = new Pegawai(namaPegawai, nip, jabatanDipilih, jamKerja, pajak);
                daftarPegawai.add(pegawai);
                txtTerminal.append("[SUKSES] Pegawai baru: " + namaPegawai + " (" + jabatanDipilih.getNamaJabatan() + ")\n");
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(frame, "Data tidak valid!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void lihatGaji() {
        if (daftarPegawai.isEmpty()) {
            JOptionPane.showMessageDialog(frame, "Belum ada pegawai.", "Info", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        NumberFormat currencyFormat = NumberFormat.getCurrencyInstance(new Locale("id", "ID"));
        txtTerminal.append("\n==================================\n");
        txtTerminal.append("       LAPORAN GAJI PEGAWAI       \n");
        txtTerminal.append("==================================\n");
        double totalGaji = 0.0;
        for (Pegawai pegawai : daftarPegawai) {
            double gaji = pegawai.hitungGaji();
            totalGaji += gaji;
            txtTerminal.append("Nama      : " + pegawai.getNama() + "\n");
            txtTerminal.append("NIP       : " + pegawai.getNip() + "\n");
            txtTerminal.append("Jabatan   : " + pegawai.getJabatan().getNamaJabatan() + "\n");
            txtTerminal.append("Jam Kerja : " + pegawai.getJamKerja() + " jam\n");
            txtTerminal.append("Gaji Bersih: " + currencyFormat.format(gaji) + "\n");
            txtTerminal.append("----------------------------------\n");
        }
        double sisaPendapatan = pendapatanPerusahaan - totalGaji;
        txtTerminal.append("\n[RINGKASAN]\n");
        txtTerminal.append("Total Pengeluaran Gaji : " + currencyFormat.format(totalGaji) + "\n");
        txtTerminal.append("Sisa Pendapatan Kantor : " + currencyFormat.format(sisaPendapatan) + "\n");
        txtTerminal.append("==================================\n");
    }

    private void clearOutput() { txtTerminal.setText(""); }
    
    private void resetData() {
        int confirm = JOptionPane.showConfirmDialog(frame, "Yakin ingin menghapus semua data?", "Konfirmasi Reset", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            daftarJabatan.clear();
            daftarPegawai.clear();
            pendapatanPerusahaan = 0;
            txtTerminal.setText("");
            txtTerminal.append("[SYSTEM] Data berhasil direset ulang.\n");
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new SistemGajiKaryawanGUI());
    }
}

// --- CLASS FILTER WARNA ---
class BlackToWhiteFilter extends RGBImageFilter {
    public BlackToWhiteFilter() {
        canFilterIndexColorModel = true;
    }
    @Override
    public int filterRGB(int x, int y, int rgb) {
        int a = rgb & 0xFF000000;
        int r = (rgb >> 16) & 0xFF;
        int g = (rgb >> 8) & 0xFF;
        int b = rgb & 0xFF;
        if (r < 100 && g < 100 && b < 100) {
            return a | 0xFFFFFF; 
        }
        return rgb;
    }
}