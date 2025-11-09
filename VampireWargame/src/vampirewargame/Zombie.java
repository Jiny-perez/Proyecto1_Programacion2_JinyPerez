package vampirewargame;

/**
 *
 * @author marye
 */
public class Zombie extends Piezas {

    public Zombie(int Jugador) {
        super(Jugador, 1, 0, 1, "Zombie");
    }
    
     public void ataqueEspecial(int opcion, Tablero tablero, int fila, int columna) {}
     
     public String realizarAccion(String accion, Tablero tablero) {
        return "Zombie: acción no implementada localmente";
    }
     
}
