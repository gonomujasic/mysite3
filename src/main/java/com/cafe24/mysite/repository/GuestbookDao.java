package com.cafe24.mysite.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.cafe24.mysite.vo.GuestbookVo;

@Repository
public class GuestbookDao {
	
	@Autowired
	DataSource dataSource;
	
	@Autowired
	SqlSession sqlSession;
	
	public boolean insert( GuestbookVo vo) {
			System.out.println(vo);
			int count = sqlSession.insert("guestbook.insert", vo);
			boolean result = count == 1;
			System.out.println( vo );
			return result;
	}

	public List<GuestbookVo> getList() {
		return sqlSession.selectList("guestbook.getList");
	}

	public boolean delete(GuestbookVo vo) {
		int count = sqlSession.delete("guestbook.delete", vo);
		boolean result = count == 1;
		
		return result;
	}
	
}
