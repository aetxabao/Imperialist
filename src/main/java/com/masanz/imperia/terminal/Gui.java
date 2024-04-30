package com.masanz.imperia.terminal;

import com.masanz.imperia.consts.Ctes;
import com.masanz.imperia.modelo.Jugador;
import com.masanz.imperia.modelo.Mundo;
import com.masanz.imperia.modelo.Territorio;

import java.util.List;
import java.util.Scanner;

public class Gui {

    private static Scanner teclado = new Scanner(System.in);

    private static void mostrarTitulo(String titulo) {
        int n = Ctes.LONG_TITULO, m1, m2;
        m1 = n - titulo.length();
        m2 = m1 / 2 - 1;
        m1 = m1 % 2 == 0? m2 : m2+1;
        System.out.println("*".repeat(n));
        System.out.println("*" + " ".repeat(m1) + titulo + " ".repeat(m2) + "*");
        System.out.println("*".repeat(n));
        System.out.println();
    }

    public static void mostrarPresentacion() {
        String s = "I M P E R I A L I S T";
        mostrarTitulo(s);
    }

    public static void mostrarGanadorJuego(String nombre) {
        String s = "THE LORD OF THE WORLD IS ";
        s += nombre.toUpperCase();
        mostrarTitulo(s);
    }

    public static void mostrarFinJuego() {
        String s = "MAKE LOVE, NOT WAR. BYE, BYE!";
        mostrarTitulo(s);
    }

    public static int leerNumeroJugadores() {
        int numJugadores = 0;
        while (numJugadores < 2 || numJugadores > 6) {
            System.out.print("Cuántos jugadores hay (2-6): ");
            try{
                numJugadores = Integer.parseInt(teclado.nextLine());
            }catch (Exception e){ }
        }
        System.out.println();
        return numJugadores;
    }

    public static String leerNombreJugador(int numero) {
        String nombre = "";
        while (nombre.length()<1) {
            System.out.printf("Nombre del jugador %d: ",numero);
            nombre = teclado.nextLine().trim();
        }
        return nombre.substring(0, Math.min(nombre.length(), Ctes.MAX_LONG_NOMBRE));
    }

    public static String leerIdentificadorJugador(int numero) {
        String identificador = "";
        while (identificador.length()<1) {
            System.out.printf("Identificador del jugador %d: ",numero);
            identificador = Gui.teclado.nextLine().trim();
        }
        System.out.println();
        return identificador.substring(0, Math.min(identificador.length(), Ctes.MAX_LONG_ID));
    }

    public static void mostrarEmpiezaJugador(int numero, String nombre) {
        System.out.printf("Empieza el jugador %d %s\n", numero, nombre);
        System.out.println();
    }


    public static int menuColocarEjercitos(String nombreJugador, int totalEjercitos) {
        int opc = -1;
        System.out.printf("%s debes colocar tus ejercitos, al menos 1 en cada territorio y %d en total.\n",
                nombreJugador, totalEjercitos);
        System.out.println("1. Mostrar mis territorios");
        System.out.println("2. Mostrar los territorios del mundo entero");
        System.out.println("3. Modificar el número de ejercitos en uno de mis territorios");
        System.out.println("4. Repartir ejercitos pendientes entre mis territorios uniformemente");
        System.out.println("0. Finalizar ");
        System.out.print("Opción: ");
        Scanner scanner = new Scanner(System.in);
        while(opc<0 || opc>4) {
            try {
                opc = Integer.parseInt(scanner.nextLine().trim());
            }catch (Exception e) { }
            System.out.println();
        }
        return opc;
    }

    public static int menuJuego(String nombreJugador) {
        int opc = -1;
        System.out.printf("%s decide que territorio quieres atacar.\n", nombreJugador);
        System.out.println("1. Mostrar mis territorios");
        System.out.println("2. Mostrar los territorios del mundo entero");
        System.out.println("3. Atacar");
        System.out.println("0. Finalizar ");
        System.out.print("Opción: ");
        Scanner scanner = new Scanner(System.in);
        while(opc<0 || opc>3) {
            try {
                opc = Integer.parseInt(scanner.nextLine().trim());
            }catch (Exception e) { }
            System.out.println();
        }
        return opc;
    }

    public static void mostrarTerritorios(String idJugador) {
        System.out.println(Mundo.listadoConFronteras(idJugador));
    }

    public static void mostrarTerritoriosMundo() {
        System.out.println(Mundo.listadoConFronteras());
    }

    public static void modificarEnTerritorioEjercitosJugador(String idJugador, int limiteEjercitos) {
            System.out.printf("Nombre del territorio a modificar: ");
            String nombreTerritorio = teclado.nextLine().trim();
            if (!Mundo.esTerritorioDelJugador(nombreTerritorio,idJugador)) {
                System.out.println("No se reconoce ese territorio como propio.");
                return;
            }
            System.out.printf("Cantidad de ejercitos: ");
            int numEjercitos = Integer.parseInt(teclado.nextLine());
            int totalTerritorios = Mundo.getListaTerritoriosDelJugador(idJugador).size();
            if (numEjercitos > (limiteEjercitos - totalTerritorios) || numEjercitos < 1) {
                System.out.println("Imposible, recuerda que en cada territorio debes poner al menos un ejercito.");
                return;
            }
            Mundo.ponerEnTerritorioEjercitos(nombreTerritorio, numEjercitos);
            int ejercitosColocados = Mundo.ejercitosDe(idJugador);
            System.out.println("Ejercitos colocados, pendientes de colocar " + (limiteEjercitos - ejercitosColocados));
            System.out.println();
    }

    public static void mostrarEjercitosPendientesDeColocarJugador(String idJugador, int limiteEjercitos) {
        System.out.println();
        int ejercitosColocados = Mundo.ejercitosDe(idJugador);
        System.out.println("Ejercitos colocados, pendientes de colocar " + (limiteEjercitos - ejercitosColocados));
    }

    public static boolean comprobarEjercitosJugador(String idJugador, int limiteEjercitos) {
        int ejercitosColocados = Mundo.ejercitosDe(idJugador);
        int ejercitosPendientes = limiteEjercitos - ejercitosColocados;
        if (ejercitosPendientes < 0) {
            System.out.printf("Se han colocado %d ejercito(s) de más.", Math.abs(ejercitosPendientes));
        } else if (ejercitosPendientes > 0) {
            System.out.printf("Falta %d ejercito(s) por colocar.", ejercitosPendientes);
        } else {
            System.out.println("Los ejércitos están colocados.");
        }
        System.out.println();
        return ejercitosPendientes == 0;
    }

    public static void mostrarJugadorSinEjercitosEliminado(String nombreJugador) {
        System.out.printf("%s se ha quedado sin ejercitos, no puede seguir jugando.\n",
                nombreJugador);
        System.out.println();
    }

    public static boolean confirmarFin() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Desea terminar la partida en curso [N]o/[s]í: ");
        String resp = scanner.nextLine().trim();
        if (resp.toUpperCase().startsWith("S")) {
            return true;
        }else {
            return false;
        }
    }

    public static String obtenerNombreTerritorioAtacante(String idJugador) {
        System.out.printf("%-34s", "Desde qué territorio atacas: ");
        String nombreTerritorioAtacante = teclado.nextLine().trim();
        if (!Mundo.esTerritorioDelJugador(nombreTerritorioAtacante, idJugador)) {
            System.out.println("No se reconoce ese territorio como propio.");
            System.out.println();
            return null;
        }
        return nombreTerritorioAtacante;
    }

    public static String obtenerNombreTerritorioAtacado(String nombreTerritorioAtacante) {
        System.out.printf("%-34s", "A qué territorio vas a atacar: ");
        String nombreTerritorioAtacado = teclado.nextLine().trim();
        Territorio territorioAtacante = Mundo.getTerritorio(nombreTerritorioAtacante);
        List<String> vecinos = territorioAtacante.getVecinos();
        if (vecinos.indexOf(nombreTerritorioAtacado) == -1) {
            System.out.println("No se reconoce ese territorio como vecino.");
            System.out.println();
            return null;
        }
        Jugador jugador = territorioAtacante.getJugador();
        Territorio territorioAtacado = Mundo.getTerritorio(nombreTerritorioAtacado);
        if (jugador.equals(territorioAtacado.getJugador())){
            System.out.println("No puedes atacar a tus territorios.");
            System.out.println();
            return null;
        }
        return territorioAtacado.getNombre();
    }

    public static void mostrarPerdidasTirada(String nombreJugador, String tirada, int perdidas, String nombreTerritorioAtacante, int ejercitosTerritorioAtacante) {
        System.out.printf("%-10s %-18s --> pierde %d ejercito(s) de %s, quedan %d\n",
                nombreJugador, tirada, perdidas, nombreTerritorioAtacante, ejercitosTerritorioAtacante);
    }

    private static void mostrarResultadoTerritorioEjercitos(String mensaje, String nombreJugador, String nombreTerritorio, int ejercitos) {
        System.out.printf(mensaje, nombreJugador, nombreTerritorio, ejercitos);
    }

    public static void mostrarNoGanaJugadorPierdeTerritorioEjercitos(String nombreJugador, String nombreTerritorio, int ejercitos) {
        mostrarResultadoTerritorioEjercitos("%s no ha conseguido ganar, ha perdido el territorio de %s y %d ejercito(s)\n",
                nombreJugador, nombreTerritorio, ejercitos);
    }

    public static void mostrarGanaJugadorTerritorioPierdeEjercitos(String nombreJugador, String nombreTerritorio, int ejercitos) {
        mostrarResultadoTerritorioEjercitos("%s ha conseguido ganar %s perdiendo %d ejercito(s)\n",
                nombreJugador, nombreTerritorio, ejercitos);
    }

    public static void mostrarDescripcionesTerritoriosAtacanteAtacado(String nombreTerritorioAtacante, String nombreTerritorioAtacado) {
        System.out.println();
        System.out.println(Mundo.descripcionTerritorioConFronteras(nombreTerritorioAtacante));
        System.out.println(Mundo.descripcionTerritorioConFronteras(nombreTerritorioAtacado));
        System.out.println();
    }
}
