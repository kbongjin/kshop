<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
	<!-- Datatables -->
    <script src="${res}/vendors/datatables.net/js/jquery.dataTables.min.js"></script>
    <script src="${res}/vendors/datatables.net-bs/js/dataTables.bootstrap.min.js"></script>
    <script src="${res}/vendors/datatables.net-select/js/dataTables.select.min.js"></script>
    <script src="${res}/vendors/datatables.net-buttons/js/dataTables.buttons.min.js"></script>
    <script src="${res}/vendors/datatables.net-buttons-bs/js/buttons.bootstrap.min.js"></script>
    
    <!--
    <script src="${res}/vendors/datatables.net-buttons/js/buttons.flash.min.js"></script>
    <script src="${res}/vendors/datatables.net-buttons/js/buttons.html5.min.js"></script>
    <script src="${res}/vendors/datatables.net-buttons/js/buttons.print.min.js"></script>
    <script src="${res}/vendors/datatables.net-fixedheader/js/dataTables.fixedHeader.min.js"></script>
    <script src="${res}/vendors/datatables.net-keytable/js/dataTables.keyTable.min.js"></script>
    <script src="${res}/vendors/datatables.net-responsive/js/dataTables.responsive.min.js"></script>
    <script src="${res}/vendors/datatables.net-responsive-bs/js/responsive.bootstrap.js"></script>
    <script src="${res}/vendors/datatables.net-scroller/js/dataTables.scroller.min.js"></script>
    <script src="${res}/vendors/jszip/dist/jszip.min.js"></script>
    <script src="${res}/vendors/pdfmake/build/pdfmake.min.js"></script>
    <script src="${res}/vendors/pdfmake/build/vfs_fonts.js"></script>
    -->
    <script src="${res}/js/jquery.spring-friendly.min.js"></script>
    <!-- Select2
    <script src="${res}/vendors/select2/dist/js/select2.full.min.js"></script>
     -->
    <script src="${res}/js/jquery.form.min.js"></script>
    
    <!-- the main fileinput plugin file -->
    <script src="${res}/vendors/bootstrap-fileinput/js/plugins/purify.min.js"></script>
    <script src="${res}/vendors/bootstrap-fileinput/js/plugins/sortable.min.js"></script>
	<script src="${res}/vendors/bootstrap-fileinput/js/fileinput.min.js"></script>
	<!-- Parsley -->
    <script src="${res}/vendors/parsleyjs/dist/parsley.min.js"></script>
    <script src="${res}/vendors/parsleyjs/dist/i18n/ko.js"></script>
	
    <!-- Custom Theme Scripts -->
    <script src="${res}/js/custom.min.js"></script>

    <!-- Datatables -->
    <script>
      $(document).ready(function() {
    	  
        var oTable = $('#datatable').DataTable({
            'ajax' : 'product/list',
            'processing': true,
            "language": {
                "processing": "데이타 조회중..."
             },
            'serverSide' : true,
            "lengthMenu": [[10, 25, 35, 50], [10, 25, 35, 50]],
            'select': true,
            "order": [[ 6, "desc" ]],
            dom: 'Blfrtip',
            buttons: [
                {
                    text: '신규 등록',
                    action: function ( e, dt, node, config ) {
                    	$('#kmModal').modal('show');
                    	$('#kmModal .modal-body').load( "product/0", function() {
                    		
	                    });
                    }
                }
            ],
            columns : [{
                data : 'id',
                visible : true,
                searchable : false,
                width: "40px"
            }, {
                data : 'img1Path',
                orderable : false,
                searchable : false,
                render: function ( data, type, row ) {
                	if(data) {
                		return "<img src='"+data+"' width='100px' />";
                	}
                    return "없음";
                }
            }, {
                data : 'name',
                render: function ( data, type, row ) {
                    return "<a href='#'>" +data +"</a>";
                }
            }, {
                data : 'retailPrice',
                orderable : false,
                searchable : false
            }, {
                data : 'discountPrice',
                orderable : false,
                searchable : false
            }, {
                data : 'sellingPrice',
                orderable : false,
                searchable : false
            }, {
                data : 'createDt',
                orderable : true,
                searchable : false
            }]
        });
        
        $('#datatable_filter input').unbind();
        $('#datatable_filter input').bind('keyup', function(e) {
            if(e.keyCode == 13) {
             oTable.search(this.value).draw();   
         }
        }); 
        
        oTable.on( 'select', function ( e, dt, type, indexes ) {
            var rowData = dt.rows( indexes ).data().toArray();
            //alert(JSON.stringify( rowData ));
            //alert(rowData[0].id);
            console.log("load product/" + rowData[0].id);
            
            $('#kmModal').modal('show');
            $('#kmModal .modal-body').load( "product/" + rowData[0].id);
        } );
        
        $("#modalSave").click(function(){
   	   		//alert("click!! btnReg");
        	$('#pfrm').parsley().validate();
        	
        	$('#pfrm').ajaxSubmit({
                beforeSubmit: function (data,form,option) {
					var valid = $('#pfrm').parsley().isValid();
					console.log('valid: ' + valid);
                    return valid;
                },
                success: function(response, status){
                	//console.log(response);//response is json.
                	
                	oTable.ajax.reload(null, false);
                	
                	if (response.success) {
	                    new PNotify({
		    	            title: 'Success',
		    	            text: "정상 등록되었습니다.",
		    	            delay: 3000,
		    	            type: 'success',
		    	            styling: 'bootstrap3'
		    	        });
	                    $('#kmModal').modal('hide');
                	}
                },
                error: function(){
                    //에러발생을 위한 code페이지
                }                               
            });
   	   	});
        
        
        $("#modalDel").click(function(){

			if(deleteable) {
				$.ajax({
	    			  method : "DELETE",
					  url: "product/" + $('#pfrm [name="id"]').val(),
					  dataType: "json"
					  
	    	    }).done(function( responseJson ) {
	    	    	new PNotify({
	    	            title: 'Success',
	    	            text: "정상 삭제되었습니다.",
	    	            delay: 3000,
	    	            type: 'success',
	    	            styling: 'bootstrap3'
	    	        });
                    $('#kmModal').modal('hide');
                    oTable.ajax.reload(null, false);
  		   		});
			
			}
	        	
	   	});
        
        // ------------ 재고 목록 -----------------
        $('#datatable2').DataTable({
        	//'ajax' : 'product/stock/list',
        	"ajax": {
        	    "url": "product/stock/list",
        	    "data": function ( d ) {
        	      return $.extend( {}, d, {"productId": 1} );
        	    }
        	},
            'processing': true,
            "language": {
                "processing": "데이타 조회중..."
             },
            'serverSide' : true,
        	dom: 'Blfrtip',
            buttons: [
                {
                    text: '재고 입력',
                    action: function ( e, dt, node, config ) {
                    	$('#kmModal').modal('show');
                    	$('#kmModal .modal-body').load( "product/0", function() {
                    		
	                    });
                    }
                }
            ],
            "search": {
                "productId": 1
            },
            columns : [{
                data : 'id',
                visible : true,
                searchable : false,
                width: "40px",
            }, {
                data : 'unitPrice',
                orderable : false,
                searchable : false
            }, {
                data : 'qty',
                orderable : false,
                searchable : false
            }, {
                data : 'sumPrice',
                orderable : false,
                searchable : false
            }, {
                data : 'stockDt',
                orderable : true,
                searchable : false
            }]
        });
        
        
      });
      
      
    </script>
    <!-- /Datatables -->