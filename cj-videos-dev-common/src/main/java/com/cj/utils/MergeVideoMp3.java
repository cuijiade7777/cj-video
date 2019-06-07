package com.cj.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class MergeVideoMp3 {
	
	private String ffmpegEXE;
	
	public MergeVideoMp3(String ffmpegEXE) {
		super();
		this.ffmpegEXE = ffmpegEXE;
	}
	
	public void convertor(String mp3OutputPath, String videoInputPath,
						  double seconds, String videoOutputPath) throws IOException {
		
		List<String> command = new ArrayList<>();
		command.add(ffmpegEXE);
		command.add("-i");
		command.add(mp3OutputPath);
		command.add("-i");
		command.add(videoInputPath);
		command.add("-t");
		command.add(String.valueOf(seconds));
		command.add("-y");
		command.add(String.valueOf(videoOutputPath));
		
		ProcessBuilder builder = new ProcessBuilder(command);
		Process process = builder.start();
		InputStream errorStream = process.getErrorStream();
		InputStreamReader inputStreamReader = new InputStreamReader(errorStream);
		BufferedReader br = new BufferedReader(inputStreamReader);
		while((br.readLine()) != null) {
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
		MergeVideoMp3 mergeVideoMp3 = new MergeVideoMp3("D:\\backup\\ffmpeg\\bin\\ffmpeg.exe");
		try {
			mergeVideoMp3.convertor("D:\\backup\\ffmpeg\\bin\\aihe-dj.mp3", "D:\\backup\\ffmpeg\\bin\\houbei.mp4", 14.9, "D:\\backup\\ffmpeg\\bin\\a.mp4");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
