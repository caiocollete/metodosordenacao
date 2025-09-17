import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Random;

/*
*  DEVEMOS GRAVAR O REGISTRO
*  E NAO ARQUIVO.WRITEINT
* */

public class Arquivo{
    //#region Propriedades
    private static int tamInt = 4;
    private String nomearquivo;
    private RandomAccessFile arquivo;
    private int comp, mov;
    //#endregion

    //#region Funções da Classe
    public Arquivo(String nomearquivo){
        this.nomearquivo = nomearquivo;
        try{
            this.arquivo = new RandomAccessFile(this.nomearquivo, "rw");
        }catch(FileNotFoundException e){
            this.arquivo = null;
        }
    }
    public Arquivo(){
        this.nomearquivo = "NoName.bin";
        try{
            this.arquivo = new RandomAccessFile(this.nomearquivo, "rw");
        }catch(FileNotFoundException e){
            this.arquivo = null;
        }
    }
    public void copiaArquivo(RandomAccessFile arquivoOrigem){
        try{
            arquivoOrigem.seek(0);
            this.arquivo.seek(0);
            while(arquivoOrigem.getFilePointer()<arquivoOrigem.length()){
                this.arquivo.writeByte(arquivoOrigem.readByte());
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public RandomAccessFile getFile() {
        return arquivo;
    }
    public void truncate(long pos) {
        try{
            arquivo.setLength(pos);
        }catch(Exception e){
            System.out.printf("Erro ao truncar arquivo: %s\n", e.getMessage());
        }
    }
    public boolean eof() {
        try{
            var tam = arquivo.length();
            if(arquivo.getFilePointer()==tam)
                return true;
        } catch (IOException e) {
            System.out.printf("Erro ao truncar arquivo: %s\n", e.getMessage());
        }
        return false;
    }
    public void seekArq(int pos) {
        try{
            arquivo.seek(pos*Registro.length());
        } catch (IOException e) {
            System.out.printf("Erro ao seekar arquivo: %s\n", e.getMessage());
        }
    }
    public long filesize() {
        try{
            return arquivo.length()/Registro.length();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public void initComp() {
        comp = 0;
    }
    public void initMov() {
        mov = 0;
    }
    public int getComp() {
        return comp;
    }
    public int getMov() {
        return mov;
    }
    public void addComp(){
        comp++;
    }
    public void addMov(){
        mov++;
    }
    public void geraArquivoOrdenado(int n) {
        Registro registro = new Registro();
        for (int i = 0; i < n; i++) {
            registro.setNumero(i + 1);
            registro.gravaNoArq(this.arquivo);
        }
    }

    public void geraArquivoReverso(int n) {
        Registro registro = new Registro();
        for (int i = 0; i < n; i++) {
            registro.setNumero(n-i);
            registro.gravaNoArq(this.arquivo);
        }
    }
    public void geraArquivoAleatorio(int n) {
        Random sorteador = new Random();
        Registro registro = new Registro();
        for (int i = 0; i < n; i++) {
            registro.setNumero(sorteador.nextInt(n) + 1);
            registro.gravaNoArq(this.arquivo);
        }
    }
    public void gerarArquivoVazio(int n) {
        Registro registro = new Registro();
        for (int i = n; i > 0; i--) {
            registro.setNumero(0);
            registro.gravaNoArq(this.arquivo);
        }
    }

    public RandomAccessFile getArquivo() {
        return arquivo;
    }
    public void insereNoFinal(Registro registro) throws IOException {
        this.seekArq((int) this.filesize());
        registro.gravaNoArq(this.arquivo);
    }

    private int contaDigitos(int numero){
        int contador = 0;
        if(numero!=0){
            while(numero!=0){
                numero = numero/10;
                contador++;
            }
            return contador;
        }
        //se o numero recebido for 0, ele retorna que possui apenas 1 digito
        return 1;
    }

    private int obterDigito(int numero, int d) {
        int divisor = 1;

        // Calcula o divisor para alcançar a posição desejada
        for (int i = 1; i < d; i++) {
            divisor *= 10;
        }

        return (numero / divisor) % 10;
    }

    private int calcMinRunTIM(int n, int min_merge) {
        int r = 0;
        while (n >= min_merge) {
            r = r + (n % 2);
            n = n/2;
        }
        return n + r;
    } /*cálculo da RUN mínima do TIM*/

    public void exibirArquivo() throws IOException {
        Registro registro = new Registro();
        arquivo.seek(0);
        while (!eof()) {
            registro.leDoArq(arquivo);
            System.out.print(registro.getNumero()+" ");

            /*
            Para exibir nao somente o numero como tambem o conteudo do 'lixo'
            System.out.print(registro.getNumero());
            System.out.println("  " + Arrays.toString(registro.getLixo()));
            */
        }
        System.out.print("");
    }
    //#endregion

    //#region Metódos de Ordenação
    public void insercaoDireta() throws IOException {
        insercaoDireta(0, (int)this.filesize()-1);
    } /*insercao direta*/

    private void insercaoDireta(int start, int end) throws IOException{
        int pos;
        Registro registro = new Registro(), registroAux = new Registro();

        comp++;
        for(int i=start+1; i<=end; i++){
            comp++;
            seekArq(i); registroAux.leDoArq(arquivo);
            pos = i;

            seekArq(pos-1); registro.leDoArq(arquivo);
            comp++;
            while(pos>start && registro.getNumero()>registroAux.getNumero()){
                comp++;
                seekArq(pos-1); registro.leDoArq(arquivo);
                seekArq(pos); registro.gravaNoArq(arquivo);
                mov++;
                pos--;
                comp++;
                if(pos>start){
                    seekArq(pos-1); registro.leDoArq(arquivo);
                }
            }
            seekArq(pos); registroAux.gravaNoArq(arquivo);
            mov++;
        }
    }
    public int buscaBinaria(int target, int tl){
        int inicio = 0, fim = tl, meio=fim/2;
        Registro reg = new Registro();
        seekArq(fim-1);
        reg.leDoArq(arquivo);
        if(target < reg.getNumero()){
            addComp();
            seekArq(meio);
            while(inicio<fim){
                reg.leDoArq(arquivo);
                addComp();
                if(reg.getNumero()>target){
                    fim = meio-1;
                }
                else{
                    inicio=meio+1;
                }
                meio = (inicio+fim)/2;
                seekArq(meio);
            }
            return meio;
        }
        return tl;
    }
    public void insercaoBinaria(){
        int aux;
        int n = (int)this.filesize(), pos;
        Registro reg = new Registro();
        for(int i = 1; i < n; i++){
            seekArq(i);
            reg.leDoArq(arquivo);
            aux = reg.getNumero();
            pos = buscaBinaria(aux, i);
            for(int j = i; j>pos; j--){
                seekArq(j-1);
                reg.leDoArq(arquivo);
                int value = reg.getNumero();
                seekArq(j);
                reg.setNumero(value);
                reg.gravaNoArq(arquivo);
                addMov();
            }
            seekArq(pos);
            reg.setNumero(aux);
            reg.gravaNoArq(arquivo);
            addMov();
        }
    }
    public void bubbleSort(){
        long TL = filesize();
        boolean flag = true;
        Registro reg = new Registro();
        Registro reg2 = new Registro();
        while(TL>1 && flag){
            flag = false;
            for(int i = 1; i < TL-1; i++){
                seekArq(i);
                reg.leDoArq(arquivo);
                reg2.leDoArq(arquivo);
                if(reg.getNumero()>reg2.getNumero()){
                    seekArq(i);
                    reg2.gravaNoArq(arquivo);
                    reg.gravaNoArq(arquivo);
                    flag = true;
                    addMov();
                    addComp();
                }
            }
            TL--;
        }

    }

    public void selectionSort(){
        int posmenor, aux, j;
        long tl=filesize();
        Registro reg = new Registro();
        Registro reg2 = new Registro();
        for( int i = 0; i < tl; i++) {
            posmenor = i;
            seekArq(posmenor);
            reg.leDoArq(arquivo);
            for( j = i; j < tl; j++) {
                seekArq(j);
                reg2.leDoArq(arquivo);
                addComp();
                if(reg.getNumero() > reg2.getNumero()) {
                    posmenor = j;
                }
            }

            seekArq(i);
            reg2.leDoArq(arquivo);
            aux = reg2.getNumero();

            seekArq(posmenor);
            reg.leDoArq(arquivo);
            reg2.setNumero(reg.getNumero());

            reg.setNumero(aux);
            seekArq(posmenor);
            reg.gravaNoArq(arquivo);

            addMov();
        }
    }

    public void shakeSort() {
        int inicio = 0, fim = (int)filesize()-1, aux;
        boolean flag = true;
        Registro reg = new Registro();
        Registro reg2 = new Registro();
        while(inicio<fim && flag) {
            flag = false;
            for(int i = inicio; i<fim-1; i++) {
                seekArq(i);
                reg.leDoArq(arquivo);
                reg2.leDoArq(arquivo);
                addComp();
                if(reg.getNumero() > reg2.getNumero()) {

                    aux = reg.getNumero();
                    reg.setNumero(reg2.getNumero());
                    reg2.setNumero(aux);

                    seekArq(i);
                    reg.gravaNoArq(arquivo);
                    reg2.gravaNoArq(arquivo);
                    addMov();
                    flag = true;
                }
            }
            fim--;

            for(int i=fim; i>inicio; i--) {
                seekArq(i-1);
                reg2.leDoArq(arquivo);
                reg.leDoArq(arquivo);
                if(reg.getNumero() < reg2.getNumero()) {
                    aux = reg.getNumero();
                    reg.setNumero(reg2.getNumero());
                    reg2.setNumero(aux);

                    seekArq(i-1);
                    reg2.gravaNoArq(arquivo);
                    reg.gravaNoArq(arquivo);
                    flag = true;
                }
            }
            inicio++;
        }
    }

    public void heapSort() {
        // baseado em arvore
        // construir uma arvore heap (filhos menor que o pai)
        // if dir = 2*n+2
        // if esc = 2*n+1
        // devemos comecar pelo ultimo
        int pai, fe, fd, maior, aux;
        Registro reg = new Registro();
        Registro reg2 = new Registro();
        for(int tl = (int)filesize(); tl>1; tl--) {
            pai = tl/2-1;
            while(pai>=0){
                fe = pai*2+1;
                fd = fe+1;

                seekArq(fe);
                reg.leDoArq(arquivo);
                seekArq(fd);
                reg2.leDoArq(arquivo);

                addComp();
                if(fd < tl && reg.getNumero() < reg2.getNumero())
                    maior = fd;
                else
                    maior = fe;

                seekArq(maior);
                reg.leDoArq(arquivo);
                seekArq(pai);
                reg2.leDoArq(arquivo);

                addComp();
                if(reg.getNumero() > reg2.getNumero()) {
                    aux = reg.getNumero();
                    reg.setNumero(reg2.getNumero());
                    reg2.setNumero(aux);

                    seekArq(maior);
                    reg.gravaNoArq(arquivo);
                    seekArq(pai);
                    reg2.gravaNoArq(arquivo);
                    addMov();
                }
                pai--;
            }
            seekArq(0);
            reg.leDoArq(arquivo);
            seekArq(tl);
            reg2.leDoArq(arquivo);
            aux = reg.getNumero();
            reg.setNumero(reg2.getNumero());
            reg2.setNumero(aux);
        }
    }

    public void shellSort() throws IOException {
        int dist=1, pos, tl=(int)this.filesize();
        Registro registro = new Registro(), registroAux = new Registro();

        comp++;
        while(dist<tl) {
            comp++;
            dist = dist * 2 + 1;
        }
        dist /= 2;

        comp++;
        while(dist>0){
            comp++;
            comp++;
            for (int i=dist; i<tl; i++) {
                comp++;
                this.seekArq(i); registroAux.leDoArq(this.arquivo);
                pos = i;
                comp++;
                if(pos>=dist){
                    this.seekArq(pos-dist); registro.leDoArq(this.arquivo);
                }
                comp++;
                while(pos>=dist && registroAux.getNumero()<registro.getNumero()){
                    comp++;
                    this.seekArq(pos-dist); registro.leDoArq(this.arquivo);
                    this.seekArq(pos); registro.gravaNoArq(this.arquivo);
                    mov++;
                    pos -= dist;
                    comp++;
                    if(pos>=dist){
                        this.seekArq(pos-dist); registro.leDoArq(this.arquivo);
                    }
                }
                this.seekArq(pos); registroAux.gravaNoArq(this.arquivo);
                mov++;
            }
            dist /= 2;
        }
    }

    //#region Quick Sort (Pivô)
    public void quickSort_pivot(){
        ordenate(0, (int)filesize());
    }

    private void ordenate(int inicio, int fim){
        if(inicio<fim){
            int pi = part(inicio, fim);
            ordenate(inicio, pi-1);
            ordenate(pi+1, fim);
        }
    }

    private int part(int inicio, int fim){
        Registro reg = new Registro();
        Registro reg2 = new Registro();
        seekArq(fim);
        reg.leDoArq(arquivo);
        int num = reg.getNumero();
        int i = (inicio - 1);
        for (int j = inicio; j < fim; j++) {
            seekArq(j);
            reg.leDoArq(arquivo);
            addComp();
            if (reg.getNumero() < num) {
                i++;
                seekArq(i);
                reg2.leDoArq(arquivo);
                int temp = reg.getNumero();
                reg2.setNumero(reg.getNumero());
                reg.setNumero(temp);

                seekArq(j);
                reg.gravaNoArq(arquivo);
                seekArq(i);
                reg2.gravaNoArq(arquivo);
                addMov();
            }
        }
        seekArq(i+1);
        reg.leDoArq(arquivo);
        seekArq(fim);
        reg2.leDoArq(arquivo);
        int temp = reg.getNumero();
        reg.setNumero(reg2.getNumero());
        reg2.setNumero(temp);

        seekArq(i+1);
        reg.gravaNoArq(arquivo);
        seekArq(fim);
        reg2.gravaNoArq(arquivo);
        addMov();

        return i + 1;
    }
    //#endregion

    //#region Quick Sort (Sem Pivô)
    public void quickSort_nopivot(){
        quickSortSP(0, (int)filesize()-1);
    }
    public void quickSortSP(int inicio, int fim){
        int i = inicio, j = fim, aux;
        boolean flag = true;
        Registro menor = new Registro();
        Registro maior = new Registro();

        while(i<j){
            seekArq(i);
            menor.leDoArq(arquivo);
            seekArq(j);
            maior.leDoArq(arquivo);
            if(flag){
                addComp();
                while(i<j  && menor.getNumero()<=maior.getNumero()){
                    i++;
                    seekArq(i);
                    menor.leDoArq(arquivo);
                    addComp();
                }
            }
            else{
                addComp();
                while(i<j && menor.getNumero()<=maior.getNumero()){
                    addComp();
                    j--;
                    seekArq(j);
                    maior.leDoArq(arquivo);
                }
            }
            aux = menor.getNumero();
            menor.setNumero(maior.getNumero());
            maior.setNumero(aux);
            seekArq(i);
            menor.gravaNoArq(arquivo);
            seekArq(j);
            maior.gravaNoArq(arquivo);
            addMov();
            flag = !flag;
        }
        if(i+1<fim){
            quickSortSP(i+1, fim);
        }
        if(inicio<j-1){
            quickSortSP(inicio, j-1);
        }
    }
    //#endregion

    //#region Merge Sort (First Implementation)
    public void mergeSort_first(){
        int tl = (int) filesize()/2;
        int[] v1 = new int[tl], v2 = new int[tl];
        int seq = 1;

        while(seq<filesize()/2){
            particao(v1,v2);
            fusao(v1,v2,seq);
            seq = seq*2;
        }
    }

    public void particao(int[] v1, int[] v2){
        int i = 0, j = 0, k = 0, tl=(int)filesize()/2;
        Registro reg = new Registro();
        while(k<tl){
            seekArq(k);
            reg.leDoArq(arquivo);
            v1[i++] = reg.getNumero();
            seekArq(k+tl);
            reg.leDoArq(arquivo);
            v2[j++] = reg.getNumero();
            k++;
        }
    }

    public void fusao(int[] v1, int[] v2, int seq){
        int i = 0, j = 0, k = 0, tl=(int)filesize()/2, aux = seq;
        Registro reg = new Registro();
        while(k<tl){
            while(i<seq && j<seq){
                if(v1[i]<v2[j]){
                    seekArq(k++);
                    reg.setNumero(v1[i++]);
                    reg.gravaNoArq(arquivo);
                }
                else{
                    seekArq(k++);
                    reg.setNumero(v2[j++]);
                    reg.gravaNoArq(arquivo);
                }
            }
            while(i<seq){
                seekArq(k++);
                reg.setNumero(v1[i++]);
                reg.gravaNoArq(arquivo);
            }
            while(j<seq){
                seekArq(k++);
                reg.setNumero(v2[j++]);
                reg.gravaNoArq(arquivo);
            }
            seq= seq + aux;
        }
    }

    //#endregion

    //#region Merge Sort (Second Implementation - Tree)
    public void mergeSortSegundaImplement() throws IOException{
        Arquivo aux = new Arquivo();
        mergeSegundaImplement(0,(int)this.filesize()-1,aux);
    } /*merge sort qualquer multiplicidade*/
    private void mergeSegundaImplement(int esq, int dir, Arquivo aux) throws IOException{
        comp++;
        if(esq<dir){
            comp++;
            int meio = (esq+dir)/2;
            mergeSegundaImplement(esq, meio, aux);
            mergeSegundaImplement(meio+1, dir, aux);
            fusaoSegundaImplement(esq, meio, meio+1, dir, aux);
        }
    }
    private void fusaoSegundaImplement(int ini1, int fim1, int ini2, int fim2, Arquivo arqAux) throws IOException {
        int i = ini1, j = ini2;
        Registro regi = new Registro(), regj = new Registro();
        arqAux.truncate(0);

        comp++;
        while (i <= fim1 && j <= fim2) {
            comp++;
            this.seekArq(i);
            regi.leDoArq(this.arquivo);
            this.seekArq(j);
            regj.leDoArq(this.arquivo);
            comp++;
            if (regi.getNumero() < regj.getNumero()) {
                arqAux.insereNoFinal(regi);
                i++;
            } else {
                arqAux.insereNoFinal(regj);
                j++;
            }
            mov++;
        }
        comp++;
        while (j <= fim2) {
            comp++;
            this.seekArq(j);
            regj.leDoArq(this.arquivo);
            arqAux.insereNoFinal(regj);
            mov++;
            j++;
        }
        comp++;
        while (i <= fim1) {
            comp++;
            this.seekArq(i);
            regi.leDoArq(this.arquivo);
            arqAux.insereNoFinal(regi);
            mov++;
            i++;
        }

        arqAux.seekArq(0);
        comp++;
        for (int pos = ini1; pos <= fim2; pos++) {
            comp++;
            regi.leDoArq(arqAux.getArquivo());
            this.seekArq(pos);
            regi.gravaNoArq(this.arquivo);
            mov++;
        }
    }
    //#endregion

    //#region CountSort
    public void countSort() throws IOException{
        int maior=Integer.MIN_VALUE, i;
        Arquivo arquivoCont = new Arquivo("Contador");
        Arquivo arquivoFinal = new Arquivo("Final");
        Registro regi = new Registro(), regj = new Registro();

        // achar o maior elemento do meu arquivo
        seekArq(0); regi.leDoArq(this.arquivo);
        comp++;
        while(!eof()){
            comp++;
            comp++;
            if(regi.getNumero()>maior){
                maior = regi.getNumero();
            }
            regi.leDoArq(this.arquivo);
        }
        comp++;
        if(regi.getNumero()>maior){
            maior = regi.getNumero();
        }

        // contar as ocorrências nas respectivas posicoes
        arquivoCont.truncate(0);
        arquivoCont.gerarArquivoVazio(maior+1);
        this.seekArq(0); regi.leDoArq(this.arquivo);
        comp++;
        while(!eof()){
            comp++;
            arquivoCont.seekArq(regi.getNumero()); regj.leDoArq(arquivoCont.getArquivo());
            regj.setNumero(regj.getNumero()+1); // incremento
            arquivoCont.seekArq(regi.getNumero()); regj.gravaNoArq(arquivoCont.getArquivo());
            mov++;
            regi.leDoArq(this.arquivo);
        }
        arquivoCont.seekArq(regi.getNumero()); regj.leDoArq(arquivoCont.getArquivo());
        regj.setNumero(regj.getNumero()+1); // incremento
        arquivoCont.seekArq(regi.getNumero()); regj.gravaNoArq(arquivoCont.getArquivo());
        mov++;

        // realizar a soma cumulativa
        i=1;
        arquivoCont.seekArq(i); regi.leDoArq(arquivoCont.getArquivo());
        comp++;
        while(!arquivoCont.eof()){
            comp++;
            arquivoCont.seekArq(i); regi.leDoArq(arquivoCont.getArquivo());
            arquivoCont.seekArq(i-1); regj.leDoArq(arquivoCont.getArquivo());
            regi.setNumero(regi.getNumero()+regj.getNumero());
            arquivoCont.seekArq(i); regi.gravaNoArq(arquivoCont.getArquivo());
            mov++;
            i++;
            arquivoCont.seekArq(i); regi.leDoArq(arquivoCont.getArquivo());
        }
        arquivoCont.seekArq(i); regi.leDoArq(arquivoCont.getArquivo());
        arquivoCont.seekArq(i-1); regj.leDoArq(arquivoCont.getArquivo());
        regi.setNumero(regi.getNumero()+regj.getNumero());
        arquivoCont.seekArq(i); regi.gravaNoArq(arquivoCont.getArquivo());
        mov++;

        // colocar os elementos ordenados no arquivo final
        arquivoFinal.gerarArquivoVazio((int)filesize());
        comp++;
        for(i=(int)filesize()-1; i>=0; i--){
            comp++;
            int pos;
            // vetor[i]
            seekArq(i); regi.leDoArq(this.arquivo);
            pos = regi.getNumero();
            // countVet.vetor[pos] - 1
            arquivoCont.seekArq(pos); regi.leDoArq(arquivoCont.getArquivo());
            pos = regi.getNumero()-1;
            // finalVet[pos] = vetor[i]
            seekArq(i); regj.leDoArq(this.arquivo);
            arquivoFinal.seekArq(pos); regj.gravaNoArq(arquivoFinal.getArquivo());
            mov++;
            // countVet.vetor[vetor[i]]--
            seekArq(i); regi.leDoArq(this.arquivo);
            pos = regi.getNumero();
            arquivoCont.seekArq(pos); regi.leDoArq(arquivoCont.getArquivo());
            regi.setNumero(regi.getNumero()-1);
            arquivoCont.seekArq(pos); regi.gravaNoArq(arquivoCont.getArquivo());
            mov++;
        }

        // copiar os dados ordenados para o arquivo original
        arquivoFinal.seekArq(0); regi.leDoArq(arquivoFinal.getArquivo());
        this.seekArq(0); regi.gravaNoArq(this.arquivo);
        mov++;
        comp++;
        while(!arquivoFinal.eof()){
            comp++;
            regi.leDoArq(arquivoFinal.getArquivo());
            regi.gravaNoArq(this.arquivo);
            mov++;
        }
    }
    //#endregion

    //#region RadixSort
    public void radixSort() throws IOException{
        int d=0, tl = (int)this.filesize();
        Registro registro = new Registro();

        //obter a quantidade máxima de digítos possíveis
        comp++;
        for(int i=0; i<tl; i++){
            comp++;
            this.seekArq(i); registro.leDoArq(this.arquivo);
            comp++;
            if(contaDigitos(registro.getNumero()) > d)
                d = contaDigitos(registro.getNumero());
        }
        comp++;
        for(int i=1; i<=d; i++) {
            comp++;
            countSort(i); //chamar o count pelos digítos
        }
    }
    private void countSort(int d) throws IOException {
        int maior=Integer.MIN_VALUE, i;
        Arquivo arquivoCont = new Arquivo("Contador");
        Arquivo arquivoFinal = new Arquivo("Final");
        Registro regi = new Registro(), regj = new Registro();

        // maior elemento do meu arquivo
        seekArq(0); regi.leDoArq(this.arquivo);
        comp++;
        while(!eof()){
            comp++;
            comp++;
            if(obterDigito(regi.getNumero(), d) > maior)
                maior = regi.getNumero();
            regi.leDoArq(this.arquivo);
        }
        comp++;
        if(obterDigito(regi.getNumero(), d) > maior)
            maior = regi.getNumero();

        // contar as ocorrências dos números
        arquivoCont.truncate(0);
        arquivoCont.gerarArquivoVazio(maior+1);
        this.seekArq(0); regi.leDoArq(this.arquivo);
        comp++;
        while(!eof()){
            comp++;
            arquivoCont.seekArq(obterDigito(regi.getNumero(), d)); regj.leDoArq(arquivoCont.getArquivo());
            regj.setNumero(regj.getNumero()+1); // incremento
            arquivoCont.seekArq(obterDigito(regi.getNumero(), d)); regj.gravaNoArq(arquivoCont.getArquivo());
            mov++;
            regi.leDoArq(this.arquivo);
        }
        arquivoCont.seekArq(obterDigito(regi.getNumero(),d)); regj.leDoArq(arquivoCont.getArquivo());
        regj.setNumero(regj.getNumero()+1); // incremento
        arquivoCont.seekArq(obterDigito(regi.getNumero(),d)); regj.gravaNoArq(arquivoCont.getArquivo());
        mov++;

        // realizar a soma comulativa
        i=1;
        arquivoCont.seekArq(0);
        comp++;
        while(!arquivoCont.eof()){
            comp++;
            arquivoCont.seekArq(i); regi.leDoArq(arquivoCont.getArquivo());
            arquivoCont.seekArq(i-1); regj.leDoArq(arquivoCont.getArquivo());
            regi.setNumero(regi.getNumero()+regj.getNumero());
            arquivoCont.seekArq(i); regi.gravaNoArq(arquivoCont.getArquivo());
            mov++;
            i++;
            arquivoCont.seekArq(i); regi.leDoArq(arquivoCont.getArquivo());
        }
        arquivoCont.seekArq(i); regi.leDoArq(arquivoCont.getArquivo());
        arquivoCont.seekArq(i-1); regj.leDoArq(arquivoCont.getArquivo());
        regi.setNumero(regi.getNumero()+regj.getNumero());
        arquivoCont.seekArq(i); regi.gravaNoArq(arquivoCont.getArquivo());
        mov++;

        // colocar os elementos ordenados no arquivo final
        arquivoFinal.gerarArquivoVazio((int)filesize());
        comp++;
        for(i=(int)filesize()-1; i>=0; i--){
            comp++;
            int pos;
            // vetor[i]
            seekArq(i); regi.leDoArq(this.arquivo);
            pos = obterDigito(regi.getNumero(),d);
            // countVet.vetor[pos] - 1
            arquivoCont.seekArq(pos); regi.leDoArq(arquivoCont.getArquivo());
            pos = regi.getNumero()-1;
            // finalVet[pos] = vetor[i]
            seekArq(i); regj.leDoArq(this.arquivo);
            arquivoFinal.seekArq(pos); regj.gravaNoArq(arquivoFinal.getArquivo());
            mov++;
            // countVet.vetor[vetor[i]]--
            seekArq(i); regi.leDoArq(this.arquivo);
            pos = obterDigito(regi.getNumero(),d);
            arquivoCont.seekArq(pos); regi.leDoArq(arquivoCont.getArquivo());
            regi.setNumero(regi.getNumero()-1);
            arquivoCont.seekArq(pos); regi.gravaNoArq(arquivoCont.getArquivo());
            mov++;
        }

        // copiei os dados ordenados para o arquivo original
        arquivoFinal.seekArq(0); regi.leDoArq(arquivoFinal.getArquivo());
        this.seekArq(0); regi.gravaNoArq(this.arquivo);
        mov++;
        comp++;
        while(!arquivoFinal.eof()){
            comp++;
            regi.leDoArq(arquivoFinal.getArquivo());
            regi.gravaNoArq(this.arquivo);
            mov++;
        }
    } /*count para auxiliar o radix sort*/
    //#endregion

    //#region BucketSort
    public void bucketSort() throws IOException{
        int max=Integer.MIN_VALUE, min=Integer.MAX_VALUE, range, baldes=5, pos, k, tl=(int)filesize();
        Arquivo[] arquivos = new Arquivo[baldes];
        Registro registro = new Registro();

        //achei o maior e o menor elementos do meu arquivo
        comp++;
        for (int i = 0; i < tl; i++) {
            comp++;
            this.seekArq(i); registro.leDoArq(this.arquivo);
            comp++;
            if(registro.getNumero()>max)
                max = registro.getNumero();
            comp++;
            if(registro.getNumero()<min)
                min = registro.getNumero();
        }

        range = (max-min+1)/5;

        // criar os buckets
        comp++;
        for (int i = 0; i < baldes; i++) {
            comp++;
            String nome = "Arq" + i;
            arquivos[i] = new Arquivo(nome);
            arquivos[i].truncate(0);
        }

        //distribuir os elementos entre os baldes
        comp++;
        for (int i = 0; i < tl; i++) {
            comp++;
            comp++;
            if(range==0){
                //leio o elemento do arquivo original na pos i
                this.seekArq(i); registro.leDoArq(this.arquivo);
                //gravo ao final do bucket correto
                arquivos[0].seekArq((int)arquivos[0].filesize());
                registro.gravaNoArq(arquivos[0].getArquivo());
                mov++;
            }
            else{
                this.seekArq(i); registro.leDoArq(this.arquivo);
                pos = (registro.getNumero()-min)/range;
                comp++;
                if(pos >= baldes)
                    pos = baldes - 1;
                arquivos[pos].seekArq((int)arquivos[pos].filesize());
                registro.gravaNoArq(arquivos[pos].getArquivo());
                mov++;
            }
        }

        // ordenar od baldes
        comp++;
        for (int i = 0; i < arquivos.length; i++) {
            comp++;
            comp++;
            if((int)arquivos[i].filesize()!=0)
                arquivos[i].insercaoDireta();
        }

        // aqui preciso colocar os elementos ordenados dos baldes de volta no vetor
        truncate(0);
        k = 0;
        comp++;
        for (int i = 0; i < baldes; i++) {
            comp++;
            comp++;
            for (int j = 0; j < (int)arquivos[i].filesize(); j++) {
                comp++;
                arquivos[i].seekArq(j); registro.leDoArq(arquivos[i].getArquivo());
                this.seekArq(k); registro.gravaNoArq(this.arquivo);
                mov++;
                k++;
                //this.vetor[k++] = buckets[i].getVetor()[j];
            }
        }
    }
    //#endregion

    //#region GnomeSort
    public void gnomeSort() throws IOException{
        int pos=1;
        Registro regi = new Registro(), regj = new Registro();

        comp++;
        while(pos<(int)this.filesize()){
            comp++;
            comp++;
            if(pos==0)
                pos++;
            else{
                this.seekArq(pos); regi.leDoArq(this.arquivo);
                this.seekArq(pos-1); regj.leDoArq(this.arquivo);
                comp++;
                if(regi.getNumero()>=regj.getNumero())
                    pos++;
                else{
                    this.seekArq(pos); regj.gravaNoArq(this.arquivo);
                    mov++;
                    this.seekArq(pos-1); regi.gravaNoArq(this.arquivo);
                    mov++;
                    pos--;
                }
            }
        }
    }
    //#endregion

    //#region TIM SORT
    public void timSort() throws IOException {
        int n, min_range, end, size, mid, right, min_run;
        n = (int)this.filesize(); //quant de registros
        min_range = 32; //quant mínima de range de ordenação
        min_run = calcMinRunTIM(n, min_range); //descobrir o novo range mínimo

        //realizar as ordenações por inserção direta
        for(int start=0; start<n; start+=min_run){
            if(start + min_run - 1 < n - 1)
                end = start + min_run - 1;
            else
                end = n - 1;

            insercaoDireta(start, end);
        }

        //realizar os merges
        size = min_run;
        while (size < n) {
            for (int left = 0; left < n; left += 2*size) {
                //achar o novo valor do meio que separa os dois sub vetores
                if(left + size - 1 < n - 1)
                    mid = left + size - 1;
                else
                    mid = n - 1;

                if(left + 2 * size - 1 < n - 1)
                    right = left + 2*size - 1;
                else
                    right = n - 1;

                // chamo o merge após achar o início, o meio e o fim
                if (mid < right) {
                    mergeTIM(left, mid, right);
                }
            }
            size *= 2;
        }
    } /*tim sort*/
    private void mergeTIM(int l, int m, int r) throws IOException {
        int len1 = m - l + 1, len2 = r - m;
        Arquivo left = new Arquivo("Esquerda");
        Arquivo right = new Arquivo("Direita");
        Registro regi = new Registro(), regj = new Registro();

        //inicializar os novos arquivos, apenas por segurança
        left.truncate(0);
        right.truncate(0);

        /*aqui é o particionamento do vetor em dois*/
        this.seekArq(l);
        for (int i = 0; i < len1; i++) {
            regi.leDoArq(this.arquivo);
            left.insereNoFinal(regi);
            //left[i] = this.vetor[l + i]; //começo a pegar os elementos a partir da posição "l"
        }
        this.seekArq(m+1);
        for (int i = 0; i < len2; i++) {
            regi.leDoArq(this.arquivo);
            right.insereNoFinal(regi);
            //right[i] = this.vetor[m + 1 + i]; //começo a pegar os elementos a partir da posição "m+1"
        }

        //colocar de volta ao arquivo original os elementos separados
        this.seekArq(l);
        left.seekArq(0); regi.leDoArq(left.getArquivo());
        right.seekArq(0); regj.leDoArq(right.getArquivo());
        int i = 0, j = 0, k=l;
        while (i < len1 && j < len2) {
            if (regi.getNumero() < regj.getNumero()) {
                this.seekArq(k++);
                regi.gravaNoArq(this.arquivo);
                i++;
                regi.leDoArq(left.getArquivo());
                //this.vetor[k++] = left[i++];
            } else {
                this.seekArq(k++);
                regj.gravaNoArq(this.arquivo);
                j++;
                regj.leDoArq(right.getArquivo());
                //this.vetor[k++] = right[j++];
            }
        }

        /*colocar no vetor original oq sobrou da primeira partição*/
        while (i < len1) {
            this.seekArq(k); regi.gravaNoArq(this.arquivo);
            k++;
            i++;
            regi.leDoArq(left.getArquivo());
            //this.vetor[k++] = left[i++];
        }
        /*colocar no vetor original oq sobrou da segunda partição*/
        while (j < len2) {
            this.seekArq(k); regj.gravaNoArq(this.arquivo);
            k++;
            j++;
            regj.leDoArq(right.getArquivo());
            //this.vetor[k++] = right[j++];
        }
    }
    //#endregion

    //#region CombSort
    public void combSort() throws IOException{
        int gap, TL=(int)this.filesize();
        gap = TL;
        boolean flag = true;
        Registro registro = new Registro(), registroAux = new Registro();

        comp++;
        while(gap>1 || flag){
            gap = (int)(gap/1.3);
            comp++;
            if(gap<1)
                gap = 1;
            flag = false;
            comp++;
            for (int i=0; i+gap<TL; i++) {
                comp++;
                this.seekArq(i); registro.leDoArq(this.arquivo);
                this.seekArq(i+gap); registroAux.leDoArq(this.arquivo);
                comp++;
                if(registro.getNumero() > registroAux.getNumero()){
                    this.seekArq(i); registroAux.gravaNoArq(this.arquivo);
                    mov++;
                    this.seekArq(i+gap); registro.gravaNoArq(this.arquivo);
                    mov++;
                    flag = true;
                }
            }
        }
    }
    //#endregion

    //#endregion
}