package com.cafe24.mysite.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.cafe24.mysite.service.UserService;
import com.cafe24.mysite.vo.UserVo;
import com.cafe24.security.Auth;
import com.cafe24.security.AuthUser;

@Controller
@RequestMapping("/user")
public class UserController {

	@Autowired
	UserService userService;
	
	@RequestMapping(value="/join", method=RequestMethod.GET)
	public String join(@ModelAttribute UserVo vo) {
		return "user/join";
	}
	
	@RequestMapping(value="/join", method=RequestMethod.POST)
	public String join(@ModelAttribute @Valid UserVo vo, BindingResult result) {
		
		if(result.hasErrors()) {
			
			/*List<ObjectError> list = result.getAllErrors();
			for(ObjectError error: list) {
				System.out.println("error"+error);
			} 스프링에서 에러 내용 뿌릴 수 있게 도와주는 태그 라이브러리 제공*/ 
			
			
			return "user/join";
		}
		
		userService.join(vo);
		return "redirect:/main";
	}
	
	@RequestMapping(value="/joinsuccess", method=RequestMethod.GET)
	public String joinSuccess() {
		return "user/joinsuccess";
	}
	
	@RequestMapping(value="/login", method=RequestMethod.GET)
	public String login() {
			return "user/login";
	}
	
/*	@RequestMapping(value="/login", method=RequestMethod.POST)
	public String login(HttpSession session,
			@ModelAttribute UserVo vo, 
			Model model) {
		UserVo authUser = userService.getUser(vo);
		if(authUser == null) {
			model.addAttribute("result", "fail");
			return "user/login";
		}
		
		//인증처리
		session.setAttribute("authUser", authUser);
		return "redirect:/main";
			
	}
	
	@RequestMapping(value="/loginsuccess", method=RequestMethod.GET)
	public String loginSuccess() {
		return "redirect:/main";
	}*/
	
/*	@RequestMapping("/logout")
	public String logout(HttpSession session) {
		session.removeAttribute("authUser");
		session.invalidate();
		return "redirect:/main";
	}*/
	
	@Auth()
	@RequestMapping(value="/modify", method=RequestMethod.GET)
	public String modify(@AuthUser UserVo authUser, Model model) {
		UserVo vo = userService.getUser(authUser.getNo());
		model.addAttribute("vo", vo);
		return "user/modify";
	}

	
	@RequestMapping(value="/modify", method=RequestMethod.POST)
	public String modify(@ModelAttribute UserVo vo) {
			
		userService.modify(vo);
		
		return "user/modifysuccess";
	}
	
	/*@ExceptionHandler( UserDaoException.class )
	public String handleUserDaoException() {
		//로그 남기기
		return "error/exception";
	}*/
	
	

}
