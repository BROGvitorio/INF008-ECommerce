package br.edu.ifba.inf008.interfaces.plugins;

import br.edu.ifba.inf008.domain.Cart;

public interface ICheckoutService {
    void createOrder(Cart cart);
}
