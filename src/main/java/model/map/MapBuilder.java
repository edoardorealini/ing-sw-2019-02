package model.map;

import com.google.gson.Gson;
import java.io.File;
import java.io.*;

public class MapBuilder {
    //TODO Classe che serve per costruire le mappe.

    private Map map;
    private int mapID;
    private Gson json;

    /*
        declaration of all the paths to the json files containing the map information
    */

    private String map1Path = "." + File.separatorChar + "src" + File.separatorChar + "main"
            + File.separatorChar + "resources" + File.separatorChar + "map1.json";

    private String map2Path = "." + File.separatorChar + "src" + File.separatorChar + "main"
            + File.separatorChar + "resources" + File.separatorChar + "map2.json";

    private String map3Path = "." + File.separatorChar + "src" + File.separatorChar + "main"
            + File.separatorChar + "resources" + File.separatorChar + "map3.json";

    private String map4Path = "." + File.separatorChar + "src" + File.separatorChar + "main"
            + File.separatorChar + "resources" + File.separatorChar + "map4.json";


    public MapBuilder() {
        map = new Map();
        json = new Gson();
    }

    /*
        makeMap builds the correct map from json according to the mapID index given in input.
     */
    public Map makeMap(int mapID) throws IOException{
        map.setMapID(mapID);
        switch (mapID){
            case 1:
                try {
                    BufferedReader br = new BufferedReader(new FileReader(map1Path));
                    map = json.fromJson(br, Map.class);
                }catch(IOException e) {
                    e.printStackTrace();
                }
                break;
            case 2:
                try {
                    BufferedReader br = new BufferedReader(new FileReader(map2Path));
                    map = json.fromJson(br, Map.class);
                }catch(IOException e) {
                    e.printStackTrace();
                }                break;
            case 3:
                try {
                    BufferedReader br1 = new BufferedReader(new FileReader(map3Path));
                    map = json.fromJson(br1, Map.class);
                }catch(IOException e) {
                    e.printStackTrace();
                }                break;
            case 4:
                try {
                    BufferedReader br = new BufferedReader(new FileReader(map4Path));
                    map = json.fromJson(br, Map.class);
                }catch(IOException e) {
                    e.printStackTrace();
                }                break;
        }

        return map;
    }
}