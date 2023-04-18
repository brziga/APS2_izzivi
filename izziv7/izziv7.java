import java.util.Scanner;

class izziv7{
    public static void main(String[] args) {
        Scanner joza = new Scanner(System.in);
        int n = joza.nextInt();
        joza.close();
        int[] prastevila = getPrimes();
        boolean najdeno = false;
        int p = 0;
        int[] rezultati = null;
        int r_ct = 0;

        for(int i=0; i<prastevila.length && !najdeno; i++){
            p = prastevila[i];
            rezultati = new int[p-1];
            r_ct = 0;

            if(p<=n) continue;

            int[][] tabela = new int[p-1][p-1];
            for(int j=0; j<p-1; j++){
                tabela[0][j] = j+1;
            }
            for(int j=0; j<p-1; j++){
                tabela[1][j] = ((j+1) * tabela[0][j])%p;
            }
            for(int vrstica=2; vrstica<n; vrstica++){
                for(int j=0; j<p-1; j++){
                    tabela[vrstica][j] = (tabela[0][j] * (tabela[vrstica-1][j]!=1 ? tabela[vrstica-1][j] : 0))%p;
                    
                }
            }
            
            for(int j=1; j<p-1; j++){
                if(tabela[n-1][j] == 1){
                    rezultati[r_ct++] = j+1;
                    najdeno = true;
                }
            }
        }

        System.out.printf("%d: ", p);
        for(int i=0; i<r_ct; i++){
            System.out.printf("%d ",rezultati[i]);
        }
        System.out.printf("\n");

        long[][] vandermond = new long[n][n];
        int najm = rezultati[0];

        for(int v=0; v<n; v++){
            for(int s=0; s<n; s++){
                //vandermond[v][s] = ((long)Math.pow(((long)Math.pow(najm, v)),s))%p;
                vandermond[v][s] = ((long)Math.pow(najm, v)) % p;
                vandermond[v][s] = ((long)Math.pow(vandermond[v][s],s)) % p;
                System.out.printf("%2d%s", vandermond[v][s],s==n-1 ? "" : " ");
            }
            System.out.printf("\n");
        }

    }


    public static int[] getPrimes(){
        //get prime numbers
        int[] primes = {2, 3, 5, 7, 11, 13, 17, 19, 23, 29, 31, 37, 41, 43, 47, 53, 59, 61, 67, 71, 73, 79, 83, 89, 97, 101, 103, 107, 109, 113, 127, 131, 137, 139, 149, 151, 157, 163, 167, 173, 179, 181, 191, 193, 197, 199, 211, 223, 227, 229, 233, 239, 241, 251, 257, 263, 269, 271, 277, 281, 283, 293, 307, 311, 313, 317, 331, 337, 347, 349, 353, 359, 367, 373, 379, 383, 389, 397, 401, 409, 419, 421, 431, 433, 439, 443, 449, 457, 461, 463, 467, 479, 487, 491, 499, 503, 509, 521, 523, 541, 547, 557, 563, 569, 571, 577, 587, 593, 599, 601, 607, 613, 617, 619, 631, 641, 643, 647, 653, 659, 661, 673, 677, 683, 691, 701, 709, 719, 727, 733, 739, 743, 751, 757, 761, 769, 773, 787, 797, 809, 811, 821, 823, 827, 829, 839, 853, 857, 859, 863, 877, 881, 883, 887, 907, 911, 919, 929, 937, 941, 947, 953, 967, 971, 977, 983, 991, 997, 1009, 1013, 1019, 1021, 1031, 1033, 1039, 1049, 1051, 1061, 1063, 1069, 1087, 1091, 1093, 1097, 1103, 1109, 1117, 1123, 1129, 1151, 1153, 1163, 1171, 1181, 1187, 1193, 1201, 1213, 1217, 1223, 1229, 1231, 1237, 1249, 1259, 1277, 1279, 1283, 1289, 1291, 1297, 1301, 1303, 1307, 1319, 1321, 1327, 1361, 1367, 1373, 1381, 1399, 1409, 1423, 1427, 1429, 1433, 1439};
        return primes;
    }
}