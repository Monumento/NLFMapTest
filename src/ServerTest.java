import Connection.Server;
import Welt.NLFWelt;

public class ServerTest {

	public static void main(String args[]) {
		long lastTime = 0;

		Server server = new Server(1, "127.0.0.1", 500);
		server.startServer();
		server.initNewServerPart(1, "Ebene");
		String t[] = null;
		String mapAsString[] = new String[1000];
		int currentMapPart = 0;
		int viewdistance = 5;
		int x = 5, y = 5, z = 5;
		for (int i = 0; i < 10; i++) {
			int currentX = (x + i * (2 * viewdistance));
			for (int j = 0; j < 10; j++) {
				int currentY = (y + j * (2 * viewdistance));
				for (int k = 0; k < 10; k++) {
					int currentZ = (z + k * (2 * viewdistance));
					mapAsString[currentMapPart] = (x + i * (2 * viewdistance)) + "," + (y + j * (2 * viewdistance))
							+ "," + (z + k * (2 * viewdistance)) + ":"
							+ server.serverPartCluster.getMapAsString((x + i * (2 * viewdistance)), (y + j * (2 * viewdistance)), (z + k * (2 * viewdistance)), viewdistance);
					currentMapPart++;
				}
			}
		}
		System.out.println(currentMapPart);
		currentMapPart = 0;
		
		while (true) {
			server.runServerParts();

			String[] s = new String[1];
			lastTime = System.currentTimeMillis();
			String[] temp = server.getUpdateFromClients();
			if (temp != null && temp.length > 0 && temp[0] != null) {
				if (temp[0].contains("pos")) {
					// String[] split = temp[0].split(":")[1].split(",");
					if (currentMapPart < 1000){
						s[0] = mapAsString[currentMapPart];
						currentMapPart++;	
					}else{
						s[0] = "finishedLoading:";
						System.out.println(s[0]);
					}
				}
			} else {
				
			}
			System.out.println(currentMapPart);
			server.updateToClients(s);

			// connection id suche
			if (temp != null && temp.length > 0)
				System.out.println(temp[0]);
			while (System.currentTimeMillis() - lastTime < 5)
				;
		}
	}
}
