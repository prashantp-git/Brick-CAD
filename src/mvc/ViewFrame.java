package mvc;

import javax.swing.*;

public class ViewFrame extends JInternalFrame {
	private static final long serialVersionUID = 1L;
	private static int openFrameCount = 0;

	public ViewFrame(View view) {
		super("Frame #" + (++openFrameCount) + ": " + view.getViewName(), true, // resizable
				true, // closable
				true, // maximizable
				true);// iconifiable
		setSize(600, 600);
		setContentPane(view);
		setLocation(30 * openFrameCount, 30 * openFrameCount);
		pack();
	}

	public static void setOpenFrameCount(int openFrameCount) {
		ViewFrame.openFrameCount = openFrameCount;
	}

}
