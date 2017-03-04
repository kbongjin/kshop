<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
				                    <table class="table table-striped table-hover jambo_table">
				                      <thead>
				                        <tr>
				                          <th>ID</th>
				                          <th>옵션 상품명</th>
				                          <th>판매가</th>
				                          <th>할인가격</th>
				                          <th>할인판매가</th>
				                          <th>재고</th>
				                          <th>등록일시</th>
				                        </tr>
				                      </thead>
				                      <tbody>
				                      <c:forEach var="product" items="${list}" varStatus="status">
				                        <tr pid="${product.id}">
				                          <th scope="row">${product.id}</th>
				                          <td><a href="javascript:viewProduct(${product.id});">${product.name}</a></td>
				                          <td>${product.retailPrice}</td>
				                          <td>${product.discountPrice}</td>
				                          <td>${product.sellingPrice}</td>
				                          <td><a class="btn btn-primary btn-sm stock" href="javascript:loadStocks(${product.id}, '${product.name}')" role="button">재고관리 <span class="badge">${product.stockQty}</span></a></td>
				                          <td>${product.createDt}</td>
				                        </tr>
				                      </c:forEach>
				                      <c:if test="${fn:length(list) == 0 }">
				                      	<tr>
				                      		<td colspan="7" style="text-align: center;">데이타가 없습니다.</td>
				                      	</tr>
				                      </c:if>
				                      </tbody>
				                    </table>