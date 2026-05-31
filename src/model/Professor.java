package model;
import java.util.ArrayList;

public class Professor extends Pessoa
{
    private String anoDeFormacao;
    private int anoDeAdmissao;
    private String email;
    private ArrayList<Disciplina> listaDisciplinas = new ArrayList<Disciplina>();


    public Professor(String nome, String telefone, Endereco endereco, String dataDeNascimento, String anoDeFormacao, int anoDeAdmissao, String email) {
        super(nome, telefone, endereco, dataDeNascimento);
        this.anoDeFormacao = anoDeFormacao;
        this.anoDeAdmissao = anoDeAdmissao;
        this.email = email;
    }

    public String getAnoDeFormacao()
    {
        return anoDeFormacao;
    }

    public void setAnoDeFormacao(String anoDeFormacao)
    {
        this.anoDeFormacao = anoDeFormacao;
    }

    public String getEmail()
    {
        return email;
    }

    public void setEmail(String email)
    {
        this.email = email;
    }

    public int getAnoDeAdmissao()
    {
        return anoDeAdmissao;
    }

    public void setAnoDeAdmissao(int anoDeAdmissao)
    {
        this.anoDeAdmissao = anoDeAdmissao;
    }

    public ArrayList<Disciplina> getListaDisciplinas()
    {
        return listaDisciplinas;
    }

    public void adicionarDisciplina(Disciplina disciplina)
    {
        this.listaDisciplinas.add(disciplina);
    }

}