package vampirewargame;

import javax.swing.JOptionPane;

/**
 *
 * @author marye
 */
public class HombreLobo extends Piezas {

    public HombreLobo() {
        super("HombreLobo", 5, 5, 2);
    }

    public boolean esAdyacente(int filaDestino, int columnaDestino, int filaActual, int columnaActual) {
        int distFila = Math.abs(filaDestino - filaActual);
        int distColumna = Math.abs(columnaDestino - columnaActual);

        if (distFila > 2 || distColumna > 2 || (distFila == 0 && distColumna == 0)) {
            return false;
        } else {
            return true;
        }
    }

    public boolean recorrido(Tablero tablero, int filaActual, int columnaActual, int filaDestino, int columnaDestino) {
        int dirFila = Integer.compare(filaDestino, filaActual);
        int dirCol = Integer.compare(columnaDestino, columnaActual);

        int recFila = filaActual + dirFila;
        int recColumna = columnaActual + dirCol;

        while (recFila != filaDestino || recColumna != columnaDestino) {
            if (!tablero.casillaVacia(recFila, recColumna)) {
                return false; 
            }
            recFila += dirFila;
            recColumna += dirCol;
        }
        return true;
    }

    public void ataqueEspecial(Tablero tablero, int filaDestino, int columnaDestino) {
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
            JOptionPane.showMessageDialog(null, "Error: Solo se puede moverte hasta 2 casilla adyacente.");
            return;
        }
        
        if(!recorrido(tablero, filaActual, columnaActual, filaDestino, filaActual)){
            JOptionPane.showMessageDialog(null, "Error: No puedes pasar sobre otra pieza.");
        }

        tablero.setPieza(filaDestino, columnaDestino, this);
        tablero.setPieza(filaActual, columnaActual, null);
    }
}
