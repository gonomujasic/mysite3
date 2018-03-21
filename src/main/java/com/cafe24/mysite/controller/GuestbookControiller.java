package com.cafe24.mysite.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.cafe24.mysite.service.GuestbookService;
import com.cafe24.mysite.vo.GuestbookVo;

@Controller
@RequestMapping("/guestbook")
public class GuestbookControiller {

	@Autowired
	GuestbookService service;
	
	@RequestMapping("/list")
	public String list(Model model) {
		model.addAttribute("list", service.list());
		return "guestbook/list";
	}

	@RequestMapping("/add")
	public String add(@ModelAttribute GuestbookVo vo) {
		service.add(vo);
		return "redirect:/guestbook/list";
	}

	@RequestMapping(value="/delete", method=RequestMethod.GET)
	public String delete(@RequestParam Long no, Model model) {
		model.addAttribute("no", no);
		return "/guestbook/deleteform";
	}

	@RequestMapping(value="/delete", method=RequestMethod.POST)
	public String delete(@ModelAttribute GuestbookVo vo) {
		System.out.println("no"+vo.getNo());
		service.delete(vo);
		
		
		return "redirect:/guestbook/list";
	}
	
	
	
}
