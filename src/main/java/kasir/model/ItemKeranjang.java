// File: src/main/java/kasir/model/ItemKeranjang.java
package kasir.model;

public class ItemKeranjang {

    private final Produk produk;
    private int jumlah;

    public ItemKeranjang(Produk produk, int jumlah) {
        if (produk == null) {
            throw new IllegalArgumentException("Produk tidak boleh kosong.");
        }
        if (jumlah <= 0) {
            throw new IllegalArgumentException("Jumlah harus lebih dari nol.");
        }
        this.produk = produk;
        this.jumlah = jumlah;
    }

    public Produk getProduk() { return produk; }
    public int getJumlah() { return jumlah; }
    public long getSubtotal() { return produk.getHarga() * jumlah; }

    // getter bantuan agar PropertyValueFactory bisa langsung baca nama & harga produk
    public String getNamaProduk() { return produk.getNamaProduk(); }
    public long getHargaSatuan() { return produk.getHarga(); }

    public void tambahJumlah(int tambahan) {
        if (tambahan <= 0) {
            throw new IllegalArgumentException("Jumlah tambahan tidak valid.");
        }
        this.jumlah += tambahan;
    }
}