package model;

import java.util.ArrayList;
import java.util.List;

public class Aluno extends Pessoa {
    private int matricula;
    private int anoIngressao;
    private List<Nota> listaNotas;


    public Aluno(String nome, String telefone, Endereco endereco,  String dataDeNascimento, int matricula, int anoIngressao){
        super(nome, telefone, endereco, dataDeNascimento);
        this.matricula = matricula;
        this.anoIngressao = anoIngressao;
    }

    public List<Nota> getListaNotas() {
        return listaNotas;
    }

    public void setListaNotas(List<Nota> listaNotas) {
        this.listaNotas = listaNotas;
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

    public void adicionarNota(Nota nota) {
        if (nota != null) {
            this.listaNotas.add(nota);
        }
    }
}
