package com.mycompany.controlenotas;

import java.awt.*;
import javax.swing.*;
//JFrame - Janela
//JLabel - Textos não editáveis ou ícones
//JPanel - Área onde abriga e organiza componentes inseridos
//JTextArea - Espaço para inserir e visualizar textos
//Editor Pane - Textos html

public class RegistroTarST extends JFrame{
    private final int idAluno;
    public RegistroTarST(int idAluno) {
        super("Registro de Tarefas - 2º TRI");
        this.idAluno = idAluno;
        setSize(800, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        // Painel com GridBagLayout
        JPanel painel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        // Espaçamento padrão entre elementos
        gbc.insets = new Insets(5, 5, 5, 5);
        
        //--------------------------------
        //         BOTÃO DE VOLTAR
        //--------------------------------
        JButton voltar = new JButton("Voltar");
        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.CENTER;
        painel.add(voltar, gbc);

        //--------------------------------
        //          AÇÃO VOLTAR
        //--------------------------------
        voltar.addActionListener(e -> {
            SegundoTri Stri = new SegundoTri(idAluno);
            Stri.setVisible(true);
            dispose();
        });
        
        add(painel);
        setVisible(true);
    }
}