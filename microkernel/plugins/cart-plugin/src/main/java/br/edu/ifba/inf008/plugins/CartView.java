package br.edu.ifba.inf008.plugins;

import java.math.BigDecimal;
import java.util.function.BiConsumer;
import java.util.function.Supplier;

import br.edu.ifba.inf008.domain.Cart;
import br.edu.ifba.inf008.domain.CartItem;
import br.edu.ifba.inf008.domain.Product;
import br.edu.ifba.inf008.interfaces.ICore;
import br.edu.ifba.inf008.interfaces.IUIController;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
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
import javafx.scene.paint.Color;
import javafx.scene.control.TableView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Region;
import javafx.scene.control.TableCell;
import javafx.scene.text.Text;
import javafx.scene.control.Separator;

public class CartView {
    private static IUIController uiController = null;
    private static Tab cartTab = null;
    private static TableView<CartItem> table = new TableView<CartItem>();
    private static final StringProperty errorMessage = new SimpleStringProperty("MUITOS ERROS");
    private static final IntegerProperty itemsCount = new SimpleIntegerProperty(0);
    private static final StringProperty subtotal = new SimpleStringProperty("$ 0,00");

    static {
        uiController = ICore.getInstance().getUIController();
    }

    public static MenuItem createMenuItem(String menutext, String menuItemText) {
        MenuItem menuItem = uiController.createMenuItem(menutext, menuItemText);
        return menuItem;
    }

    public static void createMenuItem(String menutext, String menuItemText, EventHandler<ActionEvent> eh) {
        createMenuItem(menutext, menuItemText).setOnAction(eh);
    }

    private static VBox createLeftSide(BiConsumer<CartItem, Integer> onUpdateCartItemQuantity) {
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

        TableColumn<CartItem, String> nameCol = new TableColumn<>("NAME");
        nameCol.setCellValueFactory(cellData -> new SimpleStringProperty(
                cellData.getValue().getProduct().getName()));

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
                });

                plus.setOnAction(e -> {
                    CartItem item = getTableView().getItems().get(getIndex());

                    onUpdateCartItemQuantity.accept(item, 1);
                });
            }

            @Override
            protected void updateItem(Integer item, boolean empty) {
                super.updateItem(item, empty);

                if (empty) {
                    setGraphic(null);
                } else {
                    CartItem cartItem = getTableView().getItems().get(getIndex());
                    quantity.setText(String.valueOf(cartItem.getQuantity()));
                    setGraphic(box);
                }
            }
        });

        TableColumn<CartItem, String> priceCol = new TableColumn<>("PRICE");

        priceCol.setCellValueFactory(cell -> new SimpleStringProperty(
                String.format("$%.2f",
                        cell.getValue().getUnitPrice())));

        TableColumn<CartItem, String> totalCol = new TableColumn<>("TOTAL");

        totalCol.setCellValueFactory(cell -> new SimpleStringProperty(
                String.format("$%.2f",
                        cell
                                .getValue()
                                .getUnitPrice()
                                .multiply(BigDecimal.valueOf(cell.getValue().getQuantity())))));

        table.getColumns().addAll(
                nameCol,
                quantityCol,
                priceCol,
                totalCol);

        Text continueShopping = new Text("<- Continue Shopping");

        left.getChildren().addAll(
                header,
                table,
                continueShopping);
        return left;
    }

    private static VBox createSummary(Cart cart, Runnable onCancel, Runnable onCheckout) {
        VBox summary = new VBox(15);
        summary.setPadding(new Insets(15));
        summary.setPrefWidth(280);

        summary.setStyle("-fx-background-color:#f7f7f7;" + "-fx-border-color:#dddddd;");

        Label summaryTitle = new Label("Order Summary");
        summaryTitle.setStyle("-fx-font-size:18;" + "-fx-font-weight:bold;");

        Separator s1 = new Separator();

        Label itemsLabel = new Label("ITEMS");
        Label itemsValue = new Label();
        itemsValue.textProperty().bind(itemsCount.asString());

        HBox itemsBox = new HBox();
        Region r1 = new Region();
        HBox.setHgrow(r1, Priority.ALWAYS);
        itemsBox.getChildren().addAll(itemsLabel, r1, itemsValue);

        Separator s2 = new Separator();

        Label totalLabel = new Label("TOTAL COST");
        Label totalValue = new Label();
        totalValue.textProperty().bind(subtotal);

        HBox totalBox = new HBox();
        Region r2 = new Region();
        HBox.setHgrow(r2, Priority.ALWAYS);
        totalBox.getChildren().addAll(totalLabel, r2, totalValue);

        HBox buttons = new HBox(10);
        Button checkout = new Button("CHECKOUT");
        checkout.setMaxWidth(Double.MAX_VALUE);
        checkout.prefWidthProperty().bind(buttons.widthProperty().multiply(0.75));
        checkout.setPrefHeight(20);
        checkout.setOnAction(e -> onCheckout.run());
        HBox.setHgrow(checkout, Priority.ALWAYS);

        Button cancel = new Button("✕");
        cancel.setMaxWidth(Double.MAX_VALUE);
        cancel.setPrefHeight(20);
        cancel.setStyle(
                "-fx-background-color:#d32f2f;" +
                        "-fx-text-fill:white;" +
                        "-fx-font-size:12;" +
                        "-fx-font-weight:bold;");
        cancel.prefWidthProperty().bind(buttons.widthProperty().multiply(0.25));
        cancel.setOnAction(e -> onCancel.run());
        HBox.setHgrow(cancel, Priority.ALWAYS);

        buttons.getChildren().addAll(checkout, cancel);

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
                buttons,
                s3,
                errorBox);

        return summary;
    }

    public static void showErrorMessage(String message) {
        errorMessage.set(message);
    }

    public static void createCartTab(
            Cart cart,
            Runnable onCancel,
            Runnable onCheckout,
            BiConsumer<CartItem, Integer> onUpdateCartItemQuantity) {
        try {

            if (cartTab != null)
                throw new IllegalStateException("A cart tab already exists.");

            BorderPane root = new BorderPane();
            root.setPadding(new Insets(20));

            VBox left = createLeftSide(onUpdateCartItemQuantity);
            VBox summary = createSummary(cart, onCancel, onCheckout);

            BorderPane.setMargin(summary, new Insets(0, 0, 0, 10));

            root.setCenter(left);
            root.setRight(summary);

            itemsCount.set(cart.getItems().size());
            table.getItems().setAll(cart.getItems());
            cartTab = uiController.createTab("Shopping Cart", root);

        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }

    }

    public static void updateTable(Cart cart) {
        if (cart == null)
            throw new IllegalStateException("You haven't a shipping cart already.");

        table.getItems().setAll(cart.getItems());
        table.refresh();
    }

    public static void updateSummary (Supplier<Integer> getItemsCount, Supplier<BigDecimal> getSubtotal) {
        int ic = getItemsCount.get();
        BigDecimal st = getSubtotal.get();
        
        itemsCount.set(ic);
        subtotal.set(
            String.format("$ %.2f", st)
        );
    }

    public static void cancelCartTab() {
        uiController.removeTab(cartTab);
        table = new TableView<CartItem>();
        cartTab = null;
    }

    public static void showSubTotal(BigDecimal subTotal) {

    }
}