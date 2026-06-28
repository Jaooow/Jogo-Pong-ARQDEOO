package pong.sons;

import java.net.URL;
import javax.sound.sampled.*;

// Classe para gerenciar os sons do Jogo
public class GerenciadorDeSom {

    // Toca um som uma única vez
    public static void tocar(String nomeArquivo) {
        try {
            URL url = GerenciadorDeSom.class.getResource("/pong/sons/" + nomeArquivo);
            if (url == null) {
                System.out.println("Som nao encontrado: " + nomeArquivo);
                return;
            }
            AudioInputStream audio = AudioSystem.getAudioInputStream(url);
            Clip clip = AudioSystem.getClip();
            clip.open(audio);
            clip.start();
        } catch (Exception e) {
            System.out.println("Erro ao tocar som: " + e.getMessage());
        }
    }
}