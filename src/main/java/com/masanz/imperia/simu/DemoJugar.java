package com.masanz.imperia.simu;

import com.masanz.imperia.modelo.Jugador;
import com.masanz.imperia.terminal.Gui;
import com.masanz.imperia.terminal.Juego;

import java.util.ArrayList;
import java.util.List;

/**
 * Clase que permite ejecutar la acción del juego saltándose la creación de jugadores y asignación de territorios y ejércitos.
 * Para hacer pruebas de la parte del juego de modo interactivo
 */
public class DemoJugar {

    protected static void inicializar(Juego juego) {
        List<Jugador> jugadores = new ArrayList<>();
        jugadores.add(new Jugador("T", "Aitor"));
        jugadores.add( new Jugador("L", "Alba"));
        jugadores.add( new Jugador("D", "Edu"));
        juego.setJugadores(jugadores);
        juego.repartirTerritorios();
        juego.asignarMisiones();
        juego.colocarUnEjercitoEnCadaTerritorio();
        for (Jugador jugador : jugadores) {
            juego.repartoEjercitos(jugador.getId());
        }
    }

    public static void main(String[] args) {
        Juego juego = new Juego();

        inicializar(juego);

        juego.jugar();
    }

}
