package pong.pontuacao;

import java.io.*; 
import java.util.*;

// Classe responsavel por salvar/carregar historico de partidas
public class GerenciadorDePontuacao {
    private static final String FILE_PATH = "pontuacao.txt";
    
    // Método para salvar o resultado de uma partida
    public void salvarPontuacao(String jogador1, int score1, String jogador2, int score2){
        String vencedor = score1 > score2 ? jogador1 : jogador2;
        String linha = jogador1 + " " + score1 + " x " + score2 + " " + jogador2 + " | Vencedor: " + vencedor;
        
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(FILE_PATH, true))) { // Abre o arquivo para editar
            bw.write(linha);
            bw.newLine();
            System.out.println("Placar Salvo: " + linha);
        } catch (IOException e) {
            System.out.println("Erro ao salvar o placar: " + e.getMessage());
        }
    }
    
    // Método para carregar todos os placares salvos dentro do arquivo
    public List<String> carregarPontuacao(){
        List<String> pontuacoes = new ArrayList<>();
        File f = new File(FILE_PATH);
        
        if(!f.exists()) return pontuacoes;
        
        try (BufferedReader br = new BufferedReader(new FileReader(f))) { // Abre o arquivo para leitura
            String linha;
            while((linha = br.readLine()) != null) {
                if(!linha.isEmpty()) pontuacoes.add(linha);
            }
        } catch (IOException e) {
            System.out.println("Erro ao ler o placar: " + e.getMessage());
        }
        
        return pontuacoes;
    }
}
