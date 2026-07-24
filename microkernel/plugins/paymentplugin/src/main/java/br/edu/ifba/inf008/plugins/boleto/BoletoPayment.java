package br.edu.ifba.inf008.plugins.boleto;

import java.util.Random;
import java.util.function.Consumer;

import br.edu.ifba.inf008.interfaces.plugins.IPayable;
import br.edu.ifba.inf008.interfaces.exceptions.InvalidPaymentException;
import javafx.scene.layout.VBox;

public class BoletoPayment implements IPayable {
    public String getName() {
        return "Boleto";
    }

    public VBox getUI() {
        return BoletoView.setUI(() -> generateBoleto());
    }

    public void processPayment() {
        checkCpf();
    }

    public String generateBoleto() {
        checkCpf();

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

    private void checkCpf() {
        String cpf = BoletoView.getCpf();

        if (cpf == null || cpf.isBlank()) {
            throw new InvalidPaymentException("The CPF field must be filled in.");
        }

        if (cpf.length() != 14) {
            throw new InvalidPaymentException("Invalid CPF.");
        }
    }
}