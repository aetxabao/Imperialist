package com.masanz.imperia.modelo;

public class MisionDestruirJugador extends Mision {

    private Jugador jugadorQueDestruir;

    public MisionDestruirJugador(Jugador jugadorQueDestruir) {
        this.jugadorQueDestruir = jugadorQueDestruir;
    }

    @Override
    public boolean setJugador(Jugador jugador) {
        this.jugador = jugador;
        return !jugadorQueDestruir.equals(jugador);
    }

    @Override
    public boolean estaCumplida() {
        return Mundo.ejercitosDeJugador(jugadorQueDestruir.getId()) == 0;
    }

    @Override
    public String toString() {
        return "Destruir a " + jugadorQueDestruir.getNombre() + " (" + jugadorQueDestruir.getId() + ")";
    }

}
