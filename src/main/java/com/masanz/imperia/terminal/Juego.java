package com.masanz.imperia.terminal;

import com.masanz.imperia.consts.Ctes;
import com.masanz.imperia.modelo.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Juego {

    protected List<Jugador> jugadores;

    protected int turno;

    public Juego() {

        Gui.mostrarPresentacion();

        jugadores = new ArrayList<>();
        Mundo.loadWorld();
        turno = 0;
    }

    // region Configuración del juego

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

    private void colocarUnEjercitoEnCadaTerritorio() {
        List<Territorio> listaTerritorios = Mundo.getListaTerritorios();
        for (Territorio territorio : listaTerritorios) {
            territorio.setEjercitos(1);
        }
    }

    public void colocarEjercitos(Jugador jugador) {
        while(true) {
            int opc = Gui.menuColocarEjercitos(jugador.getNombre(), ejercitosInicialesPorJugador());
            switch (opc){
                case 1: Gui.mostrarTerritorios(jugador.getId()); break;
                case 2: Gui.mostrarTerritoriosMundo(); break;
                case 3: Gui.modificarEnTerritorioEjercitosJugador(jugador.getId(), ejercitosInicialesPorJugador()); break;
                case 4: repartoUniformeEjercitos(jugador.getId()); break;
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

    public void repartoUniformeEjercitos(String idJugador) {
        int totalEjercitos = ejercitosInicialesPorJugador();
        int ejercitosColocados = Mundo.ejercitosDe(idJugador);
        int ejercitosPendientes = totalEjercitos - ejercitosColocados;
        List<Territorio> listaTerritorios = Mundo.getListaTerritoriosDelJugador(idJugador);
        int numeroTerritorios = listaTerritorios.size();
        int idx = 0;
        int maxEjercitos = listaTerritorios.get(0).getEjercitos();
        for (int i = 0; i < numeroTerritorios; i++) {
            Territorio territorio = listaTerritorios.get(i);
            if (maxEjercitos <= territorio.getEjercitos()) {
                idx = i;
                maxEjercitos = territorio.getEjercitos();
            }
        }
        idx = (idx + 1) % numeroTerritorios;
        for (int i = 0; i < ejercitosPendientes; i++) {
            Territorio territorio = listaTerritorios.get(idx);
            //System.out.printf("%d %s, ", idx, territorio.getNombre());
            territorio.sumarEjercitos(1);
            idx = (idx + 1) % numeroTerritorios;
        }
        Gui.mostrarEjercitosPendientesDeColocarJugador(idJugador, totalEjercitos);
    }

    // endregion

    // region Desarrollo del juego
    public void jugarJuego() {
        while (true) {
            Jugador jugador = jugadores.get(turno);
            if (!jugar(jugador)) { break; }
            if (!comprobarJugadores()) { break; }
            turno = (turno + 1) % jugadores.size();
        }
        if (jugadores.size() == 1) {
            Gui.mostrarGanadorJuego(jugadores.get(0).getNombre());
        }else {
            Gui.mostrarFinJuego();
        }
    }


    protected boolean comprobarJugadores() {
        int n = jugadores.size();
        for (int i = n-1; i >= 0; i--) {
            Jugador jugador = jugadores.get(i);
            int ejercitos = Mundo.ejercitosDe(jugador.getId());
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

    public boolean jugar(Jugador jugador) {
        while (true) {
            int opc = Gui.menuJuego(jugador.getNombre());
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

        resultado(territorioAtacante, territorioAtacado, perdidasAtacanteTotal, perdidasAtacadoTotal);
        
        return true;
    }


    protected DosValores atacar(Territorio territorioAtacante, Territorio territorioAtacado) {
        Tirada tiradaAtacante = new Tirada();
        Tirada tiradaAtacado = new Tirada();

        int perdidasAtacanteTotal = 0;
        int perdidasAtacadoTotal = 0;

        while (territorioAtacante.getEjercitos() != 0 && territorioAtacado.getEjercitos() != 0){
            int numDadosAtacante = Math.min(Ctes.NUM_DADOS_MAX_ATACANTE, territorioAtacante.getEjercitos());
            tiradaAtacante.tirarDados(numDadosAtacante);
            int numDadosAtacado = Math.min(Ctes.NUM_DADOS_MAX_ATACADO, territorioAtacado.getEjercitos());
            tiradaAtacado.tirarDados(numDadosAtacado);

            int perdidasAtacante = tiradaAtacante.perdidas(tiradaAtacado);
            int perdidasAtacado = Math.min(numDadosAtacante, numDadosAtacado) - perdidasAtacante;

            territorioAtacante.restarEjercitos(perdidasAtacante);
            territorioAtacado.restarEjercitos(perdidasAtacado);

            Gui.mostrarPerdidasTirada(territorioAtacante.getJugador().getNombre(), tiradaAtacante.toString(),
                    perdidasAtacante, territorioAtacante.getNombre(), territorioAtacante.getEjercitos());
            Gui.mostrarPerdidasTirada(territorioAtacado.getJugador().getNombre(), tiradaAtacado.toString(),
                    perdidasAtacado, territorioAtacado.getNombre(), territorioAtacado.getEjercitos());

            perdidasAtacanteTotal += perdidasAtacante;
            perdidasAtacadoTotal += perdidasAtacado;
        }

        return new DosValores(perdidasAtacanteTotal, perdidasAtacadoTotal);
    }
    
    protected void resultado(Territorio territorioAtacante, Territorio territorioAtacado, int perdidasAtacanteTotal, int perdidasAtacadoTotal) {
        if (territorioAtacante.getEjercitos() == 0) {
            Gui.mostrarNoGanaJugadorPierdeTerritorioEjercitos(territorioAtacante.getJugador().getNombre(), territorioAtacante.getNombre(), perdidasAtacanteTotal);
            Gui.mostrarGanaJugadorTerritorioPierdeEjercitos(territorioAtacado.getJugador().getNombre(), territorioAtacante.getNombre(), perdidasAtacadoTotal);
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
            Gui.mostrarGanaJugadorTerritorioPierdeEjercitos(territorioAtacante.getJugador().getNombre(), territorioAtacante.getNombre(), perdidasAtacanteTotal);
            Gui.mostrarNoGanaJugadorPierdeTerritorioEjercitos(territorioAtacado.getJugador().getNombre(), territorioAtacado.getNombre(), perdidasAtacadoTotal);
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
        }
        Gui.mostrarDescripcionesTerritoriosAtacanteAtacado(territorioAtacante.getNombre(), territorioAtacado.getNombre());
    }
    
    // endregion
}
