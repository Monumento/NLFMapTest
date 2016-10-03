/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Connection.TCPIP;

import Connection.Helpclasses.BufferObject;
import Connection.Helpclasses.MessageConfirmer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author jonas
 */
public class ServerThread extends Thread {

	public boolean endConnection, stopClient;
	public Socket socket;
	public BufferObject messageOut, messageIn;
	public int connectionID;
	private BufferedReader in;
	private PrintWriter out;
	public MessageConfirmer messageConfirmer;

	public ServerThread(Socket socket) {
		endConnection = false;
		stopClient = false;

		this.socket = socket;

		messageOut = new BufferObject();
		messageIn = new BufferObject();
		messageOut.updateMessage("serverID");
	}

	// send to masters/slaves
	public ServerThread(int connectionID, Socket socket) {
		this.connectionID = connectionID;
		endConnection = false;
		stopClient = false;

		this.socket = socket;

		messageOut = new BufferObject();
		messageIn = new BufferObject();
		messageOut.updateMessage("serverID");
	}

	public void run() {
		in = null;
		out = null;
		messageConfirmer = null;
		try {
			in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			out = new PrintWriter(socket.getOutputStream(), true);
			messageConfirmer = new MessageConfirmer(in, out, messageIn, messageOut);
		} catch (Exception e) {
		}
		System.out.println("Verbindung zu " + socket.getRemoteSocketAddress() + " aufgebaut");
		long lastTime = System.currentTimeMillis();
		while (!stopClient) {

			messageConfirmer.processMessages();
		}
	}

	private void close() {
		try {
			out.close();
			socket.close();
			in.close();
			stopClient = true;
		} catch (IOException ex) {
			Logger.getLogger(ClientThread.class.getName()).log(Level.SEVERE, null, ex);
		}
	}
}