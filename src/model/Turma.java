package model;

import java.util.ArrayList;

public class Turma {
    private int codigoTurma, anoLetivo;
    private Disciplina disciplina;
    private Professor professor;
    private ArrayList<Aluno> alunos = new ArrayList<>();

    public Turma(int codigoTurma, int anoLetivo, Disciplina disciplina, Professor professor) {
        this.codigoTurma = codigoTurma;
        this.anoLetivo = anoLetivo;
        this.disciplina = disciplina;
        this.professor = professor;
    }

    public void adicionarAluno(Aluno aluno) {
        this.alunos.add(aluno);
    }

    public int getCodigoTurma() {
        return codigoTurma;
    }

    public void setCodigoTurma(int codigoTurma) {
        this.codigoTurma = codigoTurma;
    }

    public ArrayList<Aluno> getAlunos() {
        return alunos;
    }


    public Professor getProfessor() {
        return professor;
    }

    public void setProfessor(Professor professor) {
        this.professor = professor;
    }

    public Disciplina getDisciplina() {
        return disciplina;
    }

    public void setDisciplina(Disciplina disciplina) {
        this.disciplina = disciplina;
    }

    public int getAnoLetivo() {
        return anoLetivo;
    }

    public void setAnoLetivo(int anoLetivo) {
        this.anoLetivo = anoLetivo;
    }
}

