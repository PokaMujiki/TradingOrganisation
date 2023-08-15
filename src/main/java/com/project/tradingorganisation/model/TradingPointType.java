package com.project.tradingorganisation.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "trading_point_type")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class TradingPointType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull
    @Column(unique = true)
    private String type;

    public TradingPointType(String type) {
        this.type = type;
    }
}
