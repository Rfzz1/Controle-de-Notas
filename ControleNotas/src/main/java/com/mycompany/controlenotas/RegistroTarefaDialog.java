package com.mycompany.controlenotas;

import java.awt.*;
import java.sql.Connection; // Conexão com o banco
import java.sql.PreparedStatement;
import java.util.logging.Level; //Log de erro
import java.util.logging.Logger;
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
    
    private final JComboBox<String> selectMateria; // Menu Suspenso com as matérias
    private final JComboBox<String> selectTipo;    // Prova/Trabalho/Simulado/Seminário/Atividade Extra
    private final JTextField inputTitulo;          // Título da Avaliação
    private final JTextArea inputDescricao;        // Detalhamento/Descrição
    private final JTextField inputValorMax;        // Nota máxima na prova
    private final JTextField inputNota;            // Nota da prova
    private final JTextField inputData;            // Data da Avaliação

    private final int idAluno;      // O aluno que está sendo registrado
    private final int trimestre;    // Trimestre da tela atual
    
    
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
        
        // Matérias
        selectMateria = new JComboBox<>();
        carregarMaterias();
        // Tipo de avaliação
        
        selectTipo = new JComboBox<>();
        carregarTipos();

       // Outros campos
       
       inputTitulo = new JTextField(); //Titulo
       inputDescricao = new JTextArea(4, 20); // Descrição
       inputValorMax = new JTextField(); //Valor máximo
       inputNota = new JTextField(); //Nota
       inputData = new JTextField("2025-12-04"); //Data
       
        //------------------------------------
        //       Adicionando ao painel
        //------------------------------------
        
        form.add(new JLabel("Matéria:"));
        form.add(selectMateria);

        form.add(new JLabel("Tipo:"));
        form.add(selectTipo);

        form.add(new JLabel("Título:"));
        form.add(inputTitulo);

        form.add(new JLabel("Descrição:"));
        form.add(new JScrollPane(inputDescricao));

        form.add(new JLabel("Valor máximo:"));
        form.add(inputValorMax);

        form.add(new JLabel("Nota obtida:"));
        form.add(inputNota);

        form.add(new JLabel("Data:"));
        form.add(inputData);

        add(form, BorderLayout.CENTER);
        
        //-------------------------------
        //      Botão SALVAR + AÇÃO
        //-------------------------------
        
        JButton salvar = new JButton("Salvar");
        salvar.addActionListener(e -> salvarAvaliacao());
        
        add(salvar, BorderLayout.SOUTH);

    }
    
    //-------------------
    //       FUNÇÕES
    //-------------------
    
    //---------------------------------------
    //        BUSCAR ID DA MATÉRIA
    //--------------------------------------
    
    private int getIdMateria(String materia) { //Função
    String sql = "SELECT id FROM materias WHERE materia = ? LIMIT 1"; //Busca todas materias da tabela

    try (Connection conn = db.conectar(); //Conecta ao banco
         PreparedStatement stmt = conn.prepareStatement(sql)) {

        stmt.setString(1, materia); // Substitui o primeiro ? da busca pelo valor da variável matéria
        var rs = stmt.executeQuery(); //Executa a consulta

        if (rs.next()) return rs.getInt(1);

    } catch (Exception ex) { // Erro
        Logger.getLogger(RegistroTarefaDialog.class.getName()).log(Level.SEVERE, null, ex);
    }
    return -1; // Retorno caso a matéria não exista
}
    
    //--------------------------------
    //        BUSCAR ID TIPO
    //--------------------------------
    
    private int getIdTipo(String avaliacao) {
    String sql = "SELECT id FROM tipos_avaliacao WHERE avaliacao = ? LIMIT 1"; //Busca todos os tipos de avaliação

    try (Connection conn = db.conectar(); //Conecta ao banco
         PreparedStatement stmt = conn.prepareStatement(sql)) {

        stmt.setString(1, avaliacao); // Substitui o primeiro ? da busca pelo valor da variável avaliação
        var rs = stmt.executeQuery(); //Executa a consulta

        if (rs.next()) return rs.getInt(1);

    } catch (Exception ex) { // Erro
        Logger.getLogger(RegistroTarefaDialog.class.getName()).log(Level.SEVERE, null, ex);
    }
    return -1; //Retorno caso o tipo não exista
}

    //----------------------------
    //       SALVAR NO BANCO
    //----------------------------
    
private void salvarAvaliacao() {

    //Variáveis
    String materia = selectMateria.getSelectedItem().toString();
    String tipo = selectTipo.getSelectedItem().toString(); 
    String titulo = inputTitulo.getText().trim();
    String descricao = inputDescricao.getText().trim();
    String valorMaxStr = inputValorMax.getText().trim();
    String notaStr = inputNota.getText().trim();
    String data = inputData.getText().trim();

    if (titulo.isEmpty() || valorMaxStr.isEmpty() || notaStr.isEmpty()) { //Se campo está vazio
        JOptionPane.showMessageDialog(this, "Preencha todos os campos!");
        return;
    }
    
    // Variável para valor da prova e nota
    double valorMax = Double.parseDouble(valorMaxStr);
    double nota = Double.parseDouble(notaStr);

    // Variável matéria e tipo avaliação
    int tipoId = selectTipo.getSelectedIndex() + 1;
    int materiaId = selectMateria.getSelectedIndex() + 1;

    //Insere no banco
    String sql = """
        INSERT INTO avaliacoes
        (aluno_id, trimestre, tipo_id, titulo, descricao, valor_max, nota, data, materia_id)
        VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)
    """;

    //Conecta ao banco e registra
    try (Connection conn = db.conectar();
         PreparedStatement stmt = conn.prepareStatement(sql)) {

        stmt.setInt(1, idAluno);
        stmt.setInt(2, trimestre);
        stmt.setInt(3, tipoId);
        stmt.setString(4, titulo);
        stmt.setString(5, descricao);
        stmt.setDouble(6, valorMax);
        stmt.setDouble(7, nota);
        stmt.setString(8, data);
        stmt.setInt(9, materiaId);

        stmt.executeUpdate();

        JOptionPane.showMessageDialog(this, "Avaliação registrada com sucesso!");
        dispose();

    // Mensagem de erro
    } catch (Exception ex) {
        JOptionPane.showMessageDialog(this, "Erro ao salvar!", "Erro", JOptionPane.ERROR_MESSAGE);
        Logger.getLogger(RegistroTarefaDialog.class.getName()).log(Level.SEVERE, null, ex);
    }
}

//---------------------
//       MATÉRIAS
//---------------------
    
private void carregarMaterias() {
    //Busca as matérias
    String sql = "SELECT materia FROM materias ORDER BY materia";

    //COnecta ao banco e exibe
    try (Connection conn = db.conectar();
         PreparedStatement stmt = conn.prepareStatement(sql);
         var rs = stmt.executeQuery()) {

        while (rs.next()) {
            selectMateria.addItem(rs.getString("materia"));
        }

    // Mensagem de erro
    } catch (Exception ex) {
    Logger.getLogger(RegistroTarefaDialog.class.getName())
          .log(Level.SEVERE, null, ex);
        JOptionPane.showMessageDialog(this, "Erro ao carregar matérias:\n" + ex.getMessage());
    }

}

//-----------------------------
//        TIPO AVALIAÇÃO
//-----------------------------

private void carregarTipos() {
    // Busca os tipos de avaliação
    String sql = "SELECT avaliacao FROM tipos_avaliacao ORDER BY avaliacao";

    //Conecta ao banco e exibe
    try (Connection conn = db.conectar();
         PreparedStatement stmt = conn.prepareStatement(sql);
         var rs = stmt.executeQuery()) {

        while (rs.next()) {
            selectTipo.addItem(rs.getString("avaliacao"));
        }

    // Mensagem de erro
    } catch (Exception ex) {
        Logger.getLogger(RegistroTarefaDialog.class.getName())
              .log(Level.SEVERE, null, ex);
        JOptionPane.showMessageDialog(this, "Erro ao carregar tipos:\n" + ex.getMessage());
    }
}
    
}