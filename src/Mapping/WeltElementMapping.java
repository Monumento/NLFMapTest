/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Mapping;

import Connection.ServerPart;
import Welt.NLFWelt;
import Welt.Welt;
import Welt.WeltElement;
import Welt.biome.Ebene;
import java.util.ArrayList;

/**
 *
 * @author jonas
 */
public class WeltElementMapping {

    public static ArrayList<WeltElement[][][]> mapElements;

    public static void initMapping() {
        int serverPartSize = NLFWelt.SERVERPART_SIZE;
        mapElements = new ArrayList<WeltElement[][][]>();
        //temporary, there is only testserverpart
        Welt temp = new Welt();
        Ebene.spawnBiom(temp);
        mapElements.add(temp.worldBlocks.weltElemente);

    }

    public static int getWeltElementID(int serverID, int x, int y, int z) {
        return mapElements.get(serverID)[x][y][z].weltElementID;
    }
}
