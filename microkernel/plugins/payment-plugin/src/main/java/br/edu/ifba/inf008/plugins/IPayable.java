package br.edu.ifba.inf008.plugins;

import javafx.scene.layout.VBox;

public interface IPayable {
    String getName();
    void processPayment();
    VBox getUI();
}
