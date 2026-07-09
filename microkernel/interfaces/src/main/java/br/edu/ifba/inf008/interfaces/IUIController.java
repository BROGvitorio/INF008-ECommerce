package br.edu.ifba.inf008.interfaces;

import javafx.scene.control.MenuItem;
import javafx.scene.control.Tab;
import javafx.scene.Node;

public interface IUIController
{
    public abstract MenuItem createMenuItem(String menuText, String menuItemText);
    public abstract Tab createTab(String tabText, Node contents);
    public abstract void removeTab(Tab tab);
}
