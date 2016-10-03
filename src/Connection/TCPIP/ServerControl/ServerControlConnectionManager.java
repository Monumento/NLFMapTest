package Connection.TCPIP.ServerControl;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import Connection.Helpclasses.ClientControl;
import Connection.TCPIP.ClientThread;
import Connection.TCPIP.MultiThreadedBufferClient;
import Connection.TCPIP.MultiThreadedBufferServer;

public class ServerControlConnectionManager {

	public MultiThreadedBufferClient systemControlPool;
	public MultiThreadedBufferServer serverPool;
	// threadpool
	public ExecutorService pool;
	public int threadCount;

	// internal messages

	// Connection Messages
	public ServerControlConnectionManager() {
		serverPool = new MultiThreadedBufferServer(1000);
		systemControlPool = new MultiThreadedBufferClient();
		threadCount = 0;
	}
	// send update to systemControl

	public void updateSystemControl(String[] message) {
		systemControlPool.updateToMasters(message);
	}
	// get update from systemControl

	public String[] getUpdateFromSystemControl() {
		return systemControlPool.getUpdateFromMasters();
	}

	// get update to servers
	public String[] getUpdateFromServers() {
		return serverPool.getUpdateFromSlaves();
	}
	// send update to servers

	public void updateToServers(String[] message) {
		serverPool.updateToSlaves(message);
	}

	public void startServerControl() {
		System.out.println("und SERVERControlConnectionManager running...");
		pool = Executors.newCachedThreadPool();
		// connect to SystemControl
		// TODO
		systemControlPool.startNewConnection("127.0.0.1", 2000);
		System.out.println("not connected to SystemControl");
		//
		// start Server for WorldServers
		pool.execute(serverPool);
		threadCount++;
		System.out.println("serverPool running");

	}
}
