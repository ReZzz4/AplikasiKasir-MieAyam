package kasir.data;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import kasir.model.ItemKeranjang;
import kasir.model.Produk;

public class Keranjang {

    private final ObservableList<ItemKeranjang> daftarItem = FXCollections.observableArrayList();

    public ObservableList<ItemKeranjang> getDaftarItem() {
        return FXCollections.unmodifiableObservableList(daftarItem);
    }

    public void tambah(Produk produk, int jumlah) {
        if (jumlah <= 0) {
            throw new IllegalArgumentException("Jumlah harus lebih dari nol.");
        }
        for (ItemKeranjang item : daftarItem) {
            if (item.getProduk() == produk) {
                int jumlahBaru = item.getJumlah() + jumlah;
                if (jumlahBaru > produk.getStok()) {
                    throw new IllegalArgumentException("Stok tidak mencukupi. Sisa stok: " + produk.getStok());
                }
                item.tambahJumlah(jumlah);
                return;
            }
        }
        if (jumlah > produk.getStok()) {
            throw new IllegalArgumentException("Stok tidak mencukupi. Sisa stok: " + produk.getStok());
        }
        daftarItem.add(new ItemKeranjang(produk, jumlah));
    }

    public void hapus(ItemKeranjang item) {
        daftarItem.remove(item);
    }

    public void kosongkan() {
        daftarItem.clear();
    }

    public long getTotal() {
        long total = 0;
        for (ItemKeranjang item : daftarItem) {
            total += item.getSubtotal();
        }
        return total;
    }

    public boolean isEmpty() {
        return daftarItem.isEmpty();
    }
}