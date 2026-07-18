package br.edu.ifba.inf008.plugins;

import java.util.List;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class PaymentView {
    private static ComboBox<String> paymentCombo = new ComboBox<>();
    private static VBox paymentMethodInputs;
    private static final StringProperty errorMessage = new SimpleStringProperty("");

    public static VBox setUI(
            List<IPayable> paymentMethods,
            Runnable onCheckout) {
        VBox right = new VBox(18);
        right.setPadding(new Insets(20));
        right.setPrefWidth(420);

        right.setStyle(
                "-fx-background-color:white;" +
                        "-fx-background-radius:8;" +
                        "-fx-border-color:#DDDDDD;" +
                        "-fx-border-radius:8;");

        Label paymentTitle = new Label("Payment");
        paymentTitle.setStyle("-fx-font-size:28px; -fx-font-weight:bold;");

        Separator s1 = new Separator();

        paymentCombo.getItems().clear();
        for (IPayable pm : paymentMethods) {
            paymentCombo.getItems().add(pm.getName());
        }
        paymentCombo.getSelectionModel().selectFirst();
        paymentMethodInputs = getSelectedMethod(paymentMethods).getUI();
        paymentCombo
                .getSelectionModel()
                .selectedItemProperty()
                .addListener((observable, oldString, newString) -> {
                    if (newString != null) {
                        PaymentView.showErrorMessage("");
                        IPayable selectedMethod = PaymentView.getSelectedMethod(paymentMethods);

                        int index = right.getChildren().indexOf(paymentMethodInputs);
                        paymentMethodInputs = selectedMethod.getUI();
                        if (index != -1)
                            right.getChildren().set(index, paymentMethodInputs);

                    }
                });

        Separator s2 = new Separator();

        Button checkout = new Button("Checkout");
        checkout.setMaxWidth(Double.MAX_VALUE);
        checkout.setPrefHeight(50);
        checkout.setOnAction(e -> onCheckout.run());

        checkout.setStyle(
                "-fx-background-color:#2E64B6;" +
                        "-fx-text-fill:white;" +
                        "-fx-font-size:24px;" +
                        "-fx-font-weight:bold;");

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

        right.getChildren().addAll(
                paymentTitle,
                s1,
                paymentCombo,
                paymentMethodInputs,
                s2,
                checkout,
                s3,
                errorBox);

        return right;
    }

    public static IPayable getSelectedMethod(List<IPayable> paymentMethods) {
        for (IPayable p : paymentMethods) {
            if (paymentCombo.getValue().equalsIgnoreCase(p.getName()))
                return p;
        }

        return null;
    }

    public static void showErrorMessage(String message) {
        errorMessage.set(message);
    }

    public static void showOrderConfirmedPopup() {

        Alert alert = new Alert(Alert.AlertType.INFORMATION);

        alert.setTitle("Order Confirmation");
        alert.setHeaderText(null);
        alert.setContentText("Your order has been confirmed!");

        alert.getButtonTypes().setAll(ButtonType.OK);

        ImageView imageView = new ImageView(
            new Image(PaymentView.class.getResourceAsStream("/confirmed-order-icon.png"))
        );
        imageView.setFitWidth(70);
        imageView.setFitHeight(70); 
        imageView.setPreserveRatio(true);
        alert.getDialogPane().setGraphic(imageView);

        alert.showAndWait();
    }
}
