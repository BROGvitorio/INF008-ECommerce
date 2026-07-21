package br.edu.ifba.inf008.interfaces.exceptions;

public class NotFoundException extends RuntimeException {
    public NotFoundException (String message) {
        super(message);
    }
}