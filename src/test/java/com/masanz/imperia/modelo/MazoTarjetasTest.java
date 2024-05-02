package com.masanz.imperia.modelo;

import org.junit.jupiter.api.Test;

import static com.masanz.imperia.consts.Ctes.*;
import static org.junit.jupiter.api.Assertions.*;

class MazoTarjetasTest {

    @Test
    void agregar44Tarjetas1() {
        MazoTarjetas mazoTarjetas = new MazoTarjetas();
        mazoTarjetas.agregar44Tarjetas();
        assertEquals(44, mazoTarjetas.tamano());
    }

    @Test
    void agregar44Tarjetas2() {
        MazoTarjetas mazoTarjetas = new MazoTarjetas();
        mazoTarjetas.agregar44Tarjetas();
        mazoTarjetas.sacarTarjeta(T_COMODIN);
        mazoTarjetas.sacarTarjeta(T_COMODIN);
        mazoTarjetas.sacarTarjeta(T_COMODIN);
        assertEquals(42, mazoTarjetas.tamano());
    }

    @Test
    void agregar44Tarjetas3() {
        MazoTarjetas mazoTarjetas = new MazoTarjetas();
        mazoTarjetas.agregar44Tarjetas();
        mazoTarjetas.sacarTarjeta(T_INFANTERIA);
        assertEquals(43, mazoTarjetas.tamano());
    }

    @Test
    void agregar44Tarjetas4() {
        MazoTarjetas mazoTarjetas = new MazoTarjetas();
        mazoTarjetas.agregar44Tarjetas();
        mazoTarjetas.sacarTarjeta(T_CABALLERIA);
        assertEquals(43, mazoTarjetas.tamano());
    }

    @Test
    void agregar44Tarjetas5() {
        MazoTarjetas mazoTarjetas = new MazoTarjetas();
        mazoTarjetas.agregar44Tarjetas();
        mazoTarjetas.sacarTarjeta(T_ARTILLERIA);
        assertEquals(43, mazoTarjetas.tamano());
    }

    @Test
    void barajar1() {
        MazoTarjetas mazoTarjetas = new MazoTarjetas();
        mazoTarjetas.agregar44Tarjetas();
        String antes = mazoTarjetas.toString();
        mazoTarjetas.barajar();
        String despues = mazoTarjetas.toString();
        assertNotEquals(antes, despues);
    }

    @Test
    void barajar2() {
        MazoTarjetas mazoTarjetas = new MazoTarjetas();
        mazoTarjetas.agregar44Tarjetas();
        String antes = mazoTarjetas.getTiposCantidad();
        mazoTarjetas.barajar();
        String despues = mazoTarjetas.getTiposCantidad();
        assertEquals(antes, despues);
    }

    @Test
    void sacarTarjeta() {
        MazoTarjetas mazoTarjetas = new MazoTarjetas();
        assertEquals(0, mazoTarjetas.tamano());
        mazoTarjetas.meterTarjeta(new Tarjeta(T_INFANTERIA));
        mazoTarjetas.meterTarjeta(new Tarjeta(T_INFANTERIA));
        assertEquals(2, mazoTarjetas.tamano());
        mazoTarjetas.sacarTarjeta(T_INFANTERIA);
        assertEquals(1, mazoTarjetas.tamano());
        mazoTarjetas.sacarTarjeta(T_ARTILLERIA);
        assertEquals(1, mazoTarjetas.tamano());
        mazoTarjetas.sacarTarjeta(T_INFANTERIA);
        assertEquals(0, mazoTarjetas.tamano());
        mazoTarjetas.sacarTarjeta(T_INFANTERIA);
        assertEquals(0, mazoTarjetas.tamano());
    }

    @Test
    void sacarTarjetaNo() {
        MazoTarjetas mazoTarjetas = new MazoTarjetas();
        assertEquals(0, mazoTarjetas.tamano());
        mazoTarjetas.meterTarjeta(new Tarjeta(T_INFANTERIA));
        mazoTarjetas.meterTarjeta(new Tarjeta(T_INFANTERIA));
        mazoTarjetas.meterTarjeta(new Tarjeta(T_ARTILLERIA));
        assertEquals(3, mazoTarjetas.tamano());
        mazoTarjetas.sacarTarjetaNo(T_ARTILLERIA);
        assertEquals(2, mazoTarjetas.tamano());
        mazoTarjetas.sacarTarjetaNo(T_INFANTERIA);
        assertEquals(1, mazoTarjetas.tamano());
        mazoTarjetas.sacarTarjetaNo(T_INFANTERIA);
        assertEquals(1, mazoTarjetas.tamano());
        mazoTarjetas.sacarTarjetaNo(T_ARTILLERIA);
        assertEquals(0, mazoTarjetas.tamano());
        mazoTarjetas.sacarTarjetaNo(T_INFANTERIA);
        assertEquals(0, mazoTarjetas.tamano());
    }

    @Test
    void getTiposCantidad1() {
        MazoTarjetas mazoTarjetas = new MazoTarjetas();
        mazoTarjetas.agregar44Tarjetas();
        String resultado = mazoTarjetas.getTiposCantidad();
        String esperado = "ARTILLERÍA(14) CABALLERÍA(14) COMODÍN(2) INFANTERÍA(14)";
        assertEquals(esperado, resultado.trim());
    }

    @Test
    void getTiposCantidad2() {
        MazoTarjetas mazoTarjetas = new MazoTarjetas();
        mazoTarjetas.meterTarjeta(new Tarjeta(T_INFANTERIA));
        mazoTarjetas.meterTarjeta(new Tarjeta(T_CABALLERIA));
        mazoTarjetas.meterTarjeta(new Tarjeta(T_ARTILLERIA));
        mazoTarjetas.meterTarjeta(new Tarjeta(T_ARTILLERIA));
        mazoTarjetas.meterTarjeta(new Tarjeta(T_COMODIN));
        String resultado = mazoTarjetas.getTiposCantidad();
        String esperado = "ARTILLERÍA(2) CABALLERÍA(1) COMODÍN(1) INFANTERÍA(1)";
        assertEquals(esperado, resultado.trim());
    }

    @Test
    void getTiposCantidad3() {
        MazoTarjetas mazoTarjetas = new MazoTarjetas();
        mazoTarjetas.meterTarjeta(new Tarjeta(T_INFANTERIA));
        mazoTarjetas.meterTarjeta(new Tarjeta(T_CABALLERIA));
        mazoTarjetas.meterTarjeta(new Tarjeta(T_INFANTERIA));
        mazoTarjetas.meterTarjeta(new Tarjeta(T_INFANTERIA));
        mazoTarjetas.meterTarjeta(new Tarjeta(T_CABALLERIA));
        String resultado = mazoTarjetas.getTiposCantidad();
        String esperado = "CABALLERÍA(2) INFANTERÍA(3)";
        assertEquals(esperado, resultado);
    }

}