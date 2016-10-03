/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Connection;

import Connection.TCPIP.Client.ClientConnectionManager;

/**
 *
 * @author jonas
 */
public class Client {

	public int clientID;
	String startIp;
	int port;
	public ClientConnectionManager clientConnectionManager;

	ServerPart[] visibleParts;
	ServerPart[] loadedParts;

	public Client(String startIp, int port, int visibleParts) {
		this.startIp = startIp;
		this.port = port;
		this.visibleParts = new ServerPart[visibleParts];
		this.visibleParts = new ServerPart[visibleParts * 4];
		clientConnectionManager = new ClientConnectionManager(startIp, 1000);
	}

	public void startClient() {
		clientConnectionManager.startClient();
	}

	public String[] getUpdateFromServer() {
		return clientConnectionManager.getUpdateFromServer();
	}

	public boolean updateToServer(String[] message) {
		clientConnectionManager.updateToServer(message);
		return true;
	}

	public String[] getUpdateFromUserControl() {
		return clientConnectionManager.getUpdateFromUserControl();
	}

	public boolean updateToUserControl(String[] message) {
		clientConnectionManager.updateToUserControl(message);
		return true;
	}

	public boolean startNewConnectionToServer(String ip, int port) {
		return clientConnectionManager.startNewConnectionToServer(ip, port);
	}

	public boolean startNewConnectionToUserControl(String ip, int port) {
		return clientConnectionManager.startNewConnectionToUserControl(ip, port);
	}

}
