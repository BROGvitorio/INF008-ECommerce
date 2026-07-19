package br.edu.ifba.inf008.interfaces;

import javafx.scene.layout.VBox;

public interface ICheckoutComponent {
    String getName();
    VBox getUI();
    Object process();
}
