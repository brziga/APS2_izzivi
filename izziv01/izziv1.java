import java.util.Random;

public class izziv1 {
    public static void main(String[] args)
    {
        /* test delovanja
        int[] a = {1,2,3,4,5,6,7};
        int[] b = {1,2,3,4,5,6,7,8,9,10};
        for(int i=0;i<8;i++){
            System.out.println(findBinary(a, 0, 6, i));
        }
        System.out.println();
        for(int i=0;i<11;i++){
            System.out.println(findBinary(b, 0, 9, i));
        }
        */

        System.out.println("    n    |   linearno   |   dvojisko   |");
        System.out.println("---------+--------------+--------------+");
        for(int n = 1000; n <= 100000; n += 1000){
            System.out.printf("%8d |%13d |%13d |\n",n,timeLinear(n),timeBinary(n));
        }

        /*
         * Razmislek:
         * Zakaj so casi pri vas drugacni kot v zgornji tabeli?
         *      - zaradi razlik v strojni opremi in drugih faktorjev okolja izvajanja
         * Kateri algoritem je hitrejsi?
         *      - dvojisko iskanje je precej hitrejse
         * Kdaj bi lahko bil pocasnejsi algoritem hitrejsi?
         *      - v primeru, da je iskano stevilo na zacetku tabele na mestu, katerega zaporedna stevilka je manjsa od rezultata log2(n),
         *        saj bu v tem primeru naredil manj korakov, kot dvojisko iskanje
         * Kako je cas odvisen od velikosti naloge (linearno, kvadratno, ...)?
         *      - pri linearnem iskanju je odvnisnost linearna, pri dvojiskem pa logaritemska
         * Je casovna odvisnost dvojiskega iskanja blizje linearni ali konstantni?
         *      - blizje je konstantni
         */

    }

    public static int[] generateTable(int n){
        int[] tabela = new int[n];
        for(int i=0; i<n; i++){
            tabela[i] = i+1;
        }
        return tabela;
    }

    public static int findLinear(int[] a, int v){
        for(int i=0; i<a.length; i++){
            if(a[i]==v){
                return i;
            }
        }
        //v primeru, da element ni v tabeli, vrnemo -1
        return -1;
    }

    public static int findBinary(int[] a, int l, int r, int v){
        if(l<=r){
            int sredina = l + (r-l) / 2;
            if(a[sredina] == v) return sredina;
            if(v < a[sredina]){
                return findBinary(a,l,sredina-1,v);
            }
            else if(v > a[sredina]){
                return findBinary(a,sredina+1,r,v);
            }
        }
        return -1;
    }

    public static long timeLinear(int n){
        int[] tabela = generateTable(n);
        Random r = new Random();

        long startTime = System.nanoTime();
        // iskanje elementa
        for(int i=0; i<1000; i++){
            int st = r.nextInt(n);
            int rez = findLinear(tabela,st);
        }
        long executionTime = System.nanoTime() - startTime;

        return executionTime/1000;
    }

    public static long timeBinary(int n){
        int[] tabela = generateTable(n);
        Random r = new Random();

        long startTime = System.nanoTime();
        // iskanje elementa
        for(int i=0; i<1000; i++){
            int st = r.nextInt(n);
            int rez = findBinary(tabela,0,tabela.length,st);
        }
        long executionTime = System.nanoTime() - startTime;

        return executionTime/1000;
    }
}