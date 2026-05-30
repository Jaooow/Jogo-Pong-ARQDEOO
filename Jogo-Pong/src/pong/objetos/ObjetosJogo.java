package pong.objetos;

import java.awt.Graphics;
import java.awt.Rectangle;

public abstract class ObjetosJogo {
   
    protected int x, y, width, height;

    // construtor da classe
    public ObjetosJogo(int x, int y, int width, int height) {
        
        setX(x);
        setY(y);
        setWidth(width);
        setHeight(height);
        
    }

    // getters e setters
    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }
    
    // renderização gráfica 
    public abstract void draw(Graphics g);
    
    // atualização do objeto no jogo
    public abstract void update();
    
    // retorna a area do limite do jogo (objeto)
    public Rectangle getArea(){
    
        return new Rectangle (x, y, width, height );
        
    }
    
}
