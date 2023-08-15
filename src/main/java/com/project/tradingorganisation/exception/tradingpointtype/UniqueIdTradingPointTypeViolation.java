package com.project.tradingorganisation.exception.tradingpointtype;

public class UniqueIdTradingPointTypeViolation extends RuntimeException {
    public UniqueIdTradingPointTypeViolation() {
    }

    public UniqueIdTradingPointTypeViolation(String message) {
        super(message);
    }

    public UniqueIdTradingPointTypeViolation(Throwable cause) {
        super(cause);
    }

    public UniqueIdTradingPointTypeViolation(String message, Throwable cause) {
        super(message, cause);
    }
}
