define(function (require, exports, module) {
    require("res-build/res/plugin/jquery-validation-1.13.1/dist/jquery.validate.min.js");
    require("res-build/res/plugin/bs-confirmation/bootstrap-confirmation.js");
    require("res-build/res/module/underscore/underscore.js");
    var tool = require("tool");
    var Page = require("page");
    var juicer = require("juicer");
    var pageIndex;

    var $table = $("#datatable_ajax");
    var $departmentList = $("#department-list");
    var $DepartmentForm = $('#vDepartmentForm');
    var $ModifyForm = $("#vDepartmentModifyForm");
    var $modifyModal = $('#modifyModal');
    var $addModal = $('#addModal');
    var $addRoletipModal = $('#modal-box');
    var pagelength = 10; //一页多少条；
    $('body').tooltip({
        selector: '.has-tooltip'
    });


    var listTpl = juicer(
        [
            '{@if total == 0}',
            '<tr>',
            '<td colspan="3" style="text-align:center">',
            '暂无记录,请添加',
            '</td>',

            '</tr>',

            '{@else}',
            '{@each data as item,index}',
            '{@if index%2==0}',
            '<tr role="row" class="odd" data-name="${item.name}">',
            '<tr role="row" class="odd" data-createdate="${item.createdate}">',

            '{@else}',
            '<tr role="row" class="even" data-name="${item.name}">',
            '<tr role="row" class="even" data-createdate="${item.createdate}">',
            '{@/if}',
            '    <td>${item.name}</td>',
            '    <td>${item.createdate}</td>',
            '    <td class=" heading">',

            ' <button type="button" class="btn btn-default btn-xs j-disable j-edit" data-toggle="modal" data-target="#modifyModal"  data-id="${item.id}"  data-name="${item.name}"><span class="iconfont iconfont-xs">&#xe62d;</span>查看</button>',
            ' <a class="btn btn-default btn-xs"  href="' + ROOTPAth + '/admin/hospitalDoctors/index?pcode=2&subcode=1&departmentid=${item.id}&departmentname=${item.name}" ><span class="iconfont iconfont-xs">&#xe617;</span>医生管理</a>',
            ' <button type="button" class="btn btn-default btn-xs j-disable j-queue" data-toggle="confirmation"  data-placement="top" data-departmentid="${item.id}"><span class="iconfont iconfont-xs">&#xe617;</span>生成队列</button>',
            ' <button type="button" class="btn btn-danger btn-xs j-disable j-del" data-toggle="confirmation"  data-placement="top" data-id="${item.id}"><span class="iconfont iconfont-xs">&#xe618;</span>删除</button>',
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
                        url: ROOTPAth + '/admin/departments/list',
                        type: 'POST',
                        dataType: 'json',
                        data: {
                            length: pagelength,
                            hospitalid: hospitalid,
                        },
                    success: function (res) {
                        var newData = $.extend({}, res);
                        $.each(newData.data, function (i, val) {

                            newData.data[i].currentpage = pageIndex.current;
                        });
                        tool.stopPageLoading();
                        $departmentList.find(".page-info-num").text(res.data.length);

                        $table.find("tbody").empty().append(listTpl.render(newData));
                        //删除权限
                        $table.find(".j-del").confirmation({
                            title: "确定删除该科室吗？",
                            btnOkLabel: "确定",
                            btnCancelLabel: "取消",
                            onConfirm: function (event, element) {
                                event.preventDefault();
                                self.deleteDepartment($(element));
                            }
                        });
                        $table.find(".j-queue").confirmation({
                            title: "确定吗？",
                            btnOkLabel: "确定",
                            btnCancelLabel: "取消",
                            onConfirm: function (event, element) {
                                event.preventDefault();
                                self.newQueue($(element));
                            }
                        });
                    }
                },
                pageName
            :
            "page",
                /*tpl: {
                 go: true //隐藏跳转到第几页
                 },*/
                getTotalPage
            :
            function (res) {
                //返回总页数
                return Math.ceil(res.total / pagelength);
            }

            ,
            pageWrapper: '.table-page'
        }
    );
pageIndex.reset();
//分页，修改每页显示数据
$departmentList.on("change", ".j-length", function () {
    var $this = $(this);
    pagelength = $this.val();
    var index = $this.get(0).selectedIndex;
    $departmentList.find(".j-length").not(this).get(0).selectedIndex = index;
    pageIndex.reset();
});

//我要编辑
$ModifyForm.on("click", ".j-form-edit", function (event) {
    var formDom = event.delegateTarget;
    $(this).hide();
    $(formDom).find(".j-form-save").show();

    $(formDom).find("input").prop("disabled", false);
    $ModifyForm.find("input[name=name]").prop("disabled", true);
    // $(event.relatedTarget)
});
//添加表单初始化
$addModal.on('show.bs.modal', function (event) {
    $DepartmentForm[0].reset();
    $DepartmentForm.find("input").removeAttr("aria-describedby");
    $DepartmentForm.find("input").removeAttr("aria-invalid");
    $DepartmentForm.find("input").removeAttr("aria-required");
    $DepartmentForm.find("div").removeClass("has-error");
    $DepartmentForm.find("span").remove();
})
//修改表单初始化
$modifyModal.on('show.bs.modal', function (event) {
    var $modal = $ModifyForm;
    $modal.find("input").prop("disabled", true);
    $modal.find("input").removeAttr("aria-describedby");
    $modal.find("input").removeAttr("aria-invalid");
    $modal.find("input").removeAttr("aria-required");
    $modal.find("div").removeClass("has-error");
    $modal.find("span").remove();
    var button = $(event.relatedTarget); // Button that triggered the modal
    var id = button.data("id");
    var name = button.data("name");

    $modal.find('input[name=id]').val(id);
    $modal.find('input[name=name]').val(name);

    $modal.find(".j-form-save").hide();
    $modal.find(".j-form-edit").show();
});

//表单验证-添加用户
$DepartmentForm.validate({
    rules: {
        name: {
            required: true,
            remote: { //自带远程验证存在的方法
                url: ROOTPAth + '/admin/departments/sameName',
                type: "POST",
                dataType: "json",
                data: {
                    name: function () {
                        return $DepartmentForm.find('input[name="name"]').val();
                    },
                    hospitalid: hospitalid
                }
            }
        },
    },
    messages: {
        name: {
            required: "科室名不能为空",
            remote: "科室名重复"
        }
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
        var savePath = ROOTPAth + '/admin/departments/newDepartment';


        $.ajax({
            type: "POST",
            url: savePath,
            data: $DepartmentForm.serialize(),
            beforeSend: function () {
                tool.startPageLoading();
            },
            dataType: "json",
            success: function (data) {
                console.log(data);
                tool.stopPageLoading();
                if (data.code == 1) {
                    $addModal.modal("hide");
                    $addRoletipModal.find(".dialogtip-msg").html("科室添加成功");
                    $addRoletipModal.modal('show');
                }
                else {
                    $("#ajax_fail").find("h4").html(data.message);
                    $("#ajax_fail").modal("show")
                    $DepartmentForm.form();
                }
                pageIndex.reset();
            },

            error: function () {
                tool.stopPageLoading();
                $("#ajax_fail").find("h4").html("系统科室添加功能出错");
                $("#ajax_fail").modal("show")
            },
        });

    }
});

//表单验证-修改用户
$ModifyForm.validate({
    rules: {
        // name: {
        //     required: true,
        //     remote: { //自带远程验证存在的方法
        //         url: ROOTPAth + '/admin/departments/sameName',
        //         type: "POST",
        //         dataType: "json",
        //         data: {
        //             name: function () {
        //                 return $DepartmentForm.find('input[name="name"]').val();
        //             },
        //             hospitalid: hospitalid
        //         }
        //     }
        // },
    },
    messages: {
        // name: {
        //     required: "科室名不能为空",
        //     remote: "科室名重复"
        // }
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
        $ModifyForm.find("input").prop("disabled",false);

        var updatePath = ROOTPAth + '/admin/departments/modDepartment';
        $.post(updatePath, $ModifyForm.serialize(), function (data) {
            if (data.code == 1) {
                $('#modifyModal').modal('hide');
                $addRoletipModal.find(".dialogtip-msg").html("科室修改成功");
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
deleteDepartment: function ($that) {
    var id = $that.data("id");
    var delPath = ROOTPAth + '/admin/departments/delDepartment/' + id;
    $.ajax({
        url: delPath,
        type: "POST",
        success: function (data) {
            if (data.code == 1) {
                pageIndex.reset();
            } else {
                $("#ajax_fail").find("h4").html(data.message);
                $("#ajax_fail").modal("show")
            }
        }
    });

},
            newQueue: function ($that) {
                var departmentid = $that.data("departmentid");
                var delPath = ROOTPAth + '/admin/departmentqueues/newQueue/'
                $.ajax({
                    url: delPath,
                    type: "POST",
                    data:{
                        departmentid:departmentid,
                    },
                    success: function (data) {
                        if (data.code == 1) {
                            $addRoletipModal.find(".dialogtip-msg").html("科室队列生成成功");
                            $addRoletipModal.modal('show');
                            pageIndex.reset();
                        } else {
                            $("#ajax_fail").find("h4").html(data.message);
                            $("#ajax_fail").modal("show")
                        }
                    }
                });

            }
}
;
Utilitiy.init();
})
;