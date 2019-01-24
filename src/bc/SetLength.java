package bc;

import mvc.Command;
import mvc.Utilities;

public class SetLength extends Command {
	private Double newLength = null;

	public SetLength() {
		setUndoable(true);
	}

	public SetLength(Double newLength) {
		setUndoable(true);
		this.newLength = newLength;
	}

	public void execute() {
		if (newLength == null) {
			while (newLength == null) {
				try {
					newLength = Double.parseDouble(Utilities.askUser("Please input new Length: "));
					if (newLength <= 0) {
						newLength = null;
						Utilities.error("Please enter valid Length");
					}
				} catch (Exception e) {
					if (e instanceof NullPointerException) {
						newLength = -1d;
						break;
					} else if (e instanceof NumberFormatException)
						Utilities.error("Please enter valid Length");
				}
			}
		}
		if (newLength != null && newLength > 0) {
			if (model instanceof Brick) {
				super.execute(); // make memento
				((Brick) model).setLength(newLength);
			} else {
				Utilities.error("Unexpected error. Please try again.");
			}
		}
	}
}
