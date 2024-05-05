package Exceptions;

public class WrongRomanInteger extends RuntimeException {
    public WrongRomanInteger(String message) {
        super(message);
    }
}
