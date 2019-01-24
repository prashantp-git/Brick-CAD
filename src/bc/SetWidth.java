package bc;

import mvc.Command;
import mvc.Utilities;

public class SetWidth extends Command {
	private Double newWidth = null;

	public SetWidth() {
		setUndoable(true);
	}

	public SetWidth(Double newWidth) {
		setUndoable(true);
		this.newWidth = newWidth;
	}

	public void execute() {
		if (newWidth == null) {
			while (newWidth == null) {
				try {
					newWidth = Double.parseDouble(Utilities.askUser("Please input new Width: "));
					if (newWidth <= 0) {
						newWidth = null;
						Utilities.error("Please enter valid Width");
					}
				} catch (Exception e) {
					if (e instanceof NullPointerException) {
						newWidth = -1d;
						break;
					} else if (e instanceof NumberFormatException)
						Utilities.error("Please enter valid Width");
				}
			}
		}
		if (newWidth != null && newWidth > 0) {
			if (model instanceof Brick) {
				super.execute(); // make memento
				((Brick) model).setWidth(newWidth);
			} else {
				Utilities.error("Unexpected error. Please try again.");
			}
		}
	}
}
