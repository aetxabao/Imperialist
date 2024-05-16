package com.masanz.imperia.modelo;

public class MisionConquistar2Continentes extends Mision {

    protected String continente1;
    protected String continente2;

    public MisionConquistar2Continentes(String continente1, String continente2) {
        this.continente1 = continente1;
        this.continente2 = continente2;
    }

    @Override
    public boolean setJugador(Jugador jugador) {
        this.jugador = jugador;
        return true;
    }

    @Override
    public boolean estaCumplida() {
        return Mundo.esContinenteDe(continente1, jugador.getId()) && Mundo.esContinenteDe(continente2, jugador.getId());
    }

    @Override
    public String toString() {
        return "Conquistar " + continente1 + " y " + continente2;
    }
}
