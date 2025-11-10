package MenuPrincipal;

import Imagenes.Panel;
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
        JFrame VMiCuenta = new JFrame();
        VMiCuenta.setSize(1200, 800);
        VMiCuenta.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        VMiCuenta.setResizable(false);
        VMiCuenta.setLocationRelativeTo(null);
        VMiCuenta.setLayout(null);

        Panel PMiCuenta = new Panel("/Imagenes/BgMenu.png");
        PMiCuenta.setBounds(0, 0, 1186, 765);

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

        JLabel lblTitulo = new JLabel("MI CUENTA", SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 42));
        lblTitulo.setForeground(amarillo);

        JPanel PDatos = new JPanel(new GridLayout(2, 2, 10, 20));
        PDatos.setBackground(Color.WHITE);

        JLabel lblUsuarioText = new JLabel("USUARIO:");
        lblUsuarioText.setFont(new Font("Segoe UI", Font.BOLD, 22));
        lblUsuarioText.setForeground(amarillo);
        PDatos.add(lblUsuarioText);

        lblUsername = new JLabel(player.getUsername());
        lblUsername.setFont(new Font("Segoe UI", Font.PLAIN, 20));
        lblUsername.setForeground(amarillo);
        PDatos.add(lblUsername);

        JLabel lblPuntosText = new JLabel("PUNTOS:");
        lblPuntosText.setFont(new Font("Segoe UI", Font.BOLD, 22));
        lblPuntosText.setForeground(amarillo);
        PDatos.add(lblPuntosText);

        lblPuntos = new JLabel(String.valueOf(player.getPuntosAcumulados()));
        lblPuntos.setFont(new Font("Segoe UI", Font.PLAIN, 20));
        lblPuntos.setForeground(amarillo);
        PDatos.add(lblPuntos);

        JPanel PBotones = new JPanel(new GridLayout(3, 1, 5, 10));
        PBotones.setBackground(Color.WHITE);

        JButton BtnCambiarPassword = new JButton("CAMBIAR CONTRASEÑA");
        JButton BtnEliminarCuenta = new JButton("ELIMINAR CUENTA");
        JButton BtnSalir = new JButton("SALIR");

        JButton[] botones = {BtnCambiarPassword, BtnEliminarCuenta, BtnSalir};

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

        BtnSalir.addActionListener(e -> {
            new MenuPrincipal(registro, player);
            VMiCuenta.dispose();
        });

        PCentral.add(lblTitulo, BorderLayout.NORTH);
        PCentral.add(PDatos, BorderLayout.CENTER);
        PCentral.add(PBotones, BorderLayout.SOUTH);

        VMiCuenta.add(PMiCuenta);
        VMiCuenta.add(PCentral);

        VMiCuenta.getContentPane().setComponentZOrder(PCentral, 0);
        VMiCuenta.getContentPane().setComponentZOrder(PMiCuenta, 1);

        VMiCuenta.validate();
        VMiCuenta.repaint();
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
