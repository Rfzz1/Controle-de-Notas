package com.mycompany.controlenotas;

import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
//JFrame - Janela
//JLabel - Textos não editáveis ou ícones
//JPanel - Área onde abriga e organiza componentes inseridos
//JTextArea - Espaço para inserir e visualizar textos
//Editor Pane - Textos html

public class TarefasMateriaTela extends JFrame {

    private final int idAluno;
    private final int idMateria;
    private final int trimestre;

    private JTable tabela;
    private DefaultTableModel modelo;

    public TarefasMateriaTela(int idAluno, int idMateria, int trimestre) {
        super("Tarefas da Matéria");
        this.idAluno = idAluno;
        this.idMateria = idMateria;
        this.trimestre = trimestre;

        setSize(700, 500);
        setLocationRelativeTo(null);

        modelo = new DefaultTableModel(
            new String[]{"Título", "Nota", "Valor Máx", "Data", "Tipo"}, 0
        );

        tabela = new JTable(modelo);
        add(new JScrollPane(tabela));

        carregarTarefas();
    }

    private void carregarTarefas() {
        String sql = """
            SELECT a.titulo, a.nota, a.valor_max, a.data, t.avaliacao
            FROM avaliacoes a
            JOIN tipos_avaliacao t ON t.id = a.tipo_id
            WHERE a.aluno_id = ? AND a.materia_id = ? AND a.trimestre = ?
            ORDER BY a.data
        """;

        try (Connection conn = db.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idAluno);
            stmt.setInt(2, idMateria);
            stmt.setInt(3, trimestre);

            var rs = stmt.executeQuery();

            while (rs.next()) {
                modelo.addRow(new Object[]{
                    rs.getString("titulo"),
                    rs.getDouble("nota"),
                    rs.getDouble("valor_max"),
                    rs.getString("data"),
                    rs.getString("avaliacao")
                });
            }

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Erro ao carregar tarefas:\n" + ex.getMessage());
        }
    }
}

