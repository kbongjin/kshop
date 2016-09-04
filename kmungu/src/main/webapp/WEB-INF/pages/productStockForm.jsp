<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<form id="pfrm" action="product/stock" method="POST" data-parsley-validate class="form-horizontal form-label-left">
                      <div class="form-group">
                        <label class="control-label col-md-2 col-sm-2 col-xs-12">개당 단가</label>
                        <div class="col-md-3 col-sm-3 col-xs-12">
                          <input type="text" name="unitPrice" class="form-control" required >
                        </div>
                        <label class="control-label col-md-2 col-sm-2 col-xs-12">입고 수량</label>
                        <div class="col-md-3 col-sm-3 col-xs-12">
                          <input type="text" name="qty" class="form-control" required>
                        </div>
                      </div>
                      
                    </form>
    <script>
      $(document).ready(function() {
    	  $('#kmModal').find('.modal-title').text('재고 입력');
    	  <c:if test="${productStock != null }">
    	  $('#modalDel').show();
    	  </c:if>
      });
    </script>