package br.edu.ifba.inf008.plugins.boleto;

import java.util.Random;
import java.util.function.Consumer;

import br.edu.ifba.inf008.plugins.IPayable;
import br.edu.ifba.inf008.plugins.exceptions.InvalidPaymentException;
import javafx.scene.layout.VBox;

public class BoletoPayment implements IPayable {
    private static Consumer<String> onMissingData;
    
    public static void setOnMissingData(Consumer<String> callback) {
        onMissingData = callback;
    } 

    public String getName() {
        return "Boleto";
    }

    public VBox getUI() {

        return BoletoView.setUI(() -> generateBoleto());
    }

    public void processPayment() {
        if (!checkCpf())
            throw new InvalidPaymentException();
    }

    public String generateBoleto() {
        if (!checkCpf())
            return null;

        Random r = new Random();

        return 
            String.format("%05d.", r.nextInt(99999)) +
            String.format("%05d ", r.nextInt(99999)) +
            String.format("%05d.", r.nextInt(99999)) +
            String.format("%07d ", r.nextInt(9999999)) +
            String.format("%05d ", r.nextInt(99999)) +
            String.format("%06d ", r.nextInt(999999)) +
            String.valueOf(r.nextInt(9)) +
            String.format("%07d", r.nextInt(9999999)) +
            String.format("%07d", r.nextInt(9999999));
    }

    private boolean checkCpf() {
        String cpf = BoletoView.getCpf();

        if (cpf == null || cpf.isBlank()) {
            onMissingData.accept("The CPF field must be filled in.");
            return false;
        }

        if (cpf.length() != 14) {
            onMissingData.accept("Invalid CPF.");
            return false;
        }

        return true;
    }
}
