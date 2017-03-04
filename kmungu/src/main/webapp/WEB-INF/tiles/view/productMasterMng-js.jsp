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
    <script src="${res}/js/jquery.number.min.js"></script>
    
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
      
      $(document).ready(function() {
    	  
        oTable = $('#datatable').DataTable({
            "ajax": {
        	    "url": "productMaster/list",
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
                    	$('#kmModal .modal-body').load( "productMaster/0", function() {
                    		
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
                    return "<a href='javascript:loadProductTab("+row.id+", \""+data+"\");' class=''>" +data +"</a>";
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
            /*}, {
            	
            	data : 'stockQty',
                orderable : true,
                searchable : false,
                render: function ( data, type, row ) {
                    return '<a class="btn btn-primary btn-sm stock" href="javascript:loadStocks('+row.id+')" role="button">재고관리 <span class="badge">'+data+'</span></a>';
                } */
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
        
        //after reload.
        $('#datatable').on( 'draw.dt', function () {
            console.log( 'Redraw occurred at: '+new Date().getTime() );
            
            
        } );
        
        
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
            },
            initComplete: function () {
            	
            	if (productId == 0 ) {
            		$('div.stock .collapse-link').trigger('click');
				} 
            }
        });
     	
        sTable.on( 'select', function ( e, dt, type, indexes ) {
            var rowData = dt.rows( indexes ).data().toArray();
            //alert(JSON.stringify( rowData ));
            //alert(rowData[0].id);
            
            $('#kmModal').modal('show');
            $('#kmModal .modal-body').load( "product/stock/"+ rowData[0].id );
        } );
        
        $(document).on("click", "li.pm span.rm-link", function(e) {
    		e.stopPropagation();
    		
    		var pid = $(e.target).parents('a').attr('pid');
    		console.log("삭제..");
    		$(e.target).parents('li').remove();
    		$('#tab_' + pid).remove();
    		$('#productTabs li:first a').tab('show');
    	});
        
      });
      
      var rmlink='<span class="rm-link"><i class="fa fa-close"></i></span>';
      
      function loadProductTab(pmId, pName) {
    	  //$('#kmModal').modal('show');
          //$('#kmModal .modal-body').load( "product/" + pId);
          var tabID = 'tab_' + pmId;
          
          if ($('#'+tabID).length > 0) {
        	  $('a[pid="'+pmId+'"]').tab('show');
        	  return;
		  }
          
          var tabName = pName;
    	  $('#productTabs').append('<li role="presentation" class="pm"><a href="#'+tabID+'" role="tab" data-toggle="tab" pid="'+ pmId +'"><span class="tname">' + tabName + '</span>' + rmlink + '</a></li>');
    	  $('#productTabsContent').append('<div role="tabpanel" class="tab-pane fade" id="' + tabID + '" aria-labelledby="profile-tab"></div>');
    	  $('#'+tabID).load( "productMaster/" + pmId);
    	  $('a[pid="'+pmId+'"]').tab('show');
          
      }
      
      function loadContents(pmId) {
    	  $('#kmModal').modal('show');
          $('#kmModal .modal-body').load( "productMaster/" + pmId + "/editor");
      }
      
      var productId = 0;
      function loadStocks(pId, pName) {
    	  //alert(pId);
    	  $('div.stock').show();
    	  $('div.stock small').text(pName);
    	  productId = pId;
    	  sTable.ajax.reload(null, false);
    	  
    	  // scroll down!!
    	  var offset = $("div.stock").offset();
          $('html, body').animate({scrollTop : offset.top}, 400);
          
          
          if ($('div.stock .collapse-link i').hasClass('fa-chevron-down')) {
        	  console.log("up!!!")
        	  $('div.stock .collapse-link').trigger('click');//up!!
		  } 
    	  
          /* 
          if (productId > 0) {
          	productId = 0;
              sTable.ajax.reload(null, false);
		   } */
      }
      
      function drawCategorySelect() {
    	  
    	  $('<label>카테고리: <select name="ctg1" class="form-control input-sm ctg1 ctg-select"><option value="">::대분류(전체)::</option></select>' 
    			  + '<select name="ctg2" class="form-control input-sm ctg2 ctg-select"><option value="">::중분류(전체)::</option></select>'
    			  + '<select name="ctg3" class="form-control input-sm ctg3 ctg-select"><option value="">::소분류(전체)::</option></select> </lable>')
          .prependTo( '#datatable_filter' );
    	  
    	  $.getJSON( "category/list", function( data ) {
    		  
    		  $.each( data.content, function( index, ctg ) {
    		    $( "<option value='" + ctg.id + "'>" + ctg.name + "</option>" ).appendTo("select.ctg1");
    		  });
    		  /*
    		  $( "<ul/>", {
    		    "class": "my-new-list",
    		    html: items.join( "" )
    		  }).appendTo( "body" );
    		  */
    	  });
    	  
    	  $("select.ctg-select").on('change', function(e){
    		  //var optionSelected = $("option:selected", this);
    		  //var valueSelected = this.value;
    		  
    		  var appendSelect = '';
    		  if ($(e.target).hasClass('ctg1')) {
    			  $("select.ctg2").empty();// clear.
        		  $('<option value="">::중분류(전체)::</option>').appendTo("select.ctg2");
        		  appendSelect = "select.ctg2";
			  }
    		  
    		  if ($(e.target).hasClass('ctg1') || $(e.target).hasClass('ctg2')) {
        		  $("select.ctg3").empty();// clear.
        		  $('<option value="">::소분류(전체)::</option>').appendTo("select.ctg3");
        		  
        		  if ($(e.target).hasClass('ctg2')) {
            		  appendSelect = "select.ctg3";
    			  }
			  }
    		  
    		  oTable.ajax.reload(null, true);
    		  
    		  if (this.value || this.value.length > 0) {
	    		  $.getJSON( "category/list?parentCtgId=" + this.value, function( data ) {
	        		  
	    			  $.each( data.content, function( index, ctg ) {
	    	    		    $( "<option value='" + ctg.id + "'>" + ctg.name + "</option>" ).appendTo(appendSelect);
	    	    	  });
	        		 
	        	  });
    		  }
    		  
    	  });
          
      }
      
      function loadProductList(tabId, pmId) {
    	  $(tabId +' div.optpl').load('productMaster/'+pmId+'/products' );
      }
      
      function viewProduct(productId) {
    	  $('#kmModal').modal('show');
      	  $('#kmModal .modal-body').load( "product/"+productId, function() {
      		 
          });
      }
      
    </script>
    <!-- /Datatables -->