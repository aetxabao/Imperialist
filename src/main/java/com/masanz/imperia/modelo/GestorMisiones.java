package com.masanz.imperia.modelo;

import com.masanz.imperia.terminal.Gui;

import java.util.*;

// TODO: Parte 1 "Misiones"

/**
 * @author TODO PON AQUÍ TU NOMBRE
 */
public class GestorMisiones {

    private static Mazo<Mision> mazoMisiones = new Mazo<>();
    private static Map<Jugador, Mision> mapaJugadoresMisiones = new HashMap<>();

    public static void cargarMisiones(List<Jugador> listaJugadores) {
//        mazoMisiones.meter(new MisionConquistarListaTerritorios(Arrays.asList("ADFI", "ASIR", "ASDI")));
//        List<String> listaNombreContinentes = Mundo.getListaNombresContinentes();
//        for (String continente : listaNombreContinentes) {
//            mazoMisiones.meter(new MisionConquistarContinente(continente));
//        }
//        mazoMisiones.meter(new MisionConquistarNTerritoriosDeCadaContinente(2));
//
//        for (Jugador jugador : listaJugadores) {
//            mapaJugadoresMisiones.put(jugador, null);
//        }
    }

    public static void repartirMisiones() {
//        mazoMisiones.barajar();
//        for (Jugador jugador : mapaJugadoresMisiones.keySet()) {
//            Mision mision = mazoMisiones.sacar();
//            mision.setIdJugador(jugador.getId());
//            while (mision.estaCumplida()) {
//                mazoMisiones.meter(mision);
//                mision = mazoMisiones.sacar();
//                mision.setIdJugador(jugador.getId());
//            }
//            mazoMisiones.meter(mision);
//            mapaJugadoresMisiones.put(jugador, mision);
//        }
    }

    public static boolean cumpleMision(Jugador jugador) {
//        return mapaJugadoresMisiones.get(jugador).estaCumplida();
        return false;
    }

    public static String getDescripcionMision(Jugador jugador) {
        return mapaJugadoresMisiones.get(jugador).toString();
    }

    public static List<String> getMisiones() {
        // TODO: Parte 2, listado 1, getMisiones
        List<String> result = new ArrayList<>();

        return result;
    }

//    public static void main(String[] args) {
//        Mundo.loadWorld();
//        List<Jugador> listaJugadores = new ArrayList<>();
//        listaJugadores.add(new Jugador("D", "Edu"));
//        listaJugadores.add(new Jugador("T", "Aitor"));
//        listaJugadores.add(new Jugador("L", "Alba"));
//        cargarMisiones(listaJugadores);
//        repartirMisiones();
//        Gui.mostrar(getMisiones());
//    }

}
