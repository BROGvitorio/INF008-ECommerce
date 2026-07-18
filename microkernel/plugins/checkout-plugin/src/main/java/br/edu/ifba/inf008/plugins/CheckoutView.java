package br.edu.ifba.inf008.plugins;

import java.math.BigDecimal;
import java.util.function.Supplier;

import br.edu.ifba.inf008.interfaces.ICore;
import br.edu.ifba.inf008.interfaces.IUIController;

import br.edu.ifba.inf008.domain.Cart;
import br.edu.ifba.inf008.domain.CartItem;

import javafx.geometry.Insets;
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.Node;
import javafx.scene.control.Tab;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Separator;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;

public class CheckoutView {
    private static IUIController uiController = null;
    private static Tab checkoutTab = null;

    private static final IntegerProperty itemsCount = new SimpleIntegerProperty(0);
    private static final StringProperty itemsTotal = new SimpleStringProperty("$ 0.00");

    private static final StringProperty errorMessage = new SimpleStringProperty("");

    private static TableView<CartItem> itemsTable = new TableView<CartItem>();

    static {
        uiController = ICore.getInstance().getUIController();
    }

    private static VBox createShippingBox () {
        TextField addressField = new TextField();
        addressField.setPromptText("Enter your full address");
        addressField.setMaxWidth(Double.MAX_VALUE); 
        addressField.setPrefHeight(40);
        HBox.setHgrow(addressField, Priority.ALWAYS);

        ComboBox<String> shippingCombo = new ComboBox<>();
        shippingCombo.getItems().addAll(
                "Standard Shipping ($5.00)",
                "Express Shipping ($15.00)",
                "Next Day ($25.00)"
        );
        shippingCombo.getSelectionModel().selectFirst();
        shippingCombo.setPrefHeight(40);
        HBox.setMargin(shippingCombo, new Insets(0, 10, 0, 10));
        
        Button calcShipping = new Button("Calculate");
        calcShipping.setPrefWidth(100);
        calcShipping.setPrefHeight(40);
        calcShipping.setStyle(
            "-fx-background-color:#2E64B6;" +
            "-fx-text-fill:white;" +
            "-fx-font-size:14px;" +
            "-fx-font-weight:bold;"
        );
        
        HBox shippingInputs = new HBox();
        shippingInputs.getChildren().addAll(addressField, shippingCombo, calcShipping);
        
        Label shippingLabel = new Label("Shipping");
        shippingLabel.setStyle("-fx-font-size:22px; -fx-font-weight:bold;");
        VBox.setMargin(shippingLabel, new Insets(0, 0, 5, 0));
        
        VBox root = new VBox();
        root.getChildren().addAll(shippingLabel, shippingInputs);

        return root;
    }

    private static void createItemsTable (Cart cart) {
        TableColumn<CartItem, String> nameCol = new TableColumn<>("NAME");
        nameCol.setCellValueFactory(cellData -> new SimpleStringProperty(
                cellData.getValue().getProduct().getName())
        );
        nameCol.setResizable(false);
        nameCol.setReorderable(false);
        
        TableColumn<CartItem, Integer> quantityCol = new TableColumn<>("QUANTITY");
        quantityCol.setCellValueFactory(cellData -> new SimpleObjectProperty<>(
            cellData.getValue().getQuantity())
        );
        quantityCol.setStyle("-fx-alignment: CENTER;");
        quantityCol.setResizable(false);
        quantityCol.setReorderable(false);
        
        TableColumn<CartItem, String> priceCol = new TableColumn<>("PRICE");
        priceCol.setCellValueFactory(cell -> new SimpleStringProperty(
                String.format("$%.2f",
                        cell
                        .getValue()
                        .getUnitPrice()
                        .multiply(BigDecimal.valueOf(cell.getValue().getQuantity()))))
        );
        priceCol.setStyle("-fx-alignment: CENTER-RIGHT;");
        priceCol.setResizable(false);
        priceCol.setReorderable(false);
        
        nameCol.prefWidthProperty().bind(itemsTable.widthProperty().multiply(0.7));
        quantityCol.prefWidthProperty().bind(itemsTable.widthProperty().multiply(0.1));
        priceCol.prefWidthProperty().bind(itemsTable.widthProperty().multiply(0.15));

        itemsTable.setStyle(
            "-fx-background-color: white;" +
            "-fx-font-size:18px;" +
            "-fx-border-color: transparent;" +
            "-fx-table-cell-border-color: transparent;"
        );
        
        itemsTable.getColumns().clear();
        itemsTable.getItems().clear();
        
        itemsTable.getItems().setAll(cart.getItems());
        itemsTable.getColumns().addAll(nameCol, quantityCol, priceCol);

    }

    public static void createCheckoutPage(
        Cart cart,
        Supplier<Integer> getItemsCount, 
        Supplier<BigDecimal> getItemsTotal
    ) {

        BorderPane root = new BorderPane();
        root.setPadding(new Insets(20));

        VBox left = new VBox(18);
        left.setPadding(new Insets(20));
        left.setPrefWidth(650);
        left.setStyle(
            "-fx-background-color:white;" +
            "-fx-background-radius:8;" +
            "-fx-border-color:#DDDDDD;" +
            "-fx-border-radius:8;"
        );

        Label summaryTitle = new Label("Checkout");
        summaryTitle.setStyle("-fx-font-size:28px; -fx-font-weight:bold;");

        Separator s1 = new Separator();
        createItemsTable(cart);
        VBox.setVgrow(itemsTable, Priority.ALWAYS);

        Separator s2 = new Separator();
        VBox shipping = createShippingBox();

        Label discountLabel = new Label("Apply Discount:");
        discountLabel.setStyle("-fx-font-size:22px; -fx-font-weight:bold;");
        ToggleGroup discountGroup = new ToggleGroup();
        RadioButton promo = new RadioButton("Promo Code");
        promo.setToggleGroup(discountGroup);
        promo.setSelected(true);
        RadioButton student = new RadioButton("Student Discount");
        student.setToggleGroup(discountGroup);
        HBox radioBox = new HBox(20, promo, student);
        TextField promoField = new TextField();
        promoField.setPromptText("Enter Promo Code");
        HBox.setHgrow(promoField, Priority.ALWAYS);
        Button applyButton = new Button("Apply");
        applyButton.setPrefWidth(130);
        HBox promoBox = new HBox(10, promoField, applyButton);

        Separator s3 = new Separator();

        VBox totals = new VBox(8);

        itemsTotal.set(String.format("$ %.2f", getItemsTotal.get()));

        totals.getChildren().addAll(
                createTotalRow("Items Total:", itemsTotal.getValue()),
                createTotalRow("Shipping:", "$5.00"),
                createTotalRow("Discount Applied:", "-$20.00", "#2BA84A"),
                new Separator(),
                createTotalRow("Total", "$130.00", true)
        );

        left.getChildren().addAll(
                summaryTitle,
                s1,
                itemsTable,
                s2,
                shipping,
                discountLabel,
                radioBox,
                promoBox,
                s3,
                totals
        );

        VBox right = new VBox(18);
        right.setPadding(new Insets(20));
        right.setPrefWidth(420);

        right.setStyle(
            "-fx-background-color:white;" +
            "-fx-background-radius:8;" +
            "-fx-border-color:#DDDDDD;" +
            "-fx-border-radius:8;"
        );

        Label paymentTitle = new Label("Payment");
        paymentTitle.setStyle("-fx-font-size:28px; -fx-font-weight:bold;");

        Separator paymentSeparator = new Separator();

        ComboBox<String> paymentCombo = new ComboBox<>();
        paymentCombo.getItems().addAll(
                "Credit Card",
                "Debit Card",
                "PIX",
                "Cash"
        );
        paymentCombo.getSelectionModel().selectFirst();

        Label cardHolder = new Label("Cardholder Name");
        TextField holderField = new TextField();

        Label cardNumber = new Label("Card Number");
        TextField cardField = new TextField();
        cardField.setPromptText("XXXX-XXXX-XXXX-XXXX");

        Label expiry = new Label("Expiry Date (MM/YY)");
        TextField expiryField = new TextField();

        Label cvv = new Label("CVV");
        PasswordField cvvField = new PasswordField();

        VBox expiryBox = new VBox(5, expiry, expiryField);
        VBox cvvBox = new VBox(5, cvv, cvvField);

        HBox dateRow = new HBox(15, expiryBox, cvvBox);
        HBox.setHgrow(expiryBox, Priority.ALWAYS);
        HBox.setHgrow(cvvBox, Priority.ALWAYS);

        expiryField.setMaxWidth(Double.MAX_VALUE);
        cvvField.setMaxWidth(Double.MAX_VALUE);

        Separator paymentSeparator2 = new Separator();

        Button placeOrder = new Button("Checkout");
        placeOrder.setMaxWidth(Double.MAX_VALUE);
        placeOrder.setPrefHeight(50);

        placeOrder.setStyle(
            "-fx-background-color:#2E64B6;" +
            "-fx-text-fill:white;" +
            "-fx-font-size:24px;" + 
            "-fx-font-weight:bold;"        
        );

        right.getChildren().addAll(
                paymentTitle,
                paymentSeparator,
                paymentCombo,
                cardHolder,
                holderField,
                cardNumber,
                cardField,
                dateRow,
                paymentSeparator2,
                placeOrder
        );

        HBox content = new HBox(20, left, right);

        HBox.setHgrow(left, Priority.ALWAYS);
        HBox.setHgrow(right, Priority.NEVER);

        root.setCenter(content);

        checkoutTab = uiController.createTab("Checkout", root);
    }

    private static BorderPane createTotalRow (
        String label,
        String value,
        String color,
        boolean bold
    ) {

        Label left = new Label(label);
        Label right = new Label(value);

        String style = bold
                ? "-fx-font-size:22px; -fx-font-weight:bold;"
                : "-fx-font-size:20px;";

        left.setStyle(style + "-fx-text-fill:" + color + ";");
        right.setStyle(style + "-fx-text-fill:" + color + ";");

        BorderPane pane = new BorderPane();
        pane.setLeft(left);
        pane.setRight(right);

        return pane;
    }

    private static BorderPane createTotalRow(String label, String value) {
        return createTotalRow(label, value, "-fx-text-base-color", false);
    }

    private static BorderPane createTotalRow(String label, String value, boolean bold) {
        return createTotalRow(label, value, "-fx-text-base-color", bold);
    }

    private static BorderPane createTotalRow(String label, String value, String color) {
        return createTotalRow(label, value, color, false);
    }
}
