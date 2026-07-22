package br.edu.ifba.inf008.plugins;

import br.edu.ifba.inf008.interfaces.IPlugin;

public class DiscountPlugin implements IPlugin
{
    public boolean init() {
        DiscountView view = new DiscountView();
        view.show();

        return true;
    }
}