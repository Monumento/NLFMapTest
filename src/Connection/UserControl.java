package Connection;

import Connection.TCPIP.UserControl.UserControlConnectionManager;

public class UserControl {

	public int userControlID;

	private UserControlConnectionManager userControlConnectionManager;

	public UserControl(int userControlID) {
		this.userControlID = userControlID;
		userControlConnectionManager = new UserControlConnectionManager();
	}

	public void startUserControl() {
		userControlConnectionManager.startUserControl();
	}

	public void updateToUsers(String[] message) {
		userControlConnectionManager.updateToUsers(message);
	}

	public String[] getUpdateFromUsers() {
		return userControlConnectionManager.getUpdateFromUsers();
	}

	public void updateToSystemControl(String[] message) {
		userControlConnectionManager.updateSystemControl(message);

	}

	public String[] getUpdateFromSystemControl() {
		return userControlConnectionManager.getUpdateFromSystemControl();
	}

	public boolean startNewConnectionToSystemControl(String ip, int port) {
		// TODO
		if (userControlConnectionManager.systemControlPool.threadCount < 16) {
			return userControlConnectionManager.systemControlPool.startNewConnection(ip, port);
		}
		return false;
	}
}
