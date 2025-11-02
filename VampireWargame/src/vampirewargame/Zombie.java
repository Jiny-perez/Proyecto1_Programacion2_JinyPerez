package vampirewargame;

import javax.swing.JOptionPane;

/**
 *
 * @author marye
 */
public class Zombie extends Piezas {

    public Zombie(int jugador) {
        super("Zombie", jugador, 1, 1, 0);
    }

    public boolean esAdyacente(int filaDestino, int columnaDestino, int filaActual, int columnaActual) {
        return super.esAdyacente(filaDestino, columnaDestino, filaActual, columnaActual);
    }

    public void ataqueEspecial(Tablero tablero, int filaZombie, int columnaZombie) {
        boolean oponenteAtacado = false;

        for (int opFila = 0; opFila < 6; opFila++) {
            for (int opColumna = 0; opColumna < 6; opColumna++) {
                Piezas oponente = tablero.getPosicion(opFila, opColumna);

                if (oponente != null && !(oponente instanceof Zombie)) {
                    if (esAdyacente(opFila, opColumna, filaZombie, columnaZombie)) {
                        oponente.danioOponente(1);
                    }

                    if (!oponente.vivo()) {
                        tablero.setPieza(opFila, opColumna, null);
                    }

                    oponenteAtacado = true;
                    break;
                }
            }
            if (oponenteAtacado) {
                break;
            }
        }

        if (!oponenteAtacado) {
            JOptionPane.showMessageDialog(null, "Error: No hay enemigo adyacente al zombie para atacar.");
        }

    }
}
