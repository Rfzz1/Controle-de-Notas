package com.mycompany.controlenotas;

import java.awt.*;
import javax.swing.*;
//JFrame - Janela
//JLabel - Textos não editáveis ou ícones
//JPanel - Área onde abriga e organiza componentes inseridos
//JTextArea - Espaço para inserir e visualizar textos
//Editor Pane - Textos html

public class NotasPrimeiroT extends JFrame {
    
    public NotasPrimeiroT() {
        super("Minhas Notas - 1º TRI");
        setSize(800, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation (JFrame.EXIT_ON_CLOSE);
    }
    
}