package bc;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Observable;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.event.AncestorEvent;
import javax.swing.event.AncestorListener;

import mvc.CommandProcessor;
import mvc.Utilities;
import mvc.View;

public class DimensionView extends View {

	private static final long serialVersionUID = 1L;
	private JLabel lengthL;
	private JLabel heightL;
	private JLabel widthL;
	private JLabel pressEnterL;
	private JTextField lengthTF;
	private JTextField heightTF;
	private JTextField widthTF;

	public DimensionView() {
		setViewName("Dimension View");
		createView();
		lengthTF.addActionListener(new actionHandler());
		heightTF.addActionListener(new actionHandler());
		widthTF.addActionListener(new actionHandler());

		addAncestorListener(new AncestorListener() {
			public void ancestorAdded(AncestorEvent event) {
				updateTF();
			}

			public void ancestorRemoved(AncestorEvent event) {
			}

			public void ancestorMoved(AncestorEvent event) {
			}
		});
	}

	private void updateTF() {
		if (model instanceof Brick) {
			Brick b = (Brick) model;
			lengthTF.setText(b.getLength().toString());
			heightTF.setText(b.getHeight().toString());
			widthTF.setText(b.getWidth().toString());
		} else {
			Utilities.error("Unexpected error. Please try again.");
		}
	}

	@Override
	public void update(Observable arg0, Object arg1) {
		updateTF();
	}

	private void createView() {
		setLayout(new GridBagLayout());

		lengthL = new JLabel("Length:");
		heightL = new JLabel("Height:");
		widthL = new JLabel("Width:");
		pressEnterL = new JLabel("Type text in a field and press Enter");
		lengthTF = new JTextField(15);
		heightTF = new JTextField(15);
		widthTF = new JTextField(15);

		add(lengthL, createConstraints(0, 0, new Insets(20, 20, 2, 2), GridBagConstraints.EAST));
		add(heightL, createConstraints(0, 1, new Insets(2, 20, 2, 2), GridBagConstraints.EAST));
		add(widthL, createConstraints(0, 2, new Insets(2, 20, 2, 2), GridBagConstraints.EAST));
		add(lengthTF, createConstraints(1, 0, new Insets(20, 2, 2, 20), GridBagConstraints.WEST));
		add(heightTF, createConstraints(1, 1, new Insets(2, 2, 2, 20), GridBagConstraints.WEST));
		add(widthTF, createConstraints(1, 2, new Insets(2, 2, 2, 20), GridBagConstraints.WEST));
		GridBagConstraints constraints = createConstraints(0, 3, new Insets(20, 20, 20, 20), GridBagConstraints.CENTER);
		constraints.gridwidth = 15;
		add(pressEnterL, constraints);

		setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Brick Attributes"));
	}

	private GridBagConstraints createConstraints(int gridx, int gridy, Insets insets, int anchor) {
		GridBagConstraints constraints = new GridBagConstraints();
		constraints.gridx = gridx;
		constraints.gridy = gridy;
		constraints.insets = insets;
		constraints.anchor = anchor;
		return constraints;
	}

	class actionHandler implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			if (model instanceof Brick) {
				Brick b = (Brick) model;
				String err = "";
				Double newHeight = null;
				Double newLength = null;
				Double newWidth = null;
				try {
					newHeight = Double.parseDouble(heightTF.getText());
					if (newHeight <= 0) {
						throw new Exception();
					}
				} catch (Exception e1) {
					err = err + "Please enter valid Height\n";
					heightTF.setText(b.getHeight().toString());
				}

				try {
					newLength = Double.parseDouble(lengthTF.getText());
					if (newLength <= 0) {
						throw new Exception();
					}
				} catch (Exception e1) {
					err = err + "Please enter valid Length\n";
					lengthTF.setText(b.getLength().toString());
				}

				try {
					newWidth = Double.parseDouble(widthTF.getText());
					if (newWidth <= 0) {
						throw new Exception();
					}
				} catch (Exception e1) {
					err = err + "Please enter valid Width\n";
					widthTF.setText(b.getWidth().toString());
				}

				if (err.isEmpty()) {
					if (!newLength.equals(b.getLength())) {
						SetLength l = new SetLength(newLength);
						l.setModel(b);
						CommandProcessor.makeCommandProcessor().execute(l);
					}
					if (!newHeight.equals(b.getHeight())) {
						SetHeight h = new SetHeight(newHeight);
						h.setModel(b);
						CommandProcessor.makeCommandProcessor().execute(h);
					}
					if (!newWidth.equals(b.getWidth())) {
						SetWidth w = new SetWidth(newWidth);
						w.setModel(b);
						CommandProcessor.makeCommandProcessor().execute(w);
					}
				} else {
					Utilities.error(err);
				}
			} else {
				Utilities.error("Unexpected error. Please try again.");
			}
		}
	}
}
