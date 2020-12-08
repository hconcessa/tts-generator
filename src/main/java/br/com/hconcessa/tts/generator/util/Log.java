package br.com.hconcessa.tts.generator.util;

public class Log {

	public static final String ERROR_TAG = "Error";
	public static final String INFO_TAG = "Info";
	public static final String WARNING_TAG = "Warning";
	
	
	public static String addLog(String tag, String message) {
		return String.format("[%s] %s", tag, message);
	}
	
}
