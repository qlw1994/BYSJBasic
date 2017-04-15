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
<div id="schedhulings" style="background-color: white">
    <div class="portlet-body" id="scheduling-list">
        <div class="table-container">
            <table class="table table-striped table-bordered table-hover dataTable no-footer"
                   id="datatable_ajax">

                <thead>
                <tr role="row">
                    <th class="sorting">日期</th>
                    <th class="sorting">上午</th>
                    <th class="sorting">下午</th>
                </tr>
                </thead>
                <tbody>
                </tbody>
            </table>
        </div>

    </div>
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

                        <div class="dialogtip-msg">预约成功</div>
                    </div>
                </div>
            </div>
            <div class="modal-footer">
                <a href="${ctx}/user/appointments/patientindex?patientname=${patientname}&patientid=${patientid}"
                   class="btn btn-success">查看历史预约记录</a>

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
                        <h4 class="dialogtip-tit" style="margin-top: 12px;">预约失败</h4>
                    </div>
                </div>
            </div>
            <div class="modal-footer">
                <%--<a href="${ctx}/user/appointments/patientindex?patientname=${patientname}&patientid=${patientid}" class="btn btn-danger j-modal-closebtn" >关闭</a>--%>
                    <a href="#" class="btn btn-danger j-modal-closebtn" >关闭</a>
            </div>
        </div>
    </div>
</div>
</body>

<content tag="jsconfig">
    <script type="text/javascript">
        var hospitalid =${hospitalid};
        var hospitalname = "${hospitalname}";
        var doctorid =${doctorid};
        var patientid =${patientid};
    </script>
    <%--<script src="${ctx}/res-build/res/echarts/echarts.common.min.js"></script>--%>
    <%--<script type="text/javascript" src="${ctx}/res-build/res/echarts/jquery-ui-bootstrap/jquery-ui-1.10.3.custom.min.js"></script>--%>
    <script type="text/javascript" src="${ctx}/res-build/config.js" data-init="users/scheduling_index"></script>

</content>
