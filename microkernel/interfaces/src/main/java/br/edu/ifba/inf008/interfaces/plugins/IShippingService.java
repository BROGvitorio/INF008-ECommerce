package br.edu.ifba.inf008.interfaces.plugins;

import br.edu.ifba.inf008.domain.ShippingMethod;

import java.util.List;

public interface IShippingService {
    List<ShippingMethod> findAllMethods();
}