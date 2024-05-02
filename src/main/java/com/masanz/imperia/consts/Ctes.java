package com.masanz.imperia.consts;

import java.nio.file.FileSystems;

public class Ctes {

    public static final String PATH_TERRITORIOS = "data" + FileSystems.getDefault().getSeparator() + "territorios.txt";
    public static final String EXP_TERRITORIO_SPLITTER = "\s*-\s*";

    public static final int LONG_TITULO = 104;
    public static final int MAX_LONG_NOMBRE = 10;
    public static final int MAX_LONG_ID = 3;

    public static final int NUM_DADOS_MAX_ATACANTE = 3;
    public static final int NUM_DADOS_MAX_ATACADO = 2;

    public static final String T_INFANTERIA = "INFANTERÍA";
    public static final String T_CABALLERIA = "CABALLERÍA";
    public static final String T_ARTILLERIA = "ARTILLERÍA";
    public static final String T_COMODIN = "COMODÍN";

    public static final int CAMBIO_INFANTERIA = 1;
    public static final int CAMBIO_CABALLERIA = 1;//2;
    public static final int CAMBIO_ARTILLERIA = 1;//3;
    public static final int CAMBIO_TRESTIPOS = 2;//4;
    public static final int MAX_TARJETAS = 5;

}
