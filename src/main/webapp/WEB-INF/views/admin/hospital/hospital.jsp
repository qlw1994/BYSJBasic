<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<content tag="cssconfig">
    <link rel="stylesheet" type="text/css" href="${ctx}/res-build/res/echarts/css/css.css">
    <link rel="stylesheet" href="${ctx}/res-build/res/echarts/jquery-ui-bootstrap/css/jquery-ui-1.10.3.custom.css"/>
    <link href="${ctx}/res-build/css/sys.css" rel="stylesheet" type="text/css"/>
    <link href="${ctx}/res-build/res/module/ajaxpage/css/page.css" rel="stylesheet" type="text/css"/>
</content>
<body>
<!-- BEGIN PAGE HEADER-->
<h3 class="page-title">医院配置</h3>
<div class="page-bar clearfix">
    <ul class="page-breadcrumb">
        <li><i class="iconfont ico-home">&#xe60a;</i> 主页 <i
                class="iconfont ico-angle-right">
            &#xe605;</i></li>
        <li><a href="#">医院配置</a> <i class="iconfont ico-angle-right">&#xe605;</i></li>
        <li><a href="#">医院管理</a></li>
    </ul>
</div>
<!-- END PAGE HEADER-->
<!-- BEGIN PAGE CONTENT-->
<div class="row">
    <div class="col-md-12">
        <div class="portlet">
            <div class="portlet-title">
                <div class="caption">
                    医院管理
                </div>
                <div class="actions">
                    <a class="btn btn-success btn-sm" href="#" data-toggle="modal" data-target="#addModal"> <i
                            class="iconfont">&#xe612;</i> <span class="hidden-480">添加医院</span>
                    </a>
                </div>
            </div>
            <div class="portlet-body" id="hospital-list">
                <div class="table-pages clearfix">
                    <div class="table-page clearfix"></div>
                    <div class="page-length">
                        <span class="seperator">|</span> 每页显示 <select name="j-length" aria-controls="datatable_ajax"
                                                                      class="j-length input-xsmall input-sm input-inline">
                        <option value="10">10</option>
                        <option value="25">25</option>
                        <option value="50">50</option>
                        <option value="75">75</option>
                        <option value="100">100</option>
                    </select> 条记录
                    </div>
                    <div class="page-info" role="status" aria-live="polite">
                        <span class="seperator">|</span> 总共获取 <span class="page-info-num"></span> 条记录
                    </div>
                </div>
                <div class="table-container">
                    <table class="table table-striped table-bordered table-hover dataTable no-footer"
                           id="datatable_ajax">

                        <thead>
                        <tr role="row">
                            <th class="sorting">医院名称</th>
                            <th class="sorting_desc">省份</th>
                            <th class="sorting">城市</th>
                            <th class="sorting">区/县</th>
                            <th class="sorting">授权开始时间</th>
                            <th class="sorting">授权结束时间</th>
                            <th class="sorting">操作</th>

                        </tr>
                        </thead>
                        <tbody>
                        </tbody>
                    </table>
                </div>
                <div class="table-pages clearfix">
                    <div class="table-page clearfix"></div>
                    <div class="page-length">
                        <span class="seperator">|</span> 每页显示 <select name="j-length" aria-controls="datatable_ajax"
                                                                      class="j-length input-xsmall input-sm input-inline">
                        <option value="10">10</option>
                        <option value="25">25</option>
                        <option value="50">50</option>
                        <option value="75">75</option>
                        <option value="100">100</option>
                    </select> 条记录
                    </div>
                    <div class="page-info" role="status" aria-live="polite">
                        <span class="seperator">|</span> 总共获取 <span class="page-info-num"></span> 条记录
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<div class="modal fade" id="addModal">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal">
                    <span aria-hidden="true">&times;</span> <span class="sr-only">Close</span>
                </button>
                <h4 class="modal-title">添加医院</h4>
            </div>
            <form class="form-horizontal" id="vHospitalForm">
                <!-- <input type="hidden" id="orgname" name="orgname">
                <input type="hidden" id="orgid" name ="orgid"> -->
                <div class="modal-body">
                    <div class="portlet-body form">
                        <div class="form-body">
                            <div class="form-group">
                                <label class="col-md-3 control-label">省份</label>
                                <div class="col-md-8">
                                    <select  class="form-control" name="province"></select>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-md-3 control-label">城市</label>
                                <div class="col-md-8">
                                    <select  class="form-control" name="city"></select>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-md-3 control-label">区县</label>
                                <div class="col-md-8">
                                    <select class="form-control" name="area"></select>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-md-3 control-label">医院名称</label>
                                <div class="col-md-8">
                                    <input type="text" AUTOCOMPLETE="off" class="form-control" name="name"
                                           placeholder="医院名称">
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-md-3 control-label">医院等级</label>
                                <div class="col-md-8">
                                    <select id="mod_level" class="form-control" name="level">
                                        <option value="" selected="selected">请选择</option>
                                        <option value="1">一级甲</option>
                                        <option value="2">一级乙</option>
                                        <option value="3">一级丙</option>
                                        <option value="4">二级甲</option>
                                        <option value="5">二级乙</option>
                                        <option value="6">二级丙</option>
                                        <option value="7">三级甲</option>
                                        <option value="8">三级乙</option>
                                        <option value="9">三级丙</option>
                                    </select>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-md-3 control-label">医院简介</label>
                                <div class="col-md-8">
                                    <textarea AUTOCOMPLETE="off" class="form-control" name="resume"
                                              placeholder="医院简介"></textarea>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-md-3 control-label">医院地址</label>
                                <div class="col-md-8">
                                    <textarea  AUTOCOMPLETE="off" class="form-control" name="address"
                                               placeholder="医院地址"></textarea>
                                </div>
                            </div>
                        </div>
                        <!-- END FORM-->
                    </div>
                </div>
                <div class="modal-footer">
                    <button type="submit" class="btn btn-success">
                        <i class="iconfont">&#xe61c;</i> 保存
                    </button>
                    <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
                </div>
            </form>
        </div>
        <!-- /.modal-content -->
    </div>
    <!-- /.modal-dialog -->
</div>
<!-- /.modal -->
<div class="modal fade" id="modifyModal">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal">
                    <span aria-hidden="true">&times;</span> <span class="sr-only">Close</span>
                </button>
                <h4 class="modal-title">修改医院</h4>
            </div>
            <form class="form-horizontal" id="vHospitalModifyForm">
                <div class="modal-body">
                    <div class="portlet-body form j-disabled-item">
                        <div class="form-body">
                            <input type="hidden" name="id">
                            <div class="form-group">
                                <label class="col-md-3 control-label">省份</label>
                                <div class="col-md-8">
                                    <select  class="form-control" name="modprovince"  disabled="disabled"></select>

                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-md-3 control-label">城市</label>
                                <div class="col-md-8">
                                    <select  class="form-control" name="modcity"  disabled="disabled"></select>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-md-3 control-label">区县</label>
                                <div class="col-md-8">
                                    <select class="form-control" name="modarea"  disabled="disabled"></select>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-md-3 control-label">医院名称</label>
                                <div class="col-md-8">
                                    <input type="text" AUTOCOMPLETE="off" class="form-control" name="name"
                                           placeholder="医院名称" disabled="disabled">
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-md-3 control-label">医院等级</label>
                                <div class="col-md-8">
                                    <select id="add_level" name="level" class="form-control" disabled="disabled">
                                        <option value="">请选择</option>
                                        <option value="1">一级甲</option>
                                        <option value="2">一级乙</option>
                                        <option value="3">一级丙</option>
                                        <option value="4">二级甲</option>
                                        <option value="5">二级乙</option>
                                        <option value="6">二级丙</option>
                                        <option value="7">三级甲</option>
                                        <option value="8">三级乙</option>
                                        <option value="9">三级丙</option>
                                    </select>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-md-3 control-label">医院简介</label>
                                <div class="col-md-8">
                                    <textarea AUTOCOMPLETE="off" class="form-control" name="resume" disabled="disabled"
                                              placeholder="医院简介"></textarea>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-md-3 control-label">医院地址</label>
                                <div class="col-md-8">
                                    <textarea AUTOCOMPLETE="off" class="form-control" name="address" disabled="disabled"
                                              placeholder="医院地址"></textarea>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-md-3 control-label">授权开始时间</label>
                                <div class="col-md-8">
                                    <input type="text" AUTOCOMPLETE="off" class="form-control" id="mod_startdate"
                                           name="startdate"
                                           readonly   disabled="disabled" placeholder="授权开始时间">
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-md-3 control-label">授权结束时间</label>
                                <div class="col-md-8">
                                    <input type="text" AUTOCOMPLETE="off" class="form-control" name="enddate"
                                           readonly   id="mod_enddate"
                                           disabled="disabled" placeholder="授权结束时间">
                                </div>
                            </div>
                        </div>
                        <!-- END FORM-->
                    </div>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-success j-form-edit">
                        <i class="iconfont">&#xe61c;</i> 我要编辑
                    </button>
                    <button type="submit" class="btn btn-success j-form-save" style="display: none">
                        <i class="iconfont">&#xe62c;</i> 保存
                    </button>
                    <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
                    <%-- <button type="submit" class="btn btn-success">修改</button>
                 <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>--%>
                </div>
        </div>
        </form>
    </div>
    <!-- /.modal-content -->
</div>
<!-- /.modal-dialog -->
</div>
<div class="modal fade bs-example-modal-sm" id="modal-box" tabindex="-1" role="dialog"
     aria-labelledby="mySmallModalLabel" aria-hidden="true">
    <div class="modal-dialog modal-sm">
        <div class="modal-content">
            <div class="modal-body">
                <div class="dialogtip-con-wrap clearfix dialogtipg-success">
                    <div class="dialogtip-icon iconfont">&#xe614;</div>
                    <div class="dialogtip-con">
                        <h4 class="dialogtip-tit">操作成功</h4>

                        <div class="dialogtip-msg">数据添加成功</div>
                    </div>
                </div>
            </div>
            <div class="modal-footer">
                <a href="${ctx}/admin/hospitals/index?pcode=2&subcode=1"
                   class="btn btn-success">返回列表</a>

            </div>
        </div>
    </div>
</div>
<div class="modal fade bs-example-modal-sm" id="ajax_fail" tabindex="-1" role="dialog"
     aria-labelledby="mySmallModalLabel" aria-hidden="true" style="z-index: 9998">
    <div class="modal-dialog modal-sm">
        <div class="modal-content">
            <div class="modal-body">
                <div class="dialogtip-con-wrap clearfix dialogtipg-warning">
                    <div class="dialogtip-icon iconfont">&#xe615;</div>
                    <div class="dialogtip-con">
                        <h4 class="dialogtip-tit" style="margin-top: 12px;">保存失败</h4>
                    </div>
                </div>
            </div>
            <div class="modal-footer">
                <a href="#" class="btn btn-danger j-modal-closebtn" data-dismiss="modal">关闭</a>
            </div>
        </div>
    </div>
</div>
<!-- END PAGE CONTENT-->
</body>
<content tag="jsconfig">
    <script type="text/javascript">
        var currentpage = ${currentpage};
    </script>
    <script type="text/javascript"
            src="${ctx}/res-build/res/province-city-area-selector/PCASClass.js"></script>
    <script type="text/javascript"
            src="${ctx}/res-build/res/echarts/jquery-ui-bootstrap/jquery-ui-1.10.3.custom.min.js"></script>
    <script type="text/javascript" src="${ctx}/res-build/config.js"
            data-init="admin/hospital/hospital.js"></script>
</content>
