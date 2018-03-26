package com.cafe24.mysite.vo;

import java.util.List;

import org.apache.ibatis.type.Alias;
@Alias("pagerandboardlist")
public class PagerAndBoardList {

	private Pager pager;
	private List<BoardVo> list;
	
	public Pager getPager() {
		return pager;
	}
	public void setPager(Pager pager) {
		this.pager = pager;
	}
	public List<BoardVo> getList() {
		return list;
	}
	public void setList(List<BoardVo> list) {
		this.list = list;
	}

}
