import Connection.Client;

public class ClientTest {
	public static Client client;

	public static void main(String args[]) {
		long lastTime = 0;

		client = new Client("127.0.0.1", 500, 4);
		client.startClient();
		client.startNewConnectionToServer("127.0.0.1", 500);

		int i = 0;

		String test = "CLIENTMessage" + i;
		String[] s = { test, test, test, test };
		s[0] = "CLIENTMessage " + i;
		while (true) {
			lastTime = System.currentTimeMillis();
			s = new String[4];
			s[0] = "CLIENTMessage " + i;
			client.updateToServer(s);
			s = client.getUpdateFromServer();
			if (s != null && s[0] != null)
				System.out.println(s[0]);

			s = new String[4];
			s[0] = "CLIENTMessage " + i;
			client.updateToUserControl(s);
			s = client.getUpdateFromUserControl();
			if (s != null && s[0] != null)
				System.out.println(s[0]);

			i++;
			while (System.currentTimeMillis() - lastTime < 1000)
				;
		}
	}
}
