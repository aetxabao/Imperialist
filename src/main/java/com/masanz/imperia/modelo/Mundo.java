package com.masanz.imperia.modelo;

import com.masanz.imperia.consts.Ctes;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

/**
 * Clase que representa el mapa de juego que consta de territorios
 */
public class Mundo {

    // region Atributos
    private static TreeMap<String, Territorio> mapaTerritorios = new TreeMap<>();
    // endregion

    public static void loadWorld() {
        String line;
        //PATH_TERRITORIOS = "data" + FileSystems.getDefault().getSeparator() + "territorios.txt";
        File file = new File(Ctes.PATH_TERRITORIOS);
        try {
            BufferedReader reader = new BufferedReader(new FileReader(file));
            while ( (line = reader.readLine()) != null ) {
                if (line.isEmpty()) { continue; }
                String[] a = line.split(Ctes.EXP_TERRITORIO_SPLITTER); // EXP_TERRITORIO_SPLITTER = "\s*-\s*";
                Territorio territorio = new Territorio(a[0]);
                for (int i = 1; i < a.length; i++) {
                    territorio.agregarVecino(a[i]);
                }
                mapaTerritorios.put(a[0], territorio);
                //System.out.println(Arrays.toString(a));
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static Territorio getTerritorio(String nombreTerritorio) {
        return mapaTerritorios.get(nombreTerritorio);
    }

    public static void ponerEnTerritorioEjercitos(String nombreTerritorio, int numEjercitos) {
        Territorio territorio = mapaTerritorios.get(nombreTerritorio);
        territorio.setEjercitos(numEjercitos);
    }

    public static void addTerritorio(String nombreTerritorio, Territorio territorio) {
        mapaTerritorios.put(nombreTerritorio, territorio);
    }

    public static List<Territorio> getListaTerritorios() {
        return new ArrayList<>(mapaTerritorios.values());
    }

    public static List<Territorio> getListaTerritoriosDelJugador(String idJugador) {
        List<Territorio> lista = new ArrayList<>();
        for (Territorio territorio : mapaTerritorios.values()) {
            if (territorio.getJugador().getId().equals(idJugador)) {
                lista.add(territorio);
            }
        }
        return lista;
    }

    public static String listadoConFronteras() {
        return listadoConFronteras(null);
    }

    public static String listadoConFronteras(String idJugador) {
        StringBuilder sb = new StringBuilder();
        for (Territorio territorio : mapaTerritorios.values()) {
            if (idJugador==null || territorio.getJugador().getId().equals(idJugador)) {
                sb.append(territorio.toString()).append(' ').append('[');
                for (String nombreVecino : territorio.getVecinos()) {
                    //System.out.println(nombreVecino);
                    Territorio vecino = mapaTerritorios.get(nombreVecino);
                    if (vecino == null) {
                        sb.append(nombreVecino).append(',').append(' ');
                    }else {
                        sb.append(vecino.toString()).append(',').append(' ');
                    }
                }
                sb.deleteCharAt(sb.length()-1);
                sb.deleteCharAt(sb.length()-1);
                sb.append(']').append('\n');
            }
        }
        return sb.toString();
    }

    public static String descripcionTerritorioConFronteras(String nombreTerritorio) {
        Territorio territorio = mapaTerritorios.get(nombreTerritorio);
        StringBuilder sb = new StringBuilder();
        sb.append(territorio).append(' ').append('[');
        for (String nombreVecino : territorio.getVecinos()) {
            //System.out.println(nombreVecino);
            Territorio vecino = mapaTerritorios.get(nombreVecino);
            if (vecino == null) {
                sb.append(nombreVecino).append(',').append(' ');
            }else {
                sb.append(vecino.toString()).append(',').append(' ');
            }
        }
        sb.deleteCharAt(sb.length()-1);
        sb.deleteCharAt(sb.length()-1);
        sb.append(']');
        return sb.toString();
    }

    public static List<String> listaNombresTerritorios() {
        return new ArrayList<>(mapaTerritorios.keySet());
    }

    public static int ejercitosDe(String idJugador) {
        int n = 0;
        for (Territorio territorio : mapaTerritorios.values()) {
            if (territorio.getJugador().getId().equals(idJugador)) {
                n += territorio.getEjercitos();
            }
        }
        return n;
    }

    public static boolean estaTerritorio(String nombreTerritorio) {
        return mapaTerritorios.containsKey(nombreTerritorio);
    }

    public static boolean esTerritorioDelJugador(String nombreTerritorio, String idJugador) {
        return mapaTerritorios.containsKey(nombreTerritorio) && mapaTerritorios.get(nombreTerritorio).getJugador().getId().equals(idJugador);
    }

//    public static void main(String[] args) {
//        MapaTerritorios.loadWorld();
//        System.out.println(MapaTerritorios.listadoConFronteras());
//    }

}
