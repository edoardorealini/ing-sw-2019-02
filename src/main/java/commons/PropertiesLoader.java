package commons;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * This class exposes the static methods used to load the properties from the given property file (adrenaline.properties)
 */

public class PropertiesLoader {

    /**
     * Gets the turn timer duration in milliseconds from the property file
     * @return returns an integer containing the timer duration in milliseconds
     */
    public static int getTurnTimerDuration(){
        int turnTimerDuration = 120000;
        try {
            //to load application's properties, we use this class
            Properties mainProperties = new Properties();
            FileInputStream file;
            //the base folder is ./, the root of the main.properties file
            String path = "./adrenaline.properties";
            //load the file handle for main.properties
            file = new FileInputStream(path);
            //load all the properties from this file
            mainProperties.load(file);
            //we have loaded the properties, so close the file handle
            file.close();
            //retrieve the property we are intrested, the app.version
            turnTimerDuration = Integer.parseInt(mainProperties.getProperty("turnDuration"));
        }catch (IOException e){
            //returning default value
            System.out.println("[PROPERTY LOADER]: File adrenaline.properties not found, going for it with the default values (turnTimer)");
            return turnTimerDuration;
        }
        System.out.println("[PROPERTY LOADER]: turnTimer loaded correctly from properties file, value: " + turnTimerDuration);
        return turnTimerDuration;
    }

    /**
     * Gets the lobby timer duration in milliseconds from the property file
     * @return returns an integer containing the lobby duration in milliseconds
     */
    public static int getLobbyTimerDuration(){
        int lobbyTimerDuration = 20000;
        try {
            Properties mainProperties = new Properties();
            FileInputStream file;
            String path = "./adrenaline.properties";
            file = new FileInputStream(path);
            mainProperties.load(file);
            file.close();
            lobbyTimerDuration = Integer.parseInt(mainProperties.getProperty("lobbyDuration"));
        }catch (IOException e){
            System.out.println("[PROPERTY LOADER]: File adrenaline.properties not found, going for it with the default values (lobbyTimer)");
            return lobbyTimerDuration;
        }
        System.out.println("[PROPERTY LOADER]: lobbyTimer loaded correctly from properties file, value: " + lobbyTimerDuration);
        return lobbyTimerDuration;
    }

    /**
     * Gets default RMI port from the property file
     * @return returns an integer containing the port
     */
    public static int getDefaultRMIPort(){
        int defaultRMIPort = 50000;
        try {
            Properties mainProperties = new Properties();
            FileInputStream file;
            String path = "./adrenaline.properties";
            file = new FileInputStream(path);
            mainProperties.load(file);
            file.close();
            defaultRMIPort = Integer.parseInt(mainProperties.getProperty("defaultRMIPort"));
        }catch (IOException e){
            System.out.println("[PROPERTY LOADER]: File adrenaline.properties not found, going for it with the default values (RMIPort)");
            //returning default value
            return defaultRMIPort;
        }
        System.out.println("[PROPERTY LOADER]: RMIPort loaded correctly from properties file, value: " + defaultRMIPort);
        return defaultRMIPort;
    }
}
