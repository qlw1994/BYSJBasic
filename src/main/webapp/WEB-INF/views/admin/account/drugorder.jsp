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
        <li><i class="iconfont ico-home">&#xe60a;</i> 主页<i
                class="iconfont ico-angle-right">
            &#xe605;</i></li>
        <li><a href="#">账户管理</a> <i class="iconfont ico-angle-right">&#xe605;</i></li>
        <li><a href="#">用户</a><i class="iconfont ico-angle-right">&#xe605;</i></li>
        <li><a href="#">就诊人管理</a><i class="iconfont ico-angle-right">&#xe605;</i></li>
        <li><a href="#">药品订单管理</a></li>
    </ul>
</div>
<!-- END PAGE HEADER-->
<!-- BEGIN PAGE CONTENT-->
<div class="row">
    <div class="col-md-12">
        <div class="portlet">
            <div class="portlet-title">

                <div class="form-inline" id="search-form">

                    <input type="hidden" name="patientid" value="${patientid}">
                    <div class="form-group">
                        <label for="starttime">开始日期</label>
                        <div class="hospital_con">
                            <input AUTOCOMPLETE="off" name="startdate" type="text" value="${starttime}"
                                   id="starttime"/>

                        </div>
                        <label for="endtime">结束日期</label>
                        <div class="hospital_con">
                            <input AUTOCOMPLETE="off" name="enddate" type="text" value="${endtime}"
                                   id="endtime"/>

                        </div>
                    </div>
                    <button class="btn btn-info" id="search">查询</button>
                </div>
            </div>
            <div class="portlet-body" id="drugorder-list">
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
                            <th class="sorting">医嘱</th>
                            <th class="sorting">订单日期</th>
                            <th class="sorting">订单总金额</th>
                            <th class="sorting">订单状态</th>
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
                <h4 class="modal-title">添加订单</h4>
            </div>
            <form class="form-horizontal" id="vDrugorderForm">
                <div class="modal-body">
                    <div class="portlet-body form">
                        <div class="form-body">
                            <input type="hidden" name="doctorid" value="${doctorid}"/>
                            <input type="hidden" name="patientid" value="${patientid}"/>
                            <input type="hidden" name="hospitalid" value="${hospitalid}"/>
                            <input type="hidden" name="departmentid" value="${departmentid}"/>
                            <input type="hidden" id="add_total" name="total" value="1"/>
                            <div class="form-group" id="add_status">
                                <label class="col-md-3 control-label">订单状态</label>
                                <div class="col-md-8">
                                    <select name="status" class="form-control">
                                        <option value="" selected="selected">请选择</option>
                                        <option value="0">未支付</option>
                                        <option value="1">已支付</option>
                                        <option value="2">已取药</option>
                                    </select>
                                </div>
                            </div>
                            <div class="form-group" id="add_orderAdvice">
                                <label class="col-md-3 control-label">医嘱</label>
                                <div class="col-md-8">
                                    <textarea class="form-control" name="advice"></textarea>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-md-3 control-label">订单总金额</label>
                                <div class="col-md-8">
                                    <input type="text" class="form-control" name="money" disabled="disabled"
                                           id="add_orderMoney">
                                </div>
                            </div>
                            <div class="form-group" id="add_drugName_1">
                                <label class="col-md-3 control-label">药品名称</label>
                                <div class="col-md-8">
                                    <input type="text" class="form-control" AUTOCOMPLETE="off"
                                           id="add_drugName1" name="drugorderdetails[0].drugname" placeholder="药品名称">
                                    <ul id="add_drugList1" class="list">

                                    </ul>
                                </div>
                            </div>
                            <input type="hidden" id="add_drugId_1" name="drugorderdetails[0].drugid">
                            <div class="form-group" id="add_drugAmount_1">
                                <label class="col-md-3 control-label">药品数量</label>
                                <div class="col-md-8">
                                    <input type="text" class="form-control" AUTOCOMPLETE="off"
                                           id="add_drugAmount1" disabled="disabled"
                                           name="drugorderdetails[0].amount" placeholder="药品数量">
                                </div>
                            </div>
                            <div class="form-group" id="add_drugMoney_1">
                                <label class="col-md-3 control-label">药品总价</label>
                                <div class="col-md-8">
                                    <input type="text" class="form-control" disabled="disabled" id="add_drugMoney1"
                                           name="drugorderdetails[0].money">
                                </div>
                            </div>
                            <div class="form-group" id="add_drugFormat_1">
                                <label class="col-md-3 control-label">药品规格</label>
                                <div class="col-md-8">
                                    <input type="text" class="form-control" id="add_drugFormat1"
                                           name="drugorderdetails[0].format" disabled="disabled">
                                </div>
                            </div>
                            <div class="form-group" id="add_drugPrice_1">
                                <label class="col-md-3 control-label">药品单价</label>
                                <div class="col-md-8">
                                    <input type="text" class="form-control" id="add_drugPrice1"
                                           name="drugorderdetails[0].price" disabled="disabled">
                                </div>
                            </div>

                            <div class="form-group">
                                <div class="col-md-11">
                                    <button class="form-control" id="add_details">添加一条</button>
                                    <button class="form-control" id="del_details">删除一条</button>
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
                <h4 class="modal-title">修改订单</h4>
            </div>
            <form class="form-horizontal" id="vDrugorderModifyForm">

                <div class="modal-body">
                    <div class="portlet-body form j-disabled-item">
                        <div class="form-body">
                            <input type="hidden" name="id">
                            <div class="form-group">
                                <label class="col-md-3 control-label">医嘱</label>
                                <div class="col-md-8">
                                    <textarea AUTOCOMPLETE="off" class="form-control" name="advice"
                                              placeholder="医嘱" disabled="disabled"></textarea>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-md-3 control-label">订单状态</label>
                                <div class="col-md-8">
                                    <select id="mod_status" name="status" class="form-control">
                                        <option value="0">未支付</option>
                                        <option value="1">已支付</option>
                                        <option value="2">已取药</option>
                                    </select>
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
                <a href="${ctx}/admin/drugorders/paitnetindex?pcode=1&subcode=2&$patientname=${$patientname}&patientid=${patientid}"
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
<!-- END PAGE CONTENT-->
</body>
<content tag="jsconfig">
    <script type="text/javascript">
        var currentpage = ${currentpage};
        var patientid =${patientid};
        var patientname = '${patientname}';
        var uid =${uid};
    </script>
    <script type="text/javascript"
            src="${ctx}/res-build/res/echarts/jquery-ui-bootstrap/jquery-ui-1.10.3.custom.min.js"></script>
    <script type="text/javascript" src="${ctx}/res-build/res/jquery-ui-timepicker/jquery-ui-timepicker-addon.min.js"></script>
    <script type="text/javascript" src="${ctx}/res-build/config.js"
            data-init="admin/account/drugorder"></script>
</content>
