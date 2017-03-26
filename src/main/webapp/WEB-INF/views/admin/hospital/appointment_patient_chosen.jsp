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
        <li><a href="#">系统配置</a> <i class="iconfont ico-angle-right">&#xe605;</i></li>
        <li><a href="#">医院管理</a><i class="iconfont ico-angle-right">&#xe605;</i></li>
        <li><a href="#">科室管理</a><i class="iconfont ico-angle-right">&#xe605;</i></li>
        <li><a href="#">医生管理</a><i class="iconfont ico-angle-right">&#xe605;</i></li>
        <li><a href="#">排班查询</a></li>
        <li><a href="#">提交预约就诊人选择</a></li>
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
                <i id="title_info_zone" class="fa fa-comments">用户和就诊人选择</i>
            </div>
        </div>
        <div class="portlet-body form">
            <form class="form-horizontal" role="form" id="ModelForm">
                <div class="form-body">
                    <input type="hidden" id="userid" name="uid">
                    <input type="hidden" id="patientid" name="patientid">
                    <input type="hidden" name="hospitalid" value="${hospitalid}">
                    <input type="hidden" name="departmentid" value="${departmentid}">
                    <input type="hidden" name="doctorid" value="${doctorid}">
                    <div class="form-group">
                        <label class="col-md-3 control-label">用户账号</label>
                        <div class="col-md-7">
                            <input type="text" name="useraccount" id="useraccount" AUTOCOMPLETE="off">
                            <ul class="list" id="userList">

                            </ul>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-md-3 control-label">就诊人姓名</label>
                        <div class="col-md-7">
                            <select id="patientname" name="patientname" class="form-control" style="width:120px;"
                                    disabled="disabled">
                                <option data-patientid="" value="">请选择</option>
                            </select>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-md-3 control-label">性别</label>
                        <div class="col-md-8">
                            <label class="radio-inline"> <input type="radio" disabled="disabled" name="sex" value="1"/>男</label>
                            <label class="radio-inline"> <input type="radio" disabled="disabled" name="sex" value="2">女</label>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-md-3 control-label">出生日期</label>
                        <div class="col-md-8">
                            <input type="text" class="form-control" placeholder="出生日期" disabled="disabled"
                                   name="birthday" style="width:120px;"/>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-md-3 control-label">证件类型</label>
                        <div class="col-md-8">
                            <select class="form-control" id="idtype"
                                    name="idtype" style="width:150px;" disabled="disabled">
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
                            <input type="text" class="form-control" placeholder="证件号码" style="width:180px;"
                                   id="idnumber" disabled="disabled" name="idnumber"/>
                        </div>
                    </div>
                    <div class="form-actions clearfix fluid">
                        <div class="col-md-offset-3 col-md-7">
                            <button type="submit" class="btn btn-success">提交预约</button>
                        </div>
                    </div>
                </div>
            </form>
        </div>
    </div>
</div>


<!--  提示框 -->
<div class="modal fade bs-example-modal-sm" id="modal-box" tabindex="-1" role="dialog"
     aria-labelledby="mySmallModalLabel" aria-hidden="true">
    <div class="modal-dialog modal-sm">
        <div class="modal-content">
            <div class="modal-body">
                <div class="dialogtip-con-wrap clearfix dialogtipg-success">
                    <div class="dialogtip-icon iconfont">&#xe614;</div>
                    <div class="dialogtip-con">
                        <h4 class="dialogtip-tit"></h4>

                        <div class="dialogtip-msg"></div>
                    </div>
                </div>
            </div>
            <div class="modal-footer">
                <a id="closeDivlink" href=" ${ctx}/admin/appointments/doctorindex?pcode=2&subcode=1&doctorid=${doctorid} &doctorname=${doctorname}" class="btn btn-danger j-modal-closebtn">关闭</a>
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
        var hospitalid = ${hospitalid};
        var departmentid =${departmentid};
        var doctorid =${doctorid};
        var doctorname =${doctorname};
    </script>
    <script type="text/javascript" src="${ctx}/res-build/config.js"
            data-init="admin/hospital/appointment_patient_chosen.js"></script>
</content>