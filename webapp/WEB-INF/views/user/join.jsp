<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<!doctype html>
<html>
<head>
<title>mysite</title>
<meta http-equiv="content-type" content="text/html; charset=utf-8">
<link href="${pageContext.servletContext.contextPath }/assets/css/user.css" rel="stylesheet" type="text/css">
<script src="${pageContext.servletContext.contextPath }/assets/js/jquery/jquery-1.9.0.js" type="text/javascript"></script>
<script type="text/javascript">
$(function(){
	$("#btn-checkemail").click(function(){

		var email = $("#email").val();
		if(!email) {
			return;
		}
		
		$.ajax({
			url: "${pageContext.servletContext.contextPath }/api/user/checkemail?email="+email,
			type: "get",
			data: "",
			dataType: "json",
			success: function(response){//eval이 되어서 여기로 들어옴 몰랏네..
				console.log(response)
				if(response.result != "success") {
					console.log(response.message);
					
					return;
				}
				
				if(response.data == "exist") {
					alert("이미 사용 중인 이메일 입니다.");
					$("#email").val("").focus();
					$("#img-check").show();
					$("#btn-checkemail").hide();
					return;
				}
				

			},
			error: function(xhr, status, e){
				console.error( status + ":" + e);
				console.error("asdf")
			}
		});
	});
});
</script>
</head>
<body>
	<div id="container">
	<c:import url="/WEB-INF/views/includes/header.jsp"/>
		<div id="content">
			<div id="user">

				<form:form modelAttribute="userVo" id="join-form" name="joinForm" method="post" action="${pageContext.servletContext.contextPath }/user/join">
					<label class="block-label" for="name">이름</label>
					<form:input path="name"/>
					<p style="padding:0; text-align: left; color:red; font-weight: bold;">
						<form:errors path="name"/>
					</p>

					<label class="block-label" for="email">이메일</label>
					<form:input path="email"/>
					<img id="img-check" style="display: none" src="${pageContext.servletContext.contextPath }/assets/images/check.png">
					<input type="button" value="id 중복체크" id="btn-checkemail">
					<p style="padding:0; text-align: left; color:red; font-weight: bold;">
						<form:errors path="email"/>
					</p>
					
					<label class="block-label">패스워드</label>
					<form:password path="password"/>
					<p style="padding:0; text-align: left; color:red; font-weight: bold;">
						<form:errors path="password"/>
					</p>
					<fieldset>
						<legend>성별</legend>
						<label>여</label> <form:radiobutton path="gender" value="female"/>
						<label>남</label> <form:radiobutton path="gender" value="male"/>
					</fieldset>
					
					<fieldset>
						<legend>약관동의</legend>
						<input id="agree-prov" type="checkbox" name="agreeProv" value="y">
						<label>서비스 약관에 동의합니다.</label>
					</fieldset>
					
					<input type="submit" value="가입하기">
					
				</form:form>
			</div>
		</div>
		<c:import url="/WEB-INF/views/includes/navigation.jsp">
			<c:param name="menu" value="main"></c:param>
		</c:import>
		<c:import url="/WEB-INF/views/includes/footer.jsp"/>
	</div>
</body>
</html>