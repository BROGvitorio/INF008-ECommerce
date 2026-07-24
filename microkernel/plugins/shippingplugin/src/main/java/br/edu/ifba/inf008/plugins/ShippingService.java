package br.edu.ifba.inf008.plugins;

import br.edu.ifba.inf008.domain.ShippingMethod;

import br.edu.ifba.inf008.interfaces.core.ICore;
import br.edu.ifba.inf008.interfaces.core.IPersistenceController;
import br.edu.ifba.inf008.interfaces.plugins.IShippingService;

import java.util.List;

public class ShippingService implements IShippingService
{
    private ShippingView view;
    private IPersistenceController persistenceController = ICore.getInstance().getPersistenceController();

    public void setView(ShippingView view) {
        this.view = view;
    }

    public List<ShippingMethod> findAllMethods() {
        return persistenceController.findAll(ShippingMethod.class);
    }
}