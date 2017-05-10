define(function (require, exports, module) {
    require("res-build/res/plugin/jquery-validation-1.13.1/dist/jquery.validate.min.js");
    var $ModelForm = $("#ModelForm");

    function initHospital() {
        //医院选择下拉列表
        $("#hospitalname").keyup(function (e) {
            if (e.keyCode != 40 && e.keyCode != 38) {
                get_hospitalLike($("#hospitalname"));
            }
        }).focus(function () {
            this.select();
        }),

            $ModelForm.validate({
                rules: {
                    name: {
                        required: true,
                        remote: { //自带远程验证存在的方法
                            url: ROOTPAth + '/admin/hospitals/hasName',
                            type: "POST",
                            dataType: "json",
                            data: {
                                name: function () {
                                    return $ModelForm.find('input[name="name"]').val();
                                }
                            }
                        }
                    },
                },
                messages: {
                    name: {
                        required: "医院名不能为空",
                        remote: "不存在该医院"
                    },
                },
                errorElement: 'span', //default input error message container
                errorClass: 'help-block', // default input error message class
                // //focusInvalid: false, // do not focus the last invalid input

                //
                // invalidHandler: function (event, validator) { //display error alert on form submit
                //     //	                $('.alert-danger', $('.login-form')).show();
                // },
                highlight: function (element) { // hightlight error inputs
                    $(element)
                        .closest('.form-group').addClass('has-error'); // set error class to the control group
                },

                success: function (label) {
                    label.closest('.form-group').removeClass('has-error');
                    var inputid = label.closest('.form-group').find("input") == "undefined" ? "" : label.closest('.form-group').find("input").attr("id");
                    if (inputid == "hospitalname" && !label.closest('.form-group').hasClass("has-error")) {

                        $.ajax({
                            url: ROOTPAth + '/admin/hospitals/hospitalInfoByName',
                            type: 'POST',
                            dataType: 'json',
                            data: {
                                name: $("#hospitalname").val(),
                            },
                            success: function (data) {
                                data = data.data;
                                $("#hospitalid").val(data.id);
                            },
                        });
                    }
                    label.remove();

                },

                errorPlacement: function (error, element) {
                    var inputid = element.closest('.form-group').find("input") == "undefined" ? "" : element.closest('.form-group').find("input").attr("id");
                    if (inputid == "hospitalname" && element.closest('.form-group').hasClass("has-error")) {
                        $("#hospitalid").empty();
                    }
                    error.insertAfter(element);
                },
                submitHandler: function () {

                    window.location.href = ROOTPAth + "/admin/paymentdetails/index?pcode=1&subcode=2&patientid=" + patientid + "&patientname=" + patientname + "&hospitalid=" + $("#hospitalid").val() + "&hospitalname=" + $("#hospitalname").val();


                }
            });

    }

    function get_hospitalLike(obj) {
        var t = setTimeout(function () {
            $.ajax({
                url: ROOTPAth + '/admin/hospitals/listLike',
                type: 'POST',
                dataType: 'json',
                data: {
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
                        $("#hospitalid").val($(this).data("id"));
                        $list.hide();
                        $ModelForm.validate().element($(obj));
                    });
                },
                error: function (err) {
                    $("#ajax_fail").find("h4").text("模块加载用户列表失败");
                    $("#ajax_fail").modal("show")
                }
            });
        }, 500);
    }

    initHospital();

});