package com.example.eduardo.tcc.Entidades;

/**
 * Created by Eduardo on 05/10/2015.
 */
public class Doenca {
    public Doenca() {
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Integer getQtdOcorrencias() {
        return qtdOcorrencias;
    }

    public void setQtdOcorrencias(Integer qtdOcorrencias) {
        this.qtdOcorrencias = qtdOcorrencias;
    }

    public String getIdDoenca() {
        return idDoenca;
    }

    public void setIdDoenca(String idDoenca) {
        this.idDoenca = idDoenca;
    }

    public String nome;
    public Integer qtdOcorrencias = 0;
    public String idDoenca;
}
