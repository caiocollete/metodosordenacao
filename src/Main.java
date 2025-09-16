import metodos.*;

import java.io.RandomAccessFile;

public class Main
{
    //#region Propriedades
    Arquivo arqOrd, arqRev, arqRand, auxRev, auxRand;
    RandomAccessFile tabela;
    //#endregion

    //#region Funções Essenciais
    public void iniciarVars(){
        auxRev = new Arquivo("auxRev");
        auxRand = new Arquivo("auxRand");
    }

    public void geraTabela()
    {
        inicializarTabela();
        gerarArquivos();
        insercaoDireta();
        //insercaoBinaria();
        fecharTabela();
    }
    
    public void fecharTabela()
    {
        try {
            if (tabela != null) {
                tabela.close();
            }
        } catch (Exception e) {
            System.err.println("Erro ao fechar tabela: " + e.getMessage());
        }
    }
    
    public void inicializarTabela()
    {
        try {
            tabela = new RandomAccessFile("tabela_resultados.txt", "rw");
            String header = String.format("  Métodos Ordenação  |             Arquivo Ordenado                 |          Arquivo em Ordem Reversa            |               Arquivo Randômico              \n");
            String separador = "-------------------- | -------- -------- -------- -------- -------- | -------- -------- -------- -------- -------- | -------- -------- -------- -------- --------\n";
            String subheader = String.format("%-20s | %-8s %-8s %-8s %-8s %-8s | %-8s %-8s %-8s %-8s %-8s | %-8s %-8s %-8s %-8s %-8s%n",
                    "Método",
                    "Comp. Prog Ordenado", "Comp. ProgEq", "MovO", "MovOEq", "Tempo",
                    "CompRev", "CompRevEq", "MovRev", "MovRevEq", "TempoRev",
                    "CompRand", "CompRandEq", "MovRand", "MovRandEq", "TempoRand");
            tabela.writeBytes(header);
            tabela.writeBytes(separador);
            tabela.writeBytes(subheader);
            tabela.writeBytes(separador);
        } catch (Exception e) {
            System.err.println("Erro ao inicializar tabela: " + e.getMessage());
        }
    }
    public void gravaLinhaTabela(String metodo, double compO, double compOEq, double movO, double movOEq, long tempo,
                                 double compRev, double compRevEq, double movRev, double movRevEq, long tempoRev,
                                 double compRand, double compRandEq, double movRand, double movRandEq, long tempoRand) {

        String linha = String.format(
                "%-20s | %-8.2f %-8.2f %-8.2f %-8.2f %-8d | %-8.2f %-8.2f %-8.2f %-8.2f %-8d | %-8.2f %-8.2f %-8.2f %-8.2f %-8d%n",
                metodo,
                compO, compOEq, movO, movOEq, tempo,
                compRev, compRevEq, movRev, movRevEq, tempoRev,
                compRand, compRandEq, movRand, movRandEq, tempoRand
        );

        try {
            tabela.writeBytes(linha);
        } catch (Exception e) {
            System.err.println("Erro ao escrever na tabela: " + e.getMessage());
        }

        System.out.print(linha);
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
        gravaLinhaTabela("Inserção Direta", compO,
                _insercaoDireta.calculaComp(arqOrd.getComp()),
                movO,
                _insercaoDireta.calculaMov(arqOrd.getMov()),
                tempo_totalO,
                compRev,
                _insercaoDireta.calculaComp(arqRev.getComp()),
                movRev,
                _insercaoDireta.calculaMov(arqRev.getMov()),
                tempo_totalRev,
                compRand,
                _insercaoDireta.calculaComp(arqRand.getComp()),
                movRand,
                _insercaoDireta.calculaMov(arqRev.getMov()),
                tempo_totalRand
        );
    }

    public void insercaoBinaria(){
        InsercaoBinaria _insercaoBinaria = new InsercaoBinaria();
        String linha;

        //#region Arquivo Ordenado
        arqOrd.initComp();
        arqOrd.initMov();
        var tempo_ini=System.currentTimeMillis(); //metodo para pegar a hora atual em milisegundos
        arqOrd.insercaoBinaria();
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
        auxRev.insercaoBinaria();
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
        auxRand.insercaoBinaria();
        tempo_fim=System.currentTimeMillis();
        var tempo_totalRand=tempo_fim-tempo_ini;
        var compRand=auxRand.getComp();
        var movRand=auxRand.getMov();
        //#endregion

        //grava na tabela informacoes os dados extraídos das execucoes do metodo
        gravaLinhaTabela("Inserção Binária", compO,
                _insercaoBinaria.calculaComp(arqOrd.getComp()),
                movO,
                _insercaoBinaria.calculaMov(arqOrd.getMov()),
                tempo_totalO,
                compRev,
                _insercaoBinaria.calculaComp(arqRev.getComp()),
                movRev,
                _insercaoBinaria.calculaMov(arqRev.getMov()),
                tempo_totalRev,
                compRand,
                _insercaoBinaria.calculaComp(arqRand.getComp()),
                movRand,
                _insercaoBinaria.calculaMov(arqRev.getMov()),
                tempo_totalRand
        );
    }

    //#endregion

    public static void main(String args[])
    {
        Main p = new Main();
        p.iniciarVars();
        p.geraTabela();
    }
}