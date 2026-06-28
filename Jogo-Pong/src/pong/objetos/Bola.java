package pong.objetos;

import java.awt.Color;
import java.awt.Graphics;

public class Bola extends ObjetosJogo {
    
    private int velocidadeX, velocidadeY;
    private static final int TAMANHO = 10, velocidadeBola = 5;

    //construtor 
    public Bola(int x, int y) {
        super(x, y, TAMANHO, TAMANHO);
        setVelocidadeX(velocidadeBola);
        setVelocidadeY(velocidadeBola);
    }

    // getter e setters
    public int getVelocidadeX() {
        return velocidadeX;
    }

    public void setVelocidadeX(int velocidadeX) {
        this.velocidadeX = velocidadeX;
    }

    public int getVelocidadeY() {
        return velocidadeY;
    }

    public void setVelocidadeY(int velocidadeY) {
        this.velocidadeY = velocidadeY;
    }
    
    //logica que a bola usa ao ser rebatida no eixo x
    public void rebaterX(){
        velocidadeX *= -1; // inverte o valor da velocidade para negativo, retornando o eixo x 
    }
    
    //logica que a bola usa ao ser rebatida no eixo y
    public void rebaterY(){
        velocidadeY *= -1; // mesma logica para y
    }
    
    
    // atualiza a bolinha no plano cartesiano 
    @Override
    public void update() {
        x += velocidadeX;
        y += velocidadeY;
    }
    
    // renderização gráfica da bolinha 
    @Override
    public void draw(Graphics g) {
        g.setColor(Color.white);
        g.fillOval(x, y, width, height);
    }
    
    public void reset(int x, int y){
        this.x = x;
        this.y = y;
        setVelocidadeX(velocidadeBola);
        setVelocidadeY(velocidadeBola);
    }
    
}
