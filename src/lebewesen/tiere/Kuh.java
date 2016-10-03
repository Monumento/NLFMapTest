package lebewesen.tiere;

import java.awt.Color;

import resourcen.Dreck;
import resourcen.Luft;
import helpClasses.HilfsKlassen;
import helpClasses.Vector2f;
import beduerfnisse.tiere.GrundBeduerfnisse;
import beduerfnisse.tiere.ZuLetztGetan;
import Welt.Welt;
import Welt.WeltElement;
import lebewesen.Lebewesen;

public class Kuh extends Lebewesen implements GrundBeduerfnisse {

    public Kuh(Welt welt, int alter, int health, Vector2f position) {
        //arrrayList mit attributen
        super(welt, alter, health);



    }

    public static Kuh ladeKuh(Welt welt, int alter, int anzahlKinder,
            int drohendeGefahr, int durst, int elementnumber,
            int essGeschwindigkeit, int geschlecht, int harndrang, int health,
            int hunger, int maxAlter, int maxKinder, int maxZeitSchwanger,
            int muedigkeit, int paarungsDrang, int schwangerZeit,
            int sichtWeite, int size, int speed, int trinkGeschwindigkeit,
            int wasserAnteil, Vector2f position, boolean schwanger) {
        Kuh ret = new Kuh(welt, alter, health, position);


        return ret;
    }

    public boolean partnerSuche() {
        // TODO Auto-generated method stub
        return false;
    }

    public boolean sucheToilette() {

        return true;
    }

    public boolean macheEtwas() {
        // System.out.print("mache");
        // update method
        if (ziel == null) {
            ziel = welt.worldBlocks.getNext(0, positon);
        }
        moveToTarget();
        return true;
    }

    public boolean sucheWasser() {
        return true;
    }

    public boolean sucheNahrung() {
        return true;

    }

    public boolean suchePartner() {
        return true;
    }

    public boolean sucheVersteck() {
        return true;
    }

    public boolean trinken(WeltElement trinkeVon) {
        return true;
    }

    public boolean essen(WeltElement esseVon) {
        return true;
    }

    public boolean pinkeln(WeltElement pinkleAuf) {
        return true;
    }

    public boolean schlafe() {
        return true;
    }
}
