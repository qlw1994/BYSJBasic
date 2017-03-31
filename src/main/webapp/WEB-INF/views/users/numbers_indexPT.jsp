<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sitemesh" uri="http://www.opensymphony.com/sitemesh/decorator" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<content tag="cssconfig">
    <link href="${ctx}/res-build/css/sys.css" rel="stylesheet" type="text/css"/>

    <link href="${ctx}/res-build/res/module/ajaxpage/css/page.css" rel="stylesheet" type="text/css"/>
</content>

<body>
<div id="departments">
    <div class="portlet-body" id="numbers-list">
        <div class="table-container">
            <table class="table table-striped table-bordered table-hover dataTable no-footer"
                   id="datatable_ajax">

                <thead>
                <tr role="row">
                    <th class="sorting">时间段</th>
                    <th class="sorting">星期一</th>
                    <th class="sorting">星期二</th>
                    <th class="sorting">星期三</th>
                    <th class="sorting">星期四</th>
                    <th class="sorting">星期五</th>
                    <th class="sorting">星期六</th>
                    <th class="sorting">星期日</th>
                </tr>
                </thead>
                <tbody>
                </tbody>
            </table>
        </div>

    </div>
</div>
</body>

<content tag="jsconfig">
    <script type="text/javascript">
        var hospitalid =${hospitalid};
        var hospitalname = "${hospitalname}";
        var currentpage = ${currentpage};
    </script>
    <%--<script src="${ctx}/res-build/res/echarts/echarts.common.min.js"></script>--%>
    <%--<script type="text/javascript" src="${ctx}/res-build/res/echarts/jquery-ui-bootstrap/jquery-ui-1.10.3.custom.min.js"></script>--%>
    <script type="text/javascript" src="${ctx}/res-build/config.js" data-init="users/department"></script>

</content>
