define(function (require, exports, module) {
    require("res-build/res/plugin/jquery-validation-1.13.1/dist/jquery.validate.min.js");
    require("res-build/res/plugin/bs-confirmation/bootstrap-confirmation.js");
    require("res-build/res/module/underscore/underscore.js");
    var tool = require("tool");
    var Page = require("page");
    var juicer = require("juicer");
    var pageIndex;
    var $table = $("#datatable_ajax");
    var $checkreportList = $("#checkreport-list");
    var $CheckreportForm = $('#vCheckreportForm');
    var $ModifyForm = $("#vCheckreportModifyForm");
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
            '<tr role="row" class="odd" data-part="${item.part}">',
            '<tr role="row" class="odd" data-method="${item.method}">',
            '<tr role="row" class="odd" data-checktime="${item.checktime}">',
            '<tr role="row" class="odd" data-status="${item.status}">',
            '<tr role="row" class="odd" data-options="${item.options}">',
            '<tr role="row" class="odd" data-advice="${item.status}">',

            '{@else}',
            '<tr role="row" class="even" data-part="${item.part}">',
            '<tr role="row" class="even" data-method="${item.method}">',
            '<tr role="row" class="even" data-checktime="${item.checktime}">',
            '<tr role="row" class="even" data-status="${item.status}">',
            '<tr role="row" class="odd" data-options="${item.options}">',
            '<tr role="row" class="odd" data-advice="${item.advice}">',

            '{@/if}',
            '    <td>${item.part}</td>',
            '    <td>${item.method}</td>',
            '    <td>${item.checktime}</td>',
            // '{@if item.status==1}',
            // '    <td>已审核</td>',
            // '{@/if}',
            // '{@if item.status==2}',
            // '<td>未审核</td>',
            // '{@/if}',
            '  <td>${item.options}</td>',
            '  <td>${item.advice}</td>',
            '    <td class=" heading">',

            ' <button type="button" class="btn btn-default btn-xs j-disable j-edit" data-toggle="modal" data-target="#modifyModal"  data-id="${item.id}" data-part="${item.part}" data-method="${item.method}"  data-checktime="${item.checktime}"  data-status="${item.status}"  data-options="${item.options}" data-advice="${item.advice}" data-auditorid="${item.auditorid}" data-auditoraccount="${item.auditor.account}" data-auditorname="${item.auditor.name}"  data-examtime="${item.examtime}"  data-date="${item.date}"><span class="iconfont iconfont-xs">&#xe62d;</span>查看</button>',
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
                    url: ROOTPAth + '/admin/checkreports/list',
                    type: 'POST',
                    dataType: 'json',
                    data: {
                        startdate: $("#starttime").val(),
                        enddate: $("#endtime").val(),
                        patientid: patientid,
                        length: pagelength

                    },
                    success: function (res) {
                        var newData = $.extend({}, res);
                        $.each(newData.data, function (i, val) {

                            newData.data[i].currentpage = pageIndex.current;
                        });
                        tool.stopPageLoading();
                        $checkreportList.find(".page-info-num").text(res.data.length);

                        $table.find("tbody").empty().append(listTpl.render(newData));
                        //删除权限
                        $table.find(".j-del").confirmation({
                            title: "确定删除该表单吗？",
                            btnOkLabel: "确定",
                            btnCancelLabel: "取消",
                            onConfirm: function (event, element) {
                                event.preventDefault();
                                self.deleteCheckreport($(element));
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
            pageIndex.reset();
            //分页，修改每页显示数据
            $checkreportList.on("change", ".j-length", function () {
                var $this = $(this);
                pagelength = $this.val();
                var index = $this.get(0).selectedIndex;
                $checkreportList.find(".j-length").not(this).get(0).selectedIndex = index;
                pageIndex.reset();
            });
            //修改界面关闭,下拉框消失
            $modifyModal.on("hide.modal", function (event) {
                $modifyModal.find(".list").hide();
            });
            //点击查询
            $("#search").on('click', function (event) {
                event.preventDefault();
                pageIndex.current = 1;
                $.ajax({
                    url: ROOTPAth + '/admin/checkreports/list',
                    type: 'POST',
                    dataType: 'json',
                    data: {
                        startdate: function () {
                            return $("#starttime").val();
                        },
                        enddate: function () {
                            return $("#endtime").val();
                        },
                        patientid: patientid,
                        length: pagelength,
                        page: 1
                    },
                    success: function (res) {
                        var newData = $.extend({}, res);
                        $.each(newData.data, function (i, val) {

                            newData.data[i].currentpage = pageIndex.current;
                        });
                        tool.stopPageLoading();
                        $checkreportList.find(".page-info-num").text(res.data.length);

                        $table.find("tbody").empty().append(listTpl.render(newData));
                        //删除权限
                        $table.find(".j-del").confirmation({
                            title: "确定删除该表单吗？",
                            btnOkLabel: "确定",
                            btnCancelLabel: "取消",
                            onConfirm: function (event, element) {
                                event.preventDefault();
                                self.deleteCheckreport($(element));
                            }
                        });
                    }
                })
            });
            //我要编辑
            $ModifyForm.on("click", ".j-form-edit", function (event) {
                var formDom = event.delegateTarget;
                $(this).hide();
                $(formDom).find(".j-form-save").show();
                $(formDom).find("input").prop("disabled", false);
                $(formDom).find("textarea").prop("disabled", false);
                $(formDom).find("select").prop("disabled", false);
                // $(event.relatedTarget)
            });
            //修改表单初始化
            $modifyModal.on('show.modal', function (event) {
                var $modal = $ModifyForm;
                $modal.find("input").prop("disabled", true);
                $modal.find("textarea").prop("disabled", true);
                $modal.find("select").prop("disabled", true);
                $modal.find("input").removeAttr("aria-describedby");
                $modal.find("input").removeAttr("aria-invalid");
                $modal.find("input").removeAttr("aria-required");
                $modal.find("textarea").removeAttr("aria-describedby");
                $modal.find("textarea").removeAttr("aria-invalid");
                $modal.find("textarea").removeAttr("aria-required");
                $modal.find("select").removeAttr("aria-describedby");
                $modal.find("select").removeAttr("aria-invalid");
                $modal.find("select").removeAttr("aria-required");
                $modal.find("div").removeClass("has-error");
                $modal.find("span").remove();
                var button = $(event.relatedTarget); // Button that triggered the modal
                var id = button.data("id");
                var part = button.data("part");
                var method = button.data("method");
                var auditorid = button.data("auditorid");
                var auditorname = button.data("auditorname");
                var auditor = button.data("auditoraccount");
                var date = button.data("date");
                var advice = button.data("advice");
                var options = button.data("options");
                var checktime = button.data("checktime");
                var status = button.data("status");
                var examtime = button.data("examtime");
                var auditoraccount = button.data("auditoraccount");
                $modal.find('input[name=id]').val(id);
                $modal.find('input[name=date]').val(date);
                $modal.find('input[name=part]').val(part);
                $modal.find('input[name=method]').val(method);
                $modal.find('input[name=auditorid]').val(auditorid);
                $modal.find('input[name=auditoraccount]').val(auditoraccount);
                $modal.find('input[name=auditorname]').val(auditorname);
                $modal.find('input[name=auditor]').val(auditor);
                $modal.find('textarea[name=advice]').val(advice);
                $modal.find('textarea[name=options]').val(options);
                $modal.find('input[name=checktime]').val(checktime);
                $modal.find('select[name=status]').val(status);
                $modal.find('input[name=examtime]').val(examtime);
                // $modal.find(".j-form-save").hide();
                // $modal.find(".j-form-edit").show();
            });
            //时间日期截取
            $("#mod_checktime").bind("input onpropertychange", function (e) {
                $("#mod_date").val($("#mod_checktime").val().substring(0, 11));
            });


            $("#mod_auditor").keyup(function (e) {
                if (e.keyCode != 40 && e.keyCode != 38) {
                    get_auditors_modal($("#mod_auditor"), 2);
                }
            }).focus(function () {
                this.select();
            });
            // //表单验证-修改表单
            // $ModifyForm.validate({
            //     rules: {
            //         status: "required",
            //         checktime: "required",
            //         auditoraccount: {
            //             required: true,
            //             remote: {
            //                 url: ROOTPAth + "/admin/hospitalDoctors/hasAccount",
            //                 type: "post",
            //                 dataType:"json",
            //                 data: {
            //                     hospitalid: hospitalid,
            //                     account: $ModifyForm.find("input[name=auditoraccount]").val()
            //                 }
            //             }
            //         },
            //         part:"required",
            //         method:"required",
            //         advice:"required",
            //         options:"required",
            //         examtime: "required"
            //     },
            //     messages: {
            //         status: "请选择状态",
            //         auditoraccount: {
            //             required: "请输入审核人账号",
            //             remote: "医生名不存在"
            //         },
            //         checktime: {
            //             required: "请选择检验时间",
            //         },
            //         examtime: "请选择审核时间",
            //         part:"请输入检查部位",
            //         method:"请输入检查方法",
            //         advice:"请输入医嘱",
            //         options:"请输入诊断意见"
            //     },
            //     errorElement: 'span', //default input error message container
            //     errorClass: 'help-block', // default input error message class
            //     //focusInvalid: false, // do not focus the last invalid input
            //
            //
            //     invalidHandler: function (event, validator) { //display error alert on form submit
            //         //	                $('.alert-danger', $('.login-form')).show();
            //     },
            //     highlight: function (element) { // hightlight error inputs
            //         $(element)
            //             .closest('.form-group').addClass('has-error'); // set error class to the control group
            //     },
            //
            //     success: function (label) {
            //         var strId = label.closest('.form-group').find("input").attr("id") == "undefined" ? "" : label.closest('.form-group').find("input").attr("id");
            //         if (strId == "mod_auditor") {
            //             //手动输入医生全名需要查询医生信息
            //             if ($("#mod_auditorid").val() == "" && !label.closest('.form-group').hasClass('has-error')) {
            //                 $.ajax({
            //                     url: ROOTPAth + '/admin/hospitalDoctors/doctorInfo',
            //                     type: "POST",
            //                     dataType: "json",
            //                     data: {
            //                         account: $("#mod_auditor").val(),
            //                         hospitalid: hospitalid
            //                     },
            //                     success: function (data) {
            //                         $("#mod_auditorid").val(data.id);
            //                         $("#mod_auditorname").val(data.name);
            //                     }
            //                 });
            //             }
            //         }
            //         label.closest('.form-group').removeClass('has-error');
            //         label.remove();
            //     },
            //
            //     errorPlacement: function (error, element) {
            //         var strId = element.closest('.form-group').find("input").attr("id") == "undefined" ? "" : element.closest('.form-group').find("input").attr("id");
            //         if (strId == "mod_auditor" && element.closest('.form-group').hasClass('has-error')) {
            //             $("#mod_auditorid").val("");
            //             $("#mod_auditorname").val("");
            //         }
            //         error.insertAfter(element);
            //     },
            //     submitHandler: function () {
            //
            //
            //         var updatePath = ROOTPAth + '/admin/checkreports/modCheckreport';
            //         $.post(updatePath, $ModifyForm.serialize(), function (data) {
            //             if (data.code === 1) {
            //                 $('#modifyModal').modal('hide');
            //                 $addRoletipModal.find(".dialogtip-msg").html("表单修改成功");
            //                 $addRoletipModal.modal('show');
            //                 pageIndex.reset();
            //             }
            //             else {
            //                 $("#ajax_fail").find("h4").html(data.message);
            //                 $("#ajax_fail").modal("show")
            //             }
            //         });
            //
            //     }
            // });
        },
        deleteCheckreport: function ($that) {
            var id = $that.data("id");
            var delPath = ROOTPAth + '/admin/checkreports/delCheckreport/' + id;
            $.ajax({
                url: delPath,
                type: "POST",
                success: function (data) {
                    if (data.code === 1) {
                        $addRoletipModal.find(".dialogtip-msg").html(data.message);
                        $addRoletipModal.modal('show');
                        pageIndex.reset();
                    } else {
                        $("#ajax_fail").find("h4").html(data.message);
                        $("#ajax_fail").modal("show")
                    }
                }
            });

        },

    };
    // option 1 ：添加   ； 2：修改
    function get_auditors_modal(obj, option) {
        var t = setTimeout(function () {
            $.ajax({
                url: ROOTPAth + '/admin/hospitalDoctors/listDoctorLikeByHospital',
                type: 'POST',
                dataType: 'json',
                data: {
                    hospitalid: hospitalid,
                    account: $(obj).val()
                },
                success: function (data) {
                    data = data.data;
                    if (option == 1) {
                        var $list = $("#add_auditorList");
                    }
                    else {
                        var $list = $("#mod_auditorList");
                    }
                    $list.show();
                    $list.html("");
                    $.each(data, function (index, el) {
                        var html = $("<li  data-id='" + el.id + "'  data-name='" + el.name + "'>" + el.account + "</li>");
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
                        if (option == 1) {
                            $("#add_auditorid").val($(this).data("id"));
                            $("#add_auditorname").val($(this).data("name"));
                        }
                        else {
                            $("#mod_auditorid").val($(this).data("id"));
                            $("#mod_auditorname").val($(this).data("name"));
                        }
                        $list.hide();
                        $CheckreportForm.validate().element($(obj));
                    });
                },
            });
        }, 500);
    }


    $('#mod_checktime').datetimepicker({
        format: 'yyyy-mm-dd hh:ii',
    });

    $('#mod_examtime').datetimepicker({
        format: 'yyyy-mm-dd hh:ii',
    });
    $('#starttime').datepicker({
        dateFormat: "yy-mm-dd",
        selectOtherMonths: true,
        // yearRange: "-100:+0",
        changeMonth: true,
        changeYear: true,
        inline: true
    });
    $('#endtime').datepicker({

        dateFormat: "yy-mm-dd",
        selectOtherMonths: true,
        // yearRange: "-100:+0",
        changeMonth: true,
        changeYear: true,
        inline: true
    });
    function get_search() {
        pageIndex.reset();
    }

    Utilitiy.init();
});