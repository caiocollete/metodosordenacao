import metodos.*;

public class Main
{
    //#region Propriedades
    Arquivo arqOrd, arqRev, arqRand, auxRev, auxRand;
    //#endregion

    //#region Funções Essenciais
    public void geraTabela()
    {
        insercaoDireta();
    }
    public void gravaLinhaTabela(int compO, int compOEq, int movO, int movOEq, long tempo,
                                 int compRev, int compRevEq, int movRev, int movRevEq, long tempoRev,
                                 int compRand, int compRandEq, int movRand, int movRandEq, long tempoRand){
        System.out.printf("...");
    }
    public void gerarArquivos(){
        arqOrd = new Arquivo("arqOrdenado.bin");
        arqOrd.geraArquivoOrdenado();

        arqRev = new Arquivo("arqReverso.bin");
        arqRev.geraArquivoReverso();

        arqRand = new Arquivo("arqRandomico.bin");
        arqRand.geraArquivoRandomico();
    }
    //#endregion

    //#region Métodos de Ordenação
    public void insercaoDireta(){
        gerarArquivos();

        InsercaoDireta _insercaoDireta = new InsercaoDireta();
        String linha;

        //#region Arquivo Ordenado
        arqOrd.initComp();
        arqOrd.initMov();
        var tempo_ini=System.currentTimeMillis(); //metodo para pegar a hora atual em milisegundos
        arqOrd.insercaoDireta();
        var tempo_fim=System.currentTimeMillis(); //metodo para pegar a hora atual em milisegundos
        var compO=arqOrd.getComp();
        var movO=arqOrd.getMov();
        var tempo_totalO=tempo_fim-tempo_ini;
        //#endregion

        //#region Arquivo Reverso
        auxRev = new Arquivo("arqReverso - Copia.bin");
        auxRev.copiaArquivo(arqRev.getFile()); //faz uma cópia do arquivo de arqRev
        // para auxRev para preservar o original
        auxRev.initComp();
        auxRev.initMov();
        tempo_ini=System.currentTimeMillis();
        auxRev.insercaoDireta();
        tempo_fim=System.currentTimeMillis();
        var tempo_totalRev=tempo_fim-tempo_ini;
        var compRev=auxRev.getComp();
        var movRev= auxRev.getMov();
        //#endregion

        //#region Arquivo Randomico
        auxRand.copiaArquivo(arqRand.getFile()); //faz uma cópia do arquivo de arqRand
        //para auxRand para preservar o original
        auxRand.initComp();
        auxRand.initMov();
        tempo_ini=System.currentTimeMillis();
        auxRand.insercaoDireta();
        tempo_fim=System.currentTimeMillis();
        var tempo_totalRand=tempo_fim-tempo_ini;
        var compRand=auxRand.getComp();
        var movRand=auxRand.getMov();
        //#endregion

        //grava na tabela informacoes os dados extraídos das execucoes do metodo
        gravaLinhaTabela(compO,
                _insercaoDireta.calculaComp(arqOrd.filesize()),
                movO,
                _insercaoDireta.calculaMov(arqOrd.filesize()),
                tempo_totalO,
                compRev,
                _insercaoDireta.calculaComp(arqRev.filesize()),
                movRev,
                _insercaoDireta.calculaMov(arqRev.filesize()),
                tempo_totalRev,
                compRand,
                _insercaoDireta.calculaComp(arqRand.filesize()),
                movRand,
                _insercaoDireta.calculaMov(arqRev.filesize()),
                tempo_totalRand
        );
    }

    //#endregion

    public static void main(String args[])
    {
        Main p = new Main();
        p.geraTabela();
    }
}