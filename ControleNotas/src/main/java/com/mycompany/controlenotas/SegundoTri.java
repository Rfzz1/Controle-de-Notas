package com.mycompany.controlenotas;

import java.awt.*;
import javax.swing.*;
//JFrame - Janela
//JLabel - Textos não editáveis ou ícones
//JPanel - Área onde abriga e organiza componentes inseridos
//JTextArea - Espaço para inserir e visualizar textos
//Editor Pane - Textos html

public class SegundoTri extends JFrame{
    private final int idAluno;
    public SegundoTri(int idAluno) {
        super("Seguno Trimestre");
        this.idAluno = idAluno;
        setSize(800, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
                // Painel com GridBagLayout
        JPanel painel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        // Espaçamento padrão entre elementos
        gbc.insets = new Insets(5, 5, 5, 5);
        
        //-------------------------------
        //              Título
        //-------------------------------
        
        JEditorPane titulo = new JEditorPane("text/html", "<h1 'text-align:center; background:transparent;'>Título</h1>");
        titulo.setEditable(false);
        titulo.setOpaque(false);
        
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 3;       // ocupa 3 colunas
        gbc.anchor = GridBagConstraints.CENTER;
        painel.add(titulo, gbc);
        
        //-------------------------------------------------
        // REGISTRO DE PROVAS/TRABALHOS E VISUALIZAR NOTAS
        //-------------------------------------------------
        
        //Cria os botões
        JButton registro = new JButton("Registro de Provas/Trabalhos");
        JButton notas = new JButton("Visualize suas notas");
        
        // Painel para deixar lado a lado e centralizado
        JPanel painelBotoes = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 0));
        painelBotoes.add(registro);
        painelBotoes.add(notas);
        
        //Adiciona o painel de botões ABAIXO do campo nome
            gbc.gridx = 1;
            gbc.gridy = 1;
            gbc.gridwidth = 1;
            gbc.anchor = GridBagConstraints.CENTER;
            painel.add(painelBotoes, gbc);
        
        //---------------------------------------
        // AÇÃO BOTÃO REGISTRAR - Abre nova tela
        //---------------------------------------
        
        registro.addActionListener(e -> {
            RegistroTarST reg = new RegistroTarST();
            reg.setVisible(true);
            dispose();
        });
        
        //---------------------------------------
        // AÇÃO BOTÃO NOTAS - Abre nova tela
        //---------------------------------------        
        
        notas.addActionListener(e -> {
            NotasSegundoT reg = new NotasSegundoT();
            reg.setVisible(true);
            dispose();
        });
        
        //--------------------------------
        //         BOTÃO DE VOLTAR
        //--------------------------------
        JButton voltar = new JButton("Voltar");
        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.CENTER;
        painel.add(voltar,gbc);
        
        //--------------------------------
        //          AÇÃO VOLTAR
        //--------------------------------
        voltar.addActionListener(e -> {
            Trimestres tri = new Trimestres(idAluno);
            tri.setVisible(true);
            dispose();
        });
        
        add(painel);
        setVisible(true);
}
}