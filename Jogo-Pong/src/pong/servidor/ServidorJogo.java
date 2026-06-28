package pong.servidor;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import pong.jogo.EstadoDeJogo;
import pong.objetos.Raquete;
import pong.pontuacao.GerenciadorDePontuacao;

// Servidor do Jogo - Adaptar parte de pontuações
public class ServidorJogo {
    public static final int PORTA = 5555;
    
    private GerenciadorCliente gerenciador1;
    private GerenciadorCliente gerenciador2;
    private EstadoDeJogo estado;
    private GerenciadorDePontuacao gerenciadorPontuacao;
    
    // Construtor para inicializar atributos
    public ServidorJogo() {
        this.estado = new EstadoDeJogo();
        this.gerenciadorPontuacao = new GerenciadorDePontuacao();
    }
    
    // Método para iniciar o Servidor
    public void start() throws IOException {
        try (ServerSocket serverSocket = new ServerSocket(PORTA)){
            
            System.out.println("Servidor Iniciado.");
            System.out.println("Aguardando Jogadores... ");

            // conecta o jogador 1 e inicia a comunicação
            Socket socket1 = serverSocket.accept();
            System.out.println("Jogador 1 Conectado.");
            gerenciador1 = new GerenciadorCliente(socket1, 1);
            new Thread(gerenciador1).start();
            
            // o servidor fica na espera do jogador 2
            Socket socket2 = serverSocket.accept();
            System.out.println("Jogador 2 Conectado.");
            gerenciador2 = new GerenciadorCliente(socket2, 2);
            new Thread(gerenciador2).start();

            estado.resetar();

            while (!estado.isGameOver()) {            
                receberComando(gerenciador1.getLastInput(), estado.getRaquete1());
                receberComando(gerenciador2.getLastInput(), estado.getRaquete2());

                estado.update();

                String estadoAtual = montarEstado();
                gerenciador1.enviarEstado(estadoAtual);
                gerenciador2.enviarEstado(estadoAtual);

                try {
                    Thread.sleep(16);
                } catch (InterruptedException e) {
                    System.out.println("Loop interrompido.");
                }
            }
            
            String estadoFinal = montarEstado();
            gerenciador1.enviarEstado(estadoFinal);
            gerenciador2.enviarEstado(estadoFinal);
            
            gerenciadorPontuacao.salvarPontuacao("Jogador1", estado.getScore1(),"Jogador2", estado.getScore2());
            
        }
    }
    // Método para receber e aplicar o comando da raquete UP/DOWN
    private void receberComando(String input, Raquete raquete){
        raquete.setBaixo(false);
        raquete.setCima(false);
        if("CIMA".equals(input)) raquete.setCima(true);
        if("BAIXO".equals(input)) raquete.setBaixo(true);
    }
    
    // Método que monta o estado do jogo em Texto (utilizado para enviar para o jogador, já que não utilizamos serializable)
    private String montarEstado(){
        return estado.getBola().getX() + "," + estado.getBola().getY() + "," + estado.getRaquete1().getY() 
                + "," + estado.getRaquete2().getY() + "," + estado.getScore1() + "," 
                + estado.getScore2() + "," + estado.isGameOver() + "," + estado.getVencedor() + "," + estado.isEm_andamento();
    }
    
    public static void main(String[] args) {
        try {
            new ServidorJogo().start();
        } catch (IOException e) {
            System.out.println("Erro no servidor: " + e.getMessage());
        }
    }
}
