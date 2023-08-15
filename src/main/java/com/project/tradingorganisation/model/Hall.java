package com.project.tradingorganisation.model;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "hall")
public class Hall {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToOne
    private TradingPoint tradingPoint;
    @OneToMany
    private List<Employee> employees;

}
