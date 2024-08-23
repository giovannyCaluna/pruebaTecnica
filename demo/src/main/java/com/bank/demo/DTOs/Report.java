package com.bank.demo.DTOs;

import java.util.Date;
import java.util.List;

public class Report {
    private String clienteid;
    private String cliente;
    private Date fechaReporte;
    private List<CuentaReporte> cuentas;

    public Report(String clienteid, String cliente, Date fechaReporte, List<CuentaReporte> cuentas) {
        this.clienteid = clienteid;
        this.cliente = cliente;
        this.fechaReporte = fechaReporte;
        this.cuentas = cuentas;
    }
    public Report() {

    }

    public String getCliente() {
        return cliente;
    }

    public void setCliente(String cliente) {
        this.cliente = cliente;
    }

    public Date getFechaReporte() {
        return fechaReporte;
    }

    public void setFechaReporte(Date fechaReporte) {
        this.fechaReporte = fechaReporte;
    }

    public String getClienteid() {
        return clienteid;
    }

    public void setClienteid(String clienteid) {
        this.clienteid = clienteid;
    }

    public List<CuentaReporte> getCuentas() {
        return cuentas;
    }

    public void setCuentas(List<CuentaReporte> cuentas) {
        this.cuentas = cuentas;
    }
}
