package br.edu.ifba.inf008.plugins;

import br.edu.ifba.inf008.interfaces.IPlugin;
import br.edu.ifba.inf008.interfaces.ICore;
import br.edu.ifba.inf008.interfaces.IUIController;
import br.edu.ifba.inf008.interfaces.IPersistenceController;
import br.edu.ifba.inf008.domain.Product;

import javafx.scene.control.ScrollPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.geometry.Insets;
import javafx.scene.text.Text;
import javafx.geometry.Pos;
import java.util.List;

public class CatalogPlugin implements IPlugin
{
    private void ProductListing() {
        FlowPane catalog = new FlowPane();
        catalog.setHgap(15);
        catalog.setVgap(15);
        catalog.setPadding(new Insets(15));

        Label title = new Label("Catálogo de Produtos");
        title.setStyle("-fx-font-size: 24px;" + "-fx-font-weight: bold;");

        IPersistenceController persistenceController = ICore.getInstance().getPersistenceController();
        List<Product> products = persistenceController.findAll(Product.class);

        for (Product p : products) {
            VBox productBox  = new VBox(10);
            productBox.setPadding(new Insets(15));
            productBox.setAlignment(Pos.CENTER);
            productBox.setPrefSize(200, 170);
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

            Label unitPrice = new Label("R$ " + p.getUnitPrice());
            unitPrice.setStyle("-fx-font-weight: bold;");

            Button addCart = new Button("Adicionar ao Carrinho");
            addCart.setStyle(
                "-fx-background-color: #1976D2;" + "-fx-text-fill: white;" +
                "-fx-background-radius: 6;" + "-fx-cursor: hand;"
            );
            
            productBox.getChildren().addAll(name, description, unitPrice, addCart);

            catalog.getChildren().add(productBox);
        }

        VBox root = new VBox(15);
        root.setPadding(new Insets(15));

        root.getChildren().addAll(title, catalog);

        ScrollPane scroll = new ScrollPane(root);
        scroll.setFitToWidth(true);

        IUIController uiController = ICore.getInstance().getUIController();
        uiController.createTab("Catalog", scroll);
    }

    public boolean init() {
        ProductListing();

        return true;
    }
}
