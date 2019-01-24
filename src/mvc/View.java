package mvc;

import java.util.Observer;

import javax.swing.JPanel;

abstract public class View extends JPanel implements Observer {

	private static final long serialVersionUID = 1L;
	protected Model model;
	protected String viewName;
	
	public Model getModel() {
		return model;
	}

	public void setModel(Model model) {
		this.model = model;
		model.addObserver(this);
	}

	public String getViewName() {
		return viewName;
	}

	public void setViewName(String viewName) {
		this.viewName = viewName;
	}
	
}
