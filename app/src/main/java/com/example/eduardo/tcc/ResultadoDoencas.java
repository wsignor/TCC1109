package com.example.eduardo.tcc;

import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

/**
 * Created by Wagner on 20/09/2015.
 */
public class ResultadoDoencas {

    boolean
            historicoFamiliar,
            peso,
            sexo,
            idade,
            diabetes,
            hipertensao,
            tabagismo,
            sedentarismo,
            raca,
            altoConsumoAlcool,
            altoConsumoSodio,
            altoConsumoGorduraAcucares,
            colesterolAlto,
            mulherMenopausa,
            mulherAnticoncepcionais,
            estresse,
            mulherDiabeteGestacional,
            usoCortisonaDiureticosBetabloq,
            mulherComFilhos,
            homemMoraComCompanheira,
            mulherOvarioPolicistico,
            dislipidemia,
            microalbuminuria,
            intoleranciaGlicoseInsulina,
            hiperuricemia,
            estadoProTromboticoProInflamatorio,
            cardiovascularFamiliar,
            diabetesFamiliar,
            hipertensaoFamiliar,
            obesidadeFamiliar,
            sindromeFamiliar;


    public String carregarDados(){

        // pegar informacoes imutaveis
        ParseQuery innerQuery = new ParseQuery("_User");
        innerQuery.whereEqualTo("username", ParseUser.getCurrentUser().getUsername());
        ParseQuery<ParseObject> query = ParseQuery.getQuery("InformacoesImutaveis");
        query.whereMatchesQuery("idUsuario", innerQuery);

        try {
            diabetes = (boolean) query.getFirst().get("diabetico");
            hipertensao = (boolean) query.getFirst().get("hipertenso");
            cardiovascularFamiliar = (boolean) query.getFirst().get("cardiovascularFamiliar");
            diabetesFamiliar = (boolean) query.getFirst().get("diabetesFamiliar");
            hipertensaoFamiliar = (boolean) query.getFirst().get("hipertensaoFamiliar");
            obesidadeFamiliar = (boolean) query.getFirst().get("obesidadeFamiliar");
            sindromeFamiliar = (boolean) query.getFirst().get("sindromeFamiliar");

        } catch (ParseException e) {
            e.printStackTrace();
        }


        // pegar informacoes mutaveis
        ParseQuery query1 = new ParseQuery("_User");
        query1.whereEqualTo("username", ParseUser.getCurrentUser().getUsername());

        ParseQuery query2 = new ParseQuery("UsuarioInformacao");
        query2.whereEqualTo("idUsuario", query1);

        ParseQuery query3 = ParseQuery.getQuery("InformacoesMutaveis");
        query3.whereMatchesQuery("idInformacao", query2);


        return null;

    }

    public boolean isSexo() {
        return sexo;
    }

    public void setSexo(boolean sexo) {
        this.sexo = sexo;
    }

    public boolean isHistoricoFamiliar() {
        return historicoFamiliar;
    }

    public void setHistoricoFamiliar(boolean historicoFamiliar) {
        this.historicoFamiliar = historicoFamiliar;
    }

    public boolean isPeso() {
        return peso;
    }

    public void setPeso(boolean peso) {
        this.peso = peso;
    }

    public boolean isIdade() {
        return idade;
    }

    public void setIdade(boolean idade) {
        this.idade = idade;
    }

    public boolean isDiabetes() {
        return diabetes;
    }

    public void setDiabetes(boolean diabetes) {
        this.diabetes = diabetes;
    }

    public boolean isHipertensao() {
        return hipertensao;
    }

    public void setHipertensao(boolean hipertensao) {
        this.hipertensao = hipertensao;
    }

    public boolean isTabagismo() {
        return tabagismo;
    }

    public void setTabagismo(boolean tabagismo) {
        this.tabagismo = tabagismo;
    }

    public boolean isSedentarismo() {
        return sedentarismo;
    }

    public void setSedentarismo(boolean sedentarismo) {
        this.sedentarismo = sedentarismo;
    }

    public boolean isRaca() {
        return raca;
    }

    public void setRaca(boolean raca) {
        this.raca = raca;
    }

    public boolean isAltoConsumoAlcool() {
        return altoConsumoAlcool;
    }

    public void setAltoConsumoAlcool(boolean altoConsumoAlcool) {
        this.altoConsumoAlcool = altoConsumoAlcool;
    }

    public boolean isAltoConsumoSodio() {
        return altoConsumoSodio;
    }

    public void setAltoConsumoSodio(boolean altoConsumoSodio) {
        this.altoConsumoSodio = altoConsumoSodio;
    }

    public boolean isAltoConsumoGorduraAcucares() {
        return altoConsumoGorduraAcucares;
    }

    public void setAltoConsumoGorduraAcucares(boolean altoConsumoGorduraAcucares) {
        this.altoConsumoGorduraAcucares = altoConsumoGorduraAcucares;
    }

    public boolean isColesterolAlto() {
        return colesterolAlto;
    }

    public void setColesterolAlto(boolean colesterolAlto) {
        this.colesterolAlto = colesterolAlto;
    }

    public boolean isMulherMenopausa() {
        return mulherMenopausa;
    }

    public void setMulherMenopausa(boolean mulherMenopausa) {
        this.mulherMenopausa = mulherMenopausa;
    }

    public boolean isMulherAnticoncepcionais() {
        return mulherAnticoncepcionais;
    }

    public void setMulherAnticoncepcionais(boolean mulherAnticoncepcionais) {
        this.mulherAnticoncepcionais = mulherAnticoncepcionais;
    }

    public boolean isEstresse() {
        return estresse;
    }

    public void setEstresse(boolean estresse) {
        this.estresse = estresse;
    }

    public boolean isMulherDiabeteGestacional() {
        return mulherDiabeteGestacional;
    }

    public void setMulherDiabeteGestacional(boolean mulherDiabeteGestacional) {
        this.mulherDiabeteGestacional = mulherDiabeteGestacional;
    }

    public boolean isUsoCortisonaDiureticosBetabloq() {
        return usoCortisonaDiureticosBetabloq;
    }

    public void setUsoCortisonaDiureticosBetabloq(boolean usoCortisonaDiureticosBetabloq) {
        this.usoCortisonaDiureticosBetabloq = usoCortisonaDiureticosBetabloq;
    }

    public boolean isMulherComFilhos() {
        return mulherComFilhos;
    }

    public void setMulherComFilhos(boolean mulherComFilhos) {
        this.mulherComFilhos = mulherComFilhos;
    }

    public boolean isHomemMoraComCompanheira() {
        return homemMoraComCompanheira;
    }

    public void setHomemMoraComCompanheira(boolean homemMoraComCompanheira) {
        this.homemMoraComCompanheira = homemMoraComCompanheira;
    }

    public boolean isMulherOvarioPolicistico() {
        return mulherOvarioPolicistico;
    }

    public void setMulherOvarioPolicistico(boolean mulherOvarioPolicistico) {
        this.mulherOvarioPolicistico = mulherOvarioPolicistico;
    }

    public boolean isDislipidemia() {
        return dislipidemia;
    }

    public void setDislipidemia(boolean dislipidemia) {
        this.dislipidemia = dislipidemia;
    }

    public boolean isMicroalbuminuria() {
        return microalbuminuria;
    }

    public void setMicroalbuminuria(boolean microalbuminuria) {
        this.microalbuminuria = microalbuminuria;
    }

    public boolean isIntoleranciaGlicoseInsulina() {
        return intoleranciaGlicoseInsulina;
    }

    public void setIntoleranciaGlicoseInsulina(boolean intoleranciaGlicoseInsulina) {
        this.intoleranciaGlicoseInsulina = intoleranciaGlicoseInsulina;
    }

    public boolean isHiperuricemia() {
        return hiperuricemia;
    }

    public void setHiperuricemia(boolean hiperuricemia) {
        this.hiperuricemia = hiperuricemia;
    }

    public boolean isEstadoProTromboticoProInflamatorio() {
        return estadoProTromboticoProInflamatorio;
    }

    public void setEstadoProTromboticoProInflamatorio(boolean estadoProTromboticoProInflamatorio) {
        this.estadoProTromboticoProInflamatorio = estadoProTromboticoProInflamatorio;
    }

    public boolean isCardiovascularFamiliar() {
        return cardiovascularFamiliar;
    }

    public void setCardiovascularFamiliar(boolean cardiovascularFamiliar) {
        this.cardiovascularFamiliar = cardiovascularFamiliar;
    }

    public boolean isDiabetesFamiliar() {
        return diabetesFamiliar;
    }

    public void setDiabetesFamiliar(boolean diabetesFamiliar) {
        this.diabetesFamiliar = diabetesFamiliar;
    }

    public boolean isHipertensaoFamiliar() {
        return hipertensaoFamiliar;
    }

    public void setHipertensaoFamiliar(boolean hipertensaoFamiliar) {
        this.hipertensaoFamiliar = hipertensaoFamiliar;
    }

    public boolean isObesidadeFamiliar() {
        return obesidadeFamiliar;
    }

    public void setObesidadeFamiliar(boolean obesidadeFamiliar) {
        this.obesidadeFamiliar = obesidadeFamiliar;
    }

    public boolean isSindromeFamiliar() {
        return sindromeFamiliar;
    }

    public void setSindromeFamiliar(boolean sindromeFamiliar) {
        this.sindromeFamiliar = sindromeFamiliar;
    }
}
