<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<form id="pfrm" action="product" method="POST" data-parsley-validate class="form-horizontal form-label-left" enctype="multipart/form-data">
					  <input type="hidden" name="id" value="${product.id}"/>
                      <div class="form-group">
                        <label class="control-label col-md-2 col-sm-2 col-xs-12">상품명</label>
                        <div class="col-md-10 col-sm-10 col-xs-12">
                          <input type="text" name="name" class="form-control" required value="${product.name}">
                        </div>
                      </div>
                      <div class="form-group">
                        <label class="control-label col-md-2 col-sm-2 col-xs-12">판매가/할인율/할인가격</label>
                        <div class="col-md-4 col-sm-4 col-xs-12">
                          <input type="text" name="retailPrice" class="form-control" placeholder="판매원가" required value="${product.retailPrice}">
                        </div>
                        <div class="col-md-3 col-sm-3 col-xs-12">
                          <input type="text" name="discountRate" class="form-control" placeholder="할인율" value="${product.discountRate}">
                        </div>
                        <div class="col-md-3 col-sm-3 col-xs-12">
                          <input type="text" name="sellingPrice" class="form-control" placeholder="할인판매가격" value="${product.sellingPrice}">
                        </div>
                      </div>
                      <div class="form-group">
                        <label class="control-label col-md-2 col-sm-2 col-xs-12">상품 카테고리</label>
                        <div class="col-md-10 col-sm-10 col-xs-12">
                          <select name="categoryCd" class="select2_group form-control" required >
                          	<option value="">::선택하세요::</option>
                          <c:forEach var="code" items="${categories}" varStatus="status">
                          	<c:if test="${fn:length(code.code) == 2 }">
                          	<c:if test="${status.index > 0 }"></optgroup></c:if>
                          	<optgroup label="${code.codeNm}">
                          	</c:if>
                          	<c:if test="${fn:length(code.code) == 4 }">
							<option value="${code.code}" 
								<c:if test="${code.code == product.categoryCd }">selected="selected"</c:if>>
								${code.codeNm}
							</option>
							</c:if>
						  </c:forEach>
                            </optgroup>
                          </select>
                        </div>
                      </div>
                      <div class="form-group">
                          <label class="control-label col-md-2 col-sm-2 col-xs-12">상품 이미지</label>
                          <div class="col-md-10 col-sm-10 col-xs-12">
                          	<input id="pimgs" name="imgFiles" type="file" multiple class="file-loading">
                          </div>
                      </div>
                    </form>
    <script>
      $(document).ready(function() {
    	  $("#pimgs").fileinput({
    		  showCaption: false,
    		  maxFileCount: 3,
    	      allowedFileTypes: ["image"],
    	      maxFileSize: 10240,
    	      maxFilePreviewSize: 5120,
    	      showUpload: false
    		  <c:if test="${product != null }">
    		  ,initialPreview: ${product.initialPreview},
              initialPreviewAsData: true,
              initialPreviewConfig: ${product.initialPreviewConfig},
              deleteUrl: "product/img/delete",
              overwriteInitial: false
              </c:if>
    	  });
    	  $('#kmModal').find('.modal-title').text('상품입력');
    	  <c:if test="${product != null }">
    	  $('#modalDel').show();
    	  </c:if>
      });
    </script>