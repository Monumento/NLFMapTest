/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package navigation;

import Welt.WeltElement;
import com.jme3.math.Vector3f;
import java.util.ArrayList;

/**
 *
 * @author jonas
 */
public class Path {

    public ArrayList<WeltElement> path;

    public Path() {
        path = new ArrayList<WeltElement>();
    }

    public void addStep(WeltElement step) {
        path.add(step);
    }

    public boolean isEmpty() {
        return path.isEmpty();
    }

    public int size() {
        return path.size();
    }

    public WeltElement removeNextStep() {
        return path.remove(0);
    }

    public WeltElement getStep(int step) {
        return path.get(step);
    }
}
