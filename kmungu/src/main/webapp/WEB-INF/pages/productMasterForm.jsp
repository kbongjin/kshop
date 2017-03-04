<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<form action="productMaster" method="POST" data-parsley-validate class="form-horizontal form-label-left pfrm" enctype="multipart/form-data">
					  <input type="hidden" name="id" value="${productMaster.id}"/>
                      <div class="form-group">
                        <label class="control-label col-md-2 col-sm-2 col-xs-12">상품명</label>
                        <div class="col-md-10 col-sm-10 col-xs-12">
                          <input type="text" name="name" class="form-control" required value="${productMaster.name}">
                        </div>
                      </div>
                      <div class="form-group">
                        <label class="control-label col-md-2 col-sm-2 col-xs-12">제조사</label>
                        <div class="col-md-10 col-sm-10 col-xs-12">
                          <input type="text" name="maker" class="form-control" required value="${productMaster.maker}">
                        </div>
                      </div>
                      <div class="form-group">
                        <label class="control-label col-md-2 col-sm-2 col-xs-12">소비자가/할인율/할인판매가격</label>
                        <div class="col-md-2 col-sm-2 form-group has-feedback">
                          <input type="text" name="retailPrice" class="form-control number" placeholder="판매원가" value="${productMaster.retailPrice}" data-parsley-type="number" readonly="readonly">
                          <span class="form-control-feedback right" aria-hidden="true">원</span>
                        </div>
                        <div class="col-md-2 col-sm-2 form-group has-feedback">
                          <input type="text" name="discountRate" class="form-control number" placeholder="할인율" value="${productMaster.discountRate}" data-parsley-range="[0, 100]">
                          <span class="form-control-feedback right" aria-hidden="true">%</span>
                        </div>
                        <div class="col-md-2 col-sm-2 form-group has-feedback">
                          <input type="text" name="sellingPrice" class="form-control number" placeholder="할인판매가격" value="${productMaster.sellingPrice}" data-parsley-type="number">
                          <span class="form-control-feedback right" aria-hidden="true">원</span>
                        </div>
                      </div>
                      <div class="form-group">
                        <label class="control-label col-md-2 col-sm-2 col-xs-12">상품 카테고리</label>
                        <div class="col-md-10 col-sm-10 col-xs-12">
                        	<div class="row">
		                        <div class="col-md-12 col-sm-12 col-xs-12">
		                          <c:if test="${fn:length(productMaster.categories) == 0 }">
		                          <div class="alert alert-warning alert-dismissible fade in" role="alert">
		                          	<button type="button" class="close" data-dismiss="alert" aria-label="Close">
									  <span aria-hidden="true">&times;</span>
									</button>
		                          	카테고리를 추가해주세요.
		                          </div>
		                          </c:if>
		                          <ul class="ctg-list">
		                          	  <c:forEach var="ctg" items="${productMaster.categories}">
									  <li class="ctg">${ctg.parent.parent.name} > ${ctg.parent.name} > ${ctg.name} <span class="rm-link"><i class="fa fa-close"></i></span><input type="hidden" name="ctgIds" value="${ctg.id}" /></li>
									  </c:forEach>
								  </ul>
		                        </div>
	                        </div>
                        	<div class="row">
		                        <div class="col-md-3 col-sm-3 col-xs-12">
		                          <select name="category1" class="select2_group form-control category1" >
		                          	<option value="">::대분류 선택하세요::</option>
		                          
		                          </select>
		                        </div>
		                        <div class="col-md-3 col-sm-3 col-xs-12">
		                          <select name="category2" class="select2_group form-control category2" >
		                          	<option value="">::중분류 선택하세요::</option>
		                          
		                          </select>
		                        </div>
		                        <div class="col-md-3 col-sm-3 col-xs-12">
		                          <select name="categoryId" class="select2_group form-control category3" >
		                          	<option value="">::소분류 선택하세요::</option>
		                          
		                          </select>
		                        </div>
		                        <div class="col-md-3 col-sm-3 col-xs-12">
		                          <button type="button" class="btn add-ctg" data-toggle="tooltip" data-placement="right" title="소분류를 선택해주세요.">추가</button>
		                        </div>
	                        </div>
                        </div>
                      </div>
                      <div class="form-group">
                          <label class="control-label col-md-2 col-sm-2 col-xs-12">상품 이미지(3개)</label>
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
    	      
    		  <c:if test="${productMaster != null }">
    		  initialPreview: ${productMaster.initialPreview},
              initialPreviewAsData: true,
              initialPreviewConfig: ${productMaster.initialPreviewConfig},
              deleteUrl: "product/img/delete",
              overwriteInitial: false,
              </c:if>
              
              showUpload: false
    	  });
    	  $('#kmModal').find('.modal-title').text('상품입력');
    	  <c:if test="${product != null }">
    	  $('#modalDel').show();
    	  </c:if>
    	  
    	  $.extend( FormOptions, {
	  			getDeleteUrl : function(){
			  		return "productMaster/" + $('.modal-body form.pfrm [name="id"]').val();
		  		},
		  		onAfterSaveSuccess : function(responseJson){
		  			oTable.ajax.reload(null, true);
		  			$('#tab_${productMaster.id} button.optp-reload').trigger('click');// reload prodult list.
		  		},
		  		onAfterDeleteSuccess : function(responseJson){
		  			oTable.ajax.reload(null, true);
		  		}
		  } );
    	  
    	  $('input.number').number( true, 0 );
    	  var $retailPrice = $('input[name="retailPrice"]');
    	  var $sellingPrice = $('input[name="sellingPrice"]');
    	  $('input[name="discountRate"]').keyup(function(e){
    		  //console.log(this.value, $(this).val());
    		  var retailPrice = $retailPrice.val();
    		  
    		  if (retailPrice > 0) {
    			  $sellingPrice.val(retailPrice * ((100 - $(this).val()) /100));
			  }
    	  });
    	  
		  $.getJSON( "category/list", function( data ) {
    		  
    		  $.each( data.content, function( index, ctg ) {
    		    $( "<option value='" + ctg.id + "'>" + ctg.name + "</option>" ).appendTo("select.category1");
    		  });
    	  });
    	  
    	  $("select.select2_group").on('change', function(e){
    		  //var optionSelected = $("option:selected", this);
    		  //var valueSelected = this.value;
    		  
    		  var appendSelect = '';
    		  if ($(e.target).hasClass('category1')) {
    			  $("select.category2").empty();// clear.
        		  $('<option value="">::중분류 선택하세요::</option>').appendTo("select.category2");
        		  appendSelect = "select.category2";
			  }
    		  
    		  if ($(e.target).hasClass('category1') || $(e.target).hasClass('category2')) {
        		  $("select.category3").empty();// clear.
        		  $('<option value="">::소분류 선택하세요::</option>').appendTo("select.category3");
        		  
        		  if ($(e.target).hasClass('category2')) {
            		  appendSelect = "select.category3";
    			  }
			  }
    		  
    		  if (this.value && this.value.length > 0 && appendSelect != '') {
	    		  $.getJSON( "category/list?parentCtgId=" + this.value, function( data ) {
	        		  
	    			  $.each( data.content, function( index, ctg ) {
	    	    		    $( "<option value='" + ctg.id + "'>" + ctg.name + "</option>" ).appendTo(appendSelect);
	    	    	  });
	        		 
	        	  });
    		  }
    		  
    		  if ($(e.target).hasClass('category3') && this.value && this.value.length > 0) {
    			  $("button.add-ctg").tooltip('destroy');
    			  $("button.add-ctg").addClass('btn-info');
    		  } else {
    			  $("button.add-ctg").tooltip();
    			  $("button.add-ctg").removeClass('btn-info');
    		  }
    		  
    	  });
    	  
    	  $("button.add-ctg").tooltip();
    	  $("button.add-ctg").click(function(e){
    		  
    		  var ctg3Value = $("select[name='categoryId']").val();
    		  if (ctg3Value == '') {
    			  console.log('not selected.');
    			  return;
			  } 
    		  
    		  $("li.ctg span.rm-link").off();
    		  var ctgInfo = $("select[name='category1'] option:selected").text() + '>' + $("select[name='category2'] option:selected").text() + '>' + $("select[name='categoryId'] option:selected").text();
    		  var hiddenInput = '<input type="hidden" name="ctgIds" value="'+$("select[name='categoryId'] option:selected").val()+'" />';
    		  $('ul.ctg-list').append('<li class="ctg">'+ctgInfo+' <span class="rm-link"><i class="fa fa-close"></i></span>'+hiddenInput+'</li>');
    		  
    		  bindRemoveCTG();
    	  });
    	  
    	  bindRemoveCTG();
    	  
      });
      
      function bindRemoveCTG() {
    	  $("li.ctg span.rm-link").click(function(e){
    		  $(e.target).parents('li').remove();
    	  });
      }
    </script>