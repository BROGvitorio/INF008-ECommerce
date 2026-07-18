package br.edu.ifba.inf008.plugins.exceptions;

public class InvalidPaymentException extends RuntimeException {
    public InvalidPaymentException () {
        super("We were unable to confirm your order. Try again."); 
    }

    public InvalidPaymentException (String message) {
        super(message); 
    }
}
