package resourcen.pflanzen;

import helpClasses.Vector2f;

import java.awt.Color;

import Welt.WeltElement;
import com.jme3.math.Vector3f;

public class Gras extends WeltElement {

    public Gras(Vector3f position, int weltElementID) {
        elementNumber = 5;
        positon = position;
        this.weltElementID = weltElementID;
    }
}
