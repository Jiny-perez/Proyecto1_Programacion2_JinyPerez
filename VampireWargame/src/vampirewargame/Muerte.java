package vampirewargame;

import javax.swing.JOptionPane;

/**
 *
 * @author marye
 */
public class Muerte extends Piezas {

    public Muerte() {
        super("Muerte", 4, 3, 1);
    }

    public void ataqueEspecial(Tablero tablero, int fila, int columna) {
        String[] tipoAtaque = {"Lanzar lanza", "Conjurar Zombie", "Ataque Zombie"};

        String ataqueSeleccionda = tipoAtaque[0];

        switch (ataqueSeleccionda) {
            case "Lanzar lanza":
                lanzarLanza(tablero, fila, columna);
                break;
            case "Conjurar Zombie":
                conjurarZombie(tablero, fila, columna);
                break;
            case "Ataque Zombie":
                break;
            default:
                System.out.println("Ataque desconocido");
                break;
        }
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

    public void lanzarLanza(Tablero tablero, int filaDestino, int columnaDestino) {
        int[] hayPieza = tablero.buscarPieza(this);
        int filaActual = hayPieza[0];
        int columnaActual = hayPieza[1];

        if (hayPieza == null) {
            JOptionPane.showMessageDialog(null, "Error: No se encontró la pieza en el tablero.");
            return;
        }

        if (!esAdyacente(filaActual, columnaActual, filaDestino, columnaDestino)) {
            JOptionPane.showMessageDialog(null, "Error: Solo se puede atacar a una casilla a 2 de distancia.");
            return;
        }

        if (!recorrido(tablero, filaActual, columnaActual, filaDestino, columnaDestino)) {
            JOptionPane.showMessageDialog(null, "Error: No puedes atacar pasando sobre otra pieza.");
            return;
        }

        Piezas oponente = tablero.getPosicion(filaDestino, columnaDestino);

        if (oponente == null) {
            JOptionPane.showMessageDialog(null, "Error: No hay enemigo en esa casilla.");
            return;
        }

        oponente.vida -= this.ataque / 2;
        if (oponente.vida < 0) {
            oponente.vida = 0;
        }

        if (!oponente.vivo()) {
            tablero.setPieza(filaDestino, columnaDestino, null);
        }
    }

    public void conjurarZombie(Tablero tablero, int fila, int columna) {
        if (tablero.casillaVacia(fila, columna)) {
            Piezas zombie = new Zombie();
            tablero.setPieza(fila, columna, zombie);
        } else {
            JOptionPane.showMessageDialog(null, "Error: Casilla llena");
        }
    }

    public void ataqueZombie(Tablero tablero, int opFila, int opColumna) {
        Piezas oponente = tablero.getPosicion(opFila, opColumna);

        if (oponente == null) {
            JOptionPane.showMessageDialog(null, "Error: No hay enemigos en esa casilla..");
            return;
        }

        Zombie hayZombie = tablero.buscarZombie(opFila, opColumna);
        if (hayZombie != null) {
            hayZombie.ataqueEspecial(tablero, tablero.buscarPieza(hayZombie)[0], tablero.buscarPieza(hayZombie)[1]);
        } else {
            JOptionPane.showMessageDialog(null, "Error: No hay zombies adyacentes al enemigo para atacar.");
        }
    }
}
