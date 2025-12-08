package com.mycompany.controlenotas;

import java.awt.*;
import javax.swing.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger; //registrar o erro em um caderno organizado com data, hora e tipo de problema"
//JFrame - Janela
//JLabel - Textos não editáveis ou ícones
//JPanel - Área onde abriga e organiza componentes inseridos
//JTextArea - Espaço para inserir e visualizar textos
//JOptionPane - Janelas de Diálogo, serve para exibir: 
//              mensagens de aviso, erros, confirmações, perguntas, input

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
        gbc.gridwidth = 3;       // ocupa 3 colunas
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

        gbc.gridy = 1; 
        gbc.gridwidth = 2;
        painel.add(subtitulo, gbc);

        // -----------------------
        // COMPONENTE 2: LABEL: "Nome"
        // -----------------------
        JLabel labelNome = new JLabel("Nome completo: ");
        
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        painel.add(labelNome, gbc);

        // -----------------------
        // COMPONENTE 2.1: TextField: Nome
        // -----------------------
        JTextField nome = new JTextField(20);

        gbc.gridx = 1;
        gbc.gridy = 3;
        gbc.anchor = GridBagConstraints.CENTER; 
        painel.add(nome, gbc);
       
        // -------------------------------------------
        // COMPONENTE 3 e 4: Botões: Cadastro e Entrar
        // -------------------------------------------
        
        //Cria os botões
        JButton cadastrar = new JButton("Cadastrar");
        JButton entrar = new JButton("Entrar");
        
        // Painel para deixar lado a lado e centralizado
        JPanel painelBotoes = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 0));
        painelBotoes.add(cadastrar);
        painelBotoes.add(entrar);
        
        //Adiciona o painel de botões ABAIXO do campo nome
            gbc.gridx = 1;
            gbc.gridy = 4;
            gbc.gridwidth = 1;
            gbc.anchor = GridBagConstraints.CENTER;
            painel.add(painelBotoes, gbc);
            
        // -------------------------------------------------------------------
        // AÇÃO: Botão - Registra no banco e direciona para a próxima página
        // -------------------------------------------------------------------
        
        cadastrar.addActionListener(e -> { //Função
            String nomeDigitado = nome.getText().trim(); //Inicializa a variavel e separa(trim)
            if (nomeDigitado.isEmpty()) { //Se o nome digitado estiver vazio
                JOptionPane.showMessageDialog(janela, "Digite um nome!", "Erro", JOptionPane.ERROR_MESSAGE); //Mensagem de Erro
                return;
            }
            if (alunoExiste(nomeDigitado)) {
                JOptionPane.showMessageDialog(janela, "Este aluno já está cadastrado!", 
                                              "Aviso", JOptionPane.WARNING_MESSAGE);
            } else {
                cadastrarAluno(nomeDigitado, janela);
            }
            nome.setText("");
        });
            
        // -----------------------------------------------------------------------------------
        // AÇÃO: Botão - Consulta no banco, entra na conta e redireciona para a próxima janela
        // -----------------------------------------------------------------------------------
        
        entrar.addActionListener(e -> { //Função
            String nomeDigitado = nome.getText().trim(); //Lê a variavel e separa(trim)
            if (nomeDigitado.isEmpty()) { //Se o nome digitado estiver vazio
                JOptionPane.showMessageDialog(janela, "Digite um nome!", "Erro", JOptionPane.ERROR_MESSAGE); //Mensagem de Erro
                return;
            }
            
            if (!Entrar(nomeDigitado)) {
                JOptionPane.showMessageDialog(janela, "Aluno não encontrado!", "Erro", JOptionPane.ERROR_MESSAGE);
            } else {
                //Se chegou aqui aluno existe
                JOptionPane.showMessageDialog(janela, "Entrando...");
                
                int idAluno = buscarIdAluno(nomeDigitado); // você já deve ter isso no banco

                Trimestres tri = new Trimestres(idAluno);
                tri.setVisible(true);
                janela.dispose();               
            }
           
        });
        
        janela.add(painel); //Adiciona o painel na janela
        janela.setLocationRelativeTo(null);
        janela.setVisible(true);
    }
    
    
    // ----------------------------
    //           FUNÇÕES
    // ----------------------------   
    
    //-----------------
    // Cadastro de aluno
    //-----------------
    
private static void cadastrarAluno(String nome, Component parent) {
    String sql = "INSERT INTO alunos (aluno) VALUES (?)";

    try (Connection conn = db.conectar();
         PreparedStatement stmt = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {

        stmt.setString(1, nome);
        stmt.executeUpdate();

        // Pega o ID gerado
        int idAluno = -1;
        var rs = stmt.getGeneratedKeys();
        if (rs.next()) {
            idAluno = rs.getInt(1);
        }

        JOptionPane.showMessageDialog(parent, "Aluno cadastrado com sucesso!");

        // abre Trimestres com ID
        Trimestres tri = new Trimestres(idAluno);
        tri.setVisible(true);

        // fecha janela atual
        if (parent instanceof JFrame janelaAtual) {
            janelaAtual.dispose();
        }

    } catch (SQLException ex) {
        JOptionPane.showMessageDialog(parent, "Erro ao cadastrar aluno!", "Erro", JOptionPane.ERROR_MESSAGE);
        Logger.getLogger(ControleNotas.class.getName()).log(Level.SEVERE, null, ex);
    }
}


    
    //-------------------------------
    // Verifica existência do aluno
    //--------------------------------
    
    private static boolean alunoExiste(String nome) {
    String sql = "SELECT id FROM alunos WHERE aluno = ? LIMIT 1";

    try (Connection conn = db.conectar();
         PreparedStatement stmt = conn.prepareStatement(sql)) {

        stmt.setString(1, nome);
        return stmt.executeQuery().next(); // TRUE se encontrou, FALSE se não encontrou

    } catch (SQLException ex) {
        Logger.getLogger(ControleNotas.class.getName()).log(Level.SEVERE, null, ex);
        return true; // por segurança, assume que existe caso dê erro
    }
}
    
    //--------------------------------
    //            LOGIN ALUNO
    //--------------------------------
    
    private static boolean Entrar(String nome) {
        String sql = "SELECT id FROM alunos WHERE aluno = ? LIMIT 1";   
        
        try (Connection conn = db.conectar();
            PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1,nome);
            return stmt.executeQuery().next();
        
            
        } catch (SQLException ex) {
            Logger.getLogger(ControleNotas.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }
    
    //-----------------------------
    //       BUSCAR ID ALUNO
    //-----------------------------

    private static int buscarIdAluno(String nome) {
    String sql = "SELECT id FROM alunos WHERE aluno = ? LIMIT 1";

    try (Connection conn = db.conectar();
         PreparedStatement stmt = conn.prepareStatement(sql)) {

        stmt.setString(1, nome);
        var rs = stmt.executeQuery();

        if (rs.next()) {
            return rs.getInt("id");
        } else {
            return -1; // aluno não encontrado
        }

    } catch (SQLException ex) {
        Logger.getLogger(ControleNotas.class.getName()).log(Level.SEVERE, null, ex);
        return -1;
    }
}

    }