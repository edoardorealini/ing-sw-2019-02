package model.player;

import java.util.ArrayList;     // NB. id = 9 non si può usare per i giocatori
import java.util.List;

public class Board {
    private int[] lifePoints;
    private ArrayList<Integer> marks;



    public Board(){
        lifePoints = new int[12];
        marks = new ArrayList<>();
        for (int i=0; i<12; i++){
            lifePoints[i]=9;
        }
    }

    public void InitializeBoard(){   // serve anche per quando muore un giocatore
        for (int i=0; i<12; i++){
            lifePoints[i]=9;
        }
    }

    public void updateLife(int damage, int idPlayerAttaccante){   //TODO gestire i casi limite
        for (int i=0; i<12; i++){
            if (lifePoints[i]==9){
                for (int k=0; (k<damage) && ((i+k) <12) ; k++){
                    lifePoints[i+k] = idPlayerAttaccante;
                }
                return;
            }
        }
        //TODO aggiorna attributo dead quando lifepoints >12 o >10, non ricordo
        //TODO mi sa che bisogna gestire il caso in cui attacco uno che muore, ma ha anche miei marchi, devo evitare di farli diventare danni perchè rimangono dopo la rinascita
    }

    public void updateMarks(int damageMarks, int idPlayerAttaccante){
        int countMarks=0;
        for (int i=0; i<marks.size(); i++){
            if (marks.get(i) ==idPlayerAttaccante) countMarks++;
        }
        if (countMarks<3){
            // allora posso aggiungere dei taget
            if (countMarks+damageMarks>3){
                // aggiungo la differenza tra il massimo(3) e quelli che già ho
                for (int i=0; i<(3-countMarks); i++){
                    marks.add(marks.size(), idPlayerAttaccante);
                }
            }
            else {
                // aggiungo numero di marks pari al damageMarks
                for (int i=0; i<damageMarks; i++){
                    marks.add(marks.size(), idPlayerAttaccante);
                }

            }
        }
    }

    public void removeMarks(int numeroDiMarksDaTogliere, int idPlayerAttaccante){
        int countMarks=0;
        for (int i=0; (i<marks.size()) && (countMarks<numeroDiMarksDaTogliere); i++){
            if (marks.get(i)==idPlayerAttaccante) {
                marks.remove(i);
                countMarks++;
                i=i-1;
            }
        }
    }

    public List<Integer> getMarks() {
        return this.marks;
    }

    public int getSpecificMarks(int idPlayerAttaccante) {
        int countMarks=0;
        for (int i=0; i<marks.size(); i++){
            if (marks.get(i) ==idPlayerAttaccante) countMarks++;
        }
        return countMarks;
    }


    public int[] getLifePoints() {
        return lifePoints;
    }

    public boolean isFullLife(){
        int i=0;
        for (;i < 12;){
            if (lifePoints[i] != 9) i++;
        }
        if (i==12) return true;
        else return false;
    }

    public int getNumberOfDamages() {       //necessary to know the progress status of life points independently by who made damages
        int damages = 0;
        for (int i=0; i<12; i++) {
            if (lifePoints[i] != 9)
                damages++;
        }
        return damages;
    }



}
