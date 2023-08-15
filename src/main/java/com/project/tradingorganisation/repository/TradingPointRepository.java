package com.project.tradingorganisation.repository;

import com.project.tradingorganisation.model.TradingPoint;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TradingPointRepository extends JpaRepository<TradingPoint, Long> {

}
