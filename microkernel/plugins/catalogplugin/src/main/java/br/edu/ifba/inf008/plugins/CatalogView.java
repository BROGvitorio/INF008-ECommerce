package br.edu.ifba.inf008.plugins;

import br.edu.ifba.inf008.interfaces.ICore;
import br.edu.ifba.inf008.interfaces.IUIController;
import br.edu.ifba.inf008.interfaces.IPersistenceController;
import br.edu.ifba.inf008.domain.Product;

import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.control.TextArea;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.geometry.Insets;
import javafx.scene.text.Text;
import javafx.geometry.HPos;
import javafx.geometry.Pos;

import java.util.concurrent.atomic.AtomicInteger;
import java.math.BigDecimal;
import java.util.List;

public class CatalogView
{
    private FlowPane catalog;
    private final CatalogService service;

    public CatalogView(CatalogService service) {
        this.service = service;
    }

    private void loadProducts(List<Product> products) {
        catalog.getChildren().clear();

        for (Product p : products) {
            VBox productBox = new VBox(10);
            productBox.setPadding(new Insets(15));
            productBox.setAlignment(Pos.CENTER);
            productBox.setPrefSize(217, 170);
            productBox.setSpacing(10);
            productBox.setStyle(
                "-fx-border-color: #DADADA;" + "-fx-border-width: 1;" + 
                "-fx-border-radius: 8;" + "-fx-background-radius: 8;"
            );

            Label name = new Label(p.getName());
            name.setMaxWidth(Double.MAX_VALUE);
            name.setStyle(
                "-fx-alignment: center;" + "-fx-font-weight: bold;" + "-fx-font-size: 14;"
            );

            Text description = new Text(p.getDescription());
            description.setWrappingWidth(180);

            Label sku = new Label("Code: " + p.getSku());
            sku.setStyle("-fx-font-size: 11px; -fx-text-fill: #666;");

            Label stock = new Label("Stock: " + service.getAvailableStock(p));
            stock.setStyle("-fx-font-size: 11px; -fx-text-fill: #2E7D32;");

            Label unitPrice = new Label("$ " + p.getUnitPrice());
            unitPrice.setStyle("-fx-font-weight: bold;");

            Button addCart = new Button("Add to Cart");
            addCart.setStyle(
                "-fx-background-color: #1976D2;" + "-fx-text-fill: white;" +
                "-fx-background-radius: 6;" + "-fx-cursor: hand;"
            );

            HBox infoBox = new HBox(15);
            infoBox.setAlignment(Pos.CENTER_LEFT);

            infoBox.getChildren().addAll(sku, stock);

            productBox.getChildren().addAll(name, description, infoBox, unitPrice, addCart);

            catalog.getChildren().add(productBox);
        }
    }

    //Cadastro de produtos
    private StackPane createForm() {
        Label title = new Label("Product Registration");
        title.setStyle("-fx-font-size: 24px;" + "-fx-font-weight: bold;");

        VBox form = new VBox(15);

        String fieldStyle = 
            "-fx-background-radius: 6;" + "-fx-border-radius: 6;" +
            "-fx-border-color: #CCCCCC;" + "-fx-font-size: 14px;"
        ;

        Label lblSku = new Label("Code");
        TextField txtSku = new TextField();
        txtSku.setMaxWidth(Double.MAX_VALUE);
        txtSku.setStyle(fieldStyle);

        Label lblName = new Label("Name");
        TextField txtName = new TextField();
        txtName.setMaxWidth(Double.MAX_VALUE);
        txtName.setStyle(fieldStyle);
        
        Label lblDescription = new Label("Description");
        TextArea txtDescription = new TextArea();
        txtDescription.setMaxWidth(Double.MAX_VALUE);
        txtDescription.setStyle(fieldStyle);
        txtDescription.setPrefRowCount(6);
        
        Label lblPrice = new Label("Price");
        TextField txtPrice = new TextField();
        txtPrice.setMaxWidth(Double.MAX_VALUE);
        txtPrice.setStyle(fieldStyle);

        VBox skuField = new VBox(5, lblSku, txtSku);
        VBox nameField = new VBox(5, lblName, txtName);
        VBox descriptionField = new VBox(5, lblDescription, txtDescription);
        VBox PriceField = new VBox(5, lblPrice, txtPrice);

        form.getChildren().addAll(skuField, nameField, descriptionField, PriceField);

        Button save = new Button("Save");
        save.setStyle(
            "-fx-background-color: #1976D2;" + "-fx-text-fill: white;" + 
            "-fx-font-weight: bold;" + "-fx-padding: 8 22;" + "-fx-background-radius: 6;" +
            "-fx-cursor: hand;"
        );

        save.setOnAction(event -> {
            String sku = txtSku.getText();
            String name = txtName.getText();
            String description = txtDescription.getText();
            BigDecimal price = new BigDecimal(txtPrice.getText());

            Product product = new Product(sku, name, description, price);
            service.createProduct(product);

            List<Product> products = service.findAllProducts();
            loadProducts(products);
            
            txtSku.clear();
            txtName.clear();
            txtDescription.clear();
            txtPrice.clear();
        });

        HBox button = new HBox(10, save);
        button.setAlignment(Pos.CENTER_RIGHT);
        
        VBox card = new VBox(15);
        card.setMaxWidth(650);
        card.setPadding(new Insets(30));
        card.setStyle(
            "-fx-background-radius: 10;" + "-fx-border-color: #DDDDDD;" + 
            "-fx-border-radius: 10;"
        );

        card.getChildren().addAll(title, form, button);

        StackPane root = new StackPane(card);
        root.setPadding(new Insets(30));
        root.setAlignment(Pos.TOP_CENTER);
        root.setStyle("-fx-background-color: #F5F5F5;");

        return root;
    }

    //Listagem de produtos
    private ScrollPane createCatalog() {
        Label title = new Label("Product Catalog");
        title.setStyle("-fx-font-size: 24px;" + "-fx-font-weight: bold;");

        HBox filter = createFilter();

        catalog = new FlowPane();
        catalog.setHgap(15);
        catalog.setVgap(15);

        List<Product> products = service.findAllProducts();
        loadProducts(products);

        VBox root = new VBox(15);
        root.setPadding(new Insets(15));

        root.getChildren().addAll(title, filter, catalog);

        ScrollPane scroll = new ScrollPane(root);
        scroll.setFitToWidth(true);

        return scroll;
    }

    //Consulta de produtos
    private HBox createFilter() {
        String fieldStyle = 
            "-fx-background-radius: 6;" + "-fx-border-radius: 6;" +
            "-fx-border-color: #CCCCCC;" + "-fx-font-size: 14px;"
        ;

        TextField txtFilter = new TextField();
        txtFilter.setPromptText("Research...");
        txtFilter.setMaxWidth(Double.MAX_VALUE);
        txtFilter.setStyle(fieldStyle);

        Button search = new Button("Search");
        search.setStyle(
            "-fx-background-color: #1976D2;" + "-fx-text-fill: white;" + 
            "-fx-font-weight: bold;" + "-fx-padding: 8 22;" + "-fx-background-radius: 6;" +
            "-fx-cursor: hand;"
        );

        search.setOnAction(event -> {
            String filter = txtFilter.getText();

            List<Product> products = service.findByNameProduct(filter);
            loadProducts(products);
        });

        HBox filter = new HBox(10, txtFilter, search);
        filter.setAlignment(Pos.CENTER);

        return filter;
    }

    public void show() {
        IUIController uiController = ICore.getInstance().getUIController();
        uiController.createTab("Catalog", createCatalog());
        uiController.createTab("Form", createForm());
    } 
}