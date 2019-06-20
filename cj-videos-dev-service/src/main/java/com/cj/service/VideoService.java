package com.cj.service;

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
	public PagedResult getAllVideos(Integer page, Integer pageSize);
}
