package com.cj.mapper;

import java.util.List;

import com.cj.pojo.Videos;
import com.cj.pojo.vo.VideosVO;
import com.cj.utils.MyMapper;

public interface VideosMapperCustom extends MyMapper<Videos> {
	
	public List<VideosVO> queryAllVideos();
}