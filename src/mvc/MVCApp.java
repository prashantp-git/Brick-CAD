package mvc;

import java.awt.event.*;
import javax.swing.*;

public class MVCApp extends JFrame implements ActionListener {

	private static final long serialVersionUID = 1L;
	private JDesktopPane desktop;
	private AppFactory factory;
	private Model model;
	private CommandProcessor commandProcessor;

	public MVCApp(AppFactory factory) {

		this.factory = factory;
		this.model = factory.makeModel();
		this.commandProcessor = CommandProcessor.makeCommandProcessor();

		setSize(1024, 768);
		setTitle(factory.getTitle());
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setDefaultLookAndFeelDecorated(true);

		desktop = new JDesktopPane();
		desktop.setDragMode(JDesktopPane.OUTLINE_DRAG_MODE);
		setContentPane(desktop);

		setJMenuBar(createMenuBar());
		showView(null);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String fileCmmd = e.getActionCommand();
		switch (fileCmmd) {
		case "New":
			Utilities.saveChanges(model);
			model = factory.makeModel();
			showView(null);
			Utilities.informUser("New Model Created");
			break;
		case "Open File...":
			Utilities.saveChanges(model);
			Model openedModel = Utilities.open();
			if (openedModel != null) {
				model = openedModel;
				showView(null);
				Utilities.informUser("File opened successfully");
			}
			break;
		case "Save":
			Utilities.saveOrAs(model, false);
			break;
		case "Save As...":
			Utilities.saveOrAs(model, true);
			break;
		case "Help":
			Utilities.informUser(factory.getHelp());
			break;
		case "About":
			Utilities.informUser(factory.about());
			break;
		case "Exit":
			Utilities.saveChanges(model);
			System.exit(1);
			break;
		default:
			Utilities.error("Unrecognized command: " + fileCmmd);
			break;
		}
	}

	class ViewHandler implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			String viewCmmd = e.getActionCommand();
			showView(viewCmmd);
		}
	}

	class EditHandler implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			String editCmmd = e.getActionCommand();
			switch (editCmmd) {
			case "Undo":
				commandProcessor.undo();
				break;
			case "Redo":
				commandProcessor.redo();
				break;
			default:
				Command cmmd = factory.makeCommand(editCmmd);
				cmmd.setModel(model);
				commandProcessor.execute(cmmd);
				break;
			}
		}
	}

	private void showView(String view) {
		if (view == null) {
			view = Utilities.getFirstItem(factory.getViews());
			ViewFrame.setOpenFrameCount(0);
			for (JInternalFrame frame : desktop.getAllFrames()) {
				frame.setVisible(false);
			}
		}
		View panel = factory.makeView(view);
		panel.setModel(model);
		ViewFrame vf = new ViewFrame(panel);
		vf.setVisible(true);
		desktop.add(vf);
		try {
			vf.setSelected(true);
		} catch (java.beans.PropertyVetoException ex) {
		}
	}

	private JMenuBar createMenuBar() {

		// create a menu bar containing File, Edit, View, and Help menus
		JMenuBar menuBar = new JMenuBar();

		menuBar.add(Utilities.makeMenu("&File",
				new String[] { "&New", "&Open File...", "&Save", "Sa&ve As...", "E&xit" }, this));
		menuBar.add(Utilities.makeMenu("&Edit", factory.getCommands(), new EditHandler()));
		menuBar.add(Utilities.makeMenu("V&iew", factory.getViews(), new ViewHandler()));
		menuBar.add(Utilities.makeMenu("Hel&p", new String[] { "Hel&p", "&About" }, this));

		return menuBar;
	}

	public static void run(AppFactory factory) {
		try {
			MVCApp app = new MVCApp(factory);
			app.setVisible(true);
		} catch (Exception e) {
			Utilities.error(e.getMessage());
		}
	}
}