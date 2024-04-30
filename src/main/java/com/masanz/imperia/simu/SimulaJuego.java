package com.masanz.imperia.simu;

import com.masanz.imperia.modelo.DosValores;
import com.masanz.imperia.modelo.Jugador;
import com.masanz.imperia.modelo.Mundo;
import com.masanz.imperia.modelo.Territorio;
import com.masanz.imperia.terminal.Gui;
import com.masanz.imperia.terminal.Juego;

import java.util.List;

/**
 * Clase que hereda de Juego para poder ejecutar el main de Simulacion
 * Para separar la simulación de la clase Juego
 */
public class SimulaJuego extends Juego {

    public void jugarJuegoSimu() {
        while (true) {
            Jugador jugador = jugadores.get(turno);
            if (!jugarSimu(jugador)) { break; }
            if (!comprobarJugadores()) { break; }
            turno = (turno + 1) % jugadores.size();
        }
        Gui.mostrarGanadorJuego(jugadores.get(0).getNombre());
    }

    public boolean jugarSimu(Jugador jugador) {
        Territorio territorioAtacante = obtenerTerritorioAtacanteSimu(jugador);

        Territorio territorioAtacado = obtenerTerritorioAtacadoSimu(territorioAtacante);

        DosValores dosValores = atacar(territorioAtacante, territorioAtacado);
        int perdidasAtacanteTotal = dosValores.uno();
        int perdidasAtacadoTotal = dosValores.dos();

        resultado(territorioAtacante, territorioAtacado, perdidasAtacanteTotal, perdidasAtacadoTotal);

        return true;
    }

    private Territorio obtenerTerritorioAtacanteSimu(Jugador jugador) {
        List<Territorio> territorioList = Mundo.getListaTerritoriosDelJugador(jugador.getId());
        while (true) {
            int i = (int) (Math.random()*territorioList.size());
            Territorio territorio = territorioList.get(i);
            List<String> vecinos = territorio.getVecinos();
            for (String vecino : vecinos) {
                Territorio territorioVecino = Mundo.getTerritorio(vecino);
                if (!territorioVecino.getJugador().equals(jugador)) {
                    return territorio;
                }
            }
        }
    }

    private Territorio obtenerTerritorioAtacadoSimu(Territorio territorioAtacante) {
        Jugador jugador = territorioAtacante.getJugador();
        List<String> vecinos = territorioAtacante.getVecinos();
        while (true) {
            for (String vecino : vecinos) {
                Territorio territorioVecino = Mundo.getTerritorio(vecino);
                if (!territorioVecino.getJugador().equals(jugador)) {
                    return territorioVecino;
                }
            }
        }
    }

}
