define(function (require, exports, module) {
    require("res-build/res/plugin/jquery-validation-1.13.1/dist/jquery.validate.min.js");
    require("res-build/res/plugin/bs-confirmation/bootstrap-confirmation.js");
    require("res-build/res/module/underscore/underscore.js");
    var tool = require("tool");
    var Page = require("page");
    var juicer = require("juicer");
    var pageIndex;

    var $table = $("#datatable_ajax");
    var $drugorderdetailList = $("#drugorderdetail-list");
    var $DrugorderdetailForm = $('#vDrugorderdetailForm');
    var $ModifyForm = $("#vDrugorderdetailModifyForm");
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
            '<tr role="row" class="odd" data-drugname="${item.drugname}">',
            '<tr role="row" class="odd" data-format="${item.format}">',
            '<tr role="row" class="odd" data-price="${item.price}">',
            '<tr role="row" class="odd" data-amount="${item.amount}">',
            '<tr role="row" class="odd" data-money="${item.money}">',

            '{@else}',
            '<tr role="row" class="even" data-drugname="${item.drugname}">',
            '<tr role="row" class="even" data-name="${item.name}">',
            '<tr role="row" class="even" data-price="${item.price}">',
            '<tr role="row" class="even" data-amount="${item.amount}">',
            '<tr role="row" class="even" data-money="${item.money}">',
            '{@/if}',
            '    <td>${item.drugname}</td>',
            '    <td>${item.format}</td>',
            '    <td>${item.price}</td>',
            '    <td>${item.amount}</td>',
            '    <td>${item.money}</td>',

            '    <td class=" heading">',

            ' <button type="button" class="btn btn-default btn-xs j-disable j-edit" data-toggle="modal" data-target="#modifyModal"  data-id="${item.id}"  data-format="${item.format}"  data-drugname="${item.drugname}" data-price="${item.price}" data-amount="${item.amount}" data-drugid="${item.drugid}"  data-money="${item.money}"  ><span class="iconfont iconfont-xs">&#xe62d;</span>查看</button>',
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
                    url: ROOTPAth + '/admin/drugorderdetails/list',
                    type: 'POST',
                    dataType: 'json',
                    data: {

                        drugorderid: drugorderid,
                        length: pagelength

                    },
                    success: function (res) {
                        var newData = $.extend({}, res);
                        $.each(newData.data, function (i, val) {

                            newData.data[i].currentpage = pageIndex.current;
                        });
                        tool.stopPageLoading();
                        $drugorderdetailList.find(".page-info-num").text(res.data.length);

                        $table.find("tbody").empty().append(listTpl.render(newData));
                        //删除
                        $table.find(".j-del").confirmation({
                            title: "确定删除该帐号吗？",
                            btnOkLabel: "确定",
                            btnCancelLabel: "取消",
                            onConfirm: function (event, element) {
                                event.preventDefault();
                                self.deleteDrugorderdetail($(element));
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
            $drugorderdetailList.on("change", ".j-length", function () {
                var $this = $(this);
                pagelength = $this.val();
                var index = $this.get(0).selectedIndex;
                $drugorderdetailList.find(".j-length").not(this).get(0).selectedIndex = index;
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
                // $(event.relatedTarget)
            });
            //添加表单初始化
            $addModal.on('show.modal', function (event) {
                $DrugorderdetailForm[0].reset();
                $DrugorderdetailForm.find("input").removeAttr("aria-describedby");
                $DrugorderdetailForm.find("input").removeAttr("aria-invalid");
                $DrugorderdetailForm.find("input").removeAttr("aria-required");
                $DrugorderdetailForm.find("div").removeClass("has-error");
                $DrugorderdetailForm.find("span").remove();
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
                var drugname = button.data("drugname");
                var drugid = button.data("drugid");
                var price = button.data("price");
                var money = button.data("money");
                var format = button.data("format");
                var amount = button.data("amount");

                $modal.find('input[name=drugname]').val(drugname);
                $modal.find('input[name=drugid]').val(drugid);
                $modal.find('input[name=money]').val(money);
                $modal.find('input[name=format]').val(format);
                $modal.find('input[name=amount]').val(amount);
                $modal.find('input[name=odd_money]').val(money);
                $modal.find(".j-form-save").hide();
                $modal.find(".j-form-edit").show();
            });
            //添加模块绑定 药品下拉框
            $("#add_drugname").keyup(function (e) {
                if (e.keyCode != 40 && e.keyCode != 38) {
                    get_drugs_addModel($("#add_drugname"));
                }
            }).focus(function () {
                this.select();
            });
            //添加  药品数量变化
            $("#add_amount").bind("input onpropertychange", function (e) {
                var score = /^[0-9]*$/;
                if (score.test($(this).val())) {
                    $("#add_money").val((1.00 * $(this).val() * $("#add_price").val()).toFixed(2));
                };
            });
            //修改 -- 药品数量变化
            $("#mod").bind("input onpropertychange", function (e) {
                var score = /^[0-9]*$/;
                if (score.test($(this).val())) {
                    $("#mod_money").val((1.00 * $(this).val() * $("#mod_price").val()).toFixed(2));
                };
            });
            //保留两位小数
            jQuery.validator.addMethod("minNumber", function (value, element) {
                var returnVal = false;
                var ArrMen = value.split(".");    //截取字符串
                if (ArrMen.length == 2) {
                    if (ArrMen[1].length = 2) {    //判断小数点后面的字符串长度
                        returnVal = true;
                    }
                }
                return returnVal;
            }, "小数点后最多为两位");         //验证错误信息
            // 自定义验证自然数方法
            $.validator.addMethod("isPositive", function (value, element) {
                var score = /^[1-9]*$/;
                return score.test(value);
            }, $.validator.format("请输入正确数值!"));
            //表单验证-添加订单详情
            $DrugorderdetailForm.validate({
                rules: {
                    drugname: {
                        required: true,
                        remote: { //自带远程验证存在的方法
                            url: ROOTPAth + '/admin/drugorderdetails/hasName',
                            type: "POST",
                            dataType: "json",
                            data: {
                                hospitalid: hospitalid,
                                drugname: function () {
                                    return $DrugorderdetailForm.find('input[name="drugname"]').val();
                                }
                            }
                        }
                    },
                    amount: {
                        required: true,
                        isPositive: true
                    },
                    format: "required",
                    price: {
                        required: true,
                        minNumber: true
                    },

                },
                messages: {
                    drugname: {
                        required: "请输入药品",
                        remote: "药品不存在"
                    },
                    amount: {
                        required: "请输入数量"
                    },
                    format: "请输入规格",
                    price: {
                        required: true,
                    },
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
                    var savePath = ROOTPAth + '/admin/drugorderdetails/newDrugorderdetail';


                    $.ajax({
                        type: "POST",
                        url: savePath,
                        data: $DrugorderdetailForm.serialize(),
                        beforeSend: function () {
                            tool.startPageLoading();
                        },
                        dataType: "json",
                        success: function (data) {
                            console.log(data);
                            tool.stopPageLoading();
                            if (data.code == 1) {
                                $addModal.modal("hide");
                                $addRoletipModal.find(".dialogtip-msg").html("订单详情添加成功");
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

            //表单验证-修改订单详情
            $ModifyForm.validate({
                rules: {
                    drugname: {
                        required: true,
                        remote: { //自带远程验证存在的方法
                            url: ROOTPAth + '/admin/drugs/hasName',
                            type: "POST",
                            dataType: "json",
                            data: {
                                hospitalid: hospitalid,
                                drugname: function () {
                                    return $ModifyForm.find('input[name="drugname"]').val();
                                }
                            }
                        }
                    },
                    amount: {
                        required: true,
                        isPositive: true
                    },
                    format: "required",
                    price: {
                        required: true,
                        minNumber: true
                    },

                },
                messages: {
                    drugname: {
                        required: "请输入药品",
                        remote: "药品不存在"
                    },
                    amount: {
                        required: "请输入数量"
                    },
                    format: "请输入规格",
                    price: {
                        required: true,
                    },
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


                    var updatePath = ROOTPAth + '/admin/drugorderdetails/modDrugorderdetail';
                    $.post(updatePath, $ModifyForm.serialize(), function (data) {
                        if (data.code === 1) {
                            $('#modifyModal').modal('hide');
                            $addRoletipModal.find(".dialogtip-msg").html("订单详情修改成功");
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
        deleteDrugorderdetail: function ($that) {
            var id = $that.data("id");
            var delPath = ROOTPAth + '/admin/drugorderdetails/delDrugorderdetail/' + id;
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
                    data = data.data;
                    var $list = $("#add_drugList");
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
                        $("#add_price").val($(this).data("price"));
                        $("#add_format").val($(this).data("format"));
                        $("#add_drugid").val($(this).data("id"));
                        $list.hide();
                        $DrugorderdetailForm.validate().element($(obj));
                    });
                },
            });
        }, 500);
    }

    Utilitiy.init();
});