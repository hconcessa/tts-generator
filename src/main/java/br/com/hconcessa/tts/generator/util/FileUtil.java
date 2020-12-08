package br.com.hconcessa.tts.generator.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.UnsupportedAudioFileException;

public class FileUtil {

	
	/**
	 * Write a data stream in the file
	 * 
	 * @param inputStream Stream to save
	 * @param file	File to write the stream data
	 * @throws IOException
	 */
	public static void copyInputStreamToFile(InputStream inputStream, File file) throws IOException {

        try (FileOutputStream outputStream = new FileOutputStream(file)) {

            int read;
            byte[] bytes = new byte[1024];

            while ((read = inputStream.read(bytes)) != -1) {
                outputStream.write(bytes, 0, read);
            }
        }

    }
	
	/**
	 * 
	 * Convert a mp3 file to a new wav file.
	 * 
	 * @param mp3Data	File mp3 to convert
	 * @param baseDir	Dir base
	 * @param fileName	File name to save in Dir base
	 * @throws UnsupportedAudioFileException
	 * @throws IOException
	 */
	public static void mp3ToWav(File mp3Data, String baseDir, String fileName) throws UnsupportedAudioFileException, IOException {
        // open stream
        AudioInputStream mp3Stream = AudioSystem.getAudioInputStream(mp3Data);
        AudioFormat sourceFormat = mp3Stream.getFormat();

        // create audio format object for the desired stream/audio format
        // this is *not* the same as the file format (wav)
        AudioFormat convertFormat = new AudioFormat(AudioFormat.Encoding.PCM_SIGNED, (float) 24600.0, 16,
                sourceFormat.getChannels(), sourceFormat.getChannels() * 2, (float) 24600.0, false);
        // create stream that delivers the desired format
        AudioInputStream converted = AudioSystem.getAudioInputStream(convertFormat, mp3Stream);
        // write stream into a file with file format wav
        AudioSystem.write(converted, AudioFileFormat.Type.WAVE, new File(baseDir, fileName));
    }
	
	public static String getDirPath() {
		File dir = new File(DefaultValues.HOME_USER_DIR, DefaultValues.DIR_TTS);
        dir.mkdirs();
        
        return dir.getAbsolutePath();
	}
	
}
