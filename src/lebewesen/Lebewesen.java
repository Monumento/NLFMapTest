package lebewesen;

import helpClasses.HilfsKlassen;
import helpClasses.Vector2f;




import lebewesen.tiere.Kuh;
import beduerfnisse.tiere.GrundBeduerfnisse;
import beduerfnisse.tiere.ZuLetztGetan;
import Welt.Welt;
import Welt.WeltElement;
import com.jme3.bullet.collision.shapes.CollisionShape;
import com.jme3.bullet.control.GhostControl;
import com.jme3.math.Vector3f;

import java.util.HashMap;
import navigation.Path;

public class Lebewesen extends WeltElement implements GrundBeduerfnisse {
    // TODO prioritaeten fuer beduerfnisse als priority array zur evolution

    public Welt welt;
    // Base Things
    // items
    private ZuLetztGetan zuLetztGetan = ZuLetztGetan.nichts;
    // Verhalten
    //CombatSystem and other
    GhostControl body;
    CollisionShape weapon;
    //new Verhalten TODO
    public int layer;
    public Path path;
    public WeltElement ziel;
    public HashMap<String, Double> attributesDouble;
    public HashMap<String, Integer> attributesInteger;
    public HashMap<String, Boolean> attributesBoolean;
    public HashMap<String, Float> attributesFloat;

    public void moveToTarget() {
        if (path.isEmpty()) {
            path = welt.worldBlocks.getPathFromTO(this, ziel);
        }
        float distanceToElement = positon.distance(path.getStep(0).positon);
        Vector3f directionOfElement = path.getStep(0).positon.subtract(positon).normalize().mult(attributesFloat.get("speed"));
        if (distanceToElement < directionOfElement.length()) {
            positon.add(directionOfElement);
        } else {
            positon.set(path.removeNextStep().positon);
        }
    }
    
   

    public Lebewesen(Welt welt, double alter, double health) {
        path = new Path();
        ziel = null;
        attributesDouble = new HashMap<String, Double>();
        attributesInteger = new HashMap<String, Integer>();
        attributesFloat = new HashMap<String, Float>();
        attributesBoolean = new HashMap<String, Boolean>();

        attributesDouble.put("alter", alter);
        attributesDouble.put("health", health);
        attributesInteger.put("anzahlKinder", 0);

        this.welt = welt;
    }

    // && HilfsKlassen.betrag(
    // getPosition().addieren(richtung.multiplizieren(i)), 1)
    // - HilfsKlassen.betrag(ziel.getPosition(), 1) > 1
    public boolean macheEtwas() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public boolean sucheNahrung() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public boolean suchePartner() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public boolean sucheVersteck() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public boolean sucheToilette() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public boolean sucheWasser() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public boolean trinken(WeltElement trinkeAus) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public boolean essen(WeltElement esseVon) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public boolean pinkeln(WeltElement pinkleAuf) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public boolean geschlVerkehr(Lebewesen verkehrMit) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public boolean schlafe() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
