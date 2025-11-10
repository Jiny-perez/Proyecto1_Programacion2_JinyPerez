package MenuInicial;

import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

/**
 *
 * @author marye
 */
public class AccountRegistry implements AccountManager {

    private ArrayList<Account> player;

    public AccountRegistry() {
        player = new ArrayList<>();
    }

    public boolean agregarPlayer(Account acc) {
        try {
            for (int i = 0; i < player.size(); i++) {
                if (player.get(i).getUsername().equalsIgnoreCase(acc.getUsername())) {
                    throw new UsernameException("El usuario ya existe.");
                }
            }

            if (acc.getPassword().length() != 5) {
                throw new PasswordException("La contraseña debe de contener exactamente 5 caracteres.");
            }

            if (!acc.getPassword().matches(".*[A-Z].*")) {
                throw new PasswordException("La contraseña tiene que contener al menos una mayúscula.");
            }

            if (!acc.getPassword().matches(".*\\d.*")) {
                throw new PasswordException("La contraseña tiene que contener al menos un numero.");
            }

            if (!acc.getPassword().matches(".*[^a-zA-Z0-9].*")) {
                throw new PasswordException("La contraseña tiene que contener al menos un caracter especial");
            }
            player.add(acc);
            return true;
        } catch (UsernameException e) {
            JOptionPane.showMessageDialog(null, "Error: " + e.getMessage());
            return false;
        } catch (PasswordException e) {
            JOptionPane.showMessageDialog(null, "Error: " + e.getMessage());
            return false;
        }
    }

    public Account buscarPlayer(String username) {
        try {
            for (int i = 0; i < player.size(); i++) {
                if (player.get(i).getUsername().equalsIgnoreCase(username)) {
                    return player.get(i);
                }
            }
            throw new UsernameException("Usuario no encontrado: " + username);
        } catch (UsernameException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
            return null;
        }
    }

    public boolean eliminarPlayer(String username) {
        try {
            Account playerEncontrado = buscarPlayer(username);
            if (playerEncontrado == null) {
                throw new UsernameException("El usuario no existe.");
            }
            player.remove(playerEncontrado);
            return true;
        } catch (UsernameException e) {
            JOptionPane.showMessageDialog(null, "Error: " + e.getMessage());
            return false;
        }
    }

    public List<Account> rankingPlayers() {
        try {
            if (player.isEmpty()) {
                throw new UsernameException("No hay jugadores registrados.");
            }

            player.sort((a, b) -> Integer.compare(b.getPuntosAcumulados(), a.getPuntosAcumulados()));

            return player;
        } catch (UsernameException e) {
            JOptionPane.showMessageDialog(null, "Error: " + e.getMessage());
            return new ArrayList<>();
        }
    }

    public int totalRegistro() {
        return player.size();
    }

    public List<Account> obtenerCuentasIniciadas() {
        List<Account> registro = new ArrayList<>();
        for (Account acc : player) {
            if (acc != null && acc.isLoggedIn()) {
                registro.add(acc);
            }
        }
        return registro;
    }

    public List<Account> listarCuentas() {
        return new ArrayList<>(player);
    }
}
