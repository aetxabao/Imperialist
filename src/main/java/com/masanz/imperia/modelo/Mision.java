package com.masanz.imperia.modelo;

/**
 * @author TODO PON AQUÍ TU NOMBRE
 */
public abstract class Mision implements Comparable<Mision> {

    protected String idJugador = "";

    public String getIdJugador() {
        return idJugador;
    }

    public void setIdJugador(String idJugador) {
        this.idJugador = idJugador;
    }

    public abstract boolean estaCumplida();

    public abstract int numeroTerritorios();

    @Override
    public int compareTo(Mision other) {
        return this.numeroTerritorios() - other.numeroTerritorios();
    }

}
