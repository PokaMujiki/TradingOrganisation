package com.project.tradingorganisation.controller;

import com.project.tradingorganisation.dto.TradingPointDTO;
import com.project.tradingorganisation.model.TradingPoint;
import com.project.tradingorganisation.repository.TradingPointRepository;
import com.project.tradingorganisation.service.TradingPointService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/trading-points")
public class TradingPointController {
    private final TradingPointService service;

    @GetMapping("")
    public List<TradingPointDTO> getAllTradingPoints() {
        return service.getAllPoints();
    }

    @GetMapping("/{id}")
    public TradingPointDTO getTradingPointById(@PathVariable Long id) {
        return service.getById(id);
    }

    @PostMapping("")
    @ResponseStatus(HttpStatus.CREATED)
    public TradingPointDTO addTradingPoint(@Valid @RequestBody TradingPointDTO point) {
        return service.addNewPoint(point);
    }
    @PutMapping("/{id}")
    public void replaceTradingPointById(@RequestBody TradingPointDTO point, @PathVariable long id) {

    }

    @DeleteMapping("/{id}")
    public void deleteTradingPointById(@PathVariable long id) {

    }

    @Autowired
    public TradingPointController(TradingPointService service) {
        this.service = service;
    }
}
