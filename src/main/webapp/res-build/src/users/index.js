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
    var $divhospitals = $("#hospitals");
    var $table = $("#datatable_ajax");
    var $hospitalname = $("#hospitalname");
    var $addModal = $('#addModal');
    var $addRoletipModal = $('#modal-box');
    var pagelength = 10; //一页多少条；
    var $searchform = $("#search-form");

    var listTpl = juicer(
        [
            '{@if total === 0}',
            '<center><h1>暂无记录,请添加</h1></center>',
            '{@else}',
            '{@each data as item,index}',
            '<div class="col-md-4 col-sm-6 item">',
            '<div class="thumbnail">',
            '<img src="${ctx}/res-build/img/avatar3_small.jpg" alt="">',
            '<div class="caption">',
            '<h3>Thumbnail label</h3>',
            '<p>Lorem ipsum dolor sit amet, consectetur adipisicing elit. </p>',
            '<p><a href="#" class="btn btn-primary" role="button">Button</a> <a href="#" class="btn btn-default" role="button">Button</a></p>',
            '</div></div></div>',
            '{@/each}',
            '{@/if}'
        ].join(""));
    var Utilitiy = {
        init: function () {
            tool.startPageLoading();
            this.bind();

        },
        bind: function () {
            //点击查询
            $("#search").on('click', function (event) {
                event.preventDefault();
                get_search($hospitalname.val());
            });

            // 下拉框请求获取医院
            $hospitalname.keyup(function (e) {
                if (e.keyCode != 40 && e.keyCode != 38) {
                    get_hospitalLike($hospitalname);
                }
            }).focus(function () {
                this.select();
            });
            //表单验证-查询医院
            $searchform.validate({
                rules: {
                    hospitalname: {
                        required: true,
                        remote: { //自带远程验证存在的方法
                            url: ROOTPAth + '/admin/sys/pay/haveHosnameAlipay',
                            type: "POST",
                            dataType: "json",
                            data: {
                                hosname: function () {
                                    return $hospitalAdd.val();
                                }
                            }
                        }
                    },
                    accountname: {
                        required: true,
                        /*                        remote: { //自带远程验证存在的方法
                         url: ROOTPAth + '/admin/sys/pay/sameAlipayName',
                         type: "POST",
                         dataType: "json",
                         data: {
                         hosname: function () {
                         return $AlipayForm.find('input[name=hosname]').val();
                         },
                         accountname: function () {
                         return $AlipayForm.find('input[name=accountname]').val();
                         },
                         subhoscode: function () {
                         return $AlipayForm.find('input[name=subhoscode]').val();
                         },
                         subhosname: function () {
                         return $AlipayForm.find('input[name=subhosname]').val();
                         }
                         }
                         }*/
                    },
                    pid: "required",
                    appid: "required",
                    checkkey: "required",
                    publickey: "required",
                    privatekey: "required",
                    subhoscode: {
                        required: true,
                        /*                        remote: { //自带远程验证存在的方法
                         url: ROOTPAth + '/admin/sys/pay/sameAlipayName',
                         type: "POST",
                         dataType: "json",
                         data: {
                         hosname: function () {
                         return $AlipayForm.find('input[name=hosname]').val();
                         },
                         accountname: function () {
                         return $AlipayForm.find('input[name=accountname]').val();
                         },
                         subhoscode: function () {
                         return $AlipayForm.find('input[name=subhoscode]').val();
                         },
                         subhosname: function () {
                         return $AlipayForm.find('input[name=subhosname]').val();
                         }
                         }
                         }*/
                    },
                    subhosname: {
                        required: true,
                        /*                        remote: { //自带远程验证存在的方法
                         url: ROOTPAth + '/admin/sys/pay/sameAlipayName',
                         type: "POST",
                         dataType: "json",
                         data: {
                         hosname: function () {
                         return $AlipayForm.find('input[name=hosname]').val();
                         },
                         accountname: function () {
                         return $AlipayForm.find('input[name=accountname]').val();
                         },
                         subhoscode: function () {
                         return $AlipayForm.find('input[name=subhoscode]').val();
                         },
                         subhosname: function () {
                         return $AlipayForm.find('input[name=subhosname]').val();
                         }
                         }
                         }*/
                    }
                },
                messages: {
                    hosname: {
                        required: "医院名不能为空",
                        remote: "未找到该医院记录"
                    },
                    accountname: {
                        required: "支付宝账户名不能为空",
                        /*   remote: "支付宝账户名重复"*/
                    },
                    pid: "PID不能为空",
                    appid: "APPID不能为空",
                    checkkey: "安全校验码不能为空",
                    publickey: "支付宝公钥不能为空",
                    privatekey: "支付宝私钥不能为空",
                    subhoscode: {
                        required: "subhoscode不能为空",
                        /*   remote: "子医院支付宝账户名重复"*/

                    },
                    subhosname: {
                        required: "subhosname不能为空",
                        /*remote: "子医院支付宝账户名重复"*/
                    }
                },
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
                    console.log("element" + element.val() + ' | ' + (element)[0].tagName + ' | ' + element.attr('name'));
                    error.insertAfter(element);
                },
                submitHandler: function () {
                    var savePath = ROOTPAth + '/admin/sys/pay/saveAlipay';
                    $.ajax({
                        type: "POST",
                        url: savePath,
                        data: $searchform.serialize(),
                        beforeSend: function () {
                            tool.startPageLoading();
                        },
                        dataType: "json",
                        success: function (data) {
                            console.log(data);
                            tool.stopPageLoading();
                            if (data.code === '1') {
                                $addModal.modal("hide");
                                $addRoletipModal.find(".dialogtip-msg").html("数据添加成功");
                                $addRoletipModal.modal('show');
                                pageIndex.reset();
                            }
                            else {
                                $("#ajax_fail").find("h4").text(data.desc);
                                $("#ajax_fail").modal("show")
                            }

                        },

                        error: function () {
                            tool.stopPageLoading();
                            $("#ajax_fail").find("h4").text("保存失败");
                            $("#ajax_fail").modal("show")
                        }
                    });
                }
            });

        }
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

    function fillHospital() {
        $.ajax({
            url: ROOTPAth + '/user/hospitals/list',
            type: 'POST',
            dataType: 'json',
            data: function () {
                var data = {
                    province: $("#province").val(),
                    city: $("#city").val(),
                    area: $("#area").val(),
                    length: pagelength,
                    page: currentpage,
                    name: $hospitalname.val()
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
                    $divhospitals.append(listTpl.render(newData));
                    currentpage = currentpage * 1 + 1;
                }
            }
        });
    }

    new PCAS("province", "city", "area");
    Utilitiy.init();
})
