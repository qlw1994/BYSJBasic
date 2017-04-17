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
<div  style="float:right;padding-top: 2%;padding-right: 5%">
    <a href="${ctx}/user/paymentdetails/index?patientid=${patientid}&patientname=${patientname}" class="btn btn-success btn-sm"
    ><span class="hidden-480">去支付</span>
    </a>
</div>
<div class="portlet-body" id="hospitalpay-list" style="background-color: white">
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
                <th class="sorting">消费名称</th>
                <th class="sorting">单价</th>
                <th class="sorting">单位</th>
                <th class="sorting">数量</th>
                <th class="sorting">金额</th>
                <th class="sorting">医嘱</th>
                <th class="sorting">支付状态</th>


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

</body>

<content tag="jsconfig">
    <script type="text/javascript">
        var patientid =${hospitalid};
        var patientname = "${patientname}";
        var hospitalizationid=${hospitalizationid};
        var currentpage = ${currentpage};
    </script>
    <%--<script src="${ctx}/res-build/res/echarts/echarts.common.min.js"></script>--%>
    <%--<script type="text/javascript" src="${ctx}/res-build/res/echarts/jquery-ui-bootstrap/jquery-ui-1.10.3.custom.min.js"></script>--%>
    <script type="text/javascript" src="${ctx}/res-build/config.js" data-init="users/hospitalpay"></script>

</content>
