import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

/*
 * Navodila
 * --------------------------------------------------------------------------
 * Kodo, ki ste jo izdelali za kviz, preizkusite na svojih testnih primerih. 
 * Zasnujte teste s katerimi boste določili velikost matrik, pri katerih se 
 * že splača uporabiti Strassenov algoritem.
 * 
 * Oddajte kodo s katero ste ta preizkus naredili in rezultate testiranja s 
 * katerim ste ugotovili mejno vrednost.
 */

public class testiranje {
    public static void main(String[] args) {
        StringBuilder izhod = new StringBuilder();
        //System.out.printf("  n  | meja |     naivno     |    Strassen    |\n");
        //System.out.printf("-----|------|----------------|----------------|\n");
        izhod.append("  n  | meja |     naivno     |    Strassen    |  razlika  |\n");
        izhod.append("-----|------|----------------|----------------|-----------|\n");
        for(int i=1; i<=9; i++){
            int size = (int)Math.pow(2, i);
            int st_testov = 50;
            Matrix[] t_a = generiranjeTabele(size, 10, st_testov);
            Matrix[] t_b = generiranjeTabele(size, 10, st_testov);
            Matrix[] t_c = new Matrix[st_testov];
            for(int j=size/2; j>=size/32 && j>=1; j/=2){
                //System.out.printf("%4d |%5d |%15d |%15d |\n",size,j,timeStandard(t_a, t_b, t_c),timeStrassen(t_a, t_b, t_c,j));
                long cas_standard = timeStandard(t_a, t_b, t_c);
                long cas_strassen = timeStrassen(t_a, t_b, t_c,j);
                izhod.append(String.format("%4d |%5d |%15d |%15d |%10d |\n",size,j,cas_standard,cas_strassen,cas_standard-cas_strassen));
            }
            izhod.append("-----|------|----------------|----------------|-----------|\n");
        }
        try{
            FileWriter tiskalnik = new FileWriter("rezultati.txt");
            tiskalnik.write(izhod.toString());
            tiskalnik.close();
        }
        catch(IOException e){
            System.out.println("Napaka z izhodno datoteko!");
            e.printStackTrace();
        }

    }

    //generira kvadratno matriko velikosti n z naključnimi vrednostmi
    //vrednosti so cela števila med 0 in k ( 0 <= x < k )
    public static Matrix generiranjeTestneMatrike(int n, int k){
        Random rand = new Random();
        Matrix rez = new Matrix(n);
        for(int i=0; i<n; i++){
            for(int j=0; j<n; j++){
                rez.setV(i, j, rand.nextInt(k));
            }
        }
        return rez;
    }

    //generira tabelo testnih matrik
    //podamo parametra matrike (velikost in zgornjo mejo vrednosti) ter stevilo testov
    public static Matrix[] generiranjeTabele(int n, int k, int t){
        Matrix[] testi = new Matrix[t];
        for(int i=0; i<t; i++){
            testi[i] = generiranjeTestneMatrike(n, k);
        }
        return testi;
    }

    //zmnozi matrike a in b v c ter vrne povprecni cas za eno mnozenje
    //uporabi standardno mnozenje
    public static long timeStandard(Matrix[] tabela_a, Matrix[] tabela_b, Matrix[] tabela_c){
        long start = System.nanoTime();
        for(int i=0; i<tabela_c.length; i++){
            tabela_c[i] = tabela_a[i].mult(tabela_b[i]);
        }
        long trajanje = System.nanoTime() - start;
        return trajanje/tabela_c.length;
    }

    //zmnozi matrike, uporabi strassenovo mnozenje, vrne povp cas za eno mnozenje
    public static long timeStrassen(Matrix[] tabela_a, Matrix[] tabela_b, Matrix[] tabela_c, int cutoff){
        long start = System.nanoTime();
        for(int i=0; i<tabela_c.length; i++){
            tabela_c[i] = tabela_a[i].multStrassen(tabela_b[i], cutoff);
        }
        long trajanje = System.nanoTime() - start;
        return trajanje/tabela_c.length;
    }
}
