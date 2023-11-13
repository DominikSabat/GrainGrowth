import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashMap;

public class DataManager {

    BufferedImage img;

    int startStop=0;

    int choiceBoundary;
    int choiceNeighbor;

    int radiusNeighbor;

    ArrayList<Nucleon> nucleons;

    Nucleon[][] nucleonLast;
    Nucleon[][] nucleonCurrent;

    //==================Import===========
    int choiceImport;

    HashMap<Integer, Color> colors = new HashMap<>();

    HashMap<Color, Integer> identificators = new HashMap<>();

    //==================Import===========

    //==================Inclusions===========

    int choiceInclusion;

    //==================Inclusions===========


    int width;
    int height;

    public DataManager() {
        nucleonLast =new Nucleon[width][height];
        nucleonCurrent =new Nucleon[width][height];
        nucleons = new ArrayList<Nucleon>();
    }
}