package vampirewargame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.border.Border;

/**
 *
 * @author marye
 */
public class InterfazGame {

    private JButton[][] casillas;
    private Tablero tablero;
    private Piezas piezaSeleccionada = null;
    private int filaSeleccionada = -1;
    private int columnaSeleccionada = -1;

    public InterfazGame() {
        tablero = new Tablero();
        tablero.PosicionPiezas();
        initComponents();
        cargarImagen();
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
        casillas = new JButton[6][6];

        for (int fila = 0; fila < 6; fila++) {
            for (int columna = 0; columna < 6; columna++) {
                JButton boton = new JButton();
                boton.setPreferredSize(new Dimension(130, 130));
                boton.setFocusPainted(false);
                boton.setBorder(BorderFactory.createLineBorder(Color.BLACK));

                if ((fila + columna) % 2 == 0) {
                    boton.setBackground(new Color(200, 200, 200));
                } else {
                    boton.setBackground(new Color(100, 100, 100));
                }

                casillas[fila][columna] = boton;
                PTablero.add(boton);

                final int f = fila;
                final int c = columna;

                boton.addActionListener(e -> {
                    manejarClick(f, c);
                });
            }
        }

        PInterfaz.add(PTablero, BorderLayout.WEST);
        VInterfaz.add(PInterfaz);
        VInterfaz.setVisible(true);
    }

    private void manejarClick(int fila, int columna) {
        Piezas pieza = tablero.getPosicion(fila, columna);

        if (piezaSeleccionada == null) {
            if (pieza != null) {
                piezaSeleccionada = pieza;
                filaSeleccionada = fila;
                columnaSeleccionada = columna;
                marcarCasillasPosibles();
            }
        } else {
            piezaSeleccionada.movemiento(tablero, fila, columna);
            piezaSeleccionada = null;
            filaSeleccionada = -1;
            columnaSeleccionada = -1;
            limpiarColores();
            cargarImagen();
        }
    }

    private void marcarCasillasPosibles() {
        limpiarColores();

        casillas[filaSeleccionada][columnaSeleccionada].setBackground(new Color(200, 60, 60));

        for (int movFila = -1; movFila <= 1; movFila++) {
            for (int movColumna = -1; movColumna <= 1; movColumna++) {

                if (movFila == 0 && movColumna == 0) {
                    continue;
                }

                int nuevaFila = filaSeleccionada + movFila;
                int nuevaColumna = columnaSeleccionada + movColumna;

                if (nuevaFila >= 0 && nuevaFila < 6 && nuevaColumna >= 0 && nuevaColumna < 6) {
                    if (tablero.getPosicion(nuevaFila, nuevaColumna) == null) {
                        casillas[nuevaFila][nuevaColumna].setBackground(new Color(255, 120, 120));
                    }
                }
            }
        }
    }

    private void limpiarColores() {
        for (int fila = 0; fila < 6; fila++) {
            for (int columna = 0; columna < 6; columna++) {
                if ((fila + columna) % 2 == 0) {
                    casillas[fila][columna].setBackground(new Color(200, 200, 200));
                } else {
                    casillas[fila][columna].setBackground(new Color(100, 100, 100));
                }
            }
        }
    }

    private void cargarImagen() {
        for (int fila = 0; fila < 6; fila++) {
            for (int columna = 0; columna < 6; columna++) {

                Piezas posicion = tablero.getPosicion(fila, columna);

                if (posicion != null) {
                    ImageIcon icon = tablero.getPiezas(fila, columna);

                    int ancho = casillas[fila][columna].getWidth();
                    int alto = casillas[fila][columna].getHeight();

                    Image imagenEscalada = icon.getImage().getScaledInstance(ancho, alto, Image.SCALE_SMOOTH);
                    casillas[fila][columna].setIcon(new ImageIcon(imagenEscalada));

                } else {
                    casillas[fila][columna].setIcon(null);
                }

            }
        }
    }

    public static void main(String[] args) {
        new InterfazGame();
    }
}
