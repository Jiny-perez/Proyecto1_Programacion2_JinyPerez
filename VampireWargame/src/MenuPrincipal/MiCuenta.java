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
        Color amarillo = new Color(189, 100, 21);
        Font tituloFont = new Font("Segoe UI", Font.BOLD, 22);
        Font labelFont = new Font("Segoe UI", Font.BOLD, 14);
        Font botonFont = new Font("Segoe UI", Font.BOLD, 14);

        JDialog dialogo = new JDialog((Frame) null, "CAMBIAR CONTRASEÑA", true);
        dialogo.setSize(480, 340);
        dialogo.setResizable(false);
        dialogo.setLayout(new BorderLayout(0, 0));
        dialogo.getContentPane().setBackground(Color.WHITE);
        dialogo.setLocationRelativeTo(null);

        JPanel cabecera = new JPanel(new BorderLayout());
        cabecera.setBackground(Color.WHITE);
        JLabel titulo = new JLabel("CAMBIAR CONTRASEÑA", SwingConstants.CENTER);
        titulo.setFont(tituloFont);
        titulo.setForeground(amarillo);
        titulo.setBorder(BorderFactory.createEmptyBorder(16, 16, 8, 16));
        cabecera.add(titulo, BorderLayout.CENTER);
        dialogo.add(cabecera, BorderLayout.NORTH);

        JPanel PFormulario = new JPanel(new GridBagLayout());
        PFormulario.setBackground(Color.WHITE);

        JPanel PDatos = new JPanel(new GridLayout(4, 2, 12, 12));
        PDatos.setBackground(Color.WHITE);
        PDatos.setOpaque(true);
        PDatos.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));

        String[] labelsText = {"Contraseña actual:", "Nueva contraseña:", "Confirmar Contraseña:"};
        JLabel[] labels = new JLabel[labelsText.length];
        JPasswordField[] fields = new JPasswordField[labelsText.length];

        for (int i = 0; i < labelsText.length; i++) {
            labels[i] = new JLabel(labelsText[i], SwingConstants.RIGHT);
            labels[i].setFont(labelFont);
            labels[i].setForeground(amarillo);

            fields[i] = new JPasswordField();
            fields[i].setFont(labelFont);
            fields[i].setForeground(amarillo);
            fields[i].setCaretColor(amarillo);
            fields[i].setBackground(Color.WHITE);
            fields[i].setBorder(BorderFactory.createLineBorder(amarillo, 1));
            fields[i].setPreferredSize(new Dimension(250, 30));

            PDatos.add(labels[i]);
            PDatos.add(fields[i]);
        }

        JCheckBox chkMostrarContra = new JCheckBox(" Ver contraseña");
        chkMostrarContra.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        chkMostrarContra.setForeground(amarillo);
        chkMostrarContra.setBackground(Color.WHITE);
        chkMostrarContra.setFocusPainted(false);
        chkMostrarContra.setCursor(new Cursor(Cursor.HAND_CURSOR));

        char ocultar = fields[0].getEchoChar();
        chkMostrarContra.addActionListener(ev -> {
            char echo = chkMostrarContra.isSelected() ? (char) 0 : ocultar;
            for (JPasswordField f : fields) {
                f.setEchoChar(echo);
            }
        });
        
        PDatos.add(new JLabel("")); 
        PDatos.add(chkMostrarContra);

        PFormulario.add(PDatos);
        dialogo.add(PFormulario, BorderLayout.CENTER);

        JPanel PBotones = new JPanel(new GridLayout(1, 2, 12, 0));
        PBotones.setBackground(Color.WHITE);
        PBotones.setOpaque(true);
        PBotones.setBorder(BorderFactory.createEmptyBorder(12, 30, 20, 30));

        String[] btnText = {"CANCELAR", "CAMBIAR"};
        JButton[] btns = new JButton[2];
        for (int i = 0; i < btns.length; i++) {
            btns[i] = new JButton(btnText[i]);
            btns[i].setFont(botonFont);
            btns[i].setBackground(amarillo);
            btns[i].setForeground(Color.WHITE); 
            btns[i].setFocusPainted(false);
            btns[i].setContentAreaFilled(true);
            btns[i].setOpaque(true);
            
            btns[i].setBorder(BorderFactory.createLineBorder(amarillo, 2));
            final JButton b = btns[i];
            
            b.addMouseListener(new MouseAdapter() {
                public void mouseEntered(MouseEvent e) {
                    b.setBackground(amarillo.brighter());
                }

                public void mouseExited(MouseEvent e) {
                    b.setBackground(amarillo.darker());
                }
            });
            PBotones.add(btns[i]);
        }
        
        btns[0].setBackground(new Color(170, 170, 170));
        btns[0].setForeground(Color.WHITE);
        btns[0].addActionListener(ae -> dialogo.dispose()); 

        btns[1].addActionListener(ae -> { 
            String actual = new String(fields[0].getPassword()).trim();
            String nueva = new String(fields[1].getPassword()).trim();
            String confirm = new String(fields[2].getPassword()).trim();

            if (actual.isEmpty() || nueva.isEmpty() || confirm.isEmpty()) {
                JOptionPane.showMessageDialog(dialogo, "Todos los campos son obligatorios.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            if (!actual.equals(player.getPassword())) {
                JOptionPane.showMessageDialog(dialogo, "La contraseña actual no coincide.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            if (!nueva.equals(confirm)) {
                JOptionPane.showMessageDialog(dialogo, "La nueva contraseña y la confirmación no coinciden.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            boolean ok = player.cambiarPassword(actual, nueva);
            if (!ok) {
                JOptionPane.showMessageDialog(dialogo, "No se pudo cambiar la contraseña. Revise las reglas de contraseña.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            player.setPassword(nueva);
            JOptionPane.showMessageDialog(dialogo, "Contraseña cambiada correctamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
            dialogo.dispose();
        });

        dialogo.add(PBotones, BorderLayout.SOUTH);

        dialogo.getRootPane().setDefaultButton(btns[1]); // Enter = CAMBIAR
        dialogo.getRootPane().registerKeyboardAction(e -> dialogo.dispose(),
                KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0),
                JComponent.WHEN_IN_FOCUSED_WINDOW);

        dialogo.setVisible(true);
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
