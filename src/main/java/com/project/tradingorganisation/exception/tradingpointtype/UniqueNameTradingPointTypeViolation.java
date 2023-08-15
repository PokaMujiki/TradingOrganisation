package com.project.tradingorganisation.exception.tradingpointtype;

public class UniqueNameTradingPointTypeViolation extends RuntimeException {
    public UniqueNameTradingPointTypeViolation() {
    }

    public UniqueNameTradingPointTypeViolation(String message) {
        super(message);
    }

    public UniqueNameTradingPointTypeViolation(Throwable cause) {
        super(cause);
    }

    public UniqueNameTradingPointTypeViolation(String message, Throwable cause) {
        super(message, cause);
    }
}
