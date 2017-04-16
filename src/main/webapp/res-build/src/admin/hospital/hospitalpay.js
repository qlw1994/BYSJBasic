define(function (require, exports, module) {
    require("res-build/res/plugin/jquery-validation-1.13.1/dist/jquery.validate.min.js");
    require("res-build/res/plugin/bs-confirmation/bootstrap-confirmation.js");
    require("res-build/res/module/underscore/underscore.js");
    var tool = require("tool");
    var Page = require("page");
    var juicer = require("juicer");
    var pageIndex;

    var $table = $("#datatable_ajax");
    var $hospitalpayList = $("#hospitalpay-list");
    var $HospitalpayForm = $('#vHospitalpayForm');
    var $ModifyForm = $("#vHospitalpayModifyForm");
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
            '<tr role="row" class="odd" data-name="${item.name}">',
            '<tr role="row" class="odd" data-price="${item.format}">',
            '<tr role="row" class="odd" data-unit="${item.price}">',
            '<tr role="row" class="odd" data-amount="${item.amount}">',
            '<tr role="row" class="odd" data-money="${item.money}">',
            '<tr role="row" class="odd" data-advice="${item.advice}">',
            '<tr role="row" class="odd" data-status="${item.status}">',
            '{@else}',
            '<tr role="row" class="even" data-name="${item.name}">',
            '<tr role="row" class="even" data-price="${item.price}">',
            '<tr role="row" class="even" data-amount="${item.amount}">',
            '<tr role="row" class="even" data-unit="${item.unit}">',
            '<tr role="row" class="even" data-money="${item.money}">',
            '<tr role="row" class="even" data-advice="${item.advice}">',
            '<tr role="row" class="even" data-status="${item.status}">',
            '{@/if}',
            '    <td>${item.name}</td>',
            '    <td>${item.price}</td>',
            '    <td>${item.amount}</td>',
            '    <td>${item.unit}</td>',
            '    <td>${item.money}</td>',
            '    <td>${item.advice}</td>',
            '{@if item.status==0}',
            '    <td>未支付</td>',
            '{@/if}',
            '{@if item.status==1}',
            '    <td>已支付</td>',
            '{@/if}',
            '    <td class=" heading">',

            ' <button type="button" class="btn btn-default btn-xs j-disable j-edit" data-toggle="modal" data-target="#modifyModal"  data-id="${item.id}"  data-name="${item.name}"  data-price="${item.price}" data-unit="${item.unit}" data-amount="${item.amount}" data-advice="${item.advice}"  data-money="${item.money}"  ><span class="iconfont iconfont-xs">&#xe62d;</span>查看</button>',
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
                    url: ROOTPAth + '/admin/hospitalpays/list',
                    type: 'POST',
                    dataType: 'json',
                    data: {
                        hospitalizationid: hospitalizationid,
                        length: pagelength

                    },
                    success: function (res) {
                        var newData = $.extend({}, res);
                        $.each(newData.data, function (i, val) {

                            newData.data[i].currentpage = pageIndex.current;
                        });
                        tool.stopPageLoading();
                        $hospitalpayList.find(".page-info-num").text(res.data.length);

                        $table.find("tbody").empty().append(listTpl.render(newData));
                        //删除
                        $table.find(".j-del").confirmation({
                            title: "确定删除该记录吗？",
                            btnOkLabel: "确定",
                            btnCancelLabel: "取消",
                            onConfirm: function (event, element) {
                                event.preventDefault();
                                self.deleteHospitalpay($(element));
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
            $hospitalpayList.on("change", ".j-length", function () {
                var $this = $(this);
                pagelength = $this.val();
                var index = $this.get(0).selectedIndex;
                $hospitalpayList.find(".j-length").not(this).get(0).selectedIndex = index;
                pageIndex.reset();
            });

            //添加表单初始化
            $addModal.on('show.modal', function (event) {
                $HospitalpayForm[0].reset();
                $HospitalpayForm.find("input").removeAttr("aria-describedby");
                $HospitalpayForm.find("input").removeAttr("aria-invalid");
                $HospitalpayForm.find("input").removeAttr("aria-required");
                $HospitalpayForm.find("textarea").removeAttr("aria-describedby");
                $HospitalpayForm.find("textarea").removeAttr("aria-invalid");
                $HospitalpayForm.find("textarea").removeAttr("aria-required");
                $HospitalpayForm.find("div").removeClass("has-error");
                $HospitalpayForm.find("span").remove();
            })
            //修改表单初始化
            $modifyModal.on('show.modal', function (event) {
                var $modal = $ModifyForm;
                $($modifyModal).find("input").prop("disabled", true);
                $modal.find("input").removeAttr("aria-describedby");
                $modal.find("input").removeAttr("aria-invalid");
                $modal.find("input").removeAttr("aria-required");
                $modal.find("textarea").removeAttr("aria-describedby");
                $modal.find("textarea").removeAttr("aria-invalid");
                $modal.find("textarea").removeAttr("aria-required");
                $modal.find("div").removeClass("has-error");
                $modal.find("span").remove();
                var button = $(event.relatedTarget); // Button that triggered the modal
                var id = button.data("id");
                var name = button.data("name");
                var advice = button.data("advice");
                var price = button.data("price");
                var money = button.data("money");
                var amount = button.data("amount");

                $modal.find('input[name=name]').val(name);
                $modal.find('textarea[name=advice]').val(advice);
                $modal.find('input[name=money]').val(money);
                $modal.find('input[name=amount]').val(amount);
                $modal.find('input[name=old_money]').val(money);
                $modal.find('input[name=amount]').val(money);
                // $modal.find(".j-form-save").hide();
                // $modal.find(".j-form-edit").show();
            });
            //保留两位小数
            jQuery.validator.addMethod("minNumber", function (value, element) {
                var returnVal = false;
                var score = /^[0-9]*$/;
                var ArrMen = value.split(".");    //截取字符串
                if (!score.test(ArrMen[0]) || !score.test(ArrMen[1]||ArrMen[0]=="")) {
                    return false;
                }
                if (ArrMen.length == 2) {
                    if (ArrMen[1].length == 2) {    //判断小数点后面的字符串长度
                        returnVal = true;
                    }
                }
                return returnVal;
            }, "小数点后最多为两位");         //验证错误信息
            // 自定义验证自然数方法
            $.validator.addMethod("isPositive", function (value, element) {
                var score = /^[0-9]*$/;
                return score.test(value)&&value!=0;
            }, $.validator.format("请输入正确数值!"));
            //表单验证-添加订单详情
            $HospitalpayForm.validate({
                rules: {
                    name: {
                        required: true,
                    },
                    unit: {
                        required: true,
                    },
                    amount: {
                        required: true,
                        isPositive: true
                    },
                    price: {
                        required: true,
                        minNumber: true
                    },

                },
                messages: {
                    name: {
                        required: "请输入住院消费项目",
                    },
                    amount: {
                        required: "请输入数量"
                    },
                    unit: {
                        required: "请输入单位"
                    },
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
                    var savePath = ROOTPAth + '/admin/hospitalpays/newHospitalpay';


                    $.ajax({
                        type: "POST",
                        url: savePath,
                        data: $HospitalpayForm.serialize(),
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
        },
        deleteHospitalpay: function ($that) {
            var id = $that.data("id");
            var delPath = ROOTPAth + '/admin/hospitalpays/delHospitalpay/' + id;
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


    Utilitiy.init();
});