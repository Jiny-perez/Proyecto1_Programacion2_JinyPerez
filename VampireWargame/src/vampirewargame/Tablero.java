package vampirewargame;

import javax.swing.*;

/**
 *
 * @author marye
 */
class Tablero {

    private Piezas[][] posicion = new Piezas[6][6];

    public void PosicionPiezas() {
        posicion[0][0] = new HombreLobo();
        posicion[0][1] = new Vampiro();
        posicion[0][2] = new Muerte();
        posicion[0][3] = new Muerte();
        posicion[0][4] = new Vampiro();
        posicion[0][5] = new HombreLobo();

        posicion[5][0] = new HombreLobo();
        posicion[5][1] = new Vampiro();
        posicion[5][2] = new Muerte();
        posicion[5][3] = new Muerte();
        posicion[5][4] = new Vampiro();
        posicion[5][5] = new HombreLobo();
    }

    public ImageIcon getPiezas(int fila, int columna) {
        Piezas p = posicion[fila][columna];
        if (p == null) {
            return null;
        }
        String ruta = "/Imagenes/" + p.getNombre() + ".png";
        return new ImageIcon(getClass().getResource(ruta));
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
