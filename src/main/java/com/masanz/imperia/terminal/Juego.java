package com.masanz.imperia.terminal;

import static com.masanz.imperia.consts.Ctes.*;
import com.masanz.imperia.modelo.*;

import java.util.*;

/**
 * Clase que representa un juego de risk en modo consola.
 * @author TODO: 30 AQUÍ_TU_NOMBRE
 */
public class Juego {
    private MazoTarjetas mazoTarjetas;

    protected List<Jugador> jugadores;

    protected int turno;

    public Juego() {

        Gui.mostrarPresentacion();

        mazoTarjetas = new MazoTarjetas();
        mazoTarjetas.agregar44Tarjetas();
        mazoTarjetas.barajar();
        jugadores = new ArrayList<>();
        Mundo.loadWorld();
        turno = 0;
    }

    // region configuración

    public void setJugadores(List<Jugador> jugadores) {
        this.jugadores = jugadores;
    }

    public void crearJugadores() {
        int numJugadores = 0;
        String nombre, identificador;

        numJugadores = Gui.leerNumeroJugadores();

        for (int i = 1; i <= numJugadores; i++) {
            nombre = Gui.leerNombreJugador(i);
            identificador = Gui.leerIdentificadorJugador(i);
            jugadores.add(new Jugador(identificador, nombre));
        }
    }

    public void repartirTerritorios() {
        Random rnd = new Random();
        List<String> listaNombresTerritorios = Mundo.listaNombresTerritorios();
        turno = (int) (Math.random()*jugadores.size());
        while(!listaNombresTerritorios.isEmpty()) {
            int idx = rnd.nextInt(listaNombresTerritorios.size());
            String nombreTerritorio = listaNombresTerritorios.get(idx);
            listaNombresTerritorios.remove(idx);
            Territorio territorio = Mundo.getTerritorio(nombreTerritorio);
            Jugador jugador = jugadores.get(turno);
            territorio.setJugador(jugador);
            turno = (turno + 1) % jugadores.size();
        }

        Gui.mostrarEmpiezaJugador(turno+1, jugadores.get(turno).getNombre());

    }

    public void colocarEjercitos() {
        colocarUnEjercitoEnCadaTerritorio();
        for (int i = 0; i < jugadores.size(); i++) {
            Jugador jugador = jugadores.get(turno);
            colocarEjercitos(jugador);
            turno = (turno + 1) % jugadores.size();
        }
    }

    public void colocarUnEjercitoEnCadaTerritorio() {
        List<Territorio> listaTerritorios = Mundo.getListaTerritorios();
        for (Territorio territorio : listaTerritorios) {
            territorio.setEjercitos(1);
        }
    }

    public void colocarEjercitos(Jugador jugador) {
        while(true) {
            int opc = Gui.menuColocarEjercitos(jugador.getNombre(), ejercitosInicialesPorJugador() - Mundo.ejercitosDeJugador(jugador.getId()));
            switch (opc){
                case 1: Gui.mostrarTerritorios(jugador.getId()); break;
                case 2: Gui.mostrarTerritoriosMundo(); break;
                case 3: Gui.modificarEnTerritorioEjercitosJugador(jugador.getId(), ejercitosInicialesPorJugador()); break;
                case 4: repartoEjercitos(jugador.getId()); break;
                case 0: if (Gui.comprobarEjercitosJugador(jugador.getId(), ejercitosInicialesPorJugador())) {return;}
            }
        }
    }

    public int ejercitosInicialesPorJugador(){
        return switch (jugadores.size()) {
            case 2 -> 40;
            case 3 -> 35;
            case 4 -> 30;
            case 5 -> 25;
            default -> 20;
        };
    }

    public void repartoEjercitos(String idJugador) {
        int totalEjercitos = ejercitosInicialesPorJugador();
        int ejercitosColocados = Mundo.ejercitosDeJugador(idJugador);
        int ejercitosPendientes = totalEjercitos - ejercitosColocados;
        repartoEjercitos(idJugador, ejercitosPendientes);
    }

    /**
     * Método que reparte un número de ejércitos al hazar entre los territorios de un jugador uno a uno.
     * Devuelve una lista con los nombres de los territorios a los que se han asignado ejércitos.
     * Si se asigna más de un ejército a un territorio la lista solo tendrá el nombre del territorio una vez.
     * La lista de territorios agraciados se devuelve ordenada alfabéticamente.
     * @param idJugador identificador del jugador
     * @param numeroEjercitos número de ejercitos a repartir
     * @return lista con los nombres de los territorios agraciados ordenados alfabéticamente y sin repeticiones
     */
    public List<String> repartoEjercitos(String idJugador, int numeroEjercitos){
        List<Territorio> listaTerritorios = Mundo.getListaTerritoriosDelJugador(idJugador);
        // TODO: 31 Crear una colección de nombres de territorios para evitar repeticiones ordenadas alfabéticamente
        // elige el tipo de colección que consideres más adecuado para que sea ordenada sin repeticiones e instánciala
        // itera un número de veces igual al número de ejércitos a repartir para que en cada iteración
        // se obtenga un territorio de lista de territorios del jugador al hazar y se le sume un ejercito
        // el nombre del territorio se añade a la colección de nombres de territorios
        // Por ejemplo, si se dispone de los territorios: Alaska, Alberta y Ontario y hay que colocar tres ejercitos
        // se podría obtener una asignación a Alberta, Alberta y Alaska y entonces se devolvería una lista con
        // los nombres de los territorios ordenados Alaska y Alberta
        Set<String> cjtoNombresTerritorios = new TreeSet<>();
        for (int i = 0; i < numeroEjercitos; i++) {
            int idx = (int) (Math.random()*listaTerritorios.size());
            Territorio territorio = listaTerritorios.get(idx);
            territorio.sumarEjercitos(1);
            cjtoNombresTerritorios.add(territorio.getNombre());
        }
        // TODO: 32 Devolver una lista de nombres de territorios creada a partir del conjunto de nombres de territorios
        // utiliza la colección anterior para crear una lista de nombres de territorios y devuélvela
        // se puede hacer en una sóla línea pasando como parámetro la colección al método constructor de la lista
        return new ArrayList<>(cjtoNombresTerritorios);
    }

    // endregion

    public void jugar() {
        while (true) {
            Jugador jugador = jugadores.get(turno);
            if (!jugarAtaque(jugador)) { break; }
            if (Mundo.ejercitosDeJugador(jugador.getId())>0) {
                if (!jugarDefensa(jugador)) { break; }
            }
            if (!comprobarJugadores()) { break; }
            turno = (turno + 1) % jugadores.size();
        }
        if (jugadores.size() == 1) {
            Gui.mostrarGanadorJuego(jugadores.get(0).getNombre());
        }else {
            Gui.mostrarFinJuego();
        }
    }

    // region comprobar jugadores
    public boolean comprobarJugadores() {
        int n = jugadores.size();
        for (int i = n-1; i >= 0; i--) {
            Jugador jugador = jugadores.get(i);
            int ejercitos = Mundo.ejercitosDeJugador(jugador.getId());
            if (ejercitos == 0) {
                Gui.mostrarJugadorSinEjercitosEliminado(jugador.getNombre());
                jugadores.remove(i);
                // decrementar el turno para que al incrementarlo
                // no salte al jugador siguiente
                if (turno <= i) {
                    turno--;
                }
                n--;
            }
        }
        // Si queda un jugador habrá terminado el juego
        return n > 1;
    }
    // endregion

    // region atacar
    public boolean jugarAtaque(Jugador jugador) {
        while (true) {
            int opc = Gui.menuAtaque(jugador.getNombre());
            switch (opc) {
                case 1: Gui.mostrarTerritorios(jugador.getId()); break;
                case 2: Gui.mostrarTerritoriosMundo(); break;
                case 3: if (atacar(jugador)) { return true; } break;
                case 0: if (Gui.confirmarFin()) { return false;}
            }
        }
    }

    public boolean atacar(Jugador jugador) {
        String nombreTerritorioAtacante = Gui.obtenerNombreTerritorioAtacante(jugador.getId());
        if (nombreTerritorioAtacante == null) { return false; }
        Territorio territorioAtacante = Mundo.getTerritorio(nombreTerritorioAtacante);
        if (territorioAtacante == null) { return false; }

        String nombreTerritorioAtacado = Gui.obtenerNombreTerritorioAtacado(nombreTerritorioAtacante);
        if (nombreTerritorioAtacado == null) { return false; }
        Territorio territorioAtacado = Mundo.getTerritorio(nombreTerritorioAtacado);
        if (territorioAtacado == null) { return false; }

        DosValores dosValores = atacar(territorioAtacante, territorioAtacado);
        int perdidasAtacanteTotal = dosValores.uno();
        int perdidasAtacadoTotal = dosValores.dos();

        resultadoAtaque(territorioAtacante, territorioAtacado, perdidasAtacanteTotal, perdidasAtacadoTotal);

        return true;
    }


    protected DosValores atacar(Territorio territorioAtacante, Territorio territorioAtacado) {
        Tirada tiradaAtacante = new Tirada();
        Tirada tiradaAtacado = new Tirada();

        int perdidasAtacanteTotal = 0;
        int perdidasAtacadoTotal = 0;

        while (territorioAtacante.getEjercitos() != 0 && territorioAtacado.getEjercitos() != 0){
            int numDadosAtacante = Math.min(NUM_DADOS_MAX_ATACANTE, territorioAtacante.getEjercitos());
            tiradaAtacante.tirarDados(numDadosAtacante);
            int numDadosAtacado = Math.min(NUM_DADOS_MAX_ATACADO, territorioAtacado.getEjercitos());
            tiradaAtacado.tirarDados(numDadosAtacado);

            int perdidasAtacante = tiradaAtacante.perdidas(tiradaAtacado);
            int perdidasAtacado = Math.min(numDadosAtacante, numDadosAtacado) - perdidasAtacante;

            territorioAtacante.restarEjercitos(perdidasAtacante);
            territorioAtacado.restarEjercitos(perdidasAtacado);

            Gui.mostrarResultadoTirada( territorioAtacante.getJugador().getNombre(), tiradaAtacante.toString(),
                    perdidasAtacante, territorioAtacante.getNombre(), territorioAtacante.getEjercitos(),
                    territorioAtacado.getJugador().getNombre(), tiradaAtacado.toString(),
                    perdidasAtacado, territorioAtacado.getNombre(), territorioAtacado.getEjercitos());

            perdidasAtacanteTotal += perdidasAtacante;
            perdidasAtacadoTotal += perdidasAtacado;
        }

        return new DosValores(perdidasAtacanteTotal, perdidasAtacadoTotal);
    }

    protected void resultadoAtaque(Territorio territorioAtacante, Territorio territorioAtacado, int perdidasAtacanteTotal, int perdidasAtacadoTotal) {
        Jugador jugador = null;
        if (territorioAtacante.getEjercitos() == 0) {
            jugador = territorioAtacado.getJugador();
            Gui.mostrarResultadoAtaque( territorioAtacado.getJugador().getNombre(), territorioAtacante.getNombre(), perdidasAtacadoTotal,
                    territorioAtacante.getJugador().getNombre(), territorioAtacante.getNombre(), perdidasAtacanteTotal);
            territorioAtacante.setJugador(territorioAtacado.getJugador());
            if (territorioAtacado.getEjercitos() == 1) {
                territorioAtacante.setEjercitos(1);
            }else {
                int ejercitosRepartidos = territorioAtacado.getEjercitos() / 2;
                boolean unoMas = territorioAtacado.getEjercitos() % 2 == 1;
                territorioAtacante.setEjercitos(ejercitosRepartidos);
                territorioAtacado.setEjercitos(ejercitosRepartidos);
                if (unoMas) { territorioAtacado.sumarEjercitos(1); }
            }
        }
        if (territorioAtacado.getEjercitos() == 0) {
            jugador = territorioAtacante.getJugador();
            Gui.mostrarResultadoAtaque( territorioAtacante.getJugador().getNombre(), territorioAtacado.getNombre(), perdidasAtacanteTotal,
                    territorioAtacado.getJugador().getNombre(), territorioAtacado.getNombre(), perdidasAtacadoTotal);
            territorioAtacado.setJugador(territorioAtacante.getJugador());
            if (territorioAtacante.getEjercitos() == 1) {
                territorioAtacado.setEjercitos(1);
            }else {
                int ejercitosRepartidos = territorioAtacante.getEjercitos() / 2;
                boolean unoMas = territorioAtacante.getEjercitos() % 2 == 1;
                territorioAtacante.setEjercitos(ejercitosRepartidos);
                territorioAtacado.setEjercitos(ejercitosRepartidos);
                if (unoMas) { territorioAtacante.sumarEjercitos(1); }
            }
            // Solo si gana obtiene una tarjeta, el atacado aunque gane no obtiene tarjeta
            jugador.meterTarjeta(mazoTarjetas.sacarTarjeta());
            if (mazoTarjetas.tamano()==0) {
                mazoTarjetas.agregar44Tarjetas();
                mazoTarjetas.barajar();
            }
        }
        Gui.mostrarDescripcionesTerritorios(territorioAtacante.getNombre(), territorioAtacado.getNombre());
    }
    // endregion

    // region defender
    public boolean jugarDefensa(Jugador jugador) {
        while (true) {
            int opc = Gui.menuDefensa(jugador.getNombre(), jugador.getDescripcionTarjetas());
            switch (opc) {
                case 1: Gui.mostrarTerritorios(jugador.getId()); break;
                case 2: Gui.mostrarTerritoriosMundo(); break;
                case 3: mover(jugador); break;
                case 4: cambiar(jugador);  break;
                case 5: if (!haceFaltaCambiar(jugador)) { return true; } break;
                case 0: if (Gui.confirmarFin()) { return false;}
            }
        }
    }

    public boolean mover(Jugador jugador) {
        String nombreTerritorioOrigen = Gui.obtenerNombreTerritorioOrigen(jugador.getId());
        if (nombreTerritorioOrigen == null) { return false; }
        Territorio territorioOrigen = Mundo.getTerritorio(nombreTerritorioOrigen);
        if (territorioOrigen == null) { return false; }

        String nombreTerritorioDestino = Gui.obtenerNombreTerritorioDestino(nombreTerritorioOrigen);
        if (nombreTerritorioDestino == null) { return false; }
        Territorio territorioDestino = Mundo.getTerritorio(nombreTerritorioDestino);
        if (territorioDestino == null) { return false; }

        int n = Gui.obtenerEjercitosMovimiento(nombreTerritorioOrigen, territorioOrigen.getEjercitos(), nombreTerritorioDestino, territorioDestino.getEjercitos());

        territorioOrigen.restarEjercitos(n);
        territorioDestino.sumarEjercitos(n);

        Gui.mostrarResultadoMovimiento(nombreTerritorioOrigen, territorioOrigen.getEjercitos(), nombreTerritorioDestino, territorioDestino.getEjercitos());

        return true;
    }


    public boolean cambiar(Jugador jugador) {
        int n = jugador.cambiarTarjetas();
        if (n == 0) {
            Gui.mostrarNoSePuedeCambiarTarjetas();
            return false;
        }
        while (n > 0) {
            n = colocarEjercitos(jugador, n);
        }
        return true;
    }

    public int colocarEjercitos(Jugador jugador, int ejercitos) {
        int n = ejercitos;
        int opc = Gui.menuColocarEjercitos(ejercitos);
        switch (opc){
            case 1: Gui.mostrarTerritorios(jugador.getId()); break;
            case 2: Gui.mostrarTerritoriosMundo(); break;
            case 3: n = colocarEjercitosTerritorio(jugador, ejercitos); break;
        }
        return n;
    }

    private int colocarEjercitosTerritorio(Jugador jugador, int limiteEjercitos) {
        String nombreTerritorio = Gui.obtenerNombreTerritorio(jugador.getId());
        int numeroEjercitos = Gui.obtenerEjercitosAgregar(limiteEjercitos);
        Territorio territorio = Mundo.getTerritorio(nombreTerritorio);
        territorio.sumarEjercitos(numeroEjercitos);
        return limiteEjercitos - numeroEjercitos;
    }

    public boolean haceFaltaCambiar(Jugador jugador) {
        int n = jugador.cantidadTarjetas();
        if (n >= MAX_TARJETAS) {
            Gui.mostrarSeTieneQueCambiarTarjetas(jugador.getDescripcionTarjetas());
            return true;
        }
        return false;
    }
    // endregion
}
