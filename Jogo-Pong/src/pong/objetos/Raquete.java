package pong.objetos;

import java.awt.Color;
import java.awt.Graphics;
import pong.jogo.EstadoDeJogo;

public class Raquete extends ObjetosJogo {
    
    private static final int W = 12, H= 80, altura_tela = EstadoDeJogo.height;
    private int velocidade = 6;
    private boolean cima, baixo;

    public Raquete(int x, int y) {
        super(x, y, W, H);
        setBaixo(false);
        setCima(false);
    }
    

    // metodos para mover a raquete para cima e para baixo
    public void setCima(boolean cima) {
        this.cima = cima;
    }

    public void setBaixo(boolean baixo) {
        this.baixo = baixo;
    }
    
    // Getters
    public static int getW() {
        return W;
    }

    public static int getH() {
        return H;
    }
        
    // logica para verificar se a raquete sobe ou desce e quanto de pixels é alterado
    @Override
    public void update() {
        if( cima && y > 0 ) {
            y -= velocidade;
        } else if( baixo && y < altura_tela  - H) {
            y += velocidade;
        }
    }

    // renderiza/desenha a raquete 
    @Override
    public void draw(Graphics g) {
        g.setColor(Color.WHITE);
        g.fillRoundRect(x, y, width, height, 6, 6);
    }
 
}
