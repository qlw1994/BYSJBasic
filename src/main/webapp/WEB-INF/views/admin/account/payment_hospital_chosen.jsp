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
        <li><i class="iconfont ico-home">&#xe60a;</i> 主页 <i class="iconfont ico-angle-right">
            &#xe605;</i></li>
        <li><a href="#">账户管理</a> <i class="iconfont ico-angle-right">&#xe605;</i></li>
        <li><a href="#">用户</a><i class="iconfont ico-angle-right">&#xe605;</i></li>
        <li><a href="#">就诊人管理</a><i class="iconfont ico-angle-right">&#xe605;</i></li>
        <li><a href="#">支付列表-医院选择</a><i class="iconfont ico-angle-right">&#xe605;</i></li>

    </ul>
</div>
<!-- END PAGE HEADER-->
<div>
    <div>
        <input type="hidden" id="webBasePath" value="${ctx}">
    </div>
    <div class="portlet portlet-blue portlet-module">
        <div class="portlet-title clearfix">
            <div class="caption">
                <i id="title_info_zone" class="fa fa-comments">医院选择</i>
            </div>
        </div>
        <div class="portlet-body form">
            <form class="form-horizontal" role="form" id="ModelForm">
                <div class="form-body">
                    <input type="hidden" name="uid" value="${uid}">
                    <input type="hidden" name="patientid" value="${patientid}">
                    <input type="hidden" id="hospitalid" name="hospitalid">
                    <div class="form-group">
                        <label class="col-md-3 control-label">医院名称</label>
                        <div class="col-md-7">
                            <input class="col-md-7" type="text" id="hospitalname" name="name" AUTOCOMPLETE="off">
                            <ul class="list" id="hospitalList" style="width: 60%">

                            </ul>
                        </div>
                    </div>
                    <div class="form-actions clearfix fluid">
                        <div class="col-md-offset-3 col-md-7">
                            <button type="submit" class="btn btn-success">下一步</button>
                        </div>
                    </div>
                </div>
            </form>
        </div>
    </div>
</div>


<!--  提示框 -->
<div class="modal fade bs-example-modal-sm" id="ajax_fail" tabindex="-1" role="dialog"
     aria-labelledby="mySmallModalLabel" aria-hidden="true" style="z-index: 9998">
    <div class="modal-dialog modal-sm">
        <div class="modal-content">
            <div class="modal-body">
                <div class="dialogtip-con-wrap clearfix dialogtipg-warning">
                    <div class="dialogtip-icon iconfont">&#xe615;</div>
                    <div class="dialogtip-con">
                        <h4 class="dialogtip-tit" style="margin-top: 12px;">加载失败</h4>
                    </div>
                </div>
            </div>
            <div class="modal-footer">
                <a href="#" class="btn btn-danger j-modal-closebtn" data-dismiss="modal">关闭</a>
            </div>
        </div>
    </div>
</div>
</body>

<content tag="jsconfig">
    <script type="text/javascript">
        var uid =${uid};
        var patientid =${patientid};
        var patientname = "${patientname}";
    </script>
    <script type="text/javascript" src="${ctx}/res-build/config.js"
            data-init="admin/account/payment_hospital_chosen"></script>
</content>