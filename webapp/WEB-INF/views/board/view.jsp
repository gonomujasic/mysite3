<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE html>
<%
pageContext.setAttribute("newLine", "\n");
%>
<html>
<head>
<title>mysite</title>
<meta http-equiv="content-type" content="text/html; charset=utf-8">
<link href="${pageContext.servletContext.contextPath }/assets/css/board.css" rel="stylesheet" type="text/css">
<link href="${pageContext.servletContext.contextPath }/assets/css/guestbook.css" rel="stylesheet" type="text/css">
</head>
<body>
	<div id="container">
	<c:import url="/WEB-INF/views/includes/header.jsp"/>
		<div id="content">
			<div id="board" class="board-form">
				<table class="tbl-ex">
					<tr>
						<th colspan="2">글보기</th>
					</tr>
					<tr>
						<td class="label">제목</td>
						<td>${vo.title }</td>
					</tr>
					<tr>
						<td class="label">내용</td>
						<td>
							<div class="view-content">
								${fn:replace(vo.content, newLine, "<br>") }
							</div>
						</td>
					</tr>
				</table>
				<div class="bottom">
					<a href="${pageContext.servletContext.contextPath }/board/list">글목록</a>
					<c:if test="${authUser ne null }">
						<a href="${pageContext.servletContext.contextPath }/board/reply?no=${vo.no }">답변</a>
					<c:if test="${vo.userNo eq authUser.no }">
						<a href="${pageContext.servletContext.contextPath }/board/modify?no=${vo.no }">글수정</a>
						<a href="${pageContext.servletContext.contextPath }/board/delete?no=${vo.no }">삭제</a>
					</c:if>	
					</c:if>
				</div>
			</div>
			<div id="board">
				<c:forEach items="${vo.cList }" var="cVo">
					<table class="tbl-ex">
						<tr>
							<td class="label" colspan="1">작성자</td>
							<td id="cUserName" colspan="2">${cVo.userName }</td>
							<td class="label" colspan="1">작성일</td>
							<td id="cRegDate" colspan="2">${cVo.regDate }</td>
							<td class="buttom" colspan="1">
							<c:if test="${authUser ne null }">
								<a href="${pageContext.servletContext.contextPath }/board/cdelete?no=${cVo.no }&bno=${vo.no}">삭제</a>
							</c:if>
							</td>
						</tr>
						<tr>
							<td class="label" colspan="1">내용</td>
							<td colspan="6">
								<p align="left">
								${cVo.content }
								</p>
							</td>
						</tr>
					</table>
				</c:forEach>				
			</div>
			<c:if test="${authUser ne null }">
			<div id="guestbook">
				<form action="${pageContext.servletContext.contextPath }/board/cwrite" method="post">
					<input type="hidden" name="boardNo" value="${vo.no }">
					<input type="hidden" name="userNo" value="${authUser.no }"/>
					<table>
						<tr>
							<td>이름</td><td id="cWriter">${authUser.name }</td>
							<td colspan=4 align=right><input type="submit" VALUE="확인 "></td>
						</tr>
						<tr>
							<td colspan=4><textarea name="content" id="content"></textarea></td>
						</tr>
					</table>
				</form>
			</div>
			</c:if>
		</div>
		
		<c:import url="/WEB-INF/views/includes/navigation.jsp">
			<c:param name="menu" value="board"/>
		</c:import>
		<c:import url="/WEB-INF/views/includes/footer.jsp"/>
	</div>
</body>
</html>