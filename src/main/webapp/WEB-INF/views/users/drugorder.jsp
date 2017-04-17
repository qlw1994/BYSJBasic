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
<div class="row">
    <div class="col-md-12">
        <div class="portlet">
            <div class="portlet-title" style="background-color: white">
                <form class="form-inline" id="search-form">
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
                    <button type="submit" class="btn btn-info" id="search">查询</button>
                </form>
            </div>
            <div class="portlet-body" id="drugorder-list" style="background-color: white">
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
<!-- /.modal -->
<div class="modal fade" id="modifyModal">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal">
                    <span aria-hidden="true">&times;</span> <span class="sr-only">Close</span>
                </button>
                <h4 class="modal-title">查看</h4>
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

</body>

<content tag="jsconfig">
    <script type="text/javascript">
        var hospitalid =${hospitalid};
        var patientid=${patientid};
        var hospitalname = "${hospitalname}";
        var currentpage = ${currentpage};
    </script>
    <script src="${ctx}/res-build/res/echarts/echarts.common.min.js"></script>
    <script type="text/javascript" src="${ctx}/res-build/res/echarts/jquery-ui-bootstrap/jquery-ui-1.10.3.custom.min.js"></script>
    <script type="text/javascript" src="${ctx}/res-build/config.js" data-init="users/drugorder"></script>

</content>
