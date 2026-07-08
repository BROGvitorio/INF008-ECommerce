package br.edu.ifba.inf008.plugins;

import java.math.BigDecimal;

import br.edu.ifba.inf008.domain.Cart;
import br.edu.ifba.inf008.domain.CartItem;
import br.edu.ifba.inf008.domain.Product;
import br.edu.ifba.inf008.interfaces.ICore;
import br.edu.ifba.inf008.interfaces.IUIController;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Tab;
import javafx.scene.control.TableColumn;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.control.TableView;

public class CartView {
    private static IUIController uiController = null;
    private static Tab cartTab = null;
    
    static {
        uiController = ICore.getInstance().getUIController();
    }

    private static Product testP = new Product(
        "testP-00",
        "testP",
        "testeeee",
        new BigDecimal(1.00)
    );

    public static MenuItem createMenuItem (String menutext, String menuItemText) {
        MenuItem menuItem = uiController.createMenuItem(menutext, menuItemText);
        return menuItem;
    }

    public static void createMenuItem (String menutext, String menuItemText, EventHandler<ActionEvent> eh) {
        createMenuItem(menutext, menuItemText).setOnAction(eh);
    }

    public static void createCartTab (Cart cart) {
        try {
            if (cartTab != null)
                throw new IllegalStateException("A cart tab already exists.");
    
            Label title = new Label("Seu carrinho");
            title.setStyle("-fx-font-size: 24px; -fx-font-weight: bold;");
    
            Label subtitle = new Label("Seus itens");
            subtitle.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");
    
            TableView<CartItem> table = new TableView<>();
            TableColumn<CartItem, String> productColumn = new TableColumn<>("Product Name");
            productColumn.setCellValueFactory(cellData ->
                new SimpleStringProperty(
                    cellData.getValue().getProduct().getName()
                )
            );

            TableColumn<CartItem, Integer> quantityColumn = new TableColumn<>("Quantity");
            quantityColumn.setCellValueFactory(cellData ->
                new SimpleObjectProperty<>(
                    cellData.getValue().getQuantity()
                )
            );

            TableColumn<CartItem, BigDecimal> unitPriceColumn = new TableColumn<>("Unit Price");
            unitPriceColumn.setCellValueFactory(cellData ->
                new SimpleObjectProperty<>(
                    cellData.getValue().getUnitPrice()
                )
            );

            table.getColumns().addAll(productColumn, quantityColumn, unitPriceColumn);


            table.getItems().add(
                new CartItem(
                    cart, 
                    testP,
                    new Integer(2),
                    testP.getUnitPrice()
                )
            );
    
            Button cancelButton = new Button("Cancelar");
            Button checkoutButton = new Button("Checkout");
    
            HBox buttons = new HBox(10, cancelButton, checkoutButton);
            buttons.setAlignment(Pos.CENTER_RIGHT);
    
            VBox root = new VBox(15, title, subtitle, table, buttons);
            root.setPadding(new Insets(20));
            VBox.setVgrow(table, Priority.ALWAYS);
    
            cartTab = uiController.createTab (
                "Current Cart",
                root
            );
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }


}
