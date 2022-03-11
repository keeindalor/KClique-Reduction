import javax.swing.text.html.parser.Parser;
import java.io.FileNotFoundException;
import java.util.ArrayList;

public class Bkt {
    public static void main(String[] args) throws FileNotFoundException {
        Graf graf = new Graf();
        graf = graf.citeste(args[0]);
        ArrayList<Integer> noduri = new ArrayList<>(graf.getHarta().keySet());
        int[] graff = new int[graf.getK()];
        graf.KClique(noduri, 0, 0, graff);
        System.out.println("False");
    }
}
