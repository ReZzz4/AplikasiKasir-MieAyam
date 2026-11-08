package kasir.controller;

import java.io.IOException;
import java.text.NumberFormat;
import java.util.Locale;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.StringConverter;
import kasir.Navigator;
import kasir.data.DataProduk;
import kasir.data.Keranjang;
import kasir.model.ItemKeranjang;
import kasir.model.Produk;

public class KasirController {

    @FXML private ComboBox<Produk> produkBox;
    @FXML private TextField jumlahField;
    @FXML private TableView<ItemKeranjang> tabelKeranjang;
    @FXML private TableColumn<ItemKeranjang, String> namaCol;
    @FXML private TableColumn<ItemKeranjang, Long> hargaCol;
    @FXML private TableColumn<ItemKeranjang, Integer> jumlahCol;
    @FXML private TableColumn<ItemKeranjang, Long> subtotalCol;
    @FXML private Label totalLabel;
    @FXML private Label pesanLabel;

    private Navigator navigator;
    private Keranjang keranjang;

    @FXML
    private void initialize() {
        namaCol.setCellValueFactory(new PropertyValueFactory<>("namaProduk"));
        hargaCol.setCellValueFactory(new PropertyValueFactory<>("hargaSatuan"));
        jumlahCol.setCellValueFactory(new PropertyValueFactory<>("jumlah"));
        subtotalCol.setCellValueFactory(new PropertyValueFactory<>("subtotal"));

        produkBox.setConverter(new StringConverter<Produk>() {
            @Override
            public String toString(Produk produk) {
                return produk == null ? "" : produk.getNamaProduk() + " (Stok: " + produk.getStok() + ")";
            }
            @Override
            public Produk fromString(String string) {
                return null;
            }
        });
    }

    public void setKonteks(Navigator navigator, DataProduk dataProduk, Keranjang keranjang) {
        this.navigator = navigator;
        this.keranjang = keranjang;
        produkBox.setItems(dataProduk.getDaftar());
        tabelKeranjang.setItems(keranjang.getDaftarItem());
        tampilkanTotal();
    }

    @FXML
    private void tambahKeKeranjang() {
        Produk produk = produkBox.getValue();
        if (produk == null) {
            pesanLabel.setText("Pilih produk terlebih dahulu.");
            return;
        }
        try {
            int jumlah = Integer.parseInt(jumlahField.getText().trim());
            keranjang.tambah(produk, jumlah);
            jumlahField.clear();
            pesanLabel.setText("");
        } catch (NumberFormatException e) {
            pesanLabel.setText("Jumlah harus berupa angka bulat.");
            return;
        } catch (IllegalArgumentException e) {
            pesanLabel.setText(e.getMessage());
            return;
        }
        tampilkanTotal();
    }

    @FXML
    private void hapusItem() {
        ItemKeranjang item = tabelKeranjang.getSelectionModel().getSelectedItem();
        if (item == null) {
            pesanLabel.setText("Pilih item keranjang dahulu.");
            return;
        }
        keranjang.hapus(item);
        tampilkanTotal();
    }

    private void tampilkanTotal() {
        NumberFormat rupiah = NumberFormat.getCurrencyInstance(Locale.forLanguageTag("id-ID"));
        totalLabel.setText(rupiah.format(keranjang.getTotal()));
    }

    @FXML
    private void bayar() {
        if (keranjang.isEmpty()) {
            pesanLabel.setText("Keranjang masih kosong.");
            return;
        }
        try {
            navigator.bukaPembayaran();
        } catch (IOException e) {
            pesanLabel.setText("Halaman pembayaran gagal dibuka.");
            e.printStackTrace();
        }
    }

    @FXML
    private void bukaKelolaProduk() {
        try {
            navigator.bukaProduk();
        } catch (IOException e) {
            pesanLabel.setText("Halaman kelola produk gagal dibuka.");
            e.printStackTrace();
        }
    }
}