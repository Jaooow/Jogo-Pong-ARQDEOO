package pong.servidor;

import java.io.*;
import java.net.Socket;

public class GerenciadorCliente implements Runnable{
    
    private final Socket socket;
    private final int jogadorNumero;
    
    private PrintWriter out;
    private BufferedReader in;
    private String lastInput;

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
        }
    }

    // Getter
    public String getLastInput() { return lastInput; }
}