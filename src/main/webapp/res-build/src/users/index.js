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
    var $divhospitals = $("#hospitals");
    var $table = $("#datatable_ajax");
    var $hospitalname = $("#hospitalname");
    var $addModal = $('#addModal');
    var $addRoletipModal = $('#modal-box');
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
            '<img class="img-thumbnail" src="${ctx}/res-build/img/avatar3_small.jpg" alt="${item.name}">',
            '<div class="caption">',
            '<h3>${item.name}</h3>',
            '<textarea name="resume">${item.resume}</textarea>',
            '<p><a href="' + ROOTPAth + '/user/departments/index?hospitalid=${item.id}&hospitalname=${item.name}" class="btn btn-primary" role="button">预约挂号</a></p>',
            '</div></div></div>',
            '{@/each}',
            '{@/if}'
        ].join(""));
    var Utilitiy = {
        init: function () {
            tool.startPageLoading();
            fillHospital(1);
            this.bind();

        },
        bind: function () {
            //页面到底部加载新数据
            $(window).scroll(function () {
                if ($(document).scrollTop() >= $(document).height() - $(window).height()) {
                    fillHospitalLike(0);
                }
            });
            //点击查询
            $("#search").on('click', function (event) {
                event.preventDefault();
                tool.startPageLoading();
                fillHospitalLike(1);
            });

            // 下拉框请求获取医院
            $hospitalname.keyup(function (e) {
                if (e.keyCode != 40 && e.keyCode != 38) {
                    get_hospitalLike($hospitalname);
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
                    fillHospitalLike(1);
                }
            });

        }
    }


    function get_hospitalLike(obj) {
        var t = setTimeout(function () {
            $.ajax({
                url: ROOTPAth + '/user/hospitals/listLike',
                type: 'POST',
                dataType: 'json',
                data: {
                    province: $("#province").val(),
                    city: $("#city").val(),
                    area: $("#area").val(),
                    name: $(obj).val()
                },
                success: function (data) {
                    data = data.data;
                    var $list = $("#hospitalList");
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
                        // $("#hospitalid").val($(this).data("id"));
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

    function fillHospital(reset) {
        if (reset == 1) {
            currentpage = 1;
        }
        $.ajax({
            url: ROOTPAth + '/user/hospitals/list',
            type: 'POST',
            dataType: 'json',
            data: function () {
                var data = {
                    province: $("#province").val(),
                    city: $("#city").val(),
                    area: $("#area").val(),
                    length: pagelength,
                    page: currentpage,
                    name: $hospitalname.val()
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
                    $divhospitals.append(listTpl.render(newData));
                    currentpage = currentpage * 1 + 1;
                }
            }
        });
    }

    function fillHospitalLike(reset) {
        if (reset == 1) {
            currentpage = 1;
        }
        $.ajax({
            url: ROOTPAth + '/user/hospitals/listLike',
            type: 'POST',
            dataType: 'json',
            data: function () {
                var data = {
                    province: $("#province").val(),
                    city: $("#city").val(),
                    area: $("#area").val(),
                    length: pagelength,
                    page: currentpage,
                    name: $hospitalname.val()
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
                        $divhospitals.empty();
                    }
                    $divhospitals.append(listTpl.render(newData));
                    currentpage = currentpage * 1 + 1;
                }
            }
        });
    }

    new PCAS("province", "city", "area");
    Utilitiy.init();
})
