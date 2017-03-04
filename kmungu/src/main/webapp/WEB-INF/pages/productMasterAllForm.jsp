<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ include file="/WEB-INF/pages/productMasterForm.jsp" %>
					<form data-parsley-validate class="form-horizontal form-label-left">
					  <div class="form-group">
                        <label class="control-label col-md-2 col-sm-2"></label>
                        <div class="col-md-10 col-sm-10" style="text-align: center;">
                        	<a class="btn btn-primary btn-sm psave" href="#" role="button">저장</a>
                         	<a class="btn btn-primary btn-sm pdetail" href="javascript:loadContents(${productMaster.id})" role="button">상세 설명</a>
                        </div>
                      </div>
                      <div class="form-group">
                        <label class="control-label col-md-2 col-sm-2">옵션 상품</label>
                        <div class="col-md-10 col-sm-10">
                         	<div class="btn-group" role="group" >
							  <button type="button" class="btn btn-default optp-add">등록</button>
							  <button type="button" class="btn btn-default optp-reload">Reload</button>
							</div>
							<div class="optpl">
	                         	Loading...
		                    </div>
                        </div>
                      </div>
                      
                    </form>
                    
    <script>
      $(document).ready(function() {
    	  
    	  var pmId = '${productMaster.id}';
    	  var tabId = '#tab_' + pmId;
    	  
    	  $(tabId + ' a.psave').click(function(e){
    		  saveFormSubmit($(tabId + ' form.pfrm'));
    	  });
    	  
    	  loadProductList(tabId, pmId);
    	  
    	  $(tabId + ' button.optp-add').click(function(){
    		  $('#kmModal').modal('show');
          	  $('#kmModal .modal-body').load( "product/0?productMasterId=${productMaster.id}", function() {
          		 
              });
    	  });
    	  
    	  $(tabId + ' button.optp-reload').click(function(){
    		  loadProductList(tabId, pmId);
    	  });
    	  
      });
      
    </script>