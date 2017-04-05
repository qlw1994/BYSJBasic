define(function (require, exports, module) {
    require("res-build/res/plugin/jquery-validation-1.13.1/dist/jquery.validate.min.js");
    require("res-build/res/plugin/bs-confirmation/bootstrap-confirmation.js");
    require("res-build/res/module/underscore/underscore.js");
    var ecConfig = require("res-build/res/echarts/config.js");
    var tool = require("tool");
    var Page = require("page");
    var juicer = require("juicer");
    var pageIndex;
    var hideTime = 500;
    var $divdepartments = $("#departments");
    var $departmentname = $("#departmentname");
    var pagelength = 10; //一页多少条；
    var $searchform = $("#search-form");

    var listTpl = juicer(
        [
            '{@if total == 0}',
            '<center style="background-color: white"><h1>暂无记录</h1></center>',
            '{@else}',
            '{@each data as item,index}',
            '<div style="padding-top: 1%" class="col-md-2 col-sm-4 item">',
            '<div class="thumbnail">',
            // '<img class="img-thumbnail" src="${ctx}/res-build/img/avatar3_small.jpg" alt="${item.name}">',
            '<div class="caption">',
            '<h3>${item.name}</h3>',
            // '<textarea name="resume">${item.resume}</textarea>',
            '<p><a  href="' + ROOTPAth + '/user/doctors/doctorChosen?departmentid=${item.id}  &departmentname=${item.name}&type=2" class="btn btn-primary" role="button">普通</a>&nbsp;&nbsp;<a  href="' + ROOTPAth + '/user/doctors/doctorChosen?departmentid=${item.id}&departmentname=${item.name}&type=1" class="btn btn-primary" role="button">专家</a></p>',
            '</div></div></div>',
            '{@/each}',
            '{@/if}'
        ].join(""));
    var Utilitiy = {
        init: function () {
            tool.startPageLoading();
            fillDepartment(1);
            this.bind();

        },
        bind: function () {
            //页面到底部加载新数据
            $(window).scroll(function () {
                if ($(document).scrollTop() >= $(document).height() - $(window).height()) {
                    fillDepartmentLike(0);
                }
            });
            //点击查询
            $("#search").on('click', function (event) {
                event.preventDefault();
                tool.startPageLoading();
                fillDepartmentLike(1);
            });

            // 下拉框请求获取医院
            $departmentname.keyup(function (e) {
                if (e.keyCode != 40 && e.keyCode != 38) {
                    get_departmentLike($departmentname);
                }
            }).focus(function () {
                this.select();
            });
            //表单验证-查询医院
            $searchform.validate({
                rules: {},
                messages: {},
                errorElement: 'span', //default input error message container
                errorClass: 'help-block', // default input error message class
                focusInvalid: false, // do not focus the last invalid input
                highlight: function (element) { // hightlight error inputs
                    $(element)
                        .closest('.form-group').addClass('has-error'); // set error class to the control group
                },

                success: function (label) {
                    label.closest('.form-group').removeClass('has-error');
                    label.remove();
                },
                errorPlacement: function (error, element) {
                    error.insertAfter(element);
                },
                submitHandler: function () {
                    tool.startPageLoading();
                    fillDepartmentLike(1);
                }
            });

        }
    }


    function get_departmentLike(obj) {
        var t = setTimeout(function () {
            $.ajax({
                url: ROOTPAth + '/user/departments/listLike',
                type: 'POST',
                dataType: 'json',
                data: {
                    hospitalid: hospitalid,
                    name: $(obj).val()
                },
                success: function (data) {
                    data = data.data;
                    var $list = $("#departmentList");
                    $list.show();
                    $list.html("");
                    $.each(data, function (index, el) {
                        var html = $("<li data-id='" + el.id + "'>" + el.name + "</li>");
                        $list.append(html);

                    });
                    if ($list.html() == "") {
                        $list.hide();
                    }
                    $($list).find("li").hover(function () {

                        $(this).addClass("esultDivLiHover");
                    }, function () {
                        $(this).removeClass("esultDivLiHover");
                    });
                    $list.mouseleave(function () {
                        $list.hide();
                    });
                    $($list).find("li").click(function (event) {
                        $(obj).val($(this).text());
                        // $("#departmentid").val($(this).data("id"));
                        $list.hide();
                        // $searchform.validate().element($(obj));
                    });
                },
                error: function (err) {
                    $("#ajax_fail").find("h4").text("模块加载用户列表失败");
                    $("#ajax_fail").modal("show")
                }
            });
        }, 500);
    }

    function fillDepartment(reset) {
        if (reset == 1) {
            currentpage = 1;
        }
        $.ajax({
            url: ROOTPAth + '/user/departments/list',
            type: 'POST',
            dataType: 'json',
            data: {
                    hospitalid: hospitalid,
                    length: pagelength,
                    page: currentpage,

            },
            success: function (res) {
                console.log(res);
                tool.stopPageLoading();
                if (res.code == 1) {
                    var newData = $.extend({}, res);
                    $.each(newData.data, function (i, val) {
                        newData.data[i].currentpage = currentpage;
                    });
                    if (reset == 1) {
                        $divdepartments.empty();
                        $divdepartments.append(listTpl.render(newData));
                    }
                    else if(res.total<=pagelength||currentpage*1*pagelength>=res.total){
                        Toast("没有更多数据了", 2000);
                    }
                    else {
                        $divdepartments.append(listTpl.render(newData));
                        currentpage = currentpage * 1 + 1;
                    }
                }
            }
        });
    }

    function fillDepartmentLike(reset) {
        if (reset == 1) {
            currentpage = 1;
        }
        $.ajax({
            url: ROOTPAth + '/user/departments/listLike',
            type: 'POST',
            dataType: 'json',
            data: {
                    hospitalid: hospitalid,
                    length: pagelength,
                    page: currentpage,
                    name: $departmentname.val()

            },
            success: function (res) {
                console.log(res);
                tool.stopPageLoading();
                if (res.code == 1) {
                    var newData = $.extend({}, res);
                    $.each(newData.data, function (i, val) {
                        newData.data[i].currentpage = currentpage;
                    });
                    if (reset == 1) {
                        $divdepartments.empty();
                        $divdepartments.append(listTpl.render(newData));
                    }
                    else if(res.total<=pagelength||currentpage*1*pagelength>=res.total){
                        Toast("没有更多数据了", 2000);
                    }
                    else {
                        $divdepartments.append(listTpl.render(newData));
                        currentpage = currentpage * 1 + 1;
                    }
                }
            }
        });
    }
    //自定义弹框
    function Toast(msg, duration) {
        duration = isNaN(duration) ? 3000 : duration;
        var m = document.createElement('div');
        m.innerHTML = msg;
        m.style.cssText = "width:60%; min-width:150px; background:#000; opacity:0.5; height:40px; color:#fff; line-height:40px; text-align:center; border-radius:5px; position:fixed; top:80%; left:20%; z-index:999999; font-weight:bold;";
        document.body.appendChild(m);
        setTimeout(function () {
            var d = 0.5;
            m.style.webkitTransition = '-webkit-transform ' + d + 's ease-in, opacity ' + d + 's ease-in';
            m.style.opacity = '0';
            setTimeout(function () {
                document.body.removeChild(m)
            }, d * 1000);
        }, duration);
    }

    // function button_bind() {
    //     var indexPT = document.getElementsByName("indexPT");
    //     for (var i = 0; i < indexPT.length; i++) {
    //         $.each(indexPT, function (i, val) {
    //             // indexPT[i].attr("href", $(this).attr("href") + "$date=" + $("#appoint_date").val());
    //             indexPT[i].onClick(function () {
    //                 window.location.href = $(this).data("href") + "?date=" + $("#appoint_date").val() + "&departmentid=" + $(this).data("departmentid") + "&departmentname=" + $(this).data("departmentname");
    //             })
    //         })
    //     }
    //     var indexZJ = document.getElementsByName("indexZJ");
    //     for (var j = 0; j < indexZJ.length; j++) {
    //         $.each(indexZJ, function (i, val) {
    //             // indexZJ[i].attr("href", $(this).attr("href") + "$date=" + $("#appoint_date").val());
    //             indexZJ[i].onClick(function () {
    //                 window.location.href = $(this).data("href") + "?date=" + $("#appoint_date").val() + "&departmentid=" + $(this).data("departmentid") + "&departmentname=" + $(this).data("departmentname");
    //             })
    //         })
    //     }
    // }
    // //
    // $('#appoint_date').datepicker({
    //
    //     dateFormat: "yy-mm-dd",
    //     selectOtherMonths: true,
    //     changeMonth: true,
    //     changeYear: true,
    //     inline: true
    // });
    Utilitiy.init();
})
