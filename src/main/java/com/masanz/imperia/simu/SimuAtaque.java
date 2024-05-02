package com.masanz.imperia.simu;

import com.masanz.imperia.terminal.Juego;

/**
 * Clase para que el juego juegue solo la parte de ataque y ver si hay algún error.
 */
public class SimuAtaque extends DemoJugar {

    public static void main(String[] args) {
        Juego juego = new JuegoSimuAtaque();

        inicializar(juego);

        juego.jugar();
    }

}
