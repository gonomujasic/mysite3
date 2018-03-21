<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>  
<!DOCTYPE html>
<html>
<head>
<title>mysite</title>
<meta http-equiv="content-type" content="text/html; charset=utf-8">
<link href="${pageContext.servletContext.contextPath }/assets/css/board.css" rel="stylesheet" type="text/css">
</head>
<body>
	<div id="container">
	<c:import url="/WEB-INF/views/includes/header.jsp"/>
		<div id="content">
			<div id="board">
				
				<table class="tbl-ex">
					<tr>
						<th>번호</th>
						<th>제목</th>
						<th>글쓴이</th>
						<th>조회수</th>
						<th>작성일</th>
						<th>&nbsp;</th>
					</tr>
					<c:set var="count" value="${fn:length(pabl.list) }"/>		
					<c:forEach items="${pabl.list }" var="vo" varStatus="status">		
						<tr>
							<td>${vo.no }</td>
							<td style="text-align:left; padding-left:${20*vo.depth}px;">
								<c:if test="${vo.depth ne 0 }">
									<img src="${pageContext.servletContext.contextPath }/assets/images/reply.png"/>
								</c:if>
								<a href="${pageContext.servletContext.contextPath }/board/view?no=${vo.no }">${vo.title }</a>
							</td>
							<td>${vo.userName }</td>
							<td>${vo.hit }</td>
							<td>${vo.regDate }</td>
							<c:if test="${authUser ne null }">
							<td><a href="${pageContext.servletContext.contextPath }/board/delete?no=${vo.no }" class="del">삭제</a></td>
							</c:if>
						</tr>
					</c:forEach>
				</table>
				<form id="search_form" action="${pageContext.servletContext.contextPath }/board/list" method="get">
					<input type="text" id="kwd" name="kwd" value="${pabl.pager.keyword}">
					<input type="submit" value="찾기">
				</form>
				<div class="pager">
					<ul>
						<c:if test="${pabl.pager.startPage > pabl.pager.PAGE_PER_ORDER }">
							<li><a href="${pageContext.servletContext.contextPath }/board/list?page=${pabl.pager.page}&direction=prev&startpage=${pabl.pager.startPage}&kwd=${pabl.pager.keyword}">◀</a></li>
						</c:if>
						
						<c:forEach begin="${pabl.pager.startPage}" end="${pabl.pager.endPage}" var="i">
							<c:choose> 
								<c:when test="${i <= pabl.pager.maxPage  }">
									<li><a href="${pageContext.servletContext.contextPath }/board/list?page=${i}&kwd=${pabl.pager.keyword}">${i}</a></li>
								</c:when>
								<c:otherwise>
									<li><a>${i}</a></li>
								</c:otherwise>
							</c:choose>
						</c:forEach>
						<c:if test="${pabl.pager.endPage < pabl.pager.maxPage }">
							<li><a href="${pageContext.servletContext.contextPath }/board/list?page=${pabl.pager.page}&direction=next&endpage=${pabl.pager.endPage}&kwd=${pabl.pager.keyword}">▶</a></li>
						</c:if>
					</ul>
					
				</div>				
				<div class="bottom">
				<c:if test="${authUser ne null }">
					<a href="${pageContext.servletContext.contextPath }/board/write" id="new-book">글쓰기</a>
				</c:if>
				</div>				
			</div>
		</div>
		<c:import url="/WEB-INF/views/includes/navigation.jsp">
			<c:param name="menu" value="board"/>
		</c:import>
		<c:import url="/WEB-INF/views/includes/footer.jsp"/>
	</div>
</body>
</html>