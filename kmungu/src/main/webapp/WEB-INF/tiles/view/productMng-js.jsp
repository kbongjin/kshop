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
	<script src="${res}/vendors/bootstrap-fileinput/js/fileinput.min.js"></script>
	<!-- Parsley -->
    <script src="${res}/vendors/parsleyjs/dist/parsley.min.js"></script>
    <script src="${res}/vendors/parsleyjs/dist/i18n/ko.js"></script>
	<!-- PNotify -->
    <script src="${res}/vendors/pnotify/dist/pnotify.js"></script>
    <script src="${res}/vendors/pnotify/dist/pnotify.buttons.js"></script>
    <script src="${res}/vendors/pnotify/dist/pnotify.nonblock.js"></script>
	
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
            dom: 'Bfrtip',
            buttons: [
                {
                    text: '신규 등록',
                    action: function ( e, dt, node, config ) {
                    	$('#kmModal').modal('show');
                    }
                }
            ],
            columns : [{
                data : 'id',
                visible : true,
                searchable : false
            }, {
                data : 'name',
                "render": function ( data, type, row ) {
                    return "<a href='#'>" +data +"</a>";
                },
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
                data : 'img1Path',
                orderable : false,
                searchable : false
            }, {
                data : 'createDt',
                orderable : false,
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
            var rowData = oTable.rows( indexes ).data().toArray();
            //events.prepend( '<div><b>'+type+' selection</b> - '+JSON.stringify( rowData )+'</div>' );
            //alert(JSON.stringify( rowData ));
            $('#kmModal').modal('show');
            $('#kmModal .modal-body').load( "productForm", function() {
            	  //alert( "Load was performed." );
            	console.log('loaded productForm.jsp');
            	initProductForm();
            });
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
                success: function(response,status){
                    //성공후 서버에서 받은 데이터 처리
                    alert("업로드 성공!!");
                },
                error: function(){
                    //에러발생을 위한 code페이지
                }                               
            });
   	   	});
        
        new PNotify({
            title: "PNotify",
            type: "info",
            text: "Welcome. Try hovering over me. You can click things behind me, because I'm non-blocking.",
            nonblock: {
                nonblock: true
            },
            addclass: 'dark',
            styling: 'bootstrap3',
            hide: false,
            before_close: function(PNotify) {
              PNotify.update({
                title: PNotify.options.title + " - Enjoy your Stay",
                before_close: null
              });

              PNotify.queueRemove();

              return false;
            }
          });
        	
        
      });
      
      function initProductForm() {
    	  $("#pimgs").fileinput({showCaption: false});
      }
    </script>
    <!-- /Datatables -->