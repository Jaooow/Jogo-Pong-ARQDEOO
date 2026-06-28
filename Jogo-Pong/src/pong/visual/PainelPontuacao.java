package pong.visual;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;
import pong.jogo.EstadoDeJogo;
import pong.pontuacao.GerenciadorDePontuacao;

public class PainelPontuacao extends JPanel{

    public PainelPontuacao(PainelPrincipal painel) {
        
        // setando a tela 
        setPreferredSize(new Dimension(EstadoDeJogo.widht, EstadoDeJogo.height));
        setBackground(Color.black);
        setLayout(new BorderLayout());
        
        // Texto do titulo princial
        JLabel titulo = new JLabel("Historico de Partidas", SwingConstants.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 28));
        titulo.setForeground(Color.white);
        titulo.setBorder(BorderFactory.createEmptyBorder(30, 0, 10, 0));
        add(titulo, BorderLayout.NORTH);
        
        // Pegando os dados do gerenciador de pontuacao
        GerenciadorDePontuacao gerenciador = new GerenciadorDePontuacao();
        List<String> historico = gerenciador.carregarPontuacao();
        
        DefaultListModel<String> modelo = new DefaultListModel<>();
        if( historico.isEmpty() ){
            modelo.addElement("Nenhuma partida registrada ainda! ");
        }else{
            
            // loop para a partida mais recente aparecer no começo
            for (int i = historico.size() -1; i >= 0; i--){
                modelo.addElement(historico.get(i));
            }
        }
        
        // criando a lista de forma visual
        JList<String> lista = new JList<>(modelo);
        lista.setFont(new Font("Arial", Font.PLAIN, 16));
        lista.setForeground(Color.white);
        lista.setBackground(Color.black);
        lista.setFixedCellHeight(36);
        
        // barra de rolagem
        JScrollPane scroll = new JScrollPane(lista);
        scroll.setBorder(BorderFactory.createLineBorder(Color.white, 1));
        scroll.getViewport().setBackground(Color.black);
        
        // painel central para dar espaço na lista
        JPanel centro = new JPanel(new BorderLayout());
        centro.setBackground(Color.black);
        centro.setBorder(BorderFactory.createEmptyBorder(20, 80, 20, 80));
        centro.add(scroll, BorderLayout.CENTER);
        add(centro, BorderLayout.CENTER);
                
                
        // botao de voltar 
        JButton btnVoltar = new JButton("Voltar ao Menu");
        btnVoltar.setFont(new Font("Arial", Font.PLAIN, 16) );
        btnVoltar.setForeground(Color.black);
        btnVoltar.setBackground(Color.white);
        btnVoltar.setFocusPainted(false);
        btnVoltar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        
        // acao do botao para voltar ao menu
        btnVoltar.addActionListener(e -> painel.mostrarMenu());
        
        JPanel rodape = new JPanel();
        rodape.setBackground(Color.black);
        rodape.setBorder(BorderFactory.createEmptyBorder(10, 0, 20, 0));
        
        rodape.add(btnVoltar);
        
        add(rodape, BorderLayout.SOUTH);
        
    }
    
    
    
}
