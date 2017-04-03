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
            '<center><h1>暂无记录,请添加</h1></center>',
            '{@else}',
            '{@each data as item,index}',
            '<div class="col-md-4 col-sm-6 item">',
            '<div class="thumbnail">',
            '<img class="img-thumbnail" src="${ctx}/res-build/img/avatar3_small.jpg" alt="${item.name}">',
            '<div class="caption">',
            '<h3>${item.name}</h3>',
            '<p>${item.idnumber}</p>',
            // '<textarea name="resume">${item.resume}</textarea>',
            '<p><a  href="' + ROOTPAth + '/user/departments/index?patientid=${item.id}&patientname=${item.name}" class="btn btn-primary" role="button">预约科室</a><a  href="' + ROOTPAth + '/user/departments/index?patientid=${item.id}&patientname=${item.name}" class="btn btn-primary" role="button">检查报告</a><a href="' + ROOTPAth + '/user/departments/index?patientid=${item.id}&patientname=${item.name}" class="btn btn-primary" role="button">检验报告</a><a  href="' + ROOTPAth + '/user/drugorders/index?patientid=${item.id}&patientname=${item.name}" class="btn btn-primary" role="button">药品订单</a></p>',
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
            //页面到底部加载新数据
            $(window).scroll(function () {
                if ($(document).scrollTop() >= $(document).height() - $(window).height()) {
                    fillPatientLike(0);
                }
            });
            //点击查询
            $("#search").on('click', function (event) {
                event.preventDefault();
                tool.startPageLoading();
                fillPatientLike(1);
            });
            //添加表单初始化
            $addModal.on('show.bs.modal', function (event) {
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
                    fillPatientLike(1);
                }
            });
            //表单验证-添加就诊人
            $PatientForm.validate({
                rules: {
                    name: {
                        required: true,
                        // remote: { //自带远程验证存在的方法
                        //     url: ROOTPAth + '/admin/patients/sameName',
                        //     type: "POST",
                        //     dataType: "json",
                        //     data: {
                        //         uid: uid,
                        //         name: function () {
                        //             return $PatientForm.find('input[name="name"]').val();
                        //         }
                        //     }
                        // }
                    },
                    phone: {
                        required: true,
                        ismobile: true
                    },
                    sex: "required",
                    type: "required",
                    idtype: "required",
                    idnumber: "required",
                    guardianname: "required",
                    guardianidtype: "required",
                    guardianidnumber: "required",
                },
                messages: {
                    phone: {
                        required: "手机不能为空",
                        ismoblie: "手机格式不对"
                    },
                    name: {
                        required: "就诊人姓名不能为空",
                        // remote: "就诊人姓名重复"
                    },
                    sex: "请选择性别",
                    type: "请选择就诊人类型",
                    idnumber: "请输入证件号码",
                    idtype: "请选择证件类型",
                    guardianname: "监护人姓名不能为空",
                    guardianidtype: "请选择监护人证件类型",
                    guardianidnumber: "请输入监护人证件号码",

                },
                errorElement: 'span', //default input error message container
                errorClass: 'help-block', // default input error message class
                focusInvalid: false, // do not focus the last invalid input


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
                                $addRoletipModal.find(".dialogtip-msg").html("账号添加成功");
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
                url: ROOTPAth + '/user/patients/listNameLikeByDepartment',
                type: 'POST',
                dataType: 'json',
                data: {
                    departmentid: departmentid,
                    type: type,
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
            data: function () {
                var data = {
                    departmentid: departmentid,
                    type: type,
                    length: pagelength,
                    page: currentpage,
                    name: $patientname.val()
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
                    $divpatients.append(listTpl.render(newData));
                    // button_bind();
                    currentpage = currentpage * 1 + 1;
                }
            }
        });
    }

    function fillPatientLike(reset) {
        if (reset == 1) {
            currentpage = 1;
        }
        $.ajax({
            url: ROOTPAth + '/user/patients/listNameLikeByDepartment',
            type: 'POST',
            dataType: 'json',
            data: function () {
                var data = {
                    departmentid: departmentid,
                    length: pagelength,
                    type: type,
                    page: currentpage,
                    name: $patientname.val()
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
                        $divpatients.empty();
                    }
                    $divpatients.append(listTpl.render(newData));
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
    //                 window.location.href = $(this).data("href") + "?date=" + $("#appoint_date").val() + "&patientid=" + $(this).data("patientid") + "&patientname=" + $(this).data("patientname");
    //             })
    //         })
    //     }
    //     var indexZJ = document.getElementsByName("indexZJ");
    //     for (var j = 0; j < indexZJ.length; j++) {
    //         $.each(indexZJ, function (i, val) {
    //             // indexZJ[i].attr("href", $(this).attr("href") + "$date=" + $("#appoint_date").val());
    //             indexZJ[i].onClick(function () {
    //                 window.location.href = $(this).data("href") + "?date=" + $("#appoint_date").val() + "&patientid=" + $(this).data("patientid") + "&patientname=" + $(this).data("patientname");
    //             })
    //         })
    //     }
    // }
    // //
    // $('#appoint_date').datepicker({
    //
    //     dateFormat: "yy-mm-dd",
    //     selectOtherMonths: true,
    //     yearRange: "-100:+0",
    //     changeMonth: true,
    //     changeYear: true,
    //     inline: true
    // });
    Utilitiy.init();
})
