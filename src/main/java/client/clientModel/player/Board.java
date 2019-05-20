package client.clientModel.player;

import java.util.ArrayList;
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

    public void updateLife(int damage, int idPlayerAttaccante){
        for (int i=0; i<12; i++){
            if (lifePoints[i]==9){
                for (int k=0; (k<damage) && ((i+k) <12) ; k++){
                    lifePoints[i+k] = idPlayerAttaccante;
                }
                return;
            }
        }
    }

    //TODO mettete un po di commenti se no non si capisce un cazzo di cosa fanno i metodi

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

    public boolean isDead(){
        int damage=0;
        for (int i=0;i < 12;i++){
            if (lifePoints[i] != 9) damage++;
        }
        if (damage>9) return true;
        else return false;
    }

    public boolean isOverKilled(){
        int damage=0;
        for (int i=0;i < 12;i++){
            if (lifePoints[i] != 9) damage++;
        }
        if (damage==11) return true;
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

    //for test and maybe for CLI
    public String toStringLP() {
        StringBuilder string = new StringBuilder();
        string.append("LifePoints: ");
        for (int i=0; i<12; i++) {
            string.append(lifePoints[i]);
            if (i!=11)
                string.append("-");
        }
        return string.toString();
    }

    //for test and mayeb for CLI
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
