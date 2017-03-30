define(function (require, exports, module) {
    require("res-build/res/plugin/jquery-validation-1.13.1/dist/jquery.validate.min.js");
    require("res-build/res/plugin/bs-confirmation/bootstrap-confirmation.js");
    require("res-build/res/module/underscore/underscore.js");
    var tool = require("tool");
    var Page = require("page");
    var juicer = require("juicer");
    var pageIndex;

    var $table = $("#datatable_ajax");
    var $accountList = $("#account-list");
    var $PatientForm = $('#vPatientForm');
    var $ModifyForm = $("#vPatientModifyForm");
    var $modifyModal = $('#modifyModal');
    var $addModal = $('#addModal');
    var $addRoletipModal = $('#modal-box');
    var pagelength = 10; //一页多少条；
    $('body').tooltip({
        selector: '.has-tooltip'
    });


    var listTpl = juicer(
        [
            '{@if total === 0}',
            '<tr>',
            '<td colspan="6" style="text-align:center">',
            '暂无记录,请添加',
            '</td>',

            '</tr>',

            '{@else}',
            '{@each data as item,index}',
            '{@if index%2==0}',
            '<tr role="row" class="odd" data-name="${item.name}">',
            '<tr role="row" class="odd" data-type="${item.type}">',
            '<tr role="row" class="odd" data-sex="${item.sex}">',
            '<tr role="row" class="odd" data-phone="${item.phone}">',
            '<tr role="row" class="odd" data-createdate="${item.createdate}">',

            '{@else}',
            '<tr role="row" class="even" data-name="${item.name}">',
            '<tr role="row" class="even" data-type="${item.type}">',
            '<tr role="row" class="even" data-sex="${item.sex}">',
            '<tr role="row" class="even" data-phone="${item.phone}">',
            '<tr role="row" class="even" data-createdate="${item.createdate}">',
            '{@/if}',
            '    <td>${item.name}</td>',
            '{@if item.type==1}',
            '    <td>成人</td>',
            '{@/if}',
            '{@if item.type==2}',
            '<td>儿童</td>',
            '{@/if}',
            '{@if item.sex==1}',
            '    <td>男</td>',
            '{@/if}',
            '{@if item.sex==2}',
            '<td>女</td>',
            '{@/if}',
            '    <td>${item.phone}</td>',
            '    <td>${item.createdate}</td>',

            '    <td class=" heading">',

            ' <button type="button" class="btn btn-default btn-xs j-disable j-edit" data-toggle="modal" data-target="#modifyModal"  data-id="${item.id}"  data-name="${item.name}"  data-type="${item.type}" data-sex="${item.sex}" data-idtype="${item.idtype}"  data-idnumber="${item.idnumber}" data-phone="${item.phone}" data-guardianname="${item.guardianname}" data-guardianidtype="${item.guardianidtype}" data-guardianidnumber="${item.guardianidnumber}"  data-birthday="${item.birthday}"><span class="iconfont iconfont-xs">&#xe62d;</span>查看</button>',
            ' <a class="btn btn-default btn-xs"  href="' + ROOTPAth + '/admin/appointments/patientindex?pcode=1&subcode=2&patientid=${item.id}&patientname=${item.name}" ><span class="iconfont iconfont-xs">&#xe617;</span> 预约管理</a>',
            ' <a class="btn btn-default btn-xs"  href="' + ROOTPAth + '/admin/paymentdetails/hospitalChosenindex?pcode=1&subcode=2&patientid=${item.id}&patientname=${item.name}" ><span class="iconfont iconfont-xs">&#xe617;</span> 支付列表</a>',
            ' <button type="button" class="btn btn-danger btn-xs j-disable j-del" data-toggle="confirmation"  data-placement="top" data-id="${item.id}"><span class="iconfont iconfont-xs">&#xe618;</span>删除</button>',

            '    </td>',
            '</tr>',
            '{@/each}',
            '{@/if}'
        ].join(""));
    var Utilitiy = {
        init: function () {

            tool.startPageLoading();
            this.bind();
            var windowurl = window.location.href;
            var returnUrl = windowurl.indexOf("currentpage=");

            if (returnUrl == -1 || returnUrl == "") {
                pageIndex.resetgoto(1);
            } else {
                var returnUrl_val = parseInt(windowurl.substring(returnUrl + 12));
                if (returnUrl_val != 1) {
                    pageIndex.resetgoto(returnUrl_val);
                } else {
                    pageIndex.reset()
                }
            }
        },
        bind: function () {
            var self = this;
            //列表分页
            pageIndex = new Page({
                ajax: {
                    url: ROOTPAth + '/admin/patients/list',
                    type: 'POST',
                    dataType: 'json',
                    data: function () {
                        var data = {
                            uid:uid,
                            length: pagelength
                        };
                        return data;
                    },
                    success: function (res) {
                        var newData = $.extend({}, res);
                        $.each(newData.data, function (i, val) {

                            newData.data[i].currentpage = pageIndex.current;
                        });
                        tool.stopPageLoading();
                        $accountList.find(".page-info-num").text(res.data.length);

                        $table.find("tbody").empty().append(listTpl.render(newData));
                        //删除权限
                        $table.find(".j-del").confirmation({
                            title: "确定删除该帐号吗？",
                            btnOkLabel: "确定",
                            btnCancelLabel: "取消",
                            onConfirm: function (event, element) {
                                event.preventDefault();
                                self.deletePatient($(element));
                            }
                        });
                    }
                },
                pageName: "page",
                /*tpl: {
                 go: true //隐藏跳转到第几页
                 },*/
                getTotalPage: function (res) {
                    //返回总页数
                    return Math.ceil(res.total / pagelength);
                },
                pageWrapper: '.table-page'
            });
            pageIndex.reset();
            //分页，修改每页显示数据
            $accountList.on("change", ".j-length", function () {
                var $this = $(this);
                pagelength = $this.val();
                var index = $this.get(0).selectedIndex;
                $accountList.find(".j-length").not(this).get(0).selectedIndex = index;
                pageIndex.reset();
            });

            //我要编辑
            $ModifyForm.on("click", ".j-form-edit", function (event) {
                var formDom = event.delegateTarget;
                $(this).hide();
                $(formDom).find(".j-form-save").show();

                $(formDom).find("input").prop("disabled", false);
                $(formDom).find("select").prop("disabled", false);
                $ModifyForm.find("input[name=name]").prop("disabled", true);
                // $(event.relatedTarget)
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
            //修改表单初始化
            $modifyModal.on('show.bs.modal', function (event) {
                var $modal = $ModifyForm;
                $modal[0].reset();
                $modal.find("input").prop("disabled", true);
                $modal.find("select").prop("disabled", true);
                $modal.find("input").removeAttr("aria-describedby");
                $modal.find("input").removeAttr("aria-invalid");
                $modal.find("input").removeAttr("aria-required");
                $modal.find("select").removeAttr("aria-describedby");
                $modal.find("select").removeAttr("aria-invalid");
                $modal.find("select").removeAttr("aria-required");
                $modal.find("div").removeClass("has-error");
                $modal.find("span").remove();
                var button = $(event.relatedTarget); // Button that triggered the modal
                var id = button.data("id");
                var name = button.data("name");
                var sex = button.data("sex");
                var idtype = button.data("idtype");
                var idnumber = button.data("idnumber");
                var guardianidtype = button.data("guardianidtype");
                var guardianidnumber = button.data("guardianidnumber");
                var guardianname = button.data("guardianname");
                var phone = button.data("phone");
                var birthday = button.data("birthday");
                var type = button.data("type");
                $modal.find('input[name=id]').val(id);
                $modal.find('input[name=name]').val(name);
                $modal.find('input[name=type]').eq(type-1).attr("checked","checked");
                $modal.find('input[name=sex]').eq(sex-1).attr("checked","checked");
                $modal.find('select[name=idtype]').val(idtype)
                $modal.find('input[name=idnumber]').val(idnumber);
                $modal.find('input[name=guardianname]').val(guardianname);
                $modal.find('select[name=guardianidtype]').val(guardianidtype)
                $modal.find('input[name=guardianidnumber]').val(guardianidnumber);
                $modal.find('input[name=phone]').val(phone);
                $modal.find('input[name=birthday]').val(birthday);
                $modal.find(".j-form-save").hide();
                $modal.find(".j-form-edit").show();
            });
            //手机验证规则
            jQuery.validator.addMethod("ismobile", function (value, element) {
                var mobile = /^1[3|4|5|7|8]\d{9}$/;
                return this.optional(element) || (mobile.test(value));
            }, "手机格式不对");
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
                    type:"required",
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
                    type:"请选择就诊人类型",
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
                    var savePath = ROOTPAth + '/admin/patients/newPatient';


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
                                pageIndex.reset();
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

            //表单验证-修改就诊人
            $ModifyForm.validate({
                rules: {
                    // name: {
                    //     required: true,
                    //     // remote: { //自带远程验证存在的方法
                    //     //     url: ROOTPAth + '/admin/patients/sameName',
                    //     //     type: "POST",
                    //     //     dataType: "json",
                    //     //     data: {
                    //     //         uid: uid,
                    //     //         name: function () {
                    //     //             return $ModifyForm.find('input[name="name"]').val();
                    //     //         }
                    //     //     }
                    //     // }
                    // },
                    phone: {
                        required: true,
                        ismobile: true
                    },
                    sex: "required",
                    type:"required",
                    idtype: "required",
                    idnumber: "required",
                    guardianname: "required",
                    guardianidtype: "required",
                    guardianidnumber: "required",
                },
                messages: {
                    phone: {
                        required: "权限不能为空",
                        ismoblie: "手机格式不对"
                    },
                    // name: {
                    //     required: "就诊人姓名不能为空",
                    //     // remote: "就诊人姓名重复"
                    // },
                    sex: "请选择性别",
                    type:"请选择就诊人类型",
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
                    $ModifyForm.find("input").prop("disabled",false);

                    var updatePath = ROOTPAth + '/admin/patients/modPatient';
                    $.post(updatePath, $ModifyForm.serialize(), function (data) {
                        if (data.code === 1) {
                            $('#modifyModal').modal('hide');
                            $addRoletipModal.find(".dialogtip-msg").html("账号修改成功");
                            $addRoletipModal.modal('show');
                            pageIndex.reset();
                        }
                        else {
                            $("#ajax_fail").find("h4").html(data.message);
                            $("#ajax_fail").modal("show")
                        }
                    });

                }
            });

        },
        deletePatient: function ($that) {
            var id = $that.data("id");
            var delPath = ROOTPAth + '/admin/patients/delPatient/' + id;
            $.ajax({
                url: delPath,
                type: "POST",
                success: function (data) {
                    if (data.code === 1) {
                        pageIndex.reset();
                    } else {
                        $("#ajax_fail").find("h4").html(data.message);
                        $("#ajax_fail").modal("show")
                    }
                }
            });

        },
    };
    Utilitiy.init();
    $('#add_birthday').datepicker({

        dateFormat: "yy-mm-dd",
        selectOtherMonths: true,
        yearRange: "-100:+0",
        changeMonth: true,
        changeYear: true,
        inline: true
    });
    $('#mod_birthday').datepicker({

        dateFormat: "yy-mm-dd",
        selectOtherMonths: true,
        yearRange: "-100:+0",
        changeMonth: true,
        changeYear: true,
        inline: true
    });
});