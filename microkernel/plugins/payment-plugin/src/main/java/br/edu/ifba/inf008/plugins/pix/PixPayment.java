package br.edu.ifba.inf008.plugins.pix;

import br.edu.ifba.inf008.plugins.IPayable;
import br.edu.ifba.inf008.plugins.exceptions.InvalidPaymentException;
import javafx.scene.layout.VBox;

public class PixPayment implements IPayable {
    @Override
    public String getName () {
        return "PIX";
    }

    @Override
    public VBox getUI () {
        return PixView.setUI();
    }

    @Override
    public void processPayment () {
        if (Math.random() < 0.10)
            throw new InvalidPaymentException();
    }
}
