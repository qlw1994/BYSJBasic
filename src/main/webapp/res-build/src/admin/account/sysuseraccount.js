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
    var $SysuserForm = $('#vSysuserForm');
    var $ModifyForm = $("#vSysuserModifyForm");
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
            '<tr role="row" class="odd" data-id="${item.id}">',
            '<tr role="row" class="odd" data-name="${item.name}">',
            '<tr role="row" class="odd" data-account="${item.account}">',
            '<tr role="row" class="odd" data-power="${item.power}">',
            '<tr role="row" class="odd" data-createdate="${item.createdate}">',

            '{@else}',
            '<tr role="row" class="even" data-id="${item.id}">',
            '<tr role="row" class="even" data-name="${item.name}">',
            '<tr role="row" class="even" data-account="${item.account}">',
            '<tr role="row" class="even" data-power="${item.power}">',
            '<tr role="row" class="even" data-createdate="${item.createdate}">',
            '{@/if}',
            '    <td>${item.id}</td>',
            '    <td>${item.name}</td>',
            '    <td>${item.account}</td>',
            '    <td>${item.power}</td>',
            '    <td>${item.createdate}</td>',
            '    <td class=" heading">',

            ' <button type="button" class="btn btn-default btn-xs j-disable j-edit" data-toggle="modal" data-target="#modifyModal"  data-id="${item.id}"  data-name="${item.name}"  data-account="${item.account}" data-power="${item.power}"><span class="iconfont iconfont-xs">&#xe62d;</span>查看</button>',
            ' <button id="resetPWD" type="button" class="btn btn-danger btn-xs j-disable resetPWD" data-toggle="confirmation"  data-placement="top" data-id="${item.id}"><span class="iconfont iconfont-xs">&#xe603;</span>重置密码</button>',
            '{@if item.power!=0}',
            ' <button type="button" class="btn btn-danger btn-xs j-disable j-del" data-toggle="confirmation"  data-placement="top" data-id="${item.id}"><span class="iconfont iconfont-xs">&#xe618;</span>删除</button>',
            '{@/if}',
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
                    url: ROOTPAth + '/admin/sysusers/list',
                    type: 'POST',
                    dataType: 'json',
                    data: function () {
                        var data = {
                            length: pagelength
                        };
                        return data;
                    },
                    success: function (res) {
                        var newData = $.extend({}, res);

                        tool.stopPageLoading();
                        $.each(newData.data, function (i, val) {

                            newData.data[i].currentpage = pageIndex.current;
                        });
                        $accountList.find(".page-info-num").text(res.data.length);

                        $table.find("tbody").empty().append(listTpl.render(newData));
                        //删除权限
                        $table.find(".j-del").confirmation({
                            title: "确定删除该帐号吗？",
                            btnOkLabel: "确定",
                            btnCancelLabel: "取消",
                            onConfirm: function (event, element) {
                                event.preventDefault();
                                self.deleteSysuser($(element));
                            }
                        });
                        $table.find(".resetPWD").confirmation({
                            title: "确定重置密码吗？",
                            btnOkLabel: "确定",
                            btnCancelLabel: "取消",
                            onConfirm: function (event, element) {
                                event.preventDefault();
                                self.resetPWD($(element));
                            }

                        });
                        //$table.find("tbody").empty().append("");
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
                $SysuserForm.find("input[name='account']").prop("disabled", true);

                // $(event.relatedTarget)
            });
            //添加表单初始化
            $addModal.on('show.modal', function (event) {
                $SysuserForm[0].reset();
                $SysuserForm.find("input").removeAttr("aria-describedby");
                $SysuserForm.find("input").removeAttr("aria-invalid");
                $SysuserForm.find("input").removeAttr("aria-required");
                $SysuserForm.find("div").removeClass("has-error");
                $SysuserForm.find("span").remove();
            })
            //修改表单初始化
            $modifyModal.on('show.modal', function (event) {
                var $modal = $ModifyForm;
                $($modifyModal).find("input").prop("disabled", true);
                $modal.find("input").removeAttr("aria-describedby");
                $modal.find("input").removeAttr("aria-invalid");
                $modal.find("input").removeAttr("aria-required");
                $modal.find("div").removeClass("has-error");
                $modal.find("span").remove();
                var button = $(event.relatedTarget); // Button that triggered the modal
                var id = button.data("id");
                var account = button.data("account");
                var name = button.data("name");
                var power = button.data("power");
                var idtype = button.data("idtype");
                var idnumber = button.data("idnumber");

                $modal.find('input[name=id]').val(id);
                $modal.find('input[name=account]').val(account);
                $modal.find('input[name=name]').val(name);
                $modal.find('input[name=power]').eq(power).attr("checked","checked");

                $modal.find(".j-form-save").hide();
                $modal.find(".j-form-edit").show();
            });

            //表单验证-添加用户
            $SysuserForm.validate({
                rules: {
                    account: {
                        required: true,
                        remote: { //自带远程验证存在的方法
                            url: ROOTPAth + '/admin/sysusers/sameName',
                            type: "POST",
                            dataType: "json",
                            data: {
                                account: function () {
                                    return $SysuserForm.find('input[name="account"]').val();
                                }
                            }
                        }
                    },
                    name: "required",
                    password: "required",
                    password_again: {
                        equalTo: $("#add_password")
                    },

                    power: "required",

                },
                messages: {
                    name: "名称不能为空",
                    account: {
                        required: "用户名不能为空",
                        remote: "用户名重复"
                    },
                    power: "请选择权限",


                    password: "密码不能为空",
                    password_again: "两次输入密码不一致",

                },
                errorElement: 'span', //default input error message container
                errorClass: 'help-block', // default input error message class
                // //focusInvalid: false, // do not focus the last invalid input
                //
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
                    label.remove();
                },

                errorPlacement: function (error, element) {
                    error.insertAfter(element);
                },
                submitHandler: function () {
                    var savePath = ROOTPAth + '/admin/sysusers/newSysuser';


                    $.ajax({
                        type: "POST",
                        url: savePath,
                        data: $SysuserForm.serialize(),
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
                                $SysuserForm.form();
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
                    //     // remote: { //自带远程验证存在的方法
                    //     //     url: ROOTPAth + '/admin/sysusers/sameName',
                    //     //     type: "POST",
                    //     //     dataType: "json",
                    //     //     data: {
                    //     //         account: function () {
                    //     //             return $SysuserForm.find('input[name="account"]').val();
                    //     //         }
                    //     //     }
                    //     // }
                    // },
                    name: "required",
                    // password: "required",
                    password_again: {
                        equalTo:$("#mod_password")
                    },
                    power: "required",
                },
                messages: {
                    name: "名称不能为空",
                    // account: {
                    //     required: "用户名不能为空",
                    //     remote: "用户名重复"
                    // },
                    power: "请选择权限",
                    // password: "密码不能为空",
                    password_again: "两次输入密码不一致",

                },
                errorElement: 'span', //default input error message container
                errorClass: 'help-block', // default input error message class
                // //focusInvalid: false, // do not focus the last invalid input
                //
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
                    label.remove();
                },

                errorPlacement: function (error, element) {
                    error.insertAfter(element);
                },
                submitHandler: function () {
                    $ModifyForm.find("input").prop("disabled", false);

                    var updatePath = ROOTPAth + '/admin/sysusers/modSysuser';
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
        deleteSysuser: function ($that) {
            var id = $that.data("id");
            var delPath = ROOTPAth + '/admin/sysusers/delSysuser/' + id;
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
            var Path = ROOTPAth + '/admin/sysusers/resetPWD/' + id;
            $.ajax({
                url: Path,
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
        }
    };
    Utilitiy.init();
});