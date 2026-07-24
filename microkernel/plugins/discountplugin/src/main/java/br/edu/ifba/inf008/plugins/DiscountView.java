package br.edu.ifba.inf008.plugins;

import br.edu.ifba.inf008.interfaces.ICore;
import br.edu.ifba.inf008.interfaces.IUIController;
import br.edu.ifba.inf008.interfaces.IPersistenceController;

import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.control.Label;

public class DiscountView {
    private final DiscountService service;

    public DiscountView(DiscountService service) {
        this.service = service;
        uiController = ICore.getInstance().getUIController();
        service.setView(this);
    }

    private VBox createView() {
        Label title = new Label("Apply Discount");
        title.setStyle("-fx-font-size:22px; -fx-font-weight:bold;");

        TextField promoField = new TextField();
        promoField.setPromptText("Enter Promo Code");
        HBox.setHgrow(promoField, Priority.ALWAYS);

        Button apply = new Button("Apply");
        apply.setPrefWidth(130);
        apply.setStyle(
            "-fx-background-color: #1976D2;" + "-fx-text-fill: white;" + 
            "-fx-font-weight: bold;" + "-fx-cursor: hand;"
        );

        apply.setOnAction(event -> {
            String code = promoField.getText();
            service.applyCoupon(code);
            promoField.clear();
        });

        HBox promoBox = new HBox(10);
        promoBox.getChildren().addAll(promoField, apply);

        VBox root = new VBox(18);
        root.setPadding(new Insets(20));
        root.setPrefWidth(650);
        root.setStyle(
            "-fx-background-color:white;" +
            "-fx-background-radius:8;" +
            "-fx-border-color:#DDDDDD;" +
            "-fx-border-radius:8;"
        );

        root.getChildren().addAll(title, promoBox);

        return root;
    }

    public void show() {
        IUIController uiController = ICore.getInstance().getUIController();
        uiController.createTab("Discount", createView());
    } 
}