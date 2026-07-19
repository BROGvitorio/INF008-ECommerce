package br.edu.ifba.inf008.plugins;

import br.edu.ifba.inf008.interfaces.ICore;
import br.edu.ifba.inf008.interfaces.IPlugin;

public class CatalogPlugin implements IPlugin
{
    public boolean init() {
        ICore.getInstance().getPluginRegistry().register(IPlugin.class, this);

        return true;
    }
    
    public void start() {
        CatalogService service = new CatalogService();
    
        CatalogView view = new CatalogView(service);
        view.show();
    }
}
