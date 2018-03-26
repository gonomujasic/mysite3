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
		if(kwd != null && kwd != "") {
			pager.setKeyword(kwd);
		}
		pager.setPageNo((pager.getPage()-1)*10);
		
		PagerAndBoardList pabl= dao.getList(pager);
		
		Integer totalNo = dao.getTotalNo(pager);
		
		pabl.getPager().setTotalNo(totalNo);
		pabl.getPager().setConfig();
		
		return pabl;
	}

	public boolean insert(BoardVo vo) {
		return dao.insert(vo);
	}

	public boolean modify(BoardVo vo) {
		return dao.modify(vo);
	}

	public BoardVo view(Long no) {
		BoardVo vo = dao.view(no);
		List<CommentVo> cList = dao.getcList(no);
		vo.setcList(cList);
		
		if(vo != null)
			dao.readCount(no);
		
		return vo;
	}

	public boolean delete(Long no, String password, Long userNo) {
		if(dao.checkPW(userNo, password)) {
			return dao.delete(no);
		}
		
		return false;
	}

	public boolean cInsert(CommentVo cVo) {
		return dao.cInsert(cVo);
	}

	public boolean cDelete(Long no, Long userNo) {
		return dao.cDelete(no, userNo);
	}

	public boolean reply(BoardVo vo) {
		Long groupNo = dao.getGroupNo(vo.getNo());
		vo.setGroupNo(groupNo);
		return dao.reply(vo);
	}

}
