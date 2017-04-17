<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="ctx" value="${pageContext.request.contextPath}"/>


<!doctype html>
<html>

<head>
    <meta charset="utf-8">
    <meta content="width=device-width, initial-scale=1" name="viewport"/>

    <title>用户平台</title>
    <link href="${ctx}/res-build/css/reset.css" rel="stylesheet" type="text/css"/>
    <link href="${ctx}/res-build/res/bootstrap-3.3.2/css/bootstrap.min.css" rel="stylesheet" type="text/css"/>
    <!--[if lt IE 9]>
    <link href="${ctx}/res-build/res/bootstrap-3.3.2/css/ie8.min.css" rel="stylesheet">
    <![endif]-->
    <link href="${ctx}/res-build/css/login.css" rel="stylesheet" type="text/css"/>
    <script type="text/javascript" src="${ctx}/res-build/res/jquery/jquery-1.11.2.min.js"></script>
    <script type="text/javascript" src="${ctx}/res-build/res/bootstrap-3.3.2/js/bootstrap.min.js"></script>
    <script type="text/javascript" src="${ctx}/res-build/res/seajs-3.0.0/dist/sea.js"></script>
    <script type="text/javascript"
            src="${ctx}/res-build/res/plugin/jquery-validation-1.13.1/dist/jquery.validate.min.js"></script>

</head>

<body class="login">

<!-- BEGIN LOGO -->
<div class="logo">
    智慧医疗平台
</div>
<!-- END LOGO -->
<!-- BEGIN SIDEBAR TOGGLER BUTTON -->
<div class="menu-toggler sidebar-toggler">
</div>
<!-- END SIDEBAR TOGGLER BUTTON -->
<!-- BEGIN LOGIN -->
<h3 class="page-title">""</h3>
<div class="content" style="background-color: white">
    <!-- BEGIN LOGIN FORM -->
    <form class="login-form" id="vUserForm" method="post" novalidate="novalidate">
        <h3 class="form-title">用户注册</h3>


        <div class="modal-body">
            <div class="portlet-body form">
                <div class="form-body">
                    <div class="form-group">
                        <label class="col-md-3 control-label">账号</label>
                        <div class="col-md-8">
                            <input type="text" AUTOCOMPLETE="off" class="form-control" name="account"
                                   placeholder="账号">
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-md-3 control-label">姓名</label>
                        <div class="col-md-8">
                            <input type="text" AUTOCOMPLETE="off" class="form-control" name="name"
                                   placeholder="姓名">
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-md-3 control-label">密码</label>

                        <div class="col-md-8">
                            <input type="password" AUTOCOMPLETE="off" class="form-control" name="password"
                                   id="add_password"
                                   placeholder="密码">
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-md-3 control-label">重复密码</label>
                        <div class="col-md-8">
                            <input type="password" AUTOCOMPLETE="off" class="form-control" placeholder="重复密码"
                                   name="password_again">
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-md-3 control-label">证件类型</label>
                        <div class="col-md-8">
                            <select name="idtype" class="form-control">
                                <option value="" selected="selected">请选择</option>
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
                                   name="idnumber">
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-md-3 control-label">手机号码</label>
                        <div class="col-md-8">
                            <input type="text" AUTOCOMPLETE="off" class="form-control" placeholder="手机号码"
                                   name="phone">
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-md-3 control-label">用户性别</label>
                        <div class="col-md-8">
                            <label class="radio-inline"> <input type="radio" value="1"
                                                                name="sex">男</label>
                            <label class="radio-inline"> <input type="radio" value="2"
                                                                name="sex">女</label>
                        </div>
                    </div>
                </div>
                <!-- END FORM-->
            </div>
        </div>
        <div class="form-actions"style="padding-right: 15%">
            <button type="submit" class="btn btn-success pull-right" >
                注册<i class="m-icon-swapright m-icon-white"></i>
            </button>
        </div>

    </form>
    <!-- END LOGIN FORM -->


</div>
<!-- END LOGIN -->
<!-- BEGIN COPYRIGHT -->
<script type="text/javascript">
    var ROOTPAth = "${ctx}";
</script>
<script type="text/javascript" src="${ctx}/res-build/config.js" data-init="users/signup"></script>
</body>


</html>
