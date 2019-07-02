package server.RMIHandler;

import controller.MatchController;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.*;

/**
 * This class is the Server in the RMI implementation
 * It's purpose is to create a Registry on the specified port and bind the remote controller (ServerControllerRMI)
 * @see commons.InterfaceServerControllerRMI
 */
public class AdrenalineRMIServer implements Runnable{

    //private MatchController matchController; IL MATCHCONTROLLER NON SERVE CHE CE L'ABBIA QUESTA CLASSE
    private int port;
    private ServerControllerRMI remoteObjectRMI;
    public AdrenalineRMIServer(MatchController controller, int port) throws RemoteException {
       // matchController = controller;
        this.port = port;
        this.remoteObjectRMI = new ServerControllerRMI(controller);
    }

    /**
     * This runnable executes the class purpose, binding the remote object, created in the constructor.
     */
    public void run(){
        try {
            Registry registry = LocateRegistry.createRegistry(port);
            registry.bind("remoteController", remoteObjectRMI);
        }
        catch (Exception e){
            e.printStackTrace();
        }
        System.out.println("[RMIServer]: ready to receive remote method calls");

    }

}
