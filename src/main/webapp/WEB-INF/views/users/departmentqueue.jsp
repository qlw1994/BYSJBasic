<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sitemesh" uri="http://www.opensymphony.com/sitemesh/decorator" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<content tag="cssconfig">
    <link href="${ctx}/res-build/css/sys.css" rel="stylesheet" type="text/css"/>

    <link href="${ctx}/res-build/res/module/ajaxpage/css/page.css" rel="stylesheet" type="text/css"/>
</content>

<body>
<div class="row">
    <div class="col-md-12">
        <div class="portlet">
            <div class="portlet-search clearfix" id="h1_text">
                <center><h1></h1></center>
            </div>

        </div>
    </div>
</div>
</body>

<content tag="jsconfig">
    <script type="text/javascript">
        var patientid =${patientid};
        var hospitalid =${hospitalid};
        var hospitalname = "${hospitalname}";
        var currentpage = ${currentpage};
    </script>
    <%--<script src="${ctx}/res-build/res/echarts/echarts.common.min.js"></script>--%>
    <%--<script type="text/javascript" src="${ctx}/res-build/res/echarts/jquery-ui-bootstrap/jquery-ui-1.10.3.custom.min.js"></script>--%>
    <script type="text/javascript" src="${ctx}/res-build/config.js" data-init="users/departmentqueue"></script>

</content>
