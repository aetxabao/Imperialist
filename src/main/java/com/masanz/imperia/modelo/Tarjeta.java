package com.masanz.imperia.modelo;

public class Tarjeta {

    private static int contador = 0;

    private int numero;
    private String tipo;

    public Tarjeta(String tipo) {
        this.numero = ++contador;
        this.tipo = tipo;
    }

    public int getNumero() {
        return numero;
    }

    public String getTipo() {
        return tipo;
    }

    @Override
    public String toString() {
        return tipo;
    }

}
