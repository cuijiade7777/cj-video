package com.cj.service;

import com.cj.pojo.Users;
import com.cj.pojo.UsersReport;

public interface UserService {
	
	/**
	 * 判断用户名是否存在
	 */
	public boolean queryUsernameIsExist(String username);
	
	/**
	 * 保存用户(用户注册)
	 */
	public void saveUser(Users user);

	public Users queryUserForLogin(String username, String md5Str);
	
	/*
	 * 修改用户信息
	 */
	public void updateUserInfo(Users user);

	/*
	 * 查询用户信息
	 */
	public Users queryUserInfo(String userId);

	/*
	 * 查询当前登录者和视频额点赞关系
	 */
	public boolean isUserLikeVideo(String loginUserId, String videoId);
	
	/*
	 * 增加用户和粉丝关系
	 */
	public void saveUserFanRelation(String userId, String fanId);
	
	/*
	 * 删除用户和粉丝关系
	 */
	public void deleteUserFanRelation(String userId, String fanId);

	/**
	 * 查询用户是否关注
	 */
	public boolean queryIfFollow(String userId, String fanId);

	public void reportUser(UsersReport usersReport);
}
