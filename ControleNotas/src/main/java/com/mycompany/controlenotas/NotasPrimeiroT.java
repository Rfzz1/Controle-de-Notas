package com.mycompany.controlenotas;

import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
//JFrame - Janela
//JLabel - Textos não editáveis ou ícones
//JPanel - Área onde abriga e organiza componentes inseridos
//JTextArea - Espaço para inserir e visualizar textos
//Editor Pane - Textos html

public class NotasPrimeiroT extends JFrame {
    
    private final int idAluno;
    private JTable tabela;
    private DefaultTableModel modelo;
    private java.util.List<Tarefa> listaTarefas = new ArrayList<>();
    
    public NotasPrimeiroT(int idAluno) {
        super("Boletim - 1º TRI");
        this.idAluno = idAluno;
        setSize(800, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation (JFrame.EXIT_ON_CLOSE);
        
        // Painel com GridBagLayout
        JPanel painel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        // Espaçamento padrão entre elementos
        gbc.insets = new Insets(5, 5, 5, 5);
        
        //--------------------------------------
        //           TOPO (Botões)
        //--------------------------------------
        JPanel topo = new JPanel(new FlowLayout(FlowLayout.LEFT));

        JButton registrar = new JButton("Registrar Nova Tarefa");
        topo.add(registrar);

        JButton voltar = new JButton("Voltar");
        topo.add(voltar);

        add(topo, BorderLayout.NORTH);
        
        //--------------------------------
        //          AÇÃO VOLTAR
        //--------------------------------
        voltar.addActionListener(e -> {
            PrimeiroTri Ptri = new PrimeiroTri(idAluno);
            Ptri.setVisible(true);
            dispose();
        });
        
        //--------------------------------
        //          AÇÃO REGISTRAR
        //--------------------------------
        
        registrar.addActionListener(e -> {
            RegistroTarPT RPtri = new RegistroTarPT(idAluno);
            RPtri.setVisible(true);
            dispose();
        });
        
        //-----------------------------
        //           TABELA
        //-----------------------------
        modelo = new DefaultTableModel(
                new String[]{"ID","Disciplina", "Nota"}, 
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
        
        
        tabela.addMouseListener(new java.awt.event.MouseAdapter() {
        @Override
        public void mouseClicked(java.awt.event.MouseEvent e) {
            if (e.getClickCount() == 2) { // duplo clique
                int row = tabela.getSelectedRow();
                int materiaId = (int) modelo.getValueAt(row, 0);

                // Abre a tela de tarefas por matéria
                new TarefasMateriaTela(idAluno, materiaId, 1).setVisible(true);
            }
        }
    });


        add(new JScrollPane(tabela), BorderLayout.CENTER);
        
        add(painel);
        SomarNotas();
        setVisible(true); 
        
    }
    
    //---------------------
    //       FUNÇÕES
    //---------------------
    
    // -------------------------------------------
    // SomarNotas
    // -------------------------------------------
    
private void SomarNotas() {
    listaTarefas.clear();
    modelo.setRowCount(0);

    String sql = """
        SELECT m.id AS materia_id, m.materia, SUM(a.nota) AS soma
        FROM avaliacoes a
        JOIN materias m ON m.id = a.materia_id
        WHERE a.trimestre = 1 AND a.aluno_id = ?
        GROUP BY m.id, m.materia
        ORDER BY m.materia
    """;

    try (Connection conn = db.conectar();
         PreparedStatement stmt = conn.prepareStatement(sql)) {

        stmt.setInt(1, idAluno);
        var rs = stmt.executeQuery();

        while (rs.next()) {
            int materiaId = rs.getInt("materia_id");
            String materia = rs.getString("materia");
            double soma = rs.getDouble("soma");

            modelo.addRow(new Object[]{
                materiaId,
                materia,
                soma
            });
        }

    } catch (Exception ex) {
        JOptionPane.showMessageDialog(this,
            "Erro ao carregar notas:\n" + ex.getMessage());
        Logger.getLogger(NotasPrimeiroT.class.getName()).log(Level.SEVERE, null, ex);
    }
}


    
}