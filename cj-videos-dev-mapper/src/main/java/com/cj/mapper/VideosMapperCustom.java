package com.cj.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.cj.pojo.Videos;
import com.cj.pojo.vo.VideosVO;
import com.cj.utils.MyMapper;

public interface VideosMapperCustom extends MyMapper<Videos> {
	
	public List<VideosVO> queryAllVideos(@Param("videoDesc")String desc, @Param("userId")String userId);
	
	/**
	 * @Description:对视频喜欢的数量进行累加
	 */
	public void addVideoLikeCount(String videoId);
	
	/**
	 * @Description:对视频喜欢的数量进行累减
	 */
	public void reduceVideoLikeCount(String videoId);

	public List<VideosVO> queryMyLikeVideos(String userId);

	public List<VideosVO> queryMyFollowVideos(String userId);
}