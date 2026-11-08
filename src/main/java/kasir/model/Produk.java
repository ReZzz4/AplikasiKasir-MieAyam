package kasir.model;

public class Produk {

    private final String kodeProduk;
    private final String namaProduk;
    private final long harga;
    private int stok;

    public Produk(String kodeProduk, String namaProduk, long harga, int stok) {
        if (kodeProduk == null || kodeProduk.isBlank()) {
            throw new IllegalArgumentException("Kode produk tidak valid.");
        }
        if (namaProduk == null || namaProduk.isBlank()) {
            throw new IllegalArgumentException("Nama produk wajib diisi.");
        }
        if (harga <= 0) {
            throw new IllegalArgumentException("Harga harus lebih dari nol.");
        }
        if (stok < 0) {
            throw new IllegalArgumentException("Stok tidak boleh negatif.");
        }
        this.kodeProduk = kodeProduk;
        this.namaProduk = namaProduk.trim();
        this.harga = harga;
        this.stok = stok;
    }

    public String getKodeProduk() { return kodeProduk; }
    public String getNamaProduk() { return namaProduk; }
    public long getHarga() { return harga; }
    public int getStok() { return stok; }

    public void kurangiStok(int jumlah) {
        if (jumlah <= 0) {
            throw new IllegalArgumentException("Jumlah pengurangan stok tidak valid.");
        }
        if (jumlah > stok) {
            throw new IllegalArgumentException("Stok tidak mencukupi.");
        }
        stok -= jumlah;
    }
}