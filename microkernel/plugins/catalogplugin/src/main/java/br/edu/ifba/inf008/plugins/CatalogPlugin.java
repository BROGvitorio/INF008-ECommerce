package br.edu.ifba.inf008.plugins;

import br.edu.ifba.inf008.interfaces.core.ICore;
import br.edu.ifba.inf008.interfaces.core.IPluginRegistrar;
import br.edu.ifba.inf008.interfaces.plugins.ICatalogService;
import br.edu.ifba.inf008.interfaces.plugins.IPlugin;

public class CatalogPlugin implements IPlugin
{
    private IPluginRegistrar pluginRegistrar;

    private CatalogService service;
    private CatalogView view;

    public boolean init() {
        ICore.getInstance().getPluginRegistrar().register(IPlugin.class, this);

        service = new CatalogService();
        ICore.getInstance().getPluginRegistrar().register(ICatalogService.class, service);

        // CatalogService service = new CatalogService();

        // CatalogView view = new CatalogView(service);
        // view.show();

        return true;
    }

    public void start() {
        CatalogView view = new CatalogView(service);
        view.show();
    }
}
