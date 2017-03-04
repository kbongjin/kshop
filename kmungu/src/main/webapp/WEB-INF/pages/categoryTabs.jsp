<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
					<c:forEach var="ctg" items="${paging.content}" varStatus="status">
                        <li role="presentation" class="">
                        	<a href="#tab_content1" id="home-tab" role="tab" data-toggle="tab" cid="${ctg.id}">
                        		<span class="tname">${ctg.name}</span>
                        		<span class="rm-link"><i class="fa fa-close"></i></span>
                        	</a>
                        	
                        </li>
                    </c:forEach>
                        <li role="presentation" class="dropdown">
                        	<a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false">
                        		+ 추가/수정 <span class="caret"></span>
                        	</a>
                        	 <ul class="dropdown-menu">
						      	<li><a href="#" class="add">추가</a></li>
    							<li><a href="#" class="modify">수정 (선택한탭)</a></li>
						     </ul>
                        </li>