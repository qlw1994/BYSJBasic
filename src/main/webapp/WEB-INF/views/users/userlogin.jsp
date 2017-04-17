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
    用户平台
</div>
<!-- END LOGO -->
<!-- BEGIN SIDEBAR TOGGLER BUTTON -->
<div class="menu-toggler sidebar-toggler">
</div>
<!-- END SIDEBAR TOGGLER BUTTON -->
<!-- BEGIN LOGIN -->
<div class="content">
    <!-- BEGIN LOGIN FORM -->
    <form class="login-form" id="login-form" method="post" novalidate="novalidate">
        <h3 class="form-title">用户登录</h3>

        <div class="form-group">
            <!--ie8, ie9 does not support html5 placeholder, so we just show field title for that-->
            <label class="control-label visible-ie8 visible-ie9">用户名</label>

            <div class="input-icon">
                <i class="iconfont">&#xe601;</i>
                <input class="form-control placeholder-no-fix valid" type="text" autocomplete="off" placeholder="用户名"
                       value="user1"
                       name="account" aria-required="true" aria-invalid="false">
            </div>
        </div>
        <div class="form-group">
            <label class="control-label visible-ie8 visible-ie9">密码</label>

            <div class="input-icon">
                <i class="iconfont">&#xe609;</i>
                <input class="form-control placeholder-no-fix valid" type="password" autocomplete="off" placeholder="密码"
                       value="123456"
                       name="password" aria-required="true" aria-invalid="false">
            </div>
        </div>
        <div class="form-actions">
            <button type="submit" class="btn btn-success ">
                登录<i class="m-icon-swapright m-icon-white"></i>
            </button>
            <a href="${ctx}/userindex/signup" class="btn btn-success pull-right">
                注册<i class="m-icon-swapright m-icon-white"></i>
            </a>

        </div>

    </form>
    <!-- END LOGIN FORM -->


</div>
<!-- END LOGIN -->
<!-- BEGIN COPYRIGHT -->
<script type="text/javascript">
    var ROOTPAth = "${ctx}";
</script>
<script type="text/javascript" src="${ctx}/res-build/config.js" data-init="users/login"></script>

<%--<script type="text/javascript">
    seajs.use("${ctx}/res-build/src/login.js")
</script>--%>
</body>


</html>
