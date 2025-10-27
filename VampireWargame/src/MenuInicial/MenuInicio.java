package MenuInicial;

import javax.swing.*;
import java.awt.*;

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
        JFrame VMenuInicio = new JFrame("Menu Inicial");
        VMenuInicio.setSize(1200, 800);
        VMenuInicio.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        VMenuInicio.setResizable(false);
        VMenuInicio.setLayout(new BorderLayout());
        VMenuInicio.setLocationRelativeTo(null);

        JPanel PMenuInicio = new JPanel(new BorderLayout());

        JPanel PBotones = new JPanel(new GridLayout(3, 1, 0, 50));
        PBotones.setBorder(BorderFactory.createEmptyBorder(250, 250, 50, 250));

        JButton BtnLogIn = new JButton("INICIAR SESIÓN");
        JButton BtnCrearPlayer = new JButton("CREAR JUGADOR");
        JButton BtnSalir = new JButton("SALIR");

        JButton[] botones = {BtnLogIn, BtnCrearPlayer, BtnSalir};

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
        VMenuInicio.add(PMenuInicio);
        VMenuInicio.setVisible(true);
    }

    public static void main(String[] args) {
        AccountRegistry accRegGlobal = new AccountRegistry();
        new MenuInicio(accRegGlobal);
    }
}

