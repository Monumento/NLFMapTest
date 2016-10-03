/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Connection;

import Connection.TCPIP.Server.ServerConnectionManager;
import java.util.ArrayList;

/**
 *
 * @author jonas
 */
public class Server {
	// things to send first;
	public int serverID;

	public ServerPartCluster serverPartCluster;
	// clients and serverControl
	public ServerConnectionManager serverConnectionManager;

	public Server(int serverID, String ip, int port) {
		this.serverID = serverID;
		serverPartCluster = new ServerPartCluster();
		serverConnectionManager = new ServerConnectionManager(ip, port);
	}

	// public void sendAction(Action action)//explosion, Lebewesen suche anderes
	// Lebewesen, etc.
	// public Action getAction
	public void startServer() {
		serverConnectionManager.startServer(); // throws new
												// NoServerControlException
	}

	public boolean sendServerPart(int partNr) {

		return true;
	}

	public boolean hasUpdated() {
		return true;
	}

	public void initNewServerPart(int serverPartID, String biom) {
		// parameter is serverpart ID
		serverPartCluster.initNewPart(serverPartID, biom);
	}

	public String runServerParts() {
		return serverPartCluster.runServerParts();
	}

	public boolean sendServerPartAndDelete(int partNr) {

		return true;
	}

	public void updateToServerControl(String[] message) {
		serverConnectionManager.updateServerControl(message);
	}

	public String[] getUpdateFromServerControl() {
		return serverConnectionManager.getUpdateFromServerControl();
	}

	public void updateToClients(String[] message) {
		serverConnectionManager.updateToClients(message);
	}

	public String[] getUpdateFromClients() {
		return serverConnectionManager.getUpdateClients();
	}

	public boolean startNewConnectionToServerControl(String ip, int port) {
		if (serverConnectionManager.serverControlPool.threadCount < 16) {
			serverConnectionManager.serverControlPool.startNewConnection(ip, port);
			return true;
		}
		return false;
	}

}
