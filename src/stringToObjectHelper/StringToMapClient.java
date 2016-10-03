/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package stringToObjectHelper;

import Mapping.WeltElementMapping;
import Welt.NLFWelt;
import Welt.WorldBlocks;
import com.jme3.math.Vector3f;
import resourcen.Dreck;
import resourcen.Luft;
import resourcen.Wasser;
import resourcen.pflanzen.Gras;

/**
 *
 * @author jonas
 */
public class StringToMapClient {

    public static WorldBlocks blocks;

    public static void mapStringToServerpart(int viewDistance, int x, int y, int z, String map, WorldBlocks weltBloecke) {
        //add new serverPart if 
        String elementNumbersAsString[] = map.split(",");
        int elementNumbers[] = new int[elementNumbersAsString.length];
        for (int i = 0; i < elementNumbersAsString.length; i++) {
            elementNumbers[i] = Integer.parseInt(elementNumbersAsString[i]);
        }
        int currentElement = 0;
        for (int i = 0; i < 2 * viewDistance; i++) {
            for (int j = 0; j < 2 * viewDistance; j++) {
                for (int k = 0; k < 2 * viewDistance; k++) {
                    if (weltBloecke.weltElemente[i + x - viewDistance][j + y - viewDistance][k + z - viewDistance] == null) {
                        int weltElementID = WeltElementMapping.getWeltElementID(0, i + x - viewDistance, j + y - viewDistance, k + z - viewDistance);
                        //Vector3f position = WeltElementMapping.
                        switch (elementNumbers[currentElement]) {
                            case 0:
                                //spawn Spatial
                                weltBloecke.weltElemente[i + x - viewDistance][j + y - viewDistance][k + z - viewDistance] = new Wasser(new Vector3f(i + x - viewDistance, j + y - viewDistance, k + z - viewDistance), weltElementID);
                                break;
                            case 1:
                                weltBloecke.weltElemente[i + x - viewDistance][j + y - viewDistance][k + z - viewDistance] = new Dreck(new Vector3f(i + x - viewDistance, j + y - viewDistance, k + z - viewDistance), weltElementID);
                                break;
                            case 2:
                                weltBloecke.weltElemente[i + x - viewDistance][j + y - viewDistance][k + z - viewDistance] = new Luft(new Vector3f(i + x - viewDistance, j + y - viewDistance, k + z - viewDistance), weltElementID);
                                break;
                            case 5:
                                weltBloecke.weltElemente[i + x - viewDistance][j + y - viewDistance][k + z - viewDistance] = new Gras(new Vector3f(i + x - viewDistance, j + y - viewDistance, k + z - viewDistance), weltElementID);
                                break;
                        }
                    }
                    currentElement++;
                }
            }
        }


    }
}
