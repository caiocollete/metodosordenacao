package metodos;
/*
* Metodos CompProg, CompEqua, MovProg e MovEqua
*/
public class InsercaoBinaria implements CalculosMetodos {
    @Override
    public double calculaComp(int n) {
        return (int) (n*Math.log(n));
    }

    @Override
    public double calculaMov(int n) {
        return ((n*n)/2)+(n/2);
    }
}
