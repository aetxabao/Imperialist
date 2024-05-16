package com.masanz.imperia.modelo;

public class MisionConquistar18TerritoriosCon2Ejercitos extends MisionConquistar24Territorios {

    @Override
    public boolean setJugador(Jugador jugador) {
        this.jugador = jugador;
        return true;
    }

    @Override
    public boolean estaCumplida() {
        return Mundo.tiene18TerritoriosCon2Ejercitos(jugador.getId());
    }

    @Override
    public String toString() {
        return "Conquistar 18 territorios con al menos 2 ejércitos en cada uno";
    }

}
