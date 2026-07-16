package br.edu.ifba.inf008.plugins;

import br.edu.ifba.inf008.interfaces.IPlugin;

public class CatalogPlugin implements IPlugin
{
    public boolean init() {
        CatalogService service = new CatalogService();

        CatalogView view = new CatalogView(service);
        view.show();

        return true;
    }
}
