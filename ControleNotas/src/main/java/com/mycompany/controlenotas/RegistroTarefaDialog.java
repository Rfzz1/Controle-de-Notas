package com.mycompany.controlenotas;

import java.awt.*;
import javax.swing.*;
//JFrame - Janela
//JLabel - Textos não editáveis ou ícones
//JPanel - Área onde abriga e organiza componentes inseridos
//JTextArea - Espaço para inserir e visualizar textos
//Editor Pane - Textos html

//Janela POP-UP que herda tudo do JDialog
public class RegistroTarefaDialog extends JDialog{

    //---------------------
    //      VARIÁVEIS
    //---------------------   
    
    private JComboBox<String> selectMateria; // Menu Suspenso com as matérias
    private JComboBox<String> selectTipo;    // Prova/Trabalho/Simulado/Seminário/Atividade Extra
    private JTextField inputTitulo;          // Título da Avaliação
    private JTextArea inputDescricao;        // Detalhamento/Descrição
    private JTextField inputValorMax;        // Nota máxima na prova
    private JTextField inputNota;            // Nota da prova
    private JTextField inputData;            // Data da Avaliação

    private int idAluno;      // O aluno que está sendo registrado
    private int trimestre;    // Trimestre da tela atual
    
    
    //--------------------------------------------------------
    //        FUNÇÃO QUE FAZ A CONSTRUÇÃO DO POP UP
    //--------------------------------------------------------
    
    public RegistroTarefaDialog(Window parent, int idAluno, int trimestre) {
        //Parent - Janela Principal (Quem chamou a função)
        //APPLICATION_MODAL - Bloqueia a tela principal até salvar/fechar
        super(parent, "Registrar Avaliação", ModalityType.APPLICATION_MODAL);
        
        
        // Salva os dados
        this.idAluno = idAluno;
        this.trimestre = trimestre;
        
        //---------------------------------------------
        //        CONFIGURAÇÃO BÁSICA DO POPUP
        //---------------------------------------------
        
        setSize(500, 450); // Tamanho
        setLocationRelativeTo(parent); // Abre centralizado
        setLayout(new BorderLayout());
        
        //-------------------------------------
        //        CRIAÇÃO DE FORMULÁRIO
        //-------------------------------------
        
        JPanel form = new JPanel(new GridLayout(0, 2, 10, 10)); // 2 Colunas
        
        //---------------------
        //       CAMPOS
        //---------------------
        
        selectMateria = new JComboBox<>(new String[] {
            "Matemática", "Português", "História", "Geografia"
        });


    }
    
}
