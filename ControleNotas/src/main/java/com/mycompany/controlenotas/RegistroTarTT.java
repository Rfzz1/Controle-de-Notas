package com.mycompany.controlenotas;

import java.awt.*;
import javax.swing.*;
//JFrame - Janela
//JLabel - Textos não editáveis ou ícones
//JPanel - Área onde abriga e organiza componentes inseridos
//JTextArea - Espaço para inserir e visualizar textos
//Editor Pane - Textos html

public class RegistroTarTT extends JFrame {
    public RegistroTarTT() {
        super("Registro de Tarefas - 3º TRI");
        setSize(800, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}