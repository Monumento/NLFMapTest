import Connection.SystemControl;

public class SystemControlTest {
	public static void main(String args[]) {
		long lastTime = 0;

		SystemControl systemControl = new SystemControl(11);
		systemControl.startSystemControl();
		String s[] = { "SYSTEMControlMessage", "SYSTEMControlMessage", "SYSTEMControlMessage", "SYSTEMControlMessage" };
		String t[] = null;
		int i = 0;
		while (true) {
			lastTime = System.currentTimeMillis();
			s[0] = "SYSTEMControlMessage " + i;
			systemControl.updateToServersServerControls(s);
			t = systemControl.getUpdateFromServerControls();
			if (t.length > 0)
				System.out.println(t[0]);
			systemControl.updateToUserControl(s);
			t = systemControl.getUpdateFromUserControl();
			if (t.length > 0)
				System.out.println(t[0]);
			i++;
			while (System.currentTimeMillis() - lastTime < 1000)
				;
		}
	}
}
