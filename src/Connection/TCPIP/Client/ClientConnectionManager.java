/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Connection.TCPIP.Client;

import Connection.TCPIP.ClientThread;
import Connection.TCPIP.MultiThreadedBufferClient;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Helps client to connect to several and the right servers. first connection
 * ServerControl to get the Server the serverParts is running on, second to
 * fifth visible serverParts | maybe buffer servers for buffer serverparts
 *
 * @author jonas has a threadpool to control the servers
 *
 */
public class ClientConnectionManager {

	public ExecutorService pool;
	public MultiThreadedBufferClient userControl;
	public MultiThreadedBufferClient serverPool;
	public int threadCount;
	public int startPort;
	public String startIp;
	//
	public boolean startNewClient;

	public ClientConnectionManager(String startIp, int startPort) {
		pool = Executors.newCachedThreadPool();
		serverPool = new MultiThreadedBufferClient();
		userControl = new MultiThreadedBufferClient();
		this.startPort = startPort;
		this.startIp = startIp;
	}

	public void updateToServer(String[] message) {
		// servercontrol connection
		serverPool.updateToMasters(message);
	}

	public String[] getUpdateFromServer() {
		return serverPool.getUpdateFromMasters();
	}

	public void updateToUserControl(String[] message) {
		// servercontrol connection
		userControl.updateToMasters(message);
	}

	public String[] getUpdateFromUserControl() {

		return userControl.getUpdateFromMasters();
	}

	public boolean startNewConnectionToServer(String ip, int port) {
		if (serverPool.threadCount < 16) {
			serverPool.startNewConnection(ip, port);
			return true;
		}
		// too many connections
		return false;
	}

	public boolean startNewConnectionToUserControl(String ip, int port) {
		if (userControl.threadCount < 16) {
			userControl.startNewConnection(ip, port);
			return true;
		}
		// too many connections
		return false;
	}

	public void startClient() {
		// TODO CONNECT TO USERCONTROL

		// init pool

		// startNewConnectionToUserControl("127.0.0.1", 3000);
		System.out.println("CLIENTConnectionManager running");
		System.out.println("and is NOT connected to userControl");

		// TODO connect to ClientManager(helps to find serverParts)

		// start new Client

	}
}
