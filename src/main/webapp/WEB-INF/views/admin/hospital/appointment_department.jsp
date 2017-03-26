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
<h3 class="page-title">系统配置</h3>
<div class="page-bar clearfix">
    <ul class="page-breadcrumb">
        <li><i class="iconfont ico-home">&#xe60a;</i> 主页 <i
                class="iconfont ico-angle-right">
            &#xe605;</i></li>
        <li><a href="#">系统配置</a> <i class="iconfont ico-angle-right">&#xe605;</i></li>
        <li><a href="#">医院管理</a><i class="iconfont ico-angle-right">&#xe605;</i></li>
        <li><a href="#">科室管理</a><i class="iconfont ico-angle-right">&#xe605;</i></li>
        <li><a href="#">科室预约管理</a></li>
    </ul>
</div>
<!-- END PAGE HEADER-->
<!-- BEGIN PAGE CONTENT-->
<div class="row">
    <div class="col-md-12">
        <div class="portlet">
            <div class="portlet-title">
                <div class="caption">
                    <i class="iconfont">&#xe619;</i>${hospitalname}-${departmentname}- 预约管理
                </div>
                <div class="actions">
                </div>
                <div class="portlet-body" id="appointment-list">
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
                                <th class="sorting">就诊人姓名</th>
                                <th class="sorting">就诊序号</th>
                                <th class="sorting">挂号费</th>
                                <th class="sorting">预约状态</th>
                                <th class="sorting">预约日期</th>
                                <th class="sorting">预约时间</th>
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
                    <a href="${ctx}/admin/appointments/departmentindex?pcode=2&subcode=1&hospitalid=${hospitalid}&hospitalname=${hospitalname}"
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
        var hospitalid = ${hospitalid};
    </script>

    <script type="text/javascript" src="${ctx}/res-build/config.js" data-init="admin/hospital/appointment_doctor.js"></script>
</content>
