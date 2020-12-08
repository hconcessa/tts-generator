package br.com.hconcessa.tts.generator.gui;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import com.darkprograms.speech.synthesiser.SynthesiserV2;

import br.com.hconcessa.tts.generator.Main;
import br.com.hconcessa.tts.generator.util.ConvertFfmpeg;
import br.com.hconcessa.tts.generator.util.DefaultValues;
import br.com.hconcessa.tts.generator.util.FileUtil;
import br.com.hconcessa.tts.generator.util.Log;
import javafx.beans.property.StringProperty;
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
import javafx.scene.layout.Background;
import javafx.stage.FileChooser;

public class AudioController {

	private static SynthesiserV2 synthesizer = new SynthesiserV2("AIzaSyBOti4mM-6x9WDnZIjIeyEU21OpBXqWBgw");
	
	private Main main;
	private String format;
	private double pitch;
	private double speed;
	private String volume;
	private String channels;
	private String codec;
	private String sampleRate;
	private String bitRate;
	
	private double validPitchMax = 2.0;
	private double validSpeedMax = 2.0;
	
	private double validPitchMin = 0.0;
	private double validSpeedMin = 0.0;
	
	private boolean sucess = true;
	
	private boolean useFfmpeg = false;
	
	@FXML
	private SplitPane splitPane;
	
	@FXML
	private SplitMenuButton mediaFormat;
	
	@FXML
	private TextField txtAudioPitch;
	@FXML
	private TextField txtAudioSpeed;
	@FXML
	private TextField txtAudioVolume;
	@FXML
	private TextField txtAudioChannels;
	@FXML
	private TextField txtAudioCodec;
	@FXML
	private TextField txtAudioSampleRate;
	@FXML
	private TextField txtAudioBitRate;
	
	@FXML
	private CheckBox chkUseFfmpeg;
	
	@FXML
	private TextArea txtToTts;
	@FXML
	private TextArea logTxt;
	
	@FXML
	private Label log;
	@FXML
	private Label outputDir;
	
	@FXML
	private Button buttonGo;
	
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
		
		txtToTts.setWrapText(true);
		logTxt.setWrapText(true);
		
	}
	
	@FXML
	private void handleGo() {
		
		if (mediaFormat.getText().equals("Select..")) {
			logTxt.setStyle("-fx-background-color: red");
			logTxt.appendText(Log.addLog(Log.ERROR_TAG, "Select a format\n"));
			return;
		}
		if (txtToTts.getText().equals("") || txtToTts.getText() == null) {
			logTxt.setStyle("-fx-background-color: red");
			logTxt.appendText(Log.addLog(Log.ERROR_TAG, "Write a text\n"));
			return;
		}
		if (txtAudioSpeed.getText().equals("") || txtAudioSpeed.getText() == null) {
			logTxt.setStyle("-fx-background-color: red");
			logTxt.appendText(Log.addLog(Log.ERROR_TAG, "Write a Speed\n"));
			return;
		}
		if (txtAudioPitch.getText().equals("") || txtAudioPitch.getText() == null) {
			logTxt.setStyle("-fx-background-color: red");
			logTxt.appendText(Log.addLog(Log.ERROR_TAG, "Write a Pitch\n"));
			return;
		}
		
		String baseDir = FileUtil.getDirPath();
		
		getSimpleParam();
		synthesizer.setSpeed(this.speed);
		synthesizer.setPitch(this.pitch);
		
		try {
			InputStream audioStream = synthesizer.getMP3Data(txtToTts.getText());
			if(mediaFormat.getText() == DefaultValues.WAV_FORMAT) {
				File wavFile = new File(baseDir, DefaultValues.OUTPUT_FILE_WAV);
				FileUtil.copyInputStreamToFile(audioStream, wavFile);
				// Delete the old audio
				new File(baseDir, DefaultValues.OUTPUT_FILE_MP3).delete();
			} else {
				File mp3File = new File(baseDir, DefaultValues.OUTPUT_FILE_MP3);
				FileUtil.copyInputStreamToFile(audioStream, mp3File);
				// Delete the old audio
				new File(baseDir, DefaultValues.OUTPUT_FILE_WAV).delete();
			}
			
		} catch (IOException e) {
			e.printStackTrace();
			sucess = false;
		}
		
		/*
		if(useFfmpeg) {
			audioPitch.setText("com ffmpeg");
			sucess = ConvertFfmpeg.convertAudio(DefaultValues.OUTPUT_FILE_MP3, DefaultValues.OUTPUT_FILE_MP3_FFMPEG, DefaultValues.MP3_FORMAT);
		} else {
			audioPitch.setText("sem ffmpeg");
		}
		*/
		
		if(sucess) {
			logTxt.setStyle("-fx-background-color: green");
			logTxt.appendText(Log.addLog(Log.INFO_TAG, "Successfully generated audio\n"));
			outputDir.setText(baseDir);
		} else {
			logTxt.setStyle("-fx-background-color: red");
			logTxt.appendText(Log.addLog(Log.ERROR_TAG, "Problem occurred when generating audio\n"));
		}
	}
	
	private void getSimpleParam() {
		
		this.speed = Double.parseDouble(txtAudioSpeed.getText());
		this.pitch = Double.parseDouble(txtAudioPitch.getText());
		
		if(this.speed > validSpeedMax || this.speed < validSpeedMin) {
			logTxt.setStyle("-fx-background-color: yellow");
			logTxt.appendText(Log.addLog(Log.WARNING_TAG, "Invalid speed, a default value will be use (1.0). Valid: 0.0~2.0\n"));
			this.speed = 1.0;
		}
		if(this.pitch > validPitchMax|| this.pitch < validPitchMin) {
			logTxt.setStyle("-fx-background-color: yellow");
			logTxt.appendText(Log.addLog(Log.WARNING_TAG, "Invalid pitch, a default value will be use (1.0). Valid: 0.0~2.0\n"));
			this.pitch = 1.0;
		}
		
	}

	@FXML
	private void handleUseFfmpeg() {
		this.useFfmpeg = chkUseFfmpeg.isSelected();
	}
	
	@FXML
	private void handleTxtLog() {
	}
	
	public void setMain(Main main) {
		this.main = main;
	}
	
	
}
