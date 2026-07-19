package br.edu.ifba.inf008.plugins;

import br.edu.ifba.inf008.interfaces.IPlugin;
import br.edu.ifba.inf008.interfaces.ICheckoutComponent;

import java.util.ArrayList;
import java.util.List;

import br.edu.ifba.inf008.domain.Cart;
import br.edu.ifba.inf008.interfaces.ICore;
import br.edu.ifba.inf008.interfaces.IUIController;
import br.edu.ifba.inf008.interfaces.IPluginRegistry;
import br.edu.ifba.inf008.plugins.boleto.BoletoPayment;
import br.edu.ifba.inf008.plugins.credit_card.CreditCardPayment;
import br.edu.ifba.inf008.plugins.exceptions.InvalidPaymentException;
import br.edu.ifba.inf008.plugins.pix.PixPayment;

import javafx.scene.layout.VBox;

public class PaymentPlugin implements IPlugin, ICheckoutComponent {
    private static List<IPayable> paymentMethods = new ArrayList<>();

    public boolean init() {
        paymentMethods.add(new CreditCardPayment());
        paymentMethods.add(new PixPayment());
        paymentMethods.add(new BoletoPayment());
        BoletoPayment.setOnMissingData(PaymentView::showErrorMessage);

        IUIController uiController = ICore.getInstance().getUIController();
        IPluginRegistry pluginRegistry = ICore.getInstance().getPluginRegistry();
        
        pluginRegistry = ICore.getInstance().getPluginRegistry();
        pluginRegistry.register(IPlugin.class, this);
        pluginRegistry.register(ICheckoutComponent.class, this);

        return true;
    }
    
    public void start() {
        
    }

    public String getName() {
        return "Payment";
    }

    public Object process() {
        try {
            PaymentView.getSelectedMethod(paymentMethods).processPayment();
            PaymentView.showErrorMessage("");
            PaymentView.showOrderConfirmedPopup();            
        } catch (InvalidPaymentException ex) {
            PaymentView.showErrorMessage(ex.getMessage());
        } catch (Exception ex) {
            System.err.println(ex.getMessage());
        }

        return null;
    }

    public VBox getUI () {
        return PaymentView.setUI(
            paymentMethods,
            () -> process()
        );
    }
}