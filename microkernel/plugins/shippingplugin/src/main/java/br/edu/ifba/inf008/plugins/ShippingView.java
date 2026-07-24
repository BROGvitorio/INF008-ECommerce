package br.edu.ifba.inf008.plugins;

import br.edu.ifba.inf008.domain.ShippingMethod;

import br.edu.ifba.inf008.interfaces.core.ICore;
import br.edu.ifba.inf008.interfaces.core.IUIController;

import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
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
    
    public VBox createView() {
        TextField addressField = new TextField();
        addressField.setPromptText("Enter your full address");
        addressField.setMaxWidth(Double.MAX_VALUE); 
        addressField.setPrefHeight(40);
        HBox.setHgrow(addressField, Priority.ALWAYS);

        ComboBox<String> shippingCombo = new ComboBox<>();
        List<ShippingMethod> shippingMethods = service.findAllMethods();
        
        for (ShippingMethod method : shippingMethods) {
            shippingCombo.getItems().add(method.getName());
        }

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

    public void show() {
        uiController.createTab("Shipping", createView());
    }
}