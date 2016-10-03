package Connection;

import Welt.NLFWelt;
import Welt.Welt;
import Welt.biome.Ebene;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author jonas
 */
public class ServerPart implements TcpIpObjInterface {

    public int serverPartID;
    public Welt welt;
    //bufferedObjuekts falls jemand auf kante steht oder 

    public ServerPart(int serverPartID){
        welt = new Welt();
    }
    
    public void spawnEbene(){
        Ebene.spawnBiom(welt);
    }
    
    public String ObjToString() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public boolean ObjFromString() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void runPart() {
       welt.runWorld();
    }
}
