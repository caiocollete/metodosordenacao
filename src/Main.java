import Lista.ListaEncadeada;
import metodos.*;
import java.io.File;
import java.io.IOException;
import java.sql.SQLOutput;
import java.util.Scanner;

public class Main
{
    //#region Funções Essenciais
    public final int N = 1024;
    long tini, tfim, ttotalO;
    int compO, movO;
    int compOrd, compEqOrd, movOrd, movEqOrd;
    int compRev, compEqRev, movRev, movEqRev;
    int compRand, compEqRand, movRand, movEqRand;
    double tseg, e = 2.71828, g = 0.577216;
    long tempoOrd, tempoRev, tempoRand;
    Arquivo arqOrd = new Arquivo("Ordenado");
    Arquivo arqRev = new Arquivo("Reverso");
    Arquivo arqRand = new Arquivo("Aleatório");
    Arquivo arqCopRand = new Arquivo("Aleatório (Cópia)");
    Tabela tabela;
    {
        try {
            tabela = new Tabela("Tabela");
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }

    private void gravarLinhaArquivo(String metodo) throws IOException {
        tabela.gravaLinha(
                metodo,
                compOrd, compEqOrd, movOrd, movEqOrd, tempoOrd,
                compRev, compEqRev, movRev, movEqRev, tempoRev,
                compRand, compEqRand, movRand, movEqRand, tempoRand
        );
    }

    private void start(Arquivo arquivo){
        arquivo.initComp();
        arquivo.initMov();
        tini = System.currentTimeMillis();
    }

    private void end(Arquivo arquivo) {
        tfim = System.currentTimeMillis();
        compO = arquivo.getComp();
        movO = arquivo.getMov();
        ttotalO = tfim - tini;
        tseg = (double) ttotalO / 1000.0; //converter os millisegundos para segundos
    }
    public static void RodarListaEncadeada(){
        ListaEncadeada lista = new ListaEncadeada();

        lista.criarElementosRand(32);
        System.out.println("\nLista Gerada:");
        lista.printarValores();
        System.out.println("Inserção Direta");
        lista.insercaoDireta();
        lista.printarValores();

        lista.criarElementosRand(32);
        System.out.println("\nLista Gerada:");
        lista.printarValores();
        System.out.println("Bubble Sort");
        lista.bubbleSort();
        lista.printarValores();

        lista.criarElementosRand(32);
        System.out.println("\nLista Gerada:");
        lista.printarValores();
        System.out.println("Shake Sort");
        lista.shakesort();
        lista.printarValores();

        lista.criarElementosRand(32);
        System.out.println("\nLista Gerada:");
        lista.printarValores();
        System.out.println("Quick Sort");
        lista.selecaoDireta();
        lista.printarValores();

        lista.criarElementosRand(32);
        System.out.println("\nLista Gerada:");
        lista.printarValores();
        System.out.println("Comb Sort");
        lista.combSort();
        lista.printarValores();

        lista.criarElementosRand(32);
        System.out.println("\nLista Gerada:");
        lista.printarValores();
        System.out.println("Shell Sort");
        lista.shellSort();
        lista.printarValores();

        lista.criarElementosRand(32);
        System.out.println("\nLista Gerada:");
        lista.printarValores();
        System.out.println("Inserção Binaria");
        lista.insercaoBinaria();
        lista.printarValores();

        lista.criarElementosRand(32);
        System.out.println("\nLista Gerada:");
        lista.printarValores();
        System.out.println("Heap Sort");
        lista.heapSort();
        lista.printarValores();

        lista.criarElementosRand(32);
        System.out.println("\nLista Gerada:");
        lista.printarValores();
        System.out.println("Quick Sort SEM PIVO");
        lista.quickSortSemPivo();
        lista.printarValores();

        lista.criarElementosRand(32);
        System.out.println("\nLista Gerada:");
        lista.printarValores();
        System.out.println("Quick Sort COM PIVO");
        lista.quickSortComPivo();
        lista.printarValores();

        lista.criarElementosRand(32);
        System.out.println("\nLista Gerada:");
        lista.printarValores();
        System.out.println("Count Sort");
        lista.countSort();
        lista.printarValores();

        lista.criarElementosRand(32);
        System.out.println("\nLista Gerada:");
        lista.printarValores();
        System.out.println("Radix");
        lista.radixSort();
        lista.printarValores();

        lista.criarElementosRand(32);
        System.out.println("\nLista Gerada:");
        lista.printarValores();
        System.out.println("Bucket");
        lista.bucketSort();
        lista.printarValores();

        lista.criarElementosRand(32);
        System.out.println("\nLista Gerada:");
        lista.printarValores();
        System.out.println("Gnome");
        lista.gnomeSort();
        lista.printarValores();

        lista.criarElementosRand(32);
        System.out.println("\nLista Gerada:");
        lista.printarValores();
        System.out.println("Merge Sort PRIMEIRA IMPLEMENTACAO");
        lista.mergeSortPri();
        lista.printarValores();

        lista.criarElementosRand(32);
        System.out.println("\nLista Gerada:");
        lista.printarValores();
        System.out.println("Merge Sort SEGUNDA  IMPLEMENTACAO");
        lista.mergeSortSeg();
        lista.printarValores();
    }

    private void armazenarOrdenado() {
        compOrd = compO;
        movOrd = movO;
        tempoOrd = (long)tseg;
    }

    private void armazenarReverso() {
        compRev = compO;
        movRev = movO;
        tempoRev = (long)tseg;
    }

    private void armazenarAleatorio() {
        compRand = compO;
        movRand = movO;
        tempoRand = (long)tseg;
    }

    private void exibirInfo(Arquivo arquivo) throws IOException {
        arquivo.exibirArquivo();
        System.out.println();
        System.out.println("Segundos: " + tseg);
        System.out.println("Comp.: "+  compO);
        System.out.println("Mov: " + movO);
    }
    private void excluirArquivos() {
        // Lista de arquivos a serem excluídos
        String[] arquivosParaExcluir = {
                "Ordenado", "Reverso", "Aleatório", "Aleatório (Cópia)",
                "Contador", "Final", "Arq0", "Arq1", "Arq2", "Arq3", "Arq4",
                "Esquerda", "Direita", "NoName.bin"
        };

        for (String nomeArquivo : arquivosParaExcluir) {
            File arquivo = new File(nomeArquivo);
            if (arquivo.exists() && arquivo.isFile()) {
                arquivo.delete();
            }
        }
    }

    private void geraArquivos() throws IOException {
        arqOrd.truncate(0);
        arqRev.truncate(0);
        arqCopRand.truncate(0);

        arqOrd.geraArquivoOrdenado(N);
        arqRev.geraArquivoReverso(N);
        arqCopRand.copiaArquivo(arqRand.getArquivo());
    }

    private void geraArquivoAleatorio() throws IOException {
        arqRand.truncate(0);
        arqRand.geraArquivoAleatorio(N);
    }

    public void geraTabela() throws IOException {
        this.geraArquivoAleatorio();

        for(int i=0; i<=17; i++)
        {
            compEqOrd=0;
            movEqOrd=0;
            compEqRev=0;
            movEqRev=0;
            compEqRand=0;
            movEqRand=0;
            geraArquivos();
            System.out.println();
            switch(i){
                case 1:{
                    //insercao direta
                    System.out.println("Inserção Direta");

                    this.start(arqOrd);
                    arqOrd.insercaoDireta();
                    this.end(arqOrd);
                    System.out.print("Arquivo Ordenado: ");
                    this.exibirInfo(arqOrd);//fim
                    armazenarOrdenado(); //armazenar os valores das mov e comp
                    compEqOrd = N-1;
                    movEqOrd = 3*(N-1);

                    this.start(arqRev);
                    arqRev.insercaoDireta();
                    this.end(arqRev);
                    System.out.print("Arquivo Reverso: ");
                    this.exibirInfo(arqRev);//fim
                    armazenarReverso();
                    compEqRev = (int)((Math.pow(N,2) + N - 4)/4);
                    movEqRev = (int)(Math.pow(N,2) + 3*N - 4)/2;

                    this.start(arqCopRand);
                    arqCopRand.insercaoDireta();
                    this.end(arqCopRand);
                    System.out.print("Arquivo Aleatorio: ");
                    this.exibirInfo(arqCopRand);//fim
                    armazenarAleatorio();
                    compEqRand = (int)(Math.pow(N,2) + N - 2)/4;
                    movEqRand = (int)(Math.pow(N,2) + 9*N - 10)/4;

                    //gravar a linha na tabela
                    gravarLinhaArquivo("Insercao Direta");
                    break;
                } /*insercao Direta*/
                case 2:{
                    //insercao binaria
                    System.out.println("Inserção Binária");

                    this.start(arqOrd);
                    arqOrd.insercaoBinaria();
                    this.end(arqOrd);
                    System.out.print("Arquivo Ordenado: ");
                    this.exibirInfo(arqOrd);//fim
                    armazenarOrdenado(); //armazenar os valores das mov e comp
                    compEqOrd = (int)(N * (Math.log(N) - Math.log(e) + 0.5));
                    movEqOrd = 3*(N-1);

                    this.start(arqRev);
                    arqRev.insercaoBinaria();
                    this.end(arqRev);
                    System.out.print("Arquivo Reverso: ");
                    this.exibirInfo(arqRev);//fim
                    armazenarReverso();
                    compEqRev = (int)(N * (Math.log(N) - Math.log(e) + 0.5));
                    movEqRev = (int)(Math.pow(N,2) + 9*N - 10)/4;

                    this.start(arqCopRand);
                    arqCopRand.insercaoBinaria();
                    this.end(arqCopRand);
                    System.out.print("Arquivo Aleatorio: ");
                    this.exibirInfo(arqCopRand);//fim
                    armazenarAleatorio();
                    compEqRand = (int)(N * (Math.log(N) - Math.log(e) + 0.5));
                    movEqRand = (int)(Math.pow(N,2) + 3*N - 4)/2;

                    //gravar a linha na tabela
                    gravarLinhaArquivo("Insercao Binaria");
                    break;
                } /*insercao Binaria*/
                case 3:{
                    //selecao direta
                    System.out.println("Seleção Direta");

                    this.start(arqOrd);
                    arqOrd.selectionSort();
                    this.end(arqOrd);
                    System.out.print("Arquivo Ordenado: ");
                    this.exibirInfo(arqOrd);//fim
                    armazenarOrdenado(); //armazenar os valores das mov e comp
                    compEqOrd = (int)(Math.pow(N,2) - N)/2;
                    movEqOrd = 3 * (N - 1);

                    this.start(arqRev);
                    arqRev.selectionSort();
                    this.end(arqRev);
                    System.out.print("Arquivo Reverso: ");
                    this.exibirInfo(arqRev);//fim
                    armazenarReverso();
                    compEqRev = compEqOrd;
                    movEqRev = (int)(Math.pow(N,2)/4 + 3 * (N - 1));

                    this.start(arqCopRand);
                    arqCopRand.selectionSort();
                    this.end(arqCopRand);
                    System.out.print("Arquivo Aleatorio: ");
                    this.exibirInfo(arqCopRand);//fim
                    armazenarAleatorio();
                    compEqRand = compEqOrd;
                    movEqRand = (int)(N * (Math.log(N) + g));

                    //gravar a linha na tabela
                    gravarLinhaArquivo("Selecao Direta");
                    break;
                } /*selecao direta*/
                case 4:{
                    //bubble sort
                    System.out.println("Bubble Sort");

                    this.start(arqOrd);
                    arqOrd.bubbleSort();
                    this.end(arqOrd);
                    System.out.print("Arquivo Ordenado: ");
                    this.exibirInfo(arqOrd);//fim
                    armazenarOrdenado(); //armazenar os valores das mov e comp
                    compEqOrd = N-1;
                    movEqOrd = 0;

                    this.armazenarOrdenado();
                    this.start(arqRev);
                    arqRev.bubbleSort();
                    this.end(arqRev);
                    System.out.print("Arquivo Reverso: ");
                    this.exibirInfo(arqRev);//fim
                    armazenarReverso();
                    compEqRev = (int)((Math.pow(N,2) - N)/2);
                    movEqRev = (int)(3 * (Math.pow(N,2) - N)/2);

                    this.armazenarReverso();
                    this.start(arqCopRand);
                    arqCopRand.bubbleSort();
                    this.end(arqCopRand);
                    System.out.print("Arquivo Aleatorio: ");
                    this.exibirInfo(arqCopRand);//fim
                    armazenarAleatorio();
                    compEqRand = (int)((Math.pow(N,2) - N)/2);
                    movEqRand = (int)(3 * (Math.pow(N,2) - N)/4);

                    //gravar a linha na tabela
                    gravarLinhaArquivo("Bubble Sort");
                    break;
                } /*bubble Sort*/
                case 5:{
                    //shake sort
                    System.out.println("Shake Sort");

                    this.start(arqOrd);
                    arqOrd.shakeSort();
                    this.end(arqOrd);
                    System.out.print("Arquivo Ordenado: ");
                    this.exibirInfo(arqOrd);//fim
                    armazenarOrdenado(); //armazenar os valores das mov e comp
                    compEqOrd = N-1;
                    movEqOrd = 0;

                    this.start(arqRev);
                    arqRev.shakeSort();
                    this.end(arqRev);
                    System.out.print("Arquivo Reverso: ");
                    this.exibirInfo(arqRev);//fim
                    armazenarReverso();
                    compEqRev = (int)((Math.pow(N,2) - N)/2);
                    movEqRev = (int)(3 * (Math.pow(N,2) - N)/2);

                    this.start(arqCopRand);
                    arqCopRand.shakeSort();
                    this.end(arqCopRand);
                    System.out.print("Arquivo Aleatorio: ");
                    this.exibirInfo(arqCopRand);//fim
                    armazenarAleatorio();
                    compEqRand = (int)((Math.pow(N,2) - N)/2);
                    movEqRand = (int)(3 * (Math.pow(N,2) - N)/4);

                    //gravar a linha na tabela
                    gravarLinhaArquivo("Shake Sort");
                    break;
                } /*shake Sort*/
                case 6:{
                    //shell sort
                    System.out.println("Shell Sort");

                    this.start(arqOrd);
                    arqOrd.shellSort();
                    this.end(arqOrd);
                    System.out.print("Arquivo Ordenado: ");
                    this.exibirInfo(arqOrd);//fim
                    armazenarOrdenado(); //armazenar os valores das mov e comp

                    this.start(arqRev);
                    arqRev.shellSort();
                    this.end(arqRev);
                    System.out.print("Arquivo Reverso: ");
                    this.exibirInfo(arqRev);//fim
                    armazenarReverso();

                    this.start(arqCopRand);
                    arqCopRand.shellSort();
                    this.end(arqCopRand);
                    System.out.print("Arquivo Aleatorio: ");
                    this.exibirInfo(arqCopRand);//fim
                    armazenarAleatorio();

                    //gravar a linha na tabela
                    gravarLinhaArquivo("Shell Sort");
                    break;
                } /*shell sort*/
                case 7:{
                    //heap sort
                    System.out.println("Heap Sort");

                    this.start(arqOrd);
                    arqOrd.heapSort();
                    this.end(arqOrd);
                    System.out.print("Arquivo Ordenado: ");
                    this.exibirInfo(arqOrd);//fim
                    armazenarOrdenado(); //armazenar os valores das mov e comp

                    this.start(arqRev);
                    arqRev.heapSort();
                    this.end(arqRev);
                    System.out.print("Arquivo Reverso: ");
                    this.exibirInfo(arqRev);//fim
                    armazenarReverso();

                    this.start(arqCopRand);
                    arqCopRand.heapSort();
                    this.end(arqCopRand);
                    System.out.print("Arquivo Aleatorio: ");
                    this.exibirInfo(arqCopRand);//fim
                    armazenarAleatorio();

                    //gravar a linha na tabela
                    gravarLinhaArquivo("Heap Sort");
                    break;
                } /*heap sort*/
                case 8:{
                    //comb sort
                    System.out.println("Comb Sort");

                    this.start(arqOrd);
                    arqOrd.combSort();
                    this.end(arqOrd);
                    System.out.print("Arquivo Ordenado: ");
                    this.exibirInfo(arqOrd);//fim
                    armazenarOrdenado(); //armazenar os valores das mov e comp

                    this.start(arqRev);
                    arqRev.combSort();
                    this.end(arqRev);
                    System.out.print("Arquivo Reverso: ");
                    this.exibirInfo(arqRev);//fim
                    armazenarReverso();

                    this.start(arqCopRand);
                    arqCopRand.combSort();
                    this.end(arqCopRand);
                    System.out.print("Arquivo Aleatorio: ");
                    this.exibirInfo(arqCopRand);//fim
                    armazenarAleatorio();

                    //gravar a linha na tabela
                    gravarLinhaArquivo("Comb Sort");
                    break;
                } /*comb sort*/
                case 9:{
                    //quick sort sem pivo
                    System.out.println("Quick Sort SEM Pivo");

                    this.start(arqOrd);
                    arqOrd.quickSort_nopivot();
                    this.end(arqOrd);
                    System.out.print("Arquivo Ordenado: ");
                    this.exibirInfo(arqOrd);//fim
                    armazenarOrdenado(); //armazenar os valores das mov e comp

                    this.start(arqRev);
                    arqRev.quickSort_nopivot();
                    this.end(arqRev);
                    System.out.print("Arquivo Reverso: ");
                    this.exibirInfo(arqRev);//fim
                    armazenarReverso();

                    this.start(arqCopRand);
                    arqCopRand.quickSort_nopivot();
                    this.end(arqCopRand);
                    System.out.print("Arquivo Aleatorio: ");
                    this.exibirInfo(arqCopRand);//fim
                    armazenarAleatorio();

                    //gravar a linha na tabela
                    gravarLinhaArquivo("Quick SEM Pivo");
                    break;
                } /*quick sort sem pivo*/
                case 10:{
                    //quick sort com pivo
                    System.out.println("Quick Sort COM Pivo");

                    this.start(arqOrd);
                    arqOrd.quickSort_pivot();
                    this.end(arqOrd);
                    System.out.print("Arquivo Ordenado: ");
                    this.exibirInfo(arqOrd);//fim
                    armazenarOrdenado(); //armazenar os valores das mov e comp

                    this.start(arqRev);
                    arqRev.quickSort_pivot();
                    this.end(arqRev);
                    System.out.print("Arquivo Reverso: ");
                    this.exibirInfo(arqRev);//fim
                    armazenarReverso();

                    this.start(arqCopRand);
                    arqCopRand.quickSort_pivot();
                    this.end(arqCopRand);
                    System.out.print("Arquivo Aleatorio: ");
                    this.exibirInfo(arqCopRand);//fim
                    armazenarAleatorio();

                    //gravar a linha na tabela
                    gravarLinhaArquivo("Quick COM Pivo");
                    break;
                } /*quick sort com pivo*/
                case 11:{
                    //count sort
                    System.out.println("Count Sort");

                    this.start(arqOrd);
                    arqOrd.countSort();
                    this.end(arqOrd);
                    System.out.print("Arquivo Ordenado: ");
                    this.exibirInfo(arqOrd);//fim
                    armazenarOrdenado(); //armazenar os valores das mov e comp

                    this.start(arqRev);
                    arqRev.countSort();
                    this.end(arqRev);
                    System.out.print("Arquivo Reverso: ");
                    this.exibirInfo(arqRev);//fim
                    armazenarReverso();

                    this.start(arqCopRand);
                    arqCopRand.countSort();
                    this.end(arqCopRand);
                    System.out.print("Arquivo Aleatorio: ");
                    this.exibirInfo(arqCopRand);//fim
                    armazenarAleatorio();

                    //gravar a linha na tabela
                    gravarLinhaArquivo("Count Sort");
                    break;
                } /*count sort*/
                case 12:{
                    //radix sort
                    System.out.println("Radix Sort");

                    this.start(arqOrd);
                    arqOrd.radixSort();
                    this.end(arqOrd);
                    System.out.print("Arquivo Ordenado: ");
                    this.exibirInfo(arqOrd);//fim
                    armazenarOrdenado(); //armazenar os valores das mov e comp

                    this.start(arqRev);
                    arqRev.radixSort();
                    this.end(arqRev);
                    System.out.print("Arquivo Reverso: ");
                    this.exibirInfo(arqRev);//fim
                    armazenarReverso();

                    this.start(arqCopRand);
                    arqCopRand.radixSort();
                    this.end(arqCopRand);
                    System.out.print("Arquivo Aleatorio: ");
                    this.exibirInfo(arqCopRand);//fim
                    armazenarAleatorio();

                    //gravar a linha na tabela
                    gravarLinhaArquivo("Radix Sort");
                    break;
                } /*radix sort*/
                case 13:{
                    //bucket sort
                    System.out.println("Bucket Sort");

                    this.start(arqOrd);
                    arqOrd.bucketSort();
                    this.end(arqOrd);
                    System.out.print("Arquivo Ordenado: ");
                    this.exibirInfo(arqOrd);//fim
                    armazenarOrdenado(); //armazenar os valores das mov e comp

                    this.start(arqRev);
                    arqRev.bucketSort();
                    this.end(arqRev);
                    System.out.print("Arquivo Reverso: ");
                    this.exibirInfo(arqRev);//fim
                    armazenarReverso();

                    this.start(arqCopRand);
                    arqCopRand.bucketSort();
                    this.end(arqCopRand);
                    System.out.print("Arquivo Aleatorio: ");
                    this.exibirInfo(arqCopRand);//fim
                    armazenarAleatorio();

                    //gravar a linha na tabela
                    gravarLinhaArquivo("Bucket Sort");
                    break;
                } /*bucket sort*/
                case 14:{
                    //gnome sort
                    System.out.println("Gnome Sort");

                    this.start(arqOrd);
                    arqOrd.gnomeSort();
                    this.end(arqOrd);
                    System.out.print("Arquivo Ordenado: ");
                    this.exibirInfo(arqOrd);//fim
                    armazenarOrdenado(); //armazenar os valores das mov e comp

                    this.start(arqRev);
                    arqRev.gnomeSort();
                    this.end(arqRev);
                    System.out.print("Arquivo Reverso: ");
                    this.exibirInfo(arqRev);//fim
                    armazenarReverso();

                    this.start(arqCopRand);
                    arqCopRand.gnomeSort();
                    this.end(arqCopRand);
                    System.out.print("Arquivo Aleatorio: ");
                    this.exibirInfo(arqCopRand);//fim
                    armazenarAleatorio();

                    //gravar a linha na tabela
                    gravarLinhaArquivo("Gnome Sort");
                    break;
                } /*gnome sort*/
                case 15:{
                    //merge sort primeira implementação
                    System.out.println("Merge Sort PRIMEIRA Implementação");

                    this.start(arqOrd);
                    arqOrd.mergeSort_first();
                    this.end(arqOrd);
                    System.out.print("Arquivo Ordenado: ");
                    this.exibirInfo(arqOrd);//fim
                    armazenarOrdenado(); //armazenar os valores das mov e comp

                    this.start(arqRev);
                    arqRev.mergeSort_first();
                    this.end(arqRev);
                    System.out.print("Arquivo Reverso: ");
                    this.exibirInfo(arqRev);//fim
                    armazenarReverso();

                    this.start(arqCopRand);
                    arqCopRand.mergeSort_first();
                    this.end(arqCopRand);
                    System.out.print("Arquivo Aleatorio: ");
                    this.exibirInfo(arqCopRand);//fim
                    armazenarAleatorio();

                    //gravar a linha na tabela
                    gravarLinhaArquivo("Merge MULTIPLOS 2");
                    break;
                } /*merge sort primeira implementação*/
                case 16:{
                    //merge sort segunda implementação
                    System.out.println("Merge Sort SEGUNDA Implementação");

                    this.start(arqOrd);
                    arqOrd.mergeSortSegundaImplement();
                    this.end(arqOrd);
                    System.out.print("Arquivo Ordenado: ");
                    this.exibirInfo(arqOrd);//fim
                    armazenarOrdenado(); //armazenar os valores das mov e comp

                    this.start(arqRev);
                    arqRev.mergeSortSegundaImplement();
                    this.end(arqRev);
                    System.out.print("Arquivo Reverso: ");
                    this.exibirInfo(arqRev);//fim
                    armazenarReverso();

                    this.start(arqCopRand);
                    arqCopRand.mergeSortSegundaImplement();
                    this.end(arqCopRand);
                    System.out.print("Arquivo Aleatorio: ");
                    this.exibirInfo(arqCopRand);//fim
                    armazenarAleatorio();

                    //gravar a linha na tabela
                    gravarLinhaArquivo("Merge Sort");
                    break;
                } /*merge sort segunda implementação*/
                case 17:{
                    //tim sort
                    System.out.println("Tim Sort");

                    this.start(arqOrd);
                    arqOrd.timSort();
                    this.end(arqOrd);
                    System.out.print("Arquivo Ordenado: ");
                    this.exibirInfo(arqOrd);//fim
                    armazenarOrdenado(); //armazenar os valores das mov e comp

                    this.start(arqRev);
                    arqRev.timSort();
                    this.end(arqRev);
                    System.out.print("Arquivo Reverso: ");
                    this.exibirInfo(arqRev);//fim
                    armazenarReverso();

                    this.start(arqCopRand);
                    arqCopRand.timSort();
                    this.end(arqCopRand);
                    System.out.print("Arquivo Aleatorio: ");
                    this.exibirInfo(arqCopRand);//fim
                    armazenarAleatorio();

                    //gravar a linha na tabela
                    gravarLinhaArquivo("Tim Sort");
                    break;
                } /*tim sort*/
            }
            excluirArquivos();
        }
        tabela.fechar();
    }

    //#endregion


    public static void main(String args[]) throws IOException {

        Scanner ler = new Scanner(System.in);

        System.out.println("O que quer rodar? (1 - Arquivos ou 2 - Lista Encadeada) ");
        switch(ler.nextInt()){
            case 1:
                Main p = new Main();
                p.geraTabela();
                break;
            case 2:
                RodarListaEncadeada();
                break;
            default:
             System.out.println("Saindo da aplicação");
             break;
        }

    }
}