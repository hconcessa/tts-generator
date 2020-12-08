package br.com.hconcessa.tts.generator;
	
import java.io.IOException;

import br.com.hconcessa.tts.generator.gui.AudioController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;


public class Main extends Application {
	
	private final String VERSION_APPLICATION = "1.0.0";
	private final String TITLE_APPLICATION = "TTS Generator " + VERSION_APPLICATION;
	
	private Stage primaryStage;
	private AnchorPane application;
	
	@Override
	public void start(Stage primaryStage) {
		
		this.primaryStage = primaryStage;
		this.getPrimaryStage().setTitle(TITLE_APPLICATION);
		
		initMainStage();
		
		
		
	}
	
	private void initMainStage() {
		try {
			
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(Main.class.getResource("gui/Application.fxml"));
			this.application = (AnchorPane) loader.load();
			
			Scene cena = new Scene(this.application);
			this.getPrimaryStage().setResizable(false);
			getPrimaryStage().setWidth(600);
			getPrimaryStage().setHeight(425);
			this.getPrimaryStage().setScene(cena);
			this.getPrimaryStage().show();
			
			
			AudioController controller = loader.getController();
			controller.setMain(this);
			//controller.initialize();
			
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}

	public static void main(String[] args) {
		launch(args);
	}

	public Stage getPrimaryStage() {
		return primaryStage;
	}
}
