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
        <li><i class="iconfont ico-home">&#xe60a;</i>主页<i
                class="iconfont ico-angle-right">
            &#xe605;</i></li>
        <li><a href="#">医院配置</a> <i class="iconfont ico-angle-right">&#xe605;</i></li>
        <li><a href="#">医院管理</a><i class="iconfont ico-angle-right">&#xe605;</i></li>
        <li><a href="#">科室管理</a><i class="iconfont ico-angle-right">&#xe605;</i></li>
        <li><a href="${ctx}/admin/hospitalDoctors/index?pcode=2&subcode=1&departmentid=${departmentid}&departmentname=${departmentname}">医生管理</a><i class="iconfont ico-angle-right">&#xe605;</i></li>
        <li><a href="#">检查单管理</a></li>
    </ul>
</div>
<!-- END PAGE HEADER-->
<!-- BEGIN PAGE CONTENT-->
<div class="row">
    <div class="col-md-12">
        <div class="portlet">
            <div class="portlet-title">
                <%--<div class="caption">--%>
                  <%--${hospitalname}-${departmentname}-${doctorname}-${patientname} 检查单管理--%>
                <%--</div>--%>
                <div class="form-inline" id="search-form">
                    <input type="hidden" name="patientid" value="${patientid}">
                    <input type="hidden" name="hospitalid" value="${hospitalid}">
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
                    <button  class="btn btn-info" id="search">查询</button>
                    <div class="actions" style="float: right">
                        <a class="btn btn-success btn-sm" href="#" data-toggle="modal" data-target="#addModal"> <i
                                class="iconfont">&#xe612;</i> <span class="hidden-480">添加检查单</span>
                        </a>
                    </div>
                </div>

            </div>
            <div class="portlet-body" id="checkreport-list">
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
                            <th class="sorting">检查部位</th>
                            <th class="sorting">检查方法</th>
                            <th class="sorting">检查时间</th>
                            <%--<th class="sorting">检查表状态</th>--%>
                            <th class="sorting">诊断意见</th>
                            <th class="sorting">医嘱项</th>
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
                <h4 class="modal-title">添加检查单</h4>
            </div>
            <form class="form-horizontal" id="vCheckreportForm">
                <div class="modal-body">
                    <div class="portlet-body form">
                        <div class="form-body">
                            <input type="hidden" name="doctorid" value="${doctorid}"/>
                            <input type="hidden" name="patientid" value="${patientid}"/>
                            <input type="hidden" name="hospitalid" value="${hospitalid}"/>
                            <input type="hidden" name="departmentid" value="${departmentid}"/>
                            <input type="hidden" id="add_auditorid" name="auditorid" />
                            <input type="hidden" id="add_date" name="date" class="form-control">
                            <div class="form-group">
                                <label class="col-md-3 control-label">检查表状态</label>
                                <div class="col-md-8">
                                    <select name="status" class="form-control">
                                        <option value="">请选择</option>
                                        <option value="1">已审核</option>
                                        <option value="2">未审核</option>
                                    </select>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-md-3 control-label">检查时间</label>
                                <div class="col-md-8">
                                    <input type="text" readonly name="checktime" id="add_checktime"
                                           class="form-control">
                                </div>
                            </div>

                            <div class="form-group">
                                <label class="col-md-3 control-label">检查部位</label>
                                <div class="col-md-8">
                                    <input type="text" name="part"
                                           class="form-control">
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-md-3 control-label">检查方法</label>
                                <div class="col-md-8">
                                    <input type="text" name="method"
                                           class="form-control">
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-md-3 control-label">医嘱</label>
                                <div class="col-md-8">
                                    <textarea class="form-control" name="advice"></textarea>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-md-3 control-label">诊断意见</label>
                                <div class="col-md-8">
                                    <textarea class="form-control" name="options">
                                    </textarea>
                                </div>
                            </div>

                            <div class="form-group">
                                <label class="col-md-3 control-label">审核人账号</label>
                                <div class="col-md-8">
                                    <input type="text" class="form-control" AUTOCOMPLETE="off"
                                           id="add_auditor"
                                           name="auditoraccount" placeholder="审核人账号">
                                    <ul class="list" id="add_auditorList">

                                    </ul>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-md-3 control-label">审核人姓名</label>
                                <div class="col-md-8">
                                    <input type="text" class="form-control"
                                           id="add_auditorname" readonly
                                           name="auditorname" placeholder="审核人姓名">

                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-md-3 control-label">审核时间</label>
                                <div class="col-md-8">
                                    <input type="text" class="form-control" readonly id="add_examtime" name="examtime">
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
            <form class="form-horizontal" id="vCheckreportModifyForm">

                <div class="modal-body">
                    <div class="portlet-body form j-disabled-item">
                        <div class="form-body">
                            <input type="hidden" name="id">
                            <input type="hidden" id="mod_auditorid" name="auditorid" />
                            <input type="hidden" id="mod_date" name="date">
                            <div class="form-group">
                                <label class="col-md-3 control-label">检查表状态</label>
                                <div class="col-md-8">
                                    <select name="status" class="form-control" disabled="disabled">
                                        <option value="1">已审核</option>
                                        <option value="2">未审核</option>
                                    </select>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-md-3 control-label">检查时间</label>
                                <div class="col-md-8">
                                    <input type="text" readonly name="checktime" id="mod_checktime"
                                           disabled="disabled"
                                           class="form-control">
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-md-3 control-label">检查部位</label>
                                <div class="col-md-8">
                                    <input type="text" name="part" disabled="disabled"
                                           class="form-control">
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-md-3 control-label">检查方法</label>
                                <div class="col-md-8">
                                    <input type="text" name="method" disabled="disabled"
                                           class="form-control">
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-md-3 control-label">医嘱</label>
                                <div class="col-md-8">
                                    <textarea class="form-control" disabled="disabled" name="advice"></textarea>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-md-3 control-label">诊断意见</label>
                                <div class="col-md-8">
                                    <textarea class="form-control" name="options" disabled="disabled">
                                    </textarea>
                                </div>
                            </div>

                            <div class="form-group">
                                <label class="col-md-3 control-label">审核人账号</label>
                                <div class="col-md-8">
                                    <input type="text" class="form-control" AUTOCOMPLETE="off" id="mod_auditor"
                                           name="auditoraccount" placeholder="审核人账号" disabled="disabled">
                                    <ul class="list" id="mod_auditorList">

                                    </ul>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-md-3 control-label">审核人姓名</label>
                                <div class="col-md-8">
                                    <input type="text" class="form-control"  id="mod_auditorname"
                                           name="auditorname" placeholder="审核人姓名" readonly>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-md-3 control-label">审核时间</label>
                                <div class="col-md-8">
                                    <input type="text" class="form-control" readonly id="mod_examtime" name="examtime"
                                           disabled="disabled">
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
                <a href="${ctx}/admin/checkreports/index?pcode=2&subcode=1&uid=${uid}&patientid=${patientid}"
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
        var doctorid =${doctorid};
        var doctorname = '${docotorname}';
        var hospitalid =${hospitalid};
        var hospitalname = '${hospitalname}';
        var uid =${uid};
    </script>
    <script type="text/javascript"
            src="${ctx}/res-build/res/echarts/jquery-ui-bootstrap/jquery-ui-1.10.3.custom.min.js"></script>
    <script type="text/javascript"
            src="${ctx}/res-build/res/jquery-ui-timepicker/jquery-ui-timepicker-addon.min.js"></script>
    <script type="text/javascript" src="${ctx}/res-build/config.js"
            data-init="admin/hospital/checkreport"></script>
</content>
