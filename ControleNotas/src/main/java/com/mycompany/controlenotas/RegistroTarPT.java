package com.mycompany.controlenotas;

import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;
//JFrame - Janela
//JLabel - Textos não editáveis ou ícones
//JPanel - Área onde abriga e organiza componentes inseridos
//JTextArea - Espaço para inserir e visualizar textos
//Editor Pane - Textos html

public class RegistroTarPT extends JFrame{
    private final int idAluno;
    public RegistroTarPT(int idAluno) {
        super("Registro de Tarefas - 1º TRI");
        this.idAluno = idAluno;
        setSize(800, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        //--------------------------------------------
        //        CRIAÇÃO DO PAINEL E PADDING
        //--------------------------------------------

        // Painel com GridBagLayout
        JPanel painel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        // Espaçamento padrão entre elementos
        gbc.insets = new Insets(5, 5, 5, 5);

        //---------------------
        //  BOTÃO: ADD TAREFA
        //---------------------
        JButton adicionar = new JButton("+");
            gbc.gridx = 0;
            gbc.gridy = 0;
            gbc.gridwidth = 1;
            gbc.anchor = GridBagConstraints.EAST;
            painel.add(adicionar, gbc);
            
        //---------------------
        //   AÇÃO: ADD TAREFA  
        //---------------------
        
        adicionar.addActionListener(e -> {
        RegistroTarefaDialog dialog = new RegistroTarefaDialog(
            this, 
            idAluno, 
            1 // trimestre já definido pela página
        );
        dialog.setVisible(true);
        });

        
        //---------------------
        //    BOTÃO: VOLTAR
        //---------------------
        JButton voltar = new JButton("Voltar");
            gbc.gridx = 1;
            gbc.gridy = 0;
            gbc.gridwidth = 1;
            gbc.anchor = GridBagConstraints.WEST;
            painel.add(voltar,gbc);       
        
        //---------------------
        //     AÇÃO: VOLTAR
        //---------------------
        voltar.addActionListener(e -> {
            Trimestres tri = new Trimestres(idAluno);
            tri.setVisible(true);
            dispose();
        });
        
        add(painel);
        setVisible(true);        
    }
    
    

//---------------------
//        FUNÇÕES
//---------------------

//---------------------
// CADASTRO DE TAREFAS
//---------------------

}