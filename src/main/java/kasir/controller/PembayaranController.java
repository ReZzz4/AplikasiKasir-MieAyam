// File: src/main/java/kasir/controller/PembayaranController.java
package kasir.controller;

import java.io.IOException;
import java.text.NumberFormat;
import java.util.Locale;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import kasir.Navigator;
import kasir.data.Keranjang;
import kasir.model.ItemKeranjang;

public class PembayaranController {

    @FXML private TableView<ItemKeranjang> tabelStruk;
    @FXML private TableColumn<ItemKeranjang, String> namaCol;
    @FXML private TableColumn<ItemKeranjang, Integer> jumlahCol;
    @FXML private TableColumn<ItemKeranjang, Long> subtotalCol;
    @FXML private Label totalLabel;
    @FXML private TextField bayarField;
    @FXML private Label kembalianLabel;
    @FXML private Label pesanLabel;

    private Navigator navigator;
    private Keranjang keranjang;
    private boolean sudahDibayar = false;

    @FXML
    private void initialize() {
        namaCol.setCellValueFactory(new PropertyValueFactory<>("namaProduk"));
        jumlahCol.setCellValueFactory(new PropertyValueFactory<>("jumlah"));
        subtotalCol.setCellValueFactory(new PropertyValueFactory<>("subtotal"));
    }

    public void setKonteks(Navigator navigator, Keranjang keranjang) {
        this.navigator = navigator;
        this.keranjang = keranjang;
        tabelStruk.setItems(keranjang.getDaftarItem());
        tampilkanTotal();
    }

    private void tampilkanTotal() {
        NumberFormat rupiah = NumberFormat.getCurrencyInstance(Locale.forLanguageTag("id-ID"));
        totalLabel.setText(rupiah.format(keranjang.getTotal()));
    }

    @FXML
    private void proses() {
        if (sudahDibayar) {
            pesanLabel.setText("Transaksi ini sudah dibayar.");
            return;
        }
        long bayar;
        try {
            bayar = Long.parseLong(bayarField.getText().trim());
        } catch (NumberFormatException e) {
            pesanLabel.setText("Uang dibayar harus berupa angka bulat.");
            return;
        }
        long total = keranjang.getTotal();
        if (bayar < total) {
            pesanLabel.setText("Uang bayar kurang dari total belanja.");
            return;
        }
        try {
            for (ItemKeranjang item : keranjang.getDaftarItem()) {
                item.getProduk().kurangiStok(item.getJumlah());
            }
        } catch (IllegalArgumentException e) {
            pesanLabel.setText(e.getMessage());
            return;
        }

        long kembalian = bayar - total;
        NumberFormat rupiah = NumberFormat.getCurrencyInstance(Locale.forLanguageTag("id-ID"));
        kembalianLabel.setText(rupiah.format(kembalian));
        pesanLabel.setText("Pembayaran berhasil. Klik Transaksi Baru untuk transaksi berikutnya.");
        sudahDibayar = true;
    }

    @FXML
    private void transaksiBaru() {
        keranjang.kosongkan();
        try {
            navigator.bukaKasir();
        } catch (IOException e) {
            pesanLabel.setText("Halaman kasir gagal dibuka.");
            e.printStackTrace();
        }
    }
}