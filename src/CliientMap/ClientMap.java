/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package CliientMap;

import Connection.ServerPart;
import Welt.NLFWelt;
import com.jme3.math.Vector3f;
import java.util.ArrayList;
import mygame.MainClientScript;
import stringToObjectHelper.StringToMapClient;

/**
 *
 * @author jonas
 */
public class ClientMap {

    public ServerPart[] map;
    ArrayList<Vector3f> playerPositions;
    private MainClientScript mainClient;
    public int viewDistance;

    public void setMainCLient(MainClientScript client) {
        this.mainClient = client;
    }

    public ClientMap(int size) {
        map = new ServerPart[size];
    }

    public void addPlayerPosition(Vector3f position) {
        if (playerPositions.size() <= 100000) {
            playerPositions.add(new Vector3f((int) position.x, (int) position.y, (int) position.z));
        }
    }

    public ClientMap() {
        map = new ServerPart[8];
        map[0] = new ServerPart(1);
        viewDistance = 10;
        playerPositions = new ArrayList<Vector3f>();
    }

    public void displayParts() {
        int x, y, z;
        x = (int) playerPositions.get(0).x;
        y = (int) playerPositions.get(0).y;
        z = (int) playerPositions.get(0).z;
        playerPositions.remove(0);
        for (int i = 0; i < 2 * viewDistance; i++) {
            for (int j = 0; j < 2 * viewDistance; j++) {
                for (int k = 0; k < 2 * viewDistance; k++) {
                    mainClient.displayPart(i + x - viewDistance, j + y - viewDistance, z + k - viewDistance, map[0].welt.worldBlocks.weltElemente[i + x - viewDistance][j + y - viewDistance][k + z - viewDistance].elementNumber);
                }
            }
        }
    }

    public void updateMap(String map) {
        int x, y, z;
        x = (int) playerPositions.get(0).x;
        y = (int) playerPositions.get(0).y;
        z = (int) playerPositions.get(0).z;
        StringToMapClient.mapStringToServerpart(viewDistance, x, y, z, map, this.map[0].welt.worldBlocks);

    }
}
