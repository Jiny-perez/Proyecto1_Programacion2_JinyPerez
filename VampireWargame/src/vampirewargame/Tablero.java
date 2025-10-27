package vampirewargame;

import javax.swing.*;

/**
 *
 * @author marye
 */
class Tablero {

    private Piezas[][] tablero = new Piezas[6][6];
    private ImageIcon[][] piezas = new ImageIcon[6][6];

    public void PosicionPiezas() {
        ImageIcon imagenHombreLobo = new ImageIcon(getClass().getResource("/Imagenes/HombreLobo.png"));
        ImageIcon imagenVampiro = new ImageIcon(getClass().getResource("/Imagenes/Vampiro.png"));
        ImageIcon imagenMuerte = new ImageIcon(getClass().getResource("/Imagenes/Muerte.png"));
        
        piezas[0][0] = imagenHombreLobo;
        piezas[0][1] = imagenVampiro;
        piezas[0][2] = imagenMuerte;
        piezas[0][3] = imagenMuerte;
        piezas[0][4] = imagenVampiro;
        piezas[0][5] = imagenHombreLobo;

        piezas[5][0] = imagenHombreLobo;
        piezas[5][1] = imagenVampiro;
        piezas[5][2] = imagenMuerte;
        piezas[5][3] = imagenMuerte;
        piezas[5][4] = imagenVampiro;
        piezas[5][5] = imagenHombreLobo;
    }
    
    public ImageIcon getPiezas(int fila, int col) {
        return piezas[fila][col];
    }
    public Piezas getTablero(int fila, int col) {
        return tablero[fila][col];
    }

    public void setPieza(int fila, int col, Piezas p) {
        tablero[fila][col] = p;
    }

    public boolean casillaVacia(int fila, int col) {
        if (tablero[fila][col] == null) {
            return true;
        }
        return false;
    }
}

