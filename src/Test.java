import java.util.ArrayList;

public class Test {

	public static void main(String args) {

		ArrayList<String> s = new ArrayList<String>();
		s.add("a");
		s.add("b");
		s.add("c");
		s.remove(1);
		for (String string : s) {
			System.out.println(s);
		}

	}

}
