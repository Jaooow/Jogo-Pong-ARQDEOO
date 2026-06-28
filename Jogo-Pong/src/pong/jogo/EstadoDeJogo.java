package pong.jogo;

import pong.objetos.Bola;
import pong.objetos.Raquete;

/* Classe para atualizar o estado do Jogo */
public class EstadoDeJogo {
    public static final int widht = 800;
    public static final int height = 600;
    public static final int pontuacao_vencedora = 7;
    
    private Bola bola;
    private Raquete raquete1;
    private Raquete raquete2;
    
    private int score1;
    private int score2;
    private boolean em_andamento; 
    private boolean gameOver;
    private int vencedor;

    // Contrutor para iniciar o Jogo
    public EstadoDeJogo() {
        score1 = 0;
        score2 = 0;
        em_andamento = false; 
        gameOver = false;
        vencedor = 0;
        iniciarObjetos();
    }
    
    // Iniciar objetos que serão utilizados no jogo
    private void iniciarObjetos(){
        bola = new Bola(widht / 2 - 5, height / 2 - 5);
        raquete1 = new Raquete(30, height / 2 - 40);
        raquete2 = new Raquete(widht - 30 - Raquete.getW(), height / 2 - 40);
    }
    
    // Resetar o jogo
    public void resetar(){
        score1 = 0;
        score2 = 0;
        gameOver = false; 
        vencedor = 0;
        em_andamento = true; 
        iniciarObjetos();
    }
    
    // Método para reposicionar a bola no centro
    public void resetarBola(){
        bola.reset(widht / 2 - 7, height / 2 - 7);
    }
    
    // Atualiza o estado do jogo 
    public void update(){
        if(!em_andamento || gameOver) return;
        
        bola.update();
        raquete1.update();
        raquete2.update();
        verificarColisoes();
    }
    
    // Método para Verificar colisoes com as raquetes/paredes + pontuacao
    private void verificarColisoes(){
        
        // Paredes superior e inferior
        if(bola.getY() <= 0){
            bola.setY(0);
            bola.rebaterY();
        }else if(bola.getY() + bola.getHeight() >= height){
            bola.setY(height - bola.getHeight());
            bola.rebaterY();
        }
        
        // Colisao com a Raquete 1 
        if(bola.getArea().intersects(raquete1.getArea()) && bola.getVelocidadeX() < 0 ){
            bola.setX(raquete1.getX() + Raquete.getW());
            bola.rebaterX();
        }
        
        // Colisao com a Raquete 2 
        if(bola.getArea().intersects(raquete2.getArea()) && bola.getVelocidadeX() > 0 ){
            bola.setX(raquete2.getX() - bola.getWidth());
            bola.rebaterX();
        }
        
        // Ponto para o jogador 1
        if(bola.getX() > widht ){
            score1++;
            verificarGanhador();
            resetarBola();
        }
        
        // Ponto para o jogador 2
        if(bola.getX() + bola.getWidth() < 0 ){
            score2++;
            verificarGanhador();
            resetarBola();
        }
    }
    
    // Verificar se alguem ganhou a partida
    private void verificarGanhador(){
        if(score1 >= pontuacao_vencedora){
            vencedor = 1;
            gameOver = true;
            em_andamento = false;
        }else if(score2 >= pontuacao_vencedora){
            vencedor = 2;
            gameOver = true;
            em_andamento = false;
        }
    }
    
    // Getters
    public Bola getBola() { return bola; }
    public Raquete getRaquete1() { return raquete1; }
    public Raquete getRaquete2() { return raquete2; }
    public int getScore1() { return score1; }
    public int getScore2() { return score2; }
    public boolean isEm_andamento() { return em_andamento; }
    public boolean isGameOver() { return gameOver; }
    public int getVencedor() {return vencedor; }
    
    // Setters
    public void setEm_andamento(boolean r){ this.em_andamento = r; }
    public void setScore1(int score1){ this.score1 = score1; }
    public void setScore2(int score2){ this.score2 = score2; }
    public void setGameOver(boolean v){ this.gameOver = v; }
    public void setVencedor(int vencedor){ this.vencedor = vencedor; }
}
