package Connection.TCPIP.UserControl;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import Connection.Helpclasses.ClientControl;
import Connection.TCPIP.ClientThread;
import Connection.TCPIP.MultiThreadedBufferClient;
import Connection.TCPIP.MultiThreadedBufferServer;

public class UserControlConnectionManager {
	public MultiThreadedBufferClient systemControlPool;
	public MultiThreadedBufferServer userPool;
	// threadpool
	public ExecutorService pool;
	public int threadCount;
	// internal messages

	// Connection Messages
	public UserControlConnectionManager() {
		userPool = new MultiThreadedBufferServer(3000);
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

	// get update from users
	public String[] getUpdateFromUsers() {
		return userPool.getUpdateFromSlaves();
	}
	// send update to users

	public void updateToUsers(String[] message) {
		userPool.updateToSlaves(message);
	}

	public void startUserControl() {
		pool = Executors.newCachedThreadPool();
		// connect to SystemControl
		// TODO
		systemControlPool.startNewConnection("127.0.0.1", 2100);
		System.out.println("USERcontrol is connected to SYSTEMcontrol");
		//

		pool.execute(userPool);
		threadCount++;
		System.out.println(" und USERControlConnectionManager running...");

	}
}
