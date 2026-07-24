package br.edu.ifba.inf008.plugins.boleto;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.Callable;
import java.util.function.Supplier;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

public class BoletoView {
    private static TextField cpfField = new TextField();

    public static VBox setUI(Supplier<String> onGenerate) {

        VBox root = new VBox(20);
        root.setPadding(new Insets(20));
        root.setAlignment(Pos.TOP_CENTER);

        Label cpfLabel = new Label("CPF");
        cpfLabel.setStyle("-fx-font-size: 14px;");

        cpfField.setPromptText("000.000.000-00");
        cpfField.setPrefHeight(40);

        cpfField.setTextFormatter(new TextFormatter<>(change -> {

            if (!change.isContentChange()) {
                return change;
            }

            String text = change.getControlNewText().replaceAll("\\D", "");

            if (text.length() > 11) {
                text = text.substring(0, 11);
            }

            StringBuilder formatted = new StringBuilder();

            for (int i = 0; i < text.length(); i++) {

                if (i == 3 || i == 6) {
                    formatted.append('.');
                }

                if (i == 9) {
                    formatted.append('-');
                }

                formatted.append(text.charAt(i));
            }

            change.setText(formatted.toString());
            change.setRange(0, change.getControlText().length());

            change.setCaretPosition(formatted.length());
            change.setAnchor(formatted.length());

            return change;
        }));

        Button generateButton = new Button("Generate Boleto");
        generateButton.setPrefHeight(40);
        generateButton.setPrefWidth(Double.MAX_VALUE);
        generateButton.setAlignment(Pos.CENTER);

        VBox generateBox = new VBox(10);
        generateBox.getChildren().addAll(cpfLabel, cpfField, generateButton);

        VBox boletoBox = new VBox(15);
        boletoBox.setPadding(new Insets(25));
        boletoBox.setVisible(false);
        boletoBox.setManaged(false);
        boletoBox.setAlignment(Pos.CENTER);

        boletoBox.setStyle(
                "-fx-background-color:white;" +
                        "-fx-border-color:#D9D9D9;" +
                        "-fx-border-radius:6;" +
                        "-fx-background-radius:6;");

        ImageView barcode = new ImageView();
        barcode.setImage(
                new Image(BoletoView.class.getResourceAsStream("/barcode.png")));

        Label dueDate = new Label("Due date: " +
                LocalDate.now()
                        .format(
                                DateTimeFormatter.ofPattern("dd/MM/YYYY"))
                        .toString());
        dueDate.setStyle("-fx-font-size:18px;");

        Separator separator2 = new Separator();

        TextArea boletoCode = new TextArea ();
        boletoCode.setPrefSize(40, 45);
        boletoCode.setEditable(false);
        boletoCode.setWrapText(true);
        boletoCode.setPrefRowCount(2);

        Button copyButton = new Button("Copy");
        copyButton.setPrefWidth(70);
        copyButton.setAlignment(Pos.CENTER);

        copyButton.setOnAction(event -> {
            javafx.scene.input.Clipboard clipboard = javafx.scene.input.Clipboard.getSystemClipboard();

            javafx.scene.input.ClipboardContent content = new javafx.scene.input.ClipboardContent();

            content.putString(boletoCode.getText());

            clipboard.setContent(content);
        });

        boletoBox.getChildren().addAll(
                barcode,
                dueDate,
                separator2,
                boletoCode,
                copyButton);

        generateButton.setOnAction(e -> {
            String code = onGenerate.get();

            if (code != null) {
                boletoCode.setText(code);
                generateBox.setManaged(false);
                generateBox.setVisible(false);
    
                boletoBox.setVisible(true);
                boletoBox.setManaged(true);
            }
        });

        root.getChildren().addAll(
                generateBox,
                boletoBox);

        VBox.setVgrow(boletoBox, Priority.ALWAYS);

        return root;
    }

    public static String getCpf() {
        return cpfField.getText();
    }
}