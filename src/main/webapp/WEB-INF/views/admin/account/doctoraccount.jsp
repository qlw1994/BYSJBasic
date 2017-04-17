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
<h3 class="page-title">账户管理</h3>
<div class="page-bar clearfix">
    <ul class="page-breadcrumb">
        <li><i class="iconfont ico-home">&#xe60a;</i> 主页 <i
                class="iconfont ico-angle-right">
            &#xe605;</i></li>
        <li><a href="#">账户管理</a> <i class="iconfont ico-angle-right">&#xe605;</i></li>
        <li><a href="#">医生</a></li>
    </ul>
</div>
<!-- END PAGE HEADER-->
<!-- BEGIN PAGE CONTENT-->
<div class="row">
    <div class="col-md-12">
        <div class="portlet">
            <div class="portlet-title">
                <div class="caption">
                    <i class="iconfont">&#xe619;</i> 医生
                </div>
                <%--<div class="actions">--%>
                    <%--<a class="btn btn-success btn-sm" href="#" data-toggle="modal" data-target="#addModal"> <i--%>
                            <%--class="iconfont">&#xe612;</i> <span class="hidden-480">添加医生帐户</span>--%>
                    <%--</a>--%>
                <%--</div>--%>
            </div>
            <div class="portlet-body" id="account-list">
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
                            <th class="sorting">医生姓名</th>
                            <th class="sorting">医生账号</th>
                            <th class="sorting">医生性别</th>
                            <th class="sorting">医院</th>
                            <th class="sorting">医生科室</th>
                            <th class="sorting_desc">创建日期</th>
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
<!-- /.modal -->
<div class="modal fade" id="modifyModal">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal">
                    <span aria-hidden="true">&times;</span> <span class="sr-only">Close</span>
                </button>
                <h4 class="modal-title">修改医生</h4>
            </div>
            <form class="form-horizontal" id="vDoctorModifyForm">

                <div class="modal-body">
                    <div class="portlet-body form j-disabled-item">
                        <div class="form-body">
                            <input type="hidden" name="id">
                            <div class="form-group">
                                <label class="col-md-3 control-label">账号</label>
                                <div class="col-md-8">
                                    <input type="text" AUTOCOMPLETE="off" class="form-control" name="account"
                                           disabled="disabled"
                                           placeholder="账号名">
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-md-3 control-label">医院</label>
                                <div class="col-md-8">
                                    <input type="hidden" name="hospitalid">
                                    <input type="text"  AUTOCOMPLETE="off" name="hospitalname" disabled="disabled">
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-md-3 control-label">科室</label>
                                <div class="col-md-8">
                                    <input type="hidden" name="departmentid">
                                    <input type="text"  AUTOCOMPLETE="off" name="departmentname" disabled="disabled">
                                    <ul class="list" id="mod_departmentList">
                                    </ul>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-md-3 control-label">姓名</label>
                                <div class="col-md-8">
                                    <input type="text" AUTOCOMPLETE="off" class="form-control" name="name"
                                           disabled="disabled"
                                           placeholder="姓名">
                                </div>
                            </div>

                            <div class="form-group">
                                <label class="col-md-3 control-label">医生性别</label>
                                <div class="col-md-8">
                                    <label class="radio-inline">   <input type="radio"  value="1" disabled="disabled" name="sex">男</label>

                                    <label class="radio-inline">    <input type="radio"  value="2" disabled="disabled" name="sex">女</label>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-md-3 control-label">医生年龄</label>
                                <div class="col-md-8">
                                    <input type="text" class="form-control"
                                           AUTOCOMPLETE="off" placeholder="医生年龄" name="age" disabled="disabled">
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-md-3 control-label">医生职务</label>
                                <div class="col-md-8">
                                    <input type="text" class="form-control"
                                           AUTOCOMPLETE="off" placeholder="医生职务" name="job" disabled="disabled">
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-md-3 control-label">医生级别</label>
                                <div class="col-md-8">
                                <select  name="level" disabled="disabled" class="form-control">
                                    <option value="">请选择</option>
                                    <option value="1">专家</option>
                                    <option value="2">普通</option>

                                </select>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-md-3 control-label">证件类型</label>
                                <div class="col-md-8">
                                    <select id="mod_idtype" class="form-control"  name="idtype" disabled="disabled">
                                        <option value="">请选择</option>
                                        <option value="1">二代身份证</option>
                                        <option value="2">港澳居民身份证</option>
                                        <option value="3">台湾居民身份证</option>
                                        <option value="4">护照</option>
                                    </select>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-md-3 control-label">证件号码</label>
                                <div class="col-md-8">
                                    <input type="text" AUTOCOMPLETE="off" class="form-control" placeholder="证件号码"
                                           disabled="disabled"
                                           name="idnumber">
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-md-3 control-label">医生简介</label>
                                <div class="col-md-8">
                                    <textarea class="form-control"
                                           placeholder="医生简介" disabled="disabled"
                                              name="resume"></textarea>
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
                <a href="${ctx}/admin/doctors/index?pcode=1&subcode=3"
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
        var patientid="${patientid}";
        var patientname = '${patientname}';
    </script>
    <script type="text/javascript" src="${ctx}/res-build/config.js" data-init="admin/account/doctoraccount.js"></script>
</content>
