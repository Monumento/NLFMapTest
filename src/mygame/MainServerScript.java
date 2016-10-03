package mygame;

import Connection.Server;

import Welt.Welt;

import com.jme3.app.SimpleApplication;

import com.jme3.input.controls.ActionListener;

import com.jme3.renderer.RenderManager;

/**
 * test
 *
 * @author normenhansen
 */
public class MainServerScript extends SimpleApplication implements ActionListener {
	// game connection

	// engine
	public static Server server;
	// test code
	int testCount = 0;

	public static void main(String[] args) {
		//

		// Choose Server/CLient
		// SettingsGUI settings = new SettingsGUI();
		// settings.inputSettings();
		// startMain
		System.out.println("start server script:");
		MainServerScript app = new MainServerScript();
		app.start();
		// TCP/IP Configuration
	}

	@Override
	public void simpleInitApp() {
		initServer();
		initWelt();
	}

	@Override
	public void simpleUpdate(float tpf) {
		// CLIENTS
		// send
		server.runServerParts();

		String[] s = new String[1];
		long lastTime = System.currentTimeMillis();
		String[] temp = server.getUpdateFromClients();
		if (temp != null && temp.length > 0 && temp[0] != null) {
			if (temp[0].contains("pos")) {
				String[] split = temp[0].split(":")[1].split(",");
				s[0] = server.serverPartCluster.getMapAsString(Integer.parseInt(split[0]), Integer.parseInt(split[1]),
						Integer.parseInt(split[2]), 10);
				System.out.print(s[0].length());
			}
		} else {
			s[0] = "serverMessage:";
		}
		server.updateToClients(s);

		// connection id suche
		if (temp != null && temp.length > 0)
			System.out.println(temp[0]);
		while (System.currentTimeMillis() - lastTime < 5)
			;
		// send Messages
	}

	@Override
	public void simpleRender(RenderManager rm) {
		// TODO: add render code
	}

	private void initServer() {
		server = new Server(1, "127.0.0.1", 1000);
		server.startServer();
	}

	public void onAction(String name, boolean isPressed, float tpf) {
		throw new UnsupportedOperationException("Not supported yet."); // To
																		// change
																		// body
																		// of
																		// generated
																		// methods,
																		// choose
																		// Tools
																		// |
																		// Templates.
	}

	private void initWelt() {
		server.initNewServerPart(1, "Ebene");
	}
}
