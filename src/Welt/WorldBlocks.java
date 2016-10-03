/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Welt;

import com.jme3.math.Vector3f;
import java.util.ArrayList;
import java.util.HashMap;
import lebewesen.IsAbleTo;
import lebewesen.Lebewesen;
import navigation.Path;

/**
 *
 * @author jonas
 */
public class WorldBlocks {
// getNext auf anderem server aufgerufen gibt position des elements falls vorhanden
    //

    public WeltElement[][][] weltElemente;
    public HashMap<Integer, ArrayList<WeltElement>> sortedElements;

    public WorldBlocks() {
        weltElemente = new WeltElement[NLFWelt.SERVERPART_SIZE][NLFWelt.SERVERPART_SIZE][NLFWelt.SERVERPART_SIZE];
        sortedElements = new HashMap<Integer, ArrayList<WeltElement>>();
        //weltElemente = (ArrayList<WeltElement>[][]) new ArrayList[NLFWelt.SERVERPART_SIZE][NLFWelt.SERVERPART_SIZE];
    }

    public String getMapAsString(int x, int y, int z, int viewDistance) {
        //map,ServerpartID:part,part,...
        String map = "map,1:";
        int elementNumber = 0;
        if (x >= viewDistance && x < NLFWelt.SERVERPART_SIZE - viewDistance) {
            if (z >= viewDistance && z < NLFWelt.SERVERPART_SIZE - viewDistance) {
                if (y >= viewDistance && y < NLFWelt.SERVERPART_SIZE - viewDistance) {
                    for (int i = 0; i < 2 * viewDistance; i++) {
                        for (int j = 0; j < 2 * viewDistance; j++) {
                            for (int k = 0; k < 2 * viewDistance; k++) {
                                elementNumber = weltElemente[(x - viewDistance + i)][(y - viewDistance + j)][(z - viewDistance + k)].elementNumber;
                                map += elementNumber + ",";
                            }
                        }
                    }
                }
            }
        }
        return map;
    }

    public WeltElement getNext(int elementNumber, Vector3f position) {
        //TODO getNext fom other servers if needed
        sortedElements.containsKey(elementNumber);
        ArrayList<WeltElement> allOfKind = sortedElements.get(elementNumber);
        WeltElement ret = null;
        float shotestDistance = 1000000;
        for (WeltElement element : allOfKind) {
            if (element.positon.distance(position) < shotestDistance) {
                shotestDistance = element.positon.distance(position);
                ret = element;
            }
        }
        return ret;
    }

    public Path getPathFromTO(Lebewesen lebewesen, WeltElement target) {
        Path path = new Path();
        int x = (int) lebewesen.positon.x, y = (int) lebewesen.positon.y, z = (int) lebewesen.positon.z;
        WeltElement currentPos = weltElemente[(int) lebewesen.positon.x][(int) lebewesen.positon.y][(int) lebewesen.positon.z];
        WeltElement nextPos;


        Boolean found = false;
        WeltElement element;

        while (!found) {
            element = null;
            for (int withY = 0; element == null && withY < 2; withY++) {
                element = checkX(lebewesen.height, currentPos, target, withY);
                if (element != null) {
                    path.addStep(element);
                    currentPos = path.getStep(path.path.size() - 1);
                }
                element = checkZ(lebewesen.height, currentPos, target, withY);
                if (element != null) {
                    path.addStep(element);
                    currentPos = path.getStep(path.path.size() - 1);
                }
            }
            if (path.getStep(path.path.size() - 1).equals(target)) {
                found = true;
            }
        }
        return path;

    }

    public boolean canIStandOn(int size, WeltElement element) {
        boolean ret = true;
        for (int i = 0; i < size; i++) {
            if (weltElemente[element.x][element.y + i][element.z].elementNumber != 2) {
                ret = false;
            }
        }
        return ret;
    }
//can i standOn and can i goTO

    private WeltElement checkX(int height, WeltElement currentPos, WeltElement target, int withY) {
        int x = (int) currentPos.positon.x, z = (int) currentPos.positon.z;
        WeltElement ret = null;
        float distanceToTarget = currentPos.positon.distance(target.positon);
        WeltElement nextPos = weltElemente[x + 1][withY][z];
        float distanceNextPosToTarget = nextPos.positon.distance(target.positon);
        if (canIStandOn(height, nextPos) && distanceNextPosToTarget <= distanceToTarget) {
            do {
                x++;
                ret = nextPos;
                currentPos = nextPos;
                distanceToTarget = currentPos.positon.distance(target.positon);
                nextPos = weltElemente[x + 1][withY][z];
                distanceNextPosToTarget = nextPos.positon.distance(target.positon);
            } while (distanceNextPosToTarget <= distanceToTarget);
        }
        distanceToTarget = currentPos.positon.distance(target.positon);
        nextPos = weltElemente[x - 1][withY][z];
        distanceNextPosToTarget = nextPos.positon.distance(target.positon);
        if (canIStandOn(height, nextPos) && distanceNextPosToTarget <= distanceToTarget) {
            do {
                x--;
                ret = nextPos;
                currentPos = nextPos;
                distanceToTarget = currentPos.positon.distance(target.positon);
                nextPos = weltElemente[x - 1][withY][z];
                distanceNextPosToTarget = nextPos.positon.distance(target.positon);
            } while (distanceNextPosToTarget <= distanceToTarget);
        }
        return ret;
    }

    private WeltElement checkZ(int height, WeltElement currentPos, WeltElement target, int withY) {
        int x = (int) currentPos.positon.x, z = (int) currentPos.positon.z;
        WeltElement ret = null;
        float distanceToTarget = currentPos.positon.distance(target.positon);
        WeltElement nextPos = weltElemente[x][withY][z + 1];
        float distanceNextPosToTarget = nextPos.positon.distance(target.positon);
        if (canIStandOn(height, nextPos) && distanceNextPosToTarget <= distanceToTarget) {
            do {
                z++;
                ret = nextPos;
                currentPos = nextPos;
                distanceToTarget = currentPos.positon.distance(target.positon);
                nextPos = weltElemente[x][withY][z + 1];
                distanceNextPosToTarget = nextPos.positon.distance(target.positon);
            } while (distanceNextPosToTarget <= distanceToTarget);
        }
        distanceToTarget = currentPos.positon.distance(target.positon);
        nextPos = weltElemente[x][withY][z - 1];
        distanceNextPosToTarget = nextPos.positon.distance(target.positon);
        if (canIStandOn(height, nextPos) && distanceNextPosToTarget <= distanceToTarget) {
            do {
                z--;
                ret = nextPos;
                currentPos = nextPos;
                distanceToTarget = currentPos.positon.distance(target.positon);
                nextPos = weltElemente[x][withY][z - 1];
                distanceNextPosToTarget = nextPos.positon.distance(target.positon);
            } while (distanceNextPosToTarget <= distanceToTarget);
        }
        return ret;
    }
}
