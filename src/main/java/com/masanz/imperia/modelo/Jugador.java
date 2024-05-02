package com.masanz.imperia.modelo;

import java.util.Objects;

/**
 * Clase que representa a un jugador.
 */
public class Jugador {

    private String id;
    private String nombre;
    private MazoTarjetas mazoTarjetas;

    public Jugador(String id, String nombre) {
        this.id = id;
        this.nombre = nombre;
        this.mazoTarjetas = new MazoTarjetas();
    }

    // region id y nombre
    public String getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Jugador jugador = (Jugador) o;
        return Objects.equals(id, jugador.id) && Objects.equals(nombre, jugador.nombre);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, nombre);
    }

    @Override
    public String toString() {
        return nombre + '(' + id + ')';
    }
    // endregion

    // region Tarjetas
    public String getDescripcionTarjetas() {
        return mazoTarjetas.getTiposCantidad();
    }

    public void meterTarjeta(Tarjeta tarjeta) {
        mazoTarjetas.meter(tarjeta);
    }

    public int cantidadTarjetas() {
        return mazoTarjetas.tamano();
    }

    public int cambiarTarjetas() {
        return mazoTarjetas.cambiar();
    }
    // endregion

}
