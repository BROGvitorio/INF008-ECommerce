package br.edu.ifba.inf008.plugins;

import java.math.BigDecimal;
import java.util.function.BiConsumer;

import br.edu.ifba.inf008.domain.Cart;
import br.edu.ifba.inf008.domain.CartItem;
import br.edu.ifba.inf008.domain.Product;
import br.edu.ifba.inf008.interfaces.ICore;
import br.edu.ifba.inf008.interfaces.IUIController;

import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Tab;
import javafx.scene.control.TableColumn;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.control.TableView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Region;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.TableCell;
import javafx.scene.text.Text;
import javafx.scene.control.Separator;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

public class CartView {
    private static IUIController uiController = null;
    private static Tab cartTab = null;
    private static TableView<CartItem> table = new TableView<CartItem>();
    private static final StringProperty errorMessage = new SimpleStringProperty("MUITOS ERROS");
    
    static {
        uiController = ICore.getInstance().getUIController();
    }

    public static MenuItem createMenuItem (String menutext, String menuItemText) {
        MenuItem menuItem = uiController.createMenuItem(menutext, menuItemText);
        return menuItem;
    }

    public static void createMenuItem (String menutext, String menuItemText, EventHandler<ActionEvent> eh) {
        createMenuItem(menutext, menuItemText).setOnAction(eh);
    }

    private static VBox createLeftSide (BiConsumer<CartItem, Integer> onUpdateCartItemQuantity) {
        VBox left = new VBox(15);
        left.setPrefWidth(700);

        HBox header = new HBox();
        header.setAlignment(Pos.CENTER_LEFT);

        Label title = new Label("Shopping Cart");
        title.setStyle("-fx-font-size:20; -fx-font-weight:bold;");

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        Label items = new Label("3 Items");
        items.setStyle("-fx-font-weight:bold;");

        header.getChildren().addAll(title, spacer, items);

        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        table.setPrefHeight(400);

        // Nome
        TableColumn<CartItem, String> nameCol = new TableColumn<>("NAME");
        nameCol.setCellValueFactory(cellData ->
                new SimpleStringProperty(
                    cellData.getValue().getProduct().getName()
                )
        );


    // Quantidade
    TableColumn<CartItem, Integer> quantityCol = new TableColumn<>("QUANTITY");
    quantityCol.setCellFactory(col -> new TableCell<>() {
        
        private final Button minus = new Button("-");
        private final Button plus = new Button("+");
        private final Label quantity = new Label();
        private final HBox box = new HBox(5, minus, quantity, plus);
        
        {
            box.setAlignment(Pos.CENTER);
            
            minus.setOnAction(e -> {
                CartItem item = getTableView().getItems().get(getIndex());

                onUpdateCartItemQuantity.accept(item, -1);
                
                if(item.getQuantity() > 1){
                    quantity.setText(String.valueOf(item.getQuantity()));
                }
            });
            
            plus.setOnAction(e -> {
                    CartItem item = getTableView().getItems().get(getIndex());

                    onUpdateCartItemQuantity.accept(item, 1);

                    quantity.setText(String.valueOf(item.getQuantity()));
                });
            }
            
            @Override
            protected void updateItem(Integer item, boolean empty) {
                super.updateItem(item, empty);
                
                if(empty) {
                    setGraphic(null);
                } else {
                    CartItem cartItem = getTableView().getItems().get(getIndex());
                    quantity.setText(String.valueOf(cartItem.getQuantity()));
                    setGraphic(box);
                }
            }
        });

        // Preço
        TableColumn<CartItem, String> priceCol = new TableColumn<>("PRICE");

        priceCol.setCellValueFactory(cell ->
                new SimpleStringProperty(
                        String.format("$%.2f",
                                cell.getValue().getUnitPrice())
                ));

        // Total
        TableColumn<CartItem, String> totalCol =
                new TableColumn<>("TOTAL");

        totalCol.setCellValueFactory(cell ->
                new SimpleStringProperty(
                        String.format("$%.2f",
                                cell
                                .getValue()
                                .getUnitPrice()
                                .multiply(BigDecimal.valueOf(cell.getValue().getQuantity()))
                        )
                ));

        table.getColumns().addAll(
                nameCol,
                quantityCol,
                priceCol,
                totalCol
        );

        Text continueShopping =
                new Text("<- Continue Shopping");

        left.getChildren().addAll(
                header,
                table,
                continueShopping
        );
        return left;
    }

    private static VBox createSummary () {
        VBox summary = new VBox(15);
        summary.setPadding(new Insets(15));
        summary.setPrefWidth(280);

        summary.setStyle("-fx-background-color:#f7f7f7;" + "-fx-border-color:#dddddd;");

        Label summaryTitle = new Label("Order Summary");
        summaryTitle.setStyle("-fx-font-size:18;" + "-fx-font-weight:bold;");

        Separator s1 = new Separator();

        Label itemsLabel = new Label("ITEMS");
        Label itemsValue = new Label("3");

        HBox itemsBox = new HBox();
        Region r1 = new Region();
        HBox.setHgrow(r1, Priority.ALWAYS);
        itemsBox.getChildren().addAll(itemsLabel, r1, itemsValue);

        Separator s2 = new Separator();

        Label totalLabel = new Label("TOTAL COST");
        Label totalValue = new Label("$462.98");

        HBox totalBox = new HBox();
        Region r2 = new Region();
        HBox.setHgrow(r2, Priority.ALWAYS);
        totalBox.getChildren().addAll(totalLabel, r2, totalValue);

        Button checkout = new Button("CHECKOUT");
        checkout.setMaxWidth(Double.MAX_VALUE);

        Separator s3 = new Separator();
        VBox errorBox = new VBox(20);
        VBox.setVgrow(errorBox, Priority.SOMETIMES);
        errorBox.setAlignment(Pos.CENTER);
        errorBox.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        errorBox.setFillWidth(true);

        Text errorText = new Text();
        errorText.textProperty().bind(errorMessage);
        errorText.setFill(Color.RED);
        errorBox.getChildren().add(errorText);
        
        summary.getChildren().addAll(
            summaryTitle,
            s1,
            itemsBox,
            s2,
            totalBox,
            checkout,
            s3,
            errorBox
        );
        
        return summary;
    }
    
    public static void showErrorMessage (String message) {
        errorMessage.set(message);
    }

    public static void createCartTab (
        Cart cart, 
        Runnable onCancel, 
        Runnable onCheckout, 
        BiConsumer<CartItem, Integer> onUpdateCartItemQuantity
    ) {
        try {

            if (cartTab != null)
                throw new IllegalStateException("A cart tab already exists.");

            BorderPane root = new BorderPane();
            root.setPadding(new Insets(20));
    
            VBox left = createLeftSide(onUpdateCartItemQuantity);
            VBox summary = createSummary();
    
            BorderPane.setMargin(summary, new Insets(0, 0, 0, 10)); 
    
            root.setCenter(left);
            root.setRight(summary);
    
            table.getItems().setAll(cart.getItems());
            cartTab = uiController.createTab("Shopping Cart", root);

        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }

    }

    public static void updateTable (Cart cart) {
        if (cart == null)
                throw new IllegalStateException("You haven't a shipping cart already.");

        table.getItems().setAll(cart.getItems());
        table.refresh();
    }

    public static void cancelCartTab () {
        uiController.removeTab(cartTab);
        table = new TableView<CartItem>();
        cartTab = null;
    }

    public static void showSubTotal (BigDecimal subTotal) {

    }
}