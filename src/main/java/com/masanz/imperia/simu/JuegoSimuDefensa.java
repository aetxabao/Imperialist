package com.masanz.imperia.simu;

import com.masanz.imperia.modelo.Jugador;
import com.masanz.imperia.terminal.Gui;

import java.util.List;

public class JuegoSimuDefensa extends JuegoDemoDefensa{

    @Override
    public boolean jugarDefensa(Jugador jugador) {
        if (haceFaltaCambiar(jugador)) {
            return cambiar(jugador);
        }
        return true;
    }

    @Override
    public boolean cambiar(Jugador jugador) {
        List<String> listaNombres = repartoEjercitos(jugador.getId(), jugador.cambiarTarjetas());
        Gui.mostrarTerritorios(listaNombres);
        Gui.mostrarTerritorios(jugador.getId());
        return true;
    }

}
