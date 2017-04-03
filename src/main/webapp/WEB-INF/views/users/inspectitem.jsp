<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sitemesh" uri="http://www.opensymphony.com/sitemesh/decorator" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<content tag="cssconfig">
    <link href="${ctx}/res-build/css/sys.css" rel="stylesheet" type="text/css"/>

    <link href="${ctx}/res-build/res/module/ajaxpage/css/page.css" rel="stylesheet" type="text/css"/>
</content>

<body>
<div class="portlet-body" id="inspectitems-list">
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
                <th class="sorting">检验项目名称</th>
                <th class="sorting">检验结果</th>
                <th class="sorting">参考范围</th>
                <th class="sorting">单位</th>
                <th class="sorting">结果异常提示</th>
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
            <form class="form-horizontal" id="vInspectitemModifyForm">

                <div class="modal-body">
                    <div class="portlet-body form j-disabled-item">
                        <div class="form-body">
                            <input type="hidden" name="id">
                            <div class="form-group">
                                <label class="col-md-3 control-label">检验名</label>
                                <div class="col-md-8">
                                    <input type="text" AUTOCOMPLETE="off" class="form-control" name="name" disabled="disabled" placeholder="检验名">
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-md-3 control-label">检验结果</label>
                                <div class="col-md-8">
                                    <input type="text" AUTOCOMPLETE="off" class="form-control" name="result"  disabled="disabled" placeholder="检验结果">
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-md-3 control-label">参考范围</label>
                                <div class="col-md-8">
                                    <input type="text" AUTOCOMPLETE="off" class="form-control"  name="refrange"  disabled="disabled" placeholder="参考范围">
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-md-3 control-label">单位</label>
                                <div class="col-md-8">
                                    <input type="text" AUTOCOMPLETE="off" class="form-control"  name="unit"  disabled="disabled"
                                           placeholder="单位">
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-md-3 control-label">结果异常提示</label>
                                <div class="col-md-8">
                                    <input type="text" AUTOCOMPLETE="off" class="form-control" name="abnormal"  disabled="disabled" placeholder="检验总价">
                                </div>
                            </div>
                            <!-- END FORM-->
                        </div>
                    </div>
                    <div class="modal-footer">

                        <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
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
        var hospitalname = "${hospitalname}";
        var patientid=${patientid};
        var currentpage = ${currentpage};
    </script>
    <%--<script src="${ctx}/res-build/res/echarts/echarts.common.min.js"></script>--%>
    <%--<script type="text/javascript" src="${ctx}/res-build/res/echarts/jquery-ui-bootstrap/jquery-ui-1.10.3.custom.min.js"></script>--%>
    <script type="text/javascript" src="${ctx}/res-build/config.js" data-init="users/inspectitem"></script>

</content>
