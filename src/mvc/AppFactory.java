package mvc;

public interface AppFactory {
		public Model makeModel();
		public View makeView(String cmmd);
		public Command makeCommand(String cmmd);
		public String[] getViews();
		public String[] getCommands();
		public String getTitle();
		public String getHelp();
		public String about();
}
