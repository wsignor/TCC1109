package com.example.eduardo.tcc;

import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

/**
 * Created by Wagner on 20/09/2015.
 */
public final class ResultadoDoencas {

    private static final ResultadoDoencas INSTANCE = new ResultadoDoencas();

    boolean fumante;
    boolean sedentarismo;
    boolean diabetico;
    boolean hipertenso;
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

    int peso, idade;

    double altura, imc;

    String sexo, raca, nivelColesterol;;

    public Doenca getDiabetes() {
        diabetes.setNome("Diabetes Mellitus");
        return diabetes;
    }

    public Doenca getHipertensao() {
        hipertensao.setNome("Hipertensão Arterial");
        return hipertensao;
    }

    public Doenca getDoencasCardiovasculares() {
        doencasCardiovasculares.setNome("Doenças Cardiovasculares");
        return doencasCardiovasculares;
    }

    public Doenca getObesidade() {
        obesidade.setNome("Obesidade");
        return obesidade;
    }

    public Doenca getSindromeMetabolica() {
        sindromeMetabolica.setNome("Sindrome Metabólica");
        return sindromeMetabolica;
    }

    Doenca diabetes = new Doenca();
    Doenca hipertensao = new Doenca();
    Doenca doencasCardiovasculares = new Doenca();
    Doenca obesidade = new Doenca();
    Doenca sindromeMetabolica = new Doenca();

    public int getQtdDiabetes() {
        return diabetes.getQtdOcorrencias();
    }
    public int getQtdHipertensao() {
        return hipertensao.getQtdOcorrencias();
    }
    public int getQtdObesidade() {
        return obesidade.getQtdOcorrencias();
    }
    public int getQtdDoencasCardiovasculares() {
        return doencasCardiovasculares.getQtdOcorrencias();
    }
    public int getQtdSindromeMetabolica() {
        return sindromeMetabolica.getQtdOcorrencias();
    }

    public void limpaResultado(){
        diabetes.setQtdOcorrencias(0);
        obesidade.setQtdOcorrencias(0);
        hipertensao.setQtdOcorrencias(0);
        doencasCardiovasculares.setQtdOcorrencias(0);
        sindromeMetabolica.setQtdOcorrencias(0);
    }

    public static ResultadoDoencas getInstance(){
        return INSTANCE;
    }

    public void setIMC(double altura, int peso){
        this.altura = altura;
        this.peso = peso;
        this.imc =  peso / (altura * altura);

        if(this.imc >= 30){
            incrementDiabetes();
            incrementHipertensao();
            incrementDoencasCardiovasculares();
            incrementSindromeMetabolica();
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
            incrementDoencasCardiovasculares();
            incrementHipertensao();
        } else {
            incrementDiabetes();
            incrementObesidade();
            incrementSindromeMetabolica();
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
            incrementHipertensao();
            incrementSindromeMetabolica();
            incrementObesidade();
            incrementDiabetes();
            incrementDoencasCardiovasculares();
        } else if (idade >= 45){
            incrementObesidade();
            incrementDiabetes();
            incrementDoencasCardiovasculares();
        } else if (idade >= 40){
            incrementObesidade();
        }

    }

    public boolean isDiabetes() {
        return diabetico;
    }

    public void setDiabetes(boolean diabetes) {
        this.diabetico = diabetes;

        if(diabetes){
            incrementHipertensao();
            incrementObesidade();
            incrementDoencasCardiovasculares();
            incrementSindromeMetabolica();
        }

    }

    public boolean isHipertensao() {
        return hipertenso;
    }

    public void setHipertensao(boolean hipertensao) {
        this.hipertenso = hipertensao;

        if(hipertensao){
            incrementDiabetes();
            incrementObesidade();
            incrementDoencasCardiovasculares();
            incrementSindromeMetabolica();
        }

    }

    public boolean isFumante() {
        return fumante;
    }

    public void setFumante(boolean fumante) {
        this.fumante = fumante;

        if(fumante){
            incrementDiabetes();
            incrementHipertensao();
            incrementDoencasCardiovasculares();
            incrementSindromeMetabolica();
        }

    }

    public boolean isSedentarismo() {
        return sedentarismo;
    }

    public void setSedentarismo(boolean sedentarismo) {
        this.sedentarismo = sedentarismo;

        if(sedentarismo){
            incrementDiabetes();
            incrementHipertensao();
            incrementObesidade();
            incrementDoencasCardiovasculares();
            incrementSindromeMetabolica();
        }

    }

    public String isRaca() {
        return raca;
    }

    public void setRaca(String raca) {
        this.raca = raca;

        if(raca == "Negra"){
            incrementHipertensao();
        }

    }

    public boolean isAltoConsumoAlcool() {
        return altoConsumoAlcool;
    }

    public void setAltoConsumoAlcool(boolean altoConsumoAlcool) {
        this.altoConsumoAlcool = altoConsumoAlcool;

        if(altoConsumoAlcool){
            incrementHipertensao();
        }

    }

    public boolean isAltoConsumoSodio() {
        return altoConsumoSodio;
    }

    public void setAltoConsumoSodio(boolean altoConsumoSodio) {
        this.altoConsumoSodio = altoConsumoSodio;

        if(altoConsumoSodio) {
            incrementHipertensao();
        }

    }

    public boolean isAltoConsumoGorduraAcucares() {
        return altoConsumoGorduraAcucares;
    }

    public void setAltoConsumoGorduraAcucares(boolean altoConsumoGorduraAcucares) {
        this.altoConsumoGorduraAcucares = altoConsumoGorduraAcucares;

        if(altoConsumoGorduraAcucares){
            incrementDiabetes();
            incrementObesidade();
            incrementDoencasCardiovasculares();
            incrementSindromeMetabolica();
        }

    }

    public String isNivelColesterol() {
        return nivelColesterol;
    }

    public void setNivelColesterol(String colesterolAlto) {
        this.nivelColesterol = colesterolAlto;

        if(colesterolAlto == "Alto") {
            incrementDiabetes();
            incrementObesidade();
            incrementDoencasCardiovasculares();
            incrementSindromeMetabolica();
            incrementHipertensao();
        }

    }

    public boolean isMulherMenopausa() {
        return mulherMenopausa;
    }

    public void setMulherMenopausa(boolean mulherMenopausa) {
        this.mulherMenopausa = mulherMenopausa;

        if(mulherMenopausa){
            incrementHipertensao();
        }

    }

    public boolean isMulherAnticoncepcionais() {
        return mulherAnticoncepcionais;
    }

    public void setMulherAnticoncepcionais(boolean mulherAnticoncepcionais) {
        this.mulherAnticoncepcionais = mulherAnticoncepcionais;

        if(mulherAnticoncepcionais){
            incrementHipertensao();
        }

    }

    public boolean isEstresse() {
        return estresse;
    }

    public void setEstresse(boolean estresse) {
        this.estresse = estresse;

        if(estresse) {
            incrementHipertensao();
            incrementDiabetes();
            incrementObesidade();
            incrementDoencasCardiovasculares();
        }

    }

    public boolean isMulherDiabeteGestacional() {
        return mulherDiabeteGestacional;
    }

    public void setMulherDiabeteGestacional(boolean mulherDiabeteGestacional) {
        this.mulherDiabeteGestacional = mulherDiabeteGestacional;

        if(mulherDiabeteGestacional){
            incrementDiabetes();
        }

    }

    public boolean isUsoCortisona() {
        return usoCortisona;
    }

    public void setUsoCortisona(boolean usoCortisona) {
        this.usoCortisona = usoCortisona;

        if(usoCortisona){
            incrementDiabetes();
        }

    }

    public boolean isUsoDiureticos() {
        return usoDiureticos;
    }

    public void setUsoDiureticos(boolean usoDiureticos) {
        this.usoDiureticos = usoDiureticos;

        if(usoDiureticos){
            incrementDiabetes();
        }

    }

    public boolean isUsoBetabloqueadores() {
        return usoBetabloqueadores;
    }

    public void setUsoBetabloqueadores(boolean usoBetabloqueadores) {
        this.usoBetabloqueadores = usoBetabloqueadores;

        if(usoBetabloqueadores){
            incrementDiabetes();
        }

    }

    public boolean isMulherComFilhos() {
        return mulherComFilhos;
    }

    public void setMulherComFilhos(boolean mulherComFilhos) {
        this.mulherComFilhos = mulherComFilhos;


        if(mulherComFilhos){
            incrementObesidade();
        }

    }

    public boolean isHomemMoraComCompanheira() {
        return homemMoraComCompanheira;
    }

    public void setHomemMoraComCompanheira(boolean homemMoraComCompanheira) {
        this.homemMoraComCompanheira = homemMoraComCompanheira;

        if(homemMoraComCompanheira){
            incrementObesidade();
        }

    }

    public boolean isMulherOvarioPolicistico() {
        return mulherOvarioPolicistico;
    }

    public void setMulherOvarioPolicistico(boolean mulherOvarioPolicistico) {
        this.mulherOvarioPolicistico = mulherOvarioPolicistico;

        if(mulherOvarioPolicistico){
            incrementSindromeMetabolica();
        }

    }

    public boolean isDislipidemia() {
        return dislipidemia;
    }

    public void setDislipidemia(boolean dislipidemia) {
        this.dislipidemia = dislipidemia;

        if(dislipidemia){
            incrementSindromeMetabolica();
        }

    }

    public boolean isMicroalbuminuria() {
        return microalbuminuria;
    }

    public void setMicroalbuminuria(boolean microalbuminuria) {
        this.microalbuminuria = microalbuminuria;

        if(microalbuminuria){
            incrementSindromeMetabolica();
        }

    }

    public boolean isIntoleranciaGlicose() {
        return intoleranciaGlicose;
    }

    public void setIntoleranciaGlicose(boolean intoleranciaGlicose) {
        this.intoleranciaGlicose = intoleranciaGlicose;

        if(intoleranciaGlicose){
            incrementSindromeMetabolica();
        }

    }

    public boolean isIntoleranciaInsulina() {
        return intoleranciaInsulina;
    }

    public void setIntoleranciaInsulina(boolean intoleranciaInsulina) {
        this.intoleranciaInsulina = intoleranciaInsulina;

        if(intoleranciaInsulina){
            incrementSindromeMetabolica();
        }

    }

    public boolean isHiperuricemia() {
        return hiperuricemia;
    }

    public void setHiperuricemia(boolean hiperuricemia) {
        this.hiperuricemia = hiperuricemia;

        if(hiperuricemia){
            incrementSindromeMetabolica();
        }

    }

    public boolean isEstadoProTromboticoProInflamatorio() {
        return estadoProTromboticoProInflamatorio;
    }

    public void setEstadoProTromboticoProInflamatorio(boolean estadoProTromboticoProInflamatorio) {
        this.estadoProTromboticoProInflamatorio = estadoProTromboticoProInflamatorio;

        if(estadoProTromboticoProInflamatorio){
            incrementSindromeMetabolica();
        }

    }

    public boolean isCardiovascularFamiliar() {
        return cardiovascularFamiliar;
    }

    public void setCardiovascularFamiliar(boolean cardiovascularFamiliar) {
        this.cardiovascularFamiliar = cardiovascularFamiliar;

        if(cardiovascularFamiliar){
            incrementDoencasCardiovasculares();
        }

    }

    public boolean isDiabetesFamiliar() {
        return diabetesFamiliar;
    }

    public void setDiabetesFamiliar(boolean diabetesFamiliar) {
        this.diabetesFamiliar = diabetesFamiliar;

        if(diabetesFamiliar){
            incrementDiabetes();
        }

    }

    public boolean isHipertensaoFamiliar() {
        return hipertensaoFamiliar;
    }

    public void setHipertensaoFamiliar(boolean hipertensaoFamiliar) {
        this.hipertensaoFamiliar = hipertensaoFamiliar;

        if(hipertensaoFamiliar){
            incrementHipertensao();
        }

    }

    public boolean isObesidadeFamiliar() {
        return obesidadeFamiliar;
    }

    public void setObesidadeFamiliar(boolean obesidadeFamiliar) {
        this.obesidadeFamiliar = obesidadeFamiliar;

        if(obesidadeFamiliar){
            incrementObesidade();
        }

    }

    public boolean isSindromeFamiliar() {
        return sindromeFamiliar;
    }

    public void setSindromeFamiliar(boolean sindromeFamiliar) {
        this.sindromeFamiliar = sindromeFamiliar;

        if(sindromeFamiliar){
            incrementSindromeMetabolica();
        }

    }

    public void incrementDiabetes(){
        diabetes.setQtdOcorrencias(diabetes.getQtdOcorrencias() + 1);
    }

    public void incrementHipertensao(){
        hipertensao.setQtdOcorrencias(hipertensao.getQtdOcorrencias() + 1);
    }

    public void incrementDoencasCardiovasculares(){
        doencasCardiovasculares.setQtdOcorrencias(doencasCardiovasculares.getQtdOcorrencias() + 1);
    }

    public void incrementObesidade(){
        obesidade.setQtdOcorrencias(obesidade.getQtdOcorrencias() + 1);
    }

    public void incrementSindromeMetabolica(){
        sindromeMetabolica.setQtdOcorrencias(sindromeMetabolica.getQtdOcorrencias() + 1);
    }
}
