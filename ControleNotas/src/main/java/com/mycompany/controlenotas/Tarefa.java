package com.mycompany.controlenotas;

import java.time.LocalDate;

public class Tarefa {

    private int id;
    private String titulo;
    private String descricao;
    private LocalDate data;

    // Dados que vêm das tabelas relacionadas
    private String materia;   // nome da matéria
    private String tipo;      // nome do tipo de avaliação
    
    private double valorMax;
    private double nota;

    // -------------------------
    // GETTERS e SETTERS
    // -------------------------

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public LocalDate getData() {
        return data;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }

    public String getMateria() {
        return materia;
    }

    public void setMateria(String materia) {
        this.materia = materia;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
    
    public double getValorMax() {
    return valorMax;
}

    public void setValorMax(double valorMax) {
        this.valorMax = valorMax;
    }

    public double getNota() {
        return nota;
    }

    public void setNota(double nota) {
        this.nota = nota;
    }

    
}
