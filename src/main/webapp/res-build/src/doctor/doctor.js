define(function (require, exports, module) {
    require("res-build/res/plugin/jquery-validation-1.13.1/dist/jquery.validate.min.js");
    require("res-build/res/plugin/bs-confirmation/bootstrap-confirmation.js");
    require("res-build/res/module/underscore/underscore.js");
    var tool = require("tool");
    var Page = require("page");
    var pageIndex;

    var $table = $("#datatable_ajax");
    var $DoctorForm = $("#vDoctorForm");
    var $modifyModal = $('#modifyModal');
    var $addModal = $('#addModal');
    var $addRoletipModal = $('#modal-box');
    var pagelength = 10; //一页多少条；
    $('body').tooltip({
        selector: '.has-tooltip'
    });


    var Utilitiy = {
        init: function () {

            tool.startPageLoading();
            loadDoctor();
            this.bind();
        },
        bind: function () {
            // $("#resetPassword").confirmation({
            //     title: "确定重置密码吗？",
            //     btnOkLabel: "确定",
            //     btnCancelLabel: "取消",
            //     onConfirm: function (event, element) {
            //         event.preventDefault();
            //         self.resetPWD($(element));
            //     }
            //
            // });
            var self = this;
            //我要编辑
            $DoctorForm.on("click", ".j-form-edit", function (event) {
                var formDom = event.delegateTarget;
                $(this).hide();
                $(formDom).find(".j-form-save").show();

                $(formDom).find("input").prop("disabled", false);
                $DoctorForm.find("input[name=hospitalname]").prop("disabled", true)
                $DoctorForm.find("input[name=departmentname]").prop("disabled", true)
                $DoctorForm.find("input[name=account]").prop("disabled", true)
            });
            $("#closeDivlink").click(
                function () {
                    $DoctorForm.find(".j-form-edit").show();
                    $DoctorForm.find(".j-form-save").hide();
            }
            )
            // //修改表单中科室列表
            // $DoctorForm.find("input[name=departmentname]").keyup(function (e) {
            //     if (e.keyCode != 40 && e.keyCode != 38) {
            //         get_department_modModel($DoctorForm.find("input[name=departmentname]"));
            //     }
            // }).focus(function () {
            //     this.select();
            // });
            //图片变动随时验证
            $("#headpath").bind("input onpropertychange" ,function () {
                $DoctorForm.validate().element($("#headpath"));
            });
            //自定义验证大于0方法
            $.validator.addMethod("isPositive", function (value, element) {
                var score = /^[0-9]*$/;
                return score.test(value)&&value!=0;
            }, $.validator.format("请输入正确的年龄!"));
            //判断图片大小
            $.validator.addMethod("checkPicSize", function(value,element) {
                var size  = $("#headpath")[0].files[0].size;
                var maxSize = 1*1024*1024;
                if(size > maxSize){
                    return false;
                }else{
                    return true;
                }
            }, "请上传大小在1M以下的图片");
            jQuery.validator.addMethod("checkJPG", function(value, element) {
                var filepath=$("#headpath").val();
                //获得上传文件名
                var fileArr=filepath.split("\\");
                var fileTArr=fileArr[fileArr.length-1].toLowerCase().split(".");
                var filetype=fileTArr[fileTArr.length-1];
                //切割出后缀文件名
                if(filetype != "jpg"){
                    return false;
                }else{
                    return true;
                }
            }, "上传文件格式不适合");
            //表单验证-修改用户
            $DoctorForm.validate({
                rules: {
                    file:{
                        checkJPG:true,
                        checkPicSize:true

                    },
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
                    departmentname: {
                        required: true,
                        remote: {
                            url: ROOTPAth + '/doctor/doctors/hasDepartmentName',
                            type: "POST",
                            dataType: "json",
                            data: {
                                hospitalid: hospitalid,
                                name: function () {
                                    return $DoctorForm.find('input[name="departmentname"]').val();
                                }
                            }
                        }
                    },

                },
                messages: {
                    name: "名称不能为空",
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
                    departmentname: {
                        required: "请输入科室",
                        remote: "不存在部门"
                    },
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


                    var updatePath = ROOTPAth + '/doctor/doctors/modDoctor';
                    var formData = new FormData($("#vDoctorForm")[0]);
                    $.ajax({
                        url:updatePath,
                        type:"post",
                        dataType:"JSON",
                        data:formData,
                        cache: false,
                        contentType: false,
                        processData: false,
                        success:function (data) {
                            if (data.code == 1) {
                                $('#modifyModal').modal('hide');
                                $addRoletipModal.find(".dialogtip-msg").html("账号修改成功");
                                $addRoletipModal.modal('show');
                                $DoctorForm.find("input").prop("disabled", true);
                                $DoctorForm.find("textarea").prop("disabled", true);
                                $DoctorForm.find("select").prop("disabled", true);
                            }
                            else {
                                $("#ajax_fail").find("h4").html(data.message);
                                $("#ajax_fail").modal("show")
                            }
                        }
                    })
                    // $.post(updatePath, formData, function (data) {
                    //     if (data.code == 1) {
                    //         $('#modifyModal').modal('hide');
                    //         $addRoletipModal.find(".dialogtip-msg").html("账号修改成功");
                    //         $addRoletipModal.modal('show');
                    //         $DoctorForm.find("input").prop("disabled", true);
                    //         $DoctorForm.find("textarea").prop("disabled", true);
                    //         $DoctorForm.find("select").prop("disabled", true);
                    //     }
                    //     else {
                    //         $("#ajax_fail").find("h4").html(data.message);
                    //         $("#ajax_fail").modal("show")
                    //     }
                    // });

                }
            });

        },
    };

    function loadDoctor() {
        $.ajax({
            url: ROOTPAth + '/doctor/doctors/doctorInfo',
            type: 'POST',
            dataType: 'json',
            data: {
                id: doctorid
            },
            success: function (data) {
                tool.stopPageLoading();
                $DoctorForm.find('input[name=id]').val(data.id);
                $DoctorForm.find('input[name=account]').val(data.account);
                $DoctorForm.find('input[name=name]').val(data.name);
                $DoctorForm.find('input[name=sex]').eq(data.sex - 1).attr("checked", "checked");
                $DoctorForm.find('select[name=idtype]').val(data.idtype)
                $DoctorForm.find('input[name=idnumber]').val(data.idnumber);
                $DoctorForm.find('input[name=level]').eq(data.level - 1).attr("checked", "checked");
                $DoctorForm.find('input[name=age]').val(data.age);
                $DoctorForm.find('input[name=job]').val(data.job);
                $DoctorForm.find('textarea[name=resume]').val(data.resume);
                $DoctorForm.find(".j-form-save").hide();
                $DoctorForm.find(".j-form-edit").show();
            }
        })
    }

    // function get_department_modModel(obj) {
    //     var t = setTimeout(function () {
    //         $.ajax({
    //             url: ROOTPAth + '/doctor/departments/listLike',
    //             type: 'POST',
    //             dataType: 'json',
    //             data: {
    //                 hospitalid: hospitalid,
    //                 name: $(obj).val()
    //             },
    //             success: function (data) {
    //                 data = data.data;
    //                 var $list = $("#mod_departmentList");
    //                 $list.show();
    //                 $list.html("");
    //                 $.each(data, function (index, el) {
    //                     var html = $("<li data-departmentid='" + el.id + "'>" + el.name + "</li>");
    //                     $list.append(html);
    //
    //                 });
    //                 if ($list.html() == "") {
    //                     $list.hide();
    //                 }
    //                 $($list).find("li").hover(function () {
    //
    //                     $(this).addClass("esultDivLiHover");
    //                 }, function () {
    //                     $(this).removeClass("esultDivLiHover");
    //                 });
    //                 $list.mouseleave(function () {
    //                     $list.hide();
    //                 });
    //                 $($list).find("li").click(function (event) {
    //                     $(obj).val($(this).text());
    //                     $DoctorForm.find("input[name=departmentid]").val($(this).data("departmentid"));
    //                     $list.hide();
    //                     $DoctorForm.validate().element($(obj));
    //                 });
    //             },
    //             error: function (err) {
    //                 $("#ajax_fail").find("h4").text("修改模块加载城市列表失败");
    //                 $("#ajax_fail").modal("show")
    //             }
    //         });
    //     }, 500);
    // }

    Utilitiy.init();
});