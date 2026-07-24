package br.edu.ifba.inf008.plugins;

import br.edu.ifba.inf008.interfaces.core.ICore;
import br.edu.ifba.inf008.interfaces.core.IPluginRegistry;
import br.edu.ifba.inf008.interfaces.IPlugin;

public class DiscountPlugin implements IPlugin
{
    private IPluginRegistry pluginRegistry;

    private CatalogService service;
    private DiscountView view;

    public boolean init() {
        pluginRegistry = ICore.getInstance().getPluginRegistry();
        pluginRegistry.register(IPlugin.class, this);
        
        service = new DiscountService();
        pluginRegistry.register(IDiscountService.class, service);

        return true;
    }

    public void start() {
        view = new DiscountView(service);
        view.show();
    }
}