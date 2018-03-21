package com.cafe24.mysite.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cafe24.mysite.repository.GuestbookDao;
import com.cafe24.mysite.vo.GuestbookVo;

@Service
public class GuestbookService {

	@Autowired
	GuestbookDao dao;
	
	public List<GuestbookVo> list() {
		return dao.getList();
	}
	
	public boolean add(GuestbookVo vo) {
		return dao.insert(vo);
	}
	
	public boolean delete(GuestbookVo vo)	{
		return dao.delete(vo);
	}
}
