package br.edu.ifba.inf008.plugins;

import br.edu.ifba.inf008.interfaces.IPlugin;

import java.util.ArrayList;
import java.util.List;

import br.edu.ifba.inf008.domain.Cart;
import br.edu.ifba.inf008.domain.CartItem;
import br.edu.ifba.inf008.domain.Customer;
import br.edu.ifba.inf008.domain.Product;
import br.edu.ifba.inf008.interfaces.ICore;
import br.edu.ifba.inf008.interfaces.IPersistenceController;
import br.edu.ifba.inf008.interfaces.IUIController;
import br.edu.ifba.inf008.plugins.credit_card.CreditCardPayment;
import br.edu.ifba.inf008.plugins.credit_card.CreditCardView;
import javafx.scene.layout.VBox;

public class PaymentPlugin implements IPlugin {
    private static List<IPayable> paymentMethods = new ArrayList<>();    

    public boolean init() {
        paymentMethods.add(new CreditCardPayment());

        // paymentMethods.addAll(
        //     // new BoletoPayment(),
        //     new CreditCardPayment()
        //     // new PixPayment()
        // );

        IUIController uiController = ICore.getInstance().getUIController();

        MockClass.createCheckoutPage();
        return true;
    }

    public static void processPayment() {
        try {
            PaymentView.getSelectedMethod(paymentMethods).processPayment();
            PaymentView.showErrorMessage("");
        } catch (IllegalArgumentException ex) {
            PaymentView.showErrorMessage(ex.getMessage());
        } catch (Exception ex) {
            System.err.println(ex.getMessage());
        }
    }

    public static VBox getUI () {
        return PaymentView.setUI(
            paymentMethods,
            () -> processPayment()
        );
    }
}