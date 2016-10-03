/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Connection;

import java.util.ArrayList;

import Connection.TCPIP.SystemControl.SystemControlConnectionManager;

/**
 *
 * @author jonas
 */
public class SystemControl {

	public int systemControlID;

	SystemControlConnectionManager sysControlConnectionManager;

	public SystemControl(int systemControlID) {
		this.systemControlID = systemControlID;
		sysControlConnectionManager = new SystemControlConnectionManager();
	}

	public void startSystemControl() {
		sysControlConnectionManager.startSystemControl();
	}

	public void stopSystemControl() {

	}

	public void evaluateServers() {
		ServerInformation serverInformation = getServerInformation();
	}

	private ServerInformation getServerInformation() {
		return null;
	}

	public void updateToUserControl(String[] message) {
		sysControlConnectionManager.updateUserControl(message);
	}
	// get update from systemControl

	public String[] getUpdateFromUserControl() {
		return sysControlConnectionManager.getUpdateFromUserControl();
	}

	// get update from servers
	public String[] getUpdateFromServerControls() {
		return sysControlConnectionManager.getUpdateFromServers();
	}
	// send update to servers

	public void updateToServersServerControls(String[] message) {
		sysControlConnectionManager.updateToServers(message);
	}
}
