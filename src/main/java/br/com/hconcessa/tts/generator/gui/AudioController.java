package br.com.hconcessa.tts.generator.gui;

import java.io.File;

import br.com.hconcessa.tts.generator.Main;
import br.com.hconcessa.tts.generator.util.DefaultValues;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SplitMenuButton;
import javafx.scene.control.SplitPane;
import javafx.scene.control.SplitPane.Divider;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;

public class AudioController {

	
	
	private Main main;
	
	private boolean useFfmpeg = false;
	
	@FXML
	private SplitPane splitPane;
	
	@FXML
	private SplitMenuButton mediaFormat;
	
	@FXML
	private TextField audioPitch;
	@FXML
	private TextField audioSpeed;
	@FXML
	private TextField audioVolume;
	@FXML
	private TextField audioChannels;
	@FXML
	private TextField audioCodec;
	@FXML
	private TextField audioSampleRate;
	@FXML
	private TextField audioBitRate;
	
	@FXML
	private CheckBox chkUseFfmpeg;
	
	@FXML
	private TextArea txtToTts;
	
	@FXML
	private Label ttsDir;
	@FXML
	private Label outputDir;
	
	@FXML
	private Button buttonGo;
	@FXML
	private Button buttonSaveAs;
	
	public void AudioController() {}
	
	/**
	 * Is a native method, not need be called
	 */
	@FXML
	private void initialize() {
		
		// Block resize the divider on splitPane
		Divider divider = splitPane.getDividers().get(0);
	    divider.positionProperty().addListener(new ChangeListener<Number>()      
	    {             
	        @Override 
	        public void changed( ObservableValue<? extends Number> observable, Number oldvalue, Number newvalue )
	        {
	            divider.setPosition(0.35);
	        }
	    }); 
		
		MenuItem mp3Format = new MenuItem(DefaultValues.MP3_FORMAT);
		MenuItem wavFormat = new MenuItem(DefaultValues.WAV_FORMAT);
		mediaFormat.getItems().clear();
		mediaFormat.getItems().addAll(mp3Format, wavFormat);
		
		mp3Format.setOnAction ((e) -> {
			mediaFormat.setText(DefaultValues.MP3_FORMAT);
		});
		wavFormat.setOnAction ((e) -> {
			mediaFormat.setText(DefaultValues.WAV_FORMAT);
		});
		
		chkUseFfmpeg.setSelected(this.useFfmpeg);
		
	}
	
	@FXML
	private void handleSaveAs() {
		
		FileChooser fileChooser = new FileChooser();
		 
        //Set extension filter for text files
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("MP3 files (*.mp3)", "*.mp3");
        fileChooser.setInitialFileName("audio");
        fileChooser.getExtensionFilters().add(extFilter);
        
        //Show save file dialog
        File file = fileChooser.showSaveDialog(main.getPrimaryStage());

        if (file != null) {
            saveFile(file);
        }
	}
	
	@FXML
	private void handleGo() {
		if(useFfmpeg) {
			audioPitch.setText("com ffmpeg");
		} else {
			audioPitch.setText("sem ffmpeg");
		}
	}
	
	@FXML
	private void handleUseFfmpeg() {
		
		this.useFfmpeg = chkUseFfmpeg.isSelected();
		
	}
	
	public void setMain(Main main) {
		this.main = main;
	}
	
	private void saveFile(File file) {
		//TODO
    }
}
