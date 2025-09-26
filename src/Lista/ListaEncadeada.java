package Lista;

import java.util.Random;

public class ListaEncadeada {
    private No inicio;
    private No fim;

    public ListaEncadeada() {
        inicializar();
    }

    public void inicializar() {
        this.inicio = null;
        this.fim = null;
    }

    //métodos extras ------------------------------------------------------------------
    public int primeiroElemento() {
        return this.inicio.getInfo();
    }

    public int ultimoElemento() {
        return this.fim.getInfo();
    }

    private No andarFrente(No inicio, int n) {
        for (int i = 0; i < n; i++)
            inicio = inicio.getProx();

        return inicio;
    }

    private No andarAtras(No inicio, int n) {
        for (int i = 0; i < n; i++)
            inicio = inicio.getAnt();

        return inicio;
    }

    private int contaLista() {
        No aux = this.inicio;
        int cont = 0;
        while (aux != null) {
            aux = aux.getProx();
            cont++;
        }
        return cont;
    }

    public void criarElementosRand(int num) {
        inicializar();
        Random gerador = new Random();

        // Preenche a lista com 1..num
        for (int i = 0; i < num; i++) {
            addFinal(i + 1);
        }

        // Embaralhar com troca de valores (versão simplificada do Fisher–Yates)
        No atual = inicio;
        for (int i = 0; i < num; i++) {
            int pos = gerador.nextInt(num); // pega uma posição aleatória
            No escolhido = andarFrente(inicio, pos); // nó aleatório

            // troca os valores entre 'atual' e 'escolhido'
            int aux = escolhido.getInfo();
            escolhido.setInfo(atual.getInfo());
            atual.setInfo(aux);

            // anda para o próximo
            atual = atual.getProx();
        }
    }
/*criar elementos "aleatoriamente"*/

    public void printarValores() {
        No aux = this.inicio;
        try{
            System.out.print("-> ");
            while (aux != null) {
                System.out.print(aux.getInfo()+" ");
                aux = aux.getProx();
            }
            System.out.println();
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }/*printar os valores do vetor*/

    private int buscaBinaria(int chave, int tl) {
        int ini = 0, fim = tl - 1, meio = fim / 2;
        while (ini < fim && chave != andarFrente(this.inicio, meio).getInfo()) {
            if (chave > andarFrente(this.inicio, meio).getInfo()) {
                ini = meio + 1;
            } else {
                if (chave < andarFrente(this.inicio, meio).getInfo()) {
                    fim = meio - 1;
                }
            }
            meio = (ini + fim) / 2;
        }
        if (chave > andarFrente(this.inicio, meio).getInfo())
            return meio + 1;
        return meio;
    }

    private int contaIntervalo(No inicio, No fim) {
        int cont = 0;
        while(inicio!=fim){
            cont++;
            inicio = inicio.getProx();
        }
        return cont;
    }

    private int achaPos(No no){
        int cont = 0;
        No aux = this.inicio;

        while(aux!=no){
            cont++;
            aux = aux.getProx();
        }
        return cont;
    }

    private void criarListaZerada(int n) {
        this.fim = this.inicio = new No(0);
        for (int i = 0; i < n - 1; i++) {
            this.fim.setProx(new No(0));
            this.fim.getProx().setAnt(this.fim);
            this.fim = this.fim.getProx();
        }
    }

    private int obterDigito(int numero, int d) {
        int divisor = 1;

        // Calcula o divisor para alcançar a posição desejada
        for (int i = 1; i < d; i++) {
            divisor *= 10;
        }

        return (numero / divisor) % 10;
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

    private void addFinal(int valor) {
        No novoNo = new No(valor);

        // Caso a lista esteja vazia
        if (this.inicio == null) {
            this.inicio = this.fim = novoNo;
        } else {
            this.fim.setProx(novoNo); // O último nó atual aponta para o novo nó
            novoNo.setAnt(this.fim);  // O novo nó aponta de volta para o último nó atual
            this.fim = novoNo;        // Atualiza o fim da lista
        }
    }

    //ORDENAÇÕES ----------------------------------------------------------------------
    public void insercaoDireta(){
        insercaoDireta(inicio, fim);
    } /*insercao direta*/

    private void insercaoDireta(No left, No right) {
        int aux;
        No ppos, i = left.getProx();
        while (i != right.getProx()) {
            aux = i.getInfo();
            ppos = i;
            while (ppos != left && ppos.getAnt().getInfo() > aux) {
                ppos.setInfo(ppos.getAnt().getInfo());
                ppos = ppos.getAnt();
            }
            ppos.setInfo(aux);
            i = i.getProx();
        }
    }/*inserção direta*/

    public void bubbleSort() {
        int aux;
        No TL2 = this.fim, i;
        boolean flag = true;
        while (TL2.getAnt() != null && flag) {
            flag = false;
            i = this.inicio;
            while (i != this.fim) {
                if (i.getInfo() > i.getProx().getInfo()) {
                    aux = i.getInfo();
                    i.setInfo(i.getProx().getInfo());
                    i.getProx().setInfo(aux);
                    flag = true;
                }
                i = i.getProx();
            }
            TL2 = TL2.getAnt();
        }
    }/*bubbleSort*/

    public void shakesort() {
        int aux;
        No inicio = this.inicio, fim = this.fim, i;
        boolean flag = true;
        while (inicio != fim && flag) {
            flag = false;
            i = inicio;
            while (i != fim) {
                if (i.getInfo() > i.getProx().getInfo()) {
                    aux = i.getInfo();
                    i.setInfo(i.getProx().getInfo());
                    i.getProx().setInfo(aux);
                    flag = true;
                }
                i = i.getProx();
            }
            fim = fim.getAnt();
            if (flag) {
                flag = false;
                i = fim;
                while (i != inicio) {
                    if (i.getInfo() < i.getAnt().getInfo()) {
                        aux = i.getInfo();
                        i.setInfo(i.getAnt().getInfo());
                        i.getAnt().setInfo(aux);
                        flag = true;
                    }
                    i = i.getAnt();
                }
                inicio = inicio.getProx();
            }
        }
    }/*shakeSort*/

    public void selecaoDireta() {
        int aux;
        No pposMenor, i, j;
        i = this.inicio;
        while (i != this.fim) {
            pposMenor = i;
            j = i.getProx();
            while (j != null) {
                if (j.getInfo() < pposMenor.getInfo()) {
                    pposMenor = j;
                }
                j = j.getProx();
            } //while j
            aux = pposMenor.getInfo();
            pposMenor.setInfo(i.getInfo());
            i.setInfo(aux);

            i = i.getProx();
        } //while i
    }/*selecaoDireta*/

    public void combSort() {
        int gap, ant, aux, TL = this.contaLista();
        gap = (int) (TL / 1.3);
        ant = gap;

        while (gap > 0) {
            for (int i = 0; i + gap < TL; i++) {
                if (andarFrente(this.inicio, i).getInfo() > andarFrente(this.inicio, i + gap).getInfo()) {
                    aux = andarFrente(this.inicio, i).getInfo();
                    andarFrente(this.inicio, i).setInfo(andarFrente(this.inicio, i + gap).getInfo());
                    andarFrente(this.inicio, i + gap).setInfo(aux);
                }
            }
            gap = (int) (ant / 1.3);
            ant = gap;
        }
    }/*combSort*/

    public void shellSort() {
        int dist = 1, aux, pos, TL = contaLista();
        No auxPos;
        while (dist < TL)
            dist = dist * 2 + 1;
        dist /= 2;

        while (dist > 0) {
            for (int i = dist; i < TL; i++) {
                aux = andarFrente(this.inicio, i).getInfo();
                pos = i;
                while (pos >= dist && aux < andarFrente(this.inicio, pos - dist).getInfo()) {
                    auxPos = andarFrente(this.inicio, pos);
                    auxPos.setInfo(andarAtras(auxPos, dist).getInfo());
                    pos -= dist;
                }
                andarFrente(this.inicio, pos).setInfo(aux);
            }
            dist /= 2;
        }
    }/*shellSort*/

    public void insercaoBinaria() {
        int pos, aux, TL = contaLista();
        No auxJ;
        for (int i = 1; i < TL; i++) {
            aux = andarFrente(this.inicio, i).getInfo();
            pos = buscaBinaria(aux, i);
            for (int j = i; j > pos; j--) {
                auxJ = andarFrente(this.inicio, j);
                auxJ.setInfo(andarAtras(auxJ, 1).getInfo());
            }
            andarFrente(this.inicio, pos).setInfo(aux);
        }
    }/*insercaoBinaria*/

    public void heapSort() {
        int tl=contaLista(), fe, fd, fMaior, aux;

        while(tl>1){
            for(int pai=tl/2-1; pai>=0; pai--){
                fe = pai*2+1;
                fd = fe+1;
                fMaior = fe;
                if(fd<tl && andarFrente(this.inicio, fd).getInfo() > andarFrente(this.inicio, fe).getInfo())
                    fMaior = fd;
                if(andarFrente(this.inicio, fMaior).getInfo() > andarFrente(this.inicio, pai).getInfo()){
                    aux = andarFrente(this.inicio, fMaior).getInfo();
                    andarFrente(this.inicio, fMaior).setInfo(andarFrente(this.inicio, pai).getInfo());
                    andarFrente(this.inicio, pai).setInfo(aux);
                }
            }
            aux = this.inicio.getInfo();
            this.inicio.setInfo(andarFrente(this.inicio, tl-1).getInfo());
            andarFrente(this.inicio, tl-1).setInfo(aux);
            tl--;
        }
    }/*heapSort*/

    public void quickSortSemPivo() {
        quickSORTSemPivo(this.inicio, this.fim);
    }/*quick sort sem pivo*/
    public void quickSORTSemPivo(No ini, No fim) {
        if (ini != null && fim != null && ini != fim) {
            int aux;
            No noI = ini, noJ = fim;
            boolean flag = true;
            while (noI != noJ) {
                if (flag)
                    while (noI != noJ && noI.getInfo() <= noJ.getInfo())
                        noI = noI.getProx();
                else
                    while (noI != noJ && noI.getInfo() <= noJ.getInfo())
                        noJ = noJ.getAnt();
                if (noI != noJ) {
                    aux = noI.getInfo();
                    noI.setInfo(noJ.getInfo());
                    noJ.setInfo(aux);
                    flag = !flag;
                }
            }
            if (noI!=ini && ini != noI.getAnt())
                quickSORTSemPivo(ini, noI.getAnt());
            if (noJ!=fim && noJ.getProx() != fim)
                quickSORTSemPivo(noJ.getProx(), fim);
        }
    }/*quick sort de verdade sem pivo*/

    public void quickSortComPivo(){
        quickSORTComPivo(this.inicio, this.fim);
    } /*quick sort com pivo*/
    private void quickSORTComPivo(No ini, No fim) {
        int aux, pivo, posi = achaPos(ini), posj = achaPos(fim);
        No i=ini, j=fim;

        if(i!=null && j!=null && ini!=fim && fim.getProx()!=ini) {
            pivo = andarFrente(ini, contaIntervalo(ini, fim) / 2).getInfo();

            while(posi<posj){
                while(i.getInfo()<pivo){
                    posi++;
                    i = i.getProx();
                }
                while(j.getInfo()>pivo){
                    posj--;
                    j = j.getAnt();
                }
                if(posi<=posj){
                    aux = i.getInfo();
                    i.setInfo(j.getInfo());
                    j.setInfo(aux);
                    posi++;
                    posj--;
                    i = i.getProx();
                    j = j.getAnt();
                }
            }
            if(ini!=j || j.getProx()!=ini)
                quickSORTComPivo(ini, j);
            if(i!=fim || fim.getProx()!=i)
                quickSORTComPivo(i, fim);
        }
    } /*quick sort de verdade com pivo*/

    public void countSort(){
        int maior=Integer.MIN_VALUE;
        No aux = this.inicio, auxFinal, auxCount;

        //achar o maior elemento da minha lista
        while(aux!=null){
            if(aux.getInfo()>maior)
                maior = aux.getInfo();
            aux = aux.getProx();
        }

        //criar uma outra lista e contar as ocorrencias dos numeros
        ListaEncadeada count = new ListaEncadeada();
        count.criarListaZerada(maior+1);
        aux = this.inicio;
        while(aux!=null){
            auxCount = count.andarFrente(count.inicio,aux.getInfo());
            auxCount.setInfo(auxCount.getInfo()+1);

            aux = aux.getProx();
        }

        //realizar a soma cumulativa
        auxCount = count.inicio.getProx();
        while(auxCount!=null){
            auxCount.setInfo(auxCount.getInfo()+auxCount.getAnt().getInfo());
            auxCount = auxCount.getProx();
        }

        //colocar os elementos na listaFinal
        ListaEncadeada finalList = new ListaEncadeada();
        finalList.criarListaZerada(contaLista());
        aux = this.fim;
        while(aux!=null){
            auxFinal = andarFrente(count.inicio, aux.getInfo());
            auxFinal = andarFrente(finalList.inicio, auxFinal.getInfo()-1);
            auxFinal.setInfo(aux.getInfo());

            auxCount = andarFrente(count.inicio, aux.getInfo());
            auxCount.setInfo(auxCount.getInfo()-1);

            aux = aux.getAnt();
        }

        //coloco os elementos ordenados no vetor original
        auxFinal = finalList.inicio;
        aux = this.inicio;
        while(auxFinal!=null){
            aux.setInfo(auxFinal.getInfo());
            auxFinal = auxFinal.getProx();
            aux = aux.getProx();
        }
    } /*countSort*/

    public void radixSort(){
        int d=0;
        No aux = this.inicio;

        //achar a quantidade máxima de digitos
        while(aux!=null){
            if(contaDigitos(aux.getInfo())>d)
                d = contaDigitos(aux.getInfo());
            aux = aux.getProx();
        }

        for(int i=1; i<=d; i++)
            countSortRad(i);
    } /*radixSort*/
    private void countSortRad(int d){
        int maior=Integer.MIN_VALUE;
        No aux = this.inicio, auxFinal, auxCount;

        //achar o maior elemento da minha lista
        while(aux!=null){
            if(obterDigito(aux.getInfo(), d) > maior)
                maior = aux.getInfo();
            aux = aux.getProx();
        }

        //criar uma outra lista e contar as ocorrencias dos numeros
        ListaEncadeada count = new ListaEncadeada();
        count.criarListaZerada(maior+1);
        aux = this.inicio;
        while(aux!=null){
            auxCount = count.andarFrente(count.inicio,obterDigito(aux.getInfo(),d));
            auxCount.setInfo(auxCount.getInfo()+1);

            aux = aux.getProx();
        }

        //realizar a soma cumulativa
        auxCount = count.inicio.getProx();
        while(auxCount!=null){
            auxCount.setInfo(auxCount.getInfo()+auxCount.getAnt().getInfo());
            auxCount = auxCount.getProx();
        }

        //colocar os elementos na listaFinal
        ListaEncadeada finalList = new ListaEncadeada();
        finalList.criarListaZerada(contaLista());
        aux = this.fim;
        while(aux!=null){
            auxFinal = andarFrente(count.inicio, obterDigito(aux.getInfo(),d));
            auxFinal = andarFrente(finalList.inicio, auxFinal.getInfo()-1);
            auxFinal.setInfo(aux.getInfo());

            auxCount = andarFrente(count.inicio, obterDigito(aux.getInfo(),d));
            auxCount.setInfo(auxCount.getInfo()-1);

            aux = aux.getAnt();
        }

        //coloco os elementos ordenados no vetor original
        auxFinal = finalList.inicio;
        aux = this.inicio;
        while(auxFinal!=null){
            aux.setInfo(auxFinal.getInfo());
            auxFinal = auxFinal.getProx();
            aux = aux.getProx();
        }
    } /*countSort auxiliar do RADIX*/

    public void bucketSort(){
        int max = Integer.MIN_VALUE, min = Integer.MAX_VALUE, range, baldes=5, pos;
        ListaEncadeada[] buckets = new ListaEncadeada[baldes];
        No aux = this.inicio, auxBuckets;

        //identificar o maior e o menor elemento da minha lista
        while(aux!=null){
            if(aux.getInfo()>max)
                max = aux.getInfo();
            if(aux.getInfo()<min)
                min = aux.getInfo();
            aux = aux.getProx();
        }

        range = (max-min)/2;

        //criar os buckets
        for(int i=0; i<baldes; i++){
            buckets[i] = new ListaEncadeada();
        }

        //separar os elementos em seus respectivos baldes
        aux = this.inicio;
        while(aux!=null){
            if(range==0){
                buckets[0].addFinal(aux.getInfo());
            }
            else{
                pos = (aux.getInfo()-min)/range;
                if(pos==baldes)
                    pos--;
                buckets[pos].addFinal(aux.getInfo());
            }
            aux = aux.getProx();
        }

        //ordenar os buckets
        for(int i=0; i<buckets.length; i++){
            if(buckets[i].inicio!=null)
                buckets[i].insercaoDireta();
        }

        //realocar os elementos dos baldes para a lista original
        aux = this.inicio;
        for(int i=0; i< buckets.length; i++){
            auxBuckets = buckets[i].inicio;
            while(auxBuckets!=null){
                aux.setInfo(auxBuckets.getInfo());
                auxBuckets = auxBuckets.getProx();
                aux = aux.getProx();
            }
        }
    } /*bucketSort*/

    public void gnomeSort(){
        No aux = this.inicio.getProx();
        int auxNum;

        while(aux!=null){
            if(aux==this.inicio || aux.getInfo()>=aux.getAnt().getInfo())
                aux = aux.getProx();
            else{
                auxNum = aux.getInfo();
                aux.setInfo(aux.getAnt().getInfo());
                aux.getAnt().setInfo(auxNum);
                aux = aux.getAnt();
            }
        }
    } /*gnomeSort*/

    public void mergeSortPri(){
        ListaEncadeada list1 = new ListaEncadeada();
        ListaEncadeada list2 = new ListaEncadeada();
        int seq = 1, tl = contaLista();

        while(seq<tl){
            particaoMergePri(list1, list2);
            fusaoMergePri(list1, list2, seq);
            seq *= 2;
        }
    } /*mergeSort primeira implementação*/
    private void particaoMergePri(ListaEncadeada list1, ListaEncadeada list2){
        int tl = contaLista();
        No aux;

        list1.inicializar();
        list2.inicializar();
        aux = this.inicio;
        for(int i=0; i<(tl+1)/2; i++){
            list1.addFinal(aux.getInfo());
            aux = aux.getProx();
        }
        while(aux!=null){
            list2.addFinal(aux.getInfo());
            aux = aux.getProx();
        }
    }
    private void fusaoMergePri(ListaEncadeada list1, ListaEncadeada list2, int seq){
        No auxi=list1.inicio, auxy=list2.inicio, auxk=this.inicio;
        int aux_seq = seq, i=0, j=0;

        while(auxi!=null && auxy!=null){
            while(i<seq && j<seq){
                if(auxi.getInfo() < auxy.getInfo()){
                    auxk.setInfo(auxi.getInfo());
                    auxk = auxk.getProx(); //PRÓXIMO DA LISTA PRINCIPAL
                    auxi = auxi.getProx(); //PRÓXIMO DA LISTA 1
                    i++;
                }
                else{
                    auxk.setInfo(auxy.getInfo());
                    auxk = auxk.getProx(); //PRÓXIMO DA LISTA PRINCIPAL
                    auxy = auxy.getProx(); //PRÓXIMO DA LISTA 2
                    j++;
                }
            }
            while(i<seq){
                auxk.setInfo(auxi.getInfo());
                auxk = auxk.getProx(); //PRÓXIMO DA LISTA PRINCIPAL
                auxi = auxi.getProx(); //PRÓXIMO DA LISTA 1
                i++;
            }
            while(j<seq){
                auxk.setInfo(auxy.getInfo());
                auxk = auxk.getProx(); //PRÓXIMO DA LISTA PRINCIPAL
                auxy = auxy.getProx(); //PRÓXIMO DA LISTA 2
                j++;
            }

            seq += aux_seq;
        }
    }

    public void mergeSortSeg(){
        ListaEncadeada auxLista = new ListaEncadeada();
        mergeSORTseg(this.inicio, this.fim, auxLista);
    } /*mergeSort segunda implementação*/
    private void mergeSORTseg(No esq, No dir, ListaEncadeada auxLista){
        if(esq!=dir){
            int quant = contaIntervalo(esq,dir);
            No meio = andarFrente(esq,(quant/2));
            mergeSORTseg(esq, meio, auxLista);
            mergeSORTseg(meio.getProx(), dir, auxLista);
            fusaoMergeSeg(esq, meio, meio.getProx(), dir, auxLista);
        }
    }
    private void fusaoMergeSeg(No ini1, No fim1, No ini2, No fim2, ListaEncadeada auxLista){
        No k, i=ini1, j=ini2;
        auxLista.inicializar();

        while(i!=fim1.getProx() && j!=fim2.getProx()){
            if(i.getInfo() < j.getInfo()){
                auxLista.addFinal(i.getInfo());
                i = i.getProx();
            }
            else{
                auxLista.addFinal(j.getInfo());
                j = j.getProx();
            }
        }

        //tratando os elementos que sobraram na lista2
        while(j!=fim2.getProx()){
            auxLista.addFinal(j.getInfo());
            j = j.getProx();
        }
//        if(j==fim2){
//            k.setInfo(j.getInfo());
//            k = k.getProx();
//        }

        //tratando os elementos que sobraram na lista1
        while(i!=fim1.getProx()){
            auxLista.addFinal(i.getInfo());
            i = i.getProx();
        }
//        if(i==fim1){
//            k.setInfo(j.getInfo());
//            k = k.getProx();
//        }

        //colocando os elementos de volta para a lista original
        k = auxLista.inicio;
        i = ini1;
        while(i!=fim2.getProx()){
            i.setInfo(k.getInfo());
            k = k.getProx();
            i = i.getProx();
        }
    }

    public void timSort(){
        int TL = contaLista(), min_merge = 32;
        int corridaMinima = corridaMinima(TL, min_merge);

        /*
         * Nesse for, de acordo com o valor da corrida minima
         * que foi calculada, eu ordeno partes da lista com o
         * insercao direta
         * */
        for(int comeco=0; comeco<TL; comeco+=corridaMinima){
            int fim;
            if(comeco+corridaMinima-1 < TL-1) {
                //se NÃO ultrapassar os limites da minha lista
                fim = comeco+corridaMinima-1;
            }
            else {
                //se ultrapassar os limites da minha lista
                fim = TL-1;
            }
            insercaoDireta(andarFrente(inicio,comeco),andarFrente(inicio,fim));
        }

        int tamanho = corridaMinima, dir, meio;
        while(tamanho<TL){
            for(int esq=0; esq<TL; esq += 2*tamanho){
                if(esq+tamanho-1 < TL-1)
                    meio = esq+tamanho-1;
                else
                    meio = TL-1;

                if(esq+2*tamanho-1 < TL-1)
                    dir = esq+2*tamanho-1;
                else
                    dir = TL-1;

                /*
                 * Chamo o merge se o meio não passar do
                 * limite da direita
                 * */
                if(meio < dir){
                    mergeTIM(esq, meio, dir);
                }
            }
            tamanho *= 2;
        }
    } /*timSort*/
    private void mergeTIM(int esq, int meio, int dir){
        int TL1 = meio-esq+1;
        int TL2 = dir-meio;
        No auxLista;

        ListaEncadeada esquerda = new ListaEncadeada();
        ListaEncadeada direita = new ListaEncadeada();

        //particionamento da lista em duas partes
        auxLista = andarFrente(inicio,esq);
        for (int i=0; i<TL1; i++) {
            esquerda.addFinal(auxLista.getInfo());
            auxLista = auxLista.getProx();
        }
        auxLista = andarFrente(inicio, meio+1);
        for (int i=0; i<TL2; i++) {
            direita.addFinal(auxLista.getInfo());
            auxLista = auxLista.getProx();
        }

        No auxEsq = esquerda.inicio, auxDir = direita.inicio;
        auxLista = andarFrente(inicio, esq);
        int i=0, j=0;
        while(i<TL1 && j<TL2){
            if(auxEsq.getInfo() <= auxDir.getInfo()){
                auxLista.setInfo(auxEsq.getInfo());
                auxEsq = auxEsq.getProx();
                i++;
            }
            else {
                auxLista.setInfo(auxDir.getInfo());
                auxDir = auxDir.getProx();
                j++;
            }
            auxLista = auxLista.getProx();
        }

        //tratar os elementos que sobraram nas listas
        while(i<TL1){
            auxLista.setInfo(auxEsq.getInfo());
            auxEsq = auxEsq.getProx();
            auxLista = auxLista.getProx();
            i++;
        }
        while(j<TL2){
            auxLista.setInfo(auxDir.getInfo());
            auxDir = auxDir.getProx();
            auxLista = auxLista.getProx();
            j++;
        }
    }
    private int corridaMinima(int n, int min_merge) {
        int r = 0;
        while (n >= min_merge) {
            r = r + (n % 2);
            n = n/2;
        }
        return n + r;
    }
}