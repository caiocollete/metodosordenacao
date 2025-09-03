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
    public void copiaArquivo(RandomAccessFile arquivoOrigem){
        Registro registro = new Registro();
        try{
            while(arquivoOrigem.getFilePointer()<arquivoOrigem.length()){
                this.arquivo.seek(arquivoOrigem.getFilePointer());
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
    //demais metodos de ordenacao
    public void geraArquivoOrdenado() {
        Random rand = new Random();
        var num = rand.nextInt(300000)+2000;
        for(int count = 0; count < Registro.tf; count++) {
            Registro reg = new Registro(num);
            reg.gravaNoArq(arquivo);
            num++;
        }
    }
    public void geraArquivoReverso() {
        Random rand = new Random();
        var num = rand.nextInt(300000)+2000;
        for(int count = 0; count < Registro.tf; count++) {
            Registro reg = new Registro(num);
            reg.gravaNoArq(arquivo);
            num--;
        }
    }
    public void geraArquivoRandomico() {
        Random rand = new Random();
        while(!eof()){
            Registro reg = new Registro(rand.nextInt(300000)+2000);
            reg.gravaNoArq(arquivo);
        }
    }
    //#endregion

    //#region Metódos de Ordenação
    public void insercaoDireta() {
        Registro reg = new Registro();
            long n = Registro.tf / tamInt;
            for(int i = 1; i < n; i++){
                seekArq(i);
                reg.leDoArq(arquivo);
                int aux = reg.getNumero();

                int j = i-1;
                boolean cond = true;
                while(j>=0 && cond){
                    seekArq(j);
                    reg.leDoArq(arquivo);
                    int anterior = reg.getNumero();
                    if(aux<anterior){
                        seekArq(j+1);
                        reg.setNumero(anterior);
                        reg.gravaNoArq(arquivo);
                        addMov();
                    }
                    else
                        cond = false;
                    addComp();
                    j--;
                }
                seekArq(j+1);
                reg.setNumero(aux);
                reg.gravaNoArq(arquivo);
                addMov();
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
        int n = Registro.tf/tamInt, pos;
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

    public void shellSort() {
        int dist = 1, pos, aux;
        Registro reg = new Registro();
        Registro reg2 = new Registro();
        while(dist < filesize()) {
            dist = dist * 3 + 1;
        }
        dist = dist/3;

        while(dist > 0) {
            for(int i=dist; i<filesize(); i++){
                seekArq(i);
                reg.leDoArq(arquivo);
                aux = reg.getNumero();
                pos=i;

                while(pos >= dist){
                    seekArq(pos-dist);
                    reg2.leDoArq(arquivo);
                    if( aux<reg2.getNumero()) {
                        reg.setNumero(reg2.getNumero());
                        seekArq(pos);
                        reg.gravaNoArq(arquivo);

                        pos=pos-dist;
                        seekArq(pos);
                        reg2.leDoArq(arquivo);

                        addMov();
                        addComp();
                    }
                }
                addComp();

                seekArq(pos);
                reg.setNumero(aux);
                reg.gravaNoArq(arquivo);
                addMov();
            }
            dist=dist/3;
        }
    }
    //#endregion
}