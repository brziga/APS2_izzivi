import java.util.LinkedList;
import java.util.Scanner;

class izziv5{
    public static void main(String[] args) {
        /* //testiranje rezanja
        int[] test = {1,2,3,4,5,6};
        int[] testrez = salamoreznica(test,1,4); //2,3,4
        */

        Scanner npc = new Scanner(System.in);

        String vhod = npc.nextLine();
        npc = new Scanner(vhod);
        LinkedList<Integer> temp = new LinkedList<Integer>();
        while(npc.hasNextInt()){
            temp.add(npc.nextInt());
        }
        npc.close();
        int[] vhodi = new int[temp.size()];
        for(int i=0; i<temp.size(); i++){
            vhodi[i] = (int) temp.get(i);
        }
        //System.out.println(podzaporedje(vhodi));
        int rezultat = podzaporedje(vhodi);

    }

    public static int podzaporedje(int[] seznam){
        if(seznam.length == 1){
            System.out.printf("[%d]: %d\n", seznam[0], seznam[0]);
            return seznam[0]; //robni primer
        }

        int[] vsote = new int[3];
        int sredina = (int) Math.ceil(seznam.length/2.0);
        //delitev
        int[] leva = salamoreznica(seznam, 0, sredina);
        int[] desna = salamoreznica(seznam, sredina, seznam.length);

        //najvecji vsoti levega in desnega dela
        vsote[0] = podzaporedje(leva);
        vsote[1] = podzaporedje(desna);

        //iskanje najvecje vsote ce so elementi iz obeh
        int sred_vsota = seznam[sredina-1] + seznam[sredina]; //en iz leve in en iz desne
        int running_vsota = sred_vsota;
        for(int i=sredina-2; i>=0; i--){
            running_vsota += seznam[i];
            sred_vsota = running_vsota>sred_vsota ? running_vsota : sred_vsota;
        } //do levega roba
        running_vsota = sred_vsota;
        for(int i=sredina+1; i<seznam.length; i++){
            running_vsota += seznam[i];
            sred_vsota = running_vsota>sred_vsota ? running_vsota : sred_vsota;
        } //do desnega roba
        vsote[2] = sred_vsota;

        int rezultat = Math.max(Math.max(vsote[0],vsote[1]),vsote[2]);

        //izpis
        System.out.printf("[");
        for(int i=0; i<seznam.length; i++){
            System.out.printf("%d%s",seznam[i],i==seznam.length-1 ? "" : ", ");
        }
        System.out.printf("]: %d\n",rezultat);
        return rezultat;
    }

    public static int[] salamoreznica(int[] seznam, int start, int end){
        // ne vkljucuje end
        int[] rez = new int[end-start];
        for(int i=0; i< end-start;i++){
            rez[i] = seznam[start+i];
        }
        return rez;
    }
}