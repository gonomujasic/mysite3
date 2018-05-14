package com.cafe24.security;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.cafe24.mysite.vo.UserVo;

public class AuthInterceptor extends HandlerInterceptorAdapter {

	@Override
	public boolean preHandle(
			HttpServletRequest request, 
			HttpServletResponse response, 
			Object handler)
			throws Exception {
		
		//1. handler 종류 확인
		if( handler instanceof HandlerMethod == false) { //핸들러 메소드가 아니면 디폴트 서블릿 핸들러라는 뜻 //핸들러메소드에 url에 따른 각종 정보가 담겨있음. 
			return true;
		}
		
		
		//2. casting 
		HandlerMethod handlerMethod = (HandlerMethod) handler;
		
		//3. 핸들러 메소드에서 맵핑되는 메서드에 @Auth가 있으면 가져오기 
		Auth auth = handlerMethod.getMethodAnnotation(Auth.class);
		
		//4. Method에 @Auth가 없는 경우
		if( auth == null) {
			return true;
		}
		
		//5. @Auth가 붙어있는 경우
		HttpSession session = request.getSession();
		
		if(session == null) {
			response.sendRedirect(request.getContextPath()+"/user/login");
			return false;
		}
		
		UserVo authUser = (UserVo) session.getAttribute("authUser");
		if(authUser == null) {
			response.sendRedirect(request.getContextPath()+"/user/login");
			return false;
		}
		//6. 접근 허가
		return true;
	}

}
