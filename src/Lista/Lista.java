package Lista;

public class Lista {
    private No ini;
    private No fim;

    public Lista() {
        this.ini = null;
        this.fim = null;
    }
    public void Limpar(){
        this.ini = this.fim = null;
    }
    public No getIni() {
        return ini;
    }
    public void setIni(No ini) {
        this.ini = ini;
    }
    public No getFim() {
        return fim;
    }
    public void setFim(No fim) {
        this.fim = fim;
    }

    private int Tamanho(){
        if(this.ini == null){
            return -1;
        }
        int cont=1;
        for(No aux=this.ini;aux!=this.fim;aux=aux.getProx())
            cont++;
        return cont;
    }
    private int Pos(No no){
        No aux=this.ini;
        int cont=1;
        if(no == null)
            return -1;
        while(aux!=no){
            cont++;
            aux = aux.getProx();
        }
        if(aux == null)
            return -1;
        return cont;
    }
    private No Move(No inicio,int cont,int dir) {
        No aux = null;
        if (inicio != null) {
            if (dir == -1)
                while (aux.getAnt() != null && cont != 0) {
                    aux = aux.getProx();
                    cont++;
                }
            else if (dir == 1)
                while (aux.getProx() != null && cont != 0) {
                    aux = aux.getProx();
                    cont--;
                }
        }
        return aux;
    }
// Inserção Direta
// Inserção Binária;
// Seleção Direta
// Bolha
// Shake;
// Shell;
// Heap;
// Quick (com e sem pivô);
// Fusão Direta (Merge) (duas implementações).



// Algoritmos para serem pesquisados na literatura e implementados:
// Counting;
// Bucket;
// Radix;
// Comb;
// Gnome;
// Tim

    public void InsersaoDireta() {
        for (No j = this.fim;j!= this.ini;j=j.getAnt()) {
            for(No i = this.ini;i != j;i=i.getProx())
                if(i.getInfo()>j.getProx().getInfo()){
                    int aux = i.getInfo();
                    i.setInfo(i.getProx().getInfo());
                    i.getProx().setInfo(aux);
                }
        }
    }

    public void SelecaoDireta(){
        for(No i = this.ini; i != null; i = i.getProx()){
            No menor = i;
            for(No j = i.getProx(); j != null; j = j.getProx()){
                if(j.getInfo()<menor.getInfo()){
                    menor = j;
                }
            }
            int aux = menor.getInfo();
            menor.setInfo(i.getInfo());
            i.setInfo(aux);
        }
    }

    public void Bolha(){
        for(No i = this.ini; i != this.fim; i = i.getProx()){
            for(No j = this.ini; j != this.fim; j = j.getProx()){
                if(j.getInfo()>i.getProx().getInfo()){
                    int aux= j.getInfo();
                    j.setInfo(j.getProx().getInfo());
                    j.getProx().setInfo(aux);
                }
            }
        }
    }

    public void Shake(){
        int num = 0;
        for(No i = this.ini; i != this.fim; i = i.getProx()){
            num++;
        }
        for (No i = this.ini; num != 0; i = i.getProx(), num = num - 1){
            for(No j = this.ini; j != this.fim; j = j.getProx()){
                if(j.getInfo()>i.getProx().getInfo()){
                    int aux= j.getInfo();
                    j.setInfo(j.getProx().getInfo());
                    j.getProx().setInfo(aux);
                }
            }
            for(No j = this.fim; j != this.ini; j = j.getAnt()){
                if(j.getInfo()<i.getAnt().getInfo()){
                    int aux= j.getInfo();
                    j.setInfo(j.getAnt().getInfo());
                    j.getAnt().setInfo(aux);
                }
            }
        }
    }

    public void Quick(){
        Q(this.ini,this.fim);
    }

    private void Q(No inicio,No fim){
        No i = inicio,j = fim;
        int aux;
        boolean flag = true;
        while(i != j){
            if(flag){
                while(i!=j && i.getInfo() <= j.getInfo())
                    i=i.getProx();
            }
            else{
                while(i!=j && i.getInfo() <= j.getInfo())
                    j=j.getAnt();
            }
            aux = i.getInfo();
            i.setInfo(j.getInfo());
            j.setInfo(aux);
            flag = !flag;
        }
        if(inicio!=i.getAnt())
            Q(inicio,i.getAnt());
        if(fim!=j.getProx())
            Q(i.getProx(),fim);
    }

    public void QuickPivo() {
        QP(this.ini,this.fim);
    }

    private void QP(No inicio,No fim){
        No i = inicio,j = fim;
        int pivo = Move(inicio,(this.Pos(inicio)+this.Pos(fim))/2,1).getInfo();

        while (i != null && j != null && Pos(i) <= Pos(j)) {
            while (i.getInfo()< pivo)
                i=i.getProx();
            while (j.getInfo()> pivo)
                j=j.getAnt();
            if(i != j && i != j.getProx()){
                int aux = i.getInfo();
                i.setInfo(j.getInfo());
                j.setInfo(aux);
                i=i.getProx();
                j=j.getAnt();
            }
        }
        if(inicio!=j && inicio != j.getProx())
            QP(inicio,j);
        if (fim!=i && i != fim.getAnt())
            QP(i,fim);
    }


}

