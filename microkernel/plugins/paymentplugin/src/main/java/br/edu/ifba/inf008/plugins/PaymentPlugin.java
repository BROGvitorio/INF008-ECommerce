package br.edu.ifba.inf008.plugins;

import java.util.ArrayList;
import java.util.List;

import br.edu.ifba.inf008.domain.Cart;

import br.edu.ifba.inf008.interfaces.core.ICore;
import br.edu.ifba.inf008.interfaces.core.IUIController;
import br.edu.ifba.inf008.interfaces.core.IPluginRegistry;
import br.edu.ifba.inf008.interfaces.plugins.IPlugin;
import br.edu.ifba.inf008.interfaces.plugins.IPayable;
import br.edu.ifba.inf008.interfaces.plugins.ICheckoutComponent;
import br.edu.ifba.inf008.interfaces.exceptions.InvalidPaymentException;

import br.edu.ifba.inf008.plugins.boleto.BoletoPayment;
import br.edu.ifba.inf008.plugins.credit_card.CreditCardPayment;
import br.edu.ifba.inf008.plugins.pix.PixPayment;

import javafx.scene.layout.VBox;

public class PaymentPlugin implements IPlugin, ICheckoutComponent {
    private static List<IPayable> paymentMethods = new ArrayList<>();

    public boolean init() {
        paymentMethods.add(new CreditCardPayment());
        paymentMethods.add(new PixPayment());
        paymentMethods.add(new BoletoPayment());

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
        PaymentView.getSelectedMethod(paymentMethods).processPayment();
        // try {
        //     // PaymentView.showErrorMessage("");
        //     // PaymentView.showOrderConfirmedPopup();            
        // } catch (InvalidPaymentException ex) {
        //     PaymentView.showErrorMessage(ex.getMessage());
        // } catch (Exception ex) {
        //     System.err.println(ex.getMessage());
        // }

        return null;
    }

    public VBox getUI () {
        return PaymentView.setUI(
            paymentMethods
        );
    }
}