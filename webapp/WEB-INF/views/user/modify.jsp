<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>  
<!doctype html>
<html>
<head>
<title>mysite</title>
<meta http-equiv="content-type" content="text/html; charset=utf-8">
<link href="${pageContext.servletContext.contextPath }/assets/css/user.css" rel="stylesheet" type="text/css">
</head>
<body>
	<div id="container">
	<c:import url="/WEB-INF/views/includes/header.jsp"/>
		<div id="content">
			<div id="user">
				<form id="update-form" name="updateform" method="post" action="${pageContext.servletContext.contextPath }/user/modify">
					<input type="hidden" name="a" value="modify">
					<input type="hidden" name="no" value="${vo.no }">
					<label class="block-label" for="name">이름</label>
					<input id="name" name="name" type="text" value='${ vo.name eq null ? "" : vo.name}'>
					<label class="block-label" for="password" >패스워드</label>
					<input name="password" type="password" value="">


					<fieldset>
						<legend>성별</legend>
						<c:choose>
							<c:when test="${vo.gender == 'male' }">
								<label>여</label> <input type="radio" name="gender" value="female">
								<label>남</label> <input type="radio" name="gender" value="male" checked="checked">
							</c:when>
							<c:when test="${vo.gender == 'female' }">
								<label>여</label> <input type="radio" name="gender" value="female" checked="checked">
								<label>남</label> <input type="radio" name="gender" value="male">
							</c:when>
						</c:choose>
					</fieldset>

					<input type="submit" value="수정">
				</form>
			</div>
		</div>
		<c:import url="/WEB-INF/views/includes/navigation.jsp">
			<c:param name="menu" value="main"></c:param>
		</c:import>
		<c:import url="/WEB-INF/views/includes/footer.jsp"/>
	</div>
</body>
</html>