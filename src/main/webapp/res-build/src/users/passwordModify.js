define(function (require, exports, module) {
    require("res-build/res/plugin/jquery-validation-1.13.1/dist/jquery.validate.min.js");
    require("res-build/res/plugin/bs-confirmation/bootstrap-confirmation.js");
    require("res-build/res/module/underscore/underscore.js");

    var $modifyModal = $("#modifyModal-index");
    var $ModifyForm = $("#vPasswordForm-index");
    var $userid = $("#id-index");
    var $ModifyFormUser = $("#vUserModifyForm");
    var $modifyModalUser = $('#modifyModal-user');
    var $addRoletipModal = $('#modal-box-index');
    var Utilitiy = {
        init: function () {
            this.bind();
        },

        bind: function () {
            var self = this;
            //修改页面初始化
            $modifyModal.on('show.modal', function (event) {
                $ModifyForm.find("input").removeAttr("aria-describedby");
                $ModifyForm.find("input").removeAttr("aria-invalid");
                $ModifyForm.find("input").removeAttr("aria-required");
                $ModifyForm.find("div").removeClass("has-error");
                $ModifyForm.find("span").remove();
                $ModifyForm[0].reset();
            })
            //我要编辑
            $ModifyFormUser.on("click", ".j-form-edit", function (event) {
                var formDom = event.delegateTarget;
                $(this).hide();
                $(formDom).find(".j-form-save").show();

                $(formDom).find("input").prop("disabled", false);
                $ModifyFormUser.find("input[name='account']").prop("disabled", true);
                $ModifyFormUser.find("input[name='name']").prop("disabled", true);
                // $(event.relatedTarget)
            });
            //修改表单初始化
            $modifyModalUser.on('show.modal', function (event) {
                var $modal = $modifyModalUser;
                $($modifyModalUser).find("input").prop("disabled", true);
                $($modifyModalUser).find("select").prop("disabled", true);
                $modal.find("input").removeAttr("aria-describedby");
                $modal.find("input").removeAttr("aria-invalid");
                $modal.find("input").removeAttr("aria-required");
                $modal.find("select").removeAttr("aria-describedby");
                $modal.find("select").removeAttr("aria-invalid");
                $modal.find("select").removeAttr("aria-required");
                $modal.find("div").removeClass("has-error");
                $modal.find("span").remove();
                $.ajax({
                    url:ROOTPAth+"/user/users/userInfo",
                    type:"post",
                    dataType:"json",
                    data:{
                        id:uid
                    },
                    success:function (res) {
                        $modal.find('input[name=id]').val(res.id);
                        $modal.find('input[name=account]').val(res.account);
                        $modal.find('input[name=name]').val(res.name);
                        $modal.find('input[name=sex]').eq(res.sex-1).attr("checked","checked");
                        $modal.find('select[name=idtype]').val(res.idtype)
                        $modal.find('input[name=idnumber]').val(res.idnumber);
                        $modal.find('input[name=phone]').val(res.phone);
                    }

                })


                $modal.find(".j-form-save").hide();
                $modal.find(".j-form-edit").show();
            });
            //表单验证-修改
            $ModifyForm.validate({
                rules: {
                    oddPassword: {
                        required: true,
                        remote: { //自带远程验证存在的方法
                            url: ROOTPAth + '/user/users/sameOddPassword',
                            type: "POST",
                            dataType: "json",
                            data: {
                                id: $userid.val(),
                                password: function () {
                                    return $ModifyForm.find('input[name=oddPassword]').val();
                                }
                            }
                        }
                    },
                    newPassword: "required",
                    repeatPassword: {
                        equalTo: "#newPassword",
                    }
                    ,


                },
                messages: {
                    oddPassword: {
                        required: "原密码不能为空",
                        remote: "原密码错误"
                    }
                    ,
                    newPassword: {
                        required: "新密码不能为空",

                    }
                    ,
                    repeatPassword: {
                        required: "重复密码不能为空",
                        equalTo: "两次输入的密码不一致"
                    }
                }
                ,
                errorElement: 'span', //default input error message container
                errorClass: 'help-block', // default input error message class
                focusInvalid: false, // do not focus the last invalid input


                invalidHandler: function (event, validator) { //display error alert on form submit
                    //	                $('.alert-danger', $('.login-form')).show();
                }

                ,
                highlight: function (element) { // hightlight error inputs
                    $(element)
                        .closest('.form-group').addClass('has-error'); // set error class to the control group
                }
                ,

                success: function (label) {
                    label.closest('.form-group').removeClass('has-error');
                    label.remove();
                }
                ,

                errorPlacement: function (error, element) {
                    error.insertAfter(element);
                }
                ,
                submitHandler: function () {
                    var updatePath = ROOTPAth + '/user/users/updatePassword';

                    $.post(updatePath, $ModifyForm.serialize(), function (data) {
                        $modifyModal.modal('hide');
                        $addRoletipModal.modal('show');
                    });

                }
            })
            ;

        }
    }


    Utilitiy.init();
})
