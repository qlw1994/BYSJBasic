define(function (require, exports, module) {
    require("res-build/res/plugin/jquery-validation-1.13.1/dist/jquery.validate.min.js");
    var $ModelForm = $("#ModelForm");

    function initPatientAcc() {
        //添加界面关闭,下拉框消失
        $ModelForm.on("hide.bs.modal", function (event) {
            $ModelForm.find(".list").hide();
        });
        $("#service").val(service);
        //用户选择下拉列表
        $("#useraccount").keyup(function (e) {
            if (e.keyCode != 40 && e.keyCode != 38) {
                get_usersLike($("#useraccount"));
            }
        }).focus(function () {
            this.select();
        }),
            $("#patientname").change(function () {
                var option = $(this).children('option:selected');
                $("#patientid").val(option.data("patientid"));
                $ModelForm.find("input[name=sex]").eq(option.data("sex") - 1).attr("checked", "checked");
                $ModelForm.find("select[name=idtype]").val(option.data("idtype"));
                $ModelForm.find("input[name=idnumber]").val(option.data("idnumber"));
                $ModelForm.find("input[name=birthday]").val(option.data("birthday"));
                $("#patientid").val(option.data("patientid"));
            }),

            $ModelForm.validate({
                rules: {
                    service:"required",
                    useraccount: {
                        required: true,
                        remote: { //自带远程验证存在的方法
                            url: ROOTPAth + '/admin/users/hasName',
                            type: "POST",
                            dataType: "json",
                            data: {
                                hospitalid: hospitalid,
                                account: function () {
                                    return $ModelForm.find('input[name="useraccount"]').val();
                                }
                            }
                        }
                    },
                    patientname: "required",
                },
                messages: {
                    patientname: "请选择就诊人",
                    useraccount: {
                        required: "用户名不能为空",
                        remote: "不存在该用户"
                    },
                    service:"请选择服务"
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
                    var inputid = label.closest('.form-group').find("input")== "undefined"?"":label.closest('.form-group').find("input").attr("id");
                    if (inputid == "useraccount"&&!label.closest('.form-group').hasClass("has-error")) {

                        $.ajax({
                            url: ROOTPAth + '/admin/patients/listAll',
                            type: 'POST',
                            dataType: 'json',
                            data: {
                                uid: $("#userid").val(),
                            },
                            success: function (data) {
                                $("#patientname").prop("disabled", false);
                                data = data.data;
                                var $select = $("#patientname");
                                $select.empty();
                                $select.append("<option data-patientid='' value=''>请选择</option>")
                                $.each(data, function (index, el) {
                                    var html = $("<option data-patientid='" + el.id + "' data-sex='" + el.sex + "' data-birthday='" + el.birthday + "'  data-idtype='" + el.idtype + "'  data-idnumber='" + el.idnumber + "' value='" + el.name + "'>" + el.name + "</option>");
                                    $select.append(html);
                                });
                            },
                        });
                    }
                    label.remove();

                },

                errorPlacement: function (error, element) {
                    var inputid = element.closest('.form-group').find("input")== "undefined"?"":element.closest('.form-group').find("input").attr("id");
                    if (inputid == "useraccount"&&element.closest('.form-group').hasClass("has-error")) {
                        $("#patientname").empty();
                        $("#patientname").append("<option data-patientid='' value=''>请选择</option>")
                        $("#patientname").prop("disabled", true);

                    }
                    error.insertAfter(element);
                },
                submitHandler: function () {
                    var Path = ROOTPAth + '/admin/'+$("#service").val();
                    window.location.href = Path + "?pcode=2&subcode=1&uid=" + $("#userid").val() + "&patientid=" + $("#patientid").val() + "&patientname=" + $("#patientname").val() + "";

                }
            });

    }

    function get_usersLike(obj) {
        var t = setTimeout(function () {
            $.ajax({
                url: ROOTPAth + '/admin/users/listUsersLike',
                type: 'POST',
                dataType: 'json',
                data: {
                    account: $(obj).val()
                },
                success: function (data) {
                    data = data.data;
                    var $list = $("#userList");
                    $list.show();
                    $list.html("");
                    $.each(data, function (index, el) {
                        var html = $("<li data-uid='" + el.id + "' value='"+el.account+"'>" + el.account + "</li>");
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
                        $("#userid").val($(this).data("uid"));
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

    initPatientAcc();

});