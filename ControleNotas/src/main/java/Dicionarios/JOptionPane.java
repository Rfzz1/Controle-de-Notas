package Dicionarios;

import java.awt.*;
import javax.swing.*;

public class JOptionPane {
    public static void main(String[] args) {
        JFrame janela = new JFrame("Controle de Notas");
        janela.setSize(800,600);
        janela.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        JPanel painel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        
        //-------------------------------------
        //      EXPLICANDO JOptionPane
        //-------------------------------------
        
        //--------------------------------------------------------
        // Usada para mostrar janelas de diálogo prontas (Pop-up)
        // Pode exibir:
        //--------------------------------------------------------
        
        //----------------------
        // Mensagens de Erro
        // Afirmações
        // Perguntas de Confirmações
        // Mensagens de Aviso    
        // Caixas de Input       
        //------------------------
        
        
        //------------------
        //      ERRO
        //------------------
        
        //JOptionPane.showMessageDialog(null, "Erro ao cadastrar!", "Erro", JOptionPane.ERROR_MESSAGE);
        
        //------------------
        //   AFIRMAÇÃO
        //------------------
        
        //JOptionPane.showMessageDialog(null, "Cadastro realizado!");

        //--------------------------------------
        //      PERGUNTAS DE CONFIRMAÇÃO
        //--------------------------------------
        
        //int resposta = JOptionPane.showConfirmDialog(null, "Deseja sair?");


        //------------------
        //      AVISO
        //------------------
        
        //JOptionPane.showMessageDialog(null, "Aluno já existe!", "Aviso", JOptionPane.WARNING_MESSAGE);
        
        //------------------
        //      INPUT
        //------------------
        
        //String nome = JOptionPane.showInputDialog("Digite seu nome:");

          
        janela.add(painel);
    }
}
