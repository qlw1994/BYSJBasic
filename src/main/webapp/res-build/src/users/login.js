define(function (require, exports, module) {
    require("res-build/res/plugin/jquery-validation-1.13.1/dist/jquery.validate.min.js");
    var $loginForm = $("#login-form");
    var loginUtilitiy = {
        init: function () {
            this.bind();
        },
        bind: function () {
            var self=this;

            $loginForm.validate({
                errorElement: 'span', //default input error message container
                errorClass: 'help-block', // default input error message class
                focusInvalid: false, // do not focus the last invalid input
                rules: {
                    account: {
                        required: true
                    },
                    password: {
                        required: true
                    },
                    remember: {
                        required: false
                    }
                },

                messages: {
                    account: {
                        required: "用户名不能为空."
                    },
                    password: {
                        required: "密码不能为空."
                    }
                },

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
                    error.insertAfter(element.closest('.input-icon'));
                },

                submitHandler: function (form) {

                    self.postData($(form));

                }
            });
        },
        postData: function ($form) {
             //	                form.submit(); // form validation success, call ajax form submit
                    var basePath = location.href.substring(0, location.href.lastIndexOf('/'));

                    $.post(basePath + '/user/login', $form.serialize(), function (data) {

                        if (data.code===1) {
                            window.location.href = basePath + '/user/index';
                        } else {
                            $('#alert-info', $form).show().find(".alert-info-txt").text(data.msg);
                        }
                    });

        }
    };
    loginUtilitiy.init();
    return loginUtilitiy;

});
