package br.edu.ifba.inf008.plugins;

import br.edu.ifba.inf008.interfaces.core.ICore;
import br.edu.ifba.inf008.interfaces.core.IPluginRegistry;
import br.edu.ifba.inf008.interfaces.plugins.IPlugin;
import br.edu.ifba.inf008.interfaces.plugins.IShippingService;

public class ShippingPlugin implements IPlugin, ICheckoutComponent
{
    private IPluginRegistry pluginRegistry;

    private ShippingService service;
    private ShippingView view;

    public boolean init() {
        pluginRegistry = ICore.getInstance().getPluginRegistry();
        pluginRegistry.register(IPlugin.class, this);
        
        service = new ShippingService();
        pluginRegistry.register(IShippingService.class, service);
        pluginRegistry.register(ICheckoutComponent.class, this);

        return true;
    }

    public void start() {
        ShippingView view = new ShippingView(service);
        view.show();
    }

    public String getName() {
        return "shipping";
    }

    public VBox getUI() {
        return ShippingView.setUI(service);
    }

    public Object process() {
        service.processShipping();
        return null;
    }
}