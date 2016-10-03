/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Connection;

import Connection.TCPIP.ServerControl.ServerControlConnectionManager;

/**
 *
 * @author jonas
 */
public class ServerControl {
	// und Client? alle daten an zentrale die wertet aus
	// rmi oder jms (jms test mit variable die erhöht wird und mehreren servern
	// in eclipse

	public int serverControlID;

	public ServerControlConnectionManager serverControlConnectionManager;

	public ServerControl(int serverControlID) {
		this.serverControlID = serverControlID;
		serverControlConnectionManager = new ServerControlConnectionManager();
	}

	public void startServerControl() {
		serverControlConnectionManager.startServerControl();
	}

	public void updateToSystemControl(String[] message) {
		serverControlConnectionManager.updateSystemControl(message);
	}
	// get update from systemControl

	public String[] getUpdateFromSystemControl() {
		return serverControlConnectionManager.getUpdateFromSystemControl();
	}

	// get update to servers
	public String[] getUpdateFromServers() {
		return serverControlConnectionManager.getUpdateFromServers();
	}
	// send update to servers

	public void updateToServers(String[] message) {
		serverControlConnectionManager.updateToServers(message);
	}

	public boolean startNewConnectionToSystemControl(String ip, int port) {
		return serverControlConnectionManager.systemControlPool.startNewConnection(ip, port);
	}

}
