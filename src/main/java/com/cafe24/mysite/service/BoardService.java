package com.cafe24.mysite.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cafe24.mysite.repository.BoardDao;
import com.cafe24.mysite.vo.BoardVo;
import com.cafe24.mysite.vo.CommentVo;
import com.cafe24.mysite.vo.Pager;
import com.cafe24.mysite.vo.PagerAndBoardList;

@Service
public class BoardService {

	@Autowired
	BoardDao dao;

	public PagerAndBoardList list(String direction, 
			Integer page, 
			Integer startPage, 
			Integer endPage, 
			String kwd) {
		
		Pager pager = new Pager();
		
		if(direction != null) {
			if("next".equals(direction)) {
				pager.setPage(page+(endPage-page+1));
			} else if("prev".equals(direction)) {
				pager.setPage(page-(page-startPage+1));
			}
		} else if(page != null){
			pager.setPage(page);
		} else {
			pager.setPage(1);
		}
		if(kwd != null) {
			pager.setKeyword(kwd);
		}
		
		PagerAndBoardList pabl= dao.getList(pager);
		
		return pabl;
	}

	public boolean insert(BoardVo vo) {
		return dao.insert(vo);
	}

	public boolean modify(BoardVo vo) {
		return dao.modify(vo);
	}

	public BoardVo view(Long no) {
		return dao.view(no);
	}

	public boolean delete(Long no, String password, Long userNo) {
		return dao.delete(no, password, userNo);
	}

	public boolean cInsert(CommentVo cVo) {
		return dao.cInsert(cVo);
	}

	public boolean cDelete(Long no, Long userNo) {
		return dao.cDelete(no, userNo);
	}

	public boolean reply(BoardVo vo) {
		return dao.reply(vo);
	}

}
