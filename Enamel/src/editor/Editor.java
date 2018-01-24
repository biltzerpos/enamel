package editor;


import javafx.scene.control.MenuItem;

import java.io.File;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class Editor extends Application {
	


	@Override
	public void start(Stage primaryStage) throws Exception {
		
		//set scene and layout
		StackPane root = new StackPane();
		Scene scene = new Scene(root);
		primaryStage.centerOnScreen();
		primaryStage.setHeight(1000);
		primaryStage.setWidth(1000);
		primaryStage.setScene(scene);
		
		
		
		//add menu bar
		VBox vbox = new VBox();
		MenuBar menuBar = new MenuBar();
		
		//menu file
		Menu menuFile = new Menu("File");
		MenuItem fileOpen = new MenuItem("Open file...");
		MenuItem fileExit = new MenuItem("Exit");
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Open file...");
		fileChooser.setInitialDirectory(new File("FactoryScenarios/"));
		menuFile.setOnAction(e -> fileChooser.showOpenDialog(primaryStage));
		
		menuFile.getItems().addAll(fileOpen, fileExit);
		
		//menu edit
		Menu menuEdit = new Menu("Edit");
		//menu view
		Menu menuView = new Menu("View");
		//finalize menu
		menuBar.getMenus().addAll(menuFile, menuEdit, menuView);
		vbox.getChildren().add(menuBar);
		root.getChildren().add(vbox);
		
		//show stage
		primaryStage.show();
		
	}


}
