import java.io.*;

public class Tabela {
    private String nomeArquivo;
    private BufferedWriter writer;

    public Tabela(String nomeArquivo) throws IOException {
        this.nomeArquivo = nomeArquivo;
        this.writer = new BufferedWriter(new FileWriter(nomeArquivo));
        escreveCabecalho();
    }

    private void escreveCabecalho() throws IOException {
        writer.write(String.format("%-20s %-50s %-50s %-50s%n", "", "|               Arquivo Ordenado                |",
                "     |                Arquivo Reverso                |",
                "      |               Arquivo Randômico               |"));
        writer.write(
                "----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------\n");

        writer.write(String.format(
                "%-20s %-10s %-10s %-10s %-10s %-10s  %-10s %-10s %-10s %-10s %-10s  %-10s %-10s %-10s %-10s %-10s%n",
                "Método",
                "CompProg", "CompEqua", "MovProg", "MovEqua", "Tempo",
                "CompProg", "CompEqua", "MovProg", "MovEqua", "Tempo",
                "CompProg", "CompEqua", "MovProg", "MovEqua", "Tempo"));
        writer.write(
                "----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------\n");
    }

    public void gravaLinha(
            String metodo,
            int compOrd, int compEqOrd, int movOrd, int movEqOrd, long tempoOrd,
            int compRev, int compEqRev, int movRev, int movEqRev, long tempoRev,
            int compRand, int compEqRand, int movRand, int movEqRand, long tempoRand) throws IOException {

        writer.write(String.format(
                "%-20s %-10d %-10d %-10d %-10d %-10d  %-10d %-10d %-10d %-10d %-10d  %-10d %-10d %-10d %-10d %-10d%n",
                metodo, compOrd, compEqOrd, movOrd, movEqOrd, tempoOrd,
                compRev, compEqRev, movRev, movEqRev, tempoRev,
                compRand, compEqRand, movRand, movEqRand, tempoRand));
        writer.write(
                "----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------\n");
        writer.flush();
    }

    public void fechar() throws IOException {
        writer.close();
    }
}