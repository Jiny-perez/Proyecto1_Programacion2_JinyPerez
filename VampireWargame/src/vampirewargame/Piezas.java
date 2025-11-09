package vampirewargame;

/**
 *
 * @author marye
 */
public abstract class Piezas {

    protected String tipoPieza;
    protected int vida;
    protected int escudo;
    protected int ataque;

    protected int jugador;
    protected int fila;
    protected int columna;

    public Piezas(int jugador, int vida, int escudo, int ataque, String tipoPieza) {
        this.jugador = jugador;
        this.vida = vida;
        this.escudo = escudo;
        this.ataque = ataque;
        this.tipoPieza = tipoPieza;
    }

    //Get y set de la clase Pieza
    public int getJugador() {
        return jugador;
    }

    public void setJugador(int jugador) {
        this.jugador = jugador;
    }

    public int getFila() {
        return fila;
    }

    public int getColumna() {
        return columna;
    }

    public void setPosicion(int fila, int columna) {
        this.fila = fila;
        this.columna = columna;
    }

    public int getVida() {
        return vida;
    }

    public void setVida(int vida) {
        this.vida = vida;
    }

    public int getAtaque() {
        return ataque;
    }

    public void setAtaque(int ataque) {
        this.ataque = ataque;
    }

    public String getTipoPieza() {
        return tipoPieza;
    }

    public void setTipoPieza(String tipoPieza) {
        this.tipoPieza = tipoPieza;
    }

    //Metodos 
    public void danio(int danio) {
        if (escudo >= danio) {
            escudo -= danio;
        } else {
            int danioRestante = danio - escudo;
            escudo = 0;
            vida -= danioRestante;
            if (vida < 0) {
                vida = 0;
            }
        }
    }

    public void curacion(int vidaRobada) {
        vida += vidaRobada;
    }

    public final boolean estaVivo() {
        if (vida > 0) {
            return true;
        } else {
            return false;
        }
    }

    public void ataqueNormal(Piezas oponente) {
        if (oponente == null) {
            return;
        }

        oponente.danio(this.ataque);
    }

    public void atacar(Piezas objetivo) {
        ataqueNormal(objetivo);
    }

    public abstract void ataqueEspecial(int opcion, Tablero tablero, int fila, int columna);
    public abstract String realizarAccion(String accion, Tablero tablero);

}
