package vampirewargame;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

/**
 *
 * @author marye
 */
public abstract class Piezas {

    protected String nombre;
    protected int ataque;
    protected int vida;
    protected int escudo;

    public Piezas(String nombre, int ataque, int vida, int escudo) {
        this.nombre = nombre;
        this.ataque = ataque;
        this.vida = vida;
        this.escudo = escudo;
    }

    public String getNombre() {
        return nombre;
    }

    public int getAtaque() {
        return ataque;
    }

    public int getVida() {
        return vida;
    }

    public int getEscudo() {
        return escudo;
    }

    public boolean esAdyacente(int filaDestino, int columnaDestino, int filaActual, int columnaActual) {
        int distFila = Math.abs(filaDestino - filaActual);
        int distColumna = Math.abs(columnaDestino - columnaActual);

        if (distFila > 1 || distColumna > 1 || (distFila == 0 && distColumna == 0)) {
            return false;
        } else {
            return true;
        }
    }

    public void movemiento(Tablero tablero, int filaDestino, int columnaDestino) {
        int[] hayPieza = tablero.buscarPieza(this);
        int filaActual = hayPieza[0];
        int columnaActual = hayPieza[1];

        if (hayPieza == null) {
            JOptionPane.showMessageDialog(null, "Error: No se encontró la pieza en el tablero.");
            return;
        }

        if (filaDestino < 0 || filaDestino >= 6 || columnaDestino < 0 || columnaDestino >= 6) {
            JOptionPane.showMessageDialog(null, "Error: Movimiento invalido.");
            return;
        }

        if (!tablero.casillaVacia(filaDestino, columnaDestino)) {
            JOptionPane.showMessageDialog(null, "Error: La casilla está ocupada.");
            return;
        }

        if (!esAdyacente(filaActual, columnaActual, filaDestino, columnaDestino)) {
            JOptionPane.showMessageDialog(null, "Error: Solo se puede mover a una casilla adyacente.");
            return;
        }

        tablero.setPieza(filaDestino, columnaDestino, this);
        tablero.setPieza(filaActual, columnaActual, null);
    }

    public abstract void ataqueEspecial(Tablero tablero, int fila, int columna);

    public void danioOponente(int danioOponente) {
        if (escudo >= danioOponente) {
            escudo -= danioOponente;
        } else {
            int danioRestante = danioOponente - escudo;
            escudo = 0;
            vida -= danioRestante;
            if (vida < 0) {
                vida = 0;
            }
        }
    }

    public void curacion(int vidaAtacante) {
        vida += vidaAtacante;
    }

    public final boolean vivo() {
        if (vida > 0) {
            return true;
        } else {
            return false;
        }
    }
}
