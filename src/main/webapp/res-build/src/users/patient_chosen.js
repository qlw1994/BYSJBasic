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
    var $divpatients = $("#patients");
    var $patientname = $("#patientname");
    var pagelength = 10; //一页多少条；
    var $searchform = $("#search-form");
    var $addModal = $('#addModal');
    var $addRoletipModal = $('#modal-box');
    var $PatientForm = $('#vPatientForm');
    var listTpl = juicer(
        [
            '{@if total == 0}',
            '<center style="background-color: white"><h1>暂无记录</h1></center>',
            '{@else}',
            '{@each data as item,index}',
            '<div class="col-md-4 col-sm-6 item">',
            '<div class="thumbnail">',
            '<img  class="img-thumbnail"  style="width: 140px;height: 140px" src="${ctx}/res-build/img/user.jpg" alt="${item.name}">',
            '<div class="caption">',
            '<h3>${item.name}</h3>',
            '<p>证件号码：&nbsp;${item.idnumber}</p>',
            // '<textarea name="resume">${item.resume}</textarea>',
            '<p><a  href="' + ROOTPAth + '/user/departments/index?patientid=${item.id}&patientname=${item.name}" class="btn btn-primary col-md-5" role="button">预约科室</a><a  href="' + ROOTPAth + '/user/drugorders/index?patientid=${item.id}&patientname=${item.name}" class="btn btn-primary col-md-5" role="button">药品订单</a></p>' +
            '<a href="' + ROOTPAth + '/user/inspectionreports/index?patientid=${item.id}&patientname=${item.name}" class="btn btn-primary col-md-5" role="button">检验报告</a><a  href="' + ROOTPAth + '/user/checkreports/index?patientid=${item.id}&patientname=${item.name}" class="btn btn-primary col-md-5" role="button">检查报告</a></p>' +
            '<p><a href="' + ROOTPAth + '/user/paymentdetails/index?patientid=${item.id}&patientname=${item.name}" class="btn btn-primary col-md-5" role="button">待支付列表</a>&nbsp;<a  href="' + ROOTPAth + '/user/paymentdetails/indexpayed?patientid=${item.id}&patientname=${item.name}" class="btn btn-primary col-md-5" role="button">已支付列表</a></p>',
            '<p><a href="' + ROOTPAth + '/user/appointments/patientindex?patientid=${item.id}&patientname=${item.name}" class="btn btn-primary col-md-5" role="button">历史预约记录</a>&nbsp;<a href="' + ROOTPAth + '/user/departmentqueues/index?patientid=${item.id}&patientname=${item.name}" class="btn btn-primary col-md-5" role="button">队列当前就诊人序号</a> </p>',
            '<p><a href="' + ROOTPAth + '/user/hospitalizations/index?patientid=${item.id}&patientname=${item.name}" class="btn btn-primary col-md-5" role="button">住院记录</a></p>',
            '</div></div></div>',
            '{@/each}',
            '{@/if}'
        ].join(""));
    var Utilitiy = {
        init: function () {
            tool.startPageLoading();
            fillPatient(1);
            this.bind();

        },
        bind: function () {
            // //页面到底部加载新数据
            // $(window).scroll(function () {
            //     if ($(document).scrollTop() >= $(document).height() - $(window).height()) {
            //         fillPatientLike(0);
            //     }
            // });
            //点击查询
            $("#search").on('click', function (event) {
                event.preventDefault();
                tool.startPageLoading();
                fillPatientLike(1);
            });
            //添加表单初始化
            $addModal.on('show.modal', function (event) {
                $PatientForm[0].reset();
                $PatientForm.find("input").removeAttr("aria-describedby");
                $PatientForm.find("input").removeAttr("aria-invalid");
                $PatientForm.find("input").removeAttr("aria-required");
                $PatientForm.find("select").removeAttr("aria-describedby");
                $PatientForm.find("select").removeAttr("aria-invalid");
                $PatientForm.find("select").removeAttr("aria-required");
                $PatientForm.find("div").removeClass("has-error");
                $PatientForm.find("span").remove();
            })
            // 下拉框请求获取医院
            $patientname.keyup(function (e) {
                if (e.keyCode != 40 && e.keyCode != 38) {
                    get_patientLike($patientname);
                }
            }).focus(function () {
                this.select();
            });
            //手机验证规则
            jQuery.validator.addMethod("ismobile", function (value, element) {
                var mobile = /^1[3|4|5|7|8]\d{9}$/;
                return this.optional(element) || (mobile.test(value));
            }, "手机格式不对");
            //表单验证-查询医院
            $searchform.validate({
                rules: {},
                messages: {},
                errorElement: 'span', //default input error message container
                errorClass: 'help-block', // default input error message class
                //focusInvalid: false, // do not focus the last invalid input
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
                    fillPatientLike(1);
                }
            });
            //表单验证-添加就诊人
            $PatientForm.validate({
                rules: {
                    name: {
                        required: true,
                    },
                    phone: {
                        required: true,
                        ismobile: true
                    },
                    sex: "required",
                    type: "required",
                    idtype: "required",
                    idnumber: "required",
                    // guardianname: "required",
                    // guardianidtype: "required",
                    // guardianidnumber: "required",
                },
                messages: {
                    phone: {
                        required: "手机不能为空",
                        ismoblie: "手机格式不对"
                    },
                    name: {
                        required: "就诊人姓名不能为空",
                    },
                    sex: "请选择性别",
                    type: "请选择就诊人类型",
                    idnumber: "请输入证件号码",
                    idtype: "请选择证件类型",
                    // guardianname: "监护人姓名不能为空",
                    // guardianidtype: "请选择监护人证件类型",
                    // guardianidnumber: "请输入监护人证件号码",

                },
                errorElement: 'span', //default input error message container
                errorClass: 'help-block', // default input error message class
                //focusInvalid: false, // do not focus the last invalid input


                invalidHandler: function (event, validator) { //display error alert on form submit
                    //	                $('.alert-danger', $('.login-form')).show();
                },
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
                    var savePath = ROOTPAth + '/user/patients/newPatient';


                    $.ajax({
                        type: "POST",
                        url: savePath,
                        data: $PatientForm.serialize(),
                        beforeSend: function () {
                            tool.startPageLoading();
                        },
                        dataType: "json",
                        success: function (data) {
                            console.log(data);
                            tool.stopPageLoading();
                            if (data.code == 1) {
                                $addModal.modal("hide");
                                $addRoletipModal.find(".dialogtip-msg").html("就诊人添加成功");
                                $addRoletipModal.modal('show');
                                fillPatient(1);
                            }
                            else {
                                $("#ajax_fail").find("h4").html(data.message);
                                $("#ajax_fail").modal("show")
                            }

                        },

                        error: function () {
                            tool.stopPageLoading();
                            $("#ajax_fail").modal("show")
                        },
                    });

                }
            });


        }
    }


    function get_patientLike(obj) {
        var t = setTimeout(function () {
            $.ajax({
                url: ROOTPAth + '/user/patients/listPatientLike',
                type: 'POST',
                dataType: 'json',
                data: {
                    uid: uid,
                    name: $(obj).val()
                },
                success: function (data) {
                    data = data.data;
                    var $list = $("#patientList");
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
                        // $("#patientid").val($(this).data("id"));
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

    function fillPatient(reset) {
        if (reset == 1) {
            currentpage = 1;
        }
        $.ajax({
            url: ROOTPAth + '/user/patients/list',
            type: 'POST',
            dataType: 'json',
            data: {
                uid: uid,
                // length: pagelength,
                // page: currentpage,
                name: $patientname.val()

            },
            success: function (res) {
                console.log(res);
                tool.stopPageLoading();
                if (res.code == 1) {
                    var newData = $.extend({}, res);
                    $.each(newData.data, function (i, val) {
                        newData.data[i].currentpage = currentpage;
                    });
                    if (res.code == 1) {
                        var newData = $.extend({}, res);
                        $.each(newData.data, function (i, val) {
                            newData.data[i].currentpage = currentpage;
                        });
                        $divpatients.empty();
                        $divpatients.append(listTpl.render(newData));
                        // if (reset == 1) {
                        //     $divpatients.empty();
                        //     $divpatients.append(listTpl.render(newData));
                        // }
                        // else if (res.total <= pagelength || currentpage * 1 * pagelength >= res.total) {
                        //     Toast("没有更多数据了", 2000);
                        // }
                        // else {
                        //     $divpatients.append(listTpl.render(newData));
                        //     currentpage = currentpage * 1 + 1;
                        // }
                    }
                }
            }
        });
    }

    function fillPatientLike(reset) {
        if (reset == 1) {
            currentpage = 1;
        }
        $.ajax({
            url: ROOTPAth + '/user/patients/listPatientLike',
            type: 'POST',
            dataType: 'json',
            data: {
                uid: uid,
                // length: pagelength,
                // page: currentpage,
                name: $patientname.val()
            },
            success: function (res) {
                console.log(res);
                tool.stopPageLoading();
                if (res.code == 1) {
                    var newData = $.extend({}, res);
                    $.each(newData.data, function (i, val) {
                        newData.data[i].currentpage = currentpage;
                    });
                    if (res.code == 1) {
                        var newData = $.extend({}, res);
                        $.each(newData.data, function (i, val) {
                            newData.data[i].currentpage = currentpage;
                        });
                        $divpatients.empty();
                        $divpatients.append(listTpl.render(newData));
                        // if (reset == 1) {
                        //     $divpatients.empty();
                        //     $divpatients.append(listTpl.render(newData));
                        // }
                        // else if (res.total <= pagelength || currentpage * 1 * pagelength >= res.total) {
                        //     Toast("没有更多数据了", 2000);
                        // }
                        // else {
                        //     $divpatients.append(listTpl.render(newData));
                        //     currentpage = currentpage * 1 + 1;
                        // }
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

    $('#add_birthday').datepicker({

        dateFormat: "yy-mm-dd",
        selectOtherMonths: true,
        changeMonth: true,
        changeYear: true,
        inline: true
    });
    Utilitiy.init();
})
