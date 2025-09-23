package Lista;

public class No {
    private int info;
    private No ant;
    private No Prox;

    public No(int info) {
        this.info = info;
        this.ant = null;
        this.Prox = null;
    }

    public int getInfo() {
        return info;
    }

    public void setInfo(int info) {
        this.info = info;
    }

    public No getAnt() {
        return ant;
    }

    public void setAnt(No ant) {
        this.ant = ant;
    }

    public No getProx() {
        return Prox;
    }

    public void setProx(No prox) {
        Prox = prox;
    }
}
