package vampirewargame;

/**
 *
 * @author marye
 */
class Tablero {

    private final int fila;
    private final int columna;
    private final Piezas[][] casilla;

    public Tablero(int fila, int columna) {
        this.fila = fila;
        this.columna = columna;
        this.casilla = new Piezas[fila][columna];
    }

    public boolean estaDentro(int fila, int columna) {
        return fila >= 0 && fila < 6 && columna >= 0 && columna < 6;
    }

    public boolean estaVacia(int fila, int columna) {
        if (!estaDentro(fila, columna)) {
            return false;
        }
        return casilla[fila][columna] == null;
    }

    public Piezas get(int fila, int columna) {
        if (!estaDentro(fila, columna)) {
            return null;
        }
        return casilla[fila][columna];
    }

    public void colocarPieza(Piezas p, int fila, int columna) {
        if (!estaDentro(fila, columna)) {
            return;
        }
        casilla[fila][columna] = p;
        if (p != null) {
            p.setPosicion(fila, columna);
        }
    }

    public void eliminarPieza(int fila, int columna) {
        if (!estaDentro(fila, columna)) {
            return;
        }
        Piezas p = casilla[fila][columna];
        if (p != null) {
            p.setPosicion(-1, -1);
        }
        casilla[fila][columna] = null;
    }

    public boolean hayEspacio() {
        for (int i = 0; i < fila; i++) {
            for (int j = 0; j < columna; j++) {
                if (casilla[i][j] == null) {
                    return true;
                }
            }
        }
        return false;
    }

    public int contarPiezas(int numJugador) {
        return contarPiezasJugador(numJugador, 0, 0);
    }

    private int contarPiezasJugador(int numJugador, int f, int c) {
        if (f >= 6) {
            return 0;
        }

        int contador = 0;
        Piezas p = get(f, c);
        try {
            if (p != null && p.getJugador() == numJugador) {
                contador++;
            }
        } catch (Exception ignored) {
        }

        if (c < 5) {
            return contador + contarPiezasJugador(numJugador, f, c + 1);
        } else {
            return contador + contarPiezasJugador(numJugador, f + 1, 0);
        }
    }

   public int distanciaMaxima(int f1, int c1, int f2, int c2) {
        int difFila = Math.abs(f2 - f1);
        int difCol = Math.abs(c2 - c1);
        return Math.max(difFila, difCol);
    }

    public boolean esAdyacente(int f1, int c1, int f2, int c2) {
        if (!estaDentro(f2, c2)) {
            return false;
        }
        int distancia = distanciaMaxima(f1, c1, f2, c2);
        return distancia == 1;
    }

    public boolean DistanciaExacta(int f1, int c1, int f2, int c2, int distancia) {
        if (!estaDentro(f1, c1) || !estaDentro(f2, c2)) {
            return false;
        }

        int dr = Math.abs(f2 - f1);
        int dc = Math.abs(c2 - c1);

        if (distancia != 2) {
            return false;
        }

        boolean esValido = (dr == 2 && dc == 0) || (dr == 0 && dc == 2) || (dr == 2 && dc == 2);
        if (!esValido) {
            return false;
        }

        int pasoFila = Integer.signum(f2 - f1);
        int pasoColumna = Integer.signum(c2 - c1);

        int filaIntermedia = f1 + pasoFila;
        int columnaIntermedia = c1 + pasoColumna;

        if (!estaVacia(filaIntermedia, columnaIntermedia)) {
            return false;
        }

        return true;
    }

    public int validarMovimientoHombreLobo(int f1, int c1, int f2, int c2) {
        if (!estaDentro(f2, c2)) {
            return 0;
        }
        if (f1 == f2 && c1 == c2) {
            return 0;
        }
        int distFila = f2 - f1;
        int distCol = c2 - c1;
        int sdr = Integer.compare(distFila, 0);
        int sdc = Integer.compare(distCol, 0);
        int fPrim = f1 + sdr;
        int cPrim = c1 + sdc;
        if (fPrim == f2 && cPrim == c2) {
            if (estaVacia(f2, c2)) {
                return 1;
            } else {
                return 0;
            }
        }
        int fSeg = f1 + 2 * sdr;
        int cSeg = c1 + 2 * sdc;
        if (fSeg == f2 && cSeg == c2) {
            if (estaDentro(fPrim, cPrim) && estaVacia(fPrim, cPrim) && estaVacia(fSeg, cSeg)) {
                return 2;
            } else {
                return 0;
            }
        }
        return 0;
    }
}
