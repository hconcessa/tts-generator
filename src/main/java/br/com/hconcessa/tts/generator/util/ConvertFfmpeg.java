package br.com.hconcessa.tts.generator.util;

import java.io.IOException;

import net.bramp.ffmpeg.FFmpeg;
import net.bramp.ffmpeg.FFmpegExecutor;
import net.bramp.ffmpeg.FFprobe;
import net.bramp.ffmpeg.builder.FFmpegBuilder;

public class ConvertFfmpeg {

	
	public static boolean convertAudio(String fileNameToConvert, String outputFile, String format) {
		
		try {
			
			FFmpeg ffmpeg = new FFmpeg("C:\\Users\\hconcessa\\.tc-server\\ffmpeg.exe");
			FFprobe ffprobe = new FFprobe("C:\\Users\\hconcessa\\.tc-server\\");
			
			FFmpegBuilder builder = new FFmpegBuilder()

					  .setInput(fileNameToConvert)     // Filename, or a FFmpegProbeResult
					  .overrideOutputFiles(true) // Override the output if it exists

					  .addOutput(outputFile)   // Filename for the destination
					    .setFormat(format)        // Format is inferred from filename, or can be set

					    .disableSubtitle()       // No subtiles

					    .setAudioChannels(1)         // Mono audio
					    .setAudioCodec("aac")        // using the aac codec
					    .setAudioSampleRate(48_000)  // at 48KHz
					    .setAudioBitRate(32768)      // at 32 kbit/s

					    .setStrict(FFmpegBuilder.Strict.EXPERIMENTAL) // Allow FFmpeg to use experimental specs
					    .done();

					FFmpegExecutor executor = new FFmpegExecutor(ffmpeg, ffprobe);

					// Run a one-pass encode
					executor.createJob(builder).run();

					// Or run a two-pass encode (which is better quality at the cost of being slower)
					executor.createTwoPassJob(builder).run();
			
			
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}	
	
}
