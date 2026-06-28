package pong.visual;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.IOException;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

import pong.jogo.EstadoDeJogo;
import pong.servidor.ClienteJogo;
import pong.servidor.ServidorJogo;
import pong.sons.GerenciadorDeSom;

// Classe responsavel atuar como controlador da interface
public class PainelPrincipal extends JFrame {
    private PainelDoJogo painelDoJogo;
    private EstadoDeJogo estado;
    private ClienteJogo cliente;
    
    private Thread loopLocal;
    private volatile boolean rodarLoop;

    // Cria a janela principal
    public PainelPrincipal() {
        setTitle("Pong Multiplayer");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        
        mostrarMenu();
        
        setLocationRelativeTo(null);
        setVisible(true);
    }
    
    // Exibe a tela principal do jogo
    public void mostrarMenu(){
        // Caso volte pro menu, desconecta da rede
        if(cliente != null){
            cliente.desconectar();
            cliente = null;
        }
        
        PainelMenu menu = new PainelMenu(this);
        setContentPane(menu);
        pack();
        revalidate();
        repaint();
    }
    
    // Troca o conteudo da tela para o jogo + foco das teclas no jogo
    public void mostrarJogo(){
        setContentPane(painelDoJogo);
        revalidate();
        SwingUtilities.invokeLater(() -> painelDoJogo.requestFocusInWindow()); // puxa o foco do teclado para o jogo
    }
    
    public void mostrarPontuacao(){
        PainelPontuacao pontuacao = new PainelPontuacao(this);
        setContentPane(pontuacao);
        revalidate();
        repaint();
    }
    
    // Modo local
    public void iniciarJogoLocal(){
        rodarLoop = false;
        
        if (loopLocal != null && loopLocal.isAlive()) {
            try {
                loopLocal.join(200); // aguarda até 200ms
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
        
        estado = new EstadoDeJogo();
        painelDoJogo = new PainelDoJogo(estado);
        estado.resetar();
        GerenciadorDeSom.tocar("iniciar.wav");
        
        // Ação de clicar na tecla
        painelDoJogo.addKeyListener(new KeyAdapter(){
        @Override
        public void keyPressed(KeyEvent e){
            switch(e.getKeyCode()){
                case KeyEvent.VK_W: estado.getRaquete1().setCima(true); break;
                case KeyEvent.VK_S: estado.getRaquete1().setBaixo(true); break;
                
                case KeyEvent.VK_UP: estado.getRaquete2().setCima(true); break;
                case KeyEvent.VK_DOWN: estado.getRaquete2().setBaixo(true); break;
                
                case KeyEvent.VK_R:
                    if (estado.isGameOver()) iniciarJogoLocal();
                    break;
                case KeyEvent.VK_ESCAPE: rodarLoop = false; mostrarMenu(); break;
            }
        }
        
        // Ação de soltar a tecla
        @Override
        public void keyReleased(KeyEvent e){
            switch(e.getKeyCode()){
                case KeyEvent.VK_W: estado.getRaquete1().setCima(false); break;
                case KeyEvent.VK_S: estado.getRaquete1().setBaixo(false); break;
                
                case KeyEvent.VK_UP: estado.getRaquete2().setCima(false); break;
                case KeyEvent.VK_DOWN: estado.getRaquete2().setBaixo(false); break;
            }
        }
        });
        
        mostrarJogo();
        rodarLoop = true;
        
        // Thread para rodar o jogo de forma separada
        loopLocal = new Thread(() -> {
            while(rodarLoop && !estado.isGameOver()){
                estado.update();
                SwingUtilities.invokeLater(painelDoJogo::repaint); // Redesenhar a tela
                try {
                    Thread.sleep(16);
                } catch (InterruptedException e) {
                    break;
                }
            }
            
            if (!rodarLoop) return;
            
            SwingUtilities.invokeLater(painelDoJogo::repaint); // Já fora do loop, faz com que apareça a tela de vencedor
            
            pong.pontuacao.GerenciadorDePontuacao salvador = new pong.pontuacao.GerenciadorDePontuacao();
            salvador.salvarPontuacao("Jogador1", estado.getScore1(), "Jogador2", estado.getScore2());
            
        });
        
        loopLocal.setDaemon(true);
        loopLocal.start();
    }
    
    // Modo de rede (host)
    public void iniciarComoServidor(){
        new Thread(() -> {
            try {
                new ServidorJogo().start();
            } catch (IOException e) {
                SwingUtilities.invokeLater(() -> JOptionPane.showMessageDialog(this, "Erro ao iniciar o servidor:\n" +
                        e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE)
                );
            }
        }).start();
        
        String ip = JOptionPane.showInputDialog(this, "Servidor iniciado!\nEndereço para conectar:", "localhost");
    
        if(ip != null) iniciarJogoRede(ip.trim());
    }
    
    // Modo rede - cliente
    public void iniciarComoCliente(){
       String ip = JOptionPane.showInputDialog(this, "IP do servidor:", "localhost");
       if(ip != null) iniciarJogoRede(ip.trim());
    }
    
    // Inicia uma partida conectando no servidor informado
    private void iniciarJogoRede(String ip){
        new Thread(() -> {
            EstadoDeJogo estado = new EstadoDeJogo();
            ClienteJogo cliente = new ClienteJogo(estado);
            
            if(!cliente.conectado(ip)){
                SwingUtilities.invokeLater(() -> {
                    JOptionPane.showMessageDialog(this, "Não foi possivel conectar ao servidor.\n Verifique se o IP está correto ou se o Servidor está aberto.", "Erro", JOptionPane.ERROR_MESSAGE);            
                });
                return;
            }
            
            SwingUtilities.invokeLater(() -> {
                this.estado = estado;
                this.cliente = cliente;
                this.painelDoJogo = new PainelDoJogo(estado);
                
                this.painelDoJogo.setNumeroJogador(cliente.getNumeroJogador());
                this.cliente.setEstadoUpdate(() -> SwingUtilities.invokeLater(painelDoJogo::repaint)); // callback para o cliente receber atualizações do estado
                
                this.cliente.setAoOutroDesconectar(() -> {
                    SwingUtilities.invokeLater(() -> {
                        JOptionPane.showMessageDialog(
                            this,
                            "O outro jogador saiu da partida.",
                            "Partida encerrada",
                            JOptionPane.INFORMATION_MESSAGE
                        );
                        mostrarMenu();
                    });
                });
                
                // Teclado envia comandos para o servidor
                painelDoJogo.addKeyListener(new KeyAdapter(){
                    @Override
                    public void keyPressed(KeyEvent e){
                        switch(e.getKeyCode()){
                            case KeyEvent.VK_W:
                            case KeyEvent.VK_UP: cliente.enviarInput("CIMA"); break;
                            case KeyEvent.VK_S:
                            case KeyEvent.VK_DOWN: cliente.enviarInput("BAIXO"); break;
                            case KeyEvent.VK_R:
                                if (estado.isGameOver()) {
                                    cliente.enviarInput("REINICIAR"); // Só é possivel reiniciar quando o jogo acabar
                                }
                                break;
                            case KeyEvent.VK_ESCAPE:
                                cliente.desconectar();
                                SwingUtilities.invokeLater(PainelPrincipal.this::mostrarMenu);
                                break;
                        }
                    }

                    @Override
                    public void keyReleased(KeyEvent e){
                        switch (e.getKeyCode()) {
                            case KeyEvent.VK_W:
                            case KeyEvent.VK_UP:
                            case KeyEvent.VK_S:
                            case KeyEvent.VK_DOWN: cliente.enviarInput("PARAR"); break;
                        }
                    }
                });
                
                mostrarJogo();
            });
        }).start();
    }
}
