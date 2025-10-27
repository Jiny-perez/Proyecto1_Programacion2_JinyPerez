package MenuInicial;
import java.util.ArrayList;
import java.util.Calendar;
import javax.swing.JOptionPane;

/**
 *
 * @author marye
 */
public class Account {

    private String username;
    private String password;
    private int puntosAcumulados;
    private Calendar fechaIngreso;
    private boolean activo;
    private ArrayList<Log> partidas;

    public Account(String username, String password) {
        this.username = username;
        this.password = password;
        this.puntosAcumulados = 0;
        this.fechaIngreso = Calendar.getInstance();
        this.activo = true;
        this.partidas = new ArrayList<>();
    }

    public String getUsername() {
        return username;
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
    }

    public void agregarLog(Log l) {
        partidas.add(0, l);
    }

    public void printDatos() {
        if (!activo) {
            return;
        }

        System.out.println("Usuario: " + username);
        System.out.println("Puntos: " + puntosAcumulados);
        System.out.println("Fecha de ingreso: " + fechaIngreso);
    }
}

