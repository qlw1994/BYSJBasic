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
                   就诊人选择
                </div>
                <form class="form-inline" id="search-form">
                    <%--<div class="form-group">--%>
                    <%--<label>预约时间</label>--%>
                    <%--<input type="text" id="appoint_date" autocomplete="off" readonly class="form-control"--%>
                    <%--name="date" value="${sessionScope.nextdate}"/>--%>
                    <%--</div>--%>
                    <div class="form-group">
                        <label for="patientname">就诊人名</label>
                        <div class="hospital_con">
                            <input AUTOCOMPLETE="off" name="name" type="text" value="${patientname}" id="patientname"/>
                            <ul class="list" id="patientList">
                            </ul>
                        </div>
                    </div>
                    <button type="submit" class="btn btn-info" id="search">查询</button>
                </form>
                <div class="actions">
                    <a class="btn btn-success btn-sm" href="#" data-toggle="modal" data-target="#addModal"> <i
                            class="iconfont">&#xe612;</i> <span class="hidden-480">添加就诊人</span>
                    </a>
                </div>
            </div>

        </div>
    </div>
</div>
<div id="patients"></div>

<div class="modal fade" id="addModal">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal">
                    <span aria-hidden="true">&times;</span> <span class="sr-only">Close</span>
                </button>
                <h4 class="modal-title">添加就诊人</h4>
            </div>
            <form class="form-horizontal" id="vPatientForm">
                <div class="modal-body">
                    <div class="portlet-body form">
                        <div class="form-body">
                            <input type="hidden" name="uid" value="${uid}">
                            <div class="form-group">
                                <label class="col-md-3 control-label">姓名</label>
                                <div class="col-md-8">
                                    <input type="text" AUTOCOMPLETE="off" class="form-control" name="name"
                                           placeholder="姓名">
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-md-3 control-label">就诊人类型</label>

                                <div class="col-md-8">
                                    <label class="radio-inline">  <input type="radio"  value="1"
                                                                         name="type">成人</label>

                                    <label class="radio-inline"> <input type="radio"  value="2"
                                                                        name="type">儿童</label>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-md-3 control-label">就诊人性别</label>
                                <div class="col-md-8">
                                    <label class="radio-inline">  <input type="radio"  value="1"
                                                                         name="sex">男</label>

                                    <label class="radio-inline"> <input type="radio"  value="2"
                                                                        name="sex">女</label>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-md-3 control-label">就诊人出生日期</label>
                                <div class="col-md-8">
                                    <input type="text" AUTOCOMPLETE="off" class="form-control" id="add_birthday" name="birthday" contenteditable="false" disabled="disabled">
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-md-3 control-label">证件类型</label>
                                <div class="col-md-8">
                                    <select  name="idtype" class="form-control">
                                        <option  value="" selected="selected">请选择</option>
                                        <option  value="1">二代身份证</option>
                                        <option  value="2">港澳居民身份证</option>
                                        <option  value="3">台湾居民身份证</option>
                                        <option  value="4">护照</option>
                                    </select>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-md-3 control-label">证件号码</label>
                                <div class="col-md-8">
                                    <input type="text" AUTOCOMPLETE="off" class="form-control" placeholder="证件号码"
                                           name="idnumber">
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-md-3 control-label">监护人姓名</label>
                                <div class="col-md-8">
                                    <input type="text" AUTOCOMPLETE="off" class="form-control" placeholder="监护人姓名"
                                           name="guardianname">
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-md-3 control-label">监护人证件类型</label>
                                <div class="col-md-8">
                                    <select  name="guardianidtype" class="form-control">
                                        <option  value="" selected="selected">请选择</option>
                                        <option  value="1">二代身份证</option>
                                        <option  value="2">港澳居民身份证</option>
                                        <option  value="3">台湾居民身份证</option>
                                        <option  value="4">护照</option>
                                    </select>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-md-3 control-label">监护人证件号码</label>
                                <div class="col-md-8">
                                    <input type="text" AUTOCOMPLETE="off" class="form-control" placeholder="证件号码"
                                           name="guardianidnumber">
                                </div>
                            </div>

                            <div class="form-group">
                                <label class="col-md-3 control-label">手机号码</label>
                                <div class="col-md-8">
                                    <input type="text" AUTOCOMPLETE="off" class="form-control" placeholder="手机号码"
                                           name="phone">
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
                <h4 class="modal-title">修改就诊人</h4>
            </div>
            <form class="form-horizontal" id="vPatientModifyForm">

                <div class="modal-body">
                    <div class="portlet-body form j-disabled-item">
                        <div class="form-body">
                            <input type="hidden" name="id">
                            <div class="form-group">
                                <label class="col-md-3 control-label">姓名</label>
                                <div class="col-md-8">
                                    <input type="text" AUTOCOMPLETE="off" class="form-control" name="name"
                                           placeholder="姓名" disabled="disabled">
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-md-3 control-label">就诊人类型</label>

                                <div class="col-md-8">
                                    <label class="radio-inline">  <input type="radio"  value="1" disabled="disabled"
                                                                         name="type">成人</label>

                                    <label class="radio-inline"> <input type="radio"  value="2" disabled="disabled"
                                                                        name="type">儿童</label>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-md-3 control-label">就诊人性别</label>
                                <div class="col-md-8">
                                    <label class="radio-inline">  <input type="radio"  value="1"
                                                                         name="sex" disabled="disabled">男</label>
                                    <label class="radio-inline">  <input type="radio" value="2"
                                                                         name="sex" disabled="disabled">女</label>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-md-3 control-label">就诊人出生日期</label>
                                <div class="col-md-8">
                                    <input type="text" AUTOCOMPLETE="off" class="form-control" id="mod_birthday" name="birthday" contenteditable="false" disabled="disabled">
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-md-3 control-label">证件类型</label>
                                <div class="col-md-8">
                                    <select  name="idtype" class="form-control" disabled="disabled">
                                        <option  value="1">二代身份证</option>
                                        <option  value="2">港澳居民身份证</option>
                                        <option  value="3">台湾居民身份证</option>
                                        <option  value="4">护照</option>
                                    </select>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-md-3 control-label">证件号码</label>
                                <div class="col-md-8">
                                    <input type="text" AUTOCOMPLETE="off" class="form-control" placeholder="证件号码"
                                           name="idnumber" disabled="disabled">
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-md-3 control-label">监护人姓名</label>
                                <div class="col-md-8">
                                    <input type="text" AUTOCOMPLETE="off" class="form-control" placeholder="监护人姓名"
                                           name="guardianname" disabled="disabled">
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-md-3 control-label">监护人证件类型</label>
                                <div class="col-md-8">
                                    <select  name="guardianidtype" class="form-control" disabled="disabled">
                                        <option  value="1">二代身份证</option>
                                        <option  value="2">港澳居民身份证</option>
                                        <option  value="3">台湾居民身份证</option>
                                        <option  value="4">护照</option>
                                    </select>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-md-3 control-label">监护人证件号码</label>
                                <div class="col-md-8">
                                    <input type="text" AUTOCOMPLETE="off" class="form-control" placeholder="证件号码"
                                           name="guardianidnumber" disabled="disabled">
                                </div>
                            </div>

                            <div class="form-group">
                                <label class="col-md-3 control-label">手机号码</label>
                                <div class="col-md-8">
                                    <input type="text" AUTOCOMPLETE="off" class="form-control" placeholder="手机号码"
                                           name="phone" disabled="disabled">
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
                <a href="${ctx}/admin/patients/index?pcode=1&subcode=2&uid=${uid}"
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
</body>

<content tag="jsconfig">
    <script type="text/javascript">
        var currentpage = ${currentpage};
    </script>
    <%--<script src="${ctx}/res-build/res/echarts/echarts.common.min.js"></script>--%>
    <%--<script type="text/javascript" src="${ctx}/res-build/res/echarts/jquery-ui-bootstrap/jquery-ui-1.10.3.custom.min.js"></script>--%>
    <script type="text/javascript" src="${ctx}/res-build/config.js" data-init="users/patient_chosen"></script>

</content>
