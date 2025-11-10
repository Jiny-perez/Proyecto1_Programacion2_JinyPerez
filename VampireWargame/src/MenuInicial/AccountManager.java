package MenuInicial;

import java.util.List;

/**
 *
 * @author marye
 */
public interface AccountManager {

    boolean agregarPlayer(Account acc);

    Account buscarPlayer(String username);

    boolean eliminarPlayer(String username);

    List<Account> rankingPlayers();
    
    public List<Account> obtenerCuentasIniciadas();
}

