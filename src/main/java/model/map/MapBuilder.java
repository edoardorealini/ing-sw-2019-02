package model.map;

import com.google.gson.Gson;
import java.io.File;
import java.io.*;

public class MapBuilder implements Serializable{
    //Classe che serve per costruire le mappe.

    private Map map;
    private int mapID;
    private Gson json;

    /*
        declaration of all the paths to the json files containing the map information
    */

    private String map1Path = "." + File.separatorChar + "src" + File.separatorChar + "main"
            + File.separatorChar + "resources" + File.separatorChar + "maps"+ File.separatorChar + "map1.json";

    private String map2Path = "." + File.separatorChar + "src" + File.separatorChar + "main"
            + File.separatorChar + "resources" + File.separatorChar + "maps"+ File.separatorChar + "map2.json";

    private String map3Path = "." + File.separatorChar + "src" + File.separatorChar + "main"
            + File.separatorChar + "resources" + File.separatorChar + "maps"+ File.separatorChar + "map3.json";

    private String map4Path = "." + File.separatorChar + "src" + File.separatorChar + "main"
            + File.separatorChar + "resources" + File.separatorChar + "maps"+ File.separatorChar + "map4.json";


    public MapBuilder() {
        map = new Map();
        json = new Gson();
    }

    /*
        makeMap builds the correct map from json according to the mapID index given in input.
     */
    public Map makeMap(int mapID) throws  IOException{
        switch (mapID){
            case 1:
                BufferedReader br1 = new BufferedReader(new FileReader(map1Path));
                map = json.fromJson(br1, Map.class);
                break;
            case 2:
                BufferedReader br2 = new BufferedReader(new FileReader(map2Path));
                map = json.fromJson(br2, Map.class);
                break;
            case 3:
                BufferedReader br3 = new BufferedReader(new FileReader(map3Path));
                map = json.fromJson(br3, Map.class);
                break;
            case 4:
                BufferedReader br4 = new BufferedReader(new FileReader(map4Path));
                map = json.fromJson(br4, Map.class);
                break;
        }

        return map;
    }

    public String getMap1Path() {
        return map1Path;
    }

    public String getMap2Path() {
        return map2Path;
    }
}