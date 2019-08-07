package com.cj.mapper;

import java.util.List;

import com.cj.pojo.Comments;
import com.cj.pojo.vo.CommentsVO;
import com.cj.utils.MyMapper;

public interface CommentsMapperCustom extends MyMapper<Comments> {

	List<CommentsVO> queryComments(String videoId);
	
}