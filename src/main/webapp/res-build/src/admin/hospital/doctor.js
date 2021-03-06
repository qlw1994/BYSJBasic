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
    var $DoctorForm = $("#vDoctorForm");
    var $ModifyForm = $("#vDoctorModifyForm");
    var $UploadForm = $("#vUploadDoctorForm");
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
            '<td colspan="7" style="text-align:center">',
            '暂无记录,请添加',
            '</td>',

            '</tr>',

            '{@else}',
            '{@each data as item,index}',
            '{@if index%2==0}',
            '<tr role="row" class="odd" data-name="${item.name}">',
            '<tr role="row" class="odd" data-account="${item.account}">',
            '<tr role="row" class="odd" data-sex="${item.sex}">',
            '<tr role="row" class="odd" data-level="${item.level}">',
            '<tr role="row" class="odd" data-job="${item.job}">',

            '{@else}',
            '<tr role="row" class="even" data-name="${item.name}">',
            '<tr role="row" class="even" data-account="${item.account}">',
            '<tr role="row" class="even" data-sex="${item.sex}">',
            '<tr role="row" class="even" data-level="${item.level}">',
            '<tr role="row" class="even" data-job="${item.job}">',

            '{@/if}',
            '    <td>${item.name}</td>',
            '    <td>${item.account}</td>',
            '{@if item.sex==1}',
            '    <td>男</td>',
            '{@/if}',
            '{@if item.sex==2}',
            '<td>女</td>',
            '{@/if}',
            '    <td>${item.department.name}</td>',
            '{@if item.level==1}',
            '    <td>专家</td>',
            '{@/if}',
            '{@if item.level==2}',
            '<td>普通</td>',
            '{@/if}',
            '    <td>${item.job}</td>',

            '    <td class=" heading">',

            ' <button type="button" class="btn btn-default btn-xs j-disable j-edit" data-toggle="modal" data-target="#modifyModal"  data-id="${item.id}"  data-name="${item.name}"  data-account="${item.account}" data-sex="${item.sex}" data-idtype="${item.idtype}"  data-idnumber="${item.idnumber}" data-departmentid="${item.departmentid}" data-hospitalid="${item.hospitalid}" data-job="${item.job}" data-age="${item.age}" data-level="${item.level}"  data-resume="${item.resume}"><span class="iconfont iconfont-xs">&#xe62d;</span>查看</button>',
            ' <button  type="button" class="btn btn-danger btn-xs j-disable resetPWD" data-toggle="confirmation"  data-placement="top" data-id="${item.id}"><span class="iconfont iconfont-xs">&#xe604;</span>重置密码</button>',
            ' <button type="button" class="btn btn-danger btn-xs j-disable j-del" data-toggle="confirmation"  data-placement="top" data-id="${item.id}"><span class="iconfont iconfont-xs">&#xe618;</span>删除</button>',
            '<br/>',
            ' <a class="btn btn-default btn-xs"  href="' + ROOTPAth + '/admin/fixedschedulings/index?pcode=2&subcode=1&doctorid=${item.id}&doctorname=${item.name}" ><span class="iconfont iconfont-xs">&#xe617;</span>排班管理</a>',
            ' <button type="button"  class="btn btn-default btn-xs j-gen" data-id="${item.id}" data-type="${item.level}" data-hospitalid="${item.hospitalid}" data-departmentid="${item.departmentid}"><span class="iconfont iconfont-xs">&#xe617;</span>生成号源</button>',
            '<br/>',
            ' <a class="btn btn-default btn-xs"  href="' + ROOTPAth + '/admin/schedulings/index?pcode=2&subcode=1&doctorid=${item.id}&doctorname=${item.name}" ><span class="iconfont iconfont-xs">&#xe617;</span>预约挂号</a>',
            ' <a class="btn btn-default btn-xs"  href="' + ROOTPAth + '/admin/appointments/doctorindex?pcode=2&subcode=1&doctorid=${item.id}&doctorname=${item.name}" ><span class="iconfont iconfont-xs">&#xe617;</span>预约查询</a>',
            '<br/>',
            ' <a class="btn btn-default btn-xs"  href="' + ROOTPAth + '/admin/drugorders/patientChosen?pcode=2&subcode=1&doctorid=${item.id}&doctorname=${item.name}&service=drugorders/index" ><span class="iconfont iconfont-xs">&#xe617;</span>药品订单管理</a>',
            ' <a class="btn btn-default btn-xs"  href="' + ROOTPAth + '/admin/checkreports/patientChosen?pcode=2&subcode=1&doctorid=${item.id}&doctorname=${item.name}&service=checkreports/index"><span class="iconfont iconfont-xs">&#xe617;</span>检查表管理</a>',
            ' <a class="btn btn-default btn-xs"  href="' + ROOTPAth + '/admin/inspectionreports/patientChosen?pcode=2&subcode=1&doctorid=${item.id}&doctorname=${item.name}&service=inspectionreports/index" ><span class="iconfont iconfont-xs">&#xe617;</span>检验表管理</a>',



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
                    url: ROOTPAth + '/admin/hospitalDoctors/list',
                    type: 'POST',
                    dataType: 'json',
                    data: {
                        departmentid: departmentid,
                        length: pagelength

                    },
                    success: function (res) {
                        var newData = $.extend({}, res);
                        $.each(newData.data, function (i, val) {

                            newData.data[i].currentpage = pageIndex.current;
                        });
                        tool.stopPageLoading();
                        $accountList.find(".page-info-num").text(res.data.length);
                        newData = eval(newData);
                        $table.find("tbody").empty().append(listTpl.render(newData));
                        //删除权限
                        $table.find(".j-del").confirmation({
                            title: "确定删除该帐号吗？",
                            btnOkLabel: "确定",
                            btnCancelLabel: "取消",
                            onConfirm: function (event, element) {
                                event.preventDefault();
                                self.deleteDoctor($(element));
                            }

                        });
                        $table.find(".j-gen").confirmation({
                            title: "确定生成号源吗？",
                            btnOkLabel: "确定",
                            btnCancelLabel: "取消",
                            onConfirm: function (event, element) {
                                event.preventDefault();
                                self.generateScheduling($(element));
                            }

                        });
                        $table.find("resetPWD").confirmation({
                            title: "确定重置密码吗？",
                            btnOkLabel: "确定",
                            btnCancelLabel: "取消",
                            onConfirm: function (event, element) {
                                event.preventDefault();
                                self.resetPWD($(element));
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
            //修改界面关闭,下拉框消失
            $modifyModal.on("hide.modal", function (event) {
                $modifyModal.find(".list").hide();
            });
            //我要编辑
            $ModifyForm.on("click", ".j-form-edit", function (event) {
                var formDom = event.delegateTarget;
                $(this).hide();
                $(formDom).find(".j-form-save").show();

                $(formDom).find("input").prop("disabled", false);
                $ModifyForm.find("input[name=hospitalname]").prop("disabled", true)
                $ModifyForm.find("input[name=account]").prop("disabled", true)
                $ModifyForm.find("input[name=name]").prop("disabled", true);
                $ModifyForm.find("input[name=departmentname]").prop("disabled", true);
            });
            //添加表单账号变更就进行表单验证
            $("#add_account").bind("input onpropertychange", function () {
                $DoctorForm.validate().element($("#add_account"));
            });
            //审核人下拉框绑定
            $("#add_auditor").keyup(function (e) {
                if (e.keyCode != 40 && e.keyCode != 38) {
                    get_auditors_modal($("#add_auditor"), 1);
                }
            }).focus(function () {
                this.select();
            });
            //批量上传初始化
            $UploadForm.on('show.modal',function () {
                $UploadForm[0].reset();
                $UploadForm.find("input").removeAttr("aria-describedby");
                $UploadForm.find("input").removeAttr("aria-invalid");
                $UploadForm.find("input").removeAttr("aria-required");
                $UploadForm.find("div").removeClass("has-error");
                $UploadForm.find("span").remove();
               $("#uploadfile").val("");
            })
            //添加表单初始化
            $addModal.on('show.modal', function (event) {
                $DoctorForm[0].reset();
                $DoctorForm.find("input").removeAttr("aria-describedby");
                $DoctorForm.find("input").removeAttr("aria-invalid");
                $DoctorForm.find("input").removeAttr("aria-required");
                $DoctorForm.find("textarea").removeAttr("aria-describedby");
                $DoctorForm.find("textarea").removeAttr("aria-invalid");
                $DoctorForm.find("textarea").removeAttr("aria-required");
                $DoctorForm.find("select").removeAttr("aria-describedby");
                $DoctorForm.find("select").removeAttr("aria-invalid");
                $DoctorForm.find("select").removeAttr("aria-required");
                $DoctorForm.find("div").removeClass("has-error");
                $DoctorForm.find("span").remove();
                // $("#add_name").prop("disabled", true);
            })
            //修改表单初始化
            $modifyModal.on('show.modal', function (event) {
                var $modal = $ModifyForm;
                $($modifyModal).find("input").prop("disabled", true);
                $($modifyModal).find("select").prop("disabled", true);
                $($modifyModal).find("textarea").prop("disabled", true);
                $modal.find("input").removeAttr("aria-describedby");
                $modal.find("input").removeAttr("aria-invalid");
                $modal.find("input").removeAttr("aria-required");
                $modal.find("textarea").removeAttr("aria-describedby");
                $modal.find("textarea").removeAttr("aria-invalid");
                $modal.find("textarea").removeAttr("aria-required");
                $modal.find("select").removeAttr("aria-describedby");
                $modal.find("select").removeAttr("aria-invalid");
                $modal.find("select").removeAttr("aria-required");
                $modal.find("div").removeClass("has-error");
                $modal.find("span").remove();
                var button = $(event.relatedTarget); // Button that triggered the modal
                var id = button.data("id");
                var account = button.data("account");
                var name = button.data("name");
                var sex = button.data("sex");
                var idtype = button.data("idtype");
                var idnumber = button.data("idnumber");
                var age = button.data("age");
                var job = button.data("job");
                var level = button.data("level");
                var resume = button.data("resume");
                var departmentid = button.data("departmentid");
                var hospitalid = button.data("hospitalid")

                $modal.find('input[name=id]').val(id);
                $modal.find('input[name=account]').val(account);
                $modal.find('input[name=name]').val(name);
                $modal.find('input[name=sex]').eq(sex - 1).attr("checked", "checked");
                $modal.find('select[name=idtype]').val(idtype)
                $modal.find('input[name=idnumber]').val(idnumber);
                $modal.find('select[name=level]').val(level)
                $modal.find('input[name=age]').val(age);
                $modal.find('input[name=job]').val(job);
                $modal.find('textarea[name=resume]').val(resume);

                $modal.find(".j-form-save").hide();
                $modal.find(".j-form-edit").show();
            });
            //修改表单中科室列表
            $ModifyForm.find("input[name=departmentname]").keyup(function (e) {
                if (e.keyCode != 40 && e.keyCode != 38) {
                    get_department_modModel($ModifyForm.find("input[name=departmentname]"));
                }
            }).focus(function () {
                this.select();
            });
            //自定义验证大于0方法
            $.validator.addMethod("isPositive", function (value, element) {
                var score = /^[0-9]*$/;
                return score.test(value)&&value!=0;
            }, $.validator.format("请输入正确的年龄!"));
            jQuery.validator.addMethod("checkCsv", function(value, element) {
                var filepath=$("#uploadfile").val();
                //获得上传文件名
                var fileArr=filepath.split("\\");
                var fileTArr=fileArr[fileArr.length-1].toLowerCase().split(".");
                var filetype=fileTArr[fileTArr.length-1];
                //切割出后缀文件名
                if(filetype != "csv"){
                    return false;
                }else{
                    return true;
                }
            }, "上传文件格式不适合");
            $UploadForm.validate({
                rules:{
                    file:{
                        required:true,
                        checkCsv:true
                    }
                },
                messages:{
                    file:{
                        required:"请选择文件"
                    }
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
                    var savePath = ROOTPAth + '/admin/hospitalDoctors/upload';

                    var formData = new FormData($($UploadForm)[0]);
                    $.ajax({
                        url: savePath,
                        type: "POST",
                        dataType: "json",
                        data: formData,
                        beforeSend: function () {
                            tool.startPageLoading();
                        },


                        cache: false,
                        contentType: false,
                        processData: false,
                        success: function (data) {
                            console.log(data);
                            tool.stopPageLoading();
                            if (data.code == 1) {
                                $addModal.modal("hide");
                                $addRoletipModal.find(".dialogtip-msg").html("账号添加成功");
                                $addRoletipModal.modal('show');
                            }
                            else {
                                $("#ajax_fail").find("h4").html(data.message);
                                $("#ajax_fail").modal("show")
                            }
                            pageIndex.reset();
                        },

                        error: function () {
                            tool.stopPageLoading();
                            $("#ajax_fail").modal("show")
                        },
                    });

                }
            })

            //表单验证-添加用户
            $DoctorForm.validate({
                rules: {
                    account: {
                        required: true,
                        remote: { //自带远程验证存在的方法
                            url: ROOTPAth + '/admin/hospitalDoctors/sameName',
                            type: "POST",
                            dataType: "json",
                            data: {
                                hospitalid: function () {
                                    return $DoctorForm.find('input[name="hospitalid"]').val();
                                },
                                account: function () {
                                    return $DoctorForm.find('input[name="account"]').val();
                                }
                            }
                        }
                    },
                    name: "required",
                    password: "required",
                    password_again: {
                        equalTo: "#add_password"
                    },
                    sex: "required",
                    idtype: "required",
                    idnumber: "required",
                    age: {
                        required: true,
                        isPositive: true
                    },
                    job: "required",
                    resume: "required",
                    level: "required",


                },
                messages: {
                    name: "名称不能为空",
                    account: {
                        required: "用户名不能为空",
                        remote: "用户名重复"
                    },
                    sex: "请选择性别",
                    idnumber: "请输入证件号码",
                    idtype: "请选择证件类型",
                    age: {
                        required: "请输入年龄",
                        isPositive: "请输入正确的年龄!"
                    },
                    job: "请输入医生职务",
                    resume: "请输入医生简介",
                    level: "请输入医生级别",

                    password: "密码不能为空",
                    password_again: "两次输入密码不一致",

                },
                errorElement: 'span', //default input error message container
                errorClass: 'help-block', // default input error message class
                //focusInvalid: false, // do not focus the last invalid input


                highlight: function (element) { // hightlight error inputs
                    $(element).closest('.form-group').addClass('has-error'); // set error class to the control group
                },
                success: function (label) {
                    label.closest('.form-group').removeClass('has-error');
                    label.remove();
                },

                errorPlacement: function (error, element) {

                    error.insertAfter(element);
                },
                submitHandler: function () {
                    var savePath = ROOTPAth + '/admin/hospitalDoctors/newDoctor';


                    $.ajax({
                        type: "POST",
                        url: savePath,
                        data: $DoctorForm.serialize(),
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
                            }
                            else {
                                $("#ajax_fail").find("h4").html(data.message);
                                $("#ajax_fail").modal("show")
                            }
                            pageIndex.reset();
                        },

                        error: function () {
                            tool.stopPageLoading();
                            $("#ajax_fail").modal("show")
                        },
                    });

                }
            });

            //表单验证-修改用户
            $ModifyForm.validate({
                rules: {
                    // account: {
                    //     required: true,
                    //     remote: { //自带远程验证存在的方法
                    //         url: ROOTPAth + '/admin/hospitalDoctors/sameName',
                    //         type: "POST",
                    //         dataType: "json",
                    //         data: {
                    //             hospitalid: function () {
                    //                 return $ModifyForm.find('input[name="hospitalid"]').val();
                    //             },
                    //             account: function () {
                    //                 return $ModifyForm.find('input[name="account"]').val();
                    //             }
                    //         }
                    //     }
                    // },
                    name: "required",
                    password: "required",
                    password_again: {
                        equalTo: "#password"
                    },
                    sex: "required",
                    idtype: "required",
                    idnumber: "required",
                    age: {
                        required: true,
                        isPositive: true
                    },
                    job: "required",
                    resume: "required",
                    level: "required",
                    departmentname: "required"

                },
                messages: {
                    name: "名称不能为空",
                    // account: {
                    //     required: "用户名不能为空",
                    //     remote: "用户名重复"
                    // },
                    sex: "请选择性别",
                    idnumber: "请输入证件号码",
                    idtype: "请选择证件类型",
                    age: {
                        required: "请输入年龄",
                        isPositive: "请输入正确的年龄!"
                    },
                    job: "请输入医生职务",
                    resume: "请输入医生简介",
                    level: "请输入医生级别",
                    departmentname: "请输入科室",
                    password: "密码不能为空",
                    password_again: "两次输入密码不一致"

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

                    $ModifyForm.find("input").prop("disabled",false);
                    var updatePath = ROOTPAth + '/admin/hospitalDoctors/modDoctor';
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
        deleteDoctor: function ($that) {
            var id = $that.data("id");
            var delPath = ROOTPAth + '/admin/hospitalDoctors/delDoctor/' + id;
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
        resetPWD: function ($that) {
            var id = $that.data("id");
            var delPath = ROOTPAth + '/admin/hospitalDoctors/resetPWD/' + id;
            $.ajax({
                url: delPath,
                type: "POST",
                success: function (data) {
                    if (data.code === 1) {
                        $addRoletipModal.find(".dialogtip-msg").html("密码重置成功");
                        $addRoletipModal.modal('show');
                        pageIndex.reset();
                    } else {
                        $("#ajax_fail").find("h4").html(data.message);
                        $("#ajax_fail").modal("show")
                    }
                }
            });
        },
        generateScheduling: function ($that) {
            var id = $that.data("id");
            var delPath = ROOTPAth + '/admin/schedulings/generateScheduling/';
            $.ajax({
                url: delPath,
                type: "POST",
                data: {
                    hospitalid:  $that.data("hospitalid"),
                    departmentid: $that.data("departmentid"),
                    doctorid: id,
                    type: $that.data("type"),

                },
                success: function (data) {
                    if (data.code === 1) {
                        $addRoletipModal.find(".dialogtip-msg").html("号源生成成功");
                        $addRoletipModal.modal('show');
                        // pageIndex.reset();
                    } else {
                        $("#ajax_fail").find("h4").html(data.message);
                        $("#ajax_fail").modal("show")
                    }
                }
            });
        }
    };


    function get_department_modModel(obj) {
        var t = setTimeout(function () {
            $.ajax({
                url: ROOTPAth + '/admin/departments/listLike',
                type: 'POST',
                dataType: 'json',
                data: {
                    hospitalid: hospitalid,
                    name: $(obj).val()
                },
                success: function (data) {
                    data = data.data;
                    var $list = $("#mod_departmentList");
                    $list.show();
                    $list.html("");
                    $.each(data, function (index, el) {
                        var html = $("<li data-departmentid='" + el.id + "'>" + el.name + "</li>");
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
                        $ModifyForm.find("input[name=departmentid]").val($(this).data("departmentid"));
                        $list.hide();
                        $ModifyForm.validate().element($(obj));
                    });
                },
                error: function (err) {
                    $("#ajax_fail").find("h4").text("修改模块加载城市列表失败");
                    $("#ajax_fail").modal("show")
                }
            });
        }, 500);
    }

    Utilitiy.init();
});