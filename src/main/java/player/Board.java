package player;

import java.util.ArrayList;     // NB. id = 9 non si può usare per i giocatori

public class Board {
    private int[] lifePoints;
    private ArrayList<Integer> target;

    public void Board(){
        for (int i=0; i<12; i++){
            lifePoints[i]=9;
        }

        for (int i=0; i<6; i++){         // creo per il momento 6 slot per target
            target.add(i, 9);
        }
    }

    public void InitializeBoard(){   // serve anche per quando muore un giocatore
        for (int i=0; i<12; i++){
            lifePoints[i]=9;
        }
    }

    public void updateLife(int damage, int idPlayerAttaccante){
        for (int i=0; i<12; i++){
            if (lifePoints[i]==9){
                for (int k=0; k<damage || (i+k) <12 ; k++){
                    lifePoints[i+k] = idPlayerAttaccante;
                }
            }
        }

        if (isFullLife()){
            // TODO resetta board, assegna punteggi
        }

    }

    public ArrayList<Integer> getTarget() {
        return target;
    }


    public int[] getLifePoints() {
        return lifePoints;
    }

    public boolean isFullLife(){
        int i=0;
        for (; lifePoints[i]!=9 || i<12; i++){
        }
        if (i==12) return true;
        else return false;
    }


}
