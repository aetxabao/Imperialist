package com.masanz.imperia.modelo;

import java.util.Map;
import java.util.TreeMap;

import static com.masanz.imperia.consts.Ctes.*;

/**
 * Clase MazoTarjetas que representa un mazo de tarjetas
 * que inicialmente está vacío y se puede cargar con 44 tarjetas (mazo del juego)
 * o metiendo tarjetas (mazos de cada jugador). Se puede sacar y cambiar tarjetas.
 */
public class MazoTarjetas extends Mazo<Tarjeta> {

    public void agregar44Tarjetas() {
        for (int i = 0; i < 14; i++) {
            meter(new Tarjeta(T_INFANTERIA));
            meter(new Tarjeta(T_CABALLERIA));
            meter(new Tarjeta(T_ARTILLERIA));
        }
        meter(new Tarjeta(T_COMODIN));
        meter(new Tarjeta(T_COMODIN));
    }

    public Tarjeta sacarTarjeta(String tipo) {
        return sacarTarjeta(tipo, true);
    }

    public Tarjeta sacarTarjetaNo(String tipo) {
        return sacarTarjeta(tipo, false);
    }

    private Tarjeta sacarTarjeta(String tipo, boolean igual) {
        for (int i = 0; i < tamano(); i++) {
            if (mazo.get(i).getTipo().equals(tipo) && igual) {
                return mazo.remove(i);
            }
            if (!mazo.get(i).getTipo().equals(tipo) && !igual) {
                return mazo.remove(i);
            }
        }
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
        Map<String, Integer> mapa = new TreeMap<>();
        for (Tarjeta tarjeta : mazo) {
            String tipo = tarjeta.getTipo();
            if (mapa.containsKey(tipo)) {
                mapa.put(tipo, mapa.get(tipo) + 1);
            } else {
                mapa.put(tipo, 1);
            }
        }
        return mapa;
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
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<String, Integer> entry : mapa.entrySet()) {
            sb.append(entry.getKey()).append("(").append(entry.getValue()).append(") ");
        }
        if (mapa.size() > 0) {
            sb.deleteCharAt(sb.length()-1);
        }
        return sb.toString();
    }

}