package Connection.Helpclasses;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

public class MessageConfirmer {

	BufferObject messageIn, messageOut;

	String lineOut, lineIn;

	BufferedReader in;
	PrintWriter out;

	public MessageConfirmer(BufferedReader in, PrintWriter out, BufferObject messageIn, BufferObject messageOut) {
		this.in = in;
		this.out = out;
		this.messageIn = messageIn;
		this.messageOut = messageOut;
	}

	public void processMessages() {
		String lineOut = null;
		String lineIn = null;

		lineOut = messageOut.getUpdateMessage();

		out.println(lineOut);
		try {
			lineIn = in.readLine();
		} catch (IOException ex) {
		}
		if (lineIn != null && !lineIn.equalsIgnoreCase("null")) {
			messageIn.updateMessage(lineIn);
		}
	}

//	public void processMessages2() {
//		String lineOut = null;
//		String lineIn = null;
//		try {
//			lineIn = in.readLine();
//		} catch (IOException ex) {
//		}
//		if (lineIn != null) {
//			messageIn.updateMessage(lineIn);
//		}
//		if (messageOut.isUpdated()) {
//			lineOut = messageOut.getUpdateMessage();
//		}
//		out.println(lineOut);
//	}

}
