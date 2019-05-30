package controller.observer;
import controller.MatchController;

public abstract class Observer {
    protected MatchController subjectController;
    public abstract void update();
}
