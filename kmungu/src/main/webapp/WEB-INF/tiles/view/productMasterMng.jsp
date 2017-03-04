<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
          <div class="">
            <div class="page-title">
              <div class="title_left">
                <h3>상품/재고 관리 <small></small></h3>
              </div>

              <div class="title_right">
                
              </div>
            </div>

            <div class="clearfix"></div>

            <div class="row">
              <div class="col-md-12 col-sm-12 col-xs-12 product">
                <div class="x_panel">
                <!-- 
                  <div class="x_title">
                    <h2><i class="fa fa-table"></i> 상품 목록 <small></small></h2>
                    
                    <div class="clearfix"></div>
                  </div>
                   -->
                  <div class="x_content">
                    <div class="" role="tabpanel" data-example-id="togglable-tabs">
                      <ul id="productTabs" class="nav nav-tabs bar_tabs" role="tablist">
                        <li role="presentation" class="active"><a href="#tab_content1" id="home-tab" role="tab" data-toggle="tab" aria-expanded="true">상품 목록</a>
                        </li>
                      </ul>
                      <div id="productTabsContent" class="tab-content">
                        <div role="tabpanel" class="tab-pane fade active in" id="tab_content1" aria-labelledby="home-tab">
                          <table id="datatable" class="table table-striped table-hover" style="width:100%">
		                      <thead>
		                        <tr>
		                          <th>id</th>
		                          <th>이미지</th>
		                          <th>상품명</th>
		                          <th>판매원가</th>
		                          <th>할인가격</th>
		                          <th>판매가격</th>
		                          <th>등록일시</th>
		                          <th>상품설명</th>
		                          <!-- <th>재고관리</th> -->
		                        </tr>
		                      </thead>
		                    </table>
                        </div>
                      </div>
                    </div>
                  </div>
                </div>
              </div>
              
              <!-- 재고목록 -->
              <div class="col-md-12 col-sm-12 stock" >
                <div class="x_panel">
                  <div class="x_title">
                    <h2>재고 목록 <small></small></h2>
                    <ul class="nav navbar-right panel_toolbox">
                      <li><a class="collapse-link"><i class="fa fa-chevron-up"></i></a></li>
                    </ul>
                    <div class="clearfix"></div>
                  </div>
                  <div class="x_content">
                    <p class="text-muted font-13 m-b-30" style="color: red;">
                     <!-- 목록 조회를 위해서는 위 옵션 상품을 먼저 선택해주세요. --> 
                    </p>
                    <table id="datatable2" class="table table-striped table-hover">
                      <thead>
                        <tr>
                          <th>id</th>
                          <th>단가</th>
                          <th>입고수량</th>
                          <th>총금액</th>
                          <th>재고수량</th>
                          <th>입고일시</th>
                          <th>수정일시</th>
                        </tr>
                      </thead>
					  <tfoot>
			            <tr>
			                <th colspan="3" style="text-align:right">Total:</th>
			                <th></th>
			                <th></th>
			                <th></th>
			                <th></th>
			            </tr>
			          </tfoot>
                    </table>
                  </div>
                </div>
              </div>
			  <!-- /재고 목록 -->
            </div>
          </div>