package pong.servidor;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

// Classe para testar conexão com servidor - Adaptar futuramente
public class ClienteTeste {
    public static final String HOST = "localhost";
    public static final int PORTA = 5555;

    public static void main(String[] args){
        try (
            Socket socket = new Socket(HOST, PORTA);

            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);

            Scanner teclado = new Scanner(System.in)
        ){
            System.out.println(in.readLine());

            while(true){
                String comando = teclado.nextLine();
                out.println(comando);
            }
        } catch (IOException e){
            e.printStackTrace();
        }
    }
}
