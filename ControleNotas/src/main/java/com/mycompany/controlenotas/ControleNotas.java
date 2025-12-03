package com.mycompany.controlenotas;

import java.awt.GridBagLayout;
import java.awt.*;
import javax.swing.*;
import javax.swing.JFrame; //Janela
import javax.swing.JLabel; //Textos não editáveis ou ícones
import javax.swing.JPanel; // Área onde abriga e organiza componentes inseridos
import javax.swing.JTextArea; // Espaço para inserir e visualizar textos

public class ControleNotas {
    public static void main(String[] args) {
        // Janela principal
        JFrame janela = new JFrame("Controle de Notas");
        janela.setSize(800,600);
        janela.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Painel com GridBagLayout
        JPanel painel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        // Espaçamento padrão entre elementos
        gbc.insets = new Insets(5, 5, 5, 5);

        // -----------------------
        // COMPONENTE 1: TÍTULO
        // -----------------------
        JEditorPane titulo = new JEditorPane("text/html",
            "<h1 style='text-align:center; background:transparent;'>Controle de Notas</h1>"
        );
        titulo.setEditable(false);
        titulo.setOpaque(false); // Fundo transparente
        
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 3;       // ocupa duas colunas
        gbc.anchor = GridBagConstraints.CENTER;
        painel.add(titulo, gbc);
        
        // -----------------------
        // COMPONENTE 1.1: SUBTÍTULO
        // -----------------------
        
        JEditorPane subtitulo = new JEditorPane("text/html",
            "<h2 style='text-align:center; background:transparent;'>Cadastro do Aluno</h2>"
        );
        subtitulo.setEditable(false);
        subtitulo.setOpaque(false); // Fundo transparente

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 3;       // ocupa duas colunas
        gbc.anchor = GridBagConstraints.CENTER;
        painel.add(subtitulo, gbc);

        // -----------------------
        // COMPONENTE 2: LABEL: "Nome"
        // -----------------------
        JLabel labelNome = new JLabel("Insira o nome completo do aluno: ");
        
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.EAST;  // alinha à direita
        painel.add(labelNome, gbc);

        // -----------------------
        // COMPONENTE 2.1: TextField: Nome
        // -----------------------
        JTextField nome = new JTextField(20);

        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.anchor = GridBagConstraints.WEST;  // alinha à esquerda
        painel.add(nome, gbc);
       
        // -----------------------
        // COMPONENTE 2.1: Botao: Cadastro
        // -----------------------
        
        JButton cadastrar = new JButton("Cadastrar");
            gbc.gridx = 0;
            gbc.gridy = 1;
            gbc.gridwidth = 2;
            gbc.anchor = GridBagConstraints.CENTER;
            painel.add(cadastrar, gbc);
            
            
            
        
        // -----------------------
        // Finalizando
        // -----------------------
        janela.add(painel);
        janela.setLocationRelativeTo(null); // centraliza na tela
        janela.setVisible(true);
    }
}
