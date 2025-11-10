package MenuInicial;

import Imagenes.Panel;
import MenuPrincipal.MenuPrincipal;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 *
 * @author marye
 */
public class CrearPlayer {

    private JTextField txtUsername;
    private JPasswordField txtPassword;
    private AccountRegistry registro;
    boolean cuentaCreada;
    private Account cuentaEncontrada;

    public CrearPlayer(AccountRegistry registro) {
        this.registro = registro;
        initcomponent();
    }

    public void initcomponent() {
        JFrame VCrearPlayer = new JFrame();
        VCrearPlayer.setSize(1200, 800);
        VCrearPlayer.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        VCrearPlayer.setResizable(false);
        VCrearPlayer.setLocationRelativeTo(null);
        VCrearPlayer.setLayout(null);

        Panel PCrearPlayer = new Panel("/Imagenes/BgMenu.png");
        PCrearPlayer.setBounds(0, 0, 1186, 765);

        Color amarillo = new Color(189, 100, 21);

        JPanel PCentral = new JPanel(new BorderLayout(0, 40));
        PCentral.setBackground(Color.WHITE);
        PCentral.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(amarillo, 5),
                BorderFactory.createEmptyBorder(40, 60, 40, 60)
        ));

        PCentral.setSize(650, 440);
        PCentral.setLocation((1200 - 650) / 2, (800 - 460) / 2 + 75);
        PCentral.setOpaque(true);

        JLabel lblTitulo = new JLabel("CREAR CUENTA", SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 42));
        lblTitulo.setForeground(amarillo);

        JPanel PDatos = new JPanel(new GridLayout(3, 2, 50, 10));
        PDatos.setBackground(Color.WHITE);

        JLabel lblUsername = new JLabel("USUARIO:");
        lblUsername.setFont(new Font("Segoe UI", Font.BOLD, 26));
        lblUsername.setForeground(amarillo);
        PDatos.add(lblUsername);

        txtUsername = new JTextField();
        txtUsername.setFont(new Font("Segoe UI", Font.BOLD, 20));
        txtUsername.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(amarillo, 3),
                BorderFactory.createEmptyBorder(10, 10, 10, 10)
        ));
        txtUsername.setForeground(amarillo);
        PDatos.add(txtUsername);

        JLabel lblPassword = new JLabel("CONTRASEÑA:");
        lblPassword.setFont(new Font("Segoe UI", Font.BOLD, 26));
        lblPassword.setForeground(amarillo);
        PDatos.add(lblPassword);

        txtPassword = new JPasswordField();
        txtPassword.setFont(new Font("Segoe UI", Font.PLAIN, 20));
        txtPassword.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(amarillo, 3),
                BorderFactory.createEmptyBorder(10, 10, 10, 10)
        ));
        PDatos.add(txtPassword);

        JCheckBox chkMostrarContra = new JCheckBox("Ver contraseña");
        chkMostrarContra.setFont(new Font("Segoe UI", Font.PLAIN, 20));
        chkMostrarContra.setForeground(amarillo);
        chkMostrarContra.setBackground(Color.WHITE);
        chkMostrarContra.setFocusPainted(false);
        chkMostrarContra.setCursor(new Cursor(Cursor.HAND_CURSOR));

        PDatos.add(new JLabel(""));
        final char ocultar = txtPassword.getEchoChar();
        chkMostrarContra.addItemListener(e -> {
            if (e.getStateChange() == ItemEvent.SELECTED) {
                txtPassword.setEchoChar((char) 0);
            } else {
                txtPassword.setEchoChar(ocultar);
            }
            txtPassword.setFont(new Font("Segoe UI", Font.BOLD, 26));
            txtPassword.setForeground(amarillo);
        });
        PDatos.add(chkMostrarContra);

        JPanel PBotones = new JPanel(new GridLayout(1, 2, 10, 30));
        PBotones.setBackground(Color.WHITE);
        JButton BtnCrearCuenta = new JButton("CREAR JUGADOR");
        JButton BtnSalir = new JButton("SALIR");
        JButton[] botones = {BtnCrearCuenta, BtnSalir};

        for (JButton b : botones) {
            b.setFont(new Font("Segoe UI", Font.BOLD, 30));
            b.setBackground(amarillo);
            b.setForeground(Color.WHITE);
            b.setFocusPainted(false);
            b.setContentAreaFilled(true);
            b.setOpaque(true);

            b.setBorder(BorderFactory.createLineBorder(amarillo, 2));
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

        BtnCrearCuenta.addActionListener(e -> {
            newPlayer();
            if (cuentaCreada && cuentaEncontrada != null) {
                new MenuPrincipal(registro, cuentaEncontrada);
                VCrearPlayer.dispose();
            }
        });

        BtnSalir.addActionListener(e -> {
            new MenuInicio(registro);
            VCrearPlayer.dispose();
        });

        PCentral.add(lblTitulo, BorderLayout.NORTH);
        PCentral.add(PDatos, BorderLayout.CENTER);
        PCentral.add(PBotones, BorderLayout.SOUTH);

        VCrearPlayer.add(PCrearPlayer);
        VCrearPlayer.add(PCentral);

        VCrearPlayer.getContentPane().setComponentZOrder(PCentral, 0);
        VCrearPlayer.getContentPane().setComponentZOrder(PCrearPlayer, 1);

        VCrearPlayer.validate();
        VCrearPlayer.repaint();
        VCrearPlayer.setVisible(true);
    }

    public void newPlayer() {
        String username = txtUsername.getText();
        String password = new String(txtPassword.getPassword());

        if (username.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Error: Ingrese un el nombre del usuario.");
            return;
        }
        if (password.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Error: Ingrese una contraseña");
            return;

        }

        Account acc = new Account(username, password);
        cuentaCreada = registro.agregarPlayer(acc);

        if (cuentaCreada) {
            acc.logIn();

            cuentaEncontrada = registro.buscarPlayer(username);
            if (cuentaEncontrada != null && !cuentaEncontrada.isLoggedIn()) {
                cuentaEncontrada.logIn();
            }

            JOptionPane.showMessageDialog(null, "La cuenta de jugador se ha creado correctamente.");

            if (cuentaEncontrada != null) {
                new MenuPrincipal(registro, cuentaEncontrada);

            }
        } else {
            cuentaEncontrada = null;
        }
    }
}
