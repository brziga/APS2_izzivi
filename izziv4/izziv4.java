import java.util.Scanner;

class izziv4{
    public static void main(String[] args) {
        //branje vhoda
        Scanner npc = new Scanner(System.in);
        int n = npc.nextInt();
        int[] tabela = new int[n];
        for(int i=0; i<n; i++){
            tabela[i] = npc.nextInt();
        }
        npc.close();

        //urejanje
        tabela = counting_sort_bit_num(tabela);
        for(int i=0; i<tabela.length; i++){
            System.out.printf("%d%s",tabela[i],i==tabela.length-1 ? "" : " ");
        }
    }

    public static int st_bitov(int st){
        //vrne stevilo bitov enakh 1 v dvojiski predstavitvi
        int rez = 0;
        String binarni = Integer.toBinaryString(st);
        for(int i=0; i<binarni.length(); i++){
            if(binarni.charAt(i) == '1') rez++;
        }
        return rez;
    }

    public static int[] counting_sort_bit_num(int[] tabela){
        //uredi tabelo po stevilu bitov z urejanjem s stetjem
        
        //z nekim razlogom ta naloge rabi urejanje od desne proti levi...
        int[] c = new int[32];
        for(int i=0; i<tabela.length; i++){
            c[st_bitov(tabela[i])]++;
        }
        int[] c2 = new int[32];
        for(int i=1; i<32; i++){
            c[i] += c[i-1];
        }
        int[] nova_tabela = new int[tabela.length];
        for(int i=tabela.length-1; i>=0; i--){
            int index = c[st_bitov(tabela[i])]-- -1;
            nova_tabela[index] = tabela[i];
            System.out.printf("(%d,%d)\n",tabela[i],index);
        }

        /*
        //pravilen in logicen postopek - od leve proti desni
        int[] c = new int[32];
        for(int i=0; i<tabela.length; i++){
            c[st_bitov(tabela[i])]++;
        }
        int[] c2 = new int[32];
        for(int i=0; i<32-2; i++){
            c2[i+1] = c[i] + c2[i];
        }
        int[] nova_tabela = new int[tabela.length];
        //String[] izpisi = new String[tabela.length];
        for(int i=0; i<tabela.length; i++){
            int index = c2[st_bitov(tabela[i])]++;
            nova_tabela[index] = tabela[i];
            //izpisi[i] = String.format("(%d,%d)\n",tabela[i],index);
            System.out.printf("(%d,%d)\n",tabela[i],index);
        }
        
        //for(int i=tabela.length-1; i>=0; i--){
        //    System.out.printf(izpisi[i]);
        //}
        */
        return nova_tabela;
    }
}