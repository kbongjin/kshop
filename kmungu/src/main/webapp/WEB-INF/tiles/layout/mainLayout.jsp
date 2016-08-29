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
	<div class="modal fade" id="kmModal" tabindex="-1" role="dialog" aria-labelledby="kmModalLabel">
	  <div class="modal-dialog modal-lg" role="document">
	    <div class="modal-content">
	      <div class="modal-header">
	        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
	        <h4 class="modal-title" id="kmModalLabel">Modal title</h4>
	      </div>
	      <div class="modal-body">
	      	<div style="text-align: center;">
		        <i class="fa fa-spinner fa-pulse fa-lg fa-fw"></i>
				<span>Loading...</span>
			</div>
	      </div>
	      <div class="modal-footer">
	        <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
	        <button id="modalSave" type="button" class="btn btn-primary">저장</button>
	      </div>
	    </div>
	  </div>
	</div>

    <!-- jQuery -->
    <script src="${res}/vendors/jquery/dist/jquery.min.js"></script>
    <!-- Bootstrap -->
    <script src="${res}/vendors/bootstrap/dist/js/bootstrap.min.js"></script>
    <!-- FastClick -->
    <script src="${res}/vendors/fastclick/lib/fastclick.js"></script>
    <!-- NProgress -->
    <script src="${res}/vendors/nprogress/nprogress.js"></script>
    
    <!-- global java script -->
	<script type="text/javascript">
	    $( document ).ready(function() {
	    	$( document ).ajaxError(function( event, jqxhr, settings, thrownError ) {
	    		
	    		if (jqxhr.responseText == '') {
	    			alert("서버가 응답하지 않습니다. 서버상태를 확인하세요.");
	    		} else if (jqxhr.status == 401) {
					alert("로그인 정보가 만료되었습니다. 다시 로그인해주세요.");
					window.location.href = "index.html";
				}
	    		
			    //alert( "Error : " + thrownError + " of " + settings.url );
			});
	    });
	</script>
	    
    <!-- Our JavaScript -->
    <tiles:insertAttribute name="page-js" />
</body>

</html>
