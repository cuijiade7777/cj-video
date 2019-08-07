package com.cj.service;

import java.util.List;

import com.cj.pojo.Comments;
import com.cj.pojo.Videos;
import com.cj.utils.PagedResult;

public interface VideoService {
	
	/**
	 * @Description:保存视频
	 */
	public String saveVideo(Videos video);
	
	/**
	 * @Description:修改视频封面
	 */
	public void updateVideo(String videoId, String coverPath);
	
	/**
	 * @Description:分页查询视频列表
	 */
	public PagedResult getAllVideos(Videos video, Integer isSaveRecord, Integer page, Integer pageSize);

	public List<String> getHotWords();
	
	/**
	 * @Description:用户喜欢/点赞视频
	 */
	public void userLikeVideo(String userId, String videoId, String videoCreateId);
	
	/**
	 * @Description:用户不喜欢/取消点赞视频
	 */
	public void userUnLikeVideo(String userId, String videoId, String videoCreateId);

	public PagedResult queryMyLikeVideos(String userId, Integer page, Integer pageSize);

	public PagedResult queryMyFollowVideos(String userId, Integer page, int pageSize);

	public void saveComment(Comments comment);

	public PagedResult getAllComments(String videoId, Integer page, Integer pageSize);
}
