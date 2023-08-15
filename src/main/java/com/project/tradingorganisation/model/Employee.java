package com.project.tradingorganisation.model;

import jakarta.persistence.*;

@Entity
@Table(name = "employee")
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    private TradingPoint point;
    private String name;
    private Long salary;

    @ManyToOne
    private Hall workingHall;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public TradingPoint getPoint() {
        return point;
    }

    public void setPoint(TradingPoint point) {
        this.point = point;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getSalary() {
        return salary;
    }

    public void setSalary(Long salary) {
        this.salary = salary;
    }

    public Hall getWorkingHall() {
        return workingHall;
    }

    public void setWorkingHall(Hall workingHall) {
        this.workingHall = workingHall;
    }
}
