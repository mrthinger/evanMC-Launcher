package mc.evan.gui;

import java.util.HashMap;
import java.util.Map.Entry;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import mc.evan.LauncherMain;
import mc.evan.reference.Links;
import mc.evan.util.JSONHandler;
import mc.evan.util.LWPI;

public class GUI {

	public static boolean hidden = false;
	static double xOffset;
	static double yOffset;

	public static HashMap<String, String> pack;
	public static String choice;
	private static String[] keys;
	private static String[] values;
	private static ChoiceBox<String> cb;

	public static Pane root;
	private static Stage stage;

	private static double centerX = 225 - 55.5;
	private static double centerY = 225 - 55.5;

	public static void init(Stage primaryStage) {

		try {

			root = new Pane();
			Pane csole = new Pane();
			primaryStage.initStyle(StageStyle.TRANSPARENT);
			primaryStage.setTitle("evanMC Launcher");

			ImageView bg = new ImageView(new Image(
					LauncherMain.class.getResourceAsStream("assets/background.png")));

			//TODO: Make title banner thingy
			
			
			
			// Enter packs here
			pack = new HashMap<>();
			pack.put("evanMC", Links.EVANMC);
			//pack.put("Magi Adventure Pack", Links.MAP);

			JSONHandler.readPacks();

			addBoxInit();

			Button addPack = new Button("Add Pack");
			addPack.setLayoutX(centerX - 50);
			addPack.setLayoutY(centerY + 200);
			addPack.setMinWidth(200);

			addPack.setOnMouseClicked(new EventHandler<MouseEvent>() {
				@Override
				public void handle(MouseEvent arg0) {
					stage.show();
				}
			});

			root.getChildren().addAll(bg, addPack);

			Buttons.init(root, csole, primaryStage);

			Scene sceneMain = new Scene(root, 450, 450);
			sceneMain.setFill(null);
			primaryStage.setScene(sceneMain);

			refreshPacks();

			makeDragable(primaryStage, root);

			primaryStage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void refreshPacks() {

		keys = new String[pack.size()];
		values = new String[pack.size()];
		int i = 0;
		for (Entry<String, String> mapEntry : pack.entrySet()) {
			keys[i] = mapEntry.getKey();
			values[i] = mapEntry.getValue();
			i++;
		}

		root.getChildren().remove(cb);
		cb = new ChoiceBox<String>(FXCollections.observableArrayList(keys));

		// Sets default pack to evanMC
		cb.getSelectionModel().select("evanMC");
		choice = Links.EVANMC;

		cb.setMinWidth(200);
		cb.setLayoutX(centerX - 50);
		cb.setLayoutY(centerY + 170);

		cb.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {

			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue,
					String newValue) {

				int element = cb.getSelectionModel().getSelectedIndex();

				choice = values[element];

			}
		});

		root.getChildren().addAll(cb);

	}

	private static void addBoxInit() {
		stage = new Stage();
		Pane promptBox = new Pane();

		Button addbtn = new Button("Add Pack");
		addbtn.setLayoutX(512.5);
		addbtn.setLayoutY(15);

		final TextField packField = new TextField();
		double width = 500;
		double hieght = 30;

		packField.setMaxHeight(hieght);
		packField.setMaxWidth(width);
		packField.setMinHeight(hieght);
		packField.setMinWidth(width);

		packField.setLayoutY(10);
		packField.setLayoutX(5);

		packField.setPromptText("Enter pack link");

		packField.setOnKeyPressed(new EventHandler<KeyEvent>() {

			@Override
			public void handle(KeyEvent event) {
				if (event.getCode().equals(KeyCode.ENTER)) {

					String text = packField.getText();
					pack.put("Loading...", text);
					refreshPacks();

					stage.close();

					LWPI lwpi = new LWPI(text);
					lwpi.run();

				}

			}
		});

		addbtn.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent arg0) {

				String text = packField.getText();
				pack.put("Loading...", text);
				refreshPacks();

				stage.close();

				LWPI lwpi = new LWPI(text);
				lwpi.run();
			}
		});

		promptBox.getChildren().addAll(addbtn, packField);
		Scene prompt = new Scene(promptBox, 600, 50);
		stage.setScene(prompt);
	}

	public static void makeDragable(final Stage primaryStage, Pane pane) {
		pane.setOnMousePressed(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				xOffset = event.getSceneX();
				yOffset = event.getSceneY();
			}
		});
		pane.setOnMouseDragged(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				primaryStage.setX(event.getScreenX() - xOffset);
				primaryStage.setY(event.getScreenY() - yOffset);
			}
		});
	}

}
