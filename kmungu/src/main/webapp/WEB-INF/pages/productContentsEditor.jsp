<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
		<script src="${pageContext.request.contextPath}/resources/js/ckeditor/ckeditor.js"></script>
        <form action="product/contents" method="POST" class="pfrm">
        	<input type="hidden" name="id" value="${product.id}"/>
            <textarea name="contents" id="contents" rows="10" cols="80">
                ${product.contents}
            </textarea>
        </form>
        
        <script>
            // Replace the <textarea id="contents"> with a CKEditor
            // instance, using default configuration.
            CKEDITOR.replace( 'contents', {
    			height: 400
    		});
            
            $(document).ready(function() {
            	$('#kmModal').find('.modal-title').text('상품 상세설명 관리');
            	
            	$.extend( FormOptions, {
           	  		reloadTable : function(callback, resetPaging){
           	  			// ignore.
           	  		},
           	  		beforeSubmit : function() {
           	  			//console.log(CKEDITOR.instances.editor1.getData());
           	  			CKEDITOR.instances.contents.updateElement();
           	  		}
           	  	});
            	
            	
            });
        </script>