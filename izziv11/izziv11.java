import java.util.Scanner;

class izziv11 {
    public static void main(String[] args) {
        
        Scanner joze = new Scanner(System.in);
        int W = joze.nextInt();
        int n = joze.nextInt();
        predmet[] predmeti = new predmet[n];

        for(int i=0; i<n; i++){
            predmeti[i] = new predmet(joze.nextInt(), joze.nextInt());
        }

        joze.close();

        resitve stare = new resitve(1);
        stare.put(0,new predmet(0,0));

        System.out.printf("0: %s\n", stare.fetch(0).toString());

        for(int ip=0; ip<n; ip++){
            predmet trenutni = predmeti[ip];
            resitve nove = new resitve(stare.count);
            for(int ir=0; ir<stare.count; ir++){
                nove.put(ir, trenutni.joinwith(stare.fetch(ir)));
            }
            //zlivanje
            resitve zlite = new resitve(stare.count*2);
            zlite.put(0, stare.fetch(0));
            int is = 1;
            int in = 0;
            int i = 1;
            while(is<stare.count || in<nove.count){
                predmet vobdelavi;
                predmet vzemistar = is<stare.count ? stare.fetch(is) : null;
                predmet vzeminov = in<nove.count ? nove.fetch(in) : null;
                if( vzeminov == null || (vzemistar != null && vzemistar.getw() < vzeminov.getw()) ){
                    vobdelavi = vzemistar;
                    is++;
                }
                else{
                    vobdelavi = vzeminov;
                    in++;
                }
                
                boolean odstranimo = false;
                if(vobdelavi.getw() > W){
                    odstranimo = true;
                }
                for(int j=0; j<i && !odstranimo; j++){
                    predmet primerjani = zlite.fetch(j);
                    if(primerjani.getw() <= vobdelavi.getw() && primerjani.getv() >= vobdelavi.getv()){
                        odstranimo = true;
                        break;
                    }
                }
                
                if(!odstranimo){
                    zlite.put(i++, vobdelavi);
                }
            }
            resitve trimmed = new resitve(i);
            for(int a=0; a<i; a++){
                trimmed.put(a, zlite.fetch(a));
            }
            stare = trimmed;

            //izpis
            System.out.printf("%d:",ip+1);
            for(int a=0; a<stare.count; a++){
                System.out.printf(" %s", stare.fetch(a).toString());
            }
            System.out.println();
            
        }
    }
}

class predmet {

    private int w; // weight
    private int v; // value 
    private boolean inc;

    predmet(int a, int b){
        w = a;
        v = b;
        inc = false;
    }

    int getw(){
        return w;
    }

    int getv(){
        return v;
    }

    boolean include(){
        if(inc) return false;
        else{
            inc = true;
            return inc;
        }
    }

    predmet joinwith(predmet p){
        return new predmet(w + p.getw(), v + p.getv());
    }

    public String toString(){
        return "(" + w + ", " + v + ")";
    }

}

class resitve{

    int count;
    predmet[] seznam;

    resitve(int n){
        count = n;
        seznam = new predmet[n];
    }

    predmet fetch(int i){
        return seznam[i];
    }

    boolean remove(int i){
        if(seznam[i] == null) return false;
        seznam[i] = null;
        count--;
        return true;
    }

    boolean put(int i, predmet p){
        if(i>=count) return false;
        seznam[i] = p;
        return true;
    }

}