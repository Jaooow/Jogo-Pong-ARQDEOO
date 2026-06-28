package pong;

import javax.swing.SwingUtilities;
import pong.visual.PainelPrincipal;

public class Main {

    public static void main(String[] args) {
            
            SwingUtilities.invokeLater(() -> {
                new PainelPrincipal();
            });
            
    }
}
