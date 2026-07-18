package br.edu.ifba.inf008.plugins.credit_card;

import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.List;

import br.edu.ifba.inf008.plugins.IPayable;
import br.edu.ifba.inf008.plugins.exceptions.InvalidPaymentException;
import javafx.scene.layout.VBox;

public class CreditCardPayment implements IPayable {

    @Override
    public String getName() {
        return "Credit Card";
    }

    @Override
    public VBox getUI() {
        return CreditCardView.setUI();
    }

    @Override
    public void processPayment() {
        List<String> fields = CreditCardView.getFields();

        for (String field : fields) {
            if (field == null || field.isBlank())
                throw new InvalidPaymentException("All fields must be filled in");
        }

        String expiry = fields.getLast();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM-yyyy");
        YearMonth date = YearMonth.parse(expiry, formatter);

        if (date.isBefore(YearMonth.now()))
            throw new InvalidPaymentException("Invalid expiry date.");

        CreditCardView.clearFields();
    }


}