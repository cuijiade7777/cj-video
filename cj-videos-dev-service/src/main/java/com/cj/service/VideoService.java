package com.cj.service;

import java.util.List;

import com.cj.pojo.Videos;
import com.cj.utils.PagedResult;

public interface VideoService {
	
	/**
	 * 保存视频
	 */
	public String saveVideo(Videos video);
	
	/**
	 * 修改视频封面
	 */
	public void updateVideo(String videoId, String coverPath);
	
	/**
	 * 分页查询视频列表
	 */
	public PagedResult getAllVideos(Videos video, Integer isSaveRecord, Integer page, Integer pageSize);

	public List<String> getHotWords();
}
