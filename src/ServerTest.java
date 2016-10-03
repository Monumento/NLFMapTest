import Connection.Server;
import Welt.NLFWelt;

public class ServerTest {

	public static void main(String args[]) {
		long lastTime = 0;

		Server server = new Server(1, "127.0.0.1", 500);
		server.startServer();
		server.initNewServerPart(1, "Ebene");
		String t[] = null;
		int i = 0;
		int viewdistance = 5;
		int x = 5, y = 45, z = 5;
		while (true) {
			server.runServerParts();

			String[] s = new String[1];
			lastTime = System.currentTimeMillis();
			String[] temp = server.getUpdateFromClients();
			if (temp != null && temp.length > 0 && temp[0] != null) {
				if (temp[0].contains("pos")) {
					// String[] split = temp[0].split(":")[1].split(",");
					if (x != NLFWelt.SERVERPART_SIZE / 2 + viewdistance) {
						s[0] = x + "," + y + "," + z + ":"
								+ server.serverPartCluster.getMapAsString(x, y, z, viewdistance);
						x += 2 * viewdistance;
					} else {
						if (z != NLFWelt.SERVERPART_SIZE / 2 + viewdistance) {
							z += 2 * viewdistance;
							x = 5;
						} else
							System.exit(1);
					}
				}
			} else {
				s[0] = "serverMessage:";
			}
			server.updateToClients(s);

			// connection id suche
			if (temp != null && temp.length > 0)
				System.out.println(temp[0]);
			while (System.currentTimeMillis() - lastTime < 1000)
				;
		}
	}
}
