package com.masanz.imperia.simu;

import com.masanz.imperia.terminal.Juego;

/**
 * Clase que permite ejecutar las acciones de defensa saltándose el resto
 */
public class DemoJugarDefensa extends DemoJugar {

    public static void main(String[] args) {
        Juego juego = new JuegoDemoDefensa();

        inicializar(juego);

        juego.jugar();
    }

}
