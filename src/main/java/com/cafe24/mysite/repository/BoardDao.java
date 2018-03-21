package com.cafe24.mysite.repository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.cafe24.mysite.vo.BoardVo;
import com.cafe24.mysite.vo.CommentVo;
import com.cafe24.mysite.vo.Pager;
import com.cafe24.mysite.vo.PagerAndBoardList;

@Repository
public class BoardDao {
	
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
	
	public boolean insert( BoardVo vo) {
		boolean result = false;
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		try {
			conn = getConnection();
			String sql = "INSERT INTO BOARD "
					+ "VALUES (NULL, ?, ?, NOW(), 0, (SELECT IFNULL(MAX(GROUP_NO),0) FROM BOARD A)+1 , 1, 0, ?) ";
			pstmt = conn.prepareStatement(sql);

			pstmt.setString(1, vo.getTitle());
			pstmt.setString(2, vo.getContent());
			pstmt.setLong(3, vo.getUserNo());
			
			int count = pstmt.executeUpdate();
			
			result = count == 1;
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (conn != null) {
					conn.close();
				}
				if (pstmt != null) {
					pstmt.close();
				}

			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return result;
	}

	public boolean cInsert( CommentVo cVo) {
		boolean result = false;
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		try {
			conn = getConnection();
			String sql = "INSERT INTO COMMENT "
					+ "VALUES (NULL, ?, NOW(), ?, ?) ";
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, cVo.getContent());
			pstmt.setLong(2, cVo.getBoardNo());
			pstmt.setLong(3, cVo.getUserNo());
			
			int count = pstmt.executeUpdate();
			
			result = count == 1;
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (conn != null) {
					conn.close();
				}
				if (pstmt != null) {
					pstmt.close();
				}
				
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return result;
	}

	
	
	public PagerAndBoardList getList(Pager pager) {
		PagerAndBoardList pabl = new PagerAndBoardList();
		List<BoardVo> list = new ArrayList<>();
		Integer totalNo = 0;
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		String keyword = pager.getKeyword();
		String query = null;
				 
		try {
			conn = getConnection();

			StringBuilder sql = new StringBuilder("SELECT B.NO, B.TITLE, "
					+ "B.CONTENT, DATE_FORMAT(B.REG_DATE, '%Y-%m-%d %h:%m:%s'), "
					+ "B.GROUP_NO, B.ORDER_NO, B.DEPTH, U.NAME, "
					+ "B.HIT "
					+ "FROM BOARD B, USER U "
					+ "WHERE B.USER_NO = U.NO ");
			
			if(keyword != null) {
				query = " AND (TITLE LIKE '%" + keyword + "%' "+" OR CONTENT LIKE '%" + keyword + "%') ";
				sql.append(query);
			}
			sql.append(" ORDER BY B.GROUP_NO DESC, "
					+ "ORDER_NO ASC "
					+ "LIMIT ?,? ");
			
			pstmt = conn.prepareStatement(sql.toString());

			pstmt.setInt(1, (pager.getPage()-1)*10);
			pstmt.setInt(2, pager.getARTICLE_COUNT_PER_PAGE());
			
			rs = pstmt.executeQuery();

			// 5. 결과 처리
			while (rs.next()) {

				BoardVo result = new BoardVo();
				result.setNo(rs.getLong(1));
				result.setTitle(rs.getString(2));
				result.setContent(rs.getString(3));
				result.setRegDate(rs.getString(4));
				result.setGroupNo(rs.getLong(5));
				result.setOrderNo(rs.getLong(6));
				result.setDepth(rs.getLong(7));
				result.setUserName(rs.getString(8));
				result.setHit(rs.getLong(9));
				
				list.add(result);

			}
			pabl.setList(list);
			
			if(query==null) {
			totalNo = getTotalNo("");
			} else {
				totalNo = getTotalNo(query);
			}
			pager.setTotalNo(totalNo);
			pager.setConfig();
			pabl.setPager(pager);

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
		
		return pabl;
	}

	public Integer getTotalNo(String query) {
		
		Integer totalNo = 0;
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		conn = getConnection();

		StringBuilder sql = new StringBuilder("SELECT COUNT(*) FROM BOARD WHERE 1=1 ");
		sql.append(query);
		
		try {
			pstmt = conn.prepareStatement(sql.toString());
		
			rs = pstmt.executeQuery();
		if(rs.next()) {
			totalNo = rs.getInt(1);
		}
		} catch (SQLException e) {
			e.printStackTrace();
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
				e.printStackTrace();
			}
		}
		
		return totalNo;
	}
	
	public boolean modify( BoardVo vo) {
		boolean result = false;
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		try {
			conn = getConnection();
			String sql = "UPDATE BOARD "
					+ "SET TITLE = ?, CONTENT = ? WHERE NO = ? ";
			pstmt = conn.prepareStatement(sql);

			pstmt.setString(1, vo.getTitle());
			pstmt.setString(2, vo.getContent());
			pstmt.setLong(3, vo.getNo());
			
			int count = pstmt.executeUpdate();
			
			result = count == 1;
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (conn != null) {
					conn.close();
				}
				if (pstmt != null) {
					pstmt.close();
				}

			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return result;
	}
	
	public boolean delete(Long no, String password, Long userNo) {
		boolean result = true;
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			conn = getConnection();
			
			String sql = "SELECT * "
					+ "FROM USER "
					+ "WHERE NO = ? "
					+ "AND PASSWORD = PASSWORD(?) ";
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setLong(1, userNo);
			pstmt.setString(2, password);
			
			rs = pstmt.executeQuery();
			
			if(!rs.next()) {
				return false;
			}
			pstmt.close();
			rs.close();
			
			sql = "DELETE "
					+ "FROM BOARD "
					+ "WHERE NO = ? ";
			pstmt = conn.prepareStatement(sql);

			pstmt.setLong(1, no);
			
			int count = pstmt.executeUpdate();
			
			result = count == 1;
			
		} catch (SQLException e) {
			e.printStackTrace();
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
	
	public boolean cDelete(Long no, Long boardNo) {
		boolean result = false;
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		try {
			conn = getConnection();
			
			String sql = "DELETE FROM COMMENT "
					+ "WHERE NO = ? "
					+ "AND BOARD_NO = ? ";
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setLong(1, no);
			pstmt.setLong(2, boardNo);
								
			int count = pstmt.executeUpdate();
			
			result = count == 1;
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (conn != null) {
					conn.close();
				}
				if (pstmt != null) {
					pstmt.close();
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		return result;
	}
	
	public BoardVo view(Long no) {
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		BoardVo vo = new BoardVo();
		
		try {
			conn = getConnection();

			String sql = "SELECT B.NO, B.TITLE, "
					+ "B.CONTENT, DATE_FORMAT(B.REG_DATE, '%Y-%m-%d %h:%m:%s'), "
					+ "B.GROUP_NO, B.ORDER_NO, B.DEPTH, U.NAME, U.NO "
					+ "FROM BOARD B, USER U "
					+ "WHERE B.USER_NO = U.NO "
					+ "AND B.NO = ? ";
			pstmt = conn.prepareStatement(sql);

			pstmt.setLong(1, no);
			
			rs = pstmt.executeQuery();

			// 5. 결과 처리
			while (rs.next()) {
				
				vo.setNo(rs.getLong(1));
				vo.setTitle(rs.getString(2));
				vo.setContent(rs.getString(3));
				vo.setRegDate(rs.getString(4));
				vo.setGroupNo(rs.getLong(5));
				vo.setOrderNo(rs.getLong(6));
				vo.setDepth(rs.getLong(7));
				vo.setUserName(rs.getString(8));
				vo.setUserNo(rs.getLong(9));

			}
			vo.setcList(getcList(no));
			readCount(no);

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
		
		return vo;
	}
	
	public List<CommentVo> getcList(Long no) {
		List<CommentVo> cList = new ArrayList<>();
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			conn = getConnection();

			String sql = "SELECT C.NO, C.CONTENT, DATE_FORMAT(C.REG_DATE, '%Y-%m-%d %h:%m:%s'), "
					+ "U.NAME "
					+ "FROM BOARD B, USER U, COMMENT C "
					+ "WHERE C.USER_NO = U.NO "
					+ "AND C.BOARD_NO = B.NO "
					+ "AND C.BOARD_NO = ? ";
			
			pstmt = conn.prepareStatement(sql);

			pstmt.setLong(1, no);
			
			rs = pstmt.executeQuery();

			// 5. 결과 처리
			while (rs.next()) {

				CommentVo result = new CommentVo();
				result.setNo(rs.getLong(1));
				result.setContent(rs.getString(2));
				result.setRegDate(rs.getString(3));
				result.setUserName(rs.getString(4));
				
				cList.add(result);

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
		
		return cList;
	}
	
	
	public void readCount(Long no) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		try {
			conn = getConnection();
			String sql = "UPDATE BOARD "
					+ "SET HIT = HIT + 1 WHERE NO = ? ";
			pstmt = conn.prepareStatement(sql);

			pstmt.setLong(1, no);
			
			pstmt.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (conn != null) {
					conn.close();
				}
				if (pstmt != null) {
					pstmt.close();
				}

			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public boolean reply(BoardVo vo) {
		boolean result = false;
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		try {
			conn = getConnection();
			
			Long groupNo = getGroupNo(vo.getNo());
			
			String sql = "UPDATE BOARD A, (SELECT ORDER_NO FROM BOARD WHERE NO = ?) B "
					+ "SET A.ORDER_NO = A.ORDER_NO + 1 "
					+ "WHERE A.ORDER_NO > B.ORDER_NO "
					+ "AND A.GROUP_NO = ?";
			
			pstmt = conn.prepareStatement(sql);

			pstmt.setLong(1, vo.getNo());
			pstmt.setLong(2, groupNo);
			
			pstmt.executeUpdate();
			pstmt.close();
			
			sql = "INSERT INTO BOARD "
					+ "VALUES (NULL, ?, ?, NOW(), 0, ? , (SELECT ORDER_NO FROM BOARD A WHERE NO = ?)+1, (SELECT A.DEPTH FROM BOARD A WHERE NO = ?)+1, ?) ";
			pstmt = conn.prepareStatement(sql);

			pstmt.setString(1, vo.getTitle());
			pstmt.setString(2, vo.getContent());
			pstmt.setLong(3, groupNo);
			pstmt.setLong(4, vo.getNo());
			pstmt.setLong(5, vo.getNo());
			pstmt.setLong(6, vo.getUserNo());
			
			int count = pstmt.executeUpdate();
			
			result = count == 1;
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (conn != null) {
					conn.close();
				}
				if (pstmt != null) {
					pstmt.close();
				}

			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return result;
		
	}
	
	public Long getGroupNo(Long no) {
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Long groupNo = 0L;
		try {
			conn = getConnection();
			String sql = "SELECT GROUP_NO "
					+ "FROM BOARD "
					+ "WHERE NO = ? ";
			pstmt = conn.prepareStatement(sql);

			pstmt.setLong(1, no);
			
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				groupNo = rs.getLong(1);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (conn != null) {
					conn.close();
				}
				if (pstmt != null) {
					pstmt.close();
				}

			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return groupNo;
	}
}
