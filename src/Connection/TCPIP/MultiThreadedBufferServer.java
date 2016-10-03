package Connection.TCPIP;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MultiThreadedBufferServer implements Runnable {
	/**
	 *
	 * @author Jonas
	 */
	public ExecutorService pool;
	public ServerThread[] threadPool;
	private int threadCount;
	private int startPort;
	
	public MultiThreadedBufferServer(int startPort) {
		this.startPort = startPort;
		threadPool = new ServerThread[256];
		threadCount = 0;
	}
	
	public String[] getUpdateFromSlaves() {
		String[] ret = new String[threadCount];
		for (int i = 0; i < threadCount; i++) {
			ret[i] = threadPool[i].messageIn.getUpdateMessage();
		}
		return ret;
	}
	// TODO exceptions für voller werdendes backlog etc.

	public boolean updateToSlaves(String[] message) {
		for (int i = 0; i < threadCount; i++) {
			threadPool[i].messageOut.updateMessage(message[i]);
		}
		return true;
	}



	public void run() {
		ServerSocket server;
		try {
			server = new ServerSocket(startPort);
			pool = Executors.newCachedThreadPool();
			System.out.println("ClientControl von ");
			while (true) {
				Socket socket = server.accept();
				threadPool[threadCount] = new ServerThread(socket);
				pool.execute(threadPool[threadCount]);
				threadCount++;
			}
		} catch (IOException e) {
			System.err.println(e);
			pool.shutdown();
		}
	}
}
