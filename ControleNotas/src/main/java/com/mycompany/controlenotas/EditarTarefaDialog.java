package com.mycompany.controlenotas;

import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;

public class EditarTarefaDialog extends JDialog {

    private final JTextField inputTitulo;
    private final JTextArea inputDescricao;
    private final JTextField inputData;
    private final JTextField inputValorMax;
    private final JTextField inputNota;

    private final JComboBox<String> selectMateria;
    private final JComboBox<String> selectTipo;

    private final Tarefa tarefa;

    public EditarTarefaDialog(Window parent, Tarefa tarefa) {
        super(parent, "Editar Tarefa", ModalityType.APPLICATION_MODAL);

        this.tarefa = tarefa;

        setSize(500, 480);
        setLocationRelativeTo(parent);
        setLayout(new BorderLayout());

        JPanel form = new JPanel(new GridLayout(0, 2, 10, 10));

        // CAMPOS
        selectMateria = new JComboBox<>();
        carregarMaterias();

        selectTipo = new JComboBox<>();
        carregarTipos();

        inputTitulo = new JTextField(tarefa.getTitulo());
        inputDescricao = new JTextArea(tarefa.getDescricao(), 4, 20);
        inputData = new JTextField(tarefa.getData().toString());
        inputValorMax = new JTextField(String.valueOf(tarefa.getValorMax()));
        inputNota = new JTextField(String.valueOf(tarefa.getNota()));

        // selecionar matéria da tarefa
        selectMateria.setSelectedItem(tarefa.getMateria());

        // selecionar tipo da tarefa
        selectTipo.setSelectedItem(tarefa.getTipo());

        // ADICIONA NO FORM
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

        form.add(new JLabel("Nota:"));
        form.add(inputNota);

        form.add(new JLabel("Data:"));
        form.add(inputData);

        add(form, BorderLayout.CENTER);

        // BOTÃO SALVAR
        JButton salvar = new JButton("Salvar alterações");
        salvar.addActionListener(e -> salvarAlteracoes());

        add(salvar, BorderLayout.SOUTH);
    }

    // ---------------------------
    // SALVAR ALTERAÇÕES (UPDATE)
    // ---------------------------
    private void salvarAlteracoes() {
        
        System.out.println("ID materia: " + getIdMateria(selectMateria.getSelectedItem().toString()));
        System.out.println("ID tipo: " + getIdTipo(selectTipo.getSelectedItem().toString()));


        String sql = """
            UPDATE avaliacoes 
            SET materia_id = ?, tipo_id = ?, titulo = ?, descricao = ?, valor_max = ?, nota = ?, data = ?
            WHERE id = ?
        """;

        try (Connection conn = db.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, getIdMateria(selectMateria.getSelectedItem().toString()));
            stmt.setInt(2, getIdTipo(selectTipo.getSelectedItem().toString()));
            stmt.setString(3, inputTitulo.getText().trim());
            stmt.setString(4, inputDescricao.getText().trim());
            stmt.setDouble(5, Double.parseDouble(inputValorMax.getText().trim()));
            stmt.setDouble(6, Double.parseDouble(inputNota.getText().trim()));
            stmt.setString(7, inputData.getText().trim());
            stmt.setInt(8, tarefa.getId());

            stmt.executeUpdate();

            JOptionPane.showMessageDialog(this, "Tarefa atualizada com sucesso!");
            dispose();

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this,
                    "Erro ao salvar alterações:\n" + ex.getMessage());
        Logger.getLogger(RegistroTarefaDialog.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    // -------------------------
    // CARREGAR MATERIAS
    // -------------------------
    private void carregarMaterias() {
        String sql = "SELECT materia FROM materias ORDER BY materia";

        try (Connection conn = db.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql);
             var rs = stmt.executeQuery()) {

            while (rs.next()) {
                selectMateria.addItem(rs.getString("materia"));
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro ao carregar matérias");
        }
    }

    // -------------------------
    // CARREGAR TIPOS
    // -------------------------
    private void carregarTipos() {
        String sql = "SELECT avaliacao FROM tipos_avaliacao ORDER BY avaliacao";

        try (Connection conn = db.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql);
             var rs = stmt.executeQuery()) {

            while (rs.next()) {
                selectTipo.addItem(rs.getString("avaliacao"));
            }

    // Mensagem de erro
    } catch (Exception ex) {
        Logger.getLogger(EditarTarefaDialog.class.getName())
              .log(Level.SEVERE, null, ex);
        JOptionPane.showMessageDialog(this, "Erro ao carregar tipos:\n" + ex.getMessage());
    }
    }

    // -------------------------
    // OBTER ID DA MATERIA
    // -------------------------
    private int getIdMateria(String nome) {
        String sql = "SELECT id FROM materias WHERE materia = ? LIMIT 1";

        try (Connection conn = db.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, nome);
            var rs = stmt.executeQuery();

            if (rs.next()) return rs.getInt(1);

        } catch (Exception ex) {
            Logger.getLogger(RegistroTarefaDialog.class.getName()).log(Level.SEVERE, null, ex);
        }
        return -1;
    }

    // -------------------------
    // OBTER ID DO TIPO
    // -------------------------
    private int getIdTipo(String tipo) {
        String sql = "SELECT id FROM tipos_avaliacao WHERE avaliacao = ? LIMIT 1";

        try (Connection conn = db.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, tipo);
            var rs = stmt.executeQuery();

            if (rs.next()) return rs.getInt(1);

        } catch (Exception ex) {
            Logger.getLogger(RegistroTarefaDialog.class.getName()).log(Level.SEVERE, null, ex);
        }
        return -1;
    }
}

