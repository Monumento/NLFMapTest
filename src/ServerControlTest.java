
import Connection.ServerControl;

public class ServerControlTest {
	public static void main(String args[]) {
		long lastTime = 0;

		ServerControl serverControl = new ServerControl(11);
		serverControl.startServerControl();
		String s[] = { "hey you", "hey you", "hey you", "hey you" };
		String t[] = null;
		int i = 0;
		while (true) {
			lastTime = System.currentTimeMillis();
			s[0] = "SERVERCONTROLMessage " + i;
			serverControl.updateToServers(s);
			t = serverControl.getUpdateFromServers();
			if(t.length>0)
			System.out.println(t[0]);
			serverControl.updateToSystemControl(s);
			t = serverControl.getUpdateFromSystemControl();
			if(t.length>0)
			System.out.println(t[0]);
			i++;
			while (System.currentTimeMillis() - lastTime < 1000)
				;
		}
	}
}
