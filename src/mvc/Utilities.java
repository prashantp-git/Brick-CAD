package mvc;

import javax.swing.*;
import java.awt.event.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class Utilities {

	public static JMenu makeMenu(String name, String[] items, ActionListener handler) {

		JMenu result;
		int j = name.indexOf('&');
		if (j != -1) {
			char c = name.charAt(j + 1);
			String s = name.substring(0, j) + name.substring(j + 1);
			result = new JMenu(s);
			result.setMnemonic(c);
		} else {
			result = new JMenu(name);
		}

		for (int i = 0; i < items.length; i++) {
			if (items[i] == null) {
				result.addSeparator();
			} else {
				j = items[i].indexOf('&');
				JMenuItem item;
				if (j != -1) {
					char c = items[i].charAt(j + 1);
					String s = items[i].substring(0, j) + items[i].substring(j + 1);
					item = new JMenuItem(s, items[i].charAt(j + 1));
					item.setAccelerator(KeyStroke.getKeyStroke(c, InputEvent.CTRL_MASK));
				} else { // no accelerator or shortcut key
					item = new JMenuItem(items[i]);
				}
				item.addActionListener(handler);
				result.add(item);
			}
			// result.addMenuListener(this);
		}
		return result;
	}

	public static String askUser(String query) {
		return JOptionPane.showInputDialog(query);
	}

	public static boolean confirm(String query) {
		int result = JOptionPane.showConfirmDialog(null, query, "Choose one", JOptionPane.YES_NO_OPTION);
		return result == 0 ? true : false;
	}

	public static void error(String gripe) {
		JOptionPane.showMessageDialog(null, gripe, "OOPS!", JOptionPane.ERROR_MESSAGE);
	}

	public static void error(Exception gripe) {
		gripe.printStackTrace();
		JOptionPane.showMessageDialog(null, gripe.toString(), "OOPS!", JOptionPane.ERROR_MESSAGE);
	}

	public static void informUser(String info) {
		JOptionPane.showMessageDialog(null, info, "Information", JOptionPane.INFORMATION_MESSAGE);
	}

	public static void saveChanges(Model model) {
		if (model.hasUnsavedChanges() && Utilities.confirm("Current model has unsaved changes, want to save?")) {
			Utilities.saveOrAs(model, false);
		}
	}

	public static JFileChooser fileChooser(String title, File defaultDir) {
		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setDialogTitle(title);
		fileChooser.setCurrentDirectory(defaultDir);
		return fileChooser;
	}

	public static void saveOrAs(Model model, boolean saveAs) {
		if (model.hasUnsavedChanges() && !saveAs) {
			String fileName = null;
			if (model.getFileName() == null) {
				JFileChooser fileChooser = fileChooser("Specify a file to save",
						new File(System.getProperty("user.dir")));
				if (fileChooser.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {
					fileName = fileChooser.getSelectedFile().getAbsolutePath();
					model.setFileName(fileName);
				}
			} else {
				fileName = model.getFileName();
			}
			if (fileName != null) {
				writeObject(model, fileName);
			}
		} else if (saveAs) {
			String fileName = null;
			JFileChooser fileChooser = fileChooser("Specify a file to save", new File(System.getProperty("user.dir")));
			if (fileChooser.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {
				fileName = fileChooser.getSelectedFile().getAbsolutePath();
				model.setFileName(fileName);
			}
			if (fileName != null) {
				writeObject(model, fileName);
			}
		}
	}

	public static void writeObject(Model model, String filePath) {
		try {
			ObjectOutputStream os = new ObjectOutputStream(new FileOutputStream(filePath));
			model.setUnsavedChanges(false);
			os.writeObject(model);
			os.flush();
			os.close();
			Utilities.informUser("File saved");
		} catch (Exception err) {
			model.setUnsavedChanges(true);
			Utilities.error(err.getMessage());
		}
	}

	public static Model open() {
		Model model = null;
		JFileChooser fileChooser = fileChooser("Specify a file to open", new File(System.getProperty("user.dir")));
		if (fileChooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
			try {
				ObjectInputStream in = new ObjectInputStream(
						new FileInputStream(fileChooser.getSelectedFile().getAbsolutePath()));
				Object o = in.readObject();
				if (o instanceof Model) {
					model = (Model) o;
				}
				in.close();
			} catch (Exception err) {
				Utilities.error(err.getMessage());
			}
		}
		return model;
	}

	public static String getFirstItem(String[] items) {
		String s;
		int j = items[0].indexOf('&');
		if (j != -1) {
			s = items[0].substring(0, j) + items[0].substring(j + 1);
		} else {
			s = items[0];
		}
		return s;
	}
}
