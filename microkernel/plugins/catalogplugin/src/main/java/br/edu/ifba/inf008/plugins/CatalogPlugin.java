package br.edu.ifba.inf008.plugins;

import br.edu.ifba.inf008.interfaces.IPlugin;

public class CatalogPlugin implements IPlugin
{
    public boolean init() {
        CatalogView view = new CatalogView();
        view.show();

        return true;
    }
}
