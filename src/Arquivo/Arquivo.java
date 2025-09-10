import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Random;
import java.util.RandomAccess;
import java.util.random.RandomGenerator;

/*
*  DEVEMOS GRAVAR O REGISTRO
*  E NAO ARQUIVO.WRITEINT
* */

public class Arquivo{
    private static int tamInt = 4;
    private String nomearquivo;
    private RandomAccessFile arquivo;
    private int comp, mov;
    public Arquivo(String nomearquivo) {
        this.nomearquivo = nomearquivo;
    }
    public void copiaArquivo(RandomAccessFile arquivoOrigem){
        arquivo = arquivoOrigem;
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

    public void insercaoDireta() {
        Registro reg = new Registro();
            long n = Registro.tf / tamInt;
            for(int i = 1; i < n; i++){
                seekArq(i);
                reg.leDoArq(arquivo);
                int aux = reg.getNumero();

                int j = i-1;
                boolean cond = true;
                while(j>=0){
                    seekArq(j);
                    reg.leDoArq(arquivo);
                    int anterior = reg.getNumero();
                    if(aux<anterior){
                        seekArq(j+1);
                        reg.setNumero(anterior);
                        reg.gravaNoArq(arquivo);
                    }
                    else
                        cond = false;
                    j--;
                }
                seekArq(j+1);
                reg.setNumero(aux);
                reg.gravaNoArq(arquivo);
            }
    }

    public int buscaBinaria(int target, int tl){
        int inicio = 0, fim = tl, meio=fim/2;
        Registro reg = new Registro();
        seekArq(fim-1);
        reg.leDoArq(arquivo);
        if(target < reg.getNumero()){
            seekArq(meio);
            while(inicio<fim){
                reg.leDoArq(arquivo);
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
            }
            seekArq(pos);
            reg.setNumero(aux);
            reg.gravaNoArq(arquivo);
        }
    }
}