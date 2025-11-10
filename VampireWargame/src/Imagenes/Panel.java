package Imagenes;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.GridBagLayout;
import java.awt.Image;
import javax.swing.ImageIcon;
import javax.swing.JPanel;

/**
 *
 * @author marye
 */
public class Panel extends JPanel {
        private Image imagenFondo;

    public Panel(String rutaImagen) {
        imagenFondo = new ImageIcon(getClass().getResource(rutaImagen)).getImage();
        setLayout(new GridBagLayout());
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(imagenFondo, 0, 0, getWidth(), getHeight(), this);

        g.setColor(new Color(0, 0, 0, 0)); 
        g.fillRect(0, 0, getWidth(), getHeight());
    }
}

