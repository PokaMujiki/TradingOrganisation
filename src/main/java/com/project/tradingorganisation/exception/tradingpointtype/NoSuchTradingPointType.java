package com.project.tradingorganisation.exception.tradingpointtype;

public class NoSuchTradingPointType extends RuntimeException {
    public NoSuchTradingPointType() {
    }

    public NoSuchTradingPointType(String type) {
        super(String.format("Trading point with name %s doesn't exist", type));
    }

    public NoSuchTradingPointType(long id) {
        super(String.format("Trading point with id %d doesn't exist", id));
    }

    public NoSuchTradingPointType(Throwable cause) {
        super(cause);
    }

    public NoSuchTradingPointType(String message, Throwable cause) {
        super(message, cause);
    }
}
