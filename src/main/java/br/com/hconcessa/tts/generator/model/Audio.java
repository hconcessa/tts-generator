package br.com.hconcessa.tts.generator.model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Audio {
	
	private StringProperty format;
	private StringProperty pitch;
	private StringProperty speed;
	private StringProperty volume;
	private StringProperty channels;
	private StringProperty codec;
	private StringProperty sampleRate;
	private StringProperty bitRate;
	
	
	public Audio(String format, String pitch, String speed, String volume,
			String channels, String codec, String sampleRate, String bitRate) {
		super();
		this.format = new SimpleStringProperty(format);
		this.pitch = new SimpleStringProperty(pitch);
		this.speed = new SimpleStringProperty(speed);
		this.volume = new SimpleStringProperty(volume);
		this.channels = new SimpleStringProperty(channels);
		this.codec = new SimpleStringProperty(codec);
		this.sampleRate = new SimpleStringProperty(sampleRate);
		this.bitRate = new SimpleStringProperty(bitRate);
	}
	
	public Audio() {
		this(null, null, null, null, null, null, null, null);
	}

	public StringProperty getFormatProperty() {
		return this.format;
	}
	
	public String getFormat() {
		return this.format.get();
	}

	public StringProperty getPitchProperty() {
		return this.pitch;
	}
	
	public String getPitch() {
		return this.pitch.get();
	}

	public StringProperty getSpeedProperty() {
		return this.speed;
	}
	
	public String getSpeed() {
		return this.speed.get();
	}

	public StringProperty getVolumeProperty() {
		return this.volume;
	}
	
	public String getVolume() {
		return this.volume.get();
	}

	public StringProperty getChannelsProperty() {
		return this.channels;
	}
	
	public String getChannels() {
		return this.channels.get();
	}

	public StringProperty getCodecProperty() {
		return this.codec;
	}
	
	public String getCodec() {
		return this.codec.get();
	}

	public StringProperty getSampleRateProperty() {
		return this.sampleRate;
	}
	
	public String getSampleRate() {
		return this.sampleRate.get();
	}

	public StringProperty getBitRateProperty() {
		return bitRate;
	}
	
	public String getBitRate() {
		return this.bitRate.get();
	}
	
}
