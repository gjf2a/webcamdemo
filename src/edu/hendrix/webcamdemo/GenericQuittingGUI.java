package edu.hendrix.webcamdemo;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class GenericQuittingGUI extends Application {
	private static String fxmlFilename;
	
	public static void setup(String fxmlFilename) {
		GenericQuittingGUI.fxmlFilename = fxmlFilename;
	}
	
	@Override
	public void start(Stage primaryStage) {
		try {
			FXMLLoader loader = new FXMLLoader();
			BorderPane root = (BorderPane) loader.load(getClass().getResource(fxmlFilename).openStream());
			primaryStage.setScene(new Scene(root));
			primaryStage.show();
			
			Quittable controller = loader.getController();
			primaryStage.setOnCloseRequest(we -> {
				controller.quit(); 
			});
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(1);
		}
	}
}
