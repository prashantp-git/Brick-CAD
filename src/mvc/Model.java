package mvc;

import java.io.Serializable;
import java.util.Observable;

abstract public class Model extends Observable implements Serializable {

	private static final long serialVersionUID = 1L;
	private String fileName = null;
	private boolean unsavedChanges = false;

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public boolean hasUnsavedChanges() {
		return unsavedChanges;
	}

	public void setUnsavedChanges(boolean unsavedChanges) {
		this.unsavedChanges = unsavedChanges;
	}

	public void changed() {
		setUnsavedChanges(true);
		setChanged(); // setChanged
		notifyObservers(); // Notify all the observers
		clearChanged(); // clearChanged
	}

	abstract public Memento makeMemento();

	abstract public void accept(Memento m);

}
