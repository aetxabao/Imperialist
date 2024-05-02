package com.masanz.imperia.simu;

import com.masanz.imperia.modelo.*;
import com.masanz.imperia.terminal.Gui;
import com.masanz.imperia.terminal.Juego;

import java.util.List;

/**
 * Clase que hereda de Juego para poder ejecutar el main de Simulacion
 * Por no meter estos métodos en la clase Juego y ensuciarla
 */
public class JuegoSimuAtaque extends Juego {

    @Override
    public void jugar() {
        boolean hayGanador = false;
        Jugador jugador;
        while (true) {
            jugador = jugadores.get(turno);
            if (jugadores.size()==1) {
                // Puede ser que solo quede un jugador y no haya conseguido su misión
                // si tenía que tener 18 territorios con dos ejércitos en cada uno
                hayGanador = true;
                break;
            }
            if (GestorMisiones.cumpleMision(jugador)) { hayGanador = true; break; }
            if (!jugarAtaque(jugador)) { break; }
            //if (!comprobarJugadores()) { break; }
            comprobarJugadores();
            turno = (turno + 1) % jugadores.size();
        }
        Gui.mostrarGanadorJuego(jugador.getNombre());
        Gui.mostrarMisionSecreta(jugador.getNombre(), GestorMisiones.getDescripcionMision(jugador));
    }

    @Override
    public boolean jugarAtaque(Jugador jugador) {
        Territorio territorioAtacante = obtenerTerritorioAtacanteSimu(jugador);

        Territorio territorioAtacado = obtenerTerritorioAtacadoSimu(territorioAtacante);

        DosValores dosValores = atacar(territorioAtacante, territorioAtacado);
        int perdidasAtacanteTotal = dosValores.uno();
        int perdidasAtacadoTotal = dosValores.dos();

        resultadoAtaque(territorioAtacante, territorioAtacado, perdidasAtacanteTotal, perdidasAtacadoTotal);

        return true;
    }

    private Territorio obtenerTerritorioAtacanteSimu(Jugador jugador) {
        int i;
        Territorio territorio;
        List<Territorio> territorioList = Mundo.getListaTerritoriosDelJugador(jugador.getId());
        while (true) {
            try {
                i = (int) (Math.random()*territorioList.size());
                territorio = territorioList.get(i);
                List<String> vecinos = territorio.getVecinos();
                for (String vecino : vecinos) {
                    Territorio territorioVecino = Mundo.getTerritorio(vecino);
                    if (!territorioVecino.getJugador().equals(jugador)) {
                        return territorio;
                    }
                }
            } catch (Exception e) {
                System.out.println("Error en obtenerTerritorioAtacanteSimu");
                throw e;
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
