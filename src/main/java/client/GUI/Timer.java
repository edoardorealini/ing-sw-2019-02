package client.GUI;

public class Timer {
    private FirstPage firstPage;
    static Thread thread = new Thread();

    public static void main(String[] args) throws InterruptedException{
        for (int i=15; i>0;i--){
            thread.sleep(1000);
        }
    }

    public Timer (FirstPage firstPage){
        this.firstPage=firstPage;
    }

}
