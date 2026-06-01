import java.util.Scanner;

import control.Secretaria;
import model.Aluno;
import model.Disciplina;
import model.Nota;
import model.Professor;
import model.Turma;

public class Main {
    public static void main(String[] args) {
        Secretaria secretaria = new Secretaria();
        Scanner scanner = new Scanner(System.in);

        boolean executando = true;

        while (executando) {
            System.out.println("\n-------------------------------------");
            System.out.println("----------- MENU PRINCIPAL ----------");
            System.out.println("-------------------------------------");
            System.out.println(" 1 - Cadastrar Aluno");
            System.out.println(" 2 - Cadastrar Professor");
            System.out.println(" 3 - Cadastrar Disciplina");
            System.out.println(" 4 - Cadastrar Turma");
            System.out.println(" 5 - Inserir Nota");
            System.out.println(" 6 - Relatorio Alunos");
            System.out.println(" 7 - Relatorio Professores");
            System.out.println(" 8 - Relatorio Disciplinas");
            System.out.println(" 9 - Relatorio Turmas");
            System.out.println(" 10 - Relatorio Aluno X Nota X Media");
            System.out.println(" 11 - Quantidade de alunos cadastrados");
            System.out.println(" 12 - Nome de aluno que possui maior nota");
            System.out.println(" 13 - Alunos de uma determinada disciplina");
            System.out.println(" 14 - Turmas associadas por professor");
            System.out.println(" 15 - Sair");
            System.out.println("-------------------------------------\n");


            int opcao = scanner.nextInt();
            scanner.nextLine();

            switch(opcao) {
                case 1:
                    System.out.print("Digite o nome do aluno: ");
                    String nomeAluno = scanner.nextLine();
                    System.out.println("Digite a matricula do aluno: ");
                    int matriculaAluno = scanner.nextInt();
                    scanner.nextLine();
                    System.out.println("Data de nascimento: ");
                    String dataNascimento = scanner.nextLine();

                    Aluno novoAluno = new Aluno(
                            nomeAluno,
                            null,
                            null,
                            dataNascimento,
                            matriculaAluno,
                            0
                    );

                    System.out.print("Codigo da turma do aluno (0 se não souber): ");
                    int codTurmaAluno = scanner.nextInt();
                    scanner.nextLine();
                    if (codTurmaAluno != 0) secretaria.vincularAlunoTurma(novoAluno, codTurmaAluno);
                    System.out.println("Cadastro feito");
                    break;

                case 2:
                    System.out.println("Digite o nome do professor");
                    String nomeProfessor = scanner.nextLine();
                    System.out.println("Data de nascimento: ");
                    String dataProfessor = scanner.nextLine();
                    System.out.println("Especialidade: ");
                    String especialidadeProfessor = scanner.nextLine();

                    Professor novoProfessor = new Professor(
                            nomeProfessor, null, null,
                            dataProfessor, null,
                            especialidadeProfessor, 0, null
                    );

                    novoProfessor.setNome(nomeProfessor);
                    novoProfessor.setDataDeNascimento(dataProfessor);

                    boolean adicionandoDisciplinas = true;
                    while (adicionandoDisciplinas) {
                        System.out.println("Digite o nome da disciplina (ou 0 para sair): ");
                        String nomeDirecBusca = scanner.nextLine();
                        if (nomeDirecBusca.equals("0")) {
                            adicionandoDisciplinas = false;
                        } else {
                            Disciplina d = secretaria.buscarDisciplinaPorNome(nomeDirecBusca);
                            if (d != null) {
                                novoProfessor.getListaDisciplinas().add(d);
                                System.out.println("Disciplina: " + d.getNomeDisciplina() + " vinculada ao professor");
                            } else {
                                System.out.println("Disciplina nao encontrada, cadastre primeiro");
                            }
                        }
                    }
                    secretaria.cadastrarProfessor(novoProfessor);
                    break;

                case 3:
                    System.out.println("Cadastrar Disciplina");
                    System.out.println("Nome da disciplina: ");
                    String nomeDisciplina = scanner.nextLine();
                    System.out.println("Codigo da disciplina: ");
                    int codDisciplina = scanner.nextInt();
                    System.out.println("Carga horaria: ");
                    int cargaHoraria = scanner.nextInt();
                    scanner.nextLine();

                    Disciplina novaDisciplina = new Disciplina();
                    novaDisciplina.setNomeDisciplina(nomeDisciplina);
                    novaDisciplina.setCodigo(codDisciplina);
                    novaDisciplina.setCargaHoraria(cargaHoraria);
                    secretaria.cadastrarDisciplina(novaDisciplina);
                    System.out.println("Disciplina cadastrada");
                    break;

                case 4:
                    System.out.println("--- Cadastrar Turma ---");
                    System.out.print("Digite o código da turma: ");
                    int codTurma = scanner.nextInt();
                    scanner.nextLine();
                    System.out.print("Nome da disciplina da turma: ");
                    String nomeDiscTurma = scanner.nextLine();
                    System.out.print("Nome do Professor: ");
                    String nomeProfessorTurma = scanner.nextLine();

                    Disciplina discTurma = secretaria.buscarDisciplinaPorNome(nomeDiscTurma);
                    Professor profTurma = secretaria.buscarProfessorPorNome(nomeProfessorTurma);

                    if (discTurma == null || profTurma == null) {
                        System.out.println("Professor ou disciplina não encontrados.");
                        break;
                    }

                    Turma novaTurma = new Turma();
                    novaTurma.setCodigoTurma(codTurma);
                    novaTurma.setDisciplina(discTurma);
                    novaTurma.setProfessor(profTurma);
                    secretaria.cadastrarTurma(novaTurma);
                    System.out.println("Turma cadastrada.");
                    break;

                case 5:
                    System.out.println("--- INSERIR NOTA ---");
                    System.out.print("Matricula do Aluno: ");
                    int matNota = scanner.nextInt();
                    scanner.nextLine();
                    System.out.print("Nome da Disciplina: ");
                    String nomeDiscNota = scanner.nextLine();
                    System.out.print("Valor da Nota: ");
                    double valorNota = scanner.nextDouble();
                    scanner.nextLine();
                    Aluno alunoNota = secretaria.buscarAlunoPorMatricula(matNota);
                    Disciplina discNota = secretaria.buscarDisciplinaPorNome(nomeDiscNota);

                    if (alunoNota != null && discNota != null) {
                        Nota novaNota = new Nota();
                        novaNota.setValor(valorNota);
                        novaNota.setDisciplina(discNota);
                        secretaria.adicionarNota(alunoNota, discNota, novaNota);
                    } else {
                        System.out.println("Aluno ou disciplina nao encontrados no sistema");
                    }
                    break;

                case 6:
                    secretaria.listarAlunos();
                    break;

                case 7:
                    secretaria.listarProfessores();
                    break;

                case 8:
                    secretaria.listarDisciplinas();
                    break;

                case 9:
                    secretaria.listarTurmas();
                    break;

                case 10:
                    System.out.print("Digite a matricula do aluno pro relatorio: ");
                    int matRelatorio = scanner.nextInt();
                    scanner.nextLine();
                    secretaria.gerarRelatorioNotasXMedias(matRelatorio);
                    break;

                case 11:
                    secretaria.apresentarQuantidadeDeAlunos();
                    break;

                case 12:
                    secretaria.apresentarAlunoComMaiorNota();
                    break;

                case 13:
                    System.out.print("Digite o nome da disciplina: ");
                    String nomeDiscBusca = scanner.nextLine();
                    secretaria.listarAlunosPorDeterminadaDisciplina(nomeDiscBusca);
                    break;

                case 14:
                    System.out.print("Digite o nome do professor: ");
                    String nomeProfBusca = scanner.nextLine();
                    secretaria.listarTurmasPorProfessor(nomeProfBusca);
                    break;

                case 15:
                    System.out.println("Saindo");
                    executando = false;
                    break;

                default:
                    System.out.println("Opção invalida");
                    break;

            }
        }
        scanner.close();

    }

}
