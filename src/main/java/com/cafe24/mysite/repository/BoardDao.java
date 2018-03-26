package com.cafe24.mysite.repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.cafe24.mysite.vo.BoardVo;
import com.cafe24.mysite.vo.CommentVo;
import com.cafe24.mysite.vo.Pager;
import com.cafe24.mysite.vo.PagerAndBoardList;

@Repository
public class BoardDao {
	
	@Autowired
	SqlSession sqlSession;
	
	public boolean insert( BoardVo vo) {
		int count = sqlSession.insert("board.insert", vo);
		
		return count == 1;
	}
	public boolean cInsert( CommentVo cVo) {
		int count = sqlSession.insert("board.cInsert", cVo);
		
		return count == 1;
	}

	public PagerAndBoardList getList(Pager pager) {
		
		PagerAndBoardList pabl = new PagerAndBoardList();
		List<BoardVo> list = sqlSession.selectList("dboard.getList", pager);
		pabl.setList(list);
		pabl.setPager(pager);
		
		return pabl;
	}
	
	public Integer getTotalNo(Pager pager) {
		return sqlSession.selectOne("board.getTotalNo", pager);
	}
	
	public boolean modify( BoardVo vo) {
		int count = sqlSession.update("board.update",vo);
		
		return count == 1;
		
	}
	
	public boolean delete(Long no) {
		
		int count = sqlSession.delete("board.delete", no);
		
		return count == 1;
	}
	
	public boolean checkPW(Long userNo, String password) {
		
		Map<String, String> parameterMap = new HashMap<>();
		parameterMap.put("password", password);
		parameterMap.put("userNo", userNo.toString());
		
		Integer count = sqlSession.selectOne("board.checkPW", parameterMap);
		
		return count > 0;
	}

	public boolean cDelete(Long no, Long bno) {
		Map<String, Long> parameterMap = new HashMap<>();
		parameterMap.put("no", no);
		parameterMap.put("bno", bno);
		int count = sqlSession.delete("board.cDelete", parameterMap);
		
		return count == 1;
		
	}
	
	public BoardVo view(Long no) {
		return sqlSession.selectOne("board.view", no);
	}
		
	public List<CommentVo> getcList(Long no) {
		return sqlSession.selectList("board.cList", no);
	}
	
	public void readCount(Long no) {
		sqlSession.update("board.readCount", no);
	}
	
	public boolean reply(BoardVo vo) {
		sqlSession.update("beforeReply", vo);
		
		int count = sqlSession.insert("reply", vo);
		return count == 1;
	}
	
	public Long getGroupNo(Long no) {
		return sqlSession.selectOne("long", no);
	}
	
}
