package com.cj.service.impl;

import java.util.List;

import org.n3r.idworker.Sid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.cj.mapper.SearchRecordsMapper;
import com.cj.mapper.UsersLikeVideosMapper;
import com.cj.mapper.UsersMapper;
import com.cj.mapper.VideosMapper;
import com.cj.mapper.VideosMapperCustom;
import com.cj.pojo.SearchRecords;
import com.cj.pojo.UsersLikeVideos;
import com.cj.pojo.Videos;
import com.cj.pojo.vo.VideosVO;
import com.cj.service.VideoService;
import com.cj.utils.PagedResult;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.entity.Example.Criteria;

@Service
public class VideoServiceImpl implements VideoService {
	
	@Autowired
	private VideosMapper videosMapper;
	
	@Autowired
	private VideosMapperCustom videosMapperCustom;
	
	@Autowired
	private SearchRecordsMapper searchRecordsMapper;
	
	@Autowired
	private UsersLikeVideosMapper usersLikeVideosMapper;
	
	@Autowired
	private UsersMapper usersMapper;
	
	@Autowired
	private Sid sid;

	@Transactional(propagation = Propagation.REQUIRED)
	@Override
	public String saveVideo(Videos video) {
		String id = sid.nextShort();
		video.setId(id);
		videosMapper.insertSelective(video);
		return id;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	@Override
	public void updateVideo(String videoId, String coverPath) {
		Videos video = new Videos();
		video.setId(videoId);
		video.setCoverPath(coverPath);
		videosMapper.updateByPrimaryKeySelective(video);
	}

	@Transactional(propagation = Propagation.SUPPORTS)
	@Override
	public PagedResult getAllVideos(Videos video, Integer isSaveRecord, Integer page, Integer pageSize) {
		
		String desc = video.getVideoDesc();
		String userId = video.getUserId();
		
		//保存热搜词
		if (isSaveRecord != null && isSaveRecord == 1) {
			SearchRecords record = new SearchRecords();
			String id = sid.nextShort();
			record.setId(id);
			record.setContent(desc);
			searchRecordsMapper.insert(record);
		}
		
		PageHelper.startPage(page, pageSize);
		List<VideosVO> list = videosMapperCustom.queryAllVideos(desc, userId);
		PageInfo<VideosVO> pageList = new PageInfo<>(list);
		PagedResult pagedResult = new PagedResult();
		pagedResult.setPage(page);
		pagedResult.setTotal(pageList.getPages());
		pagedResult.setRecords(pageList.getTotal());
		pagedResult.setRows(list);
		return pagedResult;
	}

	@Transactional(propagation=Propagation.SUPPORTS)
	@Override
	public List<String> getHotWords() {
		return searchRecordsMapper.getHotWords();
	}

	@Transactional(propagation=Propagation.REQUIRED)
	@Override
	public void userLikeVideo(String userId, String videoId, String videoCreateId) {
		//1.保存用户和视频的喜欢点赞关系
		String likeId = Sid.next();
		UsersLikeVideos usersLikeVideos = new UsersLikeVideos();
		usersLikeVideos.setId(likeId);
		usersLikeVideos.setUserId(userId);
		usersLikeVideos.setVideoId(videoId);
		usersLikeVideosMapper.insert(usersLikeVideos);
		
		//2.视频喜欢数量累加
		videosMapperCustom.addVideoLikeCount(videoId);
		
		//3.用户受喜欢数量累加
		usersMapper.addReceiveLikeCount(userId);
	}

	@Transactional(propagation=Propagation.REQUIRED)
	@Override
	public void userUnLikeVideo(String userId, String videoId, String videoCreateId) {
		//1.删除用户和视频的喜欢点赞关系
		Example example = new Example(UsersLikeVideos.class);
		Criteria criteria = example.createCriteria();
		criteria.andEqualTo("userId", userId);
		criteria.andEqualTo("videoId", videoId);
		usersLikeVideosMapper.deleteByExample(example);
		
		//2.视频喜欢数量累减
		videosMapperCustom.reduceVideoLikeCount(videoId);
		
		//3.用户受喜欢数量累减
		usersMapper.reduceReceiveLikeCount(userId);
		
	}

	@Transactional(propagation = Propagation.SUPPORTS)
	@Override
	public PagedResult queryMyLikeVideos(String userId, Integer page, Integer pageSize) {
		PageHelper.startPage(page, pageSize);
		List<VideosVO> list = videosMapperCustom.queryMyLikeVideos(userId);
				
		PageInfo<VideosVO> pageList = new PageInfo<>(list);
		
		PagedResult pagedResult = new PagedResult();
		pagedResult.setTotal(pageList.getPages());
		pagedResult.setRows(list);
		pagedResult.setPage(page);
		pagedResult.setRecords(pageList.getTotal());
		
		return pagedResult;
	}

	@Transactional(propagation = Propagation.SUPPORTS)
	@Override
	public PagedResult queryMyFollowVideos(String userId, Integer page, int pageSize) {
		PageHelper.startPage(page, pageSize);
		List<VideosVO> list = videosMapperCustom.queryMyFollowVideos(userId);
				
		PageInfo<VideosVO> pageList = new PageInfo<>(list);
		
		PagedResult pagedResult = new PagedResult();
		pagedResult.setTotal(pageList.getPages());
		pagedResult.setRows(list);
		pagedResult.setPage(page);
		pagedResult.setRecords(pageList.getTotal());
		
		return pagedResult;
	}
}
