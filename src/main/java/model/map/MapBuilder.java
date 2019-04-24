package model.map;

import com.google.gson.Gson;
import java.io.File;

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


    public MapBuilder(){
        map = new Map();
        json = new Gson();
    }

    /*
        makeMap builds the correct map from json according to the mapID index given in input.
     */
    public Map makeMap(int mapID){
        map.setMapID(mapID);
        switch (mapID){
            case 1:
                map = json.fromJson(map1Path, Map.class);
                break;
            case 2:
                map = json.fromJson(map2Path, Map.class);
                break;
            case 3:
                map = json.fromJson(map3Path, Map.class);
                break;
            case 4:
                map = json.fromJson(map4Path, Map.class);
                break;
        }

        return map;
    }
}
