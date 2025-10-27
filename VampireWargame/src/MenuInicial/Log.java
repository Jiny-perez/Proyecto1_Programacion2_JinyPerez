package MenuInicial;

import java.util.Calendar;

/**
 *
 * @author marye
 */
public class Log {

    private String player;
    private String descripcion;
    private Calendar fecha;

    public Log(String player, String descripcion) {
        this.player = player;
        this.descripcion = descripcion;
        this.fecha=fecha;

    }

    public String getPlayer() {
        return player;
    }

    public String getDescripcion() {
        return descripcion;
    }
    
    public Calendar getFecha(){
        return fecha;
    }
    
    public String mostrarFecha() {
        return fecha.get(Calendar.DAY_OF_MONTH) + "/" +
               (fecha.get(Calendar.MONTH)+1) + "/" +
               fecha.get(Calendar.YEAR) + " " +
               fecha.get(Calendar.HOUR_OF_DAY) + ":" +
               fecha.get(Calendar.MINUTE);
    }
    
}

