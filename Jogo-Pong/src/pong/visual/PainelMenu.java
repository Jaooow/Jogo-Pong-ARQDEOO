package pong.visual;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import pong.jogo.EstadoDeJogo;

/**
 *
 * @author joaop
 */
public class PainelMenu extends JPanel{

    public PainelMenu(PainelPrincipal painel) {
        // Setando a tela 
        setPreferredSize(new Dimension(EstadoDeJogo.widht, EstadoDeJogo.height));
        setBackground(Color.black);
        setLayout( new BoxLayout(this, BoxLayout.Y_AXIS));
        
        add(Box.createVerticalStrut(80));
        
        // Texto do titulo princial
        JLabel titulo = new JLabel("PONG");
        titulo.setFont(new Font("Arial", Font.BOLD, 80));
        titulo.setForeground(Color.white);
        titulo.setAlignmentX(CENTER_ALIGNMENT);
        add(titulo);
        
        // Subtitulo
        JLabel subtitulo = new JLabel("Multiplayer em Rede");
        subtitulo.setFont(new Font("Arial", Font.BOLD, 16));
        subtitulo.setForeground(Color.lightGray);
        subtitulo.setAlignmentX(CENTER_ALIGNMENT);
        add(subtitulo);
        
        add(Box.createVerticalStrut(40));
        
        // Logica para adicionar os botoes, - terminar a logica no proximo sprint
        add(criarBotao("Jogar Local (mesmo teclado)", () -> System.out.println("Iniciar Local clicado!")));
        add(criarBotao("Criar partida (Servidor)", () -> System.out.println("Criar Servidor clicado!")));
        add(criarBotao("Entrar Partida (Cliente)", () -> System.out.println("Entrar Cliente clicado!")));
        add(criarBotao("Ver Historico de Partidas)", () -> System.out.println("Historico clicado!")));
        
        add(Box.createVerticalStrut(40));
        
        JLabel controles = new JLabel(" Jogador 1: W / S  |  Jogador 2: Setas ");
        controles.setFont(new Font("Arial", Font.BOLD, 14));
        controles.setForeground(Color.gray);
        subtitulo.setAlignmentX(CENTER_ALIGNMENT);
        add(controles);
    }
    
    // Metodo para criar botao padrozinado do menu
    private JButton criarBotao(String texto, Runnable acao){
       
        JButton btn = new JButton(texto);
        btn.setFont(new Font("Arial", Font.BOLD, 16));
        btn.setForeground(Color.black);
        btn.setBackground(Color.white);
        btn.setFocusPainted(false);
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btn.setMaximumSize(new Dimension(300, 400));
        btn.setAlignmentX(CENTER_ALIGNMENT);
        
        btn.addActionListener(e -> acao.run());
        
        return btn;
    }
    
    
    
   
}
