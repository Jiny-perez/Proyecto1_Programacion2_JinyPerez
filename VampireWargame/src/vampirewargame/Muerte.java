package vampirewargame;

/**
 *
 * @author marye
 */
public class Muerte extends Piezas {

    public Muerte(int jugador) {
        super(jugador, 3, 1, 4, "Muerte");
    }

    public void ataqueEspecial(int opcion, Tablero tablero, int fila, int columna) {
        int op = opcion;
        Piezas oponente = tablero.get(fila, columna);

        // calcular distancia entre la muerte y el oponente
        int distFila = Math.abs(this.getFila() - fila);
        int distColumna = Math.abs(this.getColumna() - columna);
        int distancia = Math.max(distFila, distColumna);

        switch (op) {
            case 1:

                if (!tablero.estaDentro(fila, columna)) {
                    return;
                }

                if (oponente == null && oponente.getJugador() == this.getJugador()) {
                    return;
                }

                if (distancia == 2) {
                    oponente.vida -= 2;
                }
                break;

            case 2:
                if (!tablero.estaDentro(fila, columna)) {
                    return;
                }
                if (!tablero.hayEspacio()) {
                    return;
                }
                if (!tablero.estaVacia(fila, columna)) {
                    return;
                }

                Zombie zombi = new Zombie(this.getJugador());
                tablero.colocarPieza(zombi, fila, columna);
                break;

            case 3:
                if (!tablero.estaDentro(fila, columna)) {
                    return;
                }

                if (oponente == null && oponente.getJugador() == this.getJugador()) {
                    return;
                }
                if (distancia <= 2) {
                    return;
                }

                boolean zombieAdyacente = false;
                Zombie atacante = null;

                for (int rx = -1; rx <= 1 && !zombieAdyacente; rx++) {
                    for (int ry = -1; ry <= 1 && !zombieAdyacente; ry++) {
                        int nx = fila + rx;
                        int ny = columna + ry;

                        if (!tablero.estaDentro(nx, ny)) {
                            continue;
                        }

                        Piezas p = tablero.get(nx, ny);
                        if (p != null && p instanceof Zombie && p.getJugador() == this.getJugador()) {
                            zombieAdyacente = true;
                            atacante = (Zombie) p;
                        }
                    }
                }

                if (zombieAdyacente && atacante != null) {
                    oponente.danio(1);
                }
                break;

            default:
                if (!tablero.estaDentro(fila, columna)) {
                    return;
                }
                if (oponente != null || oponente.getJugador() != this.getJugador()) {
                    this.ataqueNormal(oponente);
                }
                break;
        }
        
        if (oponente != null && !oponente.estaVivo()) {
            tablero.eliminarPieza(fila, columna);
        }
    }

    public String realizarAccion(String accion, Tablero tablero) {
        return "Muerte: acción no implementada localmente";
    }

}
