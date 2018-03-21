package com.cafe24.mysite.repository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.cafe24.mysite.exception.UserDaoException;
import com.cafe24.mysite.vo.UserVo;

@Repository
public class UserDao {
	
	public static Connection getConnection() {
		Connection conn = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");

			String url = "jdbc:mysql://localhost/webdb";

			conn = DriverManager.getConnection(url, "webdb", "webdb");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return conn;
	}
	
	public boolean insert( UserVo vo) {
		boolean result = false;
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		try {
			conn = getConnection();
			String sql = "INSERT INTO USER VALUES(NULL, ?, ?, PASSWORD(?), ?, now()) ";
			pstmt = conn.prepareStatement(sql);

			pstmt.setString(1, vo.getName());
			pstmt.setString(2, vo.getEmail());
			pstmt.setString(3, vo.getPassword());
			pstmt.setString(4, vo.getGender());
			
			int count = pstmt.executeUpdate();
			
			result = count == 1;
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}
	
	public UserVo get(String email, String password) {
		
		UserVo result = null;
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			conn = getConnection();

			String sql = "SELECT NO, NAME  "
					+ "FROM USER "
					+ "WHERE EMAIL=? "					
					+ "AND PASSWORD= PASSWORD(?) ";					
			pstmt = conn.prepareStatement(sql);

			pstmt.setString(1, email);
			pstmt.setString(2, password);
			
			rs = pstmt.executeQuery();

			// 5. 결과 처리

			if(rs.next()) {
				result = new UserVo();
				result.setNo(rs.getLong(1));
				result.setName(rs.getString(2));
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
		
		return result;
		
	}
	public UserVo get(Long no) {
		
		UserVo result = null;
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			conn = getConnection();

			String sql = "SELECT NO, EMAIL, NAME, PASSWORD, GENDER  "
					+ "FROM USER "
					+ "WHERE NO=? ";					
			pstmt = conn.prepareStatement(sql);

			pstmt.setLong(1, no);
			
			rs = pstmt.executeQuery();

			// 5. 결과 처리

			if(rs.next()) {
				result = new UserVo();
				result.setNo(rs.getLong(1));
				result.setEmail(rs.getString(2));
				result.setName(rs.getString(3));
				result.setPassword(rs.getString(4));
				result.setGender(rs.getString(5));
			}

		} catch (SQLException e) {
			throw new UserDaoException();
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
		return result;
		
	}
	

	public List<UserVo> getList() {
		List<UserVo> list = new ArrayList<>();
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			conn = getConnection();

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
		
		boolean result = false;
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		try {
			conn = getConnection();
			String sql = "UPDATE USER "
					+ "SET NAME = ?, "
					+ "    PASSWORD = PASSWORD(?), "
					+ "    GENDER = ? "
					+ "WHERE NO = ? ";
			pstmt = conn.prepareStatement(sql);

			pstmt.setString(1, vo.getName());
			pstmt.setString(2, vo.getPassword());
			pstmt.setString(3, vo.getGender());
			pstmt.setLong(4, vo.getNo());
			
			int count = pstmt.executeUpdate();
			
			result = count == 1;
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return result;
		
	}
	
	public boolean delete(UserVo vo) {
		boolean result = false;
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		try {
			conn = getConnection();
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
