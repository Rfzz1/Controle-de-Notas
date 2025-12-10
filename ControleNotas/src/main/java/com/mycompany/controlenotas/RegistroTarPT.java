package com.mycompany.controlenotas;

import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class RegistroTarPT extends JFrame {

    private final int idAluno;
    private JTable tabela;
    private DefaultTableModel modelo;
    private java.util.List<Tarefa> listaTarefas = new ArrayList<>();

    public RegistroTarPT(int idAluno) {
        super("Registro de Tarefas - 1º TRI");
        this.idAluno = idAluno;

        setSize(900, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        //--------------------------------------
        //           TOPO (Botões)
        //--------------------------------------
        JPanel topo = new JPanel(new FlowLayout(FlowLayout.LEFT));

        JButton adicionar = new JButton("+");
        topo.add(adicionar);

        JButton editar = new JButton("Editar");
        topo.add(editar);
        
        JButton visu = new JButton("Boletim - 1º Trimestre");
        topo.add(visu);

        JButton voltar = new JButton("Voltar");
        topo.add(voltar);

        add(topo, BorderLayout.NORTH);

        //--------------------------------------
        //           TABELA
        //--------------------------------------
        modelo = new DefaultTableModel(
                new String[]{"ID","Matéria", "Tipo", "Título","Descrição","Valor Máximo","Nota","Data"}, 
                0
        ) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // impede edição direta
            }
        };

        tabela = new JTable(modelo);
        tabela.setRowHeight(25);
        tabela.getColumnModel().getColumn(0).setMinWidth(0);
        tabela.getColumnModel().getColumn(0).setMaxWidth(0);
        tabela.getColumnModel().getColumn(0).setWidth(0);


        add(new JScrollPane(tabela), BorderLayout.CENTER);

        //--------------------------------------
        //           AÇÕES
        //--------------------------------------

        adicionar.addActionListener(e -> {
            RegistroTarefaDialog dialog = new RegistroTarefaDialog(
                    this, idAluno, 1
            );
            dialog.setVisible(true);
            listarTarefas();
        });

        editar.addActionListener(e -> editarTarefa());
        
        visu.addActionListener(e -> {
            NotasPrimeiroT NPT = new NotasPrimeiroT(idAluno);
            NPT.setVisible(true);
            dispose();
        });

        voltar.addActionListener(e -> {
            PrimeiroTri p = new PrimeiroTri(idAluno);
            p.setVisible(true);
            dispose();
        });

        //--------------------------------------
        // Carrega ao abrir
        //--------------------------------------
        listarTarefas();
        setVisible(true);
    }

    // -------------------------------------------
    // CARREGA LISTA DE TAREFAS DO BANCO
    // -------------------------------------------
private void listarTarefas() {
    listaTarefas.clear();
    modelo.setRowCount(0);

    String sql = """
        SELECT a.id,
               a.titulo,
               a.descricao,
               a.data,
               a.valor_max,
               a.nota,
               m.materia AS materia,
               t.avaliacao AS tipo
        FROM avaliacoes a
        JOIN materias m ON m.id = a.materia_id
        JOIN tipos_avaliacao t ON t.id = a.tipo_id
        WHERE a.aluno_id = ?
          AND a.trimestre = 1
        ORDER BY a.data
    """;

    try (Connection conn = db.conectar();
         PreparedStatement stmt = conn.prepareStatement(sql)) {

        stmt.setInt(1, idAluno);
        var rs = stmt.executeQuery();

        while (rs.next()) {
            Tarefa tar = new Tarefa();
            tar.setId(rs.getInt("id"));
            tar.setTitulo(rs.getString("titulo"));
            tar.setDescricao(rs.getString("descricao"));
            tar.setData(rs.getDate("data").toLocalDate());
            tar.setMateria(rs.getString("materia"));
            tar.setTipo(rs.getString("tipo"));
            tar.setValorMax(rs.getDouble("valor_max"));
            tar.setNota(rs.getDouble("nota"));

            listaTarefas.add(tar);

            modelo.addRow(new Object[]{
                tar.getId(),
                tar.getMateria(),
                tar.getTipo(),
                tar.getTitulo(),
                tar.getDescricao(),
                tar.getValorMax(),
                tar.getNota(),
                tar.getData()
            });
        }

    } catch (Exception ex) {
        JOptionPane.showMessageDialog(this, 
            "Erro ao carregar tarefas:\n" + ex.getMessage());
        Logger.getLogger(RegistroTarPT.class.getName()).log(Level.SEVERE, null, ex);
    }
}


    // -------------------------------------------
    //          EDITAR TAREFA SELECIONADA
    // -------------------------------------------
    private void editarTarefa() {
        int row = tabela.getSelectedRow();

        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Selecione uma tarefa!");
            return;
        }

        int idSelecionado = Integer.parseInt(tabela.getValueAt(row, 0).toString());

        Tarefa tarefa = listaTarefas.stream()
                .filter(t -> t.getId() == idSelecionado)
                .findFirst()
                .orElse(null);

        if (tarefa == null) {
            JOptionPane.showMessageDialog(this, "Erro: tarefa não encontrada!");
            return;
        }

        // abre popup de edição (mesmo popup, mas em modo edição)
        EditarTarefaDialog edit = new EditarTarefaDialog(this, tarefa);
        edit.setVisible(true);

        listarTarefas(); // recarregar após edição
    }
}
