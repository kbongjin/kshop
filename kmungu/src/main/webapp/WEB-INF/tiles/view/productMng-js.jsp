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
    	  
        oTable = $('#datatable').DataTable({
            "ajax": {
        	    "url": "product/list",
        	    "data": function ( d ) {
        	      return $.extend( {}, d, {"ctg1": $("select.ctg1").val(), "ctg2": $("select.ctg2").val()} );
        	    }
        	},
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
                },
                {
                    text: 'Reload',
                    action: function ( e, dt, node, config ) {
                    	dt.ajax.reload(null, false);
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
                    return "<a href='javascript:loadProduct("+row.id+")' class=''>" +data +"</a>";
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
                searchable : false,
            }, {
                data : 'createDt',
                orderable : true,
                searchable : false
			}, {
                orderable : false,
                searchable : false,
                render: function ( data, type, row ) {
                    return '<a class="btn btn-primary btn-sm pdetail" href="javascript:loadContents('+row.id+')" role="button">상품설명</a>';
                }
            }, {
            	
            	data : 'stockQty',
                orderable : true,
                searchable : false,
                render: function ( data, type, row ) {
                    return '<a class="btn btn-primary btn-sm stock" href="javascript:loadStocks('+row.id+')" role="button">재고관리 <span class="badge">'+data+'</span></a>';
                }
            }],
            initComplete: function () {

            	drawCategorySelect();
            }
        });
        
        $('#datatable_filter input').unbind();
        $('#datatable_filter input').bind('keyup', function(e) {
            if(e.keyCode == 13) {
             oTable.search(this.value).draw();   
         }
        }); 
        
        $('#datatable').on( 'draw.dt', function () {
            console.log( 'Redraw occurred at: '+new Date().getTime() );
            
            $("a.stock").on('click', function(e){
            	
            	var offset = $("div.stock").offset();
                $('html, body').animate({scrollTop : offset.top}, 400);
            });
            
            if (productId > 0) {
            	productId = 0;
                sTable.ajax.reload(null, false);
			}
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
        
        
     	// ------------ 재고 목록 -----------------
        sTable = $('#datatable2').DataTable({
        	//'ajax' : 'product/stock/list',
        	"ajax": {
        	    "url": "product/stock/list",
        	    "data": function ( d ) {
        	      return $.extend( {}, d, {"productId": productId} );
        	    }
        	},
            'processing': true,
            "language": {
                "processing": "데이타 조회중..."
             },
            'serverSide' : true,
            'select' : true,
        	dom: 'Blrtip',
            buttons: [
                {
                    text: '재고 입력',
                    action: function ( e, dt, node, config ) {
                    	
                    	if ( productId === 0 ) {
                    		new PNotify({
        	    	            title: '주의',
        	    	            text: '먼저 상품의 재고관리 버턴을 클릭하세요.',
        	    	            delay: 3000,
        	    	            styling: 'bootstrap3'
        	    	        });
                    		return;
                    	}
                    	
                    	$('#kmModal').modal('show');
                    	$('#kmModal .modal-body').load( "product/stock/0?productId=" + productId, function() {
                    		
	                    });
                    }
                }
            ],
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
                data : 'stockQty',
                orderable : false,
                searchable : false
            }, {
                data : 'stockDt',
                orderable : true,
                searchable : false
            }, {
                data : 'updateDt',
                orderable : false,
                searchable : false
            }],
            footerCallback: function ( row, data, start, end, display ) {
                var api = this.api(), data;
     
                // Remove the formatting to get integer data for summation
                var intVal = function ( i ) {
                    return typeof i === 'string' ?
                        i.replace(/[\$,]/g, '')*1 :
                        typeof i === 'number' ?
                            i : 0;
                };
         
                // Total over this page
                pageTotal = api
                    .column( 3, { page: 'current'} )
                    .data()
                    .reduce( function (a, b) {
                        return intVal(a) + intVal(b);
                    }, 0 );
     
                // Update footer
                $( api.column( 3 ).footer() ).html(
                    '$'+pageTotal 
                );
            }
        });
     	
        sTable.on( 'select', function ( e, dt, type, indexes ) {
            var rowData = dt.rows( indexes ).data().toArray();
            //alert(JSON.stringify( rowData ));
            //alert(rowData[0].id);
            
            $('#kmModal').modal('show');
            $('#kmModal .modal-body').load( "product/stock/"+ rowData[0].id );
        } );
        
        
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
      
      function loadStocks(pId) {
    	  //alert(pId);
    	  productId = pId;
    	  sTable.ajax.reload(null, false);
      }
      
      function drawCategorySelect() {
    	  
    	  $('<label>카테고리: <select name="ctg1" class="form-control input-sm ctg1"><option value="">::대분류(전체)::</option></select> </lable>' 
    			  + '<select name="ctg2" class="form-control input-sm ctg2"><option value="">::중분류(전체)::</option></select>')
          .prependTo( '#datatable_filter' );
    	  
    	  $.getJSON( "code/list/p_categoryA", function( data ) {
    		  
    		  $.each( data, function( index, code ) {
    		    $( "<option value='" + code.code + "'>" + code.codeNm + "</option>" ).appendTo("select.ctg1");
    		  });
    		  /*
    		  $( "<ul/>", {
    		    "class": "my-new-list",
    		    html: items.join( "" )
    		  }).appendTo( "body" );
    		  */
    	  });
    	  
    	  $("select.ctg1").on('change', function(e){
    		  //var optionSelected = $("option:selected", this);
    		  //var valueSelected = this.value;
    		  $("select.ctg2").empty();// clear.
    		  $('<option value="">::중분류(전체)::</option>').appendTo("select.ctg2");
    		  
    		  oTable.ajax.reload(null, true);
    		  
    		  if (this.value || this.value.length > 0) {
	    		  $.getJSON( "code/list/p_category" + this.value, function( data ) {
	        		  
	        		  $.each( data, function( index, code ) {
	        		    $( "<option value='" + code.code + "'>" + code.codeNm + "</option>" ).appendTo("select.ctg2");
	        		  });
	        		 
	        	  });
    		  }
    		  
    	  });
          
      }
      
    </script>
    <!-- /Datatables -->