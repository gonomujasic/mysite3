package com.cafe24.mysite.controller;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cafe24.mysite.dto.JSONResult;
import com.cafe24.mysite.vo.UserVo;

@Controller
public class MainController {

	
	@RequestMapping("/main")
	public String main() {

		return "main/index";
	}

	@ResponseBody
	@RequestMapping("/hello")
	public String hello() {
		return "한글";
	}
	
	@ResponseBody
	@RequestMapping("/hello2")
	public Map<String, Object> hello2() {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("ret", true);
		map.put("data", new UserVo());
		return map;
	}
	
	@ResponseBody
	@RequestMapping("/hello3")
	public JSONResult hello3() {
		
		return JSONResult.success(new UserVo());
		
	}
}
