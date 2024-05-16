package com.masanz.imperia.modelo;

/**
 * @author TODO PON AQUÍ TU NOMBRE
 */
public class MisionConquistarContinente extends Mision {
    protected String continente;

    public MisionConquistarContinente(String continente) {
        this.continente = continente;
    }

    @Override
    public boolean estaCumplida() {
        return Mundo.esContinenteDe(continente, idJugador);
    }

    @Override
    public int numeroTerritorios() {
        return Mundo.getListaTerritorios(continente).size();
    }

    @Override
    public String toString() {
        return "Conquistar " + continente;
    }

}
