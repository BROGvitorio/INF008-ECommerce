package br.edu.ifba.inf008.interfaces.exceptions;

public class InsufficientStockException extends RuntimeException {
    public InsufficientStockException (int stock) {
        super("There isn't enough stock" + "\n current stock: " + stock);
    }
}