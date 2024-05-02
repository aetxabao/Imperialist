package com.masanz.imperia.simu;

import com.masanz.imperia.terminal.Juego;

/**
 * Clase para que el juego juegue solo (parte de ataque y defensa) y ver si hay algún error.
 * Para que en el juego solo quede un jugador los cambios de tarjetas deben ser pequeños, 1 o 2.
 */
public class SimuDefensa extends DemoJugar {

    public static void main(String[] args) {
        Juego juego = new JuegoSimuDefensa();

        inicializar(juego);

        juego.jugar();
    }

}
