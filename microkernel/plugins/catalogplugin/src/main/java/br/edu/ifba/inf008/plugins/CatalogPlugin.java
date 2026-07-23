package br.edu.ifba.inf008.plugins;

import br.edu.ifba.inf008.interfaces.core.ICore;
import br.edu.ifba.inf008.interfaces.core.IPluginRegistry;
import br.edu.ifba.inf008.interfaces.plugins.ICatalogService;
import br.edu.ifba.inf008.interfaces.plugins.IPlugin;

public class CatalogPlugin implements IPlugin
{
    private IPluginRegistry pluginRegistry;

    private CatalogService service;
    private CatalogView view;

    public boolean init() {
        pluginRegistry = ICore.getInstance().getPluginRegistry();
        pluginRegistry.register(IPlugin.class, this);
        
        service = new CatalogService();
        pluginRegistry.register(ICatalogService.class, service);

        return true;
    }

    public void start() {
        CatalogView view = new CatalogView(service);
        view.show();
    }
}
