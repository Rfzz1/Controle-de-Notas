package com.mycompany.controlenotas;

import java.awt.*;
import javax.swing.*;
//JFrame - Janela
//JLabel - Textos não editáveis ou ícones
//JPanel - Área onde abriga e organiza componentes inseridos
//JTextArea - Espaço para inserir e visualizar textos
//Editor Pane - Textos html

public class Trimestres extends JFrame {
    private final int idAluno;
    public Trimestres(int idAluno) {
        super("Trimestres");
        this.idAluno = idAluno;
        
        setSize(800, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Painel com GridBagLayout
        JPanel painel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        // Espaçamento padrão entre elementos
        gbc.insets = new Insets(5, 5, 5, 5);
        
        
        // -----------------------
        // COMPONENTE 2: BOtão - 1 trimestre
        // -----------------------
        
        JButton primeirotri = new JButton ("1º TRIMESTRE");
            gbc.gridx = 0;
            gbc.gridy = 1;
            gbc.gridwidth = 3;
            gbc.anchor = GridBagConstraints.CENTER;
            painel.add(primeirotri, gbc);
            
        // -----------------------
        // AÇÃO: Botão - 1 Trimestre
        // -----------------------
        
        primeirotri.addActionListener(e -> {
            PrimeiroTri ptri = new PrimeiroTri(idAluno);
            ptri.setVisible(true);
            dispose(); // fecha a tela atual (opcional)
        });
        
        // -----------------------
        // COMPONENTE 3: BOtão - 2 trimestre
        // -----------------------
        
        JButton segundotri = new JButton ("2º TRIMESTRE");
            gbc.gridx = 0;
            gbc.gridy = 2;
            gbc.gridwidth = 3;
            gbc.anchor = GridBagConstraints.CENTER;
            painel.add(segundotri, gbc);
            
        // -----------------------
        // AÇÃO: Botão - 2 Trimestre
        // -----------------------
        
        segundotri.addActionListener(e -> {
        SegundoTri stri = new SegundoTri(idAluno);
        stri.setVisible(true);
        dispose(); // fecha a tela atual (opcional)
    });
        
        // -----------------------
        // COMPONENTE 4: BOtão - 3 trimestre
        // -----------------------
        
        JButton terceirotri = new JButton ("3º TRIMESTRE");
            gbc.gridx = 0;
            gbc.gridy = 3;
            gbc.gridwidth = 3;
            gbc.anchor = GridBagConstraints.CENTER;
            painel.add(terceirotri, gbc);
            
        // -----------------------
        // AÇÃO: Botão - 3 Trimestre
        // -----------------------
        
        terceirotri.addActionListener(e -> {
        TerceiroTri ttri = new TerceiroTri(idAluno);
        ttri.setVisible(true);
        dispose(); // fecha a tela atual (opcional)
    });
        
        add(painel);
    }
    
}