/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Connection.TCPIP.Server;

import Connection.Helpclasses.BufferObject;
import Connection.Helpclasses.ClientControl;
import Connection.TCPIP.ClientThread;
import Connection.TCPIP.MultiThreadedBufferClient;
import Connection.TCPIP.MultiThreadedBufferServer;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * first connection keeps Connection to serverControl second to fifth servers
 * around this server 6-* clients
 *
 * @author jonas
 */
public class ServerConnectionManager {

	/*
	 * To change this template, choose Tools | Templates and open the template
	 * in the editor.
	 */
	/**
	 *
	 * @author jonas
	 *
	 */
	// connections
	public MultiThreadedBufferClient serverControlPool;
	public MultiThreadedBufferServer clientPool;
	// threadpool
	public ExecutorService pool;
	public int threadCount;
	public int startPort;
	public String startIp;
	// internal messages

	// Connection Messages
	public ServerConnectionManager(String startIp, int startPort) {
		this.startPort = startPort;
		this.startIp = startIp;
		serverControlPool = new MultiThreadedBufferClient();
		clientPool = new MultiThreadedBufferServer(500);
		threadCount = 0;
	}
	// send update to serverControl

	public void updateServerControl(String[] message) {
		serverControlPool.updateToMasters(message);
	}
	// get update from serverControl

	public String[] getUpdateFromServerControl() {
		return serverControlPool.getUpdateFromMasters();
	}

	// get update to clients
	public String[] getUpdateClients() {
		return clientPool.getUpdateFromSlaves();
	}
	// send update to clients

	public void updateToClients(String[] message) {
		clientPool.updateToSlaves(message);
	}

	public void startServer() {
		System.out.println("und SERVERConnectionManager läuft...");
		pool = Executors.newCachedThreadPool();
		// connect to ServerControl
		// TODO
		System.out.println("not Connected to ServerControl");
		//serverControlPool.startNewConnection("127.0.0.1", 1000);
		//
		pool.execute(clientPool);
		threadCount++;
		System.out.println("ClientPool running");

	}

}
