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
        VMenuPrincipal.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        VMenuPrincipal.setResizable(false);
        VMenuPrincipal.setLayout(new BorderLayout());
        VMenuPrincipal.setLocationRelativeTo(null);

        Panel PMenuPrincipal = new Panel("/Imagenes/BgMenuInicial.png");
        PMenuPrincipal.setLayout(new BorderLayout());

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
            if (player == null) {
                JOptionPane.showMessageDialog(null,
                        "No hay ninguna sesión iniciada. Inicia sesión para jugar.",
                        "Sin sesión", JOptionPane.WARNING_MESSAGE);
                return;
            }

            if (!player.isLoggedIn()) {
                JOptionPane.showMessageDialog(null,
                        "Tu cuenta no está marcada como iniciada. Inicia sesión correctamente antes de jugar.",
                        "Sesión no activa", JOptionPane.WARNING_MESSAGE);
                return;
            }

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
                JOptionPane.showMessageDialog(null,
                        "Se requiere al menos otra cuenta registrada para jugar.\n"
                        + "Crea o registra otra cuenta y vuelve a intentarlo.",
                        "No hay otros jugadores", JOptionPane.WARNING_MESSAGE);
                return;
            }

            String[] opciones = new String[otras.size()];
            for (int i = 0; i < otras.size(); i++) {
                opciones[i] = otras.get(i).getUsername() + (otras.get(i).isLoggedIn() ? " (conectado)" : "");
            }

            int seleccionado = -1;
            if (opciones.length == 1) {
                seleccionado = 0;
                int confirm = JOptionPane.showConfirmDialog(null,
                        "Se jugará contra: " + opciones[0] + "\n¿Deseas continuar?",
                        "Confirmar oponente", JOptionPane.YES_NO_OPTION);
                if (confirm != JOptionPane.YES_OPTION) {
                    return;
                }
            } else {
                String elegido = (String) JOptionPane.showInputDialog(
                        null,
                        "Selecciona con cuál cuenta registrada quieres jugar:",
                        "Elegir oponente",
                        JOptionPane.QUESTION_MESSAGE,
                        null,
                        opciones,
                        opciones[0]);
                if (elegido == null) {
                    return;
                }
                for (int i = 0; i < opciones.length; i++) {
                    if (opciones[i].equals(elegido)) {
                        seleccionado = i;
                        break;
                    }
                }
                if (seleccionado < 0) {
                    JOptionPane.showMessageDialog(null, "No se seleccionó un oponente válido.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
            }

            MenuInicial.Account oponente = otras.get(seleccionado);

            JOptionPane.showMessageDialog(null,
                    "Iniciando partida entre:\n1) " + player.getUsername() + "\n2) " + oponente.getUsername(),
                    "Iniciando juego", JOptionPane.INFORMATION_MESSAGE);

            new InterfazJuego(player, oponente);
            VMenuPrincipal.dispose();
        });

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
        VMenuPrincipal.setContentPane(PMenuPrincipal);
        VMenuPrincipal.setVisible(true);
    }
}
