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
                <form class="form-inline" id="search-form">
                    <div class="form-group">
                        <label>省份</label>
                        <select id="priovince" class="form-control" name="province"></select>
                    </div>
                    <div class="form-group">
                        <label>城市</label>
                        <select id="city" class="form-control" name="city"></select>
                    </div>
                    <div class="form-group">
                        <label>区县</label>
                        <select id="area" class="form-control" name="area"></select>
                    </div>
                    <div class="form-group">
                        <label for="hospitalname"> 医院</label>
                        <div class="hospital_con">
                            <input AUTOCOMPLETE="off" type="text" value="${hospitalname}" id="hospitalname"/>
                            <ul class="list" id="hospitalList">
                            </ul>
                        </div>
                    </div>
                    <button type="submit" class="btn btn-info" id="search">查询</button>
                </form>

            </div>

        </div>
    </div>
</div>
<div id="hospitals">
    <div class="col-md-4 col-sm-6 item">
        <div class="thumbnail">
            <img src="${ctx}/res-build/img/avatar3_small.jpg" alt="">
            <div class="caption">
                <h3>Thumbnail label</h3>
                <p>Lorem ipsum dolor sit amet, consectetur adipisicing elit. </p>
                <p><a href="#" class="btn btn-primary" role="button">Button</a> <a href="#" class="btn btn-default"
                                                                                   role="button">Button</a></p>
            </div>
        </div>
    </div>
    <div class="col-md-4 col-sm-6 item">
        <div class="thumbnail">
            <img src="${ctx}/res-build/img/avatar3_small.jpg" alt="">
            <div class="caption">
                <h3>Thumbnail label</h3>
                <p>Lorem ipsum dolor sit amet, consectetur adipisicing elit. </p>
                <p><a href="#" class="btn btn-primary" role="button">Button</a> <a href="#" class="btn btn-default"
                                                                                   role="button">Button</a></p>
            </div>
        </div>
    </div>
    <div class="col-md-4 col-sm-6 item">
        <div class="thumbnail">
            <img src="${ctx}/res-build/img/avatar3_small.jpg" alt="">
            <div class="caption">
                <h3>Thumbnail label</h3>
                <p>Lorem ipsum dolor sit amet, consectetur adipisicing elit. </p>
                <p><a href="#" class="btn btn-primary" role="button">Button</a> <a href="#" class="btn btn-default"
                                                                                   role="button">Button</a></p>
            </div>
        </div>
    </div>
    <div class="col-md-4 col-sm-6 item">
        <div class="thumbnail">
            <img src="${ctx}/res-build/img/avatar3_small.jpg" alt="">
            <div class="caption">
                <h3>Thumbnail label</h3>
                <p>Lorem ipsum dolor sit amet, consectetur adipisicing elit. </p>
                <p><a href="#" class="btn btn-primary" role="button">Button</a> <a href="#" class="btn btn-default"
                                                                                   role="button">Button</a></p>
            </div>
        </div>
    </div>
    <div class="col-md-4 col-sm-6 item">
        <div class="thumbnail">
            <img src="${ctx}/res-build/img/avatar3_small.jpg" alt="">
            <div class="caption">
                <h3>Thumbnail label</h3>
                <p>Lorem ipsum dolor sit amet, consectetur adipisicing elit. </p>
                <p><a href="#" class="btn btn-primary" role="button">Button</a> <a href="#" class="btn btn-default"
                                                                                   role="button">Button</a></p>
            </div>
        </div>
    </div>
    <div class="col-md-4 col-sm-6 item">
        <div class="thumbnail">
            <img src="${ctx}/res-build/img/avatar3_small.jpg" alt="">
            <div class="caption">
                <h3>Thumbnail label</h3>
                <p>Lorem ipsum dolor sit amet, consectetur adipisicing elit. </p>
                <p><a href="#" class="btn btn-primary" role="button">Button</a> <a href="#" class="btn btn-default"
                                                                                   role="button">Button</a></p>
            </div>
        </div>
    </div>
    <div class="col-md-4 col-sm-6 item">
        <div class="thumbnail">
            <img src="${ctx}/res-build/img/avatar3_small.jpg" alt="">
            <div class="caption">
                <h3>Thumbnail label</h3>
                <p>Lorem ipsum dolor sit amet, consectetur adipisicing elit. </p>
                <p><a href="#" class="btn btn-primary" role="button">Button</a> <a href="#" class="btn btn-default"
                                                                                   role="button">Button</a></p>
            </div>
        </div>
    </div>
    <div class="col-md-4 col-sm-6 item">
        <div class="thumbnail">
            <img src="${ctx}/res-build/img/avatar3_small.jpg" alt="">
            <div class="caption">
                <h3>Thumbnail label</h3>
                <p>Lorem ipsum dolor sit amet, consectetur adipisicing elit. </p>
                <p><a href="#" class="btn btn-primary" role="button">Button</a> <a href="#" class="btn btn-default"
                                                                                   role="button">Button</a></p>
            </div>
        </div>
    </div>
    <div class="col-md-4 col-sm-6 item">
        <div class="thumbnail">
            <img src="${ctx}/res-build/img/avatar3_small.jpg" alt="">
            <div class="caption">
                <h3>Thumbnail label</h3>
                <p>Lorem ipsum dolor sit amet, consectetur adipisicing elit. </p>
                <p><a href="#" class="btn btn-primary" role="button">Button</a> <a href="#" class="btn btn-default"
                                                                                   role="button">Button</a></p>
            </div>
        </div>
    </div>
    <div class="col-md-4 col-sm-6 item">
        <div class="thumbnail">
            <img src="${ctx}/res-build/img/avatar3_small.jpg" alt="">
            <div class="caption">
                <h3>Thumbnail label</h3>
                <p>Lorem ipsum dolor sit amet, consectetur adipisicing elit. </p>
                <p><a href="#" class="btn btn-primary" role="button">Button</a> <a href="#" class="btn btn-default"
                                                                                   role="button">Button</a></p>
            </div>
        </div>
    </div>
    <div class="col-md-4 col-sm-6 item">
        <div class="thumbnail">
            <img src="${ctx}/res-build/img/avatar3_small.jpg" alt="">
            <div class="caption">
                <h3>Thumbnail label</h3>
                <p>Lorem ipsum dolor sit amet, consectetur adipisicing elit. </p>
                <p><a href="#" class="btn btn-primary" role="button">Button</a> <a href="#" class="btn btn-default"
                                                                                   role="button">Button</a></p>
            </div>
        </div>
    </div>
</div>
</body>

<content tag="jsconfig">
    <script type="text/javascript">
        var currentpage = ${currentpage};
    </script>
    <script type="text/javascript"
            src="${ctx}/res-build/res/province-city-area-selector/PCASClass.js"></script>
    <script type="text/javascript"
            src="${ctx}/res-build/res/echarts/jquery-ui-bootstrap/jquery-ui-1.10.3.custom.min.js"></script>
    <script type="text/javascript" src="${ctx}/res-build/config.js" data-init="users/index"></script>

</content>
