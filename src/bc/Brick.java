package bc;

import mvc.Memento;
import mvc.Model;

public class Brick extends Model {

	private static final long serialVersionUID = 1L;
	private Double height, width, length;

	public Brick() {
		this(50.0, 50.0, 100.0);
	}

	public Brick(Double height, Double width, Double length) {
		super();
		this.height = height;
		this.width = width;
		this.length = length;
	}

	public Double getHeight() {
		return height;
	}

	public void setHeight(Double height) {
		this.height = height;
		changed();
	}

	public Double getWidth() {
		return width;
	}

	public void setWidth(Double width) {
		this.width = width;
		changed();
	}

	public Double getLength() {
		return length;
	}

	public void setLength(Double length) {
		this.length = length;
		changed();
	}

	private class BrickMomento implements Memento {
		Double height, width, length;

		public BrickMomento(Double height, Double width, Double length) {
			super();
			this.height = height;
			this.width = width;
			this.length = length;
		}

		Double getHeight() {
			return height;
		}

		Double getWidth() {
			return width;
		}

		Double getLength() {
			return length;
		}
	}

	@Override
	public Memento makeMemento() {
		return new BrickMomento(height, width, length);
	}

	@Override
	public void accept(Memento m) {
		if (m instanceof BrickMomento) {
			setLength(((BrickMomento) m).getLength());
			setHeight(((BrickMomento) m).getHeight());
			setWidth(((BrickMomento) m).getWidth());
		}
	}
}
