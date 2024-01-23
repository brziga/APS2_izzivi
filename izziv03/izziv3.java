import java.util.Scanner;

class izziv3 {
    public static void main(String[] args) {
        Scanner joze = new Scanner(System.in);

        int n = joze.nextInt();
        int m = joze.nextInt();
        
        int[] a = new int[n];
        int[] b = new int[m];

        for(int i=0; i<n; i++){
            a[i] = joze.nextInt();
        }
        for(int i=0; i<m; i++){
            b[i] = joze.nextInt();
        }

        int[] zlitje = new int[n+m];
        int ia = 0;
        int ib = 0;
        String izpis = "";

        for(int i=0; i<n+m; i++){
            if(ia >= n){
                zlitje[i] = b[ib++];
                izpis += "b";
                continue;
            }
            else if(ib >= m){
                zlitje[i] = a[ia++];
                izpis += "a";
                continue;
            }
            if(a[ia] <= b[ib]){
                zlitje[i] = a[ia++];
                izpis += "a";
            }
            else{
                zlitje[i] = b[ib++];
                izpis += "b";
            }
        }

        System.out.println(izpis);
        for(int i=0; i<n+m; i++){
            System.out.printf("%d%s", zlitje[i], i==n+m-1 ? "" : " ");
        }
    }
}