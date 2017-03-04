<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<form action="category" method="POST" data-parsley-validate class="form-horizontal form-label-left pfrm" >
					  <input type="hidden" name="id" value="${category.id}"/>
					  <input type="hidden" name="level" value="${category.level}"/>
					  <input type="hidden" name="parentCtgId" value="${category.parentCtgId}"/>
					  <input type="hidden" name="orderSeq" value="${category.orderSeq}"/>
                      <div class="form-group">
                        <label class="control-label col-md-2 col-sm-2 col-xs-12">카테고리명</label>
                        <div class="col-md-10 col-sm-10 col-xs-12">
                          <input type="text" name="name" class="form-control" required value="${category.name}">
                        </div>
                      </div>
                      
                    </form>
    <script>
      $(document).ready(function() {
    	 
    	  $('#kmModal').find('.modal-title').text('카테고리 입력');
    	  
    	  $.extend( FormOptions, {
	  			getDeleteUrl : function(){
			  		return "category/" + $('form.pfrm [name="id"]').val();
		  		},
		  		onAfterSaveSuccess : function(responseJson){
		  			var ctgid = $('form.pfrm [name="id"]').val();
		  			var tabName = $('form.pfrm [name="name"]').val();
		  			var level = $('form.pfrm [name="level"]').val();
		  			//console.log('onAfterSaveSuccess cid=', cid);
		  			if (ctgid == '') {
		  				
		  				if (level == 1) {
		  					ctgid = responseJson.data.id;
			                $('#ctgTab li.dropdown').before($('<li role="presentation"><a href="#tab_content1" role="tab" data-toggle="tab" cid="'+ ctgid +'"><span class="tname">' + tabName + '</span>' + rmlink + '</a></li>'));
			                //$('#ctgTabContent').append($('<div role="tabpanel" class="tab-pane fade" id="tab_content' + tabID + '" aria-labelledby="profile-tab">' + tabName + ' content</div>'));
			                 
			                $('a[data-toggle="tab"]').off();
			                $('span.rm-link').off();
			                
			                bindTabEvents();
						}
		  				
					} else {
						//수정
						if (level == 1) {
							$('#ctgTab li.active span.tname').text(tabName);
						}
					}
		  			//console.log('onAfterSaveSuccess level=', level);
		  			if (level > 1) {
		  				var parentCtgId = $('form.pfrm [name="parentCtgId"]').val();
		  				loadCategoryList(level, parentCtgId);
		  			} else {
		  				console.log("not loadCategoryList(..) level:", level);
		  			}
		  			
		  		},
		  		onAfterDeleteSuccess : function(responseJson){
		  			//
		  		}
		  } );
    	  
      });
    </script>