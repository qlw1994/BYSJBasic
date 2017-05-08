define(function (require, exports, module) {
    require("res-build/res/plugin/jquery-validation-1.13.1/dist/jquery.validate.min.js");
    require("res-build/res/plugin/bs-confirmation/bootstrap-confirmation.js");
    require("res-build/res/module/underscore/underscore.js");
    var tool = require("tool");
    var Page = require("page");
    var juicer = require("juicer");
    var pageIndex;
    var $add_total = $("#add_total");
    var $AddUI_add = $("#add_details");
    var $AddUI_del = $("#del_details");
    var $addOrdermoney = $("#add_orderMoney");
    var $table = $("#datatable_ajax");
    var $drugorderList = $("#drugorder-list");
    var $DrugorderForm = $('#vDrugorderForm');
    var $ModifyForm = $("#vDrugorderModifyForm");
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
            '<tr role="row" class="odd" data-advice="${item.advice}">',
            '<tr role="row" class="odd" data-createdate="${item.createdate}">',
            '<tr role="row" class="odd" data-money="${item.money}">',
            '<tr role="row" class="odd" data-status="${item.status}">',
            '{@else}',
            '<tr role="row" class="even" data-advice="${item.advice}">',
            '<tr role="row" class="even" data-createdate="${item.createdate}">',
            '<tr role="row" class="even" data-money="${item.money}">',
            '<tr role="row" class="even" data-status="${item.status}">',
            '{@/if}',
            '    <td>${item.advice}</td>',
            '    <td>${item.createdate}</td>',
            '    <td>${item.money}</td>',
            '{@if item.status==0}',
            '    <td>未支付</td>',
            '{@/if}',
            '{@if item.status==1}',
            '<td>已支付</td>',
            '{@/if}',
            '{@if item.status==2}',
            '    <td>已取药</td>',
            '{@/if}',
            '    <td class=" heading">',

            ' <button type="button" class="btn btn-default btn-xs j-disable j-edit" data-toggle="modal" data-target="#modifyModal"  data-id="${item.id}" data-advice="${item.advice}" data-status="${item.status}"><span class="iconfont iconfont-xs">&#xe62d;</span>修改</button>',
            ' <a class="btn btn-default btn-xs"  href="' + ROOTPAth + '/admin/drugorderdetails/index?pcode=2&subcode=1&drugorderid=${item.id}" ><span class="iconfont iconfont-xs">&#xe617;</span> 查看详情</a>',
            '{@if item.total==0}',
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
                    url: ROOTPAth + '/admin/drugorders/list',
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
                        $drugorderList.find(".page-info-num").text(res.data.length);

                        $table.find("tbody").empty().append(listTpl.render(newData));
                        //删除权限
                        $table.find(".j-del").confirmation({
                            title: "确定删除该订单吗？",
                            btnOkLabel: "确定",
                            btnCancelLabel: "取消",
                            onConfirm: function (event, element) {
                                event.preventDefault();
                                self.deleteDrugorder($(element));
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
            $drugorderList.on("change", ".j-length", function () {
                var $this = $(this);
                pagelength = $this.val();
                var index = $this.get(0).selectedIndex;
                $drugorderList.find(".j-length").not(this).get(0).selectedIndex = index;
                pageIndex.reset();
            });
            //添加界面关闭,下拉框消失
            $addModal.on("hide.modal", function (event) {
                $addModal.find(".list").hide();
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
            //添加表单初始化
            $addModal.on('show.modal', function (event) {
                $DrugorderForm[0].reset();
                $DrugorderForm.find("input").removeAttr("aria-describedby");
                $DrugorderForm.find("input").removeAttr("aria-invalid");
                $DrugorderForm.find("input").removeAttr("aria-required");
                $DrugorderForm.find("textarea").removeAttr("aria-describedby");
                $DrugorderForm.find("textarea").removeAttr("aria-invalid");
                $DrugorderForm.find("textarea").removeAttr("aria-required");
                $DrugorderForm.find("select").removeAttr("aria-describedby");
                $DrugorderForm.find("select").removeAttr("aria-invalid");
                $DrugorderForm.find("select").removeAttr("aria-required");
                $DrugorderForm.find("div").removeClass("has-error");
                $DrugorderForm.find("span").remove();
                $("#add_drugAmount1").prop("disabled", true);
                $("#add_drugMoney1").prop("disabled", true);
                $("#add_drugPrice1").prop("disabled", true);
                $("#add_drugFormat1").prop("disabled", true);
                for (var i = 2; i <= $add_total.val() * 1; i++) {
                    $("#add_drugMoney_" + i).remove();
                    $("#add_drugId_" + i).remove();
                    $("#add_drugName_" + i).remove();
                    $("#add_drugPrice_" + i).remove();
                    $("#add_drugAmount_" + i).remove();
                    $("#add_drugFormat_" + i).remove();
                }
                $add_total.val(1);
            })
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
                var advice = button.data("advice");
                var status = button.data("status");
                $modal.find('input[name=id]').val(id);
                $modal.find('textarea[name=advice]').val(advice);
                $modal.find('select[name=status]').val(status);
                $modal.find(".j-form-save").hide();
                $modal.find(".j-form-edit").show();
            });
            $("#add_drugName1").keyup(function (e) {
                if (e.keyCode != 40 && e.keyCode != 38) {
                    get_drugs_addModel($("#add_drugName1"));
                }
            }).focus(function () {
                this.select();
            });
            //药品数量变化
            $("#add_drugAmount1").bind("input onpropertychange", function (e) {
                var score = /^[0-9]*$/;
                if (score.test($(this).val())) {
                    if ($("#add_drugMoney1").val() == "") {
                        $("#add_drugMoney1").val((1.00 * $(this).val() * $("#add_drugPrice1").val()).toFixed(2));
                        $addOrdermoney.val(($addOrdermoney.val() * 1.00 + $("#add_drugMoney1").val() * 1.00).toFixed(2));
                    } else {
                        $addOrdermoney.val(($addOrdermoney.val() * 1.00 - $("#add_drugMoney1").val() * 1.00).toFixed(2));
                        $("#add_drugMoney1").val((1.00 * $(this).val() * $("#add_drugPrice1").val()).toFixed(2));
                        $addOrdermoney.val(($addOrdermoney.val() * 1.00 + $("#add_drugMoney1").val() * 1.00).toFixed(2));
                    }
                }
                ;
            });
            // 自定义验证自然数方法
            $.validator.addMethod("isPositive", function (value, element) {
                var score = /^[0-9]*$/;
                return score.test(value) && value != 0;
            }, $.validator.format("请输入正确数值!"));
            //表单验证-添加订单
            $DrugorderForm.validate({
                rules: {
                    status: "required",
                    'drugorderdetails[0].drugname': {
                        required: true,
                        remote: {
                            url: ROOTPAth + '/admin/drugs/hasName',
                            type: "POST",
                            dataType: "json",
                            data: {
                                hospitalid: hospitalid,
                                name: function () {
                                    return $DrugorderForm.find('input[name="drugorderdetails[0].drugname"]').val();
                                }
                            }
                        }
                    },
                    'drugorderdetails[0].amount': {
                        required: true,
                        isPositive: true,
                    },
                    advice: "required"
                },
                messages: {
                    status: "请选择订单状态",
                    'drugorderdetails[0].drugname': {
                        required: "请输入药品名",
                        remote: "药品名不存在"
                    },
                    'drugorderdetails[0].amount': {
                        required: "请输入药品数量",
                    },
                    advice: "请输入医嘱"

                },
                errorElement: 'span', //default input error message container
                errorClass: 'help-block', // default input error message class
                //focusInvalid: false, // do not focus the last invalid input
                invalidHandler: function (event, validator) { //display error alert on form submit
                    //	                $('.alert-danger', $('.login-form')).show();
                },
                highlight: function (element) { // hightlight error inputs
                    $(element)
                        .closest('.form-group').addClass('has-error'); // set error class to the control group
                },
                success: function (label) {
                    var strId = label.closest('.form-group') == "undefined" ? "" : label.closest('.form-group').attr("id");
                    var index = strId.charAt(strId.length - 1);
                    if (strId == "add_drugName_" + index) {
                        $("#add_drugAmount" + index).prop("disabled", false);
                        //手动输入药品全名需要查询药品信息
                        if ($("#add_drugId_" + index).val() == "" && !label.closest('.form-group').hasClass('has-error')) {
                            $.ajax({
                                url: ROOTPAth + '/admin/drugs/drugInfoByName',
                                type: "POST",
                                dataType: "json",
                                data: {
                                    name: $("#add_drugName" + index).val(),
                                    hospitalid: hospitalid
                                },
                                success: function (data) {
                                    $("#add_drugPrice" + index).val(data.price);
                                    $("#add_drugFormat" + index).val(data.format);
                                    $("#add_drugId_" + index).val(data.id);
                                }
                            });
                        }
                    }
                    label.closest('.form-group').removeClass('has-error');
                    label.remove();
                },

                errorPlacement: function (error, element) {
                    var strId = element.closest('.form-group') == "undefined" ? "" : element.closest('.form-group').attr("id");
                    var index = strId.charAt(strId.length - 1);
                    if ((strId == "add_drugName_" + index) && element.closest('.form-group').hasClass('has-error')) {
                        $addOrdermoney.val(($addOrdermoney.val() * 1.00 - 1.00 * $("#add_drugMoney" + index).val()).toFixed(2));
                        $("#add_drugMoney" + index).val("");
                        $("#add_drugFormat" + index).val("");
                        $("#add_drugPrice" + index).val("");
                        $("#add_drugId_" + index).val("");
                        $("#add_drugAmount" + index).val("");
                        $("#add_drugAmount" + index).prop("disabled", true);
                    }
                    error.insertAfter(element);
                },
                submitHandler: function () {
                    var savePath = ROOTPAth + '/admin/drugorders/newDrugorder';
                    $DrugorderForm.find("input").prop("disabled", false);
                    $.ajax({
                        type: "POST",
                        url: savePath,
                        dataType: "json",
                        data: $DrugorderForm.serialize(),
                        beforeSend: function () {
                            tool.startPageLoading();
                        },

                        success: function (data) {
                            console.log(data);
                            tool.stopPageLoading();
                            if (data.code == 1) {
                                $addModal.modal("hide");
                                $addRoletipModal.find(".dialogtip-msg").html("订单添加成功");
                                $addRoletipModal.modal('show');
                                $add_total.val(1);
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
            //日期搜索
            $("#search").click(function () {
                pageIndex.current = 1;
                $.ajax({
                    url: ROOTPAth + '/admin/drugorders/list',
                    type: 'POST',
                    dataType: 'json',
                    data: {
                        hospitalid: hospitalid,
                        patientid: patientid,
                        startdate: function () {
                            return $("#starttime").val();
                        },
                        enddate: function () {
                            return $("#endtime").val();
                        },
                        length: pagelength
                    },
                    success: function (res) {
                        var newData = $.extend({}, res);
                        $.each(newData.data, function (i, val) {

                            newData.data[i].currentpage = pageIndex.current;
                        });
                        tool.stopPageLoading();
                        $drugorderList.find(".page-info-num").text(res.data.length);

                        $table.find("tbody").empty().append(listTpl.render(newData));
                    }
                })
            })
            //表单验证-修改订单
            $ModifyForm.validate({
                rules: {
                    advice: {
                        required: true,
                    },
                    status: "required",

                },
                messages: {

                    advice: "请输入医嘱",
                    status: "请选择订单状态",

                },
                errorElement: 'span', //default input error message container
                errorClass: 'help-block', // default input error message class
                //focusInvalid: false, // do not focus the last invalid input


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


                    var updatePath = ROOTPAth + '/admin/drugorders/modDrugorder';
                    $.post(updatePath, $ModifyForm.serialize(), function (data) {
                        if (data.code === 1) {
                            $('#modifyModal').modal('hide');
                            $addRoletipModal.find(".dialogtip-msg").html("订单修改成功");
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
            //订单详情添加按钮
            $AddUI_add.click(function () {
                $add_total.val($add_total.val() * 1 + 1);
                var newTotal = $add_total.val();
                $(this).closest(".form-group").prepend(
                    '<div class="form-group" id="add_drugName_' + newTotal + '">' +
                    '    <label class="col-md-3 control-label">药品名称</label>' +
                    '    <div class="col-md-8">' +
                    '   <input type="text" class="form-control" AUTOCOMPLETE="off"' +
                    'id="add_drugName' + newTotal + '" name="drugorderdetails[' + (newTotal * 1 - 1) + '].drugname" placeholder="药品名称">' +
                    '    <ul id="add_drugList' + newTotal + '" class="list">' +
                    '    </ul>' +
                    '    </div>' +
                    '    </div>' +
                    '    <input type="hidden" id="add_drugId_' + newTotal + '" name="drugorderdetails[' + (newTotal * 1 - 1) + '].drugid">' +
                    '    <div class="form-group" id="add_drugAmount_' + newTotal + '">' +
                    '    <label class="col-md-3 control-label">药品数量</label>' +
                    '    <div class="col-md-8">' +
                    '    <input type="text" class="form-control" AUTOCOMPLETE="off"' +
                    'id="add_drugAmount' + newTotal + '" disabled="disabled"' +
                    ' name="drugorderdetails[' + (newTotal * 1 - 1) + '].amount" placeholder="药品数量">' +
                    '    </div>' +
                    '    </div>' +
                    '    <div class="form-group" id="add_drugMoney_' + newTotal + '">' +
                    '    <label class="col-md-3 control-label">药品总价</label>' +
                    '    <div class="col-md-8">' +
                    '    <input type="text" class="form-control" disabled="disabled" id="add_drugMoney' + newTotal + '"' +
                    'name="drugorderdetails[' + (newTotal * 1 - 1) + '].money">' +
                    '    </div>' +
                    '    </div>' +
                    '    <div class="form-group" id="add_drugFormat_' + newTotal + '">' +
                    '    <label class="col-md-3 control-label">药品规格</label>' +
                    '    <div class="col-md-8">' +
                    '    <input type="text" class="form-control" id="add_drugFormat' + newTotal + '"' +
                    'name="drugorderdetails[' + (newTotal * 1 - 1) + '].format" disabled="disabled">' +
                    '    </div>' +
                    '    </div>' +
                    '    <div class="form-group" id="add_drugPrice_' + newTotal + '">' +
                    '    <label class="col-md-3 control-label">药品单价</label>' +
                    '    <div class="col-md-8">' +
                    '    <input type="text" class="form-control" id="add_drugPrice' + newTotal + '"' +
                    'name="drugorderdetails[' + (newTotal * 1 - 1) + '].price" disabled="disabled">' +
                    '    </div>' +
                    '    </div>'
                )
                //添加modal下拉框请求获取商品
                $("#add_drugName" + newTotal).keyup(function (e) {
                    if (e.keyCode != 40 && e.keyCode != 38) {
                        get_drugs_addModel($("#add_drugName" + newTotal));
                    }
                }).focus(function () {
                    this.select();
                });
                //药品数量变化
                $("#add_drugAmount" + newTotal).bind("input onpropertychange", function (e) {
                    var score = /^[0-9]*$/;
                    if (score.test($(this).val())) {
                        if ($("#add_drugMoney" + newTotal).val() == "") {
                            $("#add_drugMoney" + newTotal).val((1.00 * $(this).val() * $("#add_drugPrice" + newTotal).val()).toFixed(2));
                            $addOrdermoney.val(($addOrdermoney.val() * 1.00 + $("#add_drugMoney" + newTotal).val() * 1.00).toFixed(2));
                        } else {
                            $addOrdermoney.val(($addOrdermoney.val() * 1.00 - $("#add_drugMoney" + newTotal).val() * 1.00).toFixed(2));
                            $("#add_drugMoney" + newTotal).val((1.00 * $(this).val() * $("#add_drugPrice" + newTotal).val()).toFixed(2));
                            $addOrdermoney.val(($addOrdermoney.val() * 1.00 + $("#add_drugMoney" + newTotal).val() * 1.00).toFixed(2));
                        }
                    }
                    ;
                });
                $("#add_drugName" + newTotal).rules("add", {
                    required: true,
                    remote: {
                        url: ROOTPAth + '/admin/drugs/hasName',
                        type: "POST",
                        dataType: "json",
                        data: {
                            hospitalid: hospitalid,
                            name: function () {
                                return $DrugorderForm.find('input[name="drugorderdetails[' + (newTotal * 1 - 1) + '].drugname"]').val();
                            }
                        }
                    },
                    messages: {
                        required: "请输入药品名",
                        remote: "药品名不存在"
                    }
                });
                $("#add_drugAmount" + newTotal).rules("add", {
                    required: true,
                    isPositive: true,
                    messages: {
                        required: "请输入药品数量"
                    }
                });
            });

            //订单详情删除按钮
            $AddUI_del.click(function () {
                if ($add_total.val() == 1) {
                    $("#ajax_fail").find("h4").text("不能少于一条");
                    $("#ajax_fail").modal("show");
                    $(this).closest(".modal").modal("hide");
                } else {
                    var index = $add_total.val()
                    $("#add_drugMoney_" + index).remove();
                    $("#add_drugId_" + index).remove();
                    $("#add_drugFormat_" + index).remove();
                    $("#add_drugPrice_" + index).remove();
                    $("#add_drugName_" + index).remove();
                    $("#add_drugAmount_" + index).remove();
                    $add_total.val(index * 1 - 1);
                }
            })
        },
        deleteDrugorder: function ($that) {
            var id = $that.data("id");
            var delPath = ROOTPAth + '/admin/drugorders/delDrugorder/' + id;
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

    function get_drugs_addModel(obj) {
        var t = setTimeout(function () {
            $.ajax({
                url: ROOTPAth + '/admin/drugs/listDrugLike',
                type: 'POST',
                dataType: 'json',
                data: {
                    hospitalid: hospitalid,
                    name: $(obj).val()
                },
                success: function (data) {
                    var strId = $(obj).attr("id");
                    var index = strId.charAt(strId.length - 1);
                    data = data.data;
                    var $list = $("#add_drugList" + index);
                    $list.show();
                    $list.html("");
                    $.each(data, function (index, el) {
                        var html = $("<li data-price='" + el.price + "' data-id='" + el.id + "' data-format='" + el.format + "'>" + el.name + "</li>");
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
                        $("#add_drugPrice" + index).val($(this).data("price"));
                        $("#add_drugFormat" + index).val($(this).data("format"));
                        $("#add_drugId_" + index).val($(this).data("id"));
                        $list.hide();
                        $DrugorderForm.validate().element($(obj));
                    });
                },
            });
        }, 500);
    }

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
    Utilitiy.init();
});