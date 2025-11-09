package vampirewargame;

/**
 *
 * @author marye
 */
public class LogicaJuego {

    private final Tablero tablero;
    private int jugadorActual = 1;

    private String piezaPermitida = null;
    private int piezaPermitidaJugador = -1;

    private static final String[] tipoPiezas = {"Vampiro", "Hombre Lobo", "Muerte", "Zombie"};
    private int[][] piezaPerdidas = new int[3][tipoPiezas.length];

    public LogicaJuego(Tablero tablero) {
        this.tablero = tablero;
    }

    public int getJugadorActual() {
        return jugadorActual;
    }

    public void setJugadorActual(int jugadorActual) {
        this.jugadorActual = jugadorActual;
    }

    public void cambiarTurno() {
        jugadorActual = (jugadorActual == 1) ? 2 : 1;
    }

    public void setPiezaPermitida(String tipo, int jugador) {
        if (tipo == null) {
            piezaPermitida = null;
            piezaPermitidaJugador = -1;
        } else {
            piezaPermitida = tipo.trim();
            piezaPermitidaJugador = jugador;
        }
    }

    public String getPiezaPermitida() {
        return piezaPermitida;
    }

    public int getPiezaPermitidaJugador() {
        return piezaPermitidaJugador;
    }

    public void limpiarPieza() {
        piezaPermitida = null;
        piezaPermitidaJugador = -1;
    }

    private int tipoAIndice(String tipo) {
        if (tipo == null) {
            return -1;
        }
        
        String t = tipo.trim();
        for (int i = 0; i < tipoPiezas.length; i++) {
            if (tipoPiezas[i].equalsIgnoreCase(t)) {
                return i;
            }
        }
        return -1;
    }

 
    public void registrarPerdida(Piezas pieza) {
        if (pieza == null) {
            return;
        }
        
        int jugador;
        try {
            jugador = pieza.getJugador();
        } catch (Exception e) {
            return;
        }
        
        if (jugador < 1 || jugador > 2) {
            return;
        }
        
        String tipo = pieza.getTipoPieza();
        if (tipo == null || tipo.trim().isEmpty()) {
            tipo = pieza.getClass().getSimpleName();
        }
        
        int idx = tipoAIndice(tipo);
        if (idx >= 0) {
            piezaPerdidas[jugador][idx] = piezaPerdidas[jugador][idx] + 1;
        } 
    }

   
    public int calcularIntentosExtra(int jugador) {
        if (jugador < 1 || jugador > 2) {
            return 1;
        }
        
        int tiposConDosOMas = 0;
        boolean hayUnTipoConDos = false;
        
        for (int i = 0; i < tipoPiezas.length; i++) {
            int c = piezaPerdidas[jugador][i];
            
            if (c >= 2) {
                tiposConDosOMas++;
                hayUnTipoConDos = true;
            }
            
        }
        
        if (tiposConDosOMas >= 2) {
            return 3;
        }
        
        if (hayUnTipoConDos) {
            return 2;
        }
        return 1;
    }

    public String ejecutarAccion(String accion, Piezas pieza, int... params) {
        if (pieza == null) {
            return "Error: pieza nula";
        }

        accion = accion == null ? "" : accion.toLowerCase();

        if (pieza.getJugador() != jugadorActual) {
            return "No es tu pieza (pertenece al jugador " + pieza.getJugador() + ")";
        }

        switch (accion) {
            case "mover": {
                if (params.length < 2) {
                    return "Faltan coordenadas para mover";
                }
                int destFila = params[0], destCol = params[1];

                if (!tablero.estaDentro(destFila, destCol)) {
                    return "Destino fuera del tablero";
                }
                if (!tablero.estaVacia(destFila, destCol)) {
                    return "Destino ocupado";
                }

                int oriF = pieza.getFila();
                int oriC = pieza.getColumna();
                Piezas actualEnOrigen = null;

                if (tablero.estaDentro(oriF, oriC)) {
                    actualEnOrigen = tablero.get(oriF, oriC);
                }

                if (actualEnOrigen == pieza) {
                    tablero.eliminarPieza(oriF, oriC);
                }

                tablero.colocarPieza(pieza, destFila, destCol);
                return pieza.getTipoPieza() + " movido a (" + destFila + "," + destCol + ")";
            }

            case "atacar": {
                if (params.length < 2) {
                    return "Faltan coordenadas para atacar";
                }
                int tf = params[0], tc = params[1];
                if (!tablero.estaDentro(tf, tc)) {
                    return "Objetivo fuera del tablero";
                }
                Piezas objetivo = tablero.get(tf, tc);
                if (objetivo == null) {
                    return "No hay objetivo en la casilla";
                }
                if (objetivo.getJugador() == pieza.getJugador()) {
                    return "No puedes atacar a un aliado";
                }

                pieza.ataqueNormal(objetivo);

                String resultado = pieza.getTipoPieza() + " atacó a " + objetivo.getTipoPieza()
                        + " (jugador " + objetivo.getJugador() + ")";

                if (!objetivo.estaVivo()) {
                    tablero.eliminarPieza(tf, tc);
                    resultado += " -> objetivo eliminado";
                } else {
                    resultado += " -> vida restante: " + objetivo.getVida();
                }

                return resultado;
            }

            case "conjurar": {
                if (params.length < 2) {
                    return "Faltan coordenadas para conjurar";
                }
                int cf = params[0], cc = params[1];

                if (!tablero.hayEspacio()) {
                    return "No hay espacio en el tablero para conjurar";
                }
                if (!tablero.estaDentro(cf, cc)) {
                    return "Casilla fuera del tablero";
                }
                if (!tablero.estaVacia(cf, cc)) {
                    return "Casilla ocupada";
                }

                Zombie z = new Zombie(pieza.getJugador());
                tablero.colocarPieza(z, cf, cc);
                return "Conjurado Zombie en (" + cf + "," + cc + ")";
            }

            case "especial": {
                if (params.length < 3) {
                    return "Faltan parámetros para especial (opción, fila, columna)";
                }
                int opcion = params[0];
                int sf = params[1], sc = params[2];

                if (!tablero.estaDentro(sf, sc)) {
                    return "Destino del especial fuera del tablero";
                }

                pieza.ataqueEspecial(opcion, tablero, sf, sc);

                String mensaje = pieza.getTipoPieza() + " ejecutó especial " + opcion + " en (" + sf + "," + sc + ")";

                if (pieza instanceof Vampiro && opcion == 1) {
                    mensaje = "Vampiro usó 'chupar sangre' en (" + sf + "," + sc + ")";
                }

                if (pieza instanceof Muerte && opcion == 2) {
                    Piezas colocado = tablero.get(sf, sc);
                    if (colocado instanceof Zombie) {
                        mensaje = "Muerte conjuró Zombie en (" + sf + "," + sc + ")";
                    } else {
                        mensaje = "Muerte intentó conjurar en (" + sf + "," + sc + ")";
                    }
                }
                return mensaje;
            }

            case "pasar": {
                cambiarTurno();
                return "Turno pasado. Ahora es el turno del jugador " + jugadorActual;
            }

            default:
                return "Acción desconocida: " + accion;
        }
    }
}
