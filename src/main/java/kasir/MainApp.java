// File: src/main/java/kasir/MainApp.java
package kasir;

import javafx.application.Application;
import javafx.stage.Stage;
import kasir.data.DataProduk;
import kasir.data.Keranjang;

public class MainApp extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        DataProduk dataProduk = new DataProduk();
        Keranjang keranjang = new Keranjang();

        dataProduk.tambah("Mie Ayam Original", 8_000, 20);
        dataProduk.tambah("Mie Ayam Baso", 10_000, 30);
        dataProduk.tambah("Mie Ayam ceker", 10_000, 30);
        dataProduk.tambah("Krupuk Pangsit ", 1_000, 25);

        Navigator navigator = new Navigator(stage, dataProduk, keranjang);
        navigator.bukaKasir();
    }

    public static void main(String[] args) {
        launch(args);
    }
}