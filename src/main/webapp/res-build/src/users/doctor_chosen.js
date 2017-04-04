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
    var $divdoctors = $("#doctors");
    var $doctorname = $("#doctorname");
    var pagelength = 10; //一页多少条；
    var $searchform = $("#search-form");

    var listTpl = juicer(
        [
            '{@if total == 0}',
            '<center><h1>暂无记录,请添加</h1></center>',
            '{@else}',
            '{@each data as item,index}',
            '<div class="col-md-4 col-sm-6 item">',
            '<div class="thumbnail">',
            '<img class="img-thumbnail" width="140px" height="140px" src="${ctx}/res-build/img/avatar3_small.jpg" alt="${item.name}">',
            '<div class="caption">',
            '<h3>${item.name}</h3>',
            // '<textarea name="resume">${item.resume}</textarea>',
            '<p><a name="indexPT" href="' + ROOTPAth + '/user/schedulings/index?doctorid=${item.id}&doctorname=${item.name}" class="btn btn-primary" role="button">预约</a></p>',
            '</div></div></div>',
            '{@/each}',
            '{@/if}'
        ].join(""));
    var Utilitiy = {
        init: function () {
            tool.startPageLoading();
            fillDoctor(1);
            this.bind();

        },
        bind: function () {
            //页面到底部加载新数据
            $(window).scroll(function () {
                if ($(document).scrollTop() >= $(document).height() - $(window).height()) {
                    fillDoctorLike(0);
                }
            });
            //点击查询
            $("#search").on('click', function (event) {
                event.preventDefault();
                tool.startPageLoading();
                fillDoctorLike(1);
            });

            // 下拉框请求获取医院
            $doctorname.keyup(function (e) {
                if (e.keyCode != 40 && e.keyCode != 38) {
                    get_doctorLike($doctorname);
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
                    fillDoctorLike(1);
                }
            });

        }
    }


    function get_doctorLike(obj) {
        var t = setTimeout(function () {
            $.ajax({
                url: ROOTPAth + '/user/doctors/listNameLikeByDepartment',
                type: 'POST',
                dataType: 'json',
                data: {
                    departmentid: departmentid,
                    type:type,
                    name: $(obj).val()
                },
                success: function (data) {
                    data = data.data;
                    var $list = $("#doctorList");
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
                        // $("#doctorid").val($(this).data("id"));
                        $list.hide();
                        // $searchform.validate().element($(obj));
                    });
                }

                ,
                error: function (err) {
                    $("#ajax_fail").find("h4").text("模块加载用户列表失败");
                    $("#ajax_fail").modal("show")
                }
            })
            ;
        }, 500);
    }

    function fillDoctor(reset) {
        if (reset == 1) {
            currentpage = 1;
        }
        $.ajax({
            url: ROOTPAth + '/user/doctors/list',
            type: 'POST',
            dataType: 'json',
            data: function () {
                var data = {
                    departmentid: departmentid,
                    type:type,
                    length: pagelength,
                    page: currentpage,
                    name: $doctorname.val()
                };
                return data;
            },
            success: function (res) {
                console.log(res);
                tool.stopPageLoading();
                if (res.code == 1) {
                    var newData = $.extend({}, res);
                    $.each(newData.data, function (i, val) {
                        newData.data[i].currentpage = currentpage;
                    });
                    $divdoctors.append(listTpl.render(newData));
                    // button_bind();
                    currentpage = currentpage * 1 + 1;
                }
            }
        });
    }

    function fillDoctorLike(reset) {
        if (reset == 1) {
            currentpage = 1;
        }
        $.ajax({
            url: ROOTPAth + '/user/doctors/listNameLikeByDepartment',
            type: 'POST',
            dataType: 'json',
            data: function () {
                var data = {
                    departmentid: departmentid,
                    length: pagelength,
                    type:type,
                    page: currentpage,
                    name: $doctorname.val()
                };
                return data;
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
                        $divdoctors.empty();
                    }
                    $divdoctors.append(listTpl.render(newData));
                    // button_bind();
                    currentpage = currentpage * 1 + 1;
                }
            }
        });
    }


    // function button_bind() {
    //     var indexPT = document.getElementsByName("indexPT");
    //     for (var i = 0; i < indexPT.length; i++) {
    //         $.each(indexPT, function (i, val) {
    //             // indexPT[i].attr("href", $(this).attr("href") + "$date=" + $("#appoint_date").val());
    //             indexPT[i].onClick(function () {
    //                 window.location.href = $(this).data("href") + "?date=" + $("#appoint_date").val() + "&doctorid=" + $(this).data("doctorid") + "&doctorname=" + $(this).data("doctorname");
    //             })
    //         })
    //     }
    //     var indexZJ = document.getElementsByName("indexZJ");
    //     for (var j = 0; j < indexZJ.length; j++) {
    //         $.each(indexZJ, function (i, val) {
    //             // indexZJ[i].attr("href", $(this).attr("href") + "$date=" + $("#appoint_date").val());
    //             indexZJ[i].onClick(function () {
    //                 window.location.href = $(this).data("href") + "?date=" + $("#appoint_date").val() + "&doctorid=" + $(this).data("doctorid") + "&doctorname=" + $(this).data("doctorname");
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
