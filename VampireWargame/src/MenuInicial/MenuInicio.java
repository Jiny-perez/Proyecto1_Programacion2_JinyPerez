package MenuInicial;

import Imagenes.Panel;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 *
 * @author marye
 */
public class MenuInicio {

    private AccountRegistry registro;

    public MenuInicio(AccountRegistry registro) {
        this.registro = registro;
        inticomponent();
    }

    public void inticomponent() {
        JFrame VMenuInicio = new JFrame();
        VMenuInicio.setSize(1200, 800);
        VMenuInicio.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        VMenuInicio.setResizable(false);
        VMenuInicio.setLayout(new BorderLayout());
        VMenuInicio.setLocationRelativeTo(null);

        Panel PMenuInicio = new Panel("/Imagenes/BgMenuInicial.png");
        PMenuInicio.setLayout(new BorderLayout());

        JPanel PBotones = new JPanel(new GridLayout(3, 1, 0, 50));
        PBotones.setOpaque(false);
        PBotones.setBorder(BorderFactory.createEmptyBorder(350, 350, 100, 300));

        PBotones.setBackground(Color.WHITE);
        JButton BtnLogIn = new JButton("INICIAR SESIÓN");
        JButton BtnCrearPlayer = new JButton("CREAR CUENTA");
        JButton BtnSalir = new JButton("SALIR");
        JButton[] botones = {BtnLogIn, BtnCrearPlayer, BtnSalir};

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

        BtnLogIn.addActionListener(e -> {
            new LogIn(registro);
            VMenuInicio.dispose();
        });

        BtnCrearPlayer.addActionListener(e -> {
            new CrearPlayer(registro);
            VMenuInicio.dispose();
        });

        BtnSalir.addActionListener(e -> System.exit(0));

        PMenuInicio.add(PBotones, BorderLayout.CENTER);
        VMenuInicio.setContentPane(PMenuInicio);
        VMenuInicio.setVisible(true);
    }

    public static void main(String[] args) {
        AccountRegistry accRegGlobal = new AccountRegistry();
        new MenuInicio(accRegGlobal);
    }
}
