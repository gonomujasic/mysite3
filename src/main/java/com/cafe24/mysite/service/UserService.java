package com.cafe24.mysite.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cafe24.mysite.repository.UserDao;
import com.cafe24.mysite.vo.UserVo;

@Service
public class UserService {

	@Autowired
	private UserDao userDao;
	
	public boolean join(UserVo vo) {
		return userDao.insert(vo);
	}

	public UserVo getUser(String email, String password) {
		return userDao.get(email, password);
	}
	
}