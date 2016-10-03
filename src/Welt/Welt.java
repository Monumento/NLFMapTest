package Welt;

import static Welt.NLFWelt.SERVERPART_SIZE;
import combatSystem.UnitSpatialandGhostControl;
import java.util.ArrayList;
import java.util.HashMap;
import resourcen.pflanzen.Pflanze;
import lebewesen.Lebewesen;

public class Welt {

    public WorldBlocks worldBlocks;
    //sortieren nach anzahl der zugriffe oder vermutete zugriffe
    //Objekts; arraylist of arraylist vorsortiert damit man findet was man sucht
    //vllt alle weltelemnte schon einzeln damit nurnoch durchsuche wasserblöcke nach nächstem erreichbarem 
    public HashMap<Integer, ArrayList<Lebewesen>> einheiten;
    public ArrayList<Lebewesen> leichen;
    public ArrayList<Pflanze> pflanzen;
    public int zeit;
    int countLimit;

    public Welt() {
        zeit = 0;
        worldBlocks = new WorldBlocks();
        einheiten = new HashMap<Integer, ArrayList<Lebewesen>>();
        leichen = new ArrayList<Lebewesen>();
        pflanzen = new ArrayList<Pflanze>();
    }

    public void runWorld() {
        for (int i = 0; i < einheiten.size(); i++) {
            for (int j = 0; j < einheiten.get(i).size(); j++) {
                einheiten.get(i).get(j).macheEtwas();
            }

        }
        for (int i = 0; i < pflanzen.size(); i++) {
            pflanzen.get(i).macheEtwas();
        }
    }

    public int getCountLimit() {
        return countLimit;
    }

    public void setCountLimit(int countLimit) {
        this.countLimit = countLimit;
    }

    public int getZeit() {
        return zeit;
    }

    public void setZeit(int zeit) {
        this.zeit = zeit;
    }
}
