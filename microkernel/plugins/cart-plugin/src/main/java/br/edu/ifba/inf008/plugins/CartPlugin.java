package br.edu.ifba.inf008.plugins;

import br.edu.ifba.inf008.domain.Cart;
import br.edu.ifba.inf008.domain.Customer;

import br.edu.ifba.inf008.interfaces.IPlugin;
import br.edu.ifba.inf008.interfaces.ICore;
import br.edu.ifba.inf008.interfaces.IUIController;
import br.edu.ifba.inf008.interfaces.IPersistenceController;

import javafx.scene.control.MenuItem;
import javafx.event.EventHandler;
import javafx.event.ActionEvent;
import javafx.scene.shape.Rectangle;
import javafx.scene.paint.Color;

public class CartPlugin implements IPlugin {
    public boolean init() {
        IUIController uiController = ICore.getInstance().getUIController();
        IPersistenceController persistenceController = ICore.getInstance().getPersistenceController();


        MenuItem menuItem = uiController.createMenuItem("Menu 2", "CART");
        menuItem.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                System.out.println("Cart também tá rodando");
            }
        });

        MenuItem menuItemTESTE = uiController.createMenuItem("Menu 2", "TESTE");
        menuItemTESTE.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                Cart cart = new Cart(
                    persistenceController.findById(Customer.class, new Long((long) 1)),
                    "TESTE"
                );

                persistenceController.save(cart);
            }
        });

        uiController.createTab("new tab", new Rectangle(200,200, Color.LIGHTSTEELBLUE));

        return true;
    }
}
