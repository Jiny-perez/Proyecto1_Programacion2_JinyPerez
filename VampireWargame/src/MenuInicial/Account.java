package MenuInicial;

import java.util.ArrayList;
import java.util.Calendar;
import javax.swing.JOptionPane;

/**
 *
 * @author marye
 */
public class Account extends Log{

   private String password;
    private int puntosAcumulados;
    private Calendar fechaIngreso;
    private boolean activo;
    private ArrayList<Log> partidas;
    private boolean SeccionIniciada;

    public Account(String username, String descripcion, String password) {
        super(username, descripcion);
        this.password = password;
        this.puntosAcumulados = 0;
        this.fechaIngreso = Calendar.getInstance();
        this.activo = true;
        this.partidas = new ArrayList<>();
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getPuntosAcumulados() {
        return puntosAcumulados;
    }

    public void setPuntosAcumulados(int puntosAcumulados) {
        this.puntosAcumulados = puntosAcumulados;
    }

    public Calendar getFechaIngreso() {
        return fechaIngreso;
    }

    public boolean isActivo() {
        return activo;
    }

    public ArrayList<Log> getPartidas() {
        return partidas;
    }

    public boolean isLoggedIn() {
        return SeccionIniciada;
    }

    public void logIn() {
        if (!activo) {
            return;
        }
        
        SeccionIniciada = true;
    }

    public boolean cambiarPassword(String actualPassword, String newPassword) {
        try {
            if (!activo) {
                throw new PasswordException("La cuenta está inactiva.");
            }
            if (!password.equals(actualPassword)) {
                throw new PasswordException("La contraseña actual es incorrecta.");
            }
            if (password.equals(newPassword)) {
                throw new PasswordException("La nueva contraseña no puede ser igual a la actual.");
            }
            if (newPassword.length() < 5) {
                throw new PasswordException("La nueva contraseña debe tener al menos 5 caracteres.");
            }
            if (!newPassword.matches(".*[A-Z].*")) {
                throw new PasswordException("Debe contener al menos una letra mayúscula.");
            }
            if (!newPassword.matches(".*\\d.*")) {
                throw new PasswordException("Debe contener al menos un número.");
            }
            if (!newPassword.matches(".*[^a-zA-Z0-9].*")) {
                throw new PasswordException("Debe contener al menos un símbolo especial.");
            }
            password = newPassword;
            return true;
        } catch (PasswordException e) {
            JOptionPane.showMessageDialog(null, "Error: " + e.getMessage());
            return false;
        }
    }

    public void cerrarCuenta() {
        if (!activo) {
            return;
        }
        activo = false;
        SeccionIniciada = false;
    }

    public void agregarRegistro(Log registro) {
        partidas.add(0, registro);
    }

    public void printDatos() {
        if (!activo) {
            return;
        }

        String username=super.getUsername();
        System.out.println("Usuario: " + username );
        System.out.println("Puntos: " + puntosAcumulados);
        System.out.println("Fecha de ingreso: " + fechaIngreso);
    }
}
