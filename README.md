# Aplikasi Kasir

Aplikasi kasir sederhana berbasis JavaFX untuk latihan pemrograman GUI Java, menggunakan pola MVC + Navigator.

## Fitur
- Tambah produk ke keranjang belanja dengan validasi stok
- Total belanja otomatis, termasuk penggabungan baris produk yang sama
- Proses pembayaran dengan perhitungan kembalian
- Kelola produk: tambah, edit, dan hapus produk beserta stoknya

## Teknologi
- Java 17
- JavaFX 21
- Maven
- FXML + CSS untuk tampilan

## Cara Menjalankan
1. Clone repository ini.
2. Buka project di NetBeans (atau IDE lain yang mendukung Maven).
3. Jalankan lewat goal Maven `javafx:run`:
   Atau di NetBeans: klik kanan `pom.xml` > Run Maven > Other Goals... > ketik `clean javafx:run`.

## Struktur Project
src/main/java/kasir/
MainApp.java
Navigator.java
model/ -> Produk, ItemKeranjang
data/ -> DataProduk, Keranjang
controller/ -> KasirController, PembayaranController, ProdukController
src/main/resources/kasir/
kasir.fxml, pembayaran.fxml, produk.fxml
styles/app.css

## Catatan
Project ini dibuat untuk latihan pribadi, belum terhubung ke database — data produk dan transaksi hanya tersimpan selama aplikasi berjalan (di RAM).