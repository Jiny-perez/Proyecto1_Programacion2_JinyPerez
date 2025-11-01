package vampirewargame;
import javax.swing.JOptionPane;

/**
 *
 * @author marye
 */
public class Vampiro extends Piezas {

    public Vampiro() {
        super("Vampiro", 3, 4, 5);
    }

    public void ataqueEspecial(Tablero tablero, int fila, int columna){
        Piezas oponente = tablero.getPosicion(fila, columna);
        
        if(oponente==null){
            JOptionPane.showMessageDialog(null, "Error: No hay enemigos en esa casilla.");
        }
        
        if(oponente==this){
            JOptionPane.showMessageDialog(null, "Error: No puede atacarte a ti mismo.");
        }
        
        oponente.danioOponente(1);
        this.curacion(1);
        
        if(!oponente.vivo()){
            tablero.setPieza(fila, columna, null);        
        }
    }

}

