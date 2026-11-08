package kasir;

import java.io.IOException;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import kasir.controller.KasirController;
import kasir.controller.PembayaranController;
import kasir.controller.ProdukController;
import kasir.data.DataProduk;
import kasir.data.Keranjang;

public class Navigator {

    private final Stage stage;
    private final DataProduk dataProduk;
    private final Keranjang keranjang;
    private Scene scene;

    public Navigator(Stage stage, DataProduk dataProduk, Keranjang keranjang) {
        this.stage = stage;
        this.dataProduk = dataProduk;
        this.keranjang = keranjang;
    }

    private FXMLLoader loader(String nama) {
        return new FXMLLoader(getClass().getResource("/kasir/" + nama + ".fxml"));
    }

    private void tampil(Parent root) {
        if (scene == null) {
            scene = new Scene(root, 960, 680);
            scene.getStylesheets().add(getClass().getResource("/kasir/styles/app.css").toExternalForm());
            stage.setScene(scene);
            stage.setTitle("Aplikasi Kasir");
            stage.setMinWidth(720);
            stage.setMinHeight(520);
        } else {
            scene.setRoot(root);
        }
        stage.show();
    }

    public void bukaKasir() throws IOException {
        FXMLLoader l = loader("kasir");
        Parent root = l.load();
        KasirController c = l.getController();
        c.setKonteks(this, dataProduk, keranjang);
        tampil(root);
    }

    public void bukaPembayaran() throws IOException {
        FXMLLoader l = loader("pembayaran");
        Parent root = l.load();
        PembayaranController c = l.getController();
        c.setKonteks(this, keranjang);
        tampil(root);
    }

    public void bukaProduk() throws IOException {
        FXMLLoader l = loader("produk");
        Parent root = l.load();
        ProdukController c = l.getController();
        c.setKonteks(this, dataProduk);
        tampil(root);
    }
}