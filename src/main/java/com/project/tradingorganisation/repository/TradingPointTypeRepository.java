package com.project.tradingorganisation.repository;

import com.project.tradingorganisation.model.TradingPointType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TradingPointTypeRepository  extends JpaRepository<TradingPointType, Long> {
    Optional<TradingPointType> findByType(String type);

    boolean existsByType(String typeName);
}
