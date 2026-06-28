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
        add(criarBotao("Jogar Local (mesmo teclado)", () -> painel.iniciarJogoLocal()));
        add(Box.createVerticalStrut(10));
        add(criarBotao("Criar partida (Servidor)", () -> painel.iniciarComoServidor()));
        add(Box.createVerticalStrut(10));
        add(criarBotao("Entrar Partida (Cliente)", () -> painel.iniciarComoCliente()));
        add(Box.createVerticalStrut(10));
        add(criarBotao("Ver Historico de Partidas", () -> painel.mostrarPontuacao()));
        
        add(Box.createVerticalStrut(40));
        
        // Controles modo local
        JLabel controlesLocal = new JLabel("Modo Local:  Jogador 1 → W / S   |   Jogador 2 → Setas");
        controlesLocal.setFont(new Font("Arial", Font.PLAIN, 13));
        controlesLocal.setForeground(Color.gray);
        controlesLocal.setAlignmentX(CENTER_ALIGNMENT);
        add(controlesLocal);

        add(Box.createVerticalStrut(6));

        // Controles modo rede
        JLabel controlesRede = new JLabel("Modo Rede:  Ambas as teclas funcionam");
        controlesRede.setFont(new Font("Arial", Font.PLAIN, 13));
        controlesRede.setForeground(Color.gray);
        controlesRede.setAlignmentX(CENTER_ALIGNMENT);
        add(controlesRede);
    }
    
    // Metodo para criar botao padrozinado do menu
    private JButton criarBotao(String texto, Runnable acao){
       
        JButton btn = new JButton(texto);
        btn.setFont(new Font("Arial", Font.BOLD, 16));
        btn.setForeground(Color.black);
        btn.setBackground(Color.white);
        btn.setFocusPainted(false);
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btn.setMaximumSize(new Dimension(300, 40));
        btn.setAlignmentX(CENTER_ALIGNMENT);
        
        btn.addActionListener(e -> acao.run());
        
        return btn;
    }
    
    
    
   
}
