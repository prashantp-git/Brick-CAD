package bc;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.Observable;

import mvc.Utilities;
import mvc.View;

public class TopView extends View {

	private static final long serialVersionUID = 1L;
	private Color topBrown = new Color(129, 108, 93);

	public TopView() {
		setViewName("Top View");
	}

	@Override
	public void update(Observable o, Object arg) {
		repaint();
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		if (model instanceof Brick) {
			Brick b = (Brick) model;
			g.setColor(topBrown);
			Rectangle r = getRect(b);
			g.fillRect(r.x, r.y, r.width, r.height);
			g.drawString(b.getWidth() + "", (int) (r.x - 35), (int) (r.y + (r.height * 0.5)));
			g.drawString(b.getLength() + "", (int) (r.x + (r.width * 0.4)), (int) (r.y + r.height + 20));
		} else {
			Utilities.error("Unexpected error. Please try again.");
		}
	}

	private Rectangle getRect(Brick b) {
		return new Rectangle((getWidth() / 2) - (b.getLength().intValue() / 2),
				(getHeight() / 2) - (b.getWidth().intValue() / 2), b.getLength().intValue(), b.getWidth().intValue());
	}

	@Override
	public Dimension getPreferredSize() {
		if (model instanceof Brick) {
			Brick b = (Brick) model;
			Rectangle r = getRect(b);
			return new Dimension(r.width + 200, r.height + 200);
		}
		return new Dimension(300, 300);
	}
}
