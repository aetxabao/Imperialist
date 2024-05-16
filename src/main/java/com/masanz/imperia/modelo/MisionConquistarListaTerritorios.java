package com.masanz.imperia.modelo;

import java.util.List;

/**
 * @author TODO PON AQUÍ TU NOMBRE
 */
public class MisionConquistarListaTerritorios extends Mision {

    protected List<String> listaTerritorios;

    public MisionConquistarListaTerritorios(List<String> listaTerritorios) {
        this.listaTerritorios = listaTerritorios;
    }

    @Override
    public boolean estaCumplida() {
        for (String territorio : listaTerritorios) {
            if (!Mundo.esTerritorioDelJugador(territorio, idJugador)){
                return false;
            }
        }
        return true;
    }

    @Override
    public int numeroTerritorios() {
        return listaTerritorios.size();
    }

    @Override
    public String toString() {
        return "Conquistar " + listaTerritorios.toString();
    }
}

