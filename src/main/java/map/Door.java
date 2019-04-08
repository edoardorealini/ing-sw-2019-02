package map;

public class Door { //a door is implemented as a couple of squares
                    //the couple represents the squares connected by the door
    private Square square1;
    private Square square2;

    public Door(Square sq1, Square sq2){
        this.square1 = sq1;
        this.square2 = sq2;
    }

    public Square getSquare1() {
        return square1;
    }

    public Square getSquare2() {
        return square2;
    }
    public Color getSquare1Color(){
        return square1.getColor();
    }

    public Color getSquare2Color(){
        return square2.getColor();
    }
}
