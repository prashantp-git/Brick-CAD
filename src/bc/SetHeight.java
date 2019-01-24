package bc;

import mvc.Command;
import mvc.Utilities;

public class SetHeight extends Command {
	private Double newHeight = null;

	public SetHeight() {
		setUndoable(true);
	}

	public SetHeight(Double newHeight) {
		setUndoable(true);
		this.newHeight = newHeight;
	}

	public void execute() {
		if (newHeight == null) {
			while (newHeight == null) {
				try {
					newHeight = Double.parseDouble(Utilities.askUser("Please input new Height: "));
					if (newHeight <= 0) {
						newHeight = null;
						Utilities.error("Please enter valid Height");
					}
				} catch (Exception e) {
					if (e instanceof NullPointerException) {
						newHeight = -1d;
						break;
					} else if (e instanceof NumberFormatException)
						Utilities.error("Please enter valid Height");
				}
			}
		}
		if (newHeight != null && newHeight > 0) {
			if (model instanceof Brick) {
				super.execute(); // make memento
				((Brick) model).setHeight(newHeight);
			} else {
				Utilities.error("Unexpected error. Please try again.");
			}
		}
	}
}
