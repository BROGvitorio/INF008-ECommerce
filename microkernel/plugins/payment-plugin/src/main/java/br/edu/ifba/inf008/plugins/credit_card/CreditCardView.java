package br.edu.ifba.inf008.plugins.credit_card;

import java.util.ArrayList;
import java.util.List;

import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

public class CreditCardView {
    private static TextField holderField = new TextField();
    private static TextField cardField = new TextField();
    private static TextField expiryField = new TextField();
    private static PasswordField cvvField = new PasswordField();

    public static VBox setUI() {
        VBox creditCardInputs = new VBox(10);

        Label cardHolder = new Label("Cardholder Name");
        TextFormatter<String> holderFormatter = new TextFormatter<>(change -> {
            if (change.getControlNewText().matches("[\\p{L} ]*")) {
                return change;
            }
            return null;
        });
        holderField.setTextFormatter(holderFormatter);

        Label cardNumber = new Label("Card Number");
        cardField.setPromptText("XXXX XXXX XXXX XXXX");
        TextFormatter<String> cardNumberFormatter = new TextFormatter<>(change -> {
            String digits = change.getControlNewText().replaceAll("\\D", "");

            if (digits.length() > 16) {
                return null;
            }
            StringBuilder formatted = new StringBuilder();

            for (int i = 0; i < digits.length(); i++) {
                if (i > 0 && i % 4 == 0) {
                    formatted.append(' ');
                }
                formatted.append(digits.charAt(i));
            }

            change.setText(formatted.toString());
            change.setRange(0, change.getControlText().length());

            int pos = formatted.length();
            change.setCaretPosition(pos);
            change.setAnchor(pos);

            return change;
        });

        cardField.setTextFormatter(cardNumberFormatter);

        Label expiry = new Label("Expiry Date");
        expiryField.setPromptText("MM-YYYY");
        TextFormatter<String> expiryFormatter = new TextFormatter<>(change -> {
            String text = change.getControlNewText();
            String digits = text.replaceAll("\\D", "");
            if (digits.length() > 6) {
                return null;
            }
            if (digits.length() >= 1) {
                char first = digits.charAt(0);
                if (first != '0' && first != '1') {
                    return null;
                }
            }

            if (digits.length() >= 2) {
                int month = Integer.parseInt(digits.substring(0, 2));

                if (month < 1 || month > 12) {
                    return null;
                }
            }
            String formatted;
            if (digits.length() <= 2) {
                formatted = digits;
            } else {
                formatted = digits.substring(0, 2) + "-" + digits.substring(2);
            }

            change.setText(formatted);
            change.setRange(0, change.getControlText().length());
            change.setCaretPosition(formatted.length());
            change.setAnchor(formatted.length());

            return change;
        });
        expiryField.setTextFormatter(expiryFormatter);

        Label cvv = new Label("CVV");
        TextFormatter<String> cvvFormatter = new TextFormatter<>(change -> {
            if (change.getControlNewText().matches("\\d{0,3}")) {
                return change;
            }
            return null;
        });

        cvvField.setTextFormatter(cvvFormatter);

        VBox expiryBox = new VBox(10, expiry, expiryField);
        VBox cvvBox = new VBox(10, cvv, cvvField);

        HBox dateRow = new HBox(15, expiryBox, cvvBox);
        HBox.setHgrow(expiryBox, Priority.ALWAYS);
        HBox.setHgrow(cvvBox, Priority.ALWAYS);

        expiryField.setMaxWidth(Double.MAX_VALUE);
        cvvField.setMaxWidth(Double.MAX_VALUE);

        creditCardInputs.getChildren().addAll(
                cardHolder,
                holderField,
                cardNumber,
                cardField,
                dateRow);

        return creditCardInputs;
    }

    public static void clearFields() {
        holderField.clear();
        cardField.clear();
        expiryField.clear();
        cvvField.clear();
    }

    public static List<String> getFields() {
        List<String> fieldValues = new ArrayList<String>();

        fieldValues.add(holderField.getText());
        fieldValues.add(cardField.getText());
        fieldValues.add(cvvField.getText());
        fieldValues.add(expiryField.getText());

        for (String fv : fieldValues) {
            System.out.println("\t\t" + fv);
        }

        return fieldValues;
    }
}
