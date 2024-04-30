package com.masanz.imperia.simu;

import com.masanz.imperia.terminal.Gui;
import com.masanz.imperia.modelo.Jugador;

import java.util.ArrayList;
import java.util.List;

/**
 * Clase para que el juego se juegue solo y ver si hay algún error.
 * Una partida puede ser muuuuuy larga.
 */
public class Simulacion {

    public static void main(String[] args) {
        SimulaJuego juego = new SimulaJuego();

        List<Jugador> jugadores = new ArrayList<>();
        jugadores.add(new Jugador("T", "Aitor"));
        jugadores.add( new Jugador("L", "Alba"));
        jugadores.add( new Jugador("D", "Edu"));
        juego.setJugadores(jugadores);
        juego.repartirTerritorios();
        for (Jugador jugador : jugadores) {
            juego.repartoUniformeEjercitos(jugador.getId());
        }
        System.out.println();

        juego.jugarJuegoSimu();
    }

}
