package resourcen;

import helpClasses.Vector2f;

import java.awt.Color;

import Welt.WeltElement;
import com.jme3.math.Vector3f;

public class Dreck extends WeltElement {

    public Dreck(Vector3f position, int weltElementID) {
        elementNumber = 1;
        positon = position;
        this.weltElementID = weltElementID;
    }
}
