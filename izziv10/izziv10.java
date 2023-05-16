import java.util.Scanner;

class izziv10 {
    public static void main(String[] args) {
        
        /* Preberimo vhodne podatke */
        Scanner mona = new Scanner(System.in);
        int N = mona.nextInt() + 1; // +1 ker program zahteva vkljucenost, koda pa dela brez 
        int K = mona.nextInt();
        mona.close();


        /* Pripravimo tabelo */
        int[][] tabela = new int[N][K];


        /* Poracunamo robne primere */
        
        /* s(n, 1) = n */
        for(int n=0; n<N; n++){
            tabela[n][1-1] = n;
        }

        /* s(0, k) = 0  in  s(1, k) = 1 */
        for(int k=0; k<K; k++){
            tabela[0][k] = 0;
            tabela[1][k] = 1;
        }


        /* Poracunamo preostanek tabele */
        for(int n=2; n<N; n++){ // sprehod po vrsticah
            for(int k=2-1; k<K; k++){ // sprehod po stolpcih
                /* Zberemo vse maximume */
                int[] maxi = new int[n];
                for(int i=0; i<n; i++){
                    maxi[i] = Math.max(tabela[i][k-1], tabela[n-1-i][k]);
                }

                /* Poiscemo minimum */
                int min = Integer.MAX_VALUE;
                for(int i=0; i<maxi.length; i++){
                    min = maxi[i] < min ? maxi[i] : min;
                }

                /* Koncno izracunamo vrednost celice */
                tabela[n][k] = 1 + min;

            } // for k
        }// for n


        /* Izpis */
        
        /* Naslovna vrstica */
        System.out.printf("%4s", "");
        for(int i=1; i<=K; i++){
            System.out.printf("%4d", i);
        }
        System.out.println();

        /* Oznaka vrstice in vrednosti */
        for(int n=0; n<N; n++){
            System.out.printf("%4d", n); // Oznaka stolpca
            for(int k=0; k<K; k++){
                System.out.printf("%4d", tabela[n][k]);
            } // for k
            System.out.println();
        } // for n

    }
}