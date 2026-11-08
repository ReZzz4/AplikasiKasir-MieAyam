// File: src/main/java/kasir/controller/ProdukController.java
package kasir.controller;

import java.io.IOException;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import kasir.Navigator;
import kasir.data.DataProduk;
import kasir.model.Produk;

public class ProdukController {

    @FXML private TextField namaField;
    @FXML private TextField hargaField;
    @FXML private TextField stokField;
    @FXML private TableView<Produk> tabel;
    @FXML private TableColumn<Produk, String> kodeCol;
    @FXML private TableColumn<Produk, String> namaCol;
    @FXML private TableColumn<Produk, Long> hargaCol;
    @FXML private TableColumn<Produk, Integer> stokCol;
    @FXML private Label pesanLabel;

    private Navigator navigator;
    private DataProduk data;

    @FXML
    private void initialize() {
        kodeCol.setCellValueFactory(new PropertyValueFactory<>("kodeProduk"));
        namaCol.setCellValueFactory(new PropertyValueFactory<>("namaProduk"));
        hargaCol.setCellValueFactory(new PropertyValueFactory<>("harga"));
        stokCol.setCellValueFactory(new PropertyValueFactory<>("stok"));
        tabel.setPlaceholder(new Label("Belum ada produk."));
    }

    public void setKonteks(Navigator navigator, DataProduk data) {
        this.navigator = navigator;
        this.data = data;
        tabel.setItems(data.getDaftar());
    }

    @FXML
    private void tambah() {
        try {
            long harga = Long.parseLong(hargaField.getText().trim());
            int stok = Integer.parseInt(stokField.getText().trim());
            data.tambah(namaField.getText(), harga, stok);
            namaField.clear();
            hargaField.clear();
            stokField.clear();
            pesanLabel.setText("Produk ditambahkan.");
        } catch (NumberFormatException e) {
            pesanLabel.setText("Harga dan stok harus berupa angka bulat.");
        } catch (IllegalArgumentException e) {
            pesanLabel.setText(e.getMessage());
        }
    }

    @FXML
    private void hapus() {
        Produk produk = tabel.getSelectionModel().getSelectedItem();
        if (produk == null) {
            pesanLabel.setText("Pilih produk dahulu.");
            return;
        }
        Alert dialog = new Alert(Alert.AlertType.CONFIRMATION,
                "Hapus " + produk.getNamaProduk() + "?",
                ButtonType.YES, ButtonType.NO);
        dialog.initOwner(tabel.getScene().getWindow());
        ButtonType jawaban = dialog.showAndWait().orElse(ButtonType.NO);
        if (jawaban == ButtonType.YES) {
            data.hapus(produk);
        }
    }

    @FXML
    private void bukaKasir() {
        try {
            navigator.bukaKasir();
        } catch (IOException e) {
            pesanLabel.setText("Halaman kasir gagal dibuka.");
            e.printStackTrace();
        }
    }
}