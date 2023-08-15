package com.project.tradingorganisation.model;

import com.project.tradingorganisation.repository.TradingPointTypeRepository;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "trading_point")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class TradingPoint {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @NotNull
    private TradingPointType type;
    @Min(value = 0, message = "Size must be greater or equal than zero")
    private Long size;
    @Min(value = 0, message = "Rent payment must be greater or equal than zero")
    @Column(name = "rent_payment")
    private Long rentPayment;
    @Column(name = "utilities_payment")
    @Min(value = 0, message = "Utilities payment must be greater or equal than zero")
    private Long utilitiesPayment;

    public TradingPoint(TradingPointType type) {
        this.type = type;
    }

    public TradingPoint(TradingPointType type, long size, long rentPayment, long utilitiesPayment) {
        this.type = type;
        this.size = size;
        this.rentPayment = rentPayment;
        this.utilitiesPayment = utilitiesPayment;
    }

    // todo: возможно тут еще какое-то поле должно быть
}
