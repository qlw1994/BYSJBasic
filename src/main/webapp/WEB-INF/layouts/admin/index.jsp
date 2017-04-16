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

    <title>管理平台</title>
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
    <div class="page-header clearfix header-fixed">
        <div class="page-header-inner">
            <div class="page-logo">
                <a href="${ctx}/admin/index">
                    管理平台
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
                        <a href="#" class="top-tools-item-a top-user">
                            <img alt="" class="img-circle hide1" src="${ctx}/res-build/img/avatar3_small.jpg">
                            <span class="username username-hide-on-mobile">${sessionScope.sysuser.name}</span>
                            <i class="iconfont">&#xe606;</i>
                        </a><%--

                        <div class="dropdown-menu dropdown-notification">
                            <div class="dropdown-menu-con "></div>
                        </div>--%>
                    </li>
                    <li class="top-tools-item dropdown top-userbox">
                        <a href="#" class="top-tools-item-a top-user" data-toggle="modal" data-target="#modifyModal-index">
                            <i class="iconfont">&#xe617;</i>
                            <span class="username username-hide-on-mobile">修改密码</span>

                        </a><%--

                        <div class="dropdown-menu dropdown-notification">
                            <div class="dropdown-menu-con "></div>
                        </div>--%>
                    </li>
                    <li class="top-tools-item top-logout" id="top-logout">
                        <a href="${ctx}/logout" class="top-tools-item-a j-top-logout">
                            <i class="iconfont">&#xe61e;</i>
                            <span class="tool-name">退出</span>
                        </a>
                    </li>
                </ul>
            </div>
        </div>
    </div>

    <!-- E:page-header -->
    <!-- B:page-container -->
    <div class="page-container clearfix">
        <!-- B:page-sidebar -->
        <div class="page-sidebar-wrapper">
            <div class="page-sidebar sidebar-fixed navbar-collapse collapse" id="page-sidebar"><!-- page-sidebar-in -->
                <div class="page-sidebar-menu" id="page-sidebar-menu" data-slide-speed="200">
                    <div class="page-sidebar-tit clearfix">
                        <h3>导航</h3>
                        <a href="#" class="sidebar-toggler"><i class="iconfont">&#xe603;</i>
                        </a>
                    </div>
                    <ul class="page-sidebar-menu-ul">
                        <li class="menu-item <c:if test='${pcode == 1}'>open active</c:if>">
                            <a href="javascript:" class="menu-item-a">
                                <i class="iconfont">&#xe604;</i>
                                <span class="title">账户管理</span>
                                <span class="selected"></span>
                                <i class="iconfont arrow"></i>
                            </a>
                            <ul class="sub-menu sub-menu-hide" data-code="RMsg">
                                <li class="sub-menu-item <c:if test='${subcode==1}'>sub-active</c:if>">
                                    <a class="ajaxify" href='${ctx}/admin/sysusers/index?pcode=1&subcode=1'>管理员</a>
                                </li>
                                <li class="sub-menu-item <c:if test='${subcode==2}'>sub-active</c:if>">
                                    <a class="ajaxify" href='${ctx}/admin/users/index?pcode=1&subcode=2'>用户</a>
                                </li>
                                <li class="sub-menu-item <c:if test='${subcode==3}'>sub-active</c:if>">
                                    <a class="ajaxify" href='${ctx}/admin/doctors/index?pcode=1&subcode=3'>医生</a>
                                </li>
                            </ul>
                        </li>
                    </ul>
                    <ul class="page-sidebar-menu-ul">
                        <li class="menu-item <c:if test='${pcode == 2}'>open active</c:if>">
                            <a href="javascript:" class="menu-item-a">
                                <i class="iconfont">&#xe604;</i>
                                <span class="title">医院配置</span>
                                <span class="selected"></span>
                                <i class="iconfont arrow"></i>
                            </a>
                            <ul class="sub-menu sub-menu-hide" data-code="RMsg">
                                <li class="sub-menu-item <c:if test='${subcode==1}'>sub-active</c:if>">
                                    <a class="ajaxify" href='${ctx}/admin/hospitals/index?pcode=2&subcode=1'>医院管理</a>
                                </li>
                            </ul>
                        </li>
                    </ul>
                    <ul class="page-sidebar-menu-ul">
                        <li class="menu-item <c:if test='${pcode == 3}'>open active</c:if>">
                            <a href="javascript:" class="menu-item-a">
                                <i class="iconfont">&#xe604;</i>
                                <span class="title">医院支付账号管理</span>
                                <span class="selected"></span>
                                <i class="iconfont arrow"></i>
                            </a>
                            <ul class="sub-menu sub-menu-hide" data-code="RMsg">
                                <li class="sub-menu-item <c:if test='${subcode==1}'>sub-active</c:if>">
                                    <a class="ajaxify" href='${ctx}/admin/alipayaccounts/index?pcode=3&subcode=1'>支付宝账号</a>
                                </li>
                            </ul>
                        </li>
                    </ul>
                    <ul class="page-sidebar-menu-ul">
                        <li class="menu-item <c:if test='${pcode == 4}'>open active</c:if>">
                            <a href="javascript:" class="menu-item-a">
                                <i class="iconfont">&#xe604;</i>
                                <span class="title">报告查询</span>
                                <span class="selected"></span>
                                <i class="iconfont arrow"></i>
                            </a>
                            <ul class="sub-menu sub-menu-hide" data-code="RMsg">
                                <li class="sub-menu-item <c:if test='${subcode==1}'>sub-active</c:if>">
                                    <a class="ajaxify" href='${ctx}/admin/checkreports/patient_Chosen?pcode=3&subcode=1'>就诊人检查报告查询</a>
                                </li>
                                <li class="sub-menu-item <c:if test='${subcode==2}'>sub-active</c:if>">
                                    <a class="ajaxify" href='${ctx}/admin/checkreports/patient_Chosen?pcode=3&subcode=1'>就诊人检验报告查询</a>
                                </li>
                            </ul>
                        </li>
                    </ul>
                </div>
            </div>
        </div>
        <!-- E:page-sidebar -->
        <!-- B:page-content -->
        <div class="page-content-wrapper">
            <div class="page-content" id="page-content">
                <div class="page-content-body">
                    <sitemesh:body/>
                </div>
            </div>
        </div>
        <!-- E:page-content -->
    </div>
    <div class="modal fade" id="modifyModal-index">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal">
                        <span aria-hidden="true">&times;</span> <span class="sr-only">Close</span>
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
    <div class="page-footer clearfix">
        <div class="page-footer-tools">
                <span class="go-top">
                    <i class="iconfont">&#xe602;</i>
                </span>
        </div>
    </div>
    <!-- E:page-footer -->
</div>
<script type="text/javascript">
    var ROOTPAth = "${ctx}";
</script>

<sitemesh:getProperty property="page.jsconfig"/>
</body>
<script type="text/javascript" src="${ctx}/res-build/config.js" data-init="admin/passwordModify.js"></script>
</html>


