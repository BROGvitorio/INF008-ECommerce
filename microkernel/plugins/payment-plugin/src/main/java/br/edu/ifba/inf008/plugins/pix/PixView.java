package br.edu.ifba.inf008.plugins.pix;

import java.util.UUID;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

public class PixView {
    private static String pixCode = null;

    public static VBox setUI() {

        VBox root = new VBox(20);
        root.setPadding(new Insets(20));
        root.setAlignment(Pos.TOP_CENTER);

        Button generateButton = new Button("Generate QR Code");
        generateButton.setPrefHeight(40);
        generateButton.setPrefWidth(200);
        generateButton.setAlignment(Pos.CENTER);

        Separator separator = new Separator();

        ImageView qrCodeView = new ImageView();
        qrCodeView.setFitWidth(160);
        qrCodeView.setFitHeight(160);
        qrCodeView.setPreserveRatio(true);

        TextField pixCodeField = new TextField();
        pixCodeField.setEditable(false);

        Button copyButton = new Button("Copy");
        copyButton.setPrefWidth(70);
        copyButton.setAlignment(Pos.CENTER);

        HBox copyBox = new HBox(5, pixCodeField, copyButton);
        copyBox.setAlignment(Pos.CENTER_LEFT);

        HBox.setHgrow(pixCodeField, Priority.ALWAYS);

        separator.setVisible(false);
        separator.setManaged(false);

        qrCodeView.setVisible(false);
        qrCodeView.setManaged(false);

        copyBox.setVisible(false);
        copyBox.setManaged(false);

        generateButton.setOnAction(event -> {
            pixCode = UUID.randomUUID().toString();

            Image qrImage = new Image(PixView.class.getResourceAsStream("/qrcode.png"));

            qrCodeView.setImage(qrImage);

            pixCodeField.setText(pixCode);

            separator.setManaged(true);
            separator.setVisible(true);

            qrCodeView.setManaged(true);
            qrCodeView.setVisible(true);

            copyBox.setManaged(true);
            copyBox.setVisible(true);

            generateButton.setManaged(false);
            generateButton.setVisible(false);
        });

        copyButton.setOnAction(event -> {
            javafx.scene.input.Clipboard clipboard =
                    javafx.scene.input.Clipboard.getSystemClipboard();

            javafx.scene.input.ClipboardContent content =
                    new javafx.scene.input.ClipboardContent();

            content.putString(pixCodeField.getText());

            clipboard.setContent(content);
        }); 

        root.getChildren().addAll(
                generateButton,
                separator,
                qrCodeView,
                copyBox
        );

        return root;
    }

    public static String getPixCode() {
        return pixCode;
    }
}