package pong.servidor;

import java.io.*;
import java.net.Socket;

import pong.jogo.EstadoDeJogo;

// Classe que se conecta ao Servidor
public class ClienteJogo {
    
    private Socket socket;
    private PrintWriter out;
    private BufferedReader in;
    
    private int numeroJogador;
    private boolean conectado;
    private EstadoDeJogo estadoLocal;
    private Runnable estadoUpdate;

    // Construtor que recebe o estado local do jogo
    public ClienteJogo(EstadoDeJogo estadoLocal) {
        this.estadoLocal = estadoLocal;
        this.numeroJogador = 0;
        this.conectado = false;
    }
    
    // Callback  de atualização do jogo
    public void setEstadoUpdate(Runnable r){
        this.estadoUpdate = r;
    }
    
    // Método para conectar no servidor
    public boolean conectado(String host){
        try {
            
            // cria socket e tempo de espera de 3 segundos
            socket = new Socket();
            socket.connect(new java.net.InetSocketAddress(host, ServidorJogo.PORTA), 3000);
            
            out = new PrintWriter(socket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            conectado = true;
            
            String primeira  = in.readLine(); 
            if (primeira != null && primeira.startsWith("Jogador:")) {
                numeroJogador = Integer.parseInt(primeira.split(":")[1]); // Extrai o numero do jogador 
            }
            
            Thread listener = new Thread(this::escutar);
            listener.setDaemon(true); // Encerra junto com o programa
            listener.start();
            
            return true;
        } catch (IOException e) {
            System.out.println("Erro ao conectar: " + e.getMessage());
            return false;
        }
    }
    
    // Método para escutar(recebe dados do servidor)
    private void escutar(){
        try {
            String linha; 
            
            while(conectado && (linha = in.readLine()) != null){                
                aplicarEstado(linha);
                if(estadoUpdate != null) estadoUpdate.run(); // Callback para redesenhar a tela
            }
        } catch (Exception e) {
        }
    }
    
    // Método para interpretar comandos recebidos e atualizar estado do jogo
    private void aplicarEstado(String dados) {
        try {
            String[] p = dados.split(",");
            estadoLocal.getBola().setX(Integer.parseInt(p[0])); // Atualiza x da bola
            estadoLocal.getBola().setY(Integer.parseInt(p[1])); // Atualiza y da bola
            estadoLocal.getRaquete1().setY(Integer.parseInt(p[2])); // Atualiza y da raquete 1
            estadoLocal.getRaquete2().setY(Integer.parseInt(p[3])); // Atualiza y da raquete 2
            estadoLocal.setScore1(Integer.parseInt(p[4])); // Atualiza pontuacao
            estadoLocal.setScore2(Integer.parseInt(p[5])); // Atualiza pontuacao
            estadoLocal.setGameOver(Boolean.parseBoolean(p[6])); // Atualiza se acabou ou nao
            estadoLocal.setVencedor(Integer.parseInt(p[7])); // Atualiza vencedor
            estadoLocal.setEm_andamento(Boolean.parseBoolean(p[8])); // Atualiza se o jogo esta em andamento ou nao
        } catch (Exception e) {
            System.out.println("Erro ao aplicar estado: " + e.getMessage());
        }
    }
    
    // Enviar um comando para o Servidor
    public void enviarInput(String input){
        if(out != null && conectado) out.println(input);
    }
    
    // Desconecta do servidor
    public void desconectar(){
        conectado = false;
        try {
            if(socket != null) socket.close();
        } catch (IOException e) {
            System.out.println("Erro ao desconectar.");
        }
    }
    
    // Getters
    public int getNumeroJogador(){ return numeroJogador; } 
    public boolean isConectado() { return conectado; }
    
}
