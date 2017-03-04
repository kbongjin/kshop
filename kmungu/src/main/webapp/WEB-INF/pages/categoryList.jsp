<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
				                    <table class="table table-striped table-hover jambo_table">
				                      <thead>
				                        <tr>
				                          <th>ID</th>
				                          <th>카테고리명</th>
				                          <th>Level</th>
				                          <th>순서</th>
				                          <th>삭제</th>
				                        </tr>
				                      </thead>
				                      <tbody>
				                      <c:forEach var="ctg" items="${paging.content}" varStatus="status">
				                        <tr cid="${ctg.id}">
				                          <th scope="row">${ctg.id}</th>
				                          <td><a href="javascript:viewCtg(${ctg.id});">${ctg.name}</a></td>
				                          <td>${ctg.level}</td>
				                          <td>${ctg.orderSeq}</td>
				                          <td><a href="javascript:deleteCtg(${ctg.level}, ${ctg.id}, ${ctg.parentCtgId});" class="remove-ctg"><i class="fa fa-close"></i></a></td>
				                        </tr><c:set var="maxSeq" value="${ctg.orderSeq}"></c:set>
				                      </c:forEach>
				                      <c:if test="${fn:length(paging.content) == 0 }">
				                      	<tr>
				                      		<td colspan="5" style="text-align: center;">데이타가 없습니다.</td>
				                      	</tr>
				                      </c:if>
				                      </tbody>
				                    </table>
				                    <input type="hidden" name="maxSeq" value="${maxSeq}" />