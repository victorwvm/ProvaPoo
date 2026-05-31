package control;

import interfaces.GerenciadorCadastroAluno;
import interfaces.GerenciadorCadastroProfessor;
import model.Aluno;
import model.Professor;

import java.util.ArrayList;

public class Secretaria implements GerenciadorCadastroAluno, GerenciadorCadastroProfessor {

    private ArrayList<Aluno> listaAlunos = new ArrayList<>();
    private ArrayList<Professor> listaProfessores = new ArrayList<>();

}
