package MenuPrincipal;

import MenuInicial.Account;
import MenuInicial.AccountRegistry;
import javax.swing.*;
import javax.swing.table.JTableHeader;
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
        JFrame VReportes = new JFrame();
        VReportes.setSize(1200, 800);
        VReportes.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        VReportes.setResizable(false);
        VReportes.setLocationRelativeTo(null);

        Color amarillo = new Color(189, 100, 21);      

        JPanel PReportes = new JPanel(new BorderLayout(20, 20));
        PReportes.setBackground(new Color(245, 240, 230));
        VReportes.setContentPane(PReportes);

        JPanel PCentral = new JPanel(new BorderLayout(20, 20));
        PCentral.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(amarillo, 3, true),
                BorderFactory.createEmptyBorder(30, 40, 30, 40)
        ));
        PCentral.setBackground(new Color(245, 240, 230));
        PReportes.add(PCentral, BorderLayout.CENTER);

        JLabel lblTitulo = new JLabel("REPORTES", SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 42));
        lblTitulo.setForeground(amarillo);
        PCentral.add(lblTitulo, BorderLayout.NORTH);

        JTabbedPane pestañas = new JTabbedPane();
        pestañas.setFont(new Font("Segoe UI", Font.BOLD, 20));
        pestañas.setBackground(new Color(245, 240, 230));
        pestañas.setForeground(amarillo.darker());

        pestañas.addTab("Ranking Jugadores", crearTableroRanking(amarillo,  new Color(240, 180, 90), new Color(245, 240, 230)));
        pestañas.addTab("Logs del Jugador", crearTableroPartidas(amarillo,  new Color(240, 180, 90), new Color(245, 240, 230)));

        JButton BtnMenuPrincipal = new JButton("REGRESAR A MENU PRINCIPAL");
        BtnMenuPrincipal.setFont(new Font("Segoe UI", Font.BOLD, 22));
        BtnMenuPrincipal.setBackground(amarillo);
        BtnMenuPrincipal.setForeground(Color.WHITE);
        BtnMenuPrincipal.setPreferredSize(new Dimension(340, 50));
        BtnMenuPrincipal.setFocusPainted(false);
        BtnMenuPrincipal.setBorderPainted(true);
        BtnMenuPrincipal.setOpaque(true);
        BtnMenuPrincipal.setFocusable(false);
        BtnMenuPrincipal.setBorder(BorderFactory.createLineBorder(amarillo.darker(), 2));

        BtnMenuPrincipal.addActionListener(e -> {
            new MenuPrincipal(registro, player);
            VReportes.dispose();
        });

        JPanel PBoton = new JPanel();
        PBoton.setOpaque(false);
        PBoton.add(BtnMenuPrincipal);

        PCentral.add(pestañas, BorderLayout.CENTER);
        PCentral.add(PBoton, BorderLayout.SOUTH);

        VReportes.setVisible(true);
    }

    private JPanel crearTableroRanking(Color amarillo, Color amarilloClaro, Color fondoBeige) {
        JPanel PRanking = new JPanel(new BorderLayout());
        PRanking.setBackground(fondoBeige);

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

        tablaRanking.setSelectionBackground(amarilloClaro);
        tablaRanking.setSelectionForeground(Color.BLACK);
        tablaRanking.setGridColor(amarillo.brighter());
        tablaRanking.setShowGrid(false);
        tablaRanking.setBackground(Color.WHITE);
        tablaRanking.setForeground(Color.DARK_GRAY);

        JTableHeader header = tablaRanking.getTableHeader();
        header.setOpaque(true);
        header.setBackground(amarillo.darker());
        header.setForeground(Color.WHITE);

        JScrollPane scrollRanking = new JScrollPane(tablaRanking);
        scrollRanking.getViewport().setBackground(fondoBeige);
        PRanking.add(scrollRanking, BorderLayout.CENTER);

        return PRanking;
    }

    private JPanel crearTableroPartidas(Color amarillo, Color amarilloClaro, Color fondoBeige) {
        JPanel PPartidas = new JPanel(new BorderLayout());
        PPartidas.setBackground(fondoBeige);

        String[] columnasPartidas = {"Descripción", "Fecha"};
        if (player.getPartidas() == null || player.getPartidas().isEmpty()) {
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

            tablaPartidas.setSelectionBackground(amarilloClaro);
            tablaPartidas.setSelectionForeground(Color.BLACK);
            tablaPartidas.setShowGrid(false);
            tablaPartidas.setBackground(Color.WHITE);
            tablaPartidas.setForeground(Color.DARK_GRAY);

            JTableHeader header = tablaPartidas.getTableHeader();
            header.setOpaque(true);
            header.setBackground(amarillo.darker());
            header.setForeground(Color.WHITE);

            JScrollPane scrollLogs = new JScrollPane(tablaPartidas);
            scrollLogs.getViewport().setBackground(fondoBeige);
            PPartidas.add(scrollLogs, BorderLayout.CENTER);
        }
        return PPartidas;
    }
}
