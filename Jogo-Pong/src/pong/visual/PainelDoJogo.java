package pong.visual;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import javax.swing.JPanel;
import java.awt.Graphics;
import pong.jogo.EstadoDeJogo;


public class PainelDoJogo extends JPanel {
    
    private final EstadoDeJogo estado;
    private int numeroJogador;

    public PainelDoJogo(EstadoDeJogo estado) {
        this.estado = estado;
        setNumeroJogador(0);
        
        setPreferredSize(new Dimension(EstadoDeJogo.widht, EstadoDeJogo.height));
        setBackground(Color.black);
        setFocusable(true);
    }

    public void setNumeroJogador(int numeroJogador) {
        this.numeroJogador = numeroJogador;
    }
    
    @Override
    protected void paintComponent(Graphics g){
        
        super.paintComponent(g);
        
        // Desenhando objetos do jogo
        if( estado.getBola() != null){
            estado.getBola().draw(g);
        }
        if( estado.getRaquete1() != null){
            estado.getRaquete1().draw(g);
        }
        if( estado.getRaquete2() != null ){
            estado.getRaquete2().draw(g);
        }
        
        // Desenhando placar 
        g.setFont(new Font("Arial", Font.BOLD, 50));
        g.setColor(Color.white);
        // Separa nas partes proporcionais
        g.drawString(String.valueOf(estado.getScore1()), EstadoDeJogo.widht / 4, 70);
        g.drawString(String.valueOf(estado.getScore2()), (EstadoDeJogo.widht / 4) * 3, 70);
        
        //variaveis para centralizar texto
        FontMetrics fonte;
        int centroX = EstadoDeJogo.widht / 2;
        int centroY = EstadoDeJogo.height / 2;
        
        if( estado.isGameOver() ){
            g.setColor(new Color(0, 0, 0, 180));
            g.fillRect(0, 0, EstadoDeJogo.widht, EstadoDeJogo.height);
            
            
            // mensagem para o vencedor
            g.setFont(new Font("Arial", Font.BOLD, 36));
            g.setColor(Color.yellow);
            
            String msgVitoria = "Jogador " + estado.getVencedor() + " Venceu!";
            fonte = g.getFontMetrics();
            g.drawString(msgVitoria, centroX - fonte.stringWidth(msgVitoria) / 2, centroY - 20);
            
            // mensagem para reiniciar ou encerrar o jogo
            g.setFont(new Font("Arial", Font.PLAIN, 16));
            g.setColor(Color.white);
            
            String msgReset = "Pressione R para reiniciar | ESC para sair ";
            fonte = g.getFontMetrics();
            g.drawString(msgReset, centroX - fonte.stringWidth(msgReset) / 2, centroY + 30);
            
        } else if ( !estado.isEm_andamento() ) {
            
            // tela para aguardar jogadores
            g.setFont(new Font("Arial", Font.PLAIN, 20));
            g.setColor(Color.white);
            
            String msgEspera = "Aguardando jogadores...";
            fonte = g.getFontMetrics();
            g.drawString(msgEspera, centroX - fonte.stringWidth(msgEspera) / 2, centroY);
        } 
        
        // Identificar o jogador
        if (numeroJogador > 0 ){
            g.setFont(new Font("Arial", Font.PLAIN, 12));
            g.setColor(new Color(255,255,255,120));
            
            String comandos;
            if(numeroJogador == 1){
                comandos = "( W / S )";
            }else{
                comandos = "( Setar indicadoras )";
            }
            
            g.drawString("Voce e o jogador " + numeroJogador + " " + comandos, 15, EstadoDeJogo.height - 15);
        }
    }
    
}
