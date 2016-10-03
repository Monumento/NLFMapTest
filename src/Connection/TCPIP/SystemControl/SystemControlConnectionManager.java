package Connection.TCPIP.SystemControl;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import Connection.Helpclasses.ClientControl;
import Connection.TCPIP.ClientThread;
import Connection.TCPIP.MultiThreadedBufferServer;

public class SystemControlConnectionManager {
	public MultiThreadedBufferServer userControlPool;
	public MultiThreadedBufferServer serverControlPool;
	// threadpool
	public ExecutorService pool;
	public int threadCount;
	// internal messages

	// Connection Messages
	public SystemControlConnectionManager() {
		serverControlPool = new MultiThreadedBufferServer(2000);
		userControlPool = new MultiThreadedBufferServer(2100);
		threadCount = 0;
	}
	// send update to userControl

	public void updateUserControl(String[] message) {
		userControlPool.updateToSlaves(message);
	}
	// get update from userControl

	public String[] getUpdateFromUserControl() {
		return userControlPool.getUpdateFromSlaves();
	}

	// get update from Servercontrols
	public String[] getUpdateFromServers() {
		return serverControlPool.getUpdateFromSlaves();
	}
	// send update to Servercontrols

	public void updateToServers(String[] message) {
		serverControlPool.updateToSlaves(message);
	}

	public void startSystemControl() {
		pool = Executors.newCachedThreadPool();
		// start server for userControl(s)
		pool.execute(userControlPool);
		threadCount++;
		// start server for serverControls
		pool.execute(serverControlPool);
		threadCount++;
		System.out.println("SYSTEMControlConnectionManager running...");

	}
}
