package br.edu.ifba.inf008.plugins;

public class InsufficientStockException extends RuntimeException {
    public InsufficientStockException() {
        super("Estoque insuficiente.");
    }
}