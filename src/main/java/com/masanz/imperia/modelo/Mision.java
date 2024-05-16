package com.masanz.imperia.modelo;

public abstract class Mision {

    protected Jugador jugador;

    public abstract boolean setJugador(Jugador jugador);

    public abstract boolean estaCumplida();

}
