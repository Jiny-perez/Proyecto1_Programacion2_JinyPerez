package MenuPrincipal;
import MenuInicial.AccountRegistry;
import MenuInicial.Account;
import MenuInicial.LogIn;
import MenuPrincipal.MiCuenta;
import MenuPrincipal.Reportes;
import javax.swing.*;
import java.awt.*;

/**
 *
 * @author marye
 */
public class MenuPrincipal {

    private AccountRegistry registro;
    private Account player;

    public MenuPrincipal(AccountRegistry registro, Account player) {
        this.registro = registro;
        this.player = player;
        inticomponent();
    }

    public void inticomponent() {
        JFrame VMenuPrincipal = new JFrame("Menu Inicial");
        VMenuPrincipal.setSize(1200, 800);
        VMenuPrincipal.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        VMenuPrincipal.setResizable(false);
        VMenuPrincipal.setLayout(new BorderLayout());
        VMenuPrincipal.setLocationRelativeTo(null);

        JPanel PMenuPrincipal = new JPanel(new BorderLayout());
        JPanel PBotones = new JPanel(new GridLayout(4, 1, 0, 50));
        PBotones.setBorder(BorderFactory.createEmptyBorder(250, 250, 50, 250));

        JButton BtnJugar = new JButton("JUGAR VAMPIRE WARGAME");
        JButton BtnMiCuenta = new JButton("MI CUENTA");
        JButton BtnReportes = new JButton("REPORTES");
        JButton BtnLogOut = new JButton("LOG OUT");

        JButton[] botones = {BtnJugar, BtnMiCuenta, BtnReportes, BtnLogOut};

        Font fuente = new Font("Segoe UI", Font.BOLD, 30);
        Color fondo = new Color(100, 149, 237);
        Color colorTexto = Color.WHITE;

        for (JButton b : botones) {
            b.setFont(fuente);
            b.setBackground(fondo);
            b.setForeground(colorTexto);
            b.setFocusPainted(false);

            PBotones.add(b);
        }
        
        BtnMiCuenta.addActionListener(e -> {
            new MiCuenta(player, registro); 
            VMenuPrincipal.dispose(); 
        });
        
         BtnReportes.addActionListener(e -> {
            new Reportes(player, registro);
            VMenuPrincipal.dispose();
        });

        BtnLogOut.addActionListener(e -> {
            JOptionPane.showMessageDialog(null, "Sesión cerrada");
            new LogIn(registro);
            VMenuPrincipal.dispose();
        });
        
        PMenuPrincipal.add(PBotones, BorderLayout.CENTER);
        VMenuPrincipal.add(PMenuPrincipal);
        VMenuPrincipal.setVisible(true);
    }   
}

