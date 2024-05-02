package com.masanz.imperia.simu;

import com.masanz.imperia.modelo.GestorMisiones;
import com.masanz.imperia.modelo.Jugador;
import com.masanz.imperia.modelo.Mundo;
import com.masanz.imperia.terminal.Gui;

public class JuegoDemoDefensa extends JuegoSimuAtaque {

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
            if (Mundo.ejercitosDeJugador(jugador.getId())>0) {
                if (!jugarDefensa(jugador)) { break; }
            }
            //if (!comprobarJugadores()) { break; }
            comprobarJugadores();
            turno = (turno + 1) % jugadores.size();
        }
        if (hayGanador) {
            Gui.mostrarGanadorJuego(jugador.getNombre());
            Gui.mostrarMisionSecreta(jugador.getNombre(), GestorMisiones.getDescripcionMision(jugador));
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
