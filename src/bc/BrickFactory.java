package bc;

import mvc.AppFactory;
import mvc.Command;
import mvc.Model;
import mvc.View;

public class BrickFactory implements AppFactory {

	@Override
	public Model makeModel() {
		return new Brick();
	}

	@Override
	public View makeView(String cmmd) {
		View v = null;
		switch (cmmd) {
		case "Show Side View...":
			v = new SideView();
			break;
		case "Show Top View...":
			v = new TopView();
			break;
		case "Show Front View...":
			v = new FrontView();
			break;
		case "Show Dimension View...":
			v = new DimensionView();
			break;
		default:
			break;
		}
		return v;
	}

	@Override
	public Command makeCommand(String cmmd) {
		Command c = null;
		switch (cmmd) {
		case "Set height":
			c = new SetHeight();
			break;
		case "Set length":
			c = new SetLength();
			break;
		case "Set width":
			c = new SetWidth();
			break;
		default:
			break;
		}
		return c;
	}

	@Override
	public String[] getViews() {
		String[] viewMenuItems = { "Show &Front View...", "Show &Top View...", "Sh&ow Side View...",
				"Show &Dimension View..." };
		return viewMenuItems;
	}

	@Override
	public String[] getCommands() {
		String[] editMenuItems = { "&Undo", "&Redo", "Set &height", "Set &length", "Set &width" };

		return editMenuItems;
	}

	@Override
	public String getTitle() {
		return "Brick CAD";
	}

	@Override
	public String getHelp() {
		return new String("Change the dimension using any of the \"Set\" option from Edit menu.\n"
				+ "Display a view using any of the option from View menu");
	}

	@Override
	public String about() {
		// TODO Auto-generated method stub
		return new String("Brick CAD is a CAD system for designing bricks.");
	}
}
