package com.cj.service.impl;

import java.util.Collection;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.n3r.idworker.Sid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.cj.mapper.UsersLikeVideosMapper;
import com.cj.mapper.UsersMapper;
import com.cj.pojo.Users;
import com.cj.pojo.UsersLikeVideos;
import com.cj.service.UserService;

import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.entity.Example.Criteria;

@Service
public class UserServiceImpl implements UserService {
	
	@Autowired
	private UsersMapper usersMapper;
	
	@Autowired
	private UsersLikeVideosMapper usersLikeVideosMapper;
	
	@Autowired
	private Sid sid;

	@Transactional(propagation = Propagation.SUPPORTS)
	@Override
	public boolean queryUsernameIsExist(String username) {
		Users user = new Users();
		user.setUsername(username);
		Users selectOne = usersMapper.selectOne(user);
		return selectOne !=null ;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	@Override
	public void saveUser(Users user) {
		 String userId = sid.nextShort();
		 user.setId(userId);
		 usersMapper.insert(user);

	}

	@Transactional(propagation = Propagation.SUPPORTS)
	@Override
	public Users queryUserForLogin(String username, String password) {
		Example userExample = new Example(Users.class);
		Criteria criteria = userExample.createCriteria();
		criteria.andEqualTo("username",username);
		criteria.andEqualTo("password",password);
		return usersMapper.selectOneByExample(userExample);
	}

	@Transactional(propagation = Propagation.REQUIRED)
	@Override
	public void updateUserInfo(Users user) {
		Example userExample = new Example(Users.class);
		Criteria criteria = userExample.createCriteria();
		criteria.andEqualTo("id",user.getId());
		usersMapper.updateByExampleSelective(user, userExample);
	}

	@Transactional(propagation = Propagation.SUPPORTS)
	@Override
	public Users queryUserInfo(String userId) {
		Example userExample = new Example(Users.class);
		Criteria criteria = userExample.createCriteria();
		criteria.andEqualTo("id",userId);
		Users user = usersMapper.selectOneByExample(userExample);
		return user;
	}

	@Override
	public boolean isUserLikeVideo(String loginUserId, String videoId) {
		if(StringUtils.isBlank(loginUserId) || StringUtils.isBlank(videoId)) {
			return false;
		}
		Example example = new Example(UsersLikeVideos.class);
		Criteria criteria = example.createCriteria();
		criteria.andEqualTo("userId", loginUserId);
		criteria.andEqualTo("videoId", videoId);
		List<UsersLikeVideos> List = usersLikeVideosMapper.selectByExample(example);
		if(!CollectionUtils.isEmpty(List)) {
			return true;
		}
		return false;
	}

}
