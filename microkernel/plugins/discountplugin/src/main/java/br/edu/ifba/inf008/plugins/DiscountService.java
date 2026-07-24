package br.edu.ifba.inf008.plugins;

import br.edu.ifba.inf008.domain.Order;
import br.edu.ifba.inf008.domain.Discount;
import br.edu.ifba.inf008.domain.StockMovement;

import br.edu.ifba.inf008.interfaces.core.ICore;
import br.edu.ifba.inf008.interfaces.core.IPersistenceController;
import br.edu.ifba.inf008.interfaces.core.IUIController;
import br.edu.ifba.inf008.interfaces.plugins.IDiscountService;
import br.edu.ifba.inf008.interfaces.plugins.policy.DiscountPolicy;
import javafx.scene.control.Tab;

import java.util.ArrayList;
import java.util.List;
import java.math.BigDecimal;

public class DiscountService implements IDiscountService
{
    private DiscountView view;

    private final DiscountRepository repository = new DiscountRepository();
    private IPersistenceController persistenceController = ICore.getInstance().getPersistenceController();

    public void setView(DiscountView view) {
        this.view = view;
    }

    public void applyCoupon(String code) {
        Order order = CheckoutService.getCurrentOrder();
        Discount discount = repository.findActiveCoupon(code);

        if (discount == null) {
            throw new IllegalArgumentException("Cupom inválido.");
        }

        DiscountPolicy policy = new CouponDiscountPolicy();
        BigDecimal discountTotal = policy.calculateDiscount(order, discount);

        order.setDiscountTotal(discountTotal);
        persistenceController.update(order);
    }

    public void closeTab(IUIController uiController, Tab tab) {
        view.closeTabs();
    }
}