package com.cafe24.mysite.exception;

import java.io.PrintWriter;
import java.io.StringWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.cafe24.mysite.dto.JSONResult;
import com.fasterxml.jackson.databind.ObjectMapper;

@ControllerAdvice
public class GlobalExceptionHandler {

	private static final Log LOG = LogFactory.getLog( GlobalExceptionHandler.class );
	
	@ExceptionHandler(Exception.class)
	public void handlerException (
			HttpServletRequest request, HttpServletResponse response, Exception e) throws Exception {
		//1.로깅
		StringWriter error = new StringWriter();
		e.printStackTrace(new PrintWriter(error));
		
		LOG.error(error.toString());
		
		request.setAttribute("error", error.toString());
		
		//e.printStackTrace();
		
		//2.사과
		String accept = request.getHeader("accept");
		if( accept.matches(".*application/json.*")) {
			//실패 json 응답
			JSONResult result = JSONResult.fail(error.toString());
			
			String json = new ObjectMapper().writeValueAsString(result);
			response.setContentType("application/json; charset=UTF-8");
			response.getWriter().println(json);
		} else {
			request.getRequestDispatcher("/WEB-INF/views/error/exception.jsp").forward(request, response);
		};
		
	}
	
}
