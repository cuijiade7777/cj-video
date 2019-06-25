package com.cj.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.cj.pojo.Videos;
import com.cj.pojo.vo.VideosVO;
import com.cj.utils.MyMapper;

public interface VideosMapperCustom extends MyMapper<Videos> {
	
	public List<VideosVO> queryAllVideos(@Param("videoDesc")String desc);
}