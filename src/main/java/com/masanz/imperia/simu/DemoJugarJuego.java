package com.masanz.imperia.simu;

import com.masanz.imperia.terminal.Gui;
import com.masanz.imperia.terminal.Juego;
import com.masanz.imperia.modelo.Jugador;

import java.util.Arrays;
import java.util.List;

/**
 * Clase que permite ejecutar la acción del juego saltándose la creación de jugadores y asignación de territorios y ejercitos.
 * Para hacer pruebas de la parte del juego de modo interactivo
 */
public class DemoJugarJuego {

    public static void main(String[] args) {
        Juego juego = new Juego();

        List<Jugador> jugadores = Arrays.asList(new Jugador("T", "Aitor"), new Jugador("L", "Alba"), new Jugador("D", "Edu"));
        juego.setJugadores(jugadores);
        juego.repartirTerritorios();
        for (Jugador jugador : jugadores) {
            juego.repartoUniformeEjercitos(jugador.getId());
        }
        System.out.println();

        juego.jugarJuego();
    }

}
