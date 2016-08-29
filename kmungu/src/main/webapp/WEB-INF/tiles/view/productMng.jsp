<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
          <div class="">
            <div class="page-title">
              <div class="title_left">
                <h3>상품/재고 관리 <small></small></h3>
              </div>

              <div class="title_right">
                <div class="col-md-5 col-sm-5 col-xs-12 form-group pull-right top_search">
                  <div class="input-group">
                    <input type="text" class="form-control" placeholder="Search for...">
                    <span class="input-group-btn">
                      <button class="btn btn-default" type="button">Go!</button>
                    </span>
                  </div>
                </div>
              </div>
            </div>

            <div class="clearfix"></div>

            <div class="row">
              <div class="col-md-12 col-sm-12 col-xs-12">
                <div class="x_panel">
                  <div class="x_title">
                    <h2>상품 목록 <small></small></h2>
                    <ul class="nav navbar-right panel_toolbox">
                      <li><a class="collapse-link"><i class="fa fa-chevron-up"></i></a>
                      </li>
                      <li class="dropdown">
                        <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-expanded="false"><i class="fa fa-wrench"></i></a>
                        <ul class="dropdown-menu" role="menu">
                          <li><a href="#">Settings 1</a>
                          </li>
                          <li><a href="#">Settings 2</a>
                          </li>
                        </ul>
                      </li>
                      <li><a class="close-link"><i class="fa fa-close"></i></a>
                      </li>
                    </ul>
                    <div class="clearfix"></div>
                  </div>
                  <div class="x_content">
                    <p class="text-muted font-13 m-b-30">
                      리스트 설명...
                    </p>
                    <table id="datatable" class="table table-striped table-hover">
                      <thead>
                        <tr>
                          <th>id</th>
                          <th>name</th>
                          <th>판매원가</th>
                          <th>할인가격</th>
                          <th>판매가격</th>
                          <th>img1_path</th>
                          <th>등록일시</th>
                        </tr>
                      </thead>

                    </table>
                  </div>
                </div>
              </div>

            </div>
          </div>