package com.project.tradingorganisation.dto;

import jakarta.persistence.Column;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class TradingPointDTO {
    private long id;
    @NotNull
    private String type;
    @Min(value = 0, message = "Size must be greater or equal than zero")
    private Long size;
    @Min(value = 0, message = "Rent payment must be greater or equal than zero")
    @Column(name = "rent_payment")
    private Long rentPayment;
    @Column(name = "utilities_payment")
    @Min(value = 0, message = "Utilities payment must be greater or equal than zero")
    private Long utilitiesPayment;
}
