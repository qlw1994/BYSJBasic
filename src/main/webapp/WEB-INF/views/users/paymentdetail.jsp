<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sitemesh" uri="http://www.opensymphony.com/sitemesh/decorator" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<content tag="cssconfig">
    <link rel="stylesheet" type="text/css" href="${ctx}/res-build/res/echarts/css/css.css">
    <link rel="stylesheet" href="${ctx}/res-build/res/echarts/jquery-ui-bootstrap/css/jquery-ui-1.10.3.custom.css"/>
    <link href="${ctx}/res-build/css/sys.css" rel="stylesheet" type="text/css"/>
    <link href="${ctx}/res-build/res/module/ajaxpage/css/page.css" rel="stylesheet" type="text/css"/>
</content>

<body>
<h3 class="page-title">""</h3>
<div class="row" style="background-color: white">
    <div class="col-md-12">
        <div class="portlet">
            <div class="portlet-search">
                <div class="caption">
                    ${sessionScope.hospitalname}- 待支付列表
                </div>

            </div>


        </div>
    </div>
</div>
<div  style="float:right;padding-top: 2%;padding-right: 5%">
    <button id="toPayBtn" class="btn btn-success btn-sm"
    ><span class="hidden-480">去支付</span>
    </button>
</div>
<div class="portlet-body" id="paymentdetail-list" style="background-color: white">
    <div class="table-pages clearfix">
        <div class="table-page clearfix">

        </div>
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
<div class="modal fade" id="modifyModal">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal">
                    <span aria-hidden="true">&times;</span> <span class="sr-only">Close</span>
                </button>
                <h4 class="modal-title">支付详情</h4>
            </div>
            <form class="form-horizontal" id="vPaymentdetailModifyForm">

                <div class="modal-body">
                    <div class="portlet-body form j-disabled-item">
                        <div class="form-body">
                            <input type="hidden" name="id">
                            <div class="form-group">
                                <label class="col-md-3 control-label">交易流水号</label>
                                <div class="col-md-8">
                                    <input type="text" AUTOCOMPLETE="off" class="form-control" name="name" readonly placeholder="交易流水号">
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-md-3 control-label">发票号码</label>
                                <div class="col-md-8">
                                    <input type="text" AUTOCOMPLETE="off" class="form-control" name="price"
                                           placeholder="发票号码" readonly>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-md-3 control-label">支付时间</label>
                                <div class="col-md-8">
                                    <input type="text" AUTOCOMPLETE="off" class="form-control" name="format"
                                           placeholder="支付时间" readonly>
                                </div>
                            </div>
                            <!-- END FORM-->
                            <div class="modal-footer">
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


</body>

<content tag="jsconfig">
    <script type="text/javascript">
        var patientid=${patientid};
        var patientname="${patientname}";
        var hospitalid =${hospitalid};
        var hospitalname = "${hospitalname}";
        var currentpage = ${currentpage};
        var uid=${sessionScope.user.id};
        var uname="${sessionScope.user.name}";
    </script>
    <%--<script src="${ctx}/res-build/res/echarts/echarts.common.min.js"></script>--%>
    <%--<script type="text/javascript" src="${ctx}/res-build/res/echarts/jquery-ui-bootstrap/jquery-ui-1.10.3.custom.min.js"></script>--%>
    <script type="text/javascript" src="${ctx}/res-build/res/js/jQuery.md5.js"></script>
    <script type="text/javascript" src="${ctx}/res-build/res/js/JsBarcode.all.min.js"></script>
    <script type="text/javascript" src="${ctx}/res-build/res/js/JsBarcode.code128.min.js"></script>
    <script type="text/javascript" src="${ctx}/res-build/config.js" data-init="users/paymentdetail"></script>

</content>
