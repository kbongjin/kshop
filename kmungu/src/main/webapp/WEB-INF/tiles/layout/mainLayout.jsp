<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%
	request.setAttribute("res", request.getContextPath() + "/resources") ;
%>
<!DOCTYPE html>
<html lang="en">
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <!-- Meta, title, CSS, favicons, etc. -->
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">

    <title>K문구</title>

    <!-- Bootstrap -->
    <link href="${res}/vendors/bootstrap/dist/css/bootstrap.min.css" rel="stylesheet">
    <!-- Font Awesome -->
    <link href="${res}/vendors/font-awesome/css/font-awesome.min.css" rel="stylesheet">
    
    <tiles:insertAttribute name="page-css" />

    <!-- Custom Theme Style -->
    <link href="${res}/css/custom.css" rel="stylesheet">
    <style type="text/css">
    div.dataTables_processing {padding: 4px 0 !important;}
    
    </style>
  </head>

  <body class="nav-md">
    <div class="container body">
      <div class="main_container">

	      <tiles:insertAttribute name="left-menu" />
	 
	 	  <!-- top navigation -->
	 	  <tiles:insertAttribute name="top-nav" />
	
	      <!-- page content -->
	      <div class="right_col" role="main">
	     	<tiles:insertAttribute name="page-body" />
	      </div>
	      <!-- /page content -->
        
	      <!-- footer content -->
	      <footer>
	          <div class="pull-right">
	            Gentelella - Bootstrap Admin Template by <a href="https://colorlib.com">Colorlib</a>
	          </div>
	          <div class="clearfix"></div>
	      </footer>
	      <!-- /footer content -->
      </div>
    </div>
    <!-- Modal -->
	<div class="modal fade" id="kmModal" tabindex="-1" role="dialog" aria-labelledby="kmModalLabel" style="z-index: 99999">
	  <div class="modal-dialog" role="document" style="width: 1300px;">
	    <div class="modal-content">
	      <div class="modal-header">
	        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
	        <h4 class="modal-title" id="kmModalLabel">Modal title</h4>
	      </div>
	      <div class="modal-body">
	      	
	      </div>
	      <div class="modal-footer">
	        <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
	        <button id="modalSave" type="button" class="btn btn-primary">저장</button>
	        <button id="modalDel" type="button" class="btn btn-warning" data-toggle="popover" title="확인" data-placement="left" data-trigger="focus"
	        data-content="삭제하시겠습니까?" style="display: none;">삭제</button>
	      </div>
	    </div>
	  </div>
	</div>
	<div class="mdLoading" style="text-align: center; display: none;">
        <i class="fa fa-spinner fa-pulse fa-lg fa-fw"></i>
		<span>Loading...</span>
	</div>

    <!-- jQuery -->
    <script src="${res}/vendors/jquery/dist/jquery.min.js"></script>
    <!-- Bootstrap -->
    <script src="${res}/vendors/bootstrap/dist/js/bootstrap.min.js"></script>

    <!-- FastClick -->
    <script src="${res}/vendors/fastclick/lib/fastclick.js"></script>
    <!-- NProgress -->
    <script src="${res}/vendors/nprogress/nprogress.js"></script>
    <!-- PNotify -->
    <script src="${res}/vendors/pnotify/dist/pnotify.js"></script>
    <script src="${res}/vendors/pnotify/dist/pnotify.buttons.js"></script>
    <script src="${res}/vendors/pnotify/dist/pnotify.callbacks.js"></script>
    
    <!-- global java script -->
	<script type="text/javascript">
		var deleteable = false;
	
	    $( document ).ready(function() {
	    	
	    	PNotify.prototype.options.delay = 8000;
	    	$( document ).ajaxStart(function() {
	    		NProgress.start();
	    	});
	    	$( document ).ajaxError(function( event, jqxhr, settings, thrownError ) {
	    		
	    		if (jqxhr.responseText == '' || thrownError == '') {
	    			new PNotify({
	    	            title: 'Server Error',
	    	            text: "서버가 응답하지 않습니다. 서버가 기동중인지 확인하세요.",
	    	            type: 'error',
	    	            styling: 'bootstrap3'
	    	        });
	    		} else if (jqxhr.status == 401) {

	    			new PNotify({
	    	            title: 'Server Error',
	    	            text: "로그인 정보가 만료되었습니다. 다시 로그인해주세요.",
	    	            type: 'error',
	    	            delay: 3000,
	    	            styling: 'bootstrap3',
	    	            after_close: function(notice, timer_hide) {
	    	            	window.location.href = "index.html";
	    	            }
	    	        });
	    		} else if (jqxhr.status == 403) {

	    			new PNotify({
	    	            title: '접근 권한 없음',
	    	            text: 'request url : ' + settings.url ,
	    	            type: 'error',
	    	            styling: 'bootstrap3'
	    	        });
					
				} else {
					new PNotify({
	    	            title: 'Server Error',
	    	            text: "Error : " + thrownError + " of " + settings.url ,
	    	            type: 'error',
	    	            styling: 'bootstrap3'
	    	        });
				}
	    		
			});
	    	
	    	
	    	$( document ).ajaxComplete(function( event, jqxhr, settings ) {
	    		//console.log(xhr.responseText);
	    		
	    		var ct = jqxhr.getResponseHeader("content-type") || "";
	    		
	    		if (jqxhr.status == 200 && ct.indexOf('json') > -1) {
	    			var resJson = jQuery.parseJSON(jqxhr.responseText);
		    		
		    		if (resJson.success === false) {
		    			new PNotify({
		    	            title: '작업 실패',
		    	            text: resJson.msg ,
		    	            type: 'error',
		    	            styling: 'bootstrap3'
		    	        });
					}
				}
	    		
	    		NProgress.done();
	    	});
	    	
	    	var $mdLoading = $('div.mdLoading');
	    	$('#kmModal').on('hidden.bs.modal', function (e) {
	    		$( "#kmModal form" ).replaceWith( $mdLoading );
	    		$('#modalDel').hide();
	    	});
	    	$('#kmModal').on('show.bs.modal', function (e) {
	    		$('#kmModal div.mdLoading').show();
	    	});
	    	$('#kmModal').on('shown.bs.modal', function (e) {
	    		$('#kmModal input:visible:first').focus();
	    	});
	    	
	    	$('[data-toggle="popover"]').popover();
	    	$('#modalDel').on('shown.bs.popover', function () {
	    		deleteable = true;
	    	});
	    	$('#modalDel').on('hidden.bs.popover', function () {
	    		deleteable = false;
	    	});
	    	
	    });
	</script>
	    
    <!-- Our JavaScript -->
    <tiles:insertAttribute name="page-js" />
</body>

</html>
