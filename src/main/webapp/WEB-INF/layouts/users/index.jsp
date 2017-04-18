<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="sitemesh" uri="http://www.opensymphony.com/sitemesh/decorator" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>

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
    <link href="${ctx}/res-build/css/layout.css" rel="stylesheet" type="text/css"/>
    <sitemesh:getProperty property="page.cssconfig"/>
    <script type="text/javascript" src="${ctx}/res-build/res/jquery/jquery-1.11.2.min.js"></script>
    <script type="text/javascript" src="${ctx}/res-build/res/bootstrap-3.3.2/js/bootstrap.min.js"></script>
    <script type="text/javascript" src="${ctx}/res-build/res/seajs-3.0.0/dist/sea.js"></script>
    <sitemesh:getProperty property="page.headerjsconfig"/>
</head>

<body>
<div id="page" class="page-header-fixed">
    <!-- B:page-header -->
    <c:if test='${not empty sessionScope.user.id}'>
        <div class="page-header clearfix header-fixed">
            <div class="page-header-inner">
                <div class="page-logo">
                    <a href="${ctx}/userindex/index">
                        用户平台
                    </a>
                </div>
                <!--<!--href="#page-sidebar-menu" aria-expanded="false" aria-controls="collapseExample" data-toggle="collapse" data-target=".navbar-collapse"-->
                <a class="sidebar-toggler" data-toggle="collapse" href="#page-sidebar" aria-expanded="false"
                   aria-controls="page-sidebar"><i
                        class="iconfont">&#xe603;</i></a>
                <div class="top-menu">
                    <ul class="nav top-tools">
                            <%--<li class="top-tools-item dropdown">
                                <a href="#" class="top-tools-item-a top-notification">
                                    <i class="iconfont">&#xe600;</i>
                                    <span class="tool-name">消息</span>
                                    <span class="badge">4</span>
                                </a>

                                <div class="dropdown-menu dropdown-notification">
                                    <div class="dropdown-menu-con "></div>
                                </div>
                            </li>--%>
                        <li class="top-tools-item dropdown top-userbox">
                            <a href="#" class="top-tools-item-a top-user" data-toggle="modal"
                               data-target="#modifyModal-user">
                                <img alt="" class="img-circle hide1" src="${ctx}/res-build/img/avatar3_small.jpg">
                                <span class="username username-hide-on-mobile">${sessionScope.user.name}</span>
                            </a><%--

                        <div class="dropdown-menu dropdown-notification">
                            <div class="dropdown-menu-con "></div>
                        </div>--%>
                        </li>
                        <li class="top-tools-item dropdown top-userbox">
                            <a href="${ctx}/user/hospitalizations/patientChosen" class="top-tools-item-a top-user">
                                <span class="username username-hide-on-mobile">住院记录查询</span>
                            </a><%--

                        <div class="dropdown-menu dropdown-notification">
                            <div class="dropdown-menu-con "></div>
                        </div>--%>
                        </li>
                        <li class="top-tools-item dropdown top-userbox">
                            <a href="#" class="top-tools-item-a top-user" data-toggle="modal"
                               data-target="#modifyModal-index">
                                <i class="iconfont">&#xe617;</i>
                                <span class="username username-hide-on-mobile">修改密码</span>

                            </a><%--

                        <div class="dropdown-menu dropdown-notification">
                            <div class="dropdown-menu-con "></div>
                        </div>--%>
                        </li>
                        <li class="top-tools-item top-logout" id="top-logout">
                            <a href="${ctx}/userindex/logout" class="top-tools-item-a j-top-logout">
                                <i class="iconfont">&#xe61e;</i>
                                <span class="tool-name">退出</span>
                            </a>
                        </li>
                    </ul>
                </div>
            </div>
        </div>
    </c:if>
    <c:if test='${empty sessionScope.user.id}'>
        <div class="page-header clearfix header-fixed">
            <div class="page-header-inner">
                <div class="page-logo">
                    <a href="${ctx}/userindex/index">
                        用户平台
                    </a>
                </div>
                <!--<!--href="#page-sidebar-menu" aria-expanded="false" aria-controls="collapseExample" data-toggle="collapse" data-target=".navbar-collapse"-->
                <a class="sidebar-toggler" data-toggle="collapse" href="#page-sidebar" aria-expanded="false"
                   aria-controls="page-sidebar"><i
                        class="iconfont">&#xe603;</i></a>
                <div class="top-menu">
                    <ul class="nav top-tools">
                            <%--<li class="top-tools-item dropdown">
                                <a href="#" class="top-tools-item-a top-notification">
                                    <i class="iconfont">&#xe600;</i>
                                    <span class="tool-name">消息</span>
                                    <span class="badge">4</span>
                                </a>

                                <div class="dropdown-menu dropdown-notification">
                                    <div class="dropdown-menu-con "></div>
                                </div>
                            </li>--%>
                        <li class="top-tools-item dropdown top-userbox">
                            <a href="${ctx}/userlogin" class="top-tools-item-a top-user">
                                <span class="username username-hide-on-mobile">欢迎你，请登录！</span>
                            </a><%--

                        <div class="dropdown-menu dropdown-notification">
                            <div class="dropdown-menu-con "></div>
                        </div>--%>
                        </li>
                    </ul>
                </div>
            </div>

        </div>

    </c:if>


    <sitemesh:body/>

    <!-- E:page-content -->
    <div class="modal fade" id="modifyModal-user">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal">
                        <span aria-hidden="true">&times;</span> <span class="sr-only">Close</span>
                    </button>
                    <h4 class="modal-title">修改用户</h4>
                </div>
                <form class="form-horizontal" id="vUserModifyForm">

                    <div class="modal-body">
                        <div class="portlet-body form j-disabled-item">
                            <div class="form-body">
                                <input type="hidden" name="id">
                                <div class="form-group">
                                    <label class="col-md-3 control-label">账号</label>
                                    <div class="col-md-8">
                                        <input type="text" AUTOCOMPLETE="off" class="form-control" name="account"
                                               placeholder="用户名" disabled="disabled">
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-md-3 control-label">姓名</label>
                                    <div class="col-md-8">
                                        <input type="text" AUTOCOMPLETE="off" class="form-control" name="name"
                                               placeholder="姓名" disabled="disabled">
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-md-3 control-label">用户性别</label>
                                    <div class="col-md-8">
                                        <label class="radio-inline"> <input type="radio" value="1"
                                                                            name="sex" disabled="disabled">男
                                        </label>
                                        <label class="radio-inline"> <input type="radio" value="2"
                                                                            name="sex" disabled="disabled">女</label>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-md-3 control-label">证件类型</label>
                                    <div class="col-md-8">
                                        <select name="idtype" disabled="disabled" class="form-control">
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
                                               name="idnumber" disabled="disabled">
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-md-3 control-label">手机号码</label>
                                    <div class="col-md-8">
                                        <input type="text" AUTOCOMPLETE="off" class="form-control" placeholder="手机号码"
                                               name="phone" disabled="disabled">
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

    <!-- E:page-header -->
    <div class="modal fade" id="modifyModal-index">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal">
                        <span aria-hidden="true"></span> <span class="sr-only">Close</span>
                    </button>
                    <h4 class="modal-title">修改密码</h4>
                </div>
                <form class="form-horizontal" id="vPasswordForm-index">
                    <div class="modal-body">
                        <div class="portlet-body form">
                            <div class="form-body">
                                <input id="id-index" name="id" type="hidden" value=${sessionScope.user.id}>
                                <div class="form-group">
                                    <label class="col-md-3 control-label">原密码</label>
                                    <div class="col-md-8">
                                        <input AUTOCOMPLETE="off" type="password" class="form-control"
                                               name="oddPassword"
                                               placeholder="原密码"
                                               id="oddPassword">
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-md-3 control-label">新密码</label>
                                    <div class="col-md-8">
                                        <input AUTOCOMPLETE="off" type="password" class="form-control"
                                               name="newPassword" id="newPassword"
                                               placeholder="新密码">
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-md-3 control-label">重复密码</label>
                                    <div class="col-md-8">
                                        <input AUTOCOMPLETE="off" type="password" class="form-control"
                                               name="repeatPassword"
                                               placeholder="重复密码">
                                    </div>
                                </div>
                            </div>
                            <!-- END FORM-->
                        </div>
                    </div>
                    <div class="modal-footer">
                        <button type="submit" class="btn btn-success">
                            <i class="iconfont">&#xe61c;</i> 提交
                        </button>
                        <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
                    </div>
                </form>
            </div>
            <!-- /.modal-content -->
        </div>
        <!-- /.modal-dialog -->
    </div>
    <div class="modal fade bs-example-modal-sm" id="modal-box-index" tabindex="-1" role="dialog"
         aria-labelledby="mySmallModalLabel" aria-hidden="true">
        <div class="modal-dialog modal-sm">
            <div class="modal-content">
                <div class="modal-body">
                    <div class="dialogtip-con-wrap clearfix dialogtipg-success">
                        <div class="dialogtip-icon iconfont">&#xe614;</div>
                        <div class="dialogtip-con">
                            <h4 class="dialogtip-tit">操作成功</h4>

                            <div class="dialogtip-msg">密码修改成功</div>
                        </div>
                    </div>
                </div>
                <div class="modal-footer">
                    <a href="#"
                       class="btn btn-success" data-dismiss="modal">确定</a>

                </div>
            </div>
        </div>
    </div>
    <!-- E:page-container -->
    <!-- B:page-footer -->
    <div class="page-footer clearfix " style="background-color:white;bottom:5%;right:0%;position:fixed;">
        <div class="page-footer-tools">
                <span class="go-top">
                    <a href="#"><i class="iconfont">&#xe602;</i></a>
                </span>
        </div>
    </div>
    <!-- E:page-footer -->
</div>
<script type="text/javascript">
    var ROOTPAth = "${ctx}";
    var uid;
    $(function () {
        if ("${user.id}" != "undefined") {
            uid = "${user.id}";
        }
    });

</script>

<sitemesh:getProperty property="page.jsconfig"/>
</body>
<script type="text/javascript" src="${ctx}/res-build/config.js" data-init="users/passwordModify.js"></script>
</html>


