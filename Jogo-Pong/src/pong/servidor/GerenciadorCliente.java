package pong.servidor;

import java.io.*;
import java.net.Socket;

// Classe para tratar o Jogador
public class GerenciadorCliente implements Runnable{
    
    private final Socket socket;
    private final int jogadorNumero;
    
    private PrintWriter out;
    private BufferedReader in;
    private volatile String lastInput;
    private Runnable aoDesconectar;

    // Construtor 
    public GerenciadorCliente(Socket socket, int jogadorNumero) {
        this.socket = socket;
        this.jogadorNumero = jogadorNumero;
        this.lastInput = ""; // Sem input inicial
    }
    
    // Método executado quando Thread inicia
    @Override
    public void run() {
        try {
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream(), true);
            
            out.println("Jogador:" + jogadorNumero);
            
            String line;
            while((line = in.readLine()) != null){ // Loop para ler mensagens do Cliente(Jogador)
                lastInput = line;
            }
        } catch (IOException e) {
            System.out.println("Jogador " + jogadorNumero + "desconectou-se.");
        } finally {
           if (aoDesconectar != null) aoDesconectar.run(); // Callback para o servidor saber que a thread encerrou
        }
    }

    // Getter
    public String getLastInput() { return lastInput; }
    
    // Enviar o estado de jogo para o Jogador
    public void enviarEstado(String estado){ if(out != null) out.println(estado); }
    public void setAoDesconectar(Runnable aoDesconectar) { this.aoDesconectar = aoDesconectar; }
}