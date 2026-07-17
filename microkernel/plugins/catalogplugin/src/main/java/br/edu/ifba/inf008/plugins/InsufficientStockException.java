package br.edu.ifba.inf008.plugins;

public class InsufficientStockException extends Exception {
    public InsufficientStockException() {
        super("Estoque insuficiente.");
    }
}