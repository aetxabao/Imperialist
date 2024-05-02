package com.masanz.imperia.terminal;

import static com.masanz.imperia.consts.Ctes.*;
import com.masanz.imperia.modelo.Mundo;
import com.masanz.imperia.modelo.GestorMisiones;

import java.util.List;
import java.util.Scanner;


public class Gui {

    private static Scanner teclado = new Scanner(System.in);

    private static void mostrarTitulo(String titulo) {
        int n = LONG_TITULO, m1, m2;
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
        String s = "AND THE WINNER IS ";
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
        return nombre.substring(0, Math.min(nombre.length(), MAX_LONG_NOMBRE));
    }

    public static String leerIdentificadorJugador(int numero) {
        String identificador = "";
        while (identificador.length()<1) {
            System.out.printf("Identificador del jugador %d: ",numero);
            identificador = Gui.teclado.nextLine().trim();
        }
        System.out.println();
        return identificador.substring(0, Math.min(identificador.length(), MAX_LONG_ID));
    }

    public static void mostrarEmpiezaJugador(int numero, String nombre) {
        System.out.printf("Empieza el jugador %d %s\n", numero, nombre);
        System.out.println();
    }


    public static int menuColocarEjercitos(String nombreJugador, int ejercitosPendientes) {
        int opc = -1;
        System.out.println("*".repeat(LONG_TITULO/2));
        System.out.printf("%s debes colocar tus ejércitos, al menos 1 en cada territorio. Pendientes: %d\n",
                nombreJugador, ejercitosPendientes);
        System.out.println("1. Mostrar mis territorios");
        System.out.println("2. Mostrar los territorios del mundo entero");
        System.out.println("3. Modificar el número de ejércitos en uno de mis territorios");
        System.out.println("4. Repartir ejércitos pendientes entre mis territorios aleatoriamente");
        System.out.println("5. Mostrar mi misión secreta");
        System.out.println("0. Finalizar ");
        System.out.println("*".repeat(LONG_TITULO/2));
        System.out.print("Opción: ");
        Scanner scanner = new Scanner(System.in);
        while(opc<0 || opc>5) {
            try {
                opc = Integer.parseInt(scanner.nextLine().trim());
            }catch (Exception e) { }
            System.out.println();
        }
        return opc;
    }

    public static int menuAtaque(String nombreJugador) {
        int opc = -1;
        System.out.println("*".repeat(LONG_TITULO/2));
        System.out.printf("%s decide si quieres atacar.\n", nombreJugador);
        System.out.println("1. Mostrar mis territorios");
        System.out.println("2. Mostrar los territorios del mundo entero");
        System.out.println("3. Atacar");
        System.out.println("4. No atacar");
        System.out.println("5. Mostrar mi misión secreta");
        System.out.println("0. Finalizar ");
        System.out.println("*".repeat(LONG_TITULO/2));
        System.out.print("Opción: ");
        while(opc<0 || opc>5) {
            try {
                opc = Integer.parseInt(teclado.nextLine().trim());
            }catch (Exception e) { }
            System.out.println();
        }
        return opc;
    }

    public static int menuDefensa(String nombreJugador, String descripcionTarjetas) {
        int opc = -1;
        System.out.println("*".repeat(LONG_TITULO/2));
        System.out.printf("%s decide que acción quieres realizar.\n", nombreJugador);
        System.out.printf("Tienes estas tarjetas: %s\n", descripcionTarjetas);
        System.out.println("1. Mostrar mis territorios");
        System.out.println("2. Mostrar los territorios del mundo entero");
        System.out.println("3. Mover ejércitos de un territorio a otro vecino propio");
        System.out.println("4. Cambiar tarjetas por ejércitos");
        System.out.println("5. Pasar turno al siguiente jugador");
        System.out.println("6. Mostrar mi misión secreta");
        System.out.println("0. Finalizar ");
        System.out.println("*".repeat(LONG_TITULO/2));
        System.out.print("Opción: ");
        while(opc<0 || opc>6) {
            try {
                opc = Integer.parseInt(teclado.nextLine().trim());
            }catch (Exception e) { }
            System.out.println();
        }
        return opc;
    }

    public static void mostrarTerritorios(String idJugador) {
        System.out.println(Mundo.listadoConFronteras(idJugador));
    }


    public static void mostrarMisionSecreta(String nombreJugador, String descripcion) {
        System.out.printf("MISIÓN SECRETA DE %s: %s\n", nombreJugador.toUpperCase(), descripcion);
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
        int ejercitosColocados = Mundo.ejercitosDeJugador(idJugador);
        System.out.println("Ejercitos colocados.");//, pendientes de colocar " + (limiteEjercitos - ejercitosColocados));
        System.out.println();
    }

    public static void mostrarEjercitosPendientesDeColocarJugador(String idJugador, int limiteEjercitos) {
        //System.out.println();
        int ejercitosColocados = Mundo.ejercitosDeJugador(idJugador);
        System.out.println("Ejercitos colocados, pendientes de colocar " + (limiteEjercitos - ejercitosColocados));
    }

    public static boolean comprobarEjercitosJugador(String idJugador, int limiteEjercitos) {
        int ejercitosColocados = Mundo.ejercitosDeJugador(idJugador);
        int ejercitosPendientes = limiteEjercitos - ejercitosColocados;
        if (ejercitosPendientes < 0) {
            System.out.printf("Se han colocado %d ejército(s) de más.", Math.abs(ejercitosPendientes));
        } else if (ejercitosPendientes > 0) {
            System.out.printf("Falta %d ejercito(s) por colocar.", ejercitosPendientes);
        } else {
            System.out.println("Los ejércitos están colocados.");
        }
        System.out.println();
        return ejercitosPendientes == 0;
    }

    public static void mostrarJugadorSinEjercitosEliminado(String nombreJugador) {
        System.out.printf("%s se ha quedado sin ejércitos, no puede seguir jugando.\n",
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
        if (!Mundo.estaTerritorio(nombreTerritorioAtacante)) {
            System.out.println("El nombre de ese territorio no existe, comprueba la ortografía.");
            System.out.println();
            return null;
        }
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
        if (!Mundo.estaTerritorio(nombreTerritorioAtacado)) {
            System.out.println("El nombre de ese territorio no existe, comprueba la ortografía.");
            System.out.println();
            return null;
        }
        if (!Mundo.sonVecinos(nombreTerritorioAtacante, nombreTerritorioAtacado, false)) {
            System.out.printf("No se reconoce %s como vecino de otro jugador de %s.\n", nombreTerritorioAtacado, nombreTerritorioAtacante);
            System.out.println();
            return null;
        }
        return nombreTerritorioAtacado;
    }

    public static void mostrarResultadoTirada(String nombreJugadorAtacante, String tiradaAtacante, int perdidasAtacante,
                                              String nombreTerritorioAtacante, int ejercitosTerritorioAtacante,
                                              String nombreJugadorAtacado, String tiradaAtacado, int perdidasAtacado,
                                              String nombreTerritorioAtacado, int ejercitosTerritorioAtacado) {
        System.out.println("-".repeat(LONG_TITULO));
        Gui.mostrarPerdidasTirada(nombreJugadorAtacante, tiradaAtacante, perdidasAtacante, nombreTerritorioAtacante, ejercitosTerritorioAtacante);
        Gui.mostrarPerdidasTirada(nombreJugadorAtacado, tiradaAtacado, perdidasAtacado, nombreTerritorioAtacado, ejercitosTerritorioAtacado);
        System.out.println("-".repeat(LONG_TITULO));
    }

    private static void mostrarPerdidasTirada(String nombreJugador, String tirada, int perdidas, String nombreTerritorioAtacante, int ejercitosTerritorioAtacante) {
        System.out.printf("%-10s %-18s --> pierde %d ejercito(s) de %s, quedan %d\n",
                nombreJugador, tirada, perdidas, nombreTerritorioAtacante, ejercitosTerritorioAtacante);
    }

    private static void mostrarResultadoTerritorioEjercitos(String mensaje, String nombreJugador, String nombreTerritorio, int ejercitos) {
        System.out.printf(mensaje, nombreJugador, nombreTerritorio, ejercitos);
    }

    public static void mostrarResultadoAtaque(String nombreJugadorGanador, String nombreTerritorioGanado, int ejercitosPerdidosGanador,
                                              String nombreJugadorPerdedor, String nombreTerritorioPerdido, int ejercitosPerdidosPerdedor) {
        System.out.println("#".repeat(LONG_TITULO));
        mostrarGanaJugadorTerritorioPierdeEjercitos(nombreJugadorGanador, nombreTerritorioGanado, ejercitosPerdidosGanador);
        mostrarNoGanaJugadorPierdeTerritorioEjercitos(nombreJugadorPerdedor, nombreTerritorioPerdido, ejercitosPerdidosPerdedor);
        System.out.println("#".repeat(LONG_TITULO));
    }

    private static void mostrarNoGanaJugadorPierdeTerritorioEjercitos(String nombreJugador, String nombreTerritorio, int ejercitos) {
        mostrarResultadoTerritorioEjercitos("%s no ha conseguido ganar, ha perdido el territorio de %s y %d ejercito(s)\n",
                nombreJugador, nombreTerritorio, ejercitos);
    }

    private static void mostrarGanaJugadorTerritorioPierdeEjercitos(String nombreJugador, String nombreTerritorio, int ejercitos) {
        mostrarResultadoTerritorioEjercitos("%s ha conseguido ganar %s perdiendo %d ejercito(s)\n",
                nombreJugador, nombreTerritorio, ejercitos);
    }

    public static void mostrarDescripcionTerritorio(String nombreTerritorio) {
        System.out.println("+".repeat(LONG_TITULO));
        System.out.println(Mundo.descripcionTerritorioConFronteras(nombreTerritorio));
        System.out.println("+".repeat(LONG_TITULO));
    }

    public static void mostrarDescripcionesTerritorios(String nombreTerritorio1, String nombreTerritorio2) {
        System.out.println("~".repeat(LONG_TITULO));
        System.out.println(Mundo.descripcionTerritorioConFronteras(nombreTerritorio1));
        System.out.println(Mundo.descripcionTerritorioConFronteras(nombreTerritorio2));
        System.out.println("~".repeat(LONG_TITULO));
    }

    public static String obtenerNombreTerritorioOrigen(String idJugador) {
        System.out.printf("%-44s", "Desde qué territorio mueves ejércitos: ");
        String nombreTerritorioOrigen = teclado.nextLine().trim();
        if (!Mundo.estaTerritorio(nombreTerritorioOrigen)) {
            System.out.println("El nombre de ese territorio no existe, comprueba la ortografía.");
            System.out.println();
            return null;
        }
        if (!Mundo.esTerritorioDelJugador(nombreTerritorioOrigen, idJugador)) {
            System.out.println("No se reconoce ese territorio como propio.");
            System.out.println();
            return null;
        }
        if (Mundo.ejercitosDeTerritorio(nombreTerritorioOrigen) < 2) {
            System.out.println("No se puede desplazar ejércitos si no hay más de 1.");
            System.out.println();
            return null;
        }
        return nombreTerritorioOrigen;
    }

    public static String obtenerNombreTerritorioDestino(String nombreTerritorioOrigen) {
        System.out.printf("%-44s", "A qué territorio vas a desplazar ejércitos: ");
        String nombreTerritorioDestino = teclado.nextLine().trim();
        if (!Mundo.estaTerritorio(nombreTerritorioDestino)) {
            System.out.println("El nombre de ese territorio no existe, comprueba la ortografía.");
            System.out.println();
            return null;
        }
        if (!Mundo.sonVecinos(nombreTerritorioOrigen, nombreTerritorioDestino, true)) {
            System.out.printf("No se reconoce %s como vecino propio de %s.\n", nombreTerritorioDestino, nombreTerritorioOrigen);
            System.out.println();
            return null;
        }
        return nombreTerritorioDestino;
    }

    public static int obtenerEjercitosMovimiento(String nombreTerritorioOrigen, int ejercitosOrigen, String nombreTerritorioDestino, int ejercitosDestino) {
        int n;
        while (true){
            System.out.printf(  "Cantidad de ejercitos a desplazar desde %s (%d) a %s (%d): ",
                    nombreTerritorioOrigen, ejercitosOrigen, nombreTerritorioDestino, ejercitosDestino);
            try {
                n = Integer.parseInt(teclado.nextLine().trim());
                if (n < 0 || n >= ejercitosOrigen) {
                    System.out.println("No se puede desplazar esa cantidad de ejércitos.");
                }else{
                    break;
                }
            }catch (Exception e) {
            }finally {
                System.out.println();
            }
        }
        return n;
    }

    public static void mostrarResultadoMovimiento(String nombreTerritorioOrigen, int ejercitosOrigen, String nombreTerritorioDestino, int ejercitosDestino) {
        System.out.println("~".repeat(LONG_TITULO));
        System.out.printf("%s (%d), %s (%d)\n", nombreTerritorioOrigen, ejercitosOrigen, nombreTerritorioDestino, ejercitosDestino);
        System.out.println("~".repeat(LONG_TITULO));
    }

    public static void mostrarNoSePuedeCambiarTarjetas() {
        System.out.println("No se pueden cambiar tarjetas por ejércitos.");
        System.out.println();
    }

    public static void mostrarSeTieneQueCambiarTarjetas(String descripcionTarjetas) {
        System.out.printf("Tarjetas: %s\n", descripcionTarjetas);
        System.out.printf("Se tiene que cambiar tarjetas, no puedes tener más de %d.\n", MAX_TARJETAS - 1);
    }

    public static int menuColocarEjercitos(int numEjercitos) {
        int opc = -1;
        System.out.println("*".repeat(LONG_TITULO/2));
        System.out.printf("Debes colocar %d ejércitos.\n", numEjercitos);
        System.out.println("1. Mostrar mis territorios");
        System.out.println("2. Mostrar los territorios del mundo entero");
        System.out.println("3. Colocar un número de ejércitos en uno de mis territorios");
        System.out.println("*".repeat(LONG_TITULO/2));
        System.out.print("Opción: ");
        while(opc<1 || opc>3) {
            try {
                opc = Integer.parseInt(teclado.nextLine().trim());
            }catch (Exception e) { }
            System.out.println();
        }
        return opc;
    }

    public static String obtenerNombreTerritorio(String idJugador) {
        while (true) {
            System.out.printf("Nombre del territorio al que agregar ejercitos: ");
            String nombreTerritorio = teclado.nextLine().trim();
            if (!Mundo.esTerritorioDelJugador(nombreTerritorio,idJugador)) {
                System.out.println("No se reconoce ese territorio como propio.");
            }else{
                return nombreTerritorio;
            }
        }
    }

    public static int obtenerEjercitosAgregar(int limiteEjercitos) {
        int n;
        while (true) {
            System.out.printf("Cantidad de ejercitos a agregar (hasta %d): ", limiteEjercitos);
            try {
                n = Integer.parseInt(teclado.nextLine().trim());
                if (n < 0 || n > limiteEjercitos) {
                    System.out.println("No se puede agregar esa cantidad de ejércitos.");
                    continue;
                }
                break;
            }catch (Exception e) {
            }finally {
                System.out.println();
            }
        }
        return n;
    }


    public static void mostrarTerritorios(List<String> listaTerritorios) {
        StringBuilder sb = new StringBuilder();
        sb.append("Territorios (").append(listaTerritorios.size()).append("): ");
        for (String nombre : listaTerritorios) {
            sb.append(nombre).append(", ");
        }
        if (listaTerritorios.size() > 0) {
            sb.delete(sb.length()-2, sb.length());
        }
        System.out.println(sb.toString());
    }

}
