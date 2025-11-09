package vampirewargame;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.*;
import java.util.Random;

/**
 *
 * @author marye
 */
public class Ruleta extends JPanel {

    private final String[] tipoPiezas = {
        "Vampiro", "Hombre Lobo", "Muerte",
        "Vampiro", "Hombre Lobo", "Muerte"
    };

    private final int segmentos = tipoPiezas.length;
    private double angulo = 0.0;
    private double velocidad = 0.0;
    private boolean girando = false;
    private final Random rand = new Random();
    private Timer timer;

    public interface RuletaListener {
        void piezaSeleccionada(String tipo);
    }

    private RuletaListener listener;

    public void setRuletaListener(RuletaListener l) {
        this.listener = l;
    }

    public Ruleta() {
        setPreferredSize(new Dimension(300, 300));
        setBackground(new Color(28, 28, 28));
        setOpaque(true);

        // Timer
        timer = new Timer(16, e -> {
            if (Math.abs(velocidad) > 0.01) {
                angulo += velocidad;
                velocidad *= 0.9945;
                
                if (Math.abs(velocidad) < 0.1 && girando) {  
                    velocidad = 0;
                    girando = false;
                    int piezaSeleccionada = obtenerPiezaSeleccionada();
                    final String tipo = tipoPiezas[piezaSeleccionada];
                    repaint();   
                                  
                    SwingUtilities.invokeLater(() -> {
                        if (listener != null) {
                            listener.piezaSeleccionada(tipo);
                        }
                        JOptionPane.showMessageDialog(this, "Pieza: " + piezaSeleccionada, "", JOptionPane.INFORMATION_MESSAGE);
                    });
                }
                repaint();
            }
        });
        timer.setCoalesce(true);
        timer.start();
    }

    public JButton crearBotonGirar() {
        JButton btnGirar = new JButton("Girar Ruleta");
        btnGirar.addActionListener(a -> iniciarGiro());
        return btnGirar;
    }

    private void iniciarGiro() {
        if (girando) {
            return;
        }

        girando = true;
        double min = 0.20, max = 0.40;
        double signo = rand.nextBoolean() ? 1 : -1;
        velocidad = signo * (min + rand.nextDouble() * (max - min));
    }

    private int obtenerPiezaSeleccionada() {
        double a = angulo % (2 * Math.PI);
        if (a < 0) {
            a += 2 * Math.PI;
        }

        double flecha = -Math.PI / 2.0;
        double relativo = (flecha - a) % (2 * Math.PI);
        if (relativo < 0) {
            relativo += 2 * Math.PI;
        }

        double angSector = 2 * Math.PI / segmentos;
        int idx = (int) (relativo / angSector);
        return (idx % segmentos + segmentos) % segmentos;
    }

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        dibujarRuleta((Graphics2D) g);
    }

    private void dibujarRuleta(Graphics2D g2) {
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        int w = getWidth(), h = getHeight();
        int size = Math.max(10, Math.min(w, h) - 40);
        int cx = w / 2, cy = h / 2;
        int radio = size / 2;

        // Fondo
        g2.setColor(getBackground());
        g2.fillRect(0, 0, w, h);

        // Rueda rotada
        AffineTransform old = g2.getTransform();
        g2.translate(cx, cy);
        g2.rotate(angulo);

        double angSector = 2 * Math.PI / segmentos;
        Color[] paleta = {
            new Color(220, 84, 84),
            new Color(244, 170, 74),
            new Color(93, 169, 219),
            new Color(120, 200, 150)
        };

        for (int i = 0; i < segmentos; i++) {
            double a0 = i * angSector;
            double a1 = a0 + angSector;
            g2.setColor(paleta[i % paleta.length]);
            Path2D sector = new Path2D.Double();
            sector.moveTo(0, 0);
            sector.lineTo(Math.cos(a0) * radio, Math.sin(a0) * radio);
            for (int s = 1; s <= 8; s++) {
                double t = a0 + (a1 - a0) * s / 8.0;
                sector.lineTo(Math.cos(t) * radio, Math.sin(t) * radio);
            }
            sector.closePath();
            g2.fill(sector);

            g2.setColor(new Color(30, 30, 30));
            g2.setStroke(new BasicStroke(2f));
            g2.draw(sector);

            double amid = (a0 + a1) / 2.0;
            double tx = Math.cos(amid) * radio * 0.62;
            double ty = Math.sin(amid) * radio * 0.62;

            AffineTransform save = g2.getTransform();
            g2.translate(tx, ty);
            g2.setFont(new Font("SansSerif", Font.BOLD, (int) Math.max(12, radio * 0.08)));
            g2.setColor(Color.WHITE);
            String palabra = tipoPiezas[i];
            FontMetrics fm = g2.getFontMetrics();
            int tw = fm.stringWidth(palabra), th = fm.getAscent();
            g2.drawString(palabra, -tw / 2, th / 3);
            g2.setTransform(save);
        }

        g2.setTransform(old);

        // Borde exterior
        g2.setColor(new Color(50, 50, 50));
        g2.setStroke(new BasicStroke(10f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
        g2.drawOval(cx - radio - 6, cy - radio - 6, (radio + 6) * 2, (radio + 6) * 2);

        // punto central
        g2.setColor(new Color(245, 245, 245));
        g2.fillOval(cx - 34, cy - 34, 68, 68);
        g2.setColor(new Color(150, 150, 150));
        g2.setStroke(new BasicStroke(2f));
        g2.drawOval(cx - 34, cy - 34, 68, 68);

        dibujarFlecha(g2, cx, cy - radio - 18);
    }

    private void dibujarFlecha(Graphics2D g2, int x, int y) {
        int ancho = 40, altura = 30;
        int[] xPoints = {x, x - ancho / 2, x + ancho / 2};
        int[] yPoints = {y, y + altura, y + altura};

        g2.setColor(Color.WHITE);
        g2.fillPolygon(xPoints, yPoints, 3);
        g2.setColor(Color.BLACK);
        g2.drawPolygon(xPoints, yPoints, 3);
    }
}
