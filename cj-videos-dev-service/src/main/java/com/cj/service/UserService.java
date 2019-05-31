package com.cj.service;

import com.cj.pojo.Users;

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
}
