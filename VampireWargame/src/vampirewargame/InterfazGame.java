package vampirewargame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 *
 * @author marye
 */
public class InterfazGame {

    private JButton[][] Tablero;
    private Tablero TableroLogico;

    public InterfazGame() {
        TableroLogico = new Tablero();
        TableroLogico.PosicionPiezas();
        initComponents();
        cargarImagenesEnBotones();
    }

    private void initComponents() {
        // Ventana de la interfaz del juego
        JFrame VInterfaz = new JFrame("Vampire Wargame");
        VInterfaz.setSize(1200, 800);
        VInterfaz.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        VInterfaz.setResizable(false);
        VInterfaz.setLayout(new BorderLayout());
        VInterfaz.setLocationRelativeTo(null);

        // Panel principal del interfaz del juego
        JPanel PInterfaz = new JPanel(new BorderLayout());

        // Panel del tablero
        JPanel PTablero = new JPanel(new GridLayout(6, 6, 3, 3));
        PTablero.setBackground(new Color(40, 40, 40));
        PTablero.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        Tablero = new JButton[6][6];

        for (int fila = 0; fila < 6; fila++) {
            for (int columna = 0; columna < 6; columna++) {
                JButton boton = new JButton();
                boton.setPreferredSize(new Dimension(130, 130));
                boton.setFocusPainted(false);
                boton.setBorder(BorderFactory.createLineBorder(Color.BLACK));
                Tablero[fila][columna] = boton;
                PTablero.add(boton);
            }
        }

        PInterfaz.add(PTablero, BorderLayout.WEST);
        VInterfaz.add(PInterfaz);
        VInterfaz.setVisible(true);
    }

    private void cargarImagenesEnBotones() {
        for (int fila = 0; fila < 6; fila++) {
            for (int columna = 0; columna < 6; columna++) {
                ImageIcon icon = TableroLogico.getPiezas(fila, columna);
                if (icon != null) {
                    int ancho = Tablero[fila][columna].getWidth();
                    int alto = Tablero[fila][columna].getHeight();

                    Image imagenEscalada = icon.getImage().getScaledInstance(ancho, alto, Image.SCALE_SMOOTH);
                    Tablero[fila][columna].setIcon(new ImageIcon(imagenEscalada));
                }
            }
        }
    }

    public static void main(String[] args) {
        new InterfazGame();
    }
}
