package mc.evan.gui;

import java.io.PrintStream;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import javafx.animation.FadeTransition;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextAreaBuilder;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Duration;
import mc.evan.LauncherMain;
import mc.evan.core.Run;
import mc.evan.util.FileUtil;

public class Buttons {

	public static ProgressBar pb;
	public static Pane csole;
	
	static double centerX = 225 - 55.5;
	static double centerY = 225 - 55.5;

	static FadeTransition fadeQuit;
	static FadeTransition fadePlay;

	public static void init(Pane root, final Pane csole,
			final Stage primaryStage) {
		final ImageView quitbtn = new ImageView(
				new Image(
						LauncherMain.class
								.getResourceAsStream("assets/quitbutton.png")));
		final ImageView playbtn = new ImageView(new Image(
				LauncherMain.class
						.getResourceAsStream("assets/updatebutton.png")));

		final Rectangle barTop = new Rectangle();
		
		barTop.setFill(Color.BLACK);
		barTop.setWidth(800);
		barTop.setHeight(20);
		
		fadeQuit = new FadeTransition(Duration.seconds(1), quitbtn);
		fadeQuit.setFromValue(100);
		fadeQuit.setToValue(0);
		fadeQuit.setRate(2.0);

		fadePlay = new FadeTransition(Duration.seconds(1), playbtn);
		fadePlay.setFromValue(100);
		fadePlay.setToValue(0);
		fadePlay.setRate(2.0);

		playbtn.setX(centerX - 83.25);
		playbtn.setY(centerY);

		quitbtn.setX(centerX + 83.25);
		quitbtn.setY(centerY);

		quitbtn.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {

				fadeQuit.play();

				fadeQuit.setOnFinished(new EventHandler<ActionEvent>() {
					@Override
					public void handle(ActionEvent event) {

						System.exit(0);
					}
				});
			}
		});

		playbtn.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				fadePlay.play();

				fadePlay.setOnFinished(new EventHandler<ActionEvent>() {

					@Override
					public void handle(ActionEvent event) {

						initConsole(primaryStage, quitbtn, barTop);

					}

				});

			}
		});

		root.getChildren().addAll(quitbtn, playbtn);

	}

	public static void initConsole(final Stage primaryStage,
			final ImageView quitbtn, final Rectangle barTop) {
		csole = new Pane();

		GUI.makeDragable(primaryStage, csole);

		TextArea ta = TextAreaBuilder.create().prefWidth(800)
				.prefHeight(580).wrapText(true).build();

		Console console = new Console(ta);
		PrintStream ps = new PrintStream(console, true);
		System.setOut(ps);
		System.setErr(ps);
		
		ta.setLayoutY(20);
		
		quitbtn.setX((800 - 111) - 20);
		quitbtn.setY((600 - 111) - 5);
		
		//Progress Bar
		pb = new ProgressBar();
		
		pb.setMinSize(300, 25);
		pb.setMaxSize(300, 25);
		
		pb.setLayoutX(800 - 350);
		pb.setLayoutY(50);
		
		ProgressListener();
		
		csole.getChildren().addAll(ta, quitbtn, barTop, pb);
		Scene sceneConsole = new Scene(csole, 800, 600);
		primaryStage.setScene(sceneConsole);
		primaryStage.centerOnScreen();

		ExecutorService ex = Executors.newCachedThreadPool();
		@SuppressWarnings("rawtypes")
		Callable callable = (new Run());
		@SuppressWarnings({ "unchecked", "rawtypes" })
		Future close = ex.submit(callable);
		try {
			if (Boolean.parseBoolean(close.get(500, TimeUnit.MILLISECONDS).toString()) == true) {
				primaryStage.close();
				Run.updateLauncher(); 
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		} catch (TimeoutException e) {
		}
	}
	
	public static void ProgressListener(){
		
		Task<Void> task = new Task<Void>() {
		      @Override public Void call() {
		        
		          updateProgress(FileUtil.cpt, 100);
		        
		        return null;
		      }
		    };
		
	
		pb.progressProperty().bind(task.progressProperty());
		
	}
	
	

}
