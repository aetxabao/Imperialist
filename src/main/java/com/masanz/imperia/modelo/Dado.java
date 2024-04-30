package com.masanz.imperia.modelo;

/**
 * Clase Dado que representa un dado de 6 caras.
 * Hay que tirar el dado para obtener un valor aleatorio entre 1 y 6.
 * Este valor se consulta con getValor().
 */
public class Dado {

    // region Atributos
    /** Valor del dado */
    private int valor;
    // endregion

    /**
     * Constructor de la clase Dado.
     * Inicializa el valor del dado a un valor aleatorio del 1 al 6.
     */
    public Dado() {
        this.valor = (int) (Math.random() * 6) + 1;
    }

    /**
     * Devuelve el valor del dado.
     * @return Valor del dado.
     */
    public int getValor() {
        return this.valor;
    }

    /**
     * Tira el dado para obtener un valor aleatorio entre 1 y 6.
     */
    public void tirar() {
        this.valor = (int) (Math.random() * 6) + 1;
    }

}
