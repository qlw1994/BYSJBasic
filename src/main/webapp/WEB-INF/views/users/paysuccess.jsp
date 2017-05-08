<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="ctx" value="${basePath}"/>
<!DOCTYPE html>
<html lang="en">

<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0"/>
    <title>费用详情</title>
    <link rel="stylesheet" type="text/css" href="${ctx}/res-build/css/weui.min.css">

    <style>
        body {
            font-family: "Microsoft YaHei", Helvetica;
        }
    </style>
</head>

<body>
<h3 class="page-title">""</h3>
<div class="weui_msg">
    <div class="weui_icon_area"><i class="weui_icon_success weui_icon_msg"></i></div>
    <div class="weui_text_area">
        <h2 class="weui_msg_title">恭喜，支付成功！</h2>
    </div>
    <div class="weui_opr_area">
        <p class="weui_btn_area">
            <a href="javascript:void(0);" onclick="go()"
               class="weui_btn weui_btn_primary">查看已支付项目</a>

        </p>
    </div>

</div>
<script type="text/javascript" src="${ctx}/res-build/res/jquery/jquery-1.11.2.min.js"></script>
<script type="text/javascript">
    var ROOTPAth = "${ctx}";
    var patientid = "${patientid}";
    var patientname = "${patientname}";

    function getpath() {
        path = ROOTPAth + "/user/paymentdetails/indexpayed?patientid=" + patientid + "&patientname=" + patientname;
        return path;
    }
    function go() {
        window.location.href = getpath();
    }
</script>

</body>

</html>
