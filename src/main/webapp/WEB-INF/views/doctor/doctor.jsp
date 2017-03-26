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
<div>
    <div>
        <input type="hidden" id="webBasePath" value="${ctx}">
    </div>
    <div class="portlet portlet-blue portlet-module">
        <div class="portlet-title clearfix">
            <div class="caption">
                <i id="title_info_zone" class="fa fa-comments">个人信息修改</i>
            </div>
            <div class="actions">
                <button class="btn btn-success btn-sm" id="resetPassword" > <i
                        class="iconfont">&#xe612;</i> <span class="hidden-480" data-id="${sessionScope.doctor.id}">重置密码</span>
                </button>
            </div>
        </div>
        <div class="portlet-body form">
            <form class="form-horizontal" id="vDoctorForm">

                <div class="modal-body">
                    <div class="portlet-body form j-disabled-item">
                        <div class="form-body">
                            <input type="hidden" name="id" value="${sessionScope.doctor.id}">
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
                                    <label class="radio-inline">  <input type="radio"  value="1" disabled="disabled"
                                                                         name="type">专家</label>
                                    <label class="radio-inline">  <input type="radio"  value="2" disabled="disabled"
                                                                         name="type">普通</label>
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
                                           name="password_again">
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
                <a id="closeDivlink" class="btn btn-danger j-modal-closebtn">关闭</a>
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
<!-- END PAGE CONTENT-->
</body>
<content tag="jsconfig">
    <script type="text/javascript">
        var doctorid = ${sessionScope.doctor.id};
    </script>
    <script type="text/javascript" src="${ctx}/res-build/config.js" data-init="doctor/doctor.js"></script>
</content>
