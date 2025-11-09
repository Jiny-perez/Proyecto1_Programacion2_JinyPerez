package vampirewargame;

/**
 *
 * @author marye
 */
public class Vampiro extends Piezas {

    public Vampiro(int jugador) {
        super(jugador, 4, 5, 3, "Vampiro");
    }

    public void ataqueEspecial(int opcion, Tablero tablero, int fila, int columna) {
        if (opcion == 1) {
            Piezas oponente = tablero.get(fila, columna);

            if (oponente != null && oponente.getJugador() != this.getJugador()) {
                oponente.danio(1);
                this.curacion(1);

                if (!oponente.estaVivo()) {
                    tablero.eliminarPieza(fila, columna);
                }
            }
        }
    }

    public String realizarAccion(String accion, Tablero tablero) {
        return "Vampiro: acción no implementada localmente";
    }
}
