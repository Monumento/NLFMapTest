package Welt;

import com.jme3.math.Vector3f;
import combatSystem.UnitSpatialandGhostControl;
import helpClasses.Vector2f;

import java.awt.Color;
import lebewesen.IsAbleTo;

public class WeltElement  {
    //zum übertragen des ziels etc
    public int weltElementID;
    public int modelOrAnimation;
    public boolean hasUpdated;
    public Welt welt;
    public int height;
    public int x,y,z;
    public Color color;
    public int elementNumber;
    public UnitSpatialandGhostControl spacials;
    public IsAbleTo isAbleTo;
    public Vector3f positon;
    

    public Welt getWelt() {
        return welt;
    }

    public void setWelt(Welt welt) {
        this.welt = welt;
    }

   


    public void setColor(Color color) {
        this.color = color;
    }

    public Color getColor() {
        return this.color;
    }
}
