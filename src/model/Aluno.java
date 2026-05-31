package model;

public class Aluno extends Pessoa {
    private int matricula;
    private int anoIngressao;

    public Aluno(String nome, String telefone, Endereco endereco,  String dataDeNascimento, int matricula, int anoIngressao){
        super(nome, telefone, endereco, dataDeNascimento);
        this.matricula = matricula;
        this.anoIngressao = anoIngressao;
    }

    public int getMatricula() {
        return matricula;
    }

    public void setMatricula(int matricula) {
        this.matricula = matricula;
    }

    public int getAnoIngressao() {
        return anoIngressao;
    }

    public void setAnoIngressao(int anoIngressao) {
        this.anoIngressao = anoIngressao;
    }
}
