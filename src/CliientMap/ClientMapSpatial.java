/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package CliientMap;

import Welt.WeltElement;
import com.jme3.bullet.control.RigidBodyControl;
import com.jme3.scene.Spatial;

/**
 *
 * @author jonas
 */
public class ClientMapSpatial {

    WeltElement part;
    Spatial partSpatial;
    RigidBodyControl partControl;

    public ClientMapSpatial(WeltElement part, Spatial partSpatial, RigidBodyControl partControl) {
        this.part = part;
        this.partControl = partControl;
        this.partSpatial = partSpatial;
    }
}
