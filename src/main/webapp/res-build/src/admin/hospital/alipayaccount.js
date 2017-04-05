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
    var $table = $("#datatable_ajax");
    var $alipayList = $("#alipay-list");
    var $hospital = $("#hospital");
    var $hospitalAdd = $("#hosnameAdd");
    var $hospitalModify = $("#hosnameModify");
    var $AlipayForm = $('#vAlipayForm');
    var $addModal = $('#addModal');
    var $addRoletipModal = $('#modal-box');
    var pagelength = 10; //一页多少条；
    var $modifyModal = $('#modifyModal');
    var $ModifyForm = $("#vAlipayModifyForm");
    var addFormValidate;
    var listTpl = juicer(
        [
            '{@if total === 0}',
            '<tr>',
            '<td colspan="5" style="text-align:center">',
            '暂无记录,请添加',
            '</td>',

            '</tr>',

            '{@else}',
            '{@each data as item,index}',
            '{@if index%2==0}',
            '<tr role="row" class="odd" data-accountname="${item.accountname}">',
            '<tr role="row" class="odd" data-pid="${item.pid}">',
            '<tr role="row" class="odd" data-appid="${item.appid}">',
            '<tr role="row" class="odd" data-subhoscode="${item.subhoscode}">',
            '<tr role="row" class="odd" data-subhosname="${item.subhosname}">',

            '{@else}',
            '<tr role="row" class="even" data-accountname="${item.accountname}">',
            '<tr role="row" class="even" data-pid="${item.pid}">',
            '<tr role="row" class="even" data-appid="${item.appid}">',
            '<tr role="row" class="even" data-subhoscode="${item.subhoscode}">',
            '<tr role="row" class="even" data-subhosname="${item.subhosname}">',
            '{@/if}',
            '    <td>${item.accountname}</td>',
            '    <td>${item.pid}</td>',
            '    <td>${item.appid}</td>',
            '    <td>${item.subhoscode}</td>',
            '    <td>${item.subhosname}</td>',
            '    <td class=" heading">',
            ' <button type="button" class="btn btn-default btn-xs j-disable j-edit" data-toggle="modal" data-target="#modifyModal" data-id="${item.id}" data-accountname="${item.accountname}" data-pid="${item.pid}" data-checkkey="${item.checkkey}" data-publickey="${item.publickey}" data-privatekey="${item.privatekey}" data-appid="${item.appid}"><span class="iconfont iconfont-xs">&#xe62d;</span>查看</button>',
            //  '{@if item.id!==1}',
            ' <button type="button" class="btn btn-danger btn-xs j-disable j-del" data-toggle="confirmation" data-id="${item.id}" data-placement="top"><span class="iconfont iconfont-xs">&#xe618;</span>删除</button>',
            //  '{@/if}',
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
                    url: ROOTPAth + '/admin/alipayaccounts/list',
                    type: 'POST',
                    dataType: 'json',
                    data: function () {
                        var data = {
                            length: pagelength,
                            hospitalid:hospitalid
                        };
                        return data;
                    },
                    success: function (res) {

                        console.log(res);
                        tool.stopPageLoading();
                        if (res.code === 1) {
                            var newData = $.extend({}, res);
                            $.each(newData.data, function (i, val) {

                                newData.data[i].currentpage = pageIndex.current;

                                if (!newData.data[i].accountname) {
                                    newData.data[i].accountname = ""
                                }
                                if (!newData.data[i].pid) {
                                    newData.data[i].pid = ""
                                }
                                if (!newData.data[i].appid) {
                                    newData.data[i].appid = ""
                                }
                            });

                            $alipayList.find(".page-info-num").text(res.total);
                            $table.find("tbody").empty().append(listTpl.render(newData));
                        }
                        //删除权限
                        $table.find(".j-del").confirmation({
                            title: "确定删除该记录吗？",
                            btnOkLabel: "确定",
                            btnCancelLabel: "取消",
                            onConfirm: function (event, element) {
                                event.preventDefault();
                                self.deleteAlipay($(element));
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
            //     pageIndex.reset();

            //分页，修改每页显示数据
            $alipayList.on("change", ".j-length", function () {
                var $this = $(this);
                pagelength = $this.val();
                var index = $this.get(0).selectedIndex;
                $alipayList.find(".j-length").not(this).get(0).selectedIndex = index;
                pageIndex.reset();
            });
            //添加页面初始化
            $addModal.on('show.modal', function (event) {
                $AlipayForm[0].reset();
                $AlipayForm.find("input").removeAttr("aria-describedby");
                $AlipayForm.find("input").removeAttr("aria-invalid");
                $AlipayForm.find("input").removeAttr("aria-required");
                $AlipayForm.find("textarea").removeAttr("aria-describedby");
                $AlipayForm.find("textarea").removeAttr("aria-invalid");
                $AlipayForm.find("textarea").removeAttr("aria-required");
                $AlipayForm.find("div").removeClass("has-error");
                $AlipayForm.find("span").remove();
            })
            //修改页面初始化
            $modifyModal.on('show.modal', function (event) {
                $ModifyForm.find("input").removeAttr("aria-describedby");
                $ModifyForm.find("input").removeAttr("aria-invalid");
                $ModifyForm.find("input").removeAttr("aria-required");
                $AlipayForm.find("textarea").removeAttr("aria-describedby");
                $AlipayForm.find("textarea").removeAttr("aria-invalid");
                $AlipayForm.find("textarea").removeAttr("aria-required");
                $ModifyForm.find("div").removeClass("has-error");
                $ModifyForm.find("span").remove();
                var button = $(event.relatedTarget); // Button that triggered the modal
                $ModifyForm[0].reset();
                $modifyModal.find("input").prop("disabled", true);
                $modifyModal.find("textarea").prop("disabled", true);

                var accountname = button.data("accountname");
                var pid = button.data("pid");
                var appid = button.data("appid");
                var checkkey = button.data("checkkey");
                var publickey = button.data("publickey");
                var privatekey = button.data("privatekey");
                var id = button.data("id");

                $ModifyForm.find(".modal-body").before(hidden_input);
                $ModifyForm.find('input[name=accountname]').val(accountname);
                $ModifyForm.find('input[name=pid]').val(pid);
                $ModifyForm.find('input[name=appid]').val(appid);
                $ModifyForm.find('input[name=checkkey]').val(checkkey);
                $ModifyForm.find('textarea[name=publickey]').val(publickey);
                $ModifyForm.find('textarea[name=privatekey]').val(privatekey);
                $ModifyForm.find(".j-form-save").hide();
                $ModifyForm.find(".j-form-edit").show();

            })
            //我要编辑
            $ModifyForm.on("click", ".j-form-edit", function (event) {
                var formDom = event.delegateTarget;
                $(this).hide();
                $(formDom).find(".j-form-save").show();

                $modifyModal.find("input[disabled]").prop("disabled", false);
                $modifyModal.find("textarea[disabled]").prop("disabled", false);
            });
            //表单验证-添加支付宝帐号
            addFormValidate = $AlipayForm.validate({
                rules: {
                    accountname: {
                        required: true,
                        /*                        remote: { //自带远程验证存在的方法
                         url: ROOTPAth + '/admin/alipayaccount/sameAlipayName',
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

                },
                messages: {
                    accountname: {
                        required: "支付宝账户名不能为空",
                        /*   remote: "支付宝账户名重复"*/
                    },
                    pid: "PID不能为空",
                    appid: "APPID不能为空",
                    checkkey: "安全校验码不能为空",
                    publickey: "支付宝公钥不能为空",
                    privatekey: "支付宝私钥不能为空",


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
                    var savePath = ROOTPAth + '/admin/alipayaccounts/saveAlipayaccount';
                    $.ajax({
                        type: "POST",
                        url: savePath,
                        data: $AlipayForm.serialize(),
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
            //表单验证-修改支付宝帐号
            $ModifyForm.validate({
                rules: {
                    accountname: {
                        required: true,
                        /*   remote: { //自带远程验证存在的方法
                         url: ROOTPAth + '/admin/alipayaccount/sameAlipayName',
                         type: "POST",
                         dataType: "json",
                         data: {
                         hosname: function () {
                         return $ModifyForm.find('input[name=hosname]').val();
                         },
                         accountname: function () {
                         return $ModifyForm.find('input[name=accountname]').val();
                         }
                         }
                         }*/
                    },
                    pid: "required",
                    appid: "required",
                    checkkey: "required",
                    publickey: "required",
                    privatekey: "required",
                },
                messages: {
                    accountname: {
                        required: "支付宝账户名不能为空",
                        // remote: "与旧账户名相同"
                    },
                    pid: "PID不能为空",
                    appid: "APPID不能为空",
                    checkkey: "安全校验码不能为空",
                    publickey: "支付宝公钥不能为空",
                    privatekey: "支付宝私钥不能为空",
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
                    var updatePath = ROOTPAth + '/admin/alipayaccounts/updateAlipayaccount';

                    $.post(updatePath, $ModifyForm.serialize(), function (data) {
                        if (data.code === '1') {
                            $modifyModal.modal('hide');
                            $addRoletipModal.find(".dialogtip-msg").html("数据修改成功");
                            $addRoletipModal.modal('show');
                            pageIndex.reset();
                            $ModifyForm.find("input[type='hidden']").eq(0).remove();
                            $ModifyForm.find("input[type='hidden']").eq(1).remove();
                        }
                        else {
                            $("#ajax_fail").find("h4").text(data.desc);
                            $("#ajax_fail").modal("show")
                        }
                    });

                }
            });

        }   , deleteAlipay: function ($that) {
            var id = $that.data("id");
            var delPath = ROOTPAth + '/admin/alipayaccounts/deleteAlipayaccount/' + id;
            $.ajax({
                url: delPath,
                type: "POST",
                success: function (data) {
                    pageIndex.reset();
                }
            });

        },
    }


    Utilitiy.init();
});
