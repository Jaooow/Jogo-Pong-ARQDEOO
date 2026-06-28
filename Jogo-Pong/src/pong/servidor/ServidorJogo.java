package pong.servidor;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import pong.jogo.EstadoDeJogo;
import pong.objetos.Raquete;
import pong.pontuacao.GerenciadorDePontuacao;
import pong.sons.GerenciadorDeSom;

// Servidor do Jogo - Adaptar parte de pontuações
public class ServidorJogo {
    public static final int PORTA = 5555;
    
    private GerenciadorCliente gerenciador1;
    private GerenciadorCliente gerenciador2;
    private EstadoDeJogo estado;
    private GerenciadorDePontuacao gerenciadorPontuacao;
    
    private volatile boolean jogadorDesconectou = false;
    private volatile int numeroJogadorSaiu = 0;
    
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
            socket1.setTcpNoDelay(true);
            System.out.println("Jogador 1 Conectado.");
            gerenciador1 = new GerenciadorCliente(socket1, 1);
            new Thread(gerenciador1).start();
            
            // Quando jogador 1 desconectar
            gerenciador1.setAoDesconectar(() -> {
                jogadorDesconectou = true;
                numeroJogadorSaiu  = 1;
            });
            
            // o servidor fica na espera do jogador 2
            Socket socket2 = serverSocket.accept();
            socket2.setTcpNoDelay(true);
            System.out.println("Jogador 2 Conectado.");
            gerenciador2 = new GerenciadorCliente(socket2, 2);
            new Thread(gerenciador2).start();
            
            // Jogador 2 desconectar
            gerenciador2.setAoDesconectar(() -> {
                jogadorDesconectou = true;
                numeroJogadorSaiu  = 2;
            });

            estado.resetar();
            GerenciadorDeSom.tocar("iniciar.wav");
            
            boolean placarSalvo = false; // Garantir que o placar só salve um por vez

            while (true) {
                
                if (jogadorDesconectou) {
                    String msg = "DESCONECTADO:" + numeroJogadorSaiu;
                    gerenciador1.enviarEstado(msg);
                    gerenciador2.enviarEstado(msg);
                    System.out.println("Jogador " + numeroJogadorSaiu + " saiu. Encerrando partida.");
                    break; 
                }
                
                String input1 = gerenciador1.getLastInput();
                String input2 = gerenciador2.getLastInput();

                if (estado.isGameOver()) {
                    // Se acabou de dar Game Over, salva o placar uma única vez
                    if (!placarSalvo) {
                        gerenciadorPontuacao.salvarPontuacao("Jogador1", estado.getScore1(), "Jogador2", estado.getScore2());
                        placarSalvo = true;
                    }

                    // Fica escutando se algum jogador apertou "R"
                    if ("REINICIAR".equals(input1) || "REINICIAR".equals(input2)) {
                        estado.resetar();
                        GerenciadorDeSom.tocar("iniciar.wav"); 
                        placarSalvo = false; // Reseta a variavel para que possa ser salvo a proxima partida
                    }
                } else {
                    receberComando(input1, estado.getRaquete1());
                    receberComando(input2, estado.getRaquete2());
                    estado.update();
                }

                // Envia o estado atualizado
                String estadoAtual = montarEstado();
                gerenciador1.enviarEstado(estadoAtual);
                gerenciador2.enviarEstado(estadoAtual);

                try {
                    Thread.sleep(16);
                } catch (InterruptedException e) {
                    System.out.println("Loop interrompido.");
                }
            }
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
