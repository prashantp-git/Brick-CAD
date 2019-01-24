package mvc;

import java.util.Stack;

public class CommandProcessor {

	private Stack<Command> undoStack = new Stack<Command>();
	private Stack<Command> redoStack = new Stack<Command>();

	private static CommandProcessor theCommandProcessor = null;
	
	private CommandProcessor() {
	}

	// Singleton Design Pattern
	public static CommandProcessor makeCommandProcessor() {
		if (theCommandProcessor == null)
			theCommandProcessor = new CommandProcessor();
		return theCommandProcessor;
	}

	public void execute(Command cmmd) {
		cmmd.execute();
		if (cmmd.isUndoable()) {
			undoStack.push(cmmd);
			redoStack.clear();
		}
	}

	public void redo() {
		if (!redoStack.empty()) {
			Command redoCmmd = redoStack.pop();
			redoCmmd.execute();
			undoStack.push(redoCmmd);
		}
	}

	public void undo() {
		if (!undoStack.empty()) {
			Command undoCmmd = undoStack.pop();
			undoCmmd.undo();
			redoStack.push(undoCmmd);
		}
	}
}
