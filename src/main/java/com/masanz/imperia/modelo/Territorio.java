package com.masanz.imperia.modelo;

import java.util.ArrayList;
import java.util.List;

public class Territorio {

    // region Atributos
    private String nombre;
    private List<String> vecinos;
    private Jugador jugador;
    private int ejercitos;
    // endregion


    public Territorio(String nombre) {
        this.nombre = nombre;
        vecinos = new ArrayList<>();
        ejercitos = 0;
        jugador = new Jugador("", "");
    }

    public String getNombre() {
        return nombre;
    }

    public Jugador getJugador() {
        return jugador;
    }

    public void setJugador(Jugador jugador) {
        this.jugador = jugador;
    }

    public int getEjercitos() {
        return ejercitos;
    }

    public void setEjercitos(int ejercitos) {
        this.ejercitos = ejercitos;
    }

    public void sumarEjercitos(int ejercitos) {
        this.ejercitos += ejercitos;
    }

    public void restarEjercitos(int ejercitos) {
        this.ejercitos -= ejercitos;
    }

    public List<String> getVecinos() {
        return vecinos;
    }

    public void agregarVecino(String vecino) {
        vecinos.add(vecino);
    }

    @Override
    public String toString() {
        return nombre + ' ' + jugador.getId() + '(' + ejercitos + ')' ;
                //+ ' ' + Arrays.toString(vecinos.toArray());
    }

}
