/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Connection;

import Welt.biome.Ebene;
import java.util.ArrayList;

/**
 *
 * @author jonas
 */
public class ServerPartCluster {

    ArrayList<ServerPart> serverparts;

    public ServerPartCluster() {
        serverparts = new ArrayList<ServerPart>();
    }

    public void initNewPart(int serverPartID, String biom) {
        //mapping position bufferServer TODO
        ServerPart newPart = new ServerPart(serverPartID);
        if (biom.equalsIgnoreCase("Ebene")) {
            Ebene.spawnBiom(newPart.welt);
        }
        serverparts.add(newPart);
    }

    public String getMapAsString(int x, int y, int z, int viewDistance) {
        return serverparts.get(0).welt.worldBlocks.getMapAsString(x, y, z, viewDistance);
    }

    public String runServerParts() {
        String ret = "problems with part(s):";
        for (int i = 0; i < serverparts.size(); i++) {
            try {
                serverparts.get(i).runPart();
            } catch (Exception e) {
                ret += i + ",";
            }
        }
        return ret;
    }
}
