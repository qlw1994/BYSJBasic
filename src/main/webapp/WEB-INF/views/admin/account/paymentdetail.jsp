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
        <li><a href="#">支付列表</a></li>
    </ul>
</div>
<!-- END PAGE HEADER-->
<!-- BEGIN PAGE CONTENT-->
<div class="row">
    <div class="col-md-12">
        <div class="portlet">
            <div class="portlet-title">
                <div class="caption">
                    <i class="iconfont">&#xe619;</i> ${uname}-${patientname}- 支付列表
                </div>
                <div class="actions">
                    <form class="form-inline">
                        <div class="form-group">
                            <label>发票号码</label>
                            <input id="invoicenumber" type="text" name="invoicenumber" AUTOCOMPLETE="off"
                                   placeholder="发票号码">
                        </div>

                    </form>
                    <div class="page-bar-actions" style="padding-top: 12%">
                        <button id="toPayBtn" class="btn btn-success btn-sm"
                                <%--data-toggle="modal"--%>
                           <%--data-target="#viewPaymentBudgetModal"--%>
                          ><span class="hidden-480">去支付</span>
                        </button>
                    </div>
                </div>
            </div>
        </div>

    </div>
    <div class="portlet-body" id="paymentdetail-list">
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

                    <th class="sorting">支付名称</th>
                    <th class="sorting">规格</th>
                    <th class="sorting">数量</th>
                    <th class="sorting">金额</th>
                    <th class="sorting">医生姓名</th>
                    <th class="sorting">科室名</th>
                    <th class="sorting">医院名</th>
                    <th class="sorting">订单状态</th>
                    <th class="sorting">支付方式</th>
                    <th class="sorting">创建日期</th>
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
<div class="modal fade" id="modifyModal">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal">
                    <span aria-hidden="true">&times;</span> <span class="sr-only">Close</span>
                </button>
                <h4 class="modal-title">支付详情</h4>
            </div>
            <form class="form-horizontal" id="vDrugModifyForm">

                <div class="modal-body">
                    <div class="portlet-body form j-disabled-item">
                        <div class="form-body">
                            <input type="hidden" name="id">
                            <div class="form-group">
                                <label class="col-md-3 control-label">交易流水号</label>
                                <div class="col-md-8">
                                    <input type="text" AUTOCOMPLETE="off" class="form-control" name="paynumber" readonly
                                           placeholder="交易流水号">
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-md-3 control-label">发票号码</label>
                                <div class="col-md-8">
                                    <input type="text" AUTOCOMPLETE="off" class="form-control" name="invoicenumber"
                                           placeholder="发票号码" readonly>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-md-3 control-label">支付时间</label>
                                <div class="col-md-8">
                                    <input type="text" AUTOCOMPLETE="off" class="form-control" name="paydate"
                                           placeholder="支付时间" readonly>
                                </div>
                            </div>
                            <!-- END FORM-->
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
                <a href="${ctx}/admin/paymentdetails/index?pcode=1&subcode=2&patientid=${patientid}&patientname=${patientname}"
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

<!--  预结算结果 -->
<div class="modal fade" id="viewPaymentBudgetModal">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal">
                    <span aria-hidden="true"></span> <span class="sr-only">Close</span>
                </button>
                <h4 class="modal-title">诊间支付预结算结果</h4>
            </div>
            <div class="modal-body">
                <div class="table-container">
                    <table class="table table-striped table-bordered table-hover dataTable no-footer"
                           id="datatable_ajax2" aria-describedby="datatable_ajax_info" role="grid">
                        <thead>
                        <tr role="row">
                            <th class="sorting" style="width:80px;">名称</th>
                            <th class="sorting" style="width:360px;">内容</th>
                        </tr>
                        </thead>
                        <tbody id="budget_content">
                        </tbody>
                    </table>
                </div>
            </div>
            <div class="modal-footer">
                <button type="button" id="btnContinuePay" class="btn btn-default">继续</button>
                <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
            </div>
        </div>
        <!-- /.modal-content -->
    </div>
</div>


<!--  支付中间结果 -->
<div class="modal fade" id="viewPaymentMidProcModal">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal">
                    <span aria-hidden="true">&times;</span> <span class="sr-only">Close</span>
                </button>
                <h4 class="modal-title">支付过程</h4>
            </div>
            <div class="modal-body">
                <div class="table-container">
                    <iframe id="html_con" src="${ctx}/admin/paymentdetails/blank"
                            style="width:560px;height:420px;"></iframe>
                </div>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
            </div>
        </div>
        <!-- /.modal-content -->
    </div>
</div>
<!-- END PAGE CONTENT-->
</body>
<content tag="jsconfig">
    <script type="text/javascript">
        var currentpage = ${currentpage};
        var hospitalid = ${hospitalid};
        var hospitalname="${hospitalname}";
        var uid =${uid};
        var uname = "${uname}";
        var patientid =${patientid};
        var patientname = "${patientname}";
    </script>
    <script type="text/javascript" src="${ctx}/res-build/res/js/jQuery.md5.js"></script>
    <script type="text/javascript" src="${ctx}/res-build/res/js/JsBarcode.all.min.js"></script>
    <script type="text/javascript" src="${ctx}/res-build/res/js/JsBarcode.code128.min.js"></script>
    <script type="text/javascript" src="${ctx}/res-build/config.js"
            data-init="admin/account/paymentdetail"></script>
</content>
