package com.masanz.imperia.modelo;

import com.masanz.imperia.consts.Ctes;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

/**
 * Clase que representa el mapa de juego que consta de territorios de diferentes continentes.
 */
public class Mundo {

    // region Atributos
    private static TreeMap<String, Territorio> mapaTerritorios = new TreeMap<>();
    // Definición del mapa de continentes.
    private static TreeMap<String, List<Territorio>> mapaContinentes = new TreeMap<>();
    // endregion

    /**
     * Carga los mapas de los territorios y los continentes.
     * Los territorios se cargan desde el fichero "territorios.txt" que se encuentra en la carpeta "data".
     * Observar la estructura de este fichero. Las líneas que comienzan con "#" indican el nombre de un continente.
     * Las líneas que no comienzan con "#" indican un territorio y sus vecinos.
     * Los territorios corresponden al continente que se haya indicado en la última línea que comienza con "#".
     */
    public static void loadWorld() {
        String continente = null;
        List<Territorio> territoriosContinente = null;

        String line;
        //PATH_TERRITORIOS = "data" + FileSystems.getDefault().getSeparator() + "territorios.txt";
        File file = new File(Ctes.PATH_TERRITORIOS);
        try {
            BufferedReader reader = new BufferedReader(new FileReader(file));
            while ( (line = reader.readLine()) != null ) {
                if (line.isEmpty()) { continue; }

                if (line.startsWith("#")) {
                    continente = line.replace("#", "").trim();
                    territoriosContinente = new ArrayList<>();
                    mapaContinentes.put(continente, territoriosContinente);
                    continue;
                }

                String[] a = line.split(Ctes.EXP_TERRITORIO_SPLITTER); // EXP_TERRITORIO_SPLITTER = "\s*-\s*";
                Territorio territorio = new Territorio(a[0]);
                for (int i = 1; i < a.length; i++) {
                    territorio.agregarVecino(a[i]);
                }
                mapaTerritorios.put(a[0], territorio);

                territoriosContinente.add(territorio);

                //System.out.println(Arrays.toString(a));
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    // region Territorios
    public static Territorio getTerritorio(String nombreTerritorio) {
        if (nombreTerritorio == null) { return null; }
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

    public static int ejercitosDeJugador(String idJugador) {
        int n = 0;
        for (Territorio territorio : mapaTerritorios.values()) {
            if (territorio.getJugador().getId().equals(idJugador)) {
                n += territorio.getEjercitos();
            }
        }
        return n;
    }

    public static int ejercitosDeTerritorio(String nombreTerritorio) {
        int n = 0;
        if (nombreTerritorio == null) { return n; }
        Territorio territorio = mapaTerritorios.get(nombreTerritorio);
        if (territorio != null) { n = territorio.getEjercitos(); }
        return n;
    }

    public static boolean estaTerritorio(String nombreTerritorio) {
        return mapaTerritorios.containsKey(nombreTerritorio);
    }

    public static boolean esTerritorioDelJugador(String nombreTerritorio, String idJugador) {
        return mapaTerritorios.containsKey(nombreTerritorio) && mapaTerritorios.get(nombreTerritorio).getJugador().getId().equals(idJugador);
    }

    public static boolean sonVecinos(String nombreTerritorio1, String nombreTerritorio2, boolean delMismoJugador) {
        Territorio territorio1 = mapaTerritorios.get(nombreTerritorio1);
        Territorio territorio2 = mapaTerritorios.get(nombreTerritorio2);
        List<String> vecinos1 = territorio1.getVecinos();
        if (nombreTerritorio1 == null || territorio1 == null ||
                nombreTerritorio2 == null || territorio2 == null ||
                !vecinos1.contains(nombreTerritorio2)) { return false; }
        return delMismoJugador == territorio1.getJugador().getId().equals(territorio2.getJugador().getId());// not xor = xnor
    }

    // endregion

    // region Misiones

    public static boolean esContinenteDe(String nombreContinente, String idJugador) {
        List<Territorio> territorios = mapaContinentes.get(nombreContinente);
        for (Territorio territorio : territorios) {
            if (!territorio.getJugador().getId().equals(idJugador)) {
                return false;
            }
        }
        return true;
    }

    public static boolean tiene24Territorios(String idJugador) {
        int n = 0;
        for (Territorio territorio : mapaTerritorios.values()) {
            if (territorio.getJugador().getId().equals(idJugador)) {
                n++;
            }
        }
        return n >= 24;
    }

    public static boolean tiene18TerritoriosCon2Ejercitos(String idJugador) {
        int n = 0;
        for (Territorio territorio : mapaTerritorios.values()) {
            if (territorio.getJugador().getId().equals(idJugador) && territorio.getEjercitos() >= 2) {
                n++;
            }
        }
        return n >= 18;
    }
    // endregion

//    public static void main(String[] args) {
//        MapaTerritorios.loadWorld();
//        System.out.println(MapaTerritorios.listadoConFronteras());
//    }

}
