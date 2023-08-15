package com.project.tradingorganisation.controller;

import com.project.tradingorganisation.exception.tradingpointtype.UniqueNameTradingPointTypeViolation;
import com.project.tradingorganisation.model.TradingPointType;
import com.project.tradingorganisation.service.TradingPointTypeService;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/trading-point-types")
@RequiredArgsConstructor
public class TradingPointTypeController {
    private final TradingPointTypeService service;

    @GetMapping("/{id}")
    public TradingPointType getTradingPointById(@PathVariable Long id) {
        return service.getById(id);
    }

    @GetMapping("")
    public List<TradingPointType> getAllTradingPointTypes() {
        return service.getAllTypes();
    }

    @PostMapping("")
    @ResponseStatus(HttpStatus.CREATED)
    public TradingPointType addTradingPointType(@RequestBody TradingPointType type) {
        try {
            return service.addNewType(type);
        } catch (DataIntegrityViolationException e) {
            throw new UniqueNameTradingPointTypeViolation(
                    String.format("Trading point type name \"%s\" is already taken", type.getType()));
        }
    }

    @PutMapping("/{id}")
    public TradingPointType replaceTradingPointType(@RequestBody TradingPointType newType, @PathVariable long id) {
        try {
            return service.replaceTradingPointType(newType, id);
        }
        catch (DataIntegrityViolationException e) {
            throw new UniqueNameTradingPointTypeViolation(
                    String.format("Trading point type name \"%s\" is already taken", newType.getType()));
        }
    }
}
