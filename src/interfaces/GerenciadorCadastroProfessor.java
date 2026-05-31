package interfaces;

import model.Professor;

import java.util.ArrayList;

public interface GerenciadorCadastroProfessor {
    void cadastrarProfessor(Professor professor);
    ArrayList<Professor> listarProfessores();
    Professor buscarPorfessorPorEmail(String email);
}
