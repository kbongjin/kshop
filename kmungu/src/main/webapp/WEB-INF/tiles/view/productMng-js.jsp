<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
	<!-- Datatables -->
    <script src="${res}/vendors/datatables.net/js/jquery.dataTables.min.js"></script>
    <script src="${res}/vendors/datatables.net-bs/js/dataTables.bootstrap.min.js"></script>
    <script src="${res}/vendors/datatables.net-select/js/dataTables.select.min.js"></script>
    <script src="${res}/vendors/datatables.net-buttons/js/dataTables.buttons.min.js"></script>
    <script src="${res}/vendors/datatables.net-buttons-bs/js/buttons.bootstrap.min.js"></script>
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
    <script src="${res}/js/jquery.spring-friendly.min.js"></script>

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
        
        /*
        $('#datatable tbody').on( 'click', 'tr', function () {
            if ( $(this).hasClass('selected') ) {
                $(this).removeClass('selected');
            }
            else {
            	oTable.$('tr.selected').removeClass('selected');
                $(this).addClass('selected');
            }
        } );
        
        
        $('#datatable tbody').on( 'click', 'tr', function () {
            if ( $(this).hasClass('success') ) {
                $(this).removeClass('success');
            }
            else {
            	oTable.$('tr.success').removeClass('success');
                $(this).addClass('success');
            }
        } );
        */
        
        
        oTable.on( 'select', function ( e, dt, type, indexes ) {
            var rowData = oTable.rows( indexes ).data().toArray();
            //events.prepend( '<div><b>'+type+' selection</b> - '+JSON.stringify( rowData )+'</div>' );
            //alert(JSON.stringify( rowData ));
            $('#kmModal').modal('show');
            $('#kmModal .modal-body').load( "productForm", function() {
            	  //alert( "Load was performed." );
            });
        } );
        
        	
        
      });
    </script>
    <!-- /Datatables -->