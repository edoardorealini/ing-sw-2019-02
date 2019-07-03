package model.player;

import java.io.Serializable;
import java.util.ArrayList;     // NB. id = 9 non si può usare per i giocatori
import java.util.List;

/**
 * This class represents the life board of the player
 */

public class Board  implements Serializable {
    private int[] lifePoints; // array life of the player
    private ArrayList<Integer> marks; // arrayList for marks
    private int[] points;   //points to be assigned after death
    private int numberOfDeaths; //to be updated


    /**
     * This is the constructor of the Life Board, it has an array that is initialized to 9
     * NB. never use 9 as player ID
     */
    public Board(){
        lifePoints = new int[12];

        for (int i = 0; i <= 11; i++){
            lifePoints[i]=9;
        }

        //todo rimuovere!!!

        for (int i=0; i < 10; i++){
            lifePoints[i]=2;
        }


        marks = new ArrayList<>();

        numberOfDeaths = 0;

        points = new int[6];
        points[0] = 8;
        points[1] = 6;
        points[2] = 4;
        points[3] = 2;
        points[4] = 1;
        points[5] = 1;
    }

    public void initializeBoard(){
        for (int i=0; i<12; i++){
            lifePoints[i]=9;
        }
    }

    /**
     * This method fills the lifePoints array with damage
     * @param damage quantifies the damage
     * @param idCurrentPlayer is the player ID who attacked
     * @param idTarget is the player ID who was injured
     */

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

    /**
     * This method fills the marks array with damage
     * @param damageMarks quantifies the damage
     * @param idCurrPlayer is the player ID who attacked
     * @param idTarget is the player ID who was injured
     */


    public void updateMarks(int damageMarks, int idCurrPlayer, int idTarget){
        if (idCurrPlayer != idTarget) {
            int countMarks=0;
            for (int i=0; i<marks.size(); i++){
                if (marks.get(i) == idCurrPlayer) countMarks++;
            }
            if (countMarks<3){
                if (countMarks+damageMarks>3){
                    for (int i=0; i<(3-countMarks); i++){
                        marks.add(marks.size(), idCurrPlayer);
                    }
                }
                else {
                    for (int i=0; i<damageMarks; i++){
                        marks.add(marks.size(), idCurrPlayer);
                    }

                }
            }
        }
    }

    /**
     * This method remove the marks from the arrayList
     * @param marksToBeRemoved is the number of marks to remove
     * @param idStrikerPlayer is the player ID who you want to remove
     */

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

    /**
     * This method returns the number of marks of a specific Player ID
     * @param idStrikerPlayer is the specific Player ID
     * @return the number of marks
     */

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

    /**
     * This method indicates if the player is overkilled
     * @return true if the player is overkilled
     */

    public boolean isOverKilled() {
        int damage=0;
        for (int i = 0; i < 12; i++){
            if (lifePoints[i] != 9) damage++;
        }
        return damage==12;
    }

    /**
     * This method return how many hits have been made by the player given in input
     * @param id of the player
     * @return number of hits
     */
    public int howManyHits(int id) {
        int damages = 0;
        for (int i=0; i<12; i++) {
            if (lifePoints[i] == id)
                damages++;
        }
        return damages;
    }

    /**
     * This method shows the progress status of life points independently by who made damages
     * @return the number of damage
     */

    public int getTotalNumberOfDamages() {
        int damages = 0;
        for (int i=0; i<12; i++) {
            if (lifePoints[i] != 9)
                damages++;
        }
        return damages;
    }

    public int getNumberOfDeaths() {
        return numberOfDeaths;
    }

    public void increaseNumberOfDeaths() {
        this.numberOfDeaths++;
    }

    public int[] getPoints() {
        return points;
    }

    public int whoMadeDamageBefore(List<Integer> arrayIDPlayer) {
        for (int damage : lifePoints) {
            if (arrayIDPlayer.contains(damage))
                return damage;
        }
        return -1;
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
