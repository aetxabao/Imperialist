package com.masanz.imperia.terminal;

public class Main {

    public static void main(String[] args) {
        Juego juego = new Juego();

        juego.crearJugadores();
        juego.repartirTerritorios();
        juego.asignarMisiones();
        juego.colocarEjercitos();

        juego.jugar();
    }

}
