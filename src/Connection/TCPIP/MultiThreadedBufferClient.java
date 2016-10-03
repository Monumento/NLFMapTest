package Connection.TCPIP;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MultiThreadedBufferClient {
	public ExecutorService pool;
	//
	public ClientThread[] threadPool;
	public int[] connectionID;
	//
	public int threadCount;
	//
	public boolean startNewClient;

	public MultiThreadedBufferClient() {
		pool = Executors.newCachedThreadPool();
		threadPool = new ClientThread[16];
		connectionID = new int[16];
		threadCount = 0;
	}

	public void updateToMasters(String[] message) {
		// servercontrol connection
		for (int i = 0; i < threadCount; i++) {
			// actual server connection
			threadPool[i].messageOut.updateMessage(message[i]);
		}
	}

	public String[] getUpdateFromMasters() {
		String[] ret = null;
		if (threadCount > 0) {
			ret = new String[threadCount];
			for (int i = 0; i < threadCount; i++) {
				ret[i] = threadPool[i].messageIn.getUpdateMessage();
			}
		}
		return ret;
	}

	public boolean startNewConnection(String ip, int port) {
		if (threadCount == threadPool.length) {
			return false;
		} else {
			threadPool[threadCount] = new ClientThread(ip, port);
			pool.execute(threadPool[threadCount]);
			threadCount++;
			return true;
		}

	}

}
