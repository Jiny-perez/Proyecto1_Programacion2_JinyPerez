package vampirewargame;

/**
 *
 * @author marye
 */
public class HombreLobo extends Piezas {

    public HombreLobo(int jugador) {
        super(jugador, 5, 2, 3, "Hombre Lobo");
    }

    public void ataqueEspecial(int opcion, Tablero tablero, int fila, int columna) {
        if (!tablero.estaDentro(fila, columna)) {
            return;
        }
        int filaActual = this.getFila();
        int columnaActual = this.getColumna();

        int distFila = Math.abs(filaActual - fila);
        int distColumna = Math.abs(columnaActual - columna);
        int distancia = Math.max(distFila, distColumna);

        if (distancia == 0 || distancia > 2) {
            return;
        }

        //Recorrido
        int pasoFila = Integer.compare(fila, filaActual);
        int pasoColumna = Integer.compare(columna, columnaActual);

        for (int i = 1; i <= distancia; i++) {
            int recFila = filaActual + pasoFila * i;
            int recColumna = columnaActual + pasoColumna * i;

            if (!tablero.estaDentro(recFila, recColumna)) {
                return;
            }

            if (!tablero.estaVacia(recFila, recColumna)) {
                return;
            }
        }

        tablero.eliminarPieza(this.getFila(), this.getColumna());
        tablero.colocarPieza(this, fila, columna);
    }

    public String realizarAccion(String accion, Tablero tablero) {
        return "HombreLobo: acción no implementada localmente";
    }

}
