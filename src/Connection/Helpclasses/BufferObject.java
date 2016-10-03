/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Connection.Helpclasses;

import java.util.ArrayList;
import java.util.Queue;

/**
 *
 * @author jonas
 */
public class BufferObject {
	//
	public ArrayList<String> message;

	public BufferObject() {
		message = new ArrayList<String>();
	}

	public boolean isUpdated() {
		if (message.size() > 0)
			return true;
		return false;
	}

	// bestätigeNachricht

	public void updateMessage(String message) {
		this.message.add(message);
	}

	// lösche nicht notwendige nachrichten
//	public boolean clearBuffer() {
//		// false bei vollem buffer mit wichtiger priorität
//		for (int i = 0; i < message.size(); i++) {
//				message.remove(i);
//		}
//		return true;
//	}

	public String getUpdateMessage() {
		// neuste zu erst(timestamp) außer important message
		String retMessage = null;
		if (message.size() > 0)
			retMessage = message.remove(0);

		return retMessage;
	}
}
