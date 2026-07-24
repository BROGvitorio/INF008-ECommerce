package br.edu.ifba.inf008.interfaces.plugins;

import javafx.scene.layout.VBox;

public interface ICheckoutComponent {
    String getName();
    VBox getUI();
    void process();
}
