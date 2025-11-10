package vampirewargame;

import javax.swing.*;
import java.awt.*;

/**
 *
 * @author marye
 */
public class InterfazJuego {

    private JButton[][] casillas;
    private Tablero tablero;
    private LogicaJuego logica;
    private Piezas piezaSeleccionada = null;
    private int filaSeleccionada = -1;
    private int columnaSeleccionada = -1;
    private Color PSeleccionada = new Color(200, 60, 60);
    private Color movimientoValido = new Color(255, 120, 120);

    private JFrame VInterfaz;
    private JLabel Estado;

    private JRadioButton rbMover;
    private JRadioButton rbAtaque;
    private JRadioButton rbEspecial;

    private JPanel panelOpcEspecialMuerte;
    private JRadioButton rbLanzarLanza;
    private JRadioButton rbConjurarZombie;
    private JRadioButton rbAtacarZombie;
    private ButtonGroup grupoOpcMuerte;

    private boolean esperandoEnemigo = false;
    private int filaZombieSeleccionado = -1;
    private int colZombieSeleccionado = -1;

    private boolean ruletaGiradoThisTurn = false;
    private JButton botonGirarRuleta = null;
    private int intentosRuletaRestantes = 1;

    private MenuInicial.Account jugadorCuenta;   
    private MenuInicial.Account oponenteCuenta;

    public InterfazJuego(MenuInicial.Account jugadorCuenta, MenuInicial.Account oponenteCuenta) {
        this.jugadorCuenta = jugadorCuenta;
        this.oponenteCuenta = oponenteCuenta;
        tablero = new Tablero(6, 6);

        tablero.colocarPieza(new HombreLobo(2), 0, 0);
        tablero.colocarPieza(new Vampiro(2), 0, 1);
        tablero.colocarPieza(new Muerte(2), 0, 2);
        tablero.colocarPieza(new Muerte(2), 0, 3);
        tablero.colocarPieza(new Vampiro(2), 0, 4);
        tablero.colocarPieza(new HombreLobo(2), 0, 5);
        tablero.colocarPieza(new HombreLobo(1), 5, 0);
        tablero.colocarPieza(new Vampiro(1), 5, 1);
        tablero.colocarPieza(new Muerte(1), 5, 2);
        tablero.colocarPieza(new Muerte(1), 5, 3);
        tablero.colocarPieza(new Vampiro(1), 5, 4);
        tablero.colocarPieza(new HombreLobo(1), 5, 5);

        logica = new LogicaJuego(tablero);
        casillas = new JButton[6][6];

        try {
            if (Estado != null && jugadorCuenta != null) {
                Estado.setText("Jugador actual: " + jugadorCuenta.getUsername());
            }
        } catch (Exception ignored) {
        }

        initComponents();
    }

    public void initComponents() {
        //Ventana de la Interfaz
        VInterfaz = new JFrame("Vampire Wargame");
        VInterfaz.setSize(1200, 800);
        VInterfaz.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        VInterfaz.setResizable(false);
        VInterfaz.setLayout(new BorderLayout());
        VInterfaz.setLocationRelativeTo(null);
        JPanel panelPrincipal = new JPanel(new BorderLayout());
        panelPrincipal.setBackground(new Color(35, 35, 35));
        panelPrincipal.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Panel del Tablero
        JPanel PTablero = new JPanel(new GridLayout(6, 6, 3, 3));
        PTablero.setBackground(new Color(40, 40, 40));
        PTablero.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        for (int fila = 0; fila < 6; fila++) {
            for (int columna = 0; columna < 6; columna++) {

                JButton boton = new JButton();
                boton.setPreferredSize(new Dimension(130, 130));
                boton.setFocusPainted(false);
                boton.setBorderPainted(false);
                boton.setOpaque(true);

                if ((fila + columna) % 2 == 0) {
                    boton.setBackground(new Color(200, 200, 200));
                } else {
                    boton.setBackground(new Color(100, 100, 100));
                }

                boton.setHorizontalAlignment(SwingConstants.CENTER);
                boton.setVerticalAlignment(SwingConstants.CENTER);

                final int f = fila;
                final int c = columna;

                boton.addActionListener(e -> manejarMovimiento(f, c));
                casillas[fila][columna] = boton;
                PTablero.add(boton);
            }
        }

        // Panel derecho
        JPanel PDerecho = new JPanel();
        PDerecho.setPreferredSize(new Dimension(360, 0));
        PDerecho.setLayout(new BoxLayout(PDerecho, BoxLayout.Y_AXIS));
        PDerecho.setBackground(new Color(50, 50, 50));
        PDerecho.setBorder(BorderFactory.createEmptyBorder(12, 12, 12, 12));

        // Estado 
        Estado = new JLabel();
        Estado.setForeground(Color.WHITE);
        Estado.setFont(new Font("SansSerif", Font.BOLD, 20));
        Estado.setAlignmentX(Component.CENTER_ALIGNMENT);
        actualizarEstado();
        PDerecho.add(Box.createRigidArea(new Dimension(0, 10)));
        PDerecho.add(Estado);
        PDerecho.add(Box.createRigidArea(new Dimension(0, 25)));

        //Panel centrado, lado derecho
        JPanel PCentro = new JPanel();
        PCentro.setLayout(new BoxLayout(PCentro, BoxLayout.X_AXIS));
        PCentro.setOpaque(false);
        PCentro.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Panel Acciones 
        String[] Acciones = {"Mover", "Ataque normal", "Ataque especial"};
        JRadioButton[] rbAcciones = new JRadioButton[Acciones.length];
        ButtonGroup grupo = new ButtonGroup();

        JPanel panelAcciones = new JPanel();
        panelAcciones.setLayout(new BoxLayout(panelAcciones, BoxLayout.Y_AXIS));
        panelAcciones.setBackground(new Color(60, 60, 60));
        panelAcciones.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(Color.DARK_GRAY), " "));
        panelAcciones.setPreferredSize(new Dimension(160, 120));
        panelAcciones.setMaximumSize(new Dimension(160, 200));

        for (int i = 0; i < Acciones.length; i++) {
            if (i > 0) {
                panelAcciones.add(Box.createRigidArea(new Dimension(0, 5)));
            }
            JRadioButton rb = new JRadioButton(Acciones[i]);
            rb.setOpaque(false);
            rb.setForeground(Color.WHITE);
            if (i == 0) {
                rb.setSelected(true);
            }
            grupo.add(rb);
            panelAcciones.add(rb);
            rbAcciones[i] = rb;
            rb.addActionListener(e -> {
                cancelarEspecialMuerte();
                actualizarVistaTrasCambio();
            });
        }
        rbMover = rbAcciones[0];
        rbAtaque = rbAcciones[1];
        rbEspecial = rbAcciones[2];

        panelOpcEspecialMuerte = new JPanel();
        panelOpcEspecialMuerte.setLayout(new BoxLayout(panelOpcEspecialMuerte, BoxLayout.Y_AXIS));
        panelOpcEspecialMuerte.setBackground(new Color(65, 65, 65));
        panelOpcEspecialMuerte.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(Color.DARK_GRAY), " "));
        panelOpcEspecialMuerte.setPreferredSize(new Dimension(140, 120));
        panelOpcEspecialMuerte.setMaximumSize(new Dimension(140, 200));
        panelOpcEspecialMuerte.setVisible(false);

        String[] AEMuerte = {"Lanzar Lanza", "Conjurar Zombie", "Atacar Zombie"};
        JRadioButton[] rbsMuerte = new JRadioButton[AEMuerte.length];
        grupoOpcMuerte = new ButtonGroup();
        for (int i = 0; i < AEMuerte.length; i++) {
            if (i > 0) {
                panelOpcEspecialMuerte.add(Box.createRigidArea(new Dimension(0, 6)));
            }
            JRadioButton rb = new JRadioButton(AEMuerte[i]);
            rb.setOpaque(false);
            rb.setForeground(Color.WHITE);
            if (i == 0) {
                rb.setSelected(true);
            }
            grupoOpcMuerte.add(rb);
            panelOpcEspecialMuerte.add(rb);
            rbsMuerte[i] = rb;
            rb.addActionListener(e -> {
                cancelarEspecialMuerte();
                marcarCasillas();
            });
        }
        rbLanzarLanza = rbsMuerte[0];
        rbConjurarZombie = rbsMuerte[1];
        rbAtacarZombie = rbsMuerte[2];

        PCentro.add(Box.createHorizontalGlue());
        PCentro.add(panelAcciones);
        PCentro.add(Box.createRigidArea(new Dimension(12, 0)));
        PCentro.add(panelOpcEspecialMuerte);
        PCentro.add(Box.createHorizontalGlue());

        PDerecho.add(PCentro);
        PDerecho.add(Box.createRigidArea(new Dimension(0, 25)));

        Ruleta ruleta = new Ruleta();
        botonGirarRuleta = ruleta.crearBotonGirar();

        try {
            ruleta.setRuletaListener(tipo -> {
                SwingUtilities.invokeLater(() -> {
                    int jugadorActual;
                    try {
                        jugadorActual = logica.getJugadorActual();
                    } catch (Exception ex) {
                        jugadorActual = -1;
                    }

                    if (ruletaGiradoThisTurn) {
                        return;
                    }

                    boolean tieneTipo = false;
                    for (int f = 0; f < 6 && !tieneTipo; f++) {
                        for (int c = 0; c < 6; c++) {
                            Piezas p = tablero.get(f, c);
                            if (p == null) {
                                continue;
                            }
                            try {
                                if (p.getJugador() == jugadorActual) {
                                    String tipoP = p.getTipoPieza();
                                    if (tipoP == null || tipoP.trim().isEmpty()) {
                                        tipoP = p.getClass().getSimpleName();
                                    }
                                    if (tipoP.equalsIgnoreCase(tipo.trim())) {
                                        tieneTipo = true;
                                        break;
                                    }
                                }
                            } catch (Exception ignored) {
                            }
                        }
                    }

                    if (tieneTipo) {
                        logica.setPiezaPermitida(tipo, jugadorActual);
                        ruletaGiradoThisTurn = true;
                        if (botonGirarRuleta != null) {
                            botonGirarRuleta.setEnabled(false);
                        }
                        Estado.setText("Jugador actual: " + jugadorActual);
                        marcarCasillas();
                        return;
                    } else {
                        intentosRuletaRestantes = Math.max(0, intentosRuletaRestantes - 1);
                        if (intentosRuletaRestantes > 0) {
                            Estado.setText("No obtuviste una ficha que poseas. Te quedan " + intentosRuletaRestantes + " intento(s).");
                            if (botonGirarRuleta != null) {
                                botonGirarRuleta.setEnabled(true);
                            }
                            return;
                        } else {
                            Estado.setText("No obtuviste ficha válida tras intentos. Pierdes el turno.");
                            if (botonGirarRuleta != null) {
                                botonGirarRuleta.setEnabled(false);
                            }
                            cambiarTurno();
                            return;
                        }
                    }
                });
            });
        } catch (Exception ignored) {
        }

        ruleta.setPreferredSize(new Dimension(300, 300));
        ruleta.setMaximumSize(new Dimension(320, 320));
        JPanel PRuleta = new JPanel(new BorderLayout());
        PRuleta.setOpaque(true);
        PRuleta.setBackground(new Color(55, 55, 55));
        PRuleta.setPreferredSize(new Dimension(320, 360));
        PRuleta.setMaximumSize(new Dimension(340, 380));
        PRuleta.add(ruleta, BorderLayout.CENTER);
        PRuleta.add(botonGirarRuleta, BorderLayout.SOUTH);

        JPanel PRuletaCentrada = new JPanel();
        PRuletaCentrada.setLayout(new BoxLayout(PRuletaCentrada, BoxLayout.X_AXIS));
        PRuletaCentrada.setOpaque(false);
        PRuletaCentrada.add(Box.createHorizontalGlue());
        PRuletaCentrada.add(PRuleta);
        PRuletaCentrada.add(Box.createHorizontalGlue());

        PDerecho.add(PRuletaCentrada);
        PDerecho.add(Box.createVerticalGlue());

        panelPrincipal.add(PTablero, BorderLayout.WEST);
        panelPrincipal.add(PDerecho, BorderLayout.EAST);
        VInterfaz.getContentPane().add(panelPrincipal, BorderLayout.CENTER);

        VInterfaz.pack();
        VInterfaz.setLocationRelativeTo(null);
        VInterfaz.setVisible(true);

        cargarImagen();
    }

    private void cancelarEspecialMuerte() {
        if (!esperandoEnemigo) {
            return;
        }
        resetEsperandoEnemigo();
        Estado.setText("Jugador actual: " + String.valueOf(logica.getJugadorActual()));
        marcarCasillas();
    }

    private void actualizarVistaTrasCambio() {
        actualizarControlesSegunSeleccion();
        if (piezaSeleccionada != null) {
            panelOpcEspecialMuerte.setVisible(piezaSeleccionada instanceof Muerte && rbEspecial.isSelected());
            marcarCasillas();
        }
        VInterfaz.revalidate();
    }

    private void updateMuertePanelVisibility() {
        panelOpcEspecialMuerte.setVisible(piezaSeleccionada instanceof Muerte && rbEspecial.isSelected());
        VInterfaz.revalidate();
    }

    private void manejarMovimiento(int fila, int columna) {
        if (esperandoEnemigo) {

            if (!tablero.estaDentro(fila, columna)) {
                resetEsperandoEnemigo();
                marcarCasillas();
                return;
            }

            Piezas objetivo = tablero.get(fila, columna);
            Piezas zombie = tablero.get(filaZombieSeleccionado, colZombieSeleccionado);

            if (zombie == null || !(zombie instanceof Zombie)) {
                resetEsperandoEnemigo();
                marcarCasillas();
                return;
            }

            if (objetivo != null && esEnemigo(objetivo, zombie)) {
                if (tablero.esAdyacente(filaZombieSeleccionado, colZombieSeleccionado, fila, columna)) {
                    atacarYEliminarSiMuerto(zombie, objetivo, fila, columna);
                    resetEsperandoEnemigo();
                    cambiarTurno();
                    return;
                }
            }
            resetEsperandoEnemigo();
            marcarCasillas();
            return;
        }

        Piezas piezaEnCasilla = tablero.get(fila, columna);

        if (piezaSeleccionada == null) {
            seleccionarSiPropia(piezaEnCasilla, fila, columna);
            return;
        }

        if (piezaEnCasilla != null) {
            try {
                if (piezaEnCasilla.getJugador() == piezaSeleccionada.getJugador()) {

                    if (piezaSeleccionada instanceof Muerte && rbEspecial.isSelected() && rbAtacarZombie.isSelected()
                            && piezaEnCasilla instanceof Zombie) {

                        filaZombieSeleccionado = fila;
                        colZombieSeleccionado = columna;
                        marcarEnemigosCercanosDelZombie(filaZombieSeleccionado, colZombieSeleccionado);

                        if (!hayEnemigosAdyacentes(filaZombieSeleccionado, colZombieSeleccionado)) {
                            esperandoEnemigo = false;
                            filaZombieSeleccionado = -1;
                            colZombieSeleccionado = -1;
                            SwingUtilities.invokeLater(this::marcarCasillas);
                            return;
                        }
                        esperandoEnemigo = true;
                        return;
                    } else {
                        seleccionarSiPropia(piezaEnCasilla, fila, columna);
                        return;
                    }
                }
            } catch (Exception ignored) {
            }
        }

        boolean exito = false;
        if (rbMover.isSelected()) {
            exito = intentarMover(fila, columna);
        } else if (rbAtaque.isSelected()) {
            exito = intentarAtacar(fila, columna);
        } else if (rbEspecial.isSelected()) {
            exito = intentarEspecial(fila, columna);
        }

        if (exito) {
            cambiarTurno();
        } else {
            marcarCasillas();
        }
    }

    private void seleccionarSiPropia(Piezas pieza, int fila, int columna) {
        if (pieza == null) {
            return;
        }
        try {
            if (pieza.getJugador() != logica.getJugadorActual()) {
                return;
            }
        } catch (Exception ignored) {
        }

        if (pieza instanceof Zombie) {
            return;
        }

        try {
            int jugadorActual = logica.getJugadorActual();
            String restr = logica.getPiezaPermitida();
            if (!ruletaGiradoThisTurn || restr == null) {
                Estado.setText("Gira la ruleta antes de jugar ");
                SwingUtilities.invokeLater(this::marcarCasillas);
                return;
            }
            int restrJugador = logica.getPiezaPermitidaJugador();
            if (restr != null && restrJugador == jugadorActual) {
                String tipoP = pieza.getTipoPieza();
                if (tipoP == null || tipoP.trim().isEmpty()) {
                    tipoP = pieza.getClass().getSimpleName();
                }
                if (!tipoP.equalsIgnoreCase(restr.trim())) {
                    Estado.setText("Jugador actual: " + jugadorActual);
                    SwingUtilities.invokeLater(this::marcarCasillas);
                    return;
                }
            }
        } catch (Exception ignored) {
            Estado.setText("Gira la ruleta antes de jugar");
            return;
        }

        piezaSeleccionada = pieza;
        filaSeleccionada = fila;
        columnaSeleccionada = columna;
        updateMuertePanelVisibility();
        actualizarControlesSegunSeleccion();
        marcarCasillas();
    }

    private void cambiarTurno() {
        piezaSeleccionada = null;
        filaSeleccionada = -1;
        columnaSeleccionada = -1;
        panelOpcEspecialMuerte.setVisible(false);
        limpiarCasillas();
        cargarImagen();
        try {
            int actual = logica.getJugadorActual();
            logica.setJugadorActual(actual == 1 ? 2 : 1);
        } catch (Exception ignored) {
        }

        try {
            logica.limpiarPieza();
        } catch (Exception ignored) {
        }
        ruletaGiradoThisTurn = false;
        if (botonGirarRuleta != null) {
            botonGirarRuleta.setEnabled(true);
        }

        try {
            int jugador = logica.getJugadorActual();
            intentosRuletaRestantes = logica.calcularIntentosExtra(jugador);
        } catch (Exception ignored) {
            intentosRuletaRestantes = 1;
        }

        waitingResetEstado();
        actualizarEstado();
    }

    private void waitingResetEstado() {
        esperandoEnemigo = false;
        filaZombieSeleccionado = -1;
        colZombieSeleccionado = -1;
    }

    private boolean intentarMover(int filaDestino, int columnaDestino) {
        if (!tablero.estaDentro(filaDestino, columnaDestino)) {
            return false;
        }

        if (piezaSeleccionada instanceof Zombie) {
            return false;
        }

        if (!tablero.esAdyacente(filaSeleccionada, columnaSeleccionada, filaDestino, columnaDestino)) {
            return false;
        }

        if (!tablero.estaVacia(filaDestino, columnaDestino)) {
            Estado.setText("Movimiento inválido: la casilla de destino está ocupada.");
            return false;
        }

        try {
            int jugadorActual = logica.getJugadorActual();
            String restr = logica.getPiezaPermitida();
            int restrJugador = logica.getPiezaPermitidaJugador();

            if (!ruletaGiradoThisTurn || restr == null || restrJugador != jugadorActual) {
                return false;
            }

            String tipoP = piezaSeleccionada.getTipoPieza();
            if (tipoP == null || tipoP.trim().isEmpty()) {
                tipoP = piezaSeleccionada.getClass().getSimpleName();
            }

            if (!tipoP.equalsIgnoreCase(restr.trim())) {
                Estado.setText("Solo puedes mover piezas: " + restr);
                return false;
            }

        } catch (Exception ignored) {
            Estado.setText("Gira la ruleta antes de mover.");
            return false;
        }

        try {
            tablero.eliminarPieza(filaSeleccionada, columnaSeleccionada);
            tablero.colocarPieza(piezaSeleccionada, filaDestino, columnaDestino);
            cargarImagen();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    private boolean intentarAtacar(int filaDestino, int columnaDestino) {
        if (!tablero.estaDentro(filaDestino, columnaDestino)) {
            return false;
        }

        if (piezaSeleccionada instanceof Zombie) {
            return false;
        }

        if (!tablero.esAdyacente(filaSeleccionada, columnaSeleccionada, filaDestino, columnaDestino)) {
            return false;
        }

        Piezas objetivo = tablero.get(filaDestino, columnaDestino);
        if (objetivo == null) {
            return false;
        }

        try {
            if (objetivo.getJugador() == piezaSeleccionada.getJugador()) {
                return false;
            }
        } catch (Exception ignored) {
            return false;
        }

        try {
            int jugadorActual = logica.getJugadorActual();
            String restr = logica.getPiezaPermitida();
            int restrJugador = logica.getPiezaPermitidaJugador();
            if (!ruletaGiradoThisTurn || restr == null || restrJugador != jugadorActual) {
                return false;
            }
            String tipoP = piezaSeleccionada.getTipoPieza();
            if (tipoP == null || tipoP.trim().isEmpty()) {
                tipoP = piezaSeleccionada.getClass().getSimpleName();
            }
            if (!tipoP.equalsIgnoreCase(restr.trim())) {
                return false;
            }
        } catch (Exception ignored) {
            return false;
        }

        try {
            piezaSeleccionada.atacar(objetivo);
            if (!objetivo.estaVivo()) {
                try {
                    logica.registrarPerdida(objetivo);
                } catch (Exception ignored) {
                }
                tablero.eliminarPieza(filaDestino, columnaDestino);
            }
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    private boolean intentarEspecial(int filaDestino, int columnaDestino) {
        if (!tablero.estaDentro(filaDestino, columnaDestino)) {
            return false;
        }

        try {
            int jugadorActual = logica.getJugadorActual();
            String restr = logica.getPiezaPermitida();
            int restrJugador = logica.getPiezaPermitidaJugador();
            if (!ruletaGiradoThisTurn || restr == null || restrJugador != jugadorActual) {
                return false;
            }
            String tipoP = piezaSeleccionada.getTipoPieza();
            if (tipoP == null || tipoP.trim().isEmpty()) {
                tipoP = piezaSeleccionada.getClass().getSimpleName();
            }
            if (!tipoP.equalsIgnoreCase(restr.trim())) {
                return false;
            }
        } catch (Exception ignored) {
            return false;
        }

        if (piezaSeleccionada instanceof HombreLobo) {
            int opcion = tablero.validarMovimientoHombreLobo(filaSeleccionada, columnaSeleccionada, filaDestino, columnaDestino);
            if (opcion == 0) {
                return false;
            }
            try {
                piezaSeleccionada.ataqueEspecial(opcion, tablero, filaDestino, columnaDestino);
                Piezas p = tablero.get(filaDestino, columnaDestino);
                if (p != null && !p.estaVivo()) {
                    tablero.eliminarPieza(filaDestino, columnaDestino);
                }
                return true;
            } catch (Exception e) {
                return false;
            }
        }

        if (piezaSeleccionada instanceof HombreLobo) {
            int opcion = tablero.validarMovimientoHombreLobo(filaSeleccionada, columnaSeleccionada, filaDestino, columnaDestino);
            if (opcion == 0) {
                return false;
            }
            try {
                piezaSeleccionada.ataqueEspecial(opcion, tablero, filaDestino, columnaDestino);
                Piezas p = tablero.get(filaDestino, columnaDestino);
                if (p != null && !p.estaVivo()) {
                    tablero.eliminarPieza(filaDestino, columnaDestino);
                }
                return true;
            } catch (Exception e) {
                return false;
            }
        }

        if (piezaSeleccionada instanceof Vampiro) {
            if (!tablero.esAdyacente(filaSeleccionada, columnaSeleccionada, filaDestino, columnaDestino)) {
                return false;
            }

            Piezas objetivo = tablero.get(filaDestino, columnaDestino);

            if (objetivo == null) {
                return false;
            }
            try {
                if (objetivo.getJugador() == piezaSeleccionada.getJugador()) {
                    return false;
                }
            } catch (Exception ignored) {
                return false;
            }
            try {
                piezaSeleccionada.ataqueEspecial(1, tablero, filaDestino, columnaDestino);
                if (!objetivo.estaVivo()) {
                    tablero.eliminarPieza(filaDestino, columnaDestino);
                }
                return true;
            } catch (Exception e) {
                return false;
            }
        }

        if (piezaSeleccionada instanceof Muerte) {
            int opcion = rbConjurarZombie.isSelected() ? 2 : rbAtacarZombie.isSelected() ? 3 : 1;

            try {
                if (opcion == 2) {
                    if (!tablero.estaVacia(filaDestino, columnaDestino)) {
                        return false;
                    }
                    piezaSeleccionada.ataqueEspecial(opcion, tablero, filaDestino, columnaDestino);
                    return true;
                }

                if (opcion == 3) {
                    Piezas p = tablero.get(filaDestino, columnaDestino);
                    if (p == null || !(p instanceof Zombie) || p.getJugador() != piezaSeleccionada.getJugador()) {
                        return false;
                    }
                    piezaSeleccionada.ataqueEspecial(opcion, tablero, filaDestino, columnaDestino);
                    Piezas posible = tablero.get(filaDestino, columnaDestino);
                    if (posible != null && !posible.estaVivo()) {
                        tablero.eliminarPieza(filaDestino, columnaDestino);
                    }
                    return true;
                }

                if (!tablero.esDistanciaExacta(filaSeleccionada, columnaSeleccionada, filaDestino, columnaDestino, 2)) {
                    return false;
                }

                Piezas objetivo = tablero.get(filaDestino, columnaDestino);
                if (objetivo == null) {
                    return false;
                }
                try {
                    if (objetivo.getJugador() == piezaSeleccionada.getJugador()) {
                        return false;
                    }
                } catch (Exception ignored) {
                    return false;
                }

                piezaSeleccionada.ataqueEspecial(1, tablero, filaDestino, columnaDestino);
                if (!objetivo.estaVivo()) {
                    tablero.eliminarPieza(filaDestino, columnaDestino);
                }
                return true;
            } catch (Exception e) {
                return false;
            }
        }

        if (!tablero.esAdyacente(filaSeleccionada, columnaSeleccionada, filaDestino, columnaDestino)) {
            return false;
        }
        try {
            piezaSeleccionada.ataqueEspecial(1, tablero, filaDestino, columnaDestino);
            Piezas p = tablero.get(filaDestino, columnaDestino);
            if (p != null && !p.estaVivo()) {
                tablero.eliminarPieza(filaDestino, columnaDestino);
            }
            return true;
        } catch (Exception e) {
            return false;
        }

    }

    private void marcarCasillas() {
        limpiarCasillas();

        try {
            int jugadorActual = logica.getJugadorActual();
            String restr = logica.getPiezaPermitida();
            int restrJugador = logica.getPiezaPermitidaJugador();
            if (piezaSeleccionada == null && ruletaGiradoThisTurn && restr != null && restrJugador == jugadorActual) {
                for (int f = 0; f < 6; f++) {
                    for (int c = 0; c < 6; c++) {
                        Piezas p = tablero.get(f, c);
                        if (p != null && p.getJugador() == jugadorActual) {
                            String tipoP = p.getTipoPieza();
                            if (tipoP == null || tipoP.trim().isEmpty()) {
                                tipoP = p.getClass().getSimpleName();
                            }
                            if (tipoP.equalsIgnoreCase(restr.trim())) {
                                casillas[f][c].setBackground(movimientoValido);
                            }
                        }
                    }
                }
                return;
            }
        } catch (Exception ignored) {
        }

        if (filaSeleccionada < 0 || columnaSeleccionada < 0) {
            return;
        }

        casillas[filaSeleccionada][columnaSeleccionada].setBackground(PSeleccionada);

        //MOVER
        if (rbMover.isSelected()) {
            for (int dr = -1; dr <= 1; dr++) {
                for (int dc = -1; dc <= 1; dc++) {
                    if (dr == 0 && dc == 0) {
                        continue;
                    }

                    int nf = filaSeleccionada + dr;
                    int nc = columnaSeleccionada + dc;

                    if (!tablero.estaDentro(nf, nc)) {
                        continue;
                    }

                    if (tablero.estaVacia(nf, nc) && tablero.esAdyacente(filaSeleccionada, columnaSeleccionada, nf, nc)) {
                        casillas[nf][nc].setBackground(movimientoValido);
                    }
                }
            }
            return;
        }

        //ATAQUE NORMAL
        if (rbAtaque.isSelected()) {
            for (int dr = -1; dr <= 1; dr++) {
                for (int dc = -1; dc <= 1; dc++) {

                    if (dr == 0 && dc == 0) {
                        continue;
                    }

                    int nf = filaSeleccionada + dr;
                    int nc = columnaSeleccionada + dc;

                    if (!tablero.estaDentro(nf, nc)) {
                        continue;
                    }

                    Piezas p = tablero.get(nf, nc);
                    try {
                        if (p != null && p.getJugador() != piezaSeleccionada.getJugador()) {
                            casillas[nf][nc].setBackground(new Color(180, 50, 50));
                        }
                    } catch (Exception ignored) {
                    }
                }
            }
            return;
        }

        //ATAQUE ESPECIAL
        if (rbEspecial.isSelected()) {
            //ATAQUE ESPECIAL - HOMBRELOBO
            if (piezaSeleccionada instanceof HombreLobo) {
                for (int dr = -1; dr <= 1; dr++) {
                    for (int dc = -1; dc <= 1; dc++) {
                        if (dr == 0 && dc == 0) {
                            continue;
                        }

                        int f1 = filaSeleccionada + dr, c1 = columnaSeleccionada + dc;
                        int f2 = filaSeleccionada + dr * 2, c2 = columnaSeleccionada + dc * 2;

                        if (tablero.estaDentro(f1, c1) && tablero.estaVacia(f1, c1)) {
                            casillas[f1][c1].setBackground(new Color(255, 120, 120));
                        }

                        if (tablero.estaDentro(f2, c2)) {
                            if (tablero.estaDentro(f1, c1) && tablero.estaVacia(f1, c1) && tablero.estaVacia(f2, c2)) {
                                casillas[f2][c2].setBackground(new Color(255, 160, 80));
                            }
                        }
                    }
                }
                return;
            }
            //ATAQUE ESPECIAL - VAMPIRO
            if (piezaSeleccionada instanceof Vampiro) {
                for (int dr = -1; dr <= 1; dr++) {
                    for (int dc = -1; dc <= 1; dc++) {

                        if (dr == 0 && dc == 0) {
                            continue;
                        }

                        int nf = filaSeleccionada + dr, nc = columnaSeleccionada + dc;
                        if (!tablero.estaDentro(nf, nc)) {
                            continue;
                        }

                        Piezas p = tablero.get(nf, nc);
                        try {
                            if (p != null && p.getJugador() != piezaSeleccionada.getJugador()) {
                                casillas[nf][nc].setBackground(new Color(180, 50, 50));
                            }
                        } catch (Exception ignored) {
                        }
                    }
                }
                return;
            }

            //ATAQUE ESPECIAL - ZOMBIE
            if (piezaSeleccionada instanceof Muerte) {
                panelOpcEspecialMuerte.setVisible(true);
                int opcion = rbConjurarZombie.isSelected() ? 2 : rbAtacarZombie.isSelected() ? 3 : 1;

                if (opcion == 2) {
                    for (int f = 0; f < 6; f++) {
                        for (int c = 0; c < 6; c++) {

                            if (tablero.estaVacia(f, c)) {
                                casillas[f][c].setBackground(movimientoValido);
                            }

                        }
                    }
                    return;
                } else if (opcion == 3) {
                    for (int f = 0; f < 6; f++) {
                        for (int c = 0; c < 6; c++) {
                            Piezas p = tablero.get(f, c);

                            try {
                                if (p != null && p instanceof Zombie && p.getJugador() == piezaSeleccionada.getJugador()) {
                                    casillas[f][c].setBackground(movimientoValido);
                                }

                            } catch (Exception ignored) {
                            }
                        }
                    }
                    return;
                } else {
                    for (int dr = -2; dr <= 2; dr++) {
                        for (int dc = -2; dc <= 2; dc++) {

                            if (dr == 0 && dc == 0) {
                                continue;
                            }

                            int nf = filaSeleccionada + dr;
                            int nc = columnaSeleccionada + dc;
                            if (!tablero.estaDentro(nf, nc)) {
                                continue;
                            }

                            if (!tablero.esDistanciaExacta(filaSeleccionada, columnaSeleccionada, nf, nc, 2)) {
                                continue;
                            }

                            Piezas p = tablero.get(nf, nc);
                            try {
                                if (p != null && p.getJugador() != piezaSeleccionada.getJugador()) {
                                    casillas[nf][nc].setBackground(new Color(180, 50, 50));
                                }
                            } catch (Exception ignored) {
                            }
                        }
                    }
                    return;
                }
            }

            for (int dr = -1; dr <= 1; dr++) {
                for (int dc = -1; dc <= 1; dc++) {
                    if (dr == 0 && dc == 0) {
                        continue;
                    }
                    int nf = filaSeleccionada + dr, nc = columnaSeleccionada + dc;
                    if (!tablero.estaDentro(nf, nc)) {
                        continue;
                    }
                    if (tablero.estaVacia(nf, nc)) {
                        casillas[nf][nc].setBackground(movimientoValido);
                    } else {
                        try {
                            Piezas p = tablero.get(nf, nc);
                            if (p != null && p.getJugador() != piezaSeleccionada.getJugador()) {
                                casillas[nf][nc].setBackground(new Color(180, 50, 50));
                            }
                        } catch (Exception ignored) {
                        }
                    }
                }
            }
        }
    }

    private void marcarEnemigosCercanosDelZombie(int fz, int cz) {
        limpiarCasillas();

        if (filaSeleccionada >= 0 && columnaSeleccionada >= 0) {
            casillas[filaSeleccionada][columnaSeleccionada].setBackground(PSeleccionada);
        }

        for (int dr = -1; dr <= 1; dr++) {
            for (int dc = -1; dc <= 1; dc++) {
                if (dr == 0 && dc == 0) {
                    continue;
                }
                int nf = fz + dr, nc = cz + dc;
                if (!tablero.estaDentro(nf, nc)) {
                    continue;
                }
                Piezas p = tablero.get(nf, nc);
                Piezas z = tablero.get(fz, cz);
                if (p != null && z != null && esEnemigo(p, z)) {
                    casillas[nf][nc].setBackground(movimientoValido);
                }
            }
        }
    }

    private boolean hayEnemigosAdyacentes(int fz, int cz) {
        for (int dr = -1; dr <= 1; dr++) {
            for (int dc = -1; dc <= 1; dc++) {
                if (dr == 0 && dc == 0) {
                    continue;
                }

                int nf = fz + dr, nc = cz + dc;
                if (!tablero.estaDentro(nf, nc)) {
                    continue;
                }

                Piezas p = tablero.get(nf, nc);
                Piezas z = tablero.get(fz, cz);
                if (p != null && z != null && esEnemigo(p, z)) {
                    return true;
                }
            }
        }
        return false;
    }

    private void limpiarCasillas() {
        for (int f = 0; f < 6; f++) {
            for (int c = 0; c < 6; c++) {
                if ((f + c) % 2 == 0) {
                    casillas[f][c].setBackground(new Color(200, 200, 200));
                } else {
                    casillas[f][c].setBackground(new Color(100, 100, 100));
                }
            }
        }
    }

    private void cargarImagen() {
        VInterfaz.revalidate();
        for (int f = 0; f < 6; f++) {
            for (int c = 0; c < 6; c++) {
                Piezas p = tablero.get(f, c);
                JButton btn = casillas[f][c];
                if (p != null) {
                    String tipo = p.getTipoPieza();
                    if (tipo == null || tipo.trim().isEmpty()) {
                        tipo = p.getClass().getSimpleName();
                    }
                    String nombreArchivo = tipo.replace(" ", "") + p.getJugador() + ".png";
                    String ruta = "/Imagenes/" + nombreArchivo;
                    ImageIcon icon = null;
                    try {
                        java.net.URL res = getClass().getResource(ruta);
                        if (res != null) {
                            icon = new ImageIcon(res);
                        }
                    } catch (Exception ignored) {
                    }
                    if (icon != null && icon.getImage() != null) {
                        int ancho = btn.getWidth();
                        int alto = btn.getHeight();
                        if (ancho <= 0) {
                            ancho = btn.getPreferredSize().width;
                        }
                        if (alto <= 0) {
                            alto = btn.getPreferredSize().height;
                        }
                        Image img = icon.getImage().getScaledInstance(ancho - 10, alto - 10, Image.SCALE_SMOOTH);
                        btn.setIcon(new ImageIcon(img));
                        btn.setText(null);
                    } else {
                        btn.setIcon(null);
                        btn.setText(p.getTipoPieza() != null ? p.getTipoPieza() : p.getClass().getSimpleName());
                    }
                } else {
                    btn.setIcon(null);
                    btn.setText(null);
                }
            }
        }
    }

    private void actualizarEstado() {
        Estado.setText("Jugador actual: " + String.valueOf(logica.getJugadorActual()));
    }

    private void actualizarControlesSegunSeleccion() {
        if (piezaSeleccionada == null) {
            rbMover.setEnabled(true);
            rbAtaque.setEnabled(true);
            rbEspecial.setEnabled(true);
            panelOpcEspecialMuerte.setVisible(false);
            Estado.setText("Jugador actual: " + String.valueOf(logica.getJugadorActual()));
            return;
        }

        if (piezaSeleccionada instanceof Zombie) {
            rbMover.setEnabled(false);
            rbAtaque.setEnabled(false);
            rbEspecial.setEnabled(false);
            panelOpcEspecialMuerte.setVisible(false);
            return;
        }

        rbMover.setEnabled(true);
        rbAtaque.setEnabled(true);
        rbEspecial.setEnabled(true);
        panelOpcEspecialMuerte.setVisible(piezaSeleccionada instanceof Muerte && rbEspecial.isSelected());
        Estado.setText("Jugador actual: " + String.valueOf(logica.getJugadorActual()));
    }

    //Helpers
    private void resetEsperandoEnemigo() {
        esperandoEnemigo = false;
        filaZombieSeleccionado = -1;
        colZombieSeleccionado = -1;
    }

    private boolean esEnemigo(Piezas a, Piezas b) {
        if (a == null || b == null) {
            return false;
        }
        try {
            return a.getJugador() != b.getJugador();
        } catch (Exception ignored) {
            return false;
        }
    }

    private boolean atacarYEliminarSiMuerto(Piezas atacante, Piezas objetivo, int filaObj, int colObj) {
        try {
            atacante.atacar(objetivo);
            if (!objetivo.estaVivo()) {
                tablero.eliminarPieza(filaObj, colObj);
            }
            return true;
        } catch (Exception ignored) {
            return false;
        }
    }

}
