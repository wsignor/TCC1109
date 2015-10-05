package com.example.eduardo.tcc;

import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

/**
 * Created by Wagner on 20/09/2015.
 */
public class ResultadoDoencas {

    boolean fumante;
    boolean sedentarismo;
    boolean diabetes;
    boolean hipertensao;
    boolean altoConsumoAlcool;
    boolean altoConsumoSodio;
    boolean altoConsumoGorduraAcucares;
    boolean mulherMenopausa;
    boolean mulherAnticoncepcionais;
    boolean estresse;
    boolean mulherDiabeteGestacional;
    boolean usoCortisona;
    boolean usoDiureticos;
    boolean usoBetabloqueadores;
    boolean mulherComFilhos;
    boolean homemMoraComCompanheira;
    boolean mulherOvarioPolicistico;
    boolean dislipidemia;
    boolean microalbuminuria;
    boolean intoleranciaGlicose;
    boolean intoleranciaInsulina;
    boolean hiperuricemia;
    boolean estadoProTromboticoProInflamatorio;
    boolean cardiovascularFamiliar;
    boolean diabetesFamiliar;
    boolean hipertensaoFamiliar;
    boolean obesidadeFamiliar;
    boolean sindromeFamiliar;

    int peso, idade, qtdDiabetes, qtdHipertensao, qtdObesidade, qtdDoencasCardiovasculares, qtdSindromeMetabolica;

    double altura, imc;

    String sexo, raca, nivelColesterol;;


    public void setIMC(double altura, int peso){
        this.altura = altura;
        this.peso = peso;
        this.imc =  peso / (altura * altura);

        if(this.imc >= 30){
            qtdDiabetes += 1;
            qtdHipertensao += 1;
            qtdDoencasCardiovasculares += 1;
            qtdSindromeMetabolica += 1;
        }
    }

    public double getAltura() {
        return altura;
    }

    public void setAltura(double altura) {
        this.altura = altura;
    }

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;

        if(sexo.equalsIgnoreCase("Masculino")){
            qtdDoencasCardiovasculares += 1;
            qtdHipertensao += 1;
        } else {
            qtdDiabetes += 1;
            qtdObesidade += 1;
            qtdSindromeMetabolica += 1;
        }

    }

    public int getPeso() {
        return peso;
    }

    public void setPeso(int peso) {
        this.peso = peso;
    }

    public int getIdade() {
        return idade;
    }

    public void setIdade(int idade) {
        this.idade = idade;

        if(idade >= 50){
            qtdHipertensao += 1;
            qtdSindromeMetabolica += 1;
            qtdObesidade += 1;
            qtdDiabetes += 1;
            qtdDoencasCardiovasculares += 1;
        } else if (idade >= 45){
            qtdObesidade += 1;
            qtdDiabetes += 1;
            qtdDoencasCardiovasculares += 1;
        } else if (idade >= 40){
            qtdObesidade += 1;
        }

    }

    public boolean isDiabetes() {
        return diabetes;
    }

    public void setDiabetes(boolean diabetes) {
        this.diabetes = diabetes;

        if(diabetes){
            qtdHipertensao += 1;
            qtdObesidade += 1;
            qtdDoencasCardiovasculares += 1;
            qtdSindromeMetabolica += 1;
        }

    }

    public boolean isHipertensao() {
        return hipertensao;
    }

    public void setHipertensao(boolean hipertensao) {
        this.hipertensao = hipertensao;

        if(hipertensao){
            qtdDiabetes += 1;
            qtdObesidade += 1;
            qtdDoencasCardiovasculares += 1;
            qtdSindromeMetabolica += 1;
        }

    }

    public boolean isFumante() {
        return fumante;
    }

    public void setFumante(boolean fumante) {
        this.fumante = fumante;

        if(fumante){
            qtdDiabetes += 1;
            qtdHipertensao += 1;
            qtdDoencasCardiovasculares += 1;
            qtdSindromeMetabolica += 1;
        }

    }

    public boolean isSedentarismo() {
        return sedentarismo;
    }

    public void setSedentarismo(boolean sedentarismo) {
        this.sedentarismo = sedentarismo;

        if(sedentarismo){
            qtdDiabetes += 1;
            qtdHipertensao += 1;
            qtdObesidade += 1;
            qtdDoencasCardiovasculares += 1;
            qtdSindromeMetabolica += 1;
        }

    }

    public String isRaca() {
        return raca;
    }

    public void setRaca(String raca) {
        this.raca = raca;

        if(raca == "Negra"){
            qtdHipertensao += 1;
        }

    }

    public boolean isAltoConsumoAlcool() {
        return altoConsumoAlcool;
    }

    public void setAltoConsumoAlcool(boolean altoConsumoAlcool) {
        this.altoConsumoAlcool = altoConsumoAlcool;

        if(altoConsumoAlcool){
            qtdHipertensao += 1;
        }

    }

    public boolean isAltoConsumoSodio() {
        return altoConsumoSodio;
    }

    public void setAltoConsumoSodio(boolean altoConsumoSodio) {
        this.altoConsumoSodio = altoConsumoSodio;

        if(altoConsumoSodio) {
            qtdHipertensao += 1;
        }

    }

    public boolean isAltoConsumoGorduraAcucares() {
        return altoConsumoGorduraAcucares;
    }

    public void setAltoConsumoGorduraAcucares(boolean altoConsumoGorduraAcucares) {
        this.altoConsumoGorduraAcucares = altoConsumoGorduraAcucares;

        if(altoConsumoGorduraAcucares){
            qtdDiabetes += 1;
            qtdObesidade += 1;
            qtdDoencasCardiovasculares += 1;
            qtdSindromeMetabolica += 1;
        }

    }

    public String isNivelColesterol() {
        return nivelColesterol;
    }

    public void setNivelColesterol(String colesterolAlto) {
        this.nivelColesterol = colesterolAlto;

        if(colesterolAlto == "Alto") {
            qtdDiabetes += 1;
            qtdObesidade += 1;
            qtdDoencasCardiovasculares += 1;
            qtdSindromeMetabolica += 1;
            qtdHipertensao += 1;
        }

    }

    public boolean isMulherMenopausa() {
        return mulherMenopausa;
    }

    public void setMulherMenopausa(boolean mulherMenopausa) {
        this.mulherMenopausa = mulherMenopausa;

        if(mulherMenopausa){
            qtdHipertensao += 1;
        }

    }

    public boolean isMulherAnticoncepcionais() {
        return mulherAnticoncepcionais;
    }

    public void setMulherAnticoncepcionais(boolean mulherAnticoncepcionais) {
        this.mulherAnticoncepcionais = mulherAnticoncepcionais;

        if(mulherAnticoncepcionais){
            qtdHipertensao += 1;
        }

    }

    public boolean isEstresse() {
        return estresse;
    }

    public void setEstresse(boolean estresse) {
        this.estresse = estresse;

        if(estresse) {
            qtdHipertensao += 1;
            qtdDiabetes += 1;
            qtdObesidade += 1;
            qtdDoencasCardiovasculares += 1;
        }

    }

    public boolean isMulherDiabeteGestacional() {
        return mulherDiabeteGestacional;
    }

    public void setMulherDiabeteGestacional(boolean mulherDiabeteGestacional) {
        this.mulherDiabeteGestacional = mulherDiabeteGestacional;

        if(mulherDiabeteGestacional){
            qtdDiabetes += 1;
        }

    }

    public boolean isUsoCortisona() {
        return usoCortisona;
    }

    public void setUsoCortisona(boolean usoCortisona) {
        this.usoCortisona = usoCortisona;

        if(usoCortisona){
            qtdDiabetes += 1;
        }

    }

    public boolean isUsoDiureticos() {
        return usoDiureticos;
    }

    public void setUsoDiureticos(boolean usoDiureticos) {
        this.usoDiureticos = usoDiureticos;

        if(usoDiureticos){
            qtdDiabetes += 1;
        }

    }

    public boolean isUsoBetabloqueadores() {
        return usoBetabloqueadores;
    }

    public void setUsoBetabloqueadores(boolean usoBetabloqueadores) {
        this.usoBetabloqueadores = usoBetabloqueadores;

        if(usoBetabloqueadores){
            qtdDiabetes += 1;
        }

    }

    public boolean isMulherComFilhos() {
        return mulherComFilhos;
    }

    public void setMulherComFilhos(boolean mulherComFilhos) {
        this.mulherComFilhos = mulherComFilhos;


        if(mulherComFilhos){
            qtdObesidade += 1;
        }

    }

    public boolean isHomemMoraComCompanheira() {
        return homemMoraComCompanheira;
    }

    public void setHomemMoraComCompanheira(boolean homemMoraComCompanheira) {
        this.homemMoraComCompanheira = homemMoraComCompanheira;

        if(homemMoraComCompanheira){
            qtdObesidade += 1;
        }

    }

    public boolean isMulherOvarioPolicistico() {
        return mulherOvarioPolicistico;
    }

    public void setMulherOvarioPolicistico(boolean mulherOvarioPolicistico) {
        this.mulherOvarioPolicistico = mulherOvarioPolicistico;

        if(mulherOvarioPolicistico){
            qtdSindromeMetabolica += 1;
        }

    }

    public boolean isDislipidemia() {
        return dislipidemia;
    }

    public void setDislipidemia(boolean dislipidemia) {
        this.dislipidemia = dislipidemia;

        if(dislipidemia){
            qtdSindromeMetabolica += 1;
        }

    }

    public boolean isMicroalbuminuria() {
        return microalbuminuria;
    }

    public void setMicroalbuminuria(boolean microalbuminuria) {
        this.microalbuminuria = microalbuminuria;

        if(microalbuminuria){
            qtdSindromeMetabolica += 1;
        }

    }

    public boolean isIntoleranciaGlicose() {
        return intoleranciaGlicose;
    }

    public void setIntoleranciaGlicose(boolean intoleranciaGlicose) {
        this.intoleranciaGlicose = intoleranciaGlicose;

        if(intoleranciaGlicose){
            qtdSindromeMetabolica += 1;
        }

    }

    public boolean isIntoleranciaInsulina() {
        return intoleranciaInsulina;
    }

    public void setIntoleranciaInsulina(boolean intoleranciaInsulina) {
        this.intoleranciaInsulina = intoleranciaInsulina;

        if(intoleranciaInsulina){
            qtdSindromeMetabolica += 1;
        }

    }

    public boolean isHiperuricemia() {
        return hiperuricemia;
    }

    public void setHiperuricemia(boolean hiperuricemia) {
        this.hiperuricemia = hiperuricemia;

        if(hiperuricemia){
            qtdSindromeMetabolica += 1;
        }

    }

    public boolean isEstadoProTromboticoProInflamatorio() {
        return estadoProTromboticoProInflamatorio;
    }

    public void setEstadoProTromboticoProInflamatorio(boolean estadoProTromboticoProInflamatorio) {
        this.estadoProTromboticoProInflamatorio = estadoProTromboticoProInflamatorio;

        if(estadoProTromboticoProInflamatorio){
            qtdSindromeMetabolica += 1;
        }

    }

    public boolean isCardiovascularFamiliar() {
        return cardiovascularFamiliar;
    }

    public void setCardiovascularFamiliar(boolean cardiovascularFamiliar) {
        this.cardiovascularFamiliar = cardiovascularFamiliar;

        if(cardiovascularFamiliar){
            qtdDoencasCardiovasculares += 1;
        }

    }

    public boolean isDiabetesFamiliar() {
        return diabetesFamiliar;
    }

    public void setDiabetesFamiliar(boolean diabetesFamiliar) {
        this.diabetesFamiliar = diabetesFamiliar;

        if(diabetesFamiliar){
            qtdDiabetes += 1;
        }

    }

    public boolean isHipertensaoFamiliar() {
        return hipertensaoFamiliar;
    }

    public void setHipertensaoFamiliar(boolean hipertensaoFamiliar) {
        this.hipertensaoFamiliar = hipertensaoFamiliar;

        if(hipertensaoFamiliar){
            qtdHipertensao += 1;
        }

    }

    public boolean isObesidadeFamiliar() {
        return obesidadeFamiliar;
    }

    public void setObesidadeFamiliar(boolean obesidadeFamiliar) {
        this.obesidadeFamiliar = obesidadeFamiliar;

        if(obesidadeFamiliar){
            qtdObesidade += 1;
        }

    }

    public boolean isSindromeFamiliar() {
        return sindromeFamiliar;
    }

    public void setSindromeFamiliar(boolean sindromeFamiliar) {
        this.sindromeFamiliar = sindromeFamiliar;

        if(sindromeFamiliar){
            qtdSindromeMetabolica += 1;
        }

    }
}
