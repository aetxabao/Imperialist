package com.masanz.imperia.modelo;

import java.util.ArrayList;
import java.util.List;

public class Mazo<T> {

    protected List<T> mazo;

    public Mazo() {
        mazo = new ArrayList<>();
    }

    public void barajar() {
        for (int i = 0; i < mazo.size(); i++) {
            int j = (int) (Math.random() * mazo.size());
            T aux = mazo.get(i);
            mazo.set(i, mazo.get(j));
            mazo.set(j, aux);
        }
    }

    public int tamano() {
        return mazo.size();
    }

    public void meter(T objeto) {
        mazo.add(objeto);
    }

    public T sacar() {
        if (mazo.isEmpty()) { return null; }
        return mazo.remove(0);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (T objeto : mazo) {
            sb.append(objeto.toString()).append('\n');
        }
        return sb.toString();
    }

}
