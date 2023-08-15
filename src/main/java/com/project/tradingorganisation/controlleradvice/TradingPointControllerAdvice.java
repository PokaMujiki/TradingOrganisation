package com.project.tradingorganisation.controlleradvice;

import com.project.tradingorganisation.exception.tradingpoint.NoSuchTradingPoint;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class TradingPointControllerAdvice {
    @ResponseBody
    @ExceptionHandler(NoSuchTradingPoint.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String handleNoSuchTradingPoint(NoSuchTradingPoint e) {
        return e.getMessage();
    }
}
