package model.player;

import java.util.ArrayList;     // NB. id = 9 non si può usare per i giocatori
import java.util.List;

public class Board {
    private int[] lifePoints;
    private ArrayList<Integer> target;



    public void Board(){
        lifePoints = new int[12];
        target = new ArrayList<>();
        for (int i=0; i<12; i++){
            lifePoints[i]=9;
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

    }

    public void updateTarget(int damageTarget, int idPlayerAttaccante){
        int countTarget=0;
        for (int i=0; i<target.size(); i++){
            if (target.get(i) ==idPlayerAttaccante) countTarget++;
        }
        if (countTarget<3){
            // allora posso aggiungere dei taget
            if (countTarget+damageTarget>3){
                // aggiungo la differenza tra il massimo(3) e quelli che già ho
                for (int i=0; i<(3-countTarget); i++){
                    target.add(target.size(), idPlayerAttaccante);
                }
            }
            else {
                // aggiungo numero di target pari al damageTarget
                for (int i=0; i<damageTarget; i++){
                    target.add(target.size(), idPlayerAttaccante);
                }

            }
        }
    }

    public void removeTarget(int numeroDiTargetDaTogliere, int idPlayerAttaccante){
        int countTarget=0;
        for (int i=0; i<target.size() || countTarget<numeroDiTargetDaTogliere; i++){
            if (target.get(i)==idPlayerAttaccante) {
                target.remove(i);
                countTarget++;
            }
        }
    }

    public List<Integer> getTarget() {
        return target;
    }

    public int getSpecificTarget(int idPlayerAttaccante) {
        int countTarget=0;
        for (int i=0; i<target.size(); i++){
            if (target.get(i) ==idPlayerAttaccante) countTarget++;
        }
        return countTarget;
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
