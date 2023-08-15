package com.project.tradingorganisation.exception.tradingpoint;

public class NoSuchTradingPoint extends RuntimeException {
    public NoSuchTradingPoint() {
    }

    public NoSuchTradingPoint(String message) {
        super(message);
    }

    public NoSuchTradingPoint(Throwable cause) {
        super(cause);
    }

    public NoSuchTradingPoint(String message, Throwable cause) {
        super(message, cause);
    }
}
