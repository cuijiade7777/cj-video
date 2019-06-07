package com.cj.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class FFpegTest {
	
	private String ffmpegEXE;
	
	public FFpegTest(String ffmpegEXE) {
		super();
		this.ffmpegEXE = ffmpegEXE;
	}
	
	public void convertor(String videoInputPath, String videoOutputPath) throws IOException {
		
		List<String> command = new ArrayList<>();
		command.add(ffmpegEXE);
		command.add("-i");
		command.add(videoInputPath);
		command.add(videoOutputPath);
		
		ProcessBuilder builder = new ProcessBuilder(command);
		Process process = builder.start();
		InputStream errorStream = process.getErrorStream();
		InputStreamReader inputStreamReader = new InputStreamReader(errorStream);
		BufferedReader br = new BufferedReader(inputStreamReader);
		String line = "";
		while((line = br.readLine()) != null) {
		}
		
		if(br != null) {
			br.close();
		}
		if(inputStreamReader != null) {
			inputStreamReader.close();
		}
		if(errorStream != null) {
			errorStream.close();
		}
		
	}

	public static void main(String[] args) {
		FFpegTest ffpegTest = new FFpegTest("D:\\backup\\ffmpeg\\bin\\ffmpeg.exe");
		try {
			ffpegTest.convertor("D:\\houbei.mp4", "D:\\houbei.avi");
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}
