package model.map;

import com.google.gson.Gson;
import java.io.File;
import java.io.*;

/**
 *The MapBuilder class has only one method used to build the correct map given an integer identifier in input.
 * The maps are built from specified json files containing all the information for the single squares in each map.
 */
public class MapBuilder implements Serializable{
    //Classe che serve per costruire le mappe.

    private Map map;
    private int mapID;
    private Gson json;

    /*
        declaration of all the paths to the json files containing the map information
    */
    private String map1Path = "." + File.separatorChar + "src" + File.separatorChar + "main"
            + File.separatorChar + "resources" + File.separatorChar + "maps" + File.separatorChar + "map1.json";

    private String map2Path = "." + File.separatorChar + "src" + File.separatorChar + "main"
            + File.separatorChar + "resources" + File.separatorChar + "maps" + File.separatorChar + "map2.json";

    private String map3Path = "." + File.separatorChar + "src" + File.separatorChar + "main"
            + File.separatorChar + "resources" + File.separatorChar + "maps" + File.separatorChar + "map3.json";

    private String map4Path = "." + File.separatorChar + "src" + File.separatorChar + "main"
            + File.separatorChar + "resources" + File.separatorChar + "maps" + File.separatorChar + "map4.json";

    /**
     * Default constructor, builds the json GSON parser and and empty Map object
     * @see Map
     * @see Gson
     */
    public MapBuilder() {
        map = new Map();
        json = new Gson();
    }

    /**
     * makeMap builds the correct map from json according to the mapID index given in input.
     * @param mapID integer that specifies the map to build
     * @return  returns the map correctly built
     * @throws IOException
     */
    public Map makeMap(int mapID) throws  IOException{
        switch (mapID){
            case 1:
                BufferedReader br1 = new BufferedReader(new InputStreamReader(MapBuilder.class.getResourceAsStream(File.separatorChar + "maps" + File.separatorChar + "map1.json")));
                map = json.fromJson(br1, Map.class);
                break;
            case 2:
                BufferedReader br2 = new BufferedReader(new InputStreamReader(MapBuilder.class.getResourceAsStream(File.separatorChar + "maps" + File.separatorChar + "map2.json")));
                map = json.fromJson(br2, Map.class);
                break;
            case 3:
                BufferedReader br3 = new BufferedReader(new InputStreamReader(MapBuilder.class.getResourceAsStream(File.separatorChar + "maps" + File.separatorChar + "map3.json")));
                map = json.fromJson(br3, Map.class);
                break;
            case 4:
                BufferedReader br4 = new BufferedReader(new InputStreamReader(MapBuilder.class.getResourceAsStream(File.separatorChar + "maps" + File.separatorChar + "map4.json")));
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
