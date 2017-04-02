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
            <div class="portlet-search clearfix">
                <div class="caption">
                    ${sessionScope.hospitalname}- ${sessionScope.departmentname}- 医生选择
                </div>
                <form class="form-inline" id="search-form">
                    <%--<div class="form-group">--%>
                    <%--<label>预约时间</label>--%>
                    <%--<input type="text" id="appoint_date" autocomplete="off" readonly class="form-control"--%>
                    <%--name="date" value="${sessionScope.nextdate}"/>--%>
                    <%--</div>--%>
                    <div class="form-group">
                        <label for="doctorname">医生名</label>
                        <div class="hospital_con">
                            <input AUTOCOMPLETE="off" name="name" type="text" value="${doctorname}" id="doctorname"/>
                            <ul class="list" id="doctorList">
                            </ul>
                        </div>
                    </div>
                    <button type="submit" class="btn btn-info" id="search">查询</button>
                </form>
            </div>

        </div>
    </div>
</div>
<div id="doctors"></div>
</body>

<content tag="jsconfig">
    <script type="text/javascript">
        var hospitalid =${hospitalid};
        var hospitalname = "${hospitalname}";
        var departmentid =${departmentid};
        var departmentname = "${departmentname}";
        var type=${type};
        var currentpage = ${currentpage};
    </script>
    <%--<script src="${ctx}/res-build/res/echarts/echarts.common.min.js"></script>--%>
    <%--<script type="text/javascript" src="${ctx}/res-build/res/echarts/jquery-ui-bootstrap/jquery-ui-1.10.3.custom.min.js"></script>--%>
    <script type="text/javascript" src="${ctx}/res-build/config.js" data-init="users/doctor_chosen"></script>

</content>
