package com.masanz.imperia.simu;

import com.masanz.imperia.modelo.Jugador;
import com.masanz.imperia.modelo.Mundo;
import com.masanz.imperia.terminal.Gui;

public class JuegoDemoDefensa extends JuegoSimuAtaque {

    @Override
    public void jugar() {
        while (true) {
            Jugador jugador = jugadores.get(turno);
            if (!jugarAtaque(jugador)) { break; }
            if (Mundo.ejercitosDeJugador(jugador.getId())>0) {
                if (!jugarDefensa(jugador)) { break; }
            }
            if (!comprobarJugadores()) { break; }
            turno = (turno + 1) % jugadores.size();
        }
        if (jugadores.size() == 1) {
            Gui.mostrarGanadorJuego(jugadores.get(0).getNombre());
        }else {
            Gui.mostrarFinJuego();
        }
    }

    @Override
    public boolean jugarDefensa(Jugador jugador) {
        if (haceFaltaCambiar(jugador)) {
            return super.jugarDefensa(jugador);
        }
        return true;
    }

}
