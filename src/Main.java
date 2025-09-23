import Lista.ListaEncadeada;
import metodos.*;

public class Main
{
    Arquivo arqOrd, arqRev, arqRand, auxRev, auxRand;

    public void geraTabela()

    {
        insercaoDireta();
        //e assim continua para os outros métodos de ordenacao!!!
    }

    public void gravaLinhaTabela(int compO, int compOEq, int movO, int movOEq, long tempo,
                                 int compRev, int compRevEq, int movRev, int movRevEq, long tempoRev,
                                 int compRand, int compRandEq, int movRand, int movRandEq, long tempoRand){
        System.out.printf("...");
    }

    public void gerarArquivos(){
        arqOrd.geraArquivoOrdenado();
        arqRev.geraArquivoReverso();
        arqRand.geraArquivoRandomico();
    }
    public void insercaoDireta(){
        gerarArquivos();

        InsercaoDireta _insercaoDireta = new InsercaoDireta();
        String linha;

        //Arquivo Ordenado
        arqOrd.initComp();
        arqOrd.initMov();
        var tempo_ini=System.currentTimeMillis(); //metodo para pegar a hora atual em milisegundos
        arqOrd.insercaoDireta();
        var tempo_fim=System.currentTimeMillis(); //metodo para pegar a hora atual em milisegundos
        var compO=arqOrd.getComp();
        var movO=arqOrd.getMov();
        var tempo_totalO=tempo_fim-tempo_ini;

        //Arquivo Reverso
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

        //Arquivo Randomico
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
    public static void RodarListaEncadeada(){
        ListaEncadeada lista = new ListaEncadeada();
        lista.criarElementosRand(32);
        lista.printarValores();

        //lista.insercaoDireta();
        //lista.bubbleSort();
        //lista.shakesort();
        //lista.selecaoDireta();
        //lista.combSort();
        //lista.shellSort();
        //lista.insercaoBinaria();
        //lista.heapSort();
        //lista.quickSortSemPivo();
        //lista.quickSortComPivo();
        //lista.countSort();
        //lista.radixSort();
        //lista.bucketSort();
        //lista.gnomeSort();
        lista.mergeSortPri();
        //lista.mergeSortSeg();

        lista.printarValores();
    }
    public static void main(String args[])
    {
        //Main p = new Main();
        //p.geraTabela();


        RodarListaEncadeada();

    }
}