package com.project.tradingorganisation.service;

import com.project.tradingorganisation.exception.tradingpointtype.NoSuchTradingPointType;
import com.project.tradingorganisation.exception.tradingpointtype.UniqueIdTradingPointTypeViolation;
import com.project.tradingorganisation.model.TradingPointType;
import com.project.tradingorganisation.repository.TradingPointTypeRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class TradingPointTypeService {
    private final TradingPointTypeRepository repository;

    public TradingPointType getById(Long id) {
        return repository
                .findById(id)
                .orElseThrow(() -> new NoSuchTradingPointType(id));
    }

    public List<TradingPointType> getAllTypes() {
        return repository.findAll();
    }

    public TradingPointType addNewType(TradingPointType type) {
        if (type.getId() != null && repository.findById(type.getId()).isPresent()) {
            throw new UniqueIdTradingPointTypeViolation(
                    String.format("Trading point type with id \"%d\" is already exists." +
                            " Don't specify id in POST methods", type.getId()));
        }

        return repository.save(type);
    }

    public TradingPointType replaceTradingPointType(TradingPointType newType, long toReplaceId) {
        Optional<TradingPointType> found = repository.findById(toReplaceId);

        if (found.isPresent()) {
            found.get().setType(newType.getType());
            return repository.save(found.get());
        }

        newType.setId(toReplaceId);
        return repository.save(newType);
    }
}
