package model;

import java.util.ArrayList;

public class Disciplina {
    private int cargaHoraria, codigo;
    private String nomeDisciplina;

    private ArrayList<Professor> professores = new ArrayList<Professor>();

    public Disciplina(String nomeDisciplina, int codigo, int cargaHoraria) {
        this.nomeDisciplina = nomeDisciplina;
        this.codigo = codigo;
        this.cargaHoraria = cargaHoraria;
    }

    public ArrayList<Professor> getProfessores() {
        return professores;
    }
    public String getNomeDisciplina() {
        return nomeDisciplina;
    }
    public void setNomeDisciplina(String nomeDisciplina) {
        this.nomeDisciplina = nomeDisciplina;
    }

    public void adicionarProfessor(Professor professor) {
        this.professores.add(professor);
    }

    public int getCargaHoraria() {
        return cargaHoraria;
    }

    public void setCargaHoraria(int cargaHoraria) {
        this.cargaHoraria = cargaHoraria;
    }

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }
}
