package pong.objetos;

import java.awt.Color;
import java.awt.Graphics;

public class Raquete extends ObjetosJogo {
    
    private static final int W = 12, H= 80;
    private int velocidade = 6;
    private boolean cima, baixo;

    public Raquete(int x, int y) {
        super(x, y, W, H);
    }
    
    
    //  verificar a necessidade dk set da velocidade, tendo em vista que é pre-setada

//    public void setVelocidade(int velocidade) {
//        this.velocidade = velocidade;
//    }

    // metodos para mover a raquete para cima e para baixo
    public void setCima(boolean cima) {
        this.cima = cima;
    }

    public void setBaixo(boolean baixo) {
        this.baixo = baixo;
    }

    // logica para verificar se a raquete sobe ou desce e quanto de pixels é alterado
    @Override
    public void update() {
        if( cima && y > 0 ) {
            y -= velocidade;
        } else if( baixo && y < 600 - H) {
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
