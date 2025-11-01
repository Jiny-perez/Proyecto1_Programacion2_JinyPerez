package vampirewargame;

import javax.swing.*;

/**
 *
 * @author marye
 */
class Tablero {

    private Piezas[][] posicion = new Piezas[6][6];
    private ImageIcon[][] pieza = new ImageIcon[6][6];

    public void PosicionPiezas() {
        ImageIcon imagenHombreLobo = new ImageIcon(getClass().getResource("/Imagenes/HombreLobo.png"));
        ImageIcon imagenVampiro = new ImageIcon(getClass().getResource("/Imagenes/Vampiro.png"));
        ImageIcon imagenMuerte = new ImageIcon(getClass().getResource("/Imagenes/Muerte.png"));

        pieza[0][0] = imagenHombreLobo;
        pieza[0][1] = imagenVampiro;
        pieza[0][2] = imagenMuerte;
        pieza[0][3] = imagenMuerte;
        pieza[0][4] = imagenVampiro;
        pieza[0][5] = imagenHombreLobo;

        pieza[5][0] = imagenHombreLobo;
        pieza[5][1] = imagenVampiro;
        pieza[5][2] = imagenMuerte;
        pieza[5][3] = imagenMuerte;
        pieza[5][4] = imagenVampiro;
        pieza[5][5] = imagenHombreLobo;
    }

    public ImageIcon getPiezas(int fila, int columna) {
        return pieza[fila][columna];
    }

    public Piezas getPosicion(int fila, int columna) {
        return posicion[fila][columna];
    }

    public void setPieza(int fila, int columna, Piezas pieza) {
        posicion[fila][columna] = pieza;
    }

    public boolean casillaVacia(int fila, int columna) {
        if (posicion[fila][columna] == null) {
            return true;
        }
        return false;
    }

    public int[] buscarPieza(Piezas pieza) {
        for (int fila = 0; fila < 6; fila++) {
            for (int columna = 0; columna < 6; columna++) {
                if (posicion[fila][columna] == pieza) {
                    return new int[]{fila, columna};
                }
            }
        }
        return null;
    }

    public Zombie buscarZombie(int fila, int columna) {
        for (int i = fila - 1; i <= fila + 1; i++) {
            for (int j = columna - 1; j <= columna + 1; j++) {
                if (i >= 0 && i < 6 && j >= 0 && j < 6) {
                    Piezas zombie = getPosicion(i, j);
                    if (zombie instanceof Zombie) {
                        return (Zombie) zombie;
                    }
                }
            }
        }
        return null;
    }

}
