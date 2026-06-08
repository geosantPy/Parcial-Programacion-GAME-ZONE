package org.example.view;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.example.model.DigitalVideoGame;
import org.example.model.PhysicalVideoGame;
import org.example.service.VideoGameService;

import java.util.Optional;

public class MainView extends Application {

    @Override
    public void start(Stage stage) {

        VideoGameService videoGameService = new VideoGameService();

        Button btnAddGame = new Button("Agregar Videojuego");
        Button btnListGames = new Button("Listar Videojuegos");
        Button btnSearchTitle = new Button("Buscar por Título");
        Button btnSearchPlatform = new Button("Buscar por Plataforma");
        Button btnSellGame = new Button("Realizar Venta");
        Button btnShowSales = new Button("Mostrar Ventas");
        Button btnExit = new Button("Salir");

        btnAddGame.setPrefWidth(250);
        btnListGames.setPrefWidth(250);
        btnSearchTitle.setPrefWidth(250);
        btnSearchPlatform.setPrefWidth(250);
        btnSellGame.setPrefWidth(250);
        btnShowSales.setPrefWidth(250);
        btnExit.setPrefWidth(250);

        btnAddGame.setOnAction(e -> {

            try {

                TextInputDialog typeDialog = new TextInputDialog();
                typeDialog.setTitle("Agregar Videojuego");
                typeDialog.setHeaderText("Tipo (Digital/Fisico)");

                Optional<String> typeResult = typeDialog.showAndWait();

                if (typeResult.isEmpty()) {
                    return;
                }

                String type = typeResult.get();

                TextInputDialog titleDialog = new TextInputDialog();
                titleDialog.setHeaderText("Ingrese el título");

                Optional<String> titleResult = titleDialog.showAndWait();

                if (titleResult.isEmpty()) {
                    return;
                }

                String title = titleResult.get();

                TextInputDialog priceDialog = new TextInputDialog();
                priceDialog.setHeaderText("Ingrese el precio");

                Optional<String> priceResult = priceDialog.showAndWait();

                if (priceResult.isEmpty()) {
                    return;
                }

                double price = Double.parseDouble(priceResult.get());

                TextInputDialog platformDialog = new TextInputDialog();
                platformDialog.setHeaderText("Ingrese la plataforma");

                Optional<String> platformResult = platformDialog.showAndWait();

                if (platformResult.isEmpty()) {
                    return;
                }

                String platform = platformResult.get();

                TextInputDialog stockDialog = new TextInputDialog();
                stockDialog.setHeaderText("Ingrese el stock");

                Optional<String> stockResult = stockDialog.showAndWait();

                if (stockResult.isEmpty()) {
                    return;
                }

                int stock = Integer.parseInt(stockResult.get());

                TextInputDialog genreDialog = new TextInputDialog();
                genreDialog.setHeaderText("Ingrese el género");

                Optional<String> genreResult = genreDialog.showAndWait();

                if (genreResult.isEmpty()) {
                    return;
                }

                String genre = genreResult.get();

                if (type.equalsIgnoreCase("Digital")) {

                    TextInputDialog sizeDialog = new TextInputDialog();
                    sizeDialog.setHeaderText("Tamaño en GB");

                    Optional<String> sizeResult = sizeDialog.showAndWait();

                    if (sizeResult.isEmpty()) {
                        return;
                    }

                    double sizeGB = Double.parseDouble(sizeResult.get());

                    TextInputDialog downloadDialog = new TextInputDialog();
                    downloadDialog.setHeaderText("Plataforma de descarga");

                    Optional<String> downloadResult = downloadDialog.showAndWait();

                    if (downloadResult.isEmpty()) {
                        return;
                    }

                    String downloadPlatform = downloadResult.get();

                    DigitalVideoGame game = new DigitalVideoGame(title, price, platform, stock, genre, sizeGB, downloadPlatform
                    );

                    videoGameService.addVideoGame(game);

                } else {

                    TextInputDialog conditionDialog = new TextInputDialog();
                    conditionDialog.setHeaderText("Condición (New/Used)");

                    Optional<String> conditionResult = conditionDialog.showAndWait();

                    if (conditionResult.isEmpty()) {
                        return;
                    }

                    String condition = conditionResult.get();

                    TextInputDialog distributorDialog = new TextInputDialog();
                    distributorDialog.setHeaderText("Distribuidor");

                    Optional<String> distributorResult = distributorDialog.showAndWait();

                    if (distributorResult.isEmpty()) {
                        return;
                    }

                    String distributor = distributorResult.get();

                    PhysicalVideoGame game = new PhysicalVideoGame(
                            title,
                            price,
                            platform,
                            stock,
                            genre,
                            condition,
                            distributor
                    );

                    videoGameService.addVideoGame(game);
                }

                System.out.println("Videojuego agregado correctamente");

            } catch (Exception ex) {
                ex.printStackTrace();
            }

        });
        btnListGames.setOnAction(e -> {

            try {

                StringBuilder gamesText = new StringBuilder();

                for (var game : videoGameService.getAllVideoGames()) {

                    gamesText.append("Título: ")
                            .append(game.getTitle())
                            .append("\nPrecio: $")
                            .append(game.getPrice())
                            .append("\nPlataforma: ")
                            .append(game.getPlatform())
                            .append("\nStock: ")
                            .append(game.getStock())
                            .append("\nGénero: ")
                            .append(game.getGenre())
                            .append("\n\n");
                }

                javafx.scene.control.Alert alert =
                        new javafx.scene.control.Alert(
                                javafx.scene.control.Alert.AlertType.INFORMATION
                        );

                alert.setTitle("Catálogo");
                alert.setHeaderText("Videojuegos registrados");

                if (gamesText.length() == 0) {
                    alert.setContentText("No hay videojuegos registrados.");
                } else {
                    alert.setContentText(gamesText.toString());
                }

                alert.showAndWait();

            } catch (Exception ex) {
                ex.printStackTrace();
            }

        });
        btnSearchTitle.setOnAction(e -> {

            try {

                TextInputDialog dialog = new TextInputDialog();

                dialog.setTitle("Buscar Videojuego");
                dialog.setHeaderText("Ingrese el título");

                Optional<String> result = dialog.showAndWait();

                if (result.isEmpty()) {
                    return;
                }

                String title = result.get();

                var game = videoGameService.findByTitle(title);

                javafx.scene.control.Alert alert =
                        new javafx.scene.control.Alert(
                                javafx.scene.control.Alert.AlertType.INFORMATION
                        );

                alert.setTitle("Resultado");

                if (game == null) {

                    alert.setHeaderText("Videojuego no encontrado");
                    alert.setContentText(
                            "No existe un videojuego con ese título."
                    );

                } else {

                    alert.setHeaderText("Videojuego encontrado");

                    alert.setContentText(
                            "Título: " + game.getTitle() +
                                    "\nPrecio: $" + game.getPrice() +
                                    "\nPlataforma: " + game.getPlatform() +
                                    "\nStock: " + game.getStock() +
                                    "\nGénero: " + game.getGenre()
                    );
                }

                alert.showAndWait();

            } catch (Exception ex) {
                ex.printStackTrace();
            }

        });
        btnSearchPlatform.setOnAction(e -> {

            try {

                TextInputDialog dialog = new TextInputDialog();

                dialog.setTitle("Buscar por Plataforma");
                dialog.setHeaderText("Ingrese la plataforma");

                Optional<String> result = dialog.showAndWait();

                if (result.isEmpty()) {
                    return;
                }

                String platform = result.get();

                var games = videoGameService.findByPlatform(platform);

                StringBuilder info = new StringBuilder();

                for (var game : games) {

                    info.append(game.getTitle())
                            .append(" - $")
                            .append(game.getPrice())
                            .append("\n");
                }

                javafx.scene.control.Alert alert =
                        new javafx.scene.control.Alert(
                                javafx.scene.control.Alert.AlertType.INFORMATION
                        );

                alert.setTitle("Resultados");

                if (games.isEmpty()) {

                    alert.setHeaderText("Sin resultados");
                    alert.setContentText(
                            "No hay videojuegos para esa plataforma."
                    );

                } else {

                    alert.setHeaderText(
                            "Videojuegos encontrados"
                    );

                    alert.setContentText(
                            info.toString()
                    );
                }

                alert.showAndWait();

            } catch (Exception ex) {
                ex.printStackTrace();
            }

        });
        btnSellGame.setOnAction(e -> {

            try {

                TextInputDialog titleDialog = new TextInputDialog();

                titleDialog.setTitle("Venta");
                titleDialog.setHeaderText("Ingrese el título del videojuego");

                Optional<String> titleResult =
                        titleDialog.showAndWait();

                if (titleResult.isEmpty()) {
                    return;
                }

                String title = titleResult.get();

                TextInputDialog qtyDialog = new TextInputDialog();

                qtyDialog.setTitle("Venta");
                qtyDialog.setHeaderText("Ingrese la cantidad");

                Optional<String> qtyResult =
                        qtyDialog.showAndWait();

                if (qtyResult.isEmpty()) {
                    return;
                }

                int quantity =
                        Integer.parseInt(qtyResult.get());

                org.example.service.SaleService saleService =
                        new org.example.service.SaleService();

                double total =
                        saleService.sellVideoGame(
                                title,
                                quantity
                        );

                javafx.scene.control.Alert alert =
                        new javafx.scene.control.Alert(
                                javafx.scene.control.Alert.AlertType.INFORMATION
                        );

                alert.setTitle("Venta realizada");
                alert.setHeaderText("Venta exitosa");
                alert.setContentText(
                        "Total de la venta: $" + total
                );

                alert.showAndWait();

            } catch (Exception ex) {

                javafx.scene.control.Alert alert =
                        new javafx.scene.control.Alert(
                                javafx.scene.control.Alert.AlertType.ERROR
                        );

                alert.setTitle("Error");
                alert.setHeaderText("No se pudo realizar la venta");
                alert.setContentText(ex.getMessage());

                alert.showAndWait();
            }

        });
        btnShowSales.setOnAction(e -> {

            try {

                org.example.service.SaleService saleService =
                        new org.example.service.SaleService();

                var sales = saleService.getAllSales();

                StringBuilder info = new StringBuilder();

                for (var sale : sales) {

                    info.append("ID: ")
                            .append(sale.getId())
                            .append("\nJuego: ")
                            .append(sale.getVideoGameTitle())
                            .append("\nCantidad: ")
                            .append(sale.getQuantity())
                            .append("\nTotal: $")
                            .append(sale.getTotal())
                            .append("\nFecha: ")
                            .append(sale.getSaleDate())
                            .append("\n\n");
                }

                javafx.scene.control.Alert alert =
                        new javafx.scene.control.Alert(
                                javafx.scene.control.Alert.AlertType.INFORMATION
                        );

                alert.setTitle("Ventas");

                if (sales.isEmpty()) {

                    alert.setHeaderText("Sin ventas");
                    alert.setContentText(
                            "No hay ventas registradas."
                    );

                } else {

                    alert.setHeaderText(
                            "Historial de ventas"
                    );

                    alert.setContentText(
                            info.toString()
                    );
                }

                alert.showAndWait();

            } catch (Exception ex) {
                ex.printStackTrace();
            }

        });

        btnExit.setOnAction(e -> stage.close());

        VBox root = new VBox(15);

        root.getChildren().addAll(
                btnAddGame,
                btnListGames,
                btnSearchTitle,
                btnSearchPlatform,
                btnSellGame,
                btnShowSales,
                btnExit
        );

        root.setAlignment(Pos.CENTER);
        root.setPadding(new Insets(20));

        Scene scene = new Scene(root, 500, 450);

        stage.setTitle("GameZone");
        stage.setScene(scene);
        stage.show();
    }
}
