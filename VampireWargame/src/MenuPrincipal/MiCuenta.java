package MenuPrincipal;

import MenuInicial.Account;
import MenuInicial.AccountRegistry;
import MenuInicial.MenuInicio;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class MiCuenta {

    private JLabel lblUsername, lblPuntos;
    private Account player;
    private AccountRegistry registro;
    int eliminarCuenta;

    public MiCuenta(Account player, AccountRegistry registro) {
        this.registro = registro;
        this.player = player;
        initComponents();
    }

    private void initComponents() {
        // Ventana principal
        JFrame VMiCuenta = new JFrame("Mi Cuenta");
        VMiCuenta.setSize(1200, 800);
        VMiCuenta.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        VMiCuenta.setResizable(false);
        VMiCuenta.setLayout(new BorderLayout());
        VMiCuenta.setLocationRelativeTo(null);

        // Ventana principal
        JPanel PMiCuenta = new JPanel(new GridBagLayout());

        // Color base
        Color Azul = new Color(70, 130, 180);

        // Panel central
        JPanel PCentral = new JPanel(new BorderLayout(0, 30));
        PCentral.setBackground(Color.WHITE);
        PCentral.setPreferredSize(new Dimension(700, 400));
        PCentral.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Azul, 5),
                BorderFactory.createEmptyBorder(40, 60, 40, 60)
        ));

        // Titulo
        JLabel lblTitulo = new JLabel("Mi Cuenta", SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 42));
        lblTitulo.setForeground(Azul);

        // Panel de datos del jugador
        JPanel PDatos = new JPanel(new GridLayout(2, 2, 10, 20));
        PDatos.setBackground(Color.WHITE);

        JLabel lblUsuarioText = new JLabel("USUARIO:");
        lblUsuarioText.setFont(new Font("Segoe UI", Font.BOLD, 22));
        lblUsuarioText.setForeground(Azul);
        PDatos.add(lblUsuarioText);

        lblUsername = new JLabel(player.getUsername());
        lblUsername.setFont(new Font("Segoe UI", Font.PLAIN, 20));
        lblUsername.setForeground(new Color(60, 70, 100));
        PDatos.add(lblUsername);

        JLabel lblPuntosText = new JLabel("PUNTOS:");
        lblPuntosText.setFont(new Font("Segoe UI", Font.BOLD, 22));
        lblPuntosText.setForeground(Azul);
        PDatos.add(lblPuntosText);

        lblPuntos = new JLabel(String.valueOf(player.getPuntosAcumulados()));
        lblPuntos.setFont(new Font("Segoe UI", Font.PLAIN, 20));
        lblPuntos.setForeground(new Color(60, 70, 100));
        PDatos.add(lblPuntos);

        // Panel de botones
        JPanel PBotones = new JPanel(new GridLayout(1, 2, 30, 0));
        PBotones.setBackground(Color.WHITE);

        JButton BtnCambiarPassword = new JButton("CAMBIAR CONTRASEÑA");
        JButton BtnEliminarCuenta = new JButton("ELIMINAR CUENTA");
        JButton[] botones = {BtnCambiarPassword, BtnEliminarCuenta};

        for (JButton b : botones) {
            b.setFont(new Font("Segoe UI", Font.BOLD, 20));
            b.setBackground(Azul);
            b.setForeground(Color.WHITE);
            b.setFocusPainted(false);
            b.setPreferredSize(new Dimension(300, 50));
            PBotones.add(b);

            b.addMouseListener(new MouseAdapter() {
                public void mouseEntered(MouseEvent e) {
                    b.setBackground(Azul.darker());
                }

                public void mouseExited(MouseEvent e) {
                    b.setBackground(Azul);
                }
            });
        }

        BtnCambiarPassword.addActionListener(e -> {
            CambiarPassword();
        });

        BtnEliminarCuenta.addActionListener(e -> {
            EliminarCuenta();
            if (eliminarCuenta == JOptionPane.YES_OPTION) {
                VMiCuenta.dispose();
                new MenuInicio(registro);
            }
        });

        PCentral.add(lblTitulo, BorderLayout.NORTH);
        PCentral.add(PDatos, BorderLayout.CENTER);
        PCentral.add(PBotones, BorderLayout.SOUTH);

        PMiCuenta.add(PCentral);
        VMiCuenta.add(PMiCuenta);
        VMiCuenta.setVisible(true);
    }

    public void CambiarPassword() {
        String newPassword = JOptionPane.showInputDialog(null, "Ingrese la nueva contraseña:");
        String actualPassword = player.getPassword();

        boolean cambiarPassword = player.cambiarPassword(actualPassword, newPassword);
        if (cambiarPassword) {
            player.setPassword(newPassword);
            JOptionPane.showMessageDialog(null, "Se ha cambiado de correctamente.");
        }
    }

    public void EliminarCuenta() {
        eliminarCuenta = JOptionPane.showConfirmDialog(null, "¿Está seguro que desea eliminar la cuenta?", "Confirmar", JOptionPane.YES_NO_OPTION);

        String username;
        username = player.getUsername();

        if (eliminarCuenta == JOptionPane.YES_OPTION) {
            registro.eliminarPlayer(username);
        }
    }
}

