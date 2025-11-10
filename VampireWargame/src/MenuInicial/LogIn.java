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
public class LogIn {

    private JTextField txtUsername;
    private JPasswordField txtPassword;
    private AccountRegistry registro;
    private Account cuentaEncontrada;

    public LogIn(AccountRegistry registro) {
        this.registro = registro;
        initcomponent();
    }

    public void initcomponent() {
        JFrame VLogIn = new JFrame();
        VLogIn.setSize(1200, 800);
        VLogIn.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        VLogIn.setResizable(false);
        VLogIn.setLocationRelativeTo(null);
        VLogIn.setLayout(null);

        Panel PPrincipal = new Panel("/Imagenes/BgMenu.png");
        PPrincipal.setBounds(0, 0, 1186, 765);

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

        JLabel lblTitulo = new JLabel("Iniciar Sesión", SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 42));
        lblTitulo.setForeground(amarillo);

        JPanel PDatos = new JPanel(new GridLayout(2, 2, 50, 10));
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
        txtPassword.setForeground(amarillo);
        PDatos.add(txtPassword);

        JPanel PBotones = new JPanel(new GridLayout(1, 2, 10, 30));
        PBotones.setBackground(Color.WHITE);
        JButton BtnLogin = new JButton("INICIAR SESIÓN");
        JButton BtnSalir = new JButton("SALIR");
        JButton[] botones = {BtnLogin, BtnSalir};

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

        BtnLogin.addActionListener(e -> {
            ValidarLogIn();
            if (cuentaEncontrada != null) {
                JOptionPane.showMessageDialog(null, "Inicio de sesión exitoso");
                new MenuPrincipal(registro, cuentaEncontrada);
                VLogIn.dispose();
            }
        });

        BtnSalir.addActionListener(e -> {
            new MenuInicio(registro);
            VLogIn.dispose();
        });

        PCentral.add(lblTitulo, BorderLayout.NORTH);
        PCentral.add(PDatos, BorderLayout.CENTER);
        PCentral.add(PBotones, BorderLayout.SOUTH);
        VLogIn.add(PPrincipal);
        VLogIn.add(PCentral);
        VLogIn.getContentPane().setComponentZOrder(PCentral, 0);
        VLogIn.getContentPane().setComponentZOrder(PPrincipal, 1);

        VLogIn.validate();
        VLogIn.repaint();
        VLogIn.setVisible(true);
    }

    public void ValidarLogIn() {
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

        cuentaEncontrada = registro.buscarPlayer(username);

        if (cuentaEncontrada != null) {
            if (cuentaEncontrada.getPassword().equals(password)) {

            } else {
                JOptionPane.showMessageDialog(null, "Error: Contraseña incorrecta.");
                cuentaEncontrada = null;
            }
        }
    }
}
