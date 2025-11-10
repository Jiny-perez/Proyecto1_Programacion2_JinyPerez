package MenuInicial;

import java.util.Calendar;

/**
 *
 * @author marye
 */
public class Log {

    private String username;
    private String descripcion;
    private Calendar fecha;

    public Log(String username, String descripcion) {
        this.username = username;
        this.descripcion = descripcion;
        this.fecha=Calendar.getInstance();

    }

    public String getUsername() {
        return username;
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

