package vampirewargame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 *
 * @author marye
 */
public class Ruleta extends JPanel implements ActionListener {

    private double angulo = 0;
    private double velocidad = 0;
    private Timer timer;
    private boolean girando = false;
    private final String[] piezas = {"Vampiro", "Hombre Lobo", "Muerte", "Vampiro", "Hombre Lobo", "Muerte"};
    private final Color[] colores = {Color.RED, Color.BLUE, Color.GRAY};
    private double anguloPorSector;

    public Ruleta() {
        setPreferredSize(new Dimension(300, 300));
        timer = new Timer(20, this);
        anguloPorSector = 360.0 / piezas.length;
    }

    public void girar() {
        if (!girando) {
            velocidad = 30 + Math.random() * 20;
            girando = true;
            timer.start();
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (girando) {
            angulo += Math.toRadians(velocidad);
            velocidad *= 0.97;
            if (velocidad < 0.5) {
                girando = false;
                timer.stop();
                mostrarResultado();
            }
            repaint();
        }
    }

    private void mostrarResultado() {

        double anguloFinal = (Math.toDegrees(angulo) % 360 + 360) % 360;

        double anguloPuntero = (anguloFinal + 90) % 360;

        int indice = (int) (anguloPuntero / anguloPorSector);
        String resultado = piezas[(piezas.length - indice - 1 + piezas.length) % piezas.length];
        
        JOptionPane.showMessageDialog(this,
                "🎯 Resultado: " + resultado,
                "Ruleta Detenida",
                JOptionPane.INFORMATION_MESSAGE);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        int x = getWidth() / 2;
        int y = getHeight() / 2;
        int radio = 120;

        // Dibujar sectores con texto
        for (int i = 0; i < piezas.length; i++) {
            g2.setColor(colores[i % colores.length]);
            int inicio = (int) Math.toDegrees(angulo + i * Math.toRadians(anguloPorSector));
            g2.fillArc(x - radio, y - radio, radio * 2, radio * 2, inicio, (int) anguloPorSector);

            // Dibujar texto dentro del sector
            g2.setColor(Color.WHITE);
            double anguloTexto = Math.toRadians(i * anguloPorSector) + Math.toRadians(anguloPorSector / 2);
            int tx = (int) (x + Math.cos(angulo + anguloTexto) * radio / 1.5);
            int ty = (int) (y + Math.sin(angulo + anguloTexto) * radio / 1.5);
            g2.setFont(new Font("Arial", Font.BOLD, 14));
            g2.drawString(piezas[i], tx - 30, ty);
        }

        // Círculo central
        g2.setColor(Color.BLACK);
        g2.fillOval(x - 15, y - 15, 30, 30);

        // Puntero superior
        g2.setColor(Color.RED);
        g2.fillPolygon(new int[]{x, x - 12, x + 12},
                new int[]{y - radio - 10, y - radio + 10, y - radio + 10}, 3);
    }

    public static void main(String[] args) {
        JFrame ventana = new JFrame("Ruleta de Personajes");
        Ruleta ruleta = new Ruleta();
        JButton boton = new JButton("Girar Ruleta");
        boton.addActionListener(e -> ruleta.girar());

        ventana.setLayout(new BorderLayout());
        ventana.add(ruleta, BorderLayout.CENTER);
        ventana.add(boton, BorderLayout.SOUTH);
        ventana.pack();
        ventana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        ventana.setVisible(true);
    }
}
