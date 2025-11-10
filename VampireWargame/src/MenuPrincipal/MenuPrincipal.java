package MenuPrincipal;

import Imagenes.Panel;
import MenuInicial.AccountRegistry;
import MenuInicial.Account;
import MenuInicial.LogIn;
import MenuPrincipal.MiCuenta;
import MenuPrincipal.Reportes;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Objects;
import vampirewargame.InterfazJuego;

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
        VMenuPrincipal.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        VMenuPrincipal.setResizable(false);
        VMenuPrincipal.setLayout(new BorderLayout());
        VMenuPrincipal.setLocationRelativeTo(null);

        Panel PMenuPrincipal = new Panel("/Imagenes/BMenuPrincipal.png");
        PMenuPrincipal.setLayout(new BorderLayout());

        JPanel PBotones = new JPanel(new GridLayout(4, 1, 0, 30));
        PBotones.setOpaque(false);
        PBotones.setBorder(BorderFactory.createEmptyBorder(350, 350, 100, 300));

        JButton BtnJugar = new JButton("JUGAR VAMPIRE WARGAME");
        JButton BtnMiCuenta = new JButton("MI CUENTA");
        JButton BtnReportes = new JButton("REPORTES");
        JButton BtnLogOut = new JButton("LOG OUT");

        JButton[] botones = {BtnJugar, BtnMiCuenta, BtnReportes, BtnLogOut};

        Font fuente = new Font("Segoe UI", Font.BOLD, 30);
        Color fondo = new Color(100, 149, 237);
        Color colorTexto = Color.WHITE;

        Color amarillo = new Color(189, 100, 21);

        for (JButton b : botones) {
            b.setFont(new Font("Segoe UI", Font.BOLD, 30));
            b.setBackground(amarillo);
            b.setForeground(Color.WHITE);
            b.setFocusPainted(false);
            b.setContentAreaFilled(true);
            b.setOpaque(true);

            b.setBorder(BorderFactory.createLineBorder(amarillo, 6));
            PBotones.add(b);

            b.addMouseListener(new MouseAdapter() {
                public void mouseEntered(MouseEvent e) {
                    b.setBackground(amarillo.darker());
                }

                public void mouseExited(MouseEvent e) {
                    b.setBackground(amarillo.brighter());
                }
            });
        }

        BtnJugar.addActionListener(e -> {
            java.util.List<MenuInicial.Account> todas = registro.listarCuentas();
            java.util.List<MenuInicial.Account> otras = new java.util.ArrayList<>();

            for (MenuInicial.Account acc : todas) {
                if (acc == null) {
                    continue;
                }
                if (acc.getUsername() == null) {
                    continue;
                }
                if (!acc.getUsername().equalsIgnoreCase(player.getUsername())) {
                    otras.add(acc);
                }
            }

            if (otras.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Registra otro jugador para habilatar la opcion de jugar", "ERROR", JOptionPane.WARNING_MESSAGE);
                return;
            }

            String[] opciones = new String[otras.size()];
            for (int i = 0; i < otras.size(); i++) {
                opciones[i] = otras.get(i).getUsername();
            }

            String elegido = (String) JOptionPane.showInputDialog(
                    null,"Selecciona con cuál oponente gusta jugar:",
                    null, JOptionPane.QUESTION_MESSAGE, null,
                    opciones, opciones[0]);

            if (elegido == null) {
                return;
            }

            int seleccionado = -1;
            for (int i = 0; i < opciones.length; i++) {
                if (Objects.equals(opciones[i], elegido)) {
                    seleccionado = i;
                    break;
                }
            }
            if (seleccionado < 0) {
                JOptionPane.showMessageDialog(null, "No se seleccionó un oponente válido.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            MenuInicial.Account oponente = otras.get(seleccionado);
            JOptionPane.showMessageDialog(null,
                    "Iniciando partida entre:\n " + player.getUsername() + " VS " + oponente.getUsername(),
                    null, JOptionPane.INFORMATION_MESSAGE);
            new InterfazJuego(player, oponente);
            VMenuPrincipal.dispose();
        });

        BtnMiCuenta.addActionListener(e
                -> {
            new MiCuenta(player, registro);
            VMenuPrincipal.dispose();
        }
        );

        BtnReportes.addActionListener(e
                -> {
            new Reportes(player, registro);
            VMenuPrincipal.dispose();
        }
        );

        BtnLogOut.addActionListener(e
                -> {
            int opcion = JOptionPane.showConfirmDialog(
                    null,
                    "¿Estás seguro de que deseas cerrar sesión?",
                    "Confirmar cierre de sesión",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.QUESTION_MESSAGE
            );

            if (opcion == JOptionPane.YES_OPTION) {
                new LogIn(registro);
                VMenuPrincipal.dispose();
            }
        }
        );

        PMenuPrincipal.add(PBotones, BorderLayout.CENTER);

        VMenuPrincipal.setContentPane(PMenuPrincipal);

        VMenuPrincipal.setVisible(true);
    }
}
