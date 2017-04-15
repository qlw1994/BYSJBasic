define(function (require, exports, module) {
    require("res-build/res/plugin/jquery-validation-1.13.1/dist/jquery.validate.min.js");
    require("res-build/res/plugin/bs-confirmation/bootstrap-confirmation.js");
    require("res-build/res/module/underscore/underscore.js");
    var tool = require("tool");
    var juicer = require("juicer");


    var $UserForm = $('#vUserForm');

    var $addModal = $('#addModal');
    var $addRoletipModal = $('#modal-box');
    var pagelength = 10; //一页多少条；

    var Utilitiy = {
        init: function () {

            tool.startPageLoading();
            this.bind();
        },
        bind: function () {
            var self = this;

            $.validator.addMethod("isSelect", function (value, element) {

                return value == '1' || value == '2';
            }, $.validator.format("请输入1或2"));
            //手机验证规则
            jQuery.validator.addMethod("ismobile", function (value, element) {
                var mobile = /^1[3|4|5|7|8]\d{9}$/;
                return this.optional(element) || (mobile.test(value));
            }, "手机格式不对");
            //表单验证-添加用户
            $UserForm.validate({
                rules: {
                    account: {
                        required: true,
                        remote: { //自带远程验证存在的方法
                            url: ROOTPAth + '/admin/users/sameName',
                            type: "POST",
                            dataType: "json",
                            data: {
                                account: function () {
                                    return $UserForm.find('input[name="account"]').val();
                                }
                            }
                        }
                    },
                    name: "required",
                    password: "required",
                    password_again: {
                        equalTo: "#add_password"
                    },
                    phone: {
                        required: true,
                        ismobile: true
                    },
                    sex: "required",
                    idtype: "required",
                    idnumber: "required",
                },
                messages: {
                    phone: {
                        required: "手机不能为空",
                        ismoblie: "手机格式不对"
                    },
                    name: "名称不能为空",
                    account: {
                        required: "用户名不能为空",
                        remote: "用户名重复"
                    },
                    sex: "请选择性别",
                    idnumber: "请输入证件号码",
                    idtype:"请选择证件类型",


                    password: "密码不能为空",
                    password_again: "两次输入密码不一致",

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

                    var basePath = location.href.substring(0, location.href.lastIndexOf('/'));
                    var savePath = basePath + '/usersignup';
                    $.ajax({
                        type: "POST",
                        url: savePath,
                        data: $UserForm.serialize(),
                        beforeSend: function () {
                            tool.startPageLoading();
                        },
                        dataType: "json",
                        success: function (data) {
                            tool.stopPageLoading();
                            if (data.code == 1) {
                                window.location.href = basePath + '/userindex/index';
                            }
                        }
                    });

                }
            });



        },

    };
});