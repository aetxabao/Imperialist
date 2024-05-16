package com.masanz.imperia.modelo;

import java.util.List;

/**
 * @author TODO PON AQUÍ TU NOMBRE
 */
public class MisionConquistarNTerritoriosDeCadaContinente extends Mision {

    protected int n;

    public MisionConquistarNTerritoriosDeCadaContinente(int n) {
        this.n = n;
    }

    @Override
    public boolean estaCumplida() {
        List<String> continentes = Mundo.getListaNombresContinentes();
        for (String continente : continentes) {
            int numTerJug = Mundo.getNumeroTerritoriosContinenteDelJugador(continente, idJugador);
            int numTerCont = Mundo.getNumeroTerritoriosContinente(continente);
            if (numTerJug != numTerCont && numTerJug < n) { return false; }
        }
        return true;
    }

    @Override
    public int numeroTerritorios() {
        return Mundo.getListaNombresContinentes().size() * n;
    }

    @Override
    public String toString() {
        return "Conquistar " + n + " territorios de cada continente";
    }

}