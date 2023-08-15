package com.project.tradingorganisation.mapper;

import com.project.tradingorganisation.dto.TradingPointDTO;
import com.project.tradingorganisation.model.TradingPoint;
import com.project.tradingorganisation.model.TradingPointType;

public class TradingPointMapper {

    private TradingPointMapper() {
    }

    public static TradingPointDTO toDTO(TradingPoint point) {
        TradingPointDTO dto = new TradingPointDTO();
        dto.setId(point.getId());
        dto.setType(point.getType().getType());
        dto.setSize(point.getSize());
        dto.setRentPayment(point.getRentPayment());
        dto.setUtilitiesPayment(point.getUtilitiesPayment());
        return dto;
    }

    public static TradingPoint toEntity(TradingPointDTO dto, String typeName) {
        TradingPointType type = new TradingPointType(typeName);
        return new TradingPoint(dto.getId(), type, dto.getSize(), dto.getRentPayment(), dto.getUtilitiesPayment());
    }
}
