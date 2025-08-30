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
            long n = Registro.tf / tamInt;
            for(int i = 1; i < n; i++){
                seekArq(i);
                int aux = re;

                long j = i-1;
                boolean cond = true;
                while(j>=0){
                    arquivo.seek(j*tamInt);
                    int anterior = arquivo.readInt();
                    if(aux<anterior){
                        arquivo.seek((j+1)*tamInt);
                        arquivo.writeInt(anterior);
                    }
                    else
                        cond = false;
                    j--;
                }

                arquivo.seek((j+1)*tamInt);
                arquivo.writeInt(aux);
            }
    }

    public long buscaBinaria(int target, long tl){
        long inicio = 0, fim = tl, meio=fim/2;
        try{
            arquivo.seek((fim-1)*tamInt);
            if(target < arquivo.readInt()){
                arquivo.seek(meio*tamInt);
                while(inicio<fim){
                    if(arquivo.readInt()>target){
                        fim = meio-1;
                    }
                    else{
                        inicio=meio+1;
                    }
                    meio = (inicio+fim)/2;
                    arquivo.seek(meio*tamInt);
                }
                return meio;
            }
            return tl;
        }
        catch (Exception e){
            System.out.printf("Erro ao buscar arquivo: %s\n", e.getMessage());
        }
        return -1;
    }

    public void insercaoBinaria(){
        int aux;
        long n = Registro.tf/tamInt, pos;
        try{
            for(long i = 1; i < n; i++){
                arquivo.seek(i*tamInt);
                aux = arquivo.readInt();
                pos = buscaBinaria(aux, i);
                for(long j = i; j>pos; j--){
                    arquivo.seek((j-1)*tamInt);
                    int value = arquivo.readInt();
                    arquivo.seek(j*tamInt);
                    arquivo.writeInt(value);
                }
                arquivo.seek(pos*tamInt);
                arquivo.writeInt(aux);
            }
        }catch(IOException e){
            System.out.printf("Erro ao ordenar arquivo: %s\n", e.getMessage());
        }
    }
}