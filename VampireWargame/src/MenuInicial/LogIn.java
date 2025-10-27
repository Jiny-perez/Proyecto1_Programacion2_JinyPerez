package MenuInicial;
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
        //Ventana principal -  Log In
        JFrame VLogIn = new JFrame("Menu Inicial");
        VLogIn.setSize(1200, 800);
        VLogIn.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        VLogIn.setResizable(false);
        VLogIn.setLocationRelativeTo(null);

        //Panel principal 
        JPanel PLogIn = new JPanel(new GridBagLayout());

        //Color base
        Color Azul = new Color(70, 130, 180);

        //Panel Central -  Contenido: Titulo
        JPanel PCentral = new JPanel(new BorderLayout(0, 40));
        PCentral.setBackground(Color.WHITE);
        PCentral.setPreferredSize(new Dimension(600, 500));
        PCentral.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Azul, 5),
                BorderFactory.createEmptyBorder(40, 60, 40, 60)
        ));

        JLabel lblTitulo = new JLabel("Iniciar Sesión", SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 42));
        lblTitulo.setForeground(Azul);

        //Panel Datos -  Contenido: Datos del usuario
        JPanel PDatos = new JPanel(new GridLayout(2, 2, 5, 40));
        PDatos.setBackground(Color.WHITE);

        JLabel lblUsername = new JLabel("USUARIO:");
        lblUsername.setFont(new Font("Segoe UI", Font.BOLD, 24));
        lblUsername.setForeground(Azul);
        PDatos.add(lblUsername);

        txtUsername = new JTextField();
        txtUsername.setFont(new Font("Segoe UI", Font.BOLD, 20));
        txtUsername.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Azul, 3),
                BorderFactory.createEmptyBorder(5, 10, 5, 10)
        ));
        txtUsername.setForeground(new Color(60, 70, 100));
        PDatos.add(txtUsername);

        JLabel lblPassword = new JLabel("CONTRASEÑA:");
        lblPassword.setFont(new Font("Segoe UI", Font.BOLD, 24));
        lblPassword.setForeground(Azul);
        PDatos.add(lblPassword);

        txtPassword = new JPasswordField();
        txtPassword.setFont(new Font("Segoe UI", Font.PLAIN, 20));
        txtPassword.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Azul, 3),
                BorderFactory.createEmptyBorder(5, 10, 5, 10)
        ));
        PDatos.add(txtPassword);

        //Panel de Botones 
        JPanel PBotones = new JPanel(new FlowLayout(FlowLayout.CENTER, 30, 30));
        PBotones.setBackground(Color.WHITE);

        JButton BtnLogin = new JButton("Iniciar Sesión");
        JButton BtnSalir = new JButton("Salir");

        JButton[] botones = {BtnLogin, BtnSalir};

        for (JButton b : botones) {
            b.setFont(new Font("Segoe UI", Font.BOLD, 30));
            b.setBackground(Azul);
            b.setForeground(Color.WHITE);
            b.setFocusPainted(false);
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

        PLogIn.add(PCentral);
        VLogIn.add(PLogIn);
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
