package com.masanz.imperia.modelo;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import static com.masanz.imperia.consts.Ctes.*;

/**
 * Clase MazoTarjetas que representa un mazo de tarjetas
 * que inicialmente está vacío y se puede cargar con 44 tarjetas (mazo del juego)
 * o metiendo tarjetas (mazos de cada jugador). Se puede sacar y cambiar tarjetas.
 * @author TODO: 10 AQUÍ_TU_NOMBRE
 */
public class MazoTarjetas {

    private List<Tarjeta> mazo;

    public MazoTarjetas() {
        mazo = new ArrayList<>();
    }

    public void agregar44Tarjetas() {
        // TODO: 11 Agregar 44 tarjetas al mazo de tarjetas: 14 de infantería, 14 de caballería, 14 de artillería y 2 comodines.







    }

    public void barajar() {
        // TODO: 12 Barajar las tarjetas del mazo: Intercambiar cada tarjeta con otra tarjeta aleatoria (tantas veces como tarjetas haya).






    }

    public int tamano() {
        return mazo.size();
    }

    public void meterTarjeta(Tarjeta tarjeta) {
        mazo.add(tarjeta);
    }

    public Tarjeta sacarTarjeta() {
        if (mazo.isEmpty()) { return null; }
        return mazo.remove(0);
    }

    public Tarjeta sacarTarjeta(String tipo) {
        return sacarTarjeta(tipo, true);
    }

    public Tarjeta sacarTarjetaNo(String tipo) {
        return sacarTarjeta(tipo, false);
    }

    private Tarjeta sacarTarjeta(String tipo, boolean igual) {
        // TODO: 13 Sacar la primera tarjeta del mazo que sea del tipo indicado y devolverla si igual es true.
        // TODO: 13 Si igual es false, sacar la primera tarjeta del mazo que no sea del tipo indicado y devolverla.
        // si el mazo tiene 4 tarjetas: T_ARTILLERIA, T_INFANTERIA, T_CABALLERIA, T_ARTILLERIA
        // si tipo = T_INFANTERIA e igual = true, saca la tarjeta T_INFANTERIA y quedan 3: T_ARTILLERIA, T_CABALLERIA, T_ARTILLERIA
        // si tipo = T_INFANTERIA e igual = false, saca la tarjeta T_ARTILLERIA y quedan 3: T_INFANTERIA, T_CABALLERIA, T_ARTILLERIA
        // si el mazo tiene 4 tarjetas: T_ARTILLERIA, T_INFANTERIA, T_CABALLERIA, T_ARTILLERIA
        // si tipo = T_ARTILLERIA e igual = true, saca la tarjeta T_ARTILLERIA y quedan 3: T_INFANTERIA, T_CABALLERIA, T_ARTILLERIA
        // si tipo = T_ARTILLERIA e igual = false, saca la tarjeta T_INFANTERIA y quedan 3: T_ARTILLERIA, T_CABALLERIA, T_ARTILLERIA
        // si el mazo tiene 4 tarjetas: T_ARTILLERIA, T_INFANTERIA, T_INFANTERIA, T_ARTILLERIA
        // si tipo = T_CABALLERIA e igual = true, devuelve null y quedan las mismas tarjetas
        // si tipo = T_CABALLERIA e igual = false, saca la tarjeta T_ARTILLERIA y quedan 3: T_INFANTERIA, T_INFANTERIA, T_ARTILLERIA
        // si el mazo tiene 2 tarjetas: T_INFANTERIA, T_INFANTERIA
        // si tipo = T_INFANTERIA e igual = true, saca la tarjeta T_INFANTERIA y queda 1: T_INFANTERIA
        // si tipo = T_INFANTERIA e igual = false, devuelve null y quedan las mismas tarjetas








        return null;
    }

    public int cambiar() {
        if (tresDistintas()) { return CAMBIO_TRESTIPOS; }
        if (tresIguales(T_ARTILLERIA)) { return CAMBIO_ARTILLERIA; }
        if (tresIguales(T_CABALLERIA)) { return CAMBIO_CABALLERIA; }
        if (tresIguales(T_INFANTERIA)) { return CAMBIO_INFANTERIA; }
        return 0;
    }

    private Map<String, Integer> getMapaTiposCantidad() {
        // TODO: 14 Devolver un mapa ordenado con el tipo de tarjeta y la cantidad de cada tipo que hay en el mazo.









        return null;
    }

    private boolean tresDistintas() {
        // Poner en un map el tipo de tarjeta y la cantidad de cada tipo
        Map<String, Integer> mapa = getMapaTiposCantidad();
        // Si hay 4 tipos distintos o 3 tipos distintos sin el comodín
        // quitar uno de cada tipo que no sea comodín y devolver true
        if (mapa.size() == 4 || (mapa.size() == 3 && !mapa.containsKey(T_COMODIN))) {
            sacarTarjeta(T_INFANTERIA);
            sacarTarjeta(T_CABALLERIA);
            sacarTarjeta(T_ARTILLERIA);
            return true;
        }
        // Si hay 3 tipos distintos incluido el comodín, quitar uno de cada tipo y el comodín y devolver true
        // Si se quita una tipo que no existe no pasa nada, no hace falta saber los tipos que hay
        if (mapa.size() == 3) {
            sacarTarjeta(T_INFANTERIA);
            sacarTarjeta(T_CABALLERIA);
            sacarTarjeta(T_ARTILLERIA);
            sacarTarjeta(T_COMODIN);
            return true;
        }
        // Si hay 2 tipos distintos incluido el comodín y hay dos comodines,
        // quitar los dos comodines y uno del otro tipo y devolver true
        if (mapa.size() == 2 && mapa.containsKey(T_COMODIN) && mapa.get(T_COMODIN) == 2) {
            sacarTarjeta(T_COMODIN);
            sacarTarjeta(T_COMODIN);
            sacarTarjetaNo(T_COMODIN);
            return true;
        }
        return false;
    }

    private boolean tresIguales(String tipo) {
        // Poner en un map el tipo de tarjeta y la cantidad de cada tipo
        Map<String, Integer> mapa = getMapaTiposCantidad();
        // Si hay 3 tarjetas del mismo tipo, quitar 3 tarjetas de ese tipo y devolver true
        int cantidad = mapa.getOrDefault(tipo, 0);
        if (cantidad >= 3) {
            for (int i = 0; i < 3; i++) {
                sacarTarjeta(tipo);
            }
            return true;
        }
        return false;
    }

    /**
     * Devuelve una cadena con el número de tarjetas de cada tipo que hay en el mazo obviando los tipos que no hay.
     * @return Por ejemplo "ARTILLERÍA(2) COMODÍN(1) INFANTERÍA(1)" o
     * "ARTILLERÍA(14) CABALLERÍA(14) COMODÍN(2) INFANTERÍA(14)"
     */
    public String getTiposCantidad() {
        // Poner en un map el tipo de tarjeta y la cantidad de cada tipo
        Map<String, Integer> mapa = getMapaTiposCantidad();
        // TODO: 15 Devolver una cadena con el número de tarjetas de cada tipo que hay en el mazo obviando los tipos que no hay iterando el mapa.







        return "";
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (Tarjeta tarjeta : mazo) {
            sb.append(tarjeta.getTipo()).append('\n');
        }
        return sb.toString();
    }

}