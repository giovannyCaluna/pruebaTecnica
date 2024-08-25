package com.bank.demo.DTOs;


public class CuentaDTO {
    private String cuentaid;
    private double saldoInicial;
    private Boolean estado;
    private String clienteId;
    private String tipo;

    public double getSaldoInicial() {
        return saldoInicial;
    }

    public void setSaldoInicial(double saldoInicial) {
        this.saldoInicial = saldoInicial;
    }

    public Boolean getEstado() {
        return estado;
    }

    public void setEstado(Boolean estado) {
        this.estado = estado;
    }

    public String getClienteId() {
        return clienteId;
    }

    public void setClienteId(String clienteId) {
        this.clienteId = clienteId;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getCuentaid() {
        return cuentaid;
    }

    public void setCuentaid(String cuentaid) {
        this.cuentaid = cuentaid;
    }
}
