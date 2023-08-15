package com.project.tradingorganisation.controlleradvice;

import com.project.tradingorganisation.exception.tradingpointtype.NoSuchTradingPointType;
import com.project.tradingorganisation.exception.tradingpointtype.UniqueIdTradingPointTypeViolation;
import com.project.tradingorganisation.exception.tradingpointtype.UniqueNameTradingPointTypeViolation;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class TradingPointTypeControllerAdvice {

    @ResponseBody
    @ExceptionHandler(UniqueNameTradingPointTypeViolation.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public String handleUniqueNameTradingPointTypeViolation(UniqueNameTradingPointTypeViolation e) {
        return e.getMessage();
    }

    @ResponseBody
    @ExceptionHandler(UniqueIdTradingPointTypeViolation.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public String handleUniqueIdTradingPointTypeViolation(UniqueIdTradingPointTypeViolation e) {
        return e.getMessage();
    }

    @ResponseBody
    @ExceptionHandler(NoSuchTradingPointType.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String handleNoSuchTradingPointType(NoSuchTradingPointType e) {
        return e.getMessage();
    }
}
