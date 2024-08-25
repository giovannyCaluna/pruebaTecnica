package com.bank.demo.DTOs;

import java.util.Date;

public class ReportLine {
    private Date date;
    private String type;
    private Double initialBalance;
    private Double movement;
    private Double finalBalance;

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Double getInitialBalance() {
        return initialBalance;
    }

    public void setInitialBalance(Double initialBalance) {
        this.initialBalance = initialBalance;
    }

    public Double getMovement() {
        return movement;
    }

    public void setMovement(Double movement) {
        this.movement = movement;
    }

    public Double getFinalBalance() {
        return finalBalance;
    }

    public void setFinalBalance(Double finalBalance) {
        this.finalBalance = finalBalance;
    }

    public ReportLine(Date date, String type, Double initialBalance, Double movement, Double finalBalance) {
        this.date = date;
        this.type = type;
        this.initialBalance = initialBalance;
        this.movement = movement;
        this.finalBalance = finalBalance;
    }
}
