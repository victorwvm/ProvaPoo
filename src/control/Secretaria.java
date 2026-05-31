package control;

import interfaces.GerenciadorCadastroAluno;
import interfaces.GerenciadorCadastroProfessor;
import model.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;


public class Secretaria implements GerenciadorCadastroAluno, GerenciadorCadastroProfessor {

    private ArrayList<Aluno> listaAlunos = new ArrayList<>();
    private ArrayList<Professor> listaProfessores = new ArrayList<>();
    private ArrayList<Disciplina> listaDisciplinas = new ArrayList<>();
    private ArrayList<Turma> listaTurmas = new ArrayList<>();

    private static final Path PATH_ALUNOS = Paths.get("alunos.txt");
    private static final Path PATH_PROFESSORES = Paths.get("professores.txt");
    private static final Path PATH_DISCIPLINAS = Paths.get("disciplinas.txt");
    private static final Path PATH_TURMAS = Paths.get("turmas.txt");

    public Secretaria() {
        try {
            if (Files.notExists(PATH_ALUNOS)) Files.createFile(PATH_ALUNOS);
            if (Files.notExists(PATH_PROFESSORES)) Files.createFile(PATH_PROFESSORES);
            if (Files.notExists(PATH_DISCIPLINAS)) Files.createFile(PATH_DISCIPLINAS);
            if (Files.notExists(PATH_TURMAS)) Files.createFile(PATH_TURMAS);
        } catch (IOException e) {
            System.err.println("Error" + e.getMessage());
        }
    }

    // CADASTRAR ALUNO
    @Override
    public void cadastrarAluno(Aluno aluno) {
        if (aluno == null) return;
        listaAlunos.add(aluno);
        try {
            String linha = aluno.getMatricula() + " | " + aluno.getNome() +  " | " + aluno.getDataDeNascimento() + "\n";
            Files.writeString(PATH_ALUNOS, linha, StandardOpenOption.APPEND);
        } catch (IOException err) {
            System.err.println("Error " + err.getMessage());
        }
    }

    // CADASTRAR PROFESSOR
    @Override
    public void cadastrarProfessor(Professor professor) {
        if (professor == null) return;
        listaProfessores.add(professor);
        try {
            String linha = professor.getNome() + " | "
                    + professor.getDataDeNascimento() + " | "
                    + professor.getEspecialidade() +  " | "
                    + professor.getEmail()  + " | "
                    + professor.getListaDisciplinas()
                    +  "\n";
            Files.writeString(PATH_PROFESSORES, linha, StandardOpenOption.APPEND);
        } catch (IOException err) {
            System.err.println("Error " + err.getMessage());
        }

    }

    // CADASTRAR DISCIPLINA
    public void cadastrarDisciplina(Disciplina disciplina) {
        if (disciplina == null) return;
        listaDisciplinas.add(disciplina);
        try {
            String linha = disciplina.getNomeDisciplina() + " | "
                    + disciplina.getCargaHoraria() + " | "
                    +  "\n";
            Files.writeString(PATH_DISCIPLINAS, linha, StandardOpenOption.APPEND);
        } catch (IOException err) {
            System.err.println("Error " + err.getMessage());
        }
    }

    // CADASTRAR TURMA
    public void cadastrarTurma(Turma turma) {
        if (turma == null) return;


        Disciplina disciplinaCadastrada = buscarDisciplinaPorCodigo(turma.getDisciplina().getCodigo());
        Professor professorCadastrado = buscarProfessorPorNome(turma.getProfessor().getNome());

        if(disciplinaCadastrada == null || professorCadastrado == null) {
            System.out.println("Não foi possivel cadastrar a turma.");
            System.out.println("Professor ou disciplina não encontrada no sistema");
            return;
        }

        listaTurmas.add(turma);
        try {
            String linha = professorCadastrado.getNome() + " | "
                    + disciplinaCadastrada.getNomeDisciplina() + " | " + "\n";
            Files.writeString(PATH_TURMAS, linha, StandardOpenOption.APPEND);
        } catch (IOException err) {
            System.err.println("Error " + err.getMessage());
        }
    }

    // ADICIONAR NOTA
    public void adicionarNota(Aluno aluno, Disciplina disciplina, Nota nota) {
        if (aluno == null || disciplina == null || nota == null) {
            System.out.println("Não foi possivel cadastrar a nota");
            System.out.println("Não existe aluno ou nao existe disciplina");
            return;
        }
        aluno.adicionarNota(nota);
        System.out.println("Nota inserida.");
    }

    // LISTAR ALUNO
    public void listarAlunos() {
        if (listaAlunos.isEmpty()) {
            System.out.println("Não tem nenhum aluno.");
            return;
        }
        System.out.println("-----ALUNOS-----");
        for(Aluno a: listaAlunos) {
            System.out.println("Aluno: "+a.getNome());

        }
    }

    // LISTAR PROFESSORES
    public void listarProfessores() {
        if (listaAlunos.isEmpty()) {
            System.out.println("Não tem nenhum professor.");
            return;
        }
        System.out.println("-----PROFESSORES-----");
        for(Professor p: listaProfessores) {
            System.out.println("Professor: " +p.getNome());

        }
    }

    // LISTAR DISCIPLINAS
    public void listarDisciplinas() {
        if (listaDisciplinas.isEmpty()) {
            System.out.println("Não tem nenhuma disciplina .");
            return;
        }
        System.out.println("-----DISCIPLINAS-----");
        for(Disciplina d: listaDisciplinas) {
            System.out.println("Disciplina: " +d.getNomeDisciplina());

        }
    }

    // LISTAR TURMAS
    public void listarTurmas() {
        if (listaTurmas.isEmpty()) {
            System.out.println("Não tem nenhuma turma .");
            return;
        }
        System.out.println("-----TURMAS-----");
        for(Turma t: listaTurmas) {
            System.out.println("Turma: " +t.getCodigoTurma());
        }
    }

    // GERAR RELATORIO DE ALUNO X NOTAS X MEDIAS
    public void gerarRelatorioNotasXMedias(int matricula) {
        Aluno aluno = buscarAlunoPorMatricula(matricula);

        if(aluno == null) {
            System.out.println("Aluno com matricula " +matricula+ " não foi encontrado.");
            return;
        }
        if (aluno.getListaNotas() == null  || aluno.getListaNotas().isEmpty()){
            System.out.println("O aluno " +aluno.getNome()+ " da matricula " +matricula+ " não encontrado");
        }

        System.out.println("\n----------------------------------------------------");
        System.out.println("--------- RELÁTORIO DE DESEMEPENHO ESCOLAR -----------");
        System.out.println("------------------------------------------------------");
        System.out.println("Aluno: " +aluno.getNome());
        System.out.println("Matricula: " +aluno.getMatricula());
        System.out.println("------------------------------------------------------");
        System.out.println("-------------- DISCIPLINA | NOTA ---------------------");
        for (Nota nota: aluno.getListaNotas()) {
            System.out.println(nota.getDisciplina().getNomeDisciplina() +" - "+ nota.getValor());
        }
        System.out.println("------------------------------------------------------");

        double media = aluno.getListaNotas().stream()
                .mapToDouble(Nota::getValor)
                .average()
                .orElse(0.0);
        System.out.printf("MÉDIA GERAL DO ALUNO: %.2f\n", media);
        System.out.println("----------------------------------------------------\n");
    }

    // QUANTIDADE DE ALUNOS CADASTRADOS
    public void apresentarQuantidadeDeAlunos(){
        int totalAlunos = listaAlunos.size();
        System.out.println("\n----------------------------------------------------");
        System.out.println("------- QUANTIDADE DE ALUNOS CADASTRADOS ------------- ");
        System.out.println("------------------------------------------------------");
        System.out.println("Total de alunos: " +totalAlunos);
        System.out.println("------------------------------------------------------\n");
    }

    public void apresentarAlunoComMaiorNota(){
        if (listaAlunos.isEmpty()) {
            System.out.println("Nenhum aluno cadastrado no sistema");
            return;
        }
        Aluno alunoMaiorNota = null;
        double maiorNotaEncontrada = -1.0;

        for(Aluno aluno: listaAlunos){
            for (Nota nota: aluno.getListaNotas()){
                if (nota.getValor() > maiorNotaEncontrada) {
                    maiorNotaEncontrada = nota.getValor();
                    alunoMaiorNota = aluno;
                }
            }
        }

        System.out.println("\n----------------------------------------------------");
        System.out.println("--------------- ALUNO COM MAIOR NOTA ----------------- ");
        System.out.println("------------------------------------------------------");
        if (alunoMaiorNota != null) {
            System.out.println(alunoMaiorNota.getNome());
            System.out.println(alunoMaiorNota.getMatricula());
            System.out.println(maiorNotaEncontrada);
        } else {
            System.out.println("Nenhum aluno possui notas lançadas ainda");
        }
        System.out.println("------------------------------------------------------\n");
    };

    public void listarAlunosPorDeterminadaDisciplina(String nomeDisciplina) {
        if (nomeDisciplina == null || nomeDisciplina.trim().isEmpty()) {
            System.out.println("Disciplina nao pode ser vazia.");
            return;
        }
        java.util.Set<Aluno>  alunosEncontrados = new java.util.Set<>();
        boolean disciplinaExiste = false;

        for(Turma turma: listaTurmas()) {
            if (turma.getDisciplina().getNomeDisciplina().equalsIgnoreCase(nomeDisciplina.trim()))  {
                disciplinaExiste = true;

                if (turma.getListaDeAlunos() != null) {
                    alunosEncontrados.addAll(turma.getListaDeAlunos());
                }
            }
        }
    }
}
