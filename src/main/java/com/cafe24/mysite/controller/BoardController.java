package com.cafe24.mysite.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.cafe24.mysite.service.BoardService;
import com.cafe24.mysite.vo.BoardVo;
import com.cafe24.mysite.vo.CommentVo;
import com.cafe24.mysite.vo.PagerAndBoardList;
import com.cafe24.mysite.vo.UserVo;

@Controller
@RequestMapping("/board")
public class BoardController {

	@Autowired
	BoardService service;
	
	@RequestMapping(value="/list", method=RequestMethod.GET)
	public String list(@RequestParam(required=false) String direction, 
			@RequestParam(required=false) Integer page,
			@RequestParam(name="startpage", required=false) Integer startPage,
			@RequestParam(name="endpage",required=false) Integer endPage,
			@RequestParam(required=false) String kwd,
			Model model
			) {
		
		PagerAndBoardList pabl= service.list(direction, page, startPage, endPage, kwd);
		model.addAttribute("pabl", pabl);
		
		return "/board/list";
	}
	
	@RequestMapping(value="/write", method=RequestMethod.GET)
	public String write() {
		return "/board/write";
	}

	@RequestMapping(value="/write", method=RequestMethod.POST)
	public String write(@ModelAttribute BoardVo vo, HttpServletRequest request) {
		UserVo authUser = (UserVo)request.getSession().getAttribute("authUser");
		Long userNo = authUser.getNo();
		vo.setUserNo(userNo);
		boolean result = service.insert(vo);
		
		return "redirect:/board/list";
	}
	
	@RequestMapping(value="/view", method=RequestMethod.GET)
	public String view(@RequestParam Long no, Model model) {
		BoardVo vo = service.view(no);
		model.addAttribute("vo", vo);
		
		return "/board/view";
	}

	@RequestMapping(value="/modify", method=RequestMethod.GET)
	public String modify(@RequestParam Long no, Model model) {
		BoardVo vo = service.view(no);
		model.addAttribute("vo", vo);
		
		return "/board/modify";
	}

	@RequestMapping(value="/modify", method=RequestMethod.POST)
	public String modify(@ModelAttribute BoardVo vo) {
		boolean result = service.modify(vo);
				
		return "redirect:/board/list";
	}
	
	@RequestMapping(value="/delete", method=RequestMethod.GET)
	public String delete(@RequestParam Long no, Model model) {
		model.addAttribute("no", no);		
		return "/board/delete";
	}

	@RequestMapping(value="/delete", method=RequestMethod.POST)
	public String delete(
			@RequestParam Long no, 
			@RequestParam String password, 
			HttpServletRequest request) {
		
		UserVo authUser = (UserVo)request.getSession().getAttribute("authUser");
		Long userNo = authUser.getNo();
		System.out.println(no+":"+password+":"+userNo);
		boolean result = service.delete(no, password, userNo);
		
		return "redirect:/board/list";
	}
	
	@RequestMapping(value="/reply", method=RequestMethod.GET)
	public String reply(@RequestParam Long no, Model model) {
		
		model.addAttribute("vo", service.view(no));
		return "/board/reply";
	}

	@RequestMapping(value="/reply", method=RequestMethod.POST)
	public String reply(
			@ModelAttribute BoardVo vo, 
			HttpServletRequest request) {
		UserVo authUser = (UserVo)request.getSession().getAttribute("authUser");
		Long userNo = authUser.getNo();
		vo.setUserNo(userNo);
		boolean result = service.reply(vo);
		
		return "redirect:/board/list";
	}
	
	@RequestMapping(value="/cwrite", method=RequestMethod.POST)
	public String cWrite(@ModelAttribute CommentVo cVo, Model model) {

		boolean result = service.cInsert(cVo);
		
		return "redirect:/board/view?no="+cVo.getBoardNo();
	}

	@RequestMapping(value="/cdelete", method=RequestMethod.GET)
	public String cWrite(@RequestParam Long no, @RequestParam Long bno) {
		System.out.println(no+":"+bno);
		boolean result = service.cDelete(no, bno);
		
		return "redirect:/board/view?no="+bno;
	}
	
	
}
