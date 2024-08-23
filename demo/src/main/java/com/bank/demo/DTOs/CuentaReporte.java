package com.bank.demo.DTOs;

import java.util.List;

public class CuentaReporte {
    private String numeroCuenta;
    private String tipo;
    private Boolean state;
    private List<ReportLine> lineas;

    public CuentaReporte() {

    }

    public CuentaReporte(String numeroCuenta, String tipo, Boolean state, List<ReportLine> lineas) {
        this.numeroCuenta = numeroCuenta;
        this.tipo = tipo;
        this.state = state;
        this.lineas = lineas;
    }

    public String getNumeroCuenta() {
        return numeroCuenta;
    }

    public void setNumeroCuenta(String numeroCuenta) {
        this.numeroCuenta = numeroCuenta;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTypo(String typo) {
        this.tipo = typo;
    }

    public Boolean getState() {
        return state;
    }

    public void setState(Boolean state) {
        this.state = state;
    }

    public List<ReportLine> getLineas() {
        return lineas;
    }

    public void setLineas(List<ReportLine> lineas) {
        this.lineas = lineas;
    }
}
