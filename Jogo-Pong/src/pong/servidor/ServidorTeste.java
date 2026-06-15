package pong.servidor;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

// Servidor teste para verificar concorrencia entre as threads - Adaptar futuramente
public class ServidorTeste {
    public static final int PORTA = 5555;
    
    public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(PORTA)){
            System.out.println("Servidor Iniciado");
            System.out.println("Aguardando Jogadores... ");
            
            Socket socket1 = serverSocket.accept();
            System.out.println("Jogador 1 Conectado");
            
            Socket socket2 = serverSocket.accept();
            System.out.println("Jogador 2 Conectado");
            
            GerenciadorCliente jogador1 = new GerenciadorCliente(socket1, 1);
            GerenciadorCliente jogador2 = new GerenciadorCliente(socket2, 2);
            
            new Thread(jogador1).start();
            new Thread(jogador2).start();
            
            while(true){
                if(!jogador1.getLastInput().isEmpty()){
                    System.out.println("J1:" + jogador1.getLastInput());
                }
                if(!jogador2.getLastInput().isEmpty()){
                    System.out.println("J2:" + jogador2.getLastInput());
                }
                
                Thread.sleep(100);
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}
