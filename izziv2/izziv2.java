import java.util.Scanner;

class izziv2{
    public static void main(String[] args){
        // branje n in tabele
        Scanner joze = new Scanner(System.in);
        int n = joze.nextInt();
        int[] tabela = new int[n];
        for(int i=0; i<n; i++){
            tabela[i] = joze.nextInt();
        }
        joze.close();

        /*//  popravljanje kopice tabele
        for(int i=n/2 - 1; i>=0; i--){
            pogrezni(tabela, i, n);
        }*/

        for(int iter=0; iter<n; iter++){
            int dolzina = n-iter;
            for(int i=dolzina/2 - 1; i>=0; i--){
                pogrezni(tabela, i, dolzina);
            }
            //izpis 
            boolean konec1 = false;
            for(int p=0; p<=((int) (Math.log(dolzina)/(Math.log(2)))); p++){
                for(int i = (int)Math.pow(2,p) - 1; i<(int) Math.pow(2,p+1) - 1; i++){
                    System.out.printf("%d",tabela[i]);
                    if(i==dolzina-1) konec1 = true;
                    if(!konec1){
                        System.out.printf(" ");
                    }
                    else break;
                }
                if(!konec1) System.out.printf("| ");
            }
            System.out.println();

            int temp = tabela[0];
            tabela[0] = tabela[dolzina-1];
            tabela[dolzina-1] = temp;
            

        }

        
    }

    public static void pogrezni(int a[], int i, int dolzKopice){
        //za max kopico

        if(2*i+1 >= dolzKopice) return; //list je urejena kopica
        if(2*i+2 >= dolzKopice && 2*i+1 < dolzKopice){
            //samo levi sin
            if(a[i] < a[2*i+1]){
                //menjamo z levim sinom
                int temp = a[i];
                a[i] = a[2*i+1];
                a[2*i+1] = temp;
                pogrezni(a, 2*i+1, dolzKopice);
            }
            else return;
        }

        else if(a[i] > a[2*i+1] && a[i] > a[2*i+2]) return; //vecje od obeh -> ok kopica

        else if(a[i] < a[2*i+1] && a[i] < a[2*i+2]){
            //manjsi od obeh -> menjamo z vecjim
            if(a[2*i+1] >= a[2*i+2]){
                //menjamo z levim sinom
                int temp = a[i];
                a[i] = a[2*i+1];
                a[2*i+1] = temp;
                pogrezni(a, 2*i+1, dolzKopice);
            }
            else{
                //menjamo z desnim sinom
                int temp = a[i];
                a[i] = a[2*i+2];
                a[2*i+2] = temp;
                pogrezni(a, 2*i+2, dolzKopice);
            }
        }
        else if(a[i] < a[2*i+1]){
            //menjamo z levim sinom
            int temp = a[i];
            a[i] = a[2*i+1];
            a[2*i+1] = temp;
            pogrezni(a, 2*i+1, dolzKopice);
        }
        else if(a[i] < a[2*i+2]){
            //menjamo z desnim sinom
            int temp = a[i];
            a[i] = a[2*i+2];
            a[2*i+2] = temp;
            pogrezni(a, 2*i+2, dolzKopice);
        }

        return;
    }
}