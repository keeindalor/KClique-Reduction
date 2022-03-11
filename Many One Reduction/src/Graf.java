import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.io.FileNotFoundException;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Graf {
    public int k, nr_noduri;
    public HashMap<Integer, ArrayList<Integer>> harta;

    public Graf(){}
    public Graf(int k, int nr_noduri) {
        this.k = k;
        this.nr_noduri = nr_noduri;
        this.harta = new HashMap<Integer, ArrayList<Integer>>();
    }

    public int getK() {
        return k;
    }

    public Map<Integer, ArrayList<Integer>> getHarta() {
        return harta;
    }
    //functie care creeaza o muchie intre doua noduri, a = sursa, b = destinatie
    public void adaugaMuchie (int a, int b) {
        if (harta.containsKey(a)) {
            harta.get(a).add(b);
        }
        else {
            harta.put(a, new ArrayList<Integer>());
            harta.get(a).add(b);
        }
        if (harta.containsKey(b)) {
            harta.get(b).add(a);
        }
        else {
            harta.put(b, new ArrayList<Integer>());
            harta.get(b).add(a);

        }
    }
     public Graf citeste(String file_name) throws FileNotFoundException {
        Graf graf = null;
        BufferedReader br = new BufferedReader(new FileReader(file_name));
        try {
            int k = Integer.parseInt(br.readLine());
            int nr_noduri = Integer.parseInt(br.readLine());
            int nr_muchii = Integer.parseInt(br.readLine());
            graf = new Graf(k, nr_noduri);
            String line;
            int count = 0;
            while ((line = br.readLine()) != null) {
                count++;
                String[] noduri = line.split(" ");
                int sursa = Integer.parseInt(noduri[0]);
                int destinatie = Integer.parseInt(noduri[1]);
                graf.adaugaMuchie(sursa, destinatie);
            }
            if (count != nr_muchii) {
                System.out.println("insucces");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return graf;
    }
    //functie care verifica daca exista muchie intre doua noduri
    public boolean verificaMuchie (int a, int b) {
        if (!harta.containsKey(a))
            return false;
        else
            return harta.get(a).contains(b);
    }

    //voi explica acum ce reprezinta fiecare parametru al metodei
    //noduri este lista de noduri din hashmap-ul in care am stocat nodurile si muchiile
    //index reprezinta elementul curent din vectorul temporar
    //graf este un vector temporar in care vom stoca nodurile din lista initiala de noduri
    //i reprezinta elementul curent in lista de noduri initiala
    void KClique (ArrayList<Integer> noduri, int index, int i, int[] graf) {
       //verificam combinatia curenta, daca exista muchie sau nu intre noduri
        if (index == this.k) {
            for (int j = 0; j < index; j++) {
                for (int k = j + 1; k < index; k ++) {
                    if (!verificaMuchie(graf[j], graf[k])){
                       return;
                    }
                }
            }
            System.out.println("True");
            System.exit(0);
            
        }
        //cand nu mai sunt elemente de pus in vectorul temporar, iesim din backtracking
        if (i == noduri.size()) {
            return;
        }
        //adaugam combinatia curenta, si mergem la elementul urmator (index + 1, i + 1)
        graf[index] = noduri.get(i);
        KClique(noduri, index + 1, i +1, graf);
        KClique(noduri, index , i +1, graf); //la fel ca si anterior, insa index ramane neschimbat

    }
    public String Reduction () {
        int i, j, u, v;
        String s = "";
        //pentru fiecare i,  1<=i<=k, exista un al i-lea nod in clica (avem xiv, unde 1<=v<=nr_noduri)
        for (i = 1; i <= this.k; i ++) {
            s += "(";
            for (v = 1; v <= this.nr_noduri; v++) {
                s += "x" + v + i;
                if (v < this.nr_noduri){
                    s += "V";
                }
            }
            s += ")^";
        }
       //pentru fiecare i si j, al i-lea nod este diferit fata de cel de-al j-lea nod din graf, unde v este nr maxim de noduri
        for (v = 1; v <= this.nr_noduri; v++) {
            for (i = 1; i <= this.k;i++) {
                for (j = i+1; j <= this.k; j++) {
                    s += "(~x" + v + i + "V~x" + v + j + ")^";
                }
            }
        }
        //daca nu exista muchie, clar nu poate face parte din clica (noduri de forma xiu, xjv)
        for (i = 1; i <= this.k; i++) {
            for (j = i + 1; j <= this.k; j++) {
                for (u = 1; u <= this.nr_noduri; u++) {
                    for (v = 1; v <= this.nr_noduri; v++) {
                        if (!verificaMuchie(u, v) && u != v) {
                            s += "(~x" + u + i + "V~x" + v + j ;
                            s += ")^";
                        }
                    }
                }
            }
        }
        StringBuilder sb = new StringBuilder(s);
        sb.deleteCharAt (sb.length() - 1);
        return sb.toString();
    }
}

