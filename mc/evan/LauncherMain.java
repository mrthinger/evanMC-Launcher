package mc.evan;

import javafx.application.Application;
import javafx.stage.Stage;
import mc.evan.core.Setup;
import mc.evan.gui.GUI;
import mc.evan.maintain.ConfigHandler;

public class LauncherMain extends Application {

	@Override
	public void start(Stage primaryStage) {
		System.out.println("Launching...");
		
		Setup.init();

		ConfigHandler.preInit();


		
		GUI.init(primaryStage);


	}

	public static void main(String[] args) throws InterruptedException {
		launch(args);
	}
}
