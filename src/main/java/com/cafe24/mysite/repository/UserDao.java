package com.cafe24.mysite.repository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.cafe24.mysite.exception.UserDaoException;
import com.cafe24.mysite.vo.UserVo;

@Repository
public class UserDao {
	
	@Autowired
	DataSource dataSource;
	
	@Autowired
	SqlSession sqlSession;
	
	public boolean insert( UserVo vo) {
		int count = sqlSession.insert("user.insert", vo);
		boolean result = count == 1;
		
		return result;
	}
	
	public UserVo get(UserVo vo) {
		
		UserVo result = sqlSession.selectOne("user.getByEmailAndPassword",vo);
		
		return result;
		
	}
	
	public UserVo get(Long no) {
		
		UserVo result = sqlSession.selectOne("user.getByNo", no);
		
		return result;
		
	}
	
	public UserVo get(String email) {
		
		UserVo result = sqlSession.selectOne("user.getByEmail", email);
		
		return result;
		
	}
	
	public List<UserVo> getList() {
		List<UserVo> list = new ArrayList<>();
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			conn = dataSource.getConnection();

			String sql = "SELECT NO, NAME, PASSWORD, CONTENT, DATE_FORMAT(REG_DATE, '%Y-%m-%d') "
					+ "FROM GUESTBOOK "
					+ "ORDER BY NO DESC ";
			pstmt = conn.prepareStatement(sql);

			rs = pstmt.executeQuery();

			// 5. 결과 처리
			while (rs.next()) {

				UserVo vo = new UserVo();
				vo.setNo(rs.getLong(1));
				vo.setName(rs.getString(2));
				vo.setPassword(rs.getString(3));

				list.add(vo);

			}

		} catch (SQLException e) {
			System.out.println("sql 에러" + e);
		} finally {
			try {
				if (conn != null) {
					conn.close();
				}
				if (pstmt != null) {
					pstmt.close();
				}
				if (rs != null) {
					rs.close();
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		return list;
	}

	public boolean modify(UserVo vo) {
		int count = sqlSession.update("user.update", vo);
		boolean result = count == 1;
		
		return result;
		
	}
	
	public boolean delete(UserVo vo) {
		boolean result = false;
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		try {
			conn = dataSource.getConnection();
			String sql = "DELETE "
					+ "FROM GUESTBOOK "
					+ "WHERE NO = ? "
					+ "AND PASSWORD = ? ";
			pstmt = conn.prepareStatement(sql);

			pstmt.setLong(1, vo.getNo());
			pstmt.setString(2, vo.getPassword());
			
			int count = pstmt.executeUpdate();
			
			result = count == 1;
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
}
