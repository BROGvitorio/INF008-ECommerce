package br.edu.ifba.inf008.plugins;

import br.edu.ifba.inf008.domain.ShippingMethod;

import br.edu.ifba.inf008.interfaces.core.ICore;
import br.edu.ifba.inf008.interfaces.core.IUIController;

import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.GridPane;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import java.util.List;

public class ShippingView {
    private IUIController uiController = ICore.getInstance().getUIController();
    private final ShippingService service;

    public ShippingView(ShippingService service) {
        this.service = service;
        uiController = ICore.getInstance().getUIController();
        service.setView(this);
    }

    private VBox createCard(ShippingMethod method, ToggleGroup group) {
        RadioButton radio = new RadioButton(method.getName());
        radio.setToggleGroup(group);

        Label price = new Label("Base Price: R$ " + method.getBaseCost());
        Label days = new Label("Estimated: " + method.getEstimatedDays() + " days");

        VBox card = new VBox(10);
        card.setPadding(new Insets(15));
        card.setStyle(
            "-fx-border-color:#CCCCCC;" + "-fx-border-radius:8;" +
            "-fx-background-radius:8;" + "-fx-background-color:white;"
        );

        card.getChildren().addAll(radio, price, days);

        return card;
    }
    
    public VBox createView() {
        Label lbladdress = new Label("Delivery Address");

        TextField addressField = new TextField();
        addressField.setPromptText("Enter your full address");
        addressField.setMaxWidth(Double.MAX_VALUE);
        addressField.setPrefHeight(40);

        VBox address = new VBox(5, lbladdress, addressField);

        Label lblshipping = new Label("Shipping Method");

        List<ShippingMethod> methods = service.findAllMethods();
        ToggleGroup shippingGroup = new ToggleGroup();

        HBox shippingCards = new HBox(10);
        for (ShippingMethod method : methods) {
            shippingCards.getChildren().add(createCard(method, shippingGroup));
        }

        VBox shipping = new VBox(5, lblshipping, shippingCards);

        Button calcShipping = new Button("Calculate");
        calcShipping.setPrefWidth(140);
        calcShipping.setPrefHeight(40);
        calcShipping.setStyle(
            "-fx-background-color:#2E64B6;" + "-fx-text-fill:white;" +
            "-fx-font-size:14px;" + "-fx-font-weight:bold;"
        );

        HBox buttonBox = new HBox(calcShipping);
        buttonBox.setAlignment(javafx.geometry.Pos.CENTER);

        VBox shippingInputs = new VBox(20);
        shippingInputs.setPadding(new Insets(15, 0, 0, 0));
        shippingInputs.getChildren().addAll(address, shipping, buttonBox);

        Label shippingLabel = new Label("Shipping");
        shippingLabel.setStyle("-fx-font-size:22px; -fx-font-weight:bold;");

        VBox root = new VBox(15);
        root.setPadding(new Insets(20));
        root.getChildren().addAll(shippingLabel, shippingInputs);

        return root;
    }

    public void show() {
        uiController.createTab("Shipping", createView());
    }
}