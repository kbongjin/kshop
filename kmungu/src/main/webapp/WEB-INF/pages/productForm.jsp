<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<form id="productForm" action="product" method="POST" data-parsley-validate class="form-horizontal form-label-left pfrm">
					  <input type="hidden" name="id" value="${product.id}"/>
					  <input type="hidden" name="productMasterId" value="${product.productMasterId}"/>
                      <div class="form-group">
                        <label class="control-label col-md-2 col-sm-2">옵션 상품명</label>
                        <div class="col-md-10 col-sm-10 col-xs-12">
                          <input type="text" name="name" class="form-control" required value="${product.name}">
                        </div>
                      </div>
                      <div class="form-group">
                        <label class="control-label col-md-2 col-sm-2">판매가</label>
                        <div class="col-md-4 col-sm-4 col-xs-12">
                          <input type="text" name="retailPrice" class="form-control" placeholder="판매원가" required value="${product.retailPrice}">
                        </div>
                      </div>
                    </form>
    <script>
      $(document).ready(function() {

    	  $('#kmModal').find('.modal-title').text('옵션 상품 등록');
    	  $('#modalSavec').show();
    	  
    	  <c:if test="${product.id > 0 }">
    	  $('#modalDel').show();
    	  </c:if>
    	  
    	  var pmId = '${product.productMasterId}';
    	  var tabId = '#tab_' + pmId;
    	  
    	  $.extend( FormOptions, {
	  			getDeleteUrl : function(){
			  		return "product/" + $('#productForm [name="id"]').val();
		  		},
		  		onAfterSaveSuccess : function(responseJson){
		  			loadProductList(tabId, pmId);
		  			$('#productForm [name="id"]').val('');
		  		},
		  		onAfterDeleteSuccess : function(responseJson){
		  			loadProductList(tabId, pmId);
		  		}
		  } );
    	  
    	  
      });
      
    </script>