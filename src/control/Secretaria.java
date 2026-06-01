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
    private static final Path PATH_NOTAS = Paths.get("notas.txt");

    public Secretaria() {
        try {
            if (Files.notExists(PATH_ALUNOS)) Files.createFile(PATH_ALUNOS);
            if (Files.notExists(PATH_PROFESSORES)) Files.createFile(PATH_PROFESSORES);
            if (Files.notExists(PATH_DISCIPLINAS)) Files.createFile(PATH_DISCIPLINAS);
            if (Files.notExists(PATH_TURMAS)) Files.createFile(PATH_TURMAS);
            if (Files.notExists(PATH_NOTAS)) Files.createFile(PATH_NOTAS);
            carregarDados();
        } catch (IOException e) {
            System.err.println("Error" + e.getMessage());
        }
    }

    private void carregarDados() {
        try {
            for (String linha : Files.readAllLines(PATH_ALUNOS)) {
                String[] p = linha.split(" \\| ");
                if (p.length >= 3)
                    listaAlunos.add(new Aluno(p[1].trim(), null, null, p[2].trim(), Integer.parseInt(p[0].trim()), 0));
            }
            for (String linha : Files.readAllLines(PATH_DISCIPLINAS)) {
                String[] p = linha.split(" \\| ");
                if (p.length >= 1) {
                    Disciplina d = new Disciplina();
                    d.setNomeDisciplina(p[0].trim());
                    listaDisciplinas.add(d);
                }
            }
            for (String linha : Files.readAllLines(PATH_PROFESSORES)) {
                String[] p = linha.split(" \\| ");
                if (p.length >= 3)
                    listaProfessores.add(new Professor(p[0].trim(), null, null, p[1].trim(), null, p[2].trim(), 0, null));
            }
            for (String linha : Files.readAllLines(PATH_NOTAS)) {
                String[] p = linha.split("\\|");
                if (p.length >= 3) {
                    Aluno a = buscarAlunoPorMatricula(Integer.parseInt(p[0].trim()));
                    Disciplina d = buscarDisciplinaPorNome(p[1].trim());
                    if (a != null && d != null) {
                        Nota nota = new Nota();
                        nota.setValor(Double.parseDouble(p[2].trim()));
                        nota.setDisciplina(d);
                        a.adicionarNota(nota);
                    }
                }
            }
            for (String linha : Files.readAllLines(PATH_TURMAS)) {
                String[] p = linha.split("\\|");
                if (p.length >= 3) {
                    Professor prof = buscarProfessorPorNome(p[1].trim());
                    Disciplina disc = buscarDisciplinaPorNome(p[2].trim());
                    if (prof != null && disc != null) {
                        Turma t = new Turma();
                        t.setCodigoTurma(Integer.parseInt(p[0].trim()));
                        t.setProfessor(prof);
                        t.setDisciplina(disc);
                        listaTurmas.add(t);
                    }
                }
            }
        } catch (Exception e) { System.err.println("Erro ao carregar: " + e.getMessage()); }
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
            String linha = turma.getCodigoTurma() + " | "
                    +professorCadastrado.getNome() + " | "
                    +disciplinaCadastrada.getNomeDisciplina()
                    + "\n";
            Files.writeString(PATH_TURMAS, linha, StandardOpenOption.APPEND);
        } catch (IOException err) {
            System.err.println("Error " + err.getMessage());
        }
    }

    public Disciplina buscarDisciplinaPorCodigo(int codigo) {
        for (Disciplina d : listaDisciplinas)
            if (d.getCodigo() == codigo) return d;
        return null;
    }

    public Disciplina buscarDisciplinaPorNome(String nome) {
        for (Disciplina d : listaDisciplinas)
            if (d.getNomeDisciplina().equalsIgnoreCase(nome)) return d;
        return null;
    }

    public Professor buscarProfessorPorNome(String nome) {
        for (Professor p : listaProfessores)
            if (p.getNome().equalsIgnoreCase(nome)) return p;
        return null;
    }

    // ADICIONAR NOTA
    public void adicionarNota(Aluno aluno, Disciplina disciplina, Nota nota) {
        if (aluno == null || disciplina == null || nota == null) {
            System.out.println("Não foi possivel cadastrar a nota");
            return;
        }
        aluno.adicionarNota(nota);
        try{
            String linha = aluno.getMatricula() + " | "
                    +disciplina.getNomeDisciplina() + " | "
                    +nota.getValor() + "\n";
            Files.writeString(PATH_NOTAS, linha, StandardOpenOption.APPEND);
        } catch (IOException err) {
            System.out.println("Error " +err.getMessage() );
        }
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
        if (listaProfessores.isEmpty()) {
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
        System.out.println("------------------------------------------------------");
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

        System.out.println("------------------------------------------------------");
    }

    // GERAR RELATORIO DE ALUNO X NOTAS X MEDIAS
    public void gerarRelatorioNotasXMedias(int matricula) {
        Aluno aluno = buscarAlunoPorMatricula(matricula);

        if(aluno == null) {
            System.out.println("Aluno com matricula " +matricula+ " não foi encontrado.");
            return;
        }
        if (aluno.getListaNotas() == null  || aluno.getListaNotas().isEmpty()){
            System.out.println("O aluno " +aluno.getNome()+ " da matricula " + matricula + " não encontrado");
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
            System.out.println("Nome: " + alunoMaiorNota.getNome());
            System.out.println("Matricula: " + alunoMaiorNota.getMatricula());
            System.out.println("Maior nota: " + maiorNotaEncontrada);
        } else {
            System.out.println("Nenhum aluno possui notas lançadas ainda");
        }
        System.out.println("------------------------------------------------------\n");
    };


    // Listar alunos disciplina
    public void listarAlunosPorDeterminadaDisciplina(String nomeDisciplina) {
        if (nomeDisciplina == null || nomeDisciplina.trim().isEmpty()) {
            System.out.println("Disciplina nao pode ser vazia.");
            return;
        }
        System.out.println("\n----------------------------------------------------");
        System.out.println("--------------- ALUNOS POR DISCIPLINA --------------- ");
        System.out.println("------------------------------------------------------");

        boolean achouAlgum = false;
        for (Turma turma : listaTurmas) {
            if (turma.getDisciplina().getNomeDisciplina().equalsIgnoreCase(nomeDisciplina.trim())) {
                for (Aluno a : turma.getListaDeAlunos()) {
                    System.out.println("Aluno: " + a.getNome());
                    achouAlgum = true;
                }
            }
        }

        if (!achouAlgum) System.out.println("Nenhum aluno encontrado para essa disciplina.");
        System.out.println("------------------------------------------------------\n");
    }

    public void vincularAlunoTurma(Aluno aluno, int codTurma) {
        for (Turma t : listaTurmas) {
            if (t.getCodigoTurma() == codTurma) {
                t.getListaDeAlunos().add(aluno);
                System.out.println("Aluno vinculado à turma " + codTurma);
                return;
            }
        }
        System.out.println("Turma não encontrada.");
    }

    public void listarTurmasPorProfessor(String nomeProfessor) {
        if (listaTurmas.isEmpty()) {
            System.out.println("Nenhuma turma cadastrada com este professor no sistema");
            return;
        }

        boolean encontrouAlguma = false;

        System.out.println("\n----------------------------------------");
        System.out.println("\n------TURMAS DE PROFESSOR PROCURADO-----");
        System.out.println("\n----------------------------------------");

        for (Turma t : listaTurmas) {
            if (t.getProfessor().getNome().equalsIgnoreCase(nomeProfessor.trim())) {
                System.out.println("Codigo da turma: " + t.getCodigoTurma() +
                        " | Disciplina:  " + t.getDisciplina().getNomeDisciplina());
                encontrouAlguma = true;
            }
        }

        if (!encontrouAlguma) {
            System.out.println("Nenhuma turma encontrada com o professor: " + nomeProfessor);

        }
        System.out.println("----------------------------------------\n");

    }

    @Override
    public ArrayList<Professor> listaProfessores(){
        return listaProfessores;
    }

    @Override
    public Professor buscarPorfessorPorEmail(String email){
        return null;
    }

    @Override
    public ArrayList<Aluno> listarAluno() {
        return listaAlunos;
    }

    @Override
    public Aluno buscarAlunoPorMatricula(int matricula) {
        for (Aluno a : listaAlunos) if (a.getMatricula() == matricula) return a;
        return null;
    }
}
