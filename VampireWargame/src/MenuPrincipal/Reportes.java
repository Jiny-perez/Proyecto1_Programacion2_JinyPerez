package MenuPrincipal;
import MenuInicial.Account;
import MenuInicial.AccountRegistry;
import javax.swing.*;
import java.awt.*;
import java.util.List;

/**
 *
 * @author marye
 */
public class Reportes {

    private Account player;
    private AccountRegistry registro;

    public Reportes(Account player, AccountRegistry registro) {
        this.player = player;
        this.registro = registro;
        initComponents();
    }

    private void initComponents() {
        // Ventana principal - Reportes
        JFrame VReportes = new JFrame("Reportes");
        VReportes.setSize(1200, 800);
        VReportes.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        VReportes.setResizable(false);
        VReportes.setLocationRelativeTo(null);
        VReportes.setLayout(new BorderLayout());

        // Colores base
        Color Azul = new Color(70, 130, 180);

        // Panel central
        JPanel PCentral = new JPanel(new BorderLayout(20, 20));
        PCentral.setBorder(BorderFactory.createEmptyBorder(30, 40, 30, 40));

        // Título
        JLabel lblTitulo = new JLabel("Reportes", SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 42));
        lblTitulo.setForeground(Azul);
        PCentral.add(lblTitulo, BorderLayout.NORTH);

         // Panel de pestañas
        JTabbedPane pestañas = new JTabbedPane();
        pestañas.setFont(new Font("Segoe UI", Font.BOLD, 20));

        pestañas.addTab("Ranking Jugadores", crearTableroRanking());
        pestañas.addTab("Logs del Jugador", crearTableroPartidas());
        
        // Botón para regresar al Menu Principal
        JButton BtnMenuPrincipal = new JButton("Volver a Menú Principal");
        BtnMenuPrincipal.setFont(new Font("Segoe UI", Font.BOLD, 22));
        BtnMenuPrincipal.setBackground(Azul);
        BtnMenuPrincipal.setForeground(Color.WHITE);
        BtnMenuPrincipal.setFocusPainted(false);
        BtnMenuPrincipal.setPreferredSize(new Dimension(300, 60));

        BtnMenuPrincipal.addActionListener(e -> {
            new MenuPrincipal(registro, player);
            VReportes.dispose();
        });

        JPanel PBoton = new JPanel();
        PBoton.add(BtnMenuPrincipal);

        PCentral.add(pestañas, BorderLayout.CENTER);
        PCentral.add(PBoton, BorderLayout.SOUTH);

        VReportes.add(PCentral, BorderLayout.CENTER);
        VReportes.setVisible(true);
    }
    
    private JPanel crearTableroRanking() {
        JPanel PRanking = new JPanel(new BorderLayout());
        PRanking.setBackground(Color.WHITE);

        String[] columnasRanking = {"Posición", "Jugador", "Puntos"};
        List<Account> rankingPlayers = registro.rankingPlayers();
        String[][] datosRankingPlayer = new String[rankingPlayers.size()][3];

        for (int i = 0; i < rankingPlayers.size(); i++) {
            Account acc = rankingPlayers.get(i);
            datosRankingPlayer[i][0] = String.valueOf(i + 1);
            datosRankingPlayer[i][1] = acc.getUsername();
            datosRankingPlayer[i][2] = String.valueOf(acc.getPuntosAcumulados());
        }

        JTable tablaRanking = new JTable(datosRankingPlayer, columnasRanking);
        tablaRanking.setFont(new Font("Segoe UI", Font.PLAIN, 18));
        tablaRanking.setRowHeight(30);
        tablaRanking.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 18));

        JScrollPane scrollRanking = new JScrollPane(tablaRanking);
        PRanking.add(scrollRanking, BorderLayout.CENTER);

        return PRanking;
    }


    private JPanel crearTableroPartidas() {
        JPanel PPartidas = new JPanel(new BorderLayout());
        PPartidas.setBackground(Color.WHITE);

        String[] columnasPartidas = {"Descripción", "Fecha"};

        if (player.getPartidas().isEmpty()) {
            JLabel sinPartidas = new JLabel("No hay registros de partidas aún.", SwingConstants.CENTER);
            sinPartidas.setFont(new Font("Segoe UI", Font.ITALIC, 22));
            sinPartidas.setForeground(Color.GRAY);
            PPartidas.add(sinPartidas, BorderLayout.CENTER);
        } else {
            String[][] datosPartidas = new String[player.getPartidas().size()][2];
            for (int i = 0; i < player.getPartidas().size(); i++) {
                datosPartidas[i][0] = player.getPartidas().get(i).getDescripcion();
                datosPartidas[i][1] = player.getPartidas().get(i).mostrarFecha();
            }

            JTable tablaPartidas = new JTable(datosPartidas, columnasPartidas);
            tablaPartidas.setFont(new Font("Segoe UI", Font.PLAIN, 18));
            tablaPartidas.setRowHeight(28);
            tablaPartidas.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 18));

            JScrollPane scrollLogs = new JScrollPane(tablaPartidas);
            PPartidas.add(scrollLogs, BorderLayout.CENTER);
        }

        return PPartidas;
    }
}
