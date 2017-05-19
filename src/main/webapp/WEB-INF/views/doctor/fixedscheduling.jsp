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
<h3 class="page-title">功能</h3>
<div class="page-bar clearfix">
    <ul class="page-breadcrumb">
        <li><i class="iconfont ico-home">&#xe60a;</i> 主页 <i
                class="iconfont ico-angle-right">
            &#xe605;</i></li>
        <li><a href="#">功能</a> <i class="iconfont ico-angle-right">&#xe605;</i></li>
        <li><a href="#">日程表管理</a></li>
    </ul>
</div>
<!-- END PAGE HEADER-->
<!-- BEGIN PAGE CONTENT-->
<div class="row">
    <div class="col-md-12">
        <div class="portlet">
            <div class="portlet-title">
                <div class="caption">
                    <i class="iconfont">
                        &#xe619;</i>${sessionScope.doctor.hospitalname}-${sessionScope.doctor.departmentname}- ${sessionScope.doctor.name}-
                    日程表管理
                </div>
                <div class="actions">
                    <form id="scheduling_upload" enctype="multipart/form-data">
                        <div class="form-group">
                            <input id="fixedschedulingfile" type="file" name="file">

                        </div>
                        <button type="submit" class="btn btn-success btn-sm" id="file_upload">上传
                        </button>
                    </form>
                    <%--<button class="btn btn-success btn-sm" id="scheduling_edit"><span class="hidden-480">开始编辑</span>--%>
                    <%--</button>--%>
                </div>
            </div>
            <div class="portlet-body" id="scheduling-list">
                <div class="table-container">
                    <table class="table table-striped table-bordered table-hover dataTable no-footer"
                           id="datatable_ajax">

                        <thead>
                        <tr role="row">
                            <th class="sorting">时间段</th>

                            <th class="sorting">星期一</th>
                            <th class="sorting">星期二</th>
                            <th class="sorting">星期三</th>
                            <th class="sorting">星期四</th>
                            <th class="sorting">星期五</th>
                            <th class="sorting">星期六</th>
                            <th class="sorting">星期日</th>

                        </tr>
                        </thead>
                        <tbody>
                        </tbody>
                    </table>
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
                <a href="${ctx}/doctor/fixedschedulings/index?pcode=1&subcode=2&hospitalid=${sessionScope.doctor.hospitalid}&hospitalname=${sessionScope.doctor.hospitalname}&doctorid=${sessionScopedoctor.id}&doctorname=${sessionScope.doctor.name}"
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
        var doctorid = ${sessionScope.doctor.id};
        var hospitalid="${sessionScope.doctor.hospitalid}"
    </script>

    <script type="text/javascript" src="${ctx}/res-build/config.js"
            data-init="doctor/fixedscheduling"></script>
</content>
