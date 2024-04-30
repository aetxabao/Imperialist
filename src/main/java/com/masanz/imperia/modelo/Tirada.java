package com.masanz.imperia.modelo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

/**
 * Clase que representa una tirada de dados.
 * La tirada se construye con un número de N dados
 * cuyos valores se ordenan de mayor a menor.
 * Cuando se consulta un dado el índice 0 corresponde al mayor valor.
 * Cuando una tirada se compara con otra se considera que
 * se comparan los dos valores mayores de cada tirada,
 * uno a uno, el mayor valor de la tirada con el mayor valor de la otra,
 * y si al menos las dos tiradas tienen dos dados
 * el segundo mayor valor de la tirada con el segundo mayor valor de la otra.
 * Si el valor de la otra tirada es mayor o igual se considera que la tirada ha perdido.
 */
public class Tirada {

    // region Atributos
    private ArrayList<Integer> valores;
    private Dado dado;
    // endregion

    public Tirada() {
        valores = new ArrayList<>();
        dado = new Dado();
    }

    public void tirarDados(int numDados) {
        valores.clear();
        for (int i = 0; i < numDados; i++) {
            dado.tirar();
            valores.add(dado.getValor());
        }
        ordenarValores();
    }

    /**
     * Ordena los valores de mayor a menor.
     */
    private void ordenarValores() {
        Collections.sort(valores, Collections.reverseOrder());
    }

    /**
     * Obtiene el array con los valores
     * Primero hay que tirar los dados, sino estará vacío
     * @return
     */
    public ArrayList<Integer> getValores() {
        return valores;
    }

    /**
     * Devuelve el valor máximo de la tirada.
     * @return Valor máximo.
     */
    public int getValorMaximo() {
        return valores.get(0);
    }

    /**
     * Devuelve el valor medio de la tirada, considerando el número de dados.
     * @return Valor medio.
     */
    public double getValorMedio() {
        double v = 0;
        for (int i = 0; i < valores.size(); i++) {
            v += valores.get(i);
        }
        return v / valores.size();
    }

    /**
     * Devuelve el número de dados que pierden frente a los dados de otra tirada.
     * Se comparan los valores mayores de cada tirada, uno contra el otro, uno a uno.
     * Si el valor de la otra tirada es mayor o igual se considera que la tirada ha perdido.
     * @return Número de veces que pierden los dados de la tirada frente a los de la otra tirada
     */
    public int perdidas(Tirada otra) {
        int perdidas = 0;
        int n = Math.min(this.valores.size(), otra.valores.size());
        for (int i = 0; i < n; i++) {
            if (this.valores.get(i) <= otra.valores.get(i)) {
                perdidas++;
            }
        }
        return perdidas;
    }

    @Override
    public String toString() {
        return Arrays.toString(valores.toArray());
    }

}
