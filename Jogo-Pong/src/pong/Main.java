package pong;

import javax.swing.JFrame;
import pong.jogo.EstadoDeJogo;
import pong.visual.PainelDoJogo;
import pong.visual.PainelMenu;

public class Main {

    public static void main(String[] args) {

        // Teste Criação do estado 
        System.out.println("Teste Criando EstadoDeJogo");
        EstadoDeJogo estado = new EstadoDeJogo();
        System.out.println("Estado criado com sucesso.");
        System.out.println("Em andamento: " + estado.isEm_andamento()); 
        System.out.println("Game Over: "    + estado.isGameOver());    
        System.out.println("Score 1: "      + estado.getScore1());    
        System.out.println("Score 2: "      + estado.getScore2());  
        System.out.println();

        // Teste Resetar o jogo 
        System.out.println("Teste Resetando o jogo");
        estado.resetar();
        System.out.println("Jogo resetado.");
        System.out.println("Em andamento: " + estado.isEm_andamento());
        System.out.println("Game Over: "    + estado.isGameOver());
        System.out.println();

        // Teste Objetos criados
        System.out.println("Teste Verificando objetos");
        System.out.println("Bola criada: "     + (estado.getBola() != null));
        System.out.println("Raquete 1 criada: "+ (estado.getRaquete1() != null));
        System.out.println("Raquete 2 criada: "+ (estado.getRaquete2() != null));
        System.out.println("Posicao inicial da bola -> X: " + estado.getBola().getX() + "  Y: "   + estado.getBola().getY());
        System.out.println();

        // Teste Update da bola
        System.out.println("Teste Atualizando o jogo");
        for (int i = 1; i <= 5; i++) {
            estado.update();
            System.out.println("Frame " + i + " -> Bola X: " + estado.getBola().getX() + "  Y: " + estado.getBola().getY());
        }
        System.out.println();

        // Teste Movimento das raquetes 
        System.out.println("Teste Movendo raquetes");
        int yAntesDe1 = estado.getRaquete1().getY();
        int yAntesDe2 = estado.getRaquete2().getY();

        estado.getRaquete1().setCima(true);
        estado.getRaquete2().setBaixo(true);
        estado.update();

        System.out.println("Raquete 1 subiu:  antes=" + yAntesDe1 + "  depois=" + estado.getRaquete1().getY());
        System.out.println("Raquete 2 desceu: antes=" + yAntesDe2 + "  depois=" + estado.getRaquete2().getY());

        estado.getRaquete1().setCima(false);
        estado.getRaquete2().setBaixo(false);
        System.out.println();

        // Teste Velocidade da bola 
        System.out.println("Verificando velocidade da bola");
        System.out.println("Velocidade X: " + estado.getBola().getVelocidadeX());
        System.out.println("Velocidade Y: " + estado.getBola().getVelocidadeY());
        estado.getBola().rebaterX();
        System.out.println("Apos rebaterX -> Velocidade X: " + estado.getBola().getVelocidadeX());
        estado.getBola().rebaterY();
        System.out.println("Apos rebaterY -> Velocidade Y: " + estado.getBola().getVelocidadeY());
        System.out.println();

        
        
        
        
        // teste do visual
        JFrame janela = new JFrame("Teste do Pong - Visual");
        
        estado.setEm_andamento(false);
//        PainelDoJogo painel  = new PainelDoJogo(estado);
        // arrumar centralizacao dos botoes do menu
        PainelMenu painel  = new PainelMenu();
        
        janela.add(painel);
//        janela.setSize(EstadoDeJogo.widht, EstadoDeJogo.height);
        janela.pack();
        janela.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        janela.setResizable(false);
        janela.setLocationRelativeTo(null);
        
        janela.setVisible(true);
    }
}
