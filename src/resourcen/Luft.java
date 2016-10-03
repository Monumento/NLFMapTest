package resourcen;

import helpClasses.Vector2f;

import java.awt.Color;

import Welt.WeltElement;
import com.jme3.math.Vector3f;

public class Luft extends WeltElement {

    public Luft(Vector3f position, int weltElementID) {
        elementNumber = 2;
        positon = position;
        this.weltElementID = weltElementID;
    }
}
