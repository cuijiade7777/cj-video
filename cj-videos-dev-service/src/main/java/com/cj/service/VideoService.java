package com.cj.service;

import com.cj.pojo.Videos;

public interface VideoService {
	
	/**
	 * 保存视频
	 */
	public String saveVideo(Videos video);
	
	/**
	 * 修改视频封面
	 */
	public void updateVideo(String videoId, String coverPath);
}
