import java.io.FileNotFoundException;

public class Reduction {
    public static void main(String[] args) throws FileNotFoundException {
        Graf graf = new Graf();
        graf = graf.citeste(args[0]);
        String s = graf.Reduction();
        System.out.println(s);
    }
}