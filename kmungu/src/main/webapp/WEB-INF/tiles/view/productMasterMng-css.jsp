<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
	<!-- iCheck -->
    <link href="${res}/vendors/iCheck/skins/flat/green.css" rel="stylesheet">
    <!-- Datatables -->
    <link href="${res}/vendors/datatables.net-bs/css/dataTables.bootstrap.min.css" rel="stylesheet">
    <link href="${res}/vendors/datatables.net-buttons-bs/css/buttons.bootstrap.min.css" rel="stylesheet">
    <!--
    <link href="${res}/vendors/datatables.net-fixedheader-bs/css/fixedHeader.bootstrap.min.css" rel="stylesheet">
    <link href="${res}/vendors/datatables.net-responsive-bs/css/responsive.bootstrap.min.css" rel="stylesheet">
    <link href="${res}/vendors/datatables.net-scroller-bs/css/scroller.bootstrap.min.css" rel="stylesheet">
     -->
    <link href="${res}/vendors/datatables.net-select-bs/css/select.bootstrap.min.css" rel="stylesheet">
    <!-- Select2
    <link href="${res}/vendors/select2/dist/css/select2.min.css" rel="stylesheet">
     -->
    <link href="${res}/vendors/bootstrap-fileinput/css/fileinput.min.css" media="all" rel="stylesheet" type="text/css" />
    <style>
	  ul.ctg-list {
	    list-style-type: none;
	    margin: 0;
	    padding: 5px 0 0 0;
	    float: left;
	    margin-bottom: 10px;
	  }
	  ul.ctg-list li.ctg {
	    margin: 0 5px 5px 0;
	    padding: 5px 10px;
	    font-size: 1.2em;
	    border: 1px solid #c5c5c5;
	    border-radius: 3px;
	    background: #f6f6f6;
	    cursor: pointer;
	    float: left;
	  }
	  ul.ctg-list li.ctg span.rm-link {
	  	visibility : hidden;
	  	margin-left: 5px;
	  }
	  ul.ctg-list li.ctg:hover span.rm-link {
	  	visibility : visible;
	  	color: #C5C7CB;
	  }
	  ul.ctg-list li.ctg:hover span.rm-link:hover {
	  	color: #73879C;
	  }
	  div.optpl {margin-top: 10px;}
	</style>
    