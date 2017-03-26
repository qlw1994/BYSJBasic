define(function (require, exports, module) {
    require("res-build/res/plugin/jquery-validation-1.13.1/dist/jquery.validate.min.js");
    require("res-build/res/plugin/bs-confirmation/bootstrap-confirmation.js");
    require("res-build/res/module/underscore/underscore.js");
    var tool = require("tool");
    var Page = require("page");
    var juicer = require("juicer");
    var pageIndex;
    var $table = $("#datatable_ajax");
    var $inspectionreportList = $("#inspectionreport-list");
    var $InspectreportForm = $('#vInspectreportForm');
    var $ModifyForm = $("#vInspectreportModifyForm");
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
            '<td colspan="4" style="text-align:center">',
            '暂无记录,请添加',
            '</td>',

            '</tr>',

            '{@else}',
            '{@each data as item,index}',
            '{@if index%2==0}',
            '<tr role="row" class="odd" data-inspectname="${item.inspectname}">',
            '<tr role="row" class="odd" data-inspecttime="${item.inspecttime}">',
            '<tr role="row" class="odd" data-status="${item.status}">',
            '{@else}',
            '<tr role="row" class="even" data-inspectname="${item.inspectname}">',
            '<tr role="row" class="even" data-inspecttime="${item.inspecttime}">',
            '<tr role="row" class="even" data-status="${item.status}">',
            '{@/if}',
            '    <td>${item.inspectname}</td>',
            '    <td>${item.inspecttime}</td>',
            '{@if item.status==1}',
            '    <td>已审核</td>',
            '{@/if}',
            '{@if item.status==2}',
            '<td>未审核</td>',
            '{@/if}',
            '    <td class=" heading">',

            ' <button type="button" class="btn btn-default btn-xs j-disable j-edit" data-toggle="modal" data-target="#modifyModal"  data-id="${item.id}" data-auditorid="${item.auditorid}" data-auditorname="${item.auditor.name}"   data-auditoraccount="${item.auditor.account}"  data-examtime="${item.examtime}"  data-inspectname="${item.inspectname}"  data-inspecttime="${item.inspecttime}"  data-date="${item.date}"  data-status="${item.status}"><span class="iconfont iconfont-xs">&#xe62d;</span>修改</button>',
            ' <a class="btn btn-default btn-xs"  href="' + ROOTPAth + '/admin/inspectitems/index?pcode=2&subcode=1&inspectionid=${item.id}" ><span class="iconfont iconfont-xs">&#xe617;</span> 查看详情</a>',
            ' <button type="button" class="btn btn-danger btn-xs j-disable j-del" data-toggle="confirmation"  data-placement="top" data-id="${item.id}"><span class="iconfont iconfont-xs">&#xe618;</span>删除</button>',

            '    </td>',
            '</tr>',
            '{@/each}',
            '{@/if}'
        ].join(""));
    var Utilitiy = {
        init: function () {

            tool.startPageLoading();
            $("#service").val(service);
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
                    url: ROOTPAth + '/admin/inspectreports/list',
                    type: 'POST',
                    dataType: 'json',
                    data: {
                        patientid: patientid,
                        length: pagelength

                    },
                    success: function (res) {
                        var newData = $.extend({}, res);
                        $.each(newData.data, function (i, val) {

                            newData.data[i].currentpage = pageIndex.current;
                        });
                        tool.stopPageLoading();
                        $inspectionreportList.find(".page-info-num").text(res.data.length);

                        $table.find("tbody").empty().append(listTpl.render(newData));
                        //删除权限
                        $table.find(".j-del").confirmation({
                            title: "确定删除该表单吗？",
                            btnOkLabel: "确定",
                            btnCancelLabel: "取消",
                            onConfirm: function (event, element) {
                                event.preventDefault();
                                self.deleteInspectreport($(element));
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
            $inspectionreportList.on("change", ".j-length", function () {
                var $this = $(this);
                pagelength = $this.val();
                var index = $this.get(0).selectedIndex;
                $inspectionreportList.find(".j-length").not(this).get(0).selectedIndex = index;
                pageIndex.reset();
            });
            //添加界面关闭,下拉框消失
            $addModal.on("hide.bs.modal", function (event) {
                $addModal.find(".list").hide();
            });
            //修改界面关闭,下拉框消失
            $modifyModal.on("hide.bs.modal", function (event) {
                $modifyModal.find(".list").hide();
            });
            //我要编辑
            $ModifyForm.on("click", ".j-form-edit", function (event) {
                var formDom = event.delegateTarget;
                $(this).hide();
                $(formDom).find(".j-form-save").show();
                $(formDom).find("input").prop("disabled", false);
                $(formDom).find("select").prop("disabled", false);
                // $(event.relatedTarget)
            });
            //添加表单初始化
            $addModal.on('show.bs.modal', function (event) {
                $InspectreportForm[0].reset();
                $InspectreportForm.find("input").removeAttr("aria-describedby");
                $InspectreportForm.find("input").removeAttr("aria-invalid");
                $InspectreportForm.find("input").removeAttr("aria-required");
                $InspectreportForm.find("select").removeAttr("aria-describedby");
                $InspectreportForm.find("select").removeAttr("aria-invalid");
                $InspectreportForm.find("select").removeAttr("aria-required");
                $InspectreportForm.find("div").removeClass("has-error");
                $InspectreportForm.find("span").remove();
            })
            //修改表单初始化
            $modifyModal.on('show.bs.modal', function (event) {
                var $modal = $ModifyForm;
                $modal.find("input").prop("disabled", true);
                $modal.find("select").prop("disabled", true);
                $modal.find("input").removeAttr("aria-describedby");
                $modal.find("input").removeAttr("aria-invalid");
                $modal.find("input").removeAttr("aria-required");
                $modal.find("select").removeAttr("aria-describedby");
                $modal.find("select").removeAttr("aria-invalid");
                $modal.find("select").removeAttr("aria-required");
                $modal.find("div").removeClass("has-error");
                $modal.find("span").remove();
                var button = $(event.relatedTarget); // Button that triggered the modal
                var id = button.data("id");
                var inspectname = button.data("inspectname");
                var inspecttime = button.data("inspecttime");
                var status = button.data("status");
                var auditorid = button.data("auditorid");
                var auditorname = button.data("auditorname");
                var date = button.data("date");
                var examtime = button.data("examtime");
                $modal.find('input[name=id]').val(id);
                $modal.find('input[name=date]').val(date);
                $modal.find('input[name=auditor]').val(auditoraccount);
                $modal.find('input[name=auditorid]').val(auditorid);
                $modal.find('input[name=auditorname]').val(auditorname);
                $modal.find('input[name=inspectname]').val(inspectname);
                $modal.find('input[name=inspecttime]').val(inspecttime);
                $modal.find('input[name=examtime]').val(examtime);
                $modal.find('select[name=status]').val(status);
                $modal.find(".j-form-save").hide();
                $modal.find(".j-form-edit").show();
            });
            //时间日期截取
            $("#add_inspecttime").bind("input onpropertychange", function (e) {
                $("#add_date").val($("#add_inspecttime").val().substring(0, 11));
            });
            $("#mod_inspecttime").bind("input onpropertychange", function (e) {
                $("#mod_date").val($("#mod_inspecttime").val().substring(0, 11));
            });
            //审核人下拉框绑定
            $("#add_auditor").keyup(function (e) {
                if (e.keyCode != 40 && e.keyCode != 38) {
                    get_auditors_modal($("#add_auditor"), 1);
                }
            }).focus(function () {
                this.select();
            });
            $("#mod_auditor").keyup(function (e) {
                if (e.keyCode != 40 && e.keyCode != 38) {
                    get_auditors_modal($("#mod_auditor"), 2);
                }
            }).focus(function () {
                this.select();
            });
            //表单验证-添加表单
            $InspectreportForm.validate({
                rules: {
                    status: "required",
                    inspecttime: "required",
                    auditor: {
                        required: true,
                        remote: {
                            url: ROOTPAth + "/admin/hospitalDoctors/hasAccount",
                            type: "post",
                            date: {
                                hospitalid: hospitalid,
                                account: $InspectreportForm.find("input[name=auditor]").val()
                            }
                        }
                    },
                    examtime: "required",
                },
                messages: {
                    status: "请选择状态",
                    auditor: {
                        required: "请输入审核人账号",
                        remote: "药品名不存在"
                    },
                    inspecttime: {
                        required: "请选择检验时间",
                    },
                    examtime: "请选择审核时间"

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
                    var strId = label.closest('.form-group').find("input").attr("id") == "undefined" ? "" : label.closest('.form-group').find("input").attr("id");
                    if (strId == "add_auditor") {
                        //手动输入药品全名需要查询药品信息
                        if ($("#add_auditor").val() == "" && !label.closest('.form-group').hasClass('has-error')) {
                            $.ajax({
                                url: ROOTPAth + '/admin/hospitalDoctors/doctorInfo',
                                type: "POST",
                                dataType: "json",
                                data: {
                                    account: $("#add_auditor").val(),
                                    hospitalid: hospitalid
                                },
                                success: function (data) {
                                    $("#add_auditorid").val(data.id);
                                    $("#add_auditorname").val(data.name);
                                }
                            });
                        }
                    }
                    label.closest('.form-group').removeClass('has-error');
                    label.remove();
                },

                errorPlacement: function (error, element) {
                    var strId = element.closest('.form-group').find("input").attr("id") == "undefined" ? "" : element.closest('.form-group').find("input").attr("id");
                    if (strId == "add_auditor"&& element.closest('.form-group').hasClass('has-error')) {
                        $("#add_auditorid").val("");
                        $("#add_auditorname").val("");
                    }
                    error.insertAfter(element);
                },
                submitHandler: function () {
                    var savePath = ROOTPAth + '/admin/inspectreports/newInspectreport';
                    $InspectreportForm.find("input").prop("disabled", false);
                    $.ajax({
                        type: "POST",
                        url: savePath,
                        dataType: "json",
                        data: $InspectreportForm.serialize(),
                        beforeSend: function () {
                            tool.startPageLoading();
                        },

                        success: function (data) {
                            console.log(data);
                            tool.stopPageLoading();
                            if (data.code == 1) {
                                $addModal.modal("hide");
                                $addRoletipModal.find(".dialogtip-msg").html("表单添加成功");
                                $addRoletipModal.modal('show');
                            }
                            else {
                                $("#ajax_fail").find("h4").html(data.message);
                                $("#ajax_fail").modal("show")
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
            //表单验证-修改表单
            $ModifyForm.validate({
                    rules: {
                        status: "required",
                        inspecttime: "required",
                        auditor: {
                            required: true,
                            remote: {
                                url: ROOTPAth + "/admin/hospitalDoctors/hasAccount",
                                type: "post",
                                date: {
                                    hospitalid: hospitalid,
                                    account: $ModifyForm.find("input[name=auditor]").val()
                                }
                            }
                        },
                        examtime: "required",
                    },
                    messages: {
                        status: "请选择状态",
                        auditor: {
                            required: "请输入审核人账号",
                            remote: "药品名不存在"
                        },
                        inspecttime: {
                            required: "请选择检验 时间",
                        },
                        examtime: "请选择审核时间"

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
                    var strId = label.closest('.form-group').find("input").attr("id") == "undefined" ? "" : label.closest('.form-group').find("input").attr("id");
                    if (strId == "mod_auditor") {
                        //手动输入药品全名需要查询药品信息
                        if ($("#mod_auditorid").val() == "" && !label.closest('.form-group').hasClass('has-error')) {
                            $.ajax({
                                url: ROOTPAth + '/admin/hospitalDoctors/doctorInfo',
                                type: "POST",
                                dataType: "json",
                                data: {
                                    account: $("#mod_auditor").val(),
                                    hospitalid: hospitalid
                                },
                                success: function (data) {
                                    $("#mod_auditorid").val(data.id);
                                    $("#mod_auditorname").val(data.name);
                                }
                            });
                        }
                    }
                    label.closest('.form-group').removeClass('has-error');
                    label.remove();
                },

                errorPlacement: function (error, element) {
                    var strId = element.closest('.form-group').find("input").attr("id") == "undefined" ? "" : element.closest('.form-group').find("input").attr("id");
                    if (strId == "mod_auditor"&& element.closest('.form-group').hasClass('has-error')) {
                        $("#mod_auditorid").val("");
                        $("#mod_auditorname").val("");
                    }
                    error.insertAfter(element);
                },
                submitHandler: function () {


                    var updatePath = ROOTPAth + '/admin/inspectreports/modInspectreport';
                    $.post(updatePath, $ModifyForm.serialize(), function (data) {
                        if (data.code === 1) {
                            $('#modifyModal').modal('hide');
                            $addRoletipModal.find(".dialogtip-msg").html("表单修改成功");
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
        deleteInspectreport: function ($that) {
            var id = $that.data("id");
            var delPath = ROOTPAth + '/admin/inspectreports/delInspectreport/' + id;
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
                        var html = $("<li  data-id='" + el.id + "' data-name='" + el.name + "'>" + el.account + "</li>");
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
                        $InspectreportForm.validate().element($(obj));
                    });
                },
            });
        }, 500);
    }

    $('#add_inspecttime').datetimepicker({
        format: 'yyyy-mm-dd hh:ii',
    });
    $('#mod_inspecttime').datetimepicker({
        format: 'yyyy-mm-dd hh:ii',
    });
    $('#add_examtime').datetimepicker({
        format: 'yyyy-mm-dd hh:ii',
    });
    $('#mod_examtime').datetimepicker({
        format: 'yyyy-mm-dd hh:ii',
    });
    Utilitiy.init();
});