import Connection.UserControl;

public class UserControlTest {
	public static void main(String args[]) {
		long lastTime = 0;

		UserControl userControl = new UserControl(1);
		userControl.startUserControl();
		String s[] = { "hey you", "hey you", "hey you", "hey you" };
		String t[] = null;
		int i = 0;
		while (true) {
			lastTime = System.currentTimeMillis();
			s[0] = "USERControlMessage " + i;
			userControl.updateToUsers(s);
			t = userControl.getUpdateFromUsers();
			if (t.length > 0)
				System.out.println(t[0]);
			userControl.updateToSystemControl(s);
			t = userControl.getUpdateFromSystemControl();
			if (t.length > 0)
				System.out.println(t[0]);
			i++;
			while (System.currentTimeMillis() - lastTime < 1000)
				;
		}
	}
}