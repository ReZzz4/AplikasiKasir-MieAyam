package kasir.data;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import kasir.model.Produk;

public class DataProduk {

    private final ObservableList<Produk> daftar = FXCollections.observableArrayList();
    private int urutan = 1;

    public ObservableList<Produk> getDaftar() {
        return FXCollections.unmodifiableObservableList(daftar);
    }

    public Produk tambah(String namaProduk, long harga, int stok) {
        String kode = String.format("PR%03d", urutan);
        Produk produk = new Produk(kode, namaProduk, harga, stok);
        daftar.add(produk);
        urutan++;
        return produk;
    }

    public void hapus(Produk produk) {
        daftar.remove(produk);
    }
}