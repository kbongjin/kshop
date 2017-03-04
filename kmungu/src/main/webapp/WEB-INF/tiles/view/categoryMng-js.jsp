<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

	<script src="${res}/js/jquery.form.min.js"></script>
    
	<!-- Parsley -->
    <script src="${res}/vendors/parsleyjs/dist/parsley.min.js"></script>
    <script src="${res}/vendors/parsleyjs/dist/i18n/ko.js"></script>
    <!-- Custom Theme Scripts -->
    <script src="${res}/js/custom.min.js"></script>
    
    
    <script>
    
    var rmlink='<span class="rm-link"><i class="fa fa-close"></i></span>';
    var tabLen = 1;
    
    $(document).ready(function() {
    	
    	// get tabs
    	$('#ctgTab').load( "category", function() {
    		
    		$('a.add').click(function() {
        		
        		$('#kmModal').modal('show');
        		tabLen++;
            	$('#kmModal .modal-body').load( "category/0?level=1&orderSeq=" + tabLen, function() {
            		 
                });
        	});
    		
    		tabLen = $('#ctgTab li').length - 1;
        	console.log('tab length : ' + tabLen);
        	
        	bindTabEvents();
        	
			$('a.modify').click(function() {
        		
				viewCtg(cid);
        	});
        	
        	$('#ctgTab li:first a').tab('show');
        });
    	
    	// 추가 button click.
    	$('button.add').click(function(e) {
    		
    		$('#kmModal').modal('show');
    		
    		var parentCtgId = 0;
    		var level = e.target.getAttribute('level');
    		console.log('add btn click.', e.target, 'level:', level);
    		var orderSeq = $('div.x_content.ctg' + level + ' [name="maxSeq"]').val();
    		if (orderSeq == '') {
    			orderSeq = 1;
			} else {
				orderSeq = parseInt(orderSeq) + 1;
			}
    		
    		if (level == 2) {
    			parentCtgId = $('#ctgTab li.active a').attr('cid');
			} else if(level == 3) {
				parentCtgId = $('div.x_content.ctg2 tbody tr.selected').attr('cid');
			}
    		
        	$('#kmModal .modal-body').load( "category/0?parentCtgId=" + parentCtgId +"&level="+level+"&orderSeq=" + orderSeq, function() {
        		 
            });
    	});
    	
    	
    });
    
    var cid = 0;
    var selectedCtg2Id = 0;
    function bindTabEvents() {
    	
    	// tab click! 
    	$('a[data-toggle="tab"]').on('shown.bs.tab', function (e) {
	  		e.target // newly activated tab
	  		console.log("shown tab.", e.target);
	  		//e.relatedTarget // previous active tab
	  		var parentCtgId = e.target.getAttribute('cid');
	  		  
	  		loadCategoryList(2, parentCtgId);
	  		 
	  		// init ctg3
	  		$('button.ctg3').attr("disabled","disabled");
	  		$('div.x_content.ctg3').html('<p>중분류를 선택하면 데이타가 조회됩니다.</p>');
	  		selectedCtg2Id = 0;
	  		$('div.x_title.ctg3 small').text('');
  		});
    	
    	$('span.rm-link').click(function(e) {
    		e.stopPropagation();
    		//alert("삭제..");
    		var ctgId = $(e.target).parents('a').attr('cid');
    		confirm(function(){
    			console.log('delete:', ctgId);
    			var request = $.ajax({
	    			  method : "DELETE",
					  url: "${pageContext.request.contextPath}/category/" + ctgId,
					  //data: $( "form.cform" ).serialize(),
					  dataType: "json"
	    	    });
	    		
	    	    request.done(function( responseJson ) {
	    	    	$(e.target).parents('li').remove();
    		    });
    		});
    	});
    }
    
    function loadCategoryList(level, parentCtgId) {
    	
    	if (parentCtgId == null) {
    		console.log('cancel loadCategoryList(..). parentCtgId is null.');
			return;
		}
    	
    	if (level == 2) {
			$('div.x_content.ctg2 tbody td').off();
    	}
    	console.log('load level:', level, ', parentCtgId:', parentCtgId);
    	$('div.x_content.ctg'+level).load( "category?parentCtgId=" + parentCtgId, function() {
			  
    		if (level == 2) {
    			$('div.x_content.ctg2 tbody td').click(function(e){
    				//console.log('level 2 cid:', e.target.parentElement.getAttribute('cid'), e.target);
    				
    				if(event.target.tagName.toLowerCase() === 'td') {
    					
    					$('div.x_content.ctg2 tbody tr.selected').removeClass('selected');
    					$(e.target.parentElement).addClass('selected');
    					
    					selectedCtg2Id = e.target.parentElement.getAttribute('cid');
	    				loadCategoryList(3, selectedCtg2Id);
    				}
    			});
    			
    			if (selectedCtg2Id > 0) {
    				$('div.x_content.ctg2 tbody [cid="'+selectedCtg2Id+'"]').addClass('selected');
				}
    			
			} else {
				$('button.ctg3').removeAttr('disabled');
				$('div.x_title.ctg3 small').text($('div.x_content.ctg2 tbody tr.selected td:eq(0)').text());
			}
		});
    }
    
    function viewCtg(ctgId) {
    	
    	$('#kmModal').modal('show');
		//var cid = $('#ctgTab li.active a').attr('cid');
    	$('#kmModal .modal-body').load( "category/" + ctgId, function() {
    		 
        });
    }
    
    function deleteCtg(level, ctgId, parentCtgId) {
    	confirm(function(){
			console.log('delete:', ctgId);
			var request = $.ajax({
    			  method : "DELETE",
				  url: "${pageContext.request.contextPath}/category/" + ctgId,
				  //data: $( "form.cform" ).serialize(),
				  dataType: "json"
    	    });
    		
    	    request.done(function( responseJson ) {
    	    	loadCategoryList(level, parentCtgId);
		    });
		});
    }
    </script>
    
    