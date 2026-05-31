package interfaces;

import model.Disciplina;
import model.Professor;

import java.util.ArrayList;

public interface GerenciadorCadastroProfessor {

    void cadastrarProfessor(Professor professor);
    ArrayList<Professor> listaProfessores();
    Professor buscarPorfessorPorEmail(String email);

    void cadastrarDisciplina(Disciplina disciplina);
}
