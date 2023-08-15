package com.project.tradingorganisation.service;

import com.project.tradingorganisation.dto.TradingPointDTO;
import com.project.tradingorganisation.exception.tradingpointtype.NoSuchTradingPointType;
import com.project.tradingorganisation.mapper.TradingPointMapper;
import com.project.tradingorganisation.model.TradingPoint;
import com.project.tradingorganisation.model.TradingPointType;
import com.project.tradingorganisation.repository.TradingPointRepository;
import com.project.tradingorganisation.repository.TradingPointTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TradingPointService {
    private final TradingPointRepository repository;

    private final TradingPointTypeRepository tradingPointTypeRepository;

    private boolean isCorrectTradingPointTypeName(String name) {
        return tradingPointTypeRepository.existsByType(name);
    }

    public TradingPointDTO getById(Long id) {
        return repository
                .findById(id)
                .map(TradingPointMapper::toDTO)
                .orElseThrow(() -> new NoSuchTradingPointType(id));
    }

    public List<TradingPointDTO> getAllPoints() {
        return repository.findAll().stream().map(TradingPointMapper::toDTO).toList();
    }

    public TradingPointDTO addNewPoint(TradingPointDTO point) {
        String supposedTypeName = point.getType();

        if (!isCorrectTradingPointTypeName(supposedTypeName)) {
            throw new NoSuchTradingPointType(supposedTypeName);
        }

        TradingPoint savedPoint = repository.save(TradingPointMapper.toEntity(point, supposedTypeName));

        return TradingPointMapper.toDTO(savedPoint);
    }

    public TradingPointDTO replacePoint(TradingPointDTO point, long toReplaceId) {
        String supposedTypeName = point.getType();

        if (!isCorrectTradingPointTypeName(supposedTypeName)) {
            throw new NoSuchTradingPointType(supposedTypeName);
        }

        Optional<TradingPoint> found = repository.findById(toReplaceId);

        if (found.isEmpty()) {
            TradingPoint newPoint = new TradingPoint(
                    toReplaceId,
                    new TradingPointType(point.getType()),
                    point.getSize(), point.getRentPayment(),
                    point.getUtilitiesPayment());

            return TradingPointMapper.toDTO(repository.save(newPoint));
        }

        TradingPoint foundPoint = found.get();

        foundPoint.setSize(point.getSize());
        foundPoint.setRentPayment(point.getRentPayment());
        foundPoint.setUtilitiesPayment(point.getUtilitiesPayment());

        foundPoint.setType(new TradingPointType(point.getType()));

        // todo: investigate jpa merge

        return TradingPointMapper.toDTO(repository.save(foundPoint));
    }

    @Autowired
    public TradingPointService(TradingPointRepository repository, TradingPointTypeRepository tradingPointTypeRepository) {
        this.repository = repository;
        this.tradingPointTypeRepository = tradingPointTypeRepository;
    }
}
