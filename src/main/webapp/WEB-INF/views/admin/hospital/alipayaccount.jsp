<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<content tag="cssconfig">
    <link rel="stylesheet" type="text/css" href="${ctx}/res-build/res/echarts/css/css.css">
    <link rel="stylesheet" href="${ctx}/res-build/res/echarts/jquery-ui-bootstrap/css/jquery-ui-1.10.3.custom.css"/>
    <link href="${ctx}/res-build/css/sys.css" rel="stylesheet" type="text/css"/>
    <link href="${ctx}/res-build/res/module/ajaxpage/css/page.css" rel="stylesheet" type="text/css"/>
</content>
<body id="body">
<!-- BEGIN PAGE HEADER-->
<h3 class="page-title">医院支付账号管理</h3>
<div class="page-bar clearfix">
    <ul class="page-breadcrumb">
        <li><i class="iconfont ico-home">&#xe60a;</i> <a href="index.html">主页</a> <i class="iconfont ico-angle-right">
            &#xe605;</i></li>
        <li><a href="#">医院支付账号管理</a> <i class="iconfont ico-angle-right">&#xe605;</i></li>
        <li><a href="${ctx}/admin/alipayaccounts/index?pcode=3&subcode=1">支付宝帐号</a></li>
    </ul>
</div>
<!-- END PAGE HEADER-->
<!-- BEGIN PAGE CONTENT-->
<div class="row">
    <div class="col-md-12">
        <div class="portlet">
            <div class="portlet-title">
                <form class="form-inline" id="search-form">
                    <div class="form-group">
                        <label for="search_hospitalname"> 医院</label>
                        <div class="hospital_con">
                            <input AUTOCOMPLETE="off" type="text" id="search_hospitalname"/>
                            <ul class="list">
                            </ul>
                        </div>
                    </div>
                    <button type="submit" class="btn btn-info" id="search">查询</button>
                    <div class="actions" style="float: right;">
                        <a class="btn btn-success btn-sm" href="#" data-toggle="modal" data-target="#addModal"> <i
                                class="iconfont">&#xe612;</i> <span class="hidden-480">添加账号</span>
                        </a>
                    </div>
                </form>

            </div>

        </div>
        <div class="portlet-body" id="alipay-list">
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
                <table class="table table-striped table-bordered table-hover dataTable no-footer" id="datatable_ajax">

                    <thead>
                    <tr role="row">
                        <th class="sorting">医院</th>
                        <th class="sorting">支付宝帐号</th>
                        <th class="sorting">PID</th>
                        <th class="sorting">APPID</th>
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
<div class="modal fade" id="addModal">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal">
                    <span aria-hidden="true">&times;</span> <span class="sr-only">Close</span>
                </button>
                <h4 class="modal-title">添加账户信息</h4>
            </div>
            <form class="form-horizontal" id="vAlipayForm">
                <div class="modal-body">
                    <div class="portlet-body form">
                        <div class="form-body">
                            <input type="hidden" name="hospitalid">
                            <div class="form-group">
                                <label class="col-md-3 control-label">医院名称</label>
                                <div class="col-md-8">
                                    <input AUTOCOMPLETE="off" type="text" class="form-control" name="hospitalname"
                                           id="add_hospitalname"
                                           placeholder="医院名称">
                                    <ul class="list" id="add_hospitals">

                                    </ul>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-md-3 control-label">账户名称</label>
                                <div class="col-md-8">
                                    <input AUTOCOMPLETE="off" type="text" class="form-control" name="accountname"
                                           placeholder="账户名称">
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-md-3 control-label">PID</label>
                                <div class="col-md-8">
                                    <input AUTOCOMPLETE="off" type="text" class="form-control" name="pid"
                                           placeholder="PID">
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-md-3 control-label">APPID</label>
                                <div class="col-md-8">
                                    <input AUTOCOMPLETE="off" type="text" class="form-control" name="appid"
                                           placeholder="APPID">
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-md-3 control-label">安全校验码</label>
                                <div class="col-md-8">
                                    <input AUTOCOMPLETE="off" type="text" class="form-control" name="checkkey"
                                           placeholder="安全校验码">
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-md-3 control-label">支付宝公钥</label>
                                <div class="col-md-8">
                                    <textarea AUTOCOMPLETE="off" type="text" class="form-control" name="publickey"
                                              placeholder="支付宝公钥"></textarea>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-md-3 control-label">商户私钥</label>
                                <div class="col-md-8">
                                    <textarea AUTOCOMPLETE="off" type="text" class="form-control" name="privatekey"
                                              placeholder="商户私钥"></textarea>
                                </div>
                            </div>

                        </div>
                        <!-- END FORM-->
                    </div>
                </div>
                <div class="modal-footer">
                    <button type="submit" class="btn btn-success">
                        <i class="iconfont">&#xe61c;</i> 保存
                    </button>
                    <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
                </div>
            </form>
        </div>
        <!-- /.modal-content -->
    </div>
    <!-- /.modal-dialog -->
</div>
<!-- /.modal -->
<div class="modal fade" id="modifyModal">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal">
                    <span aria-hidden="true">&times;</span> <span class="sr-only">Close</span>
                </button>
                <h4 class="modal-title">修改支付宝信息</h4>
            </div>
            <form class="form-horizontal" id="vAlipayModifyForm">

                <div class="modal-body">
                    <div class="portlet-body form">
                        <div class="form-body">
                            <input type="hidden" name="id">
                            <div class="form-group">
                                <label class="col-md-3 control-label">医院名称</label>
                                <div class="col-md-8">
                                    <input AUTOCOMPLETE="off" type="text" class="form-control" name="hospitalname"
                                           disabled="disabled"
                                           placeholder="医院名称">
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-md-3 control-label">账户名称</label>
                                <div class="col-md-8">
                                    <input AUTOCOMPLETE="off" type="text" class="form-control" name="accountname"
                                           disabled="disabled"
                                           placeholder="账户名称">
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-md-3 control-label">PID</label>
                                <div class="col-md-8">
                                    <input AUTOCOMPLETE="off" type="text" class="form-control" name="pid"
                                           disabled="disabled"
                                           placeholder="PID">
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-md-3 control-label">APPID</label>
                                <div class="col-md-8">
                                    <input AUTOCOMPLETE="off" type="text" class="form-control" name="appid"
                                           disabled="disabled"
                                           placeholder="APPID">
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-md-3 control-label">安全校验码</label>
                                <div class="col-md-8">
                                    <input AUTOCOMPLETE="off" type="text" class="form-control" name="checkkey"
                                           disabled="disabled"
                                           placeholder="安全校验码">
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-md-3 control-label">支付宝公钥</label>
                                <div class="col-md-8">
                                    <textarea AUTOCOMPLETE="off" type="text" class="form-control" name="publickey"
                                              disabled="disabled"
                                              placeholder="支付宝公钥"></textarea>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-md-3 control-label">商户私钥</label>
                                <div class="col-md-8">
                                    <textarea AUTOCOMPLETE="off" type="text" class="form-control" name="privatekey"
                                              disabled="disabled"
                                              placeholder="商户私钥"></textarea>
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
                <a href="${ctx}/admin/alipayaccounts/index?pcode=3&subcode=1"
                   class="btn btn-success">确定</a>

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
        var currentpage = $
        {
            currentpage
        }
        ;

    </script>
    <script type="text/javascript" src="${ctx}/res-build/config.js" data-init="admin/hospital/alipayaccount"></script>
</content>
