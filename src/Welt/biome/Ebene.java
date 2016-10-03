package Welt.biome;

import Mapping.WeltElementIDs;
import Welt.NLFWelt;
import lebewesen.Lebewesen;
import lebewesen.tiere.Kuh;
import lebewesen.tiere.Wolf;
import helpClasses.Vector2f;
import resourcen.Wasser;
import resourcen.pflanzen.Baum;
import resourcen.pflanzen.Gras;
import resourcen.pflanzen.Pflanze;
import Welt.Welt;
import Welt.WeltElement;
import com.jme3.math.Vector3f;
import humanoide.meineRasse.MeineRasseBauer;
import java.util.ArrayList;
import resourcen.Dreck;
import resourcen.Luft;

public class Ebene {

    public static Welt welt;

    private static Pflanze spawnPlants(Vector2f position) {
        Pflanze spawn = null;
        int i = (int) (Math.random() * 100);
        if (i < 10) {
            spawn = new Baum(100, position);
        }
        return spawn;
    }

    private static Lebewesen spawnUnit(Vector2f position) {
        Lebewesen spawn = null;
        int i = (int) (Math.random() * 100);
        if (i < 1000) {
            spawn = new Kuh(welt, 0, 100, position);
        } else if (i > 50 && i < 70) {
            spawn = new Wolf(welt, 0, 100, position);
        } else if (i > 70 && i < 90) {
            spawn = new MeineRasseBauer(welt, 0, 100, position);
        }
        return spawn;
    }

    private static WeltElement spawnSmthn(Vector3f position) {
        // Boden
        WeltElement something = new WeltElement();
        int i = (int) (Math.random() * 100);
        if (i < 70) {
            something = new Gras(position, WeltElementIDs.weltelementID++);
        } else if (i < 95) {
            something = new Dreck(position, WeltElementIDs.weltelementID++);
        } else {
            something = new Wasser(position, WeltElementIDs.weltelementID++);
        }

        return something;
    }

    public static void spawnBiom(Welt welt) {
        Ebene.welt = welt;
        // gorund
        for (int i = 0; i < NLFWelt.SERVERPART_SIZE; i++) {//x
            for (int j = 0; j < NLFWelt.SERVERPART_SIZE; j++) {//y
                for (int k = 0; k < NLFWelt.SERVERPART_SIZE; k++) {//z
                    if (j <= 50) {
                        welt.worldBlocks.weltElemente[i][j][k] = spawnSmthn(new Vector3f(i,
                                j, k));
                    } else {
                        welt.worldBlocks.weltElemente[i][j][k] = new Luft(new Vector3f(i,
                                j, k), WeltElementIDs.weltelementID++);
                    }
                    if (!welt.worldBlocks.sortedElements.containsKey(welt.worldBlocks.weltElemente[i][j][k].elementNumber)) {
                        welt.worldBlocks.sortedElements.put(welt.worldBlocks.weltElemente[i][j][k].elementNumber, new ArrayList<WeltElement>());
                    }
                    welt.worldBlocks.sortedElements.get(welt.worldBlocks.weltElemente[i][j][k].elementNumber).add(welt.worldBlocks.weltElemente[i][j][k]);
                }
            }
        }
        // plants (trees atm)
        Pflanze temp = spawnPlants(new Vector2f(50, 50));
        int numberInARow = 0;
        for (int i = 0; i < NLFWelt.SERVERPART_SIZE; i++) {
            for (int j = 0; j < NLFWelt.SERVERPART_SIZE; j++) {
                if (temp != null && numberInARow < 5) {
                    numberInARow++;

                    temp.setPosition(new Vector2f(i * 50 - numberInARow
                            * (int) (Math.random() * 5), j * 50 - numberInARow
                            * (int) (Math.random() * 5)));
                    welt.pflanzen.add(temp);
                } else if (numberInARow >= 5 || temp == null) {
                    temp = spawnPlants(new Vector2f(0, 0));
                    numberInARow = 0;
                }
            }
        }
        // Animals
        for (int i = 1; i < NLFWelt.SERVERPART_SIZE - 1; i++) {
            for (int j = 1; j < NLFWelt.SERVERPART_SIZE - 1; j++) {
                Lebewesen s = spawnUnit(new Vector2f(i * 25 + 50,
                        j * 25 + 50));
                if (!welt.einheiten.containsKey(s.elementNumber)) {
                    welt.einheiten.put(s.elementNumber, new ArrayList<Lebewesen>());
                }
                welt.worldBlocks.sortedElements.get(s.elementNumber).add(s);
            }
        }

    }
}
