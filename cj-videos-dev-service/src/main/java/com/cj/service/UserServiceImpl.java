package com.cj.service;

import org.n3r.idworker.Sid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.cj.mapper.UsersMapper;
import com.cj.pojo.Users;

@Service
public class UserServiceImpl implements UserService {
	
	@Autowired
	private UsersMapper usersMapper;
	
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

}
