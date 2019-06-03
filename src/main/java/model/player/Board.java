package model.player;

import java.io.Serializable;
import java.util.ArrayList;     // NB. id = 9 non si può usare per i giocatori
import java.util.List;

public class Board  implements Serializable {
    private int[] lifePoints;
    private ArrayList<Integer> marks;



    public Board(){
        lifePoints = new int[12];
        marks = new ArrayList<>();
        for (int i=0; i<12; i++){
            lifePoints[i]=9;
        }
    }

    public void initializeBoard(){   // serve anche per quando muore un giocatore
        for (int i=0; i<12; i++){
            lifePoints[i]=9;
        }
    }

    public void updateLife(int damage, int idCurrentPlayer, int idTarget){
        if (idCurrentPlayer != idTarget) {
            for (int i = 0; i < 12; i++) {
                if (lifePoints[i] == 9) {
                    for (int k = 0; (k < damage) && ((i + k) < 12); k++) {
                        lifePoints[i + k] = idCurrentPlayer;
                    }
                    return;
                }
            }
        }
    }

    public void updateMarks(int damageMarks, int idCurrPlayer, int idTarget){
        if (idCurrPlayer != idTarget) {
            int countMarks=0;
            for (int i=0; i<marks.size(); i++){
                if (marks.get(i) == idCurrPlayer) countMarks++;
            }
            if (countMarks<3){
                // allora posso aggiungere dei taget
                if (countMarks+damageMarks>3){
                    // aggiungo la differenza tra il massimo(3) e quelli che già ho
                    for (int i=0; i<(3-countMarks); i++){
                        marks.add(marks.size(), idCurrPlayer);
                    }
                }
                else {
                    // aggiungo numero di marks pari al damageMarks
                    for (int i=0; i<damageMarks; i++){
                        marks.add(marks.size(), idCurrPlayer);
                    }

                }
            }
        }
    }

    public void removeMarks(int marksToBeRemoved, int idStrikerPlayer){
        int countMarks=0;
        for (int i = 0; (i<marks.size()) && (countMarks< marksToBeRemoved); i++){
            if (marks.get(i)== idStrikerPlayer) {
                marks.remove(i);
                countMarks++;
                i=i-1;
            }
        }
    }

    public List<Integer> getMarks() {
        return this.marks;
    }

    public int getSpecificMarks(int idStrikerPlayer) {
        int countMarks=0;
        for (int i=0; i<marks.size(); i++){
            if (marks.get(i) == idStrikerPlayer) countMarks++;
        }
        return countMarks;
    }

    public int[] getLifePoints() {
        return lifePoints;
    }

    public boolean isDead() {
        int damage=0;
        for (int i = 0; i < 12; i++){
            if (lifePoints[i] != 9) damage++;
        }
        if (damage>9) return true;
        else return false;
    }

    public boolean isOverKilled() {
        int damage=0;
        for (int i = 0; i < 12; i++){
            if (lifePoints[i] != 9) damage++;
        }
        return damage==11;
    }

    public int howManyHits(int id) {
        int damages = 0;
        for (int i=0; i<12; i++) {
            if (lifePoints[i] == id)
                damages++;
        }
        return damages;
    }

    public int getTotalNumberOfDamages() {       //necessary to know the progress status of life points independently by who made damages
        int damages = 0;
        for (int i=0; i<12; i++) {
            if (lifePoints[i] != 9)
                damages++;
        }
        return damages;
    }

    //for test and maybe for CLI
    public String toStringLP() {
        StringBuilder string = new StringBuilder();
        string.append("LifePoints: ");
        for (int i=0; i<12; i++) {
            if (lifePoints[i] == 9)
                string.append(" ");
            else
                string.append(lifePoints[i]);
            if (i!=11)
                string.append("-");
        }
        return string.toString();
    }

    //for test and maybe for CLI
    public String toStringMarks(){
        StringBuilder string = new StringBuilder();
        string.append("Marks: ");
        for (int i=0; i<marks.size(); i++) {
            string.append(marks.get(i));
            if (i!=marks.size()-1)
                string.append("-");
        }
        return string.toString();
    }
}
