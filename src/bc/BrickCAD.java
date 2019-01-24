package bc;

import mvc.*;

public class BrickCAD {
	public static void main(String args[]) {
		MVCApp.run(new BrickFactory());
	}
}
