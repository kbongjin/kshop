<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
			<div class="row">
			  <div class="col-sm-6 col-md-4">
			  	 <div class="thumbnail">
			      <img src="${product.img1Path}" width="180">
			      <div class="caption">
			        <h3>${product.name}</h3>
			        <p>...</p>
			      </div>
			    </div>
			  </div>
			  <div class="col-sm-12 col-md-8">
					<form action="product/stock" method="POST" data-parsley-validate class="form-horizontal form-label-left pfrm">
					  <input type="hidden" name="id" value="${stock.id}"/>
					  <input type="hidden" name="productId" value="${productId}"/>
                      <div class="form-group">
                        <label class="control-label col-md-2 col-sm-2 col-xs-12">개당 단가</label>
                        <div class="col-md-2 col-sm-3 col-xs-12">
                          <input type="text" name="unitPrice" class="form-control" required value="${stock.unitPrice}">
                        </div>
                        <label class="control-label col-md-2 col-sm-2 col-xs-12">입고/재고 수량</label>
                        <div class="col-md-2 col-sm-2 col-xs-12">
                          <input type="text" name="qty" class="form-control" placeholder="입고수량" required value="${stock.qty}">
                        </div>
                        <div class="col-md-2 col-sm-2 col-xs-12">
                          <input type="text" name="stockQty" class="form-control" placeholder="재고수량" required value="${stock.stockQty}">
                        </div>
                      </div>
                      
                    </form>
			  </div>
			</div>
    <script>
      $(document).ready(function() {
    	  $('#kmModal').find('.modal-title').text('재고 입력');
    	  <c:if test="${stock != null }">
    	  $('#modalDel').show();
    	  </c:if>
    	  
    	  $.extend( FormOptions, {
      			getDeleteUrl : function(){
      				return "product/stock/" + $('form.pfrm [name="id"]').val();
      	  		},
      	  		reloadTable : function(callback, resetPaging){
      	  			sTable.ajax.reload(callback, resetPaging);
      	  		}
      	  });
    	  
    	  <c:if test="${stock == null }">
    	  $( 'form.pfrm [name="qty"]' ).keyup(function(e) {
    		  $( 'form.pfrm [name="stockQty"]' ).val(this.value);
    	  });
    	  </c:if>
      });
    </script>