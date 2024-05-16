package com.masanz.imperia.modelo;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GestorMisiones {

    private static Mazo<Mision> mazoMisiones = new Mazo<>();
    private static Map<Jugador, Mision> mapaJugadoresMisiones = new HashMap<>();

    public static void cargarMisiones(List<Jugador> listaJugadores) {
        mazoMisiones.meter(new MisionConquistar24Territorios());
        mazoMisiones.meter(new MisionConquistar18TerritoriosCon2Ejercitos());
        mazoMisiones.meter(new MisionConquistar2Continentes("Asia", "América del Sur"));
        mazoMisiones.meter(new MisionConquistar2Continentes("Asia", "África"));
        mazoMisiones.meter(new MisionConquistar2Continentes("América del Norte", "África"));
        mazoMisiones.meter(new MisionConquistar2Continentes("América del Norte", "Oceanía"));
        for (Jugador jugador : listaJugadores) {
            mazoMisiones.meter(new MisionDestruirJugador(jugador));
            mapaJugadoresMisiones.put(jugador, null);
        }
    }

    public static void repartirMisiones() {
        mazoMisiones.barajar();
        for (Jugador jugador : mapaJugadoresMisiones.keySet()) {
            Mision mision = mazoMisiones.sacar();
            if (mision.setJugador(jugador)) {
                mapaJugadoresMisiones.put(jugador, mision);
            }else{
                mazoMisiones.meter(mision);
                mision = mazoMisiones.sacar();
                mision.setJugador(jugador);
                mapaJugadoresMisiones.put(jugador, mision);
            }
        }
    }

    public static boolean cumpleMision(Jugador jugador) {
        return mapaJugadoresMisiones.get(jugador).estaCumplida();
    }

    public static String getDescripcionMision(Jugador jugador) {
        return mapaJugadoresMisiones.get(jugador).toString();
    }

}
