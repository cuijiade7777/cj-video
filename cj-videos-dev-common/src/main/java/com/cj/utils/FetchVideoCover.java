package com.cj.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class FetchVideoCover {
	
	//视频路径
	private String ffmpegEXE;
	
	public void getCover(String videoInputPath, String coverOutputPath) throws IOException {
		
		List<String> command = new ArrayList<String>();
		command.add(ffmpegEXE);
		
		command.add("-ss");
		command.add("00:00:01");
		
		command.add("-y");
		command.add("-i");
		command.add(videoInputPath);
		
		command.add("-vframes");
		command.add("1");
		command.add(coverOutputPath);
		
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
	
	public FetchVideoCover(String ffempegEXE) {
		this.ffmpegEXE = ffempegEXE;
	}
	
	public static void main(String[] args) throws IOException {
		FetchVideoCover fetchVideoCover = new FetchVideoCover("D:\\backup\\ffmpeg\\bin\\ffmpeg.exe");
		fetchVideoCover.getCover("D:\\cj_videos_dev\\190528DMWPGDHZ2W\\video\\2db135f6-5338-4d0f-8bbc-a3f8472bef85.mp4", "D:\\cj_videos_dev\\190528DMWPGDHZ2W\\video\\2db135f6-5338-4d0f-8bbc-a3f8472bef85.jpg");
	}
	
}
