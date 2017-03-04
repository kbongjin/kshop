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
    
      var oTable = {};
      var sTable = {};
      var FormOptions = {
    		beforeSubmit : function() {console.log('beforeSubmit');}
      };
      
      $(document).ready(function() {
    	  
        oTable = $('#ordertable').DataTable({
            "ajax": "orders",
            'processing': true,
            "language": {
                "processing": "데이타 조회중..."
             },
            'serverSide' : true,
            "lengthMenu": [[10, 25, 35, 50], [10, 25, 35, 50]],
            'select': true,
            //"order": [[ 7, "desc" ]],
            dom: 'Blfrtip',
            buttons: [
                {
                    text: 'Reload',
                    action: function ( e, dt, node, config ) {
                    	dt.ajax.reload(null, false);
                    }
                }
            ],
            columns : [{
                data : 'orderNo',
                orderable : false,
                searchable : true,
                width: "40px"
            }, {
                data : 'userId',
                orderable : false,
                searchable : true
            }, {
                data : 'orderPrice',
                orderable : false,
                searchable : false
            }, {
                data : 'statusCd',
                orderable : false,
                searchable : false
            }, {
                data : 'parcleInvNo',
                orderable : false,
                searchable : false
            }, {
                data : 'parcleInvPrice',
                orderable : false,
                searchable : false
            }, {
                data : 'recvAddr1',
                orderable : false,
                searchable : false
            }, {
            	
            	data : 'orderDt',
                orderable : true,
                searchable : false
            }],
            initComplete: function () {

            	//drawCategorySelect();
            }
        });
        
        $('#ordertable_filter input').unbind();
        $('#ordertable_filter input').bind('keyup', function(e) {
            if(e.keyCode == 13) {
             oTable.search(this.value).draw();   
         }
        }); 
        
        $('#ordertable').on( 'draw.dt', function () {
            console.log( 'Redraw occurred at: '+new Date().getTime() );
            /*
            $("a.stock").on('click', function(e){
            	
            	var offset = $("div.stock").offset();
                $('html, body').animate({scrollTop : offset.top}, 400);
            });
            
            if (productId > 0) {
            	productId = 0;
                sTable.ajax.reload(null, false);
			}*/
        } );
        
        $("#modalSave").click(function(){
 	   		//alert("click!! btnReg");
 	   		FormOptions.beforeSubmit();
	      	$('form.pfrm').parsley().validate();
	      	
	      	$('form.pfrm').ajaxSubmit({
	              beforeSubmit: function (data,form,option) {
						
	                  return $('form.pfrm').parsley().isValid();
	              },
	              success: function(response, status){
	              	//console.log(response);//response is json.
	              	
	              	FormOptions.reloadTable(null, false);
	              	
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
					  //url: "product/" + $('#pfrm [name="id"]').val(),
					  url: FormOptions.getDeleteUrl(),
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
                  	FormOptions.reloadTable(null, false);
		   		});
			
			}
	   	});
        
        
        
        
      });
      
      var productId = 0;
      
      function loadProduct(pId) {
    	  $('#kmModal').modal('show');
          $('#kmModal .modal-body').load( "product/" + pId);
      }
      
      function loadContents(pId) {
    	  $('#kmModal').modal('show');
          $('#kmModal .modal-body').load( "product/" + pId + "/editor");
      }

      
    </script>
    <!-- /Datatables -->