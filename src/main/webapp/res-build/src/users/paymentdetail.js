define(function (require, exports, module) {
    require("res-build/res/plugin/jquery-validation-1.13.1/dist/jquery.validate.min.js");
    require("res-build/res/plugin/bs-confirmation/bootstrap-confirmation.js");
    require("res-build/res/module/underscore/underscore.js");
    var tool = require("tool");
    var Page = require("page");
    var juicer = require("juicer");
    var pageIndex;

    var $table = $("#datatable_ajax");
    var $paymentdetailList = $("#paymentdetail-list");
    var $paymentdetailForm = $('#vpaymentdetailForm');
    var $ModifyForm = $("#vPaymentdetailModifyForm");
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
            '<td colspan="11" style="text-align:center;background-color: white">',
            '暂无记录',
            '</td>',

            '</tr>',

            '{@else}',
            '{@each data as item,index}',
            '{@if index%2==0}',
            '<tr role="row" class="odd" data-payname="${item.payname}">',
            '<tr role="row" class="odd" data-format="${item.format}">',
            '<tr role="row" class="odd" data-count="${item.count}">',
            '<tr role="row" class="odd" data-money="${item.money}">',
            '<tr role="row" class="odd" data-doctorname="${item.doctorname}">',
            '<tr role="row" class="odd" data-departmentname="${item.departmentname}">',
            '<tr role="row" class="odd" data-hospitalname="${item.hospitalname}">',
            '<tr role="row" class="odd" data-status="${item.status}">',
            '<tr role="row" class="odd" data-paytype="${item.paytype}">',
            '<tr role="row" class="odd" data-createdate="${item.createdate}">',

            '{@else}',
            '<tr role="row" class="even" data-payname="${item.payname}">',
            '<tr role="row" class="even" data-format="${item.format}">',
            '<tr role="row" class="even" data-count="${item.count}">',
            '<tr role="row" class="even" data-money="${item.money}">',
            '<tr role="row" class="even" data-doctorname="${item.doctorname}">',
            '<tr role="row" class="even" data-departmentname="${item.departmentname}">',
            '<tr role="row" class="even" data-hospitalname="${item.hospitalname}">',
            '<tr role="row" class="even" data-status="${item.status}">',
            '<tr role="row" class="even" data-paytype="${item.paytype}">',
            '<tr role="row" class="even" data-createdate="${item.createdate}">',
            '{@/if}',
            '    <td>${item.payname}</td>',
            '    <td>${item.format}</td>',
            '    <td>${item.count}</td>',
            '    <td>${item.money}</td>',
            '    <td>${item.doctorname}</td>',
            '    <td>${item.departmentname}</td>',
            '    <td>${item.hospitalname}</td>',
            '{@if item.status==0}',
            '    <td>未支付</td>',
            '{@/if}',
            '{@if item.status==1}',
            '    <td>已支付</td>',
            '{@/if}',
            '    <td>${item.paytype}</td>',
            '    <td>${item.createdate}</td>',
            '    <td class=" heading">',
            '{@if item.status==0}',
            '<input data-money="${item.money}"  type="checkbox" name="checkitem" value="${item.id}"/>选择支付',
            '{@/if}',
            '{@if item.status==1}',
            ' <button type="button" class="btn btn-default btn-xs j-disable j-edit" data-toggle="modal" data-target="#modifyModal"  data-id="${item.id}"  data-paynumber="${item.paynumber}"  data-invoicenumber="${item.invoicenumber}" data-paydate="${item.paydate}"><span class="iconfont iconfont-xs">&#xe62d;</span>查看</button>',
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
                    url: ROOTPAth + '/user/paymentdetails/list',
                    type: 'POST',
                    dataType: 'json',
                    data: function () {
                        var data = {
                            hospitalid: hospitalid,
                            patientid: patientid,
                            length: pagelength
                        };
                        return data;
                    },
                    success: function (res) {
                        var newData = $.extend({}, res);
                        $.each(newData.data, function (i, val) {

                            newData.data[i].currentpage = pageIndex.current;
                            if (newData.data[i].paytype == null) {
                                newData.data[i].paytype = "未支付";
                            }
                            if (newData.data[i].payname == null) {
                                newData.data[i].payname = "";
                            }
                            if (newData.data[i].format == null) {
                                newData.data[i].format = "";
                            }
                            if (newData.data[i].doctorname == null) {
                                newData.data[i].doctorname = "";
                            }
                        });
                        tool.stopPageLoading();
                        $paymentdetailList.find(".page-info-num").text(res.data.length);

                        $table.find("tbody").empty().append(listTpl.render(newData));
                        //删除权限
                        $table.find(".j-del").confirmation({
                            title: "确定删除该记录吗？",
                            btnOkLabel: "确定",
                            btnCancelLabel: "取消",
                            onConfirm: function (event, element) {
                                event.preventDefault();
                                self.deletepaymentdetail($(element));
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
            $paymentdetailList.on("change", ".j-length", function () {
                var $this = $(this);
                pagelength = $this.val();
                var index = $this.get(0).selectedIndex;
                $paymentdetailList.find(".j-length").not(this).get(0).selectedIndex = index;
                pageIndex.reset();
            });
            //修改表单初始化
            $modifyModal.on('show.modal', function (event) {
                var $modal = $ModifyForm;
                var button = $(event.relatedTarget); // Button that triggered the modal
                var invoicenumber = button.data("invoicenumber");
                var paynumber = button.data("paynumber");
                var paydate = button.data("paydate");
                $modal.find('input[name=invoicenumber]').val(invoicenumber);
                $modal.find('input[name=paynumber]').val(paynumber);
                $modal.find('input[name=paydate]').val(paydate);

                // $modal.find(".j-form-save").hide();
                // $modal.find(".j-form-edit").show();
            });

            $("#toPayBtn").click(function () {
                if ($table.find("input[name='checkitem']:checked").length == 0) {
                    $("#ajax_fail").find("h4").html("至少选择一项支付");
                    $("#ajax_fail").modal("show")
                }
                else {
                    $('#viewPaymentBudgetModal').modal("show");
                }
            })
            //预结算
            $('#viewPaymentBudgetModal').on('show.modal', function (event) {

                var paymentdetailids = "";
                var totalmoney = 0;
                $table.find("input[name='checkitem']:checkbox:checked").each(function () {
                    if (paymentdetailids == "") {
                        paymentdetailids = $(this).val();
                        totalmoney = totalmoney * 1 + $(this).data("money") * 1;
                    } else {
                        paymentdetailids += "|" + $(this).val();
                        totalmoney = totalmoney * 1 + $(this).data("money") * 1;
                    }
                });
                tool.startPageLoading();
                $.ajax({
                    url: ROOTPAth + "/user/paymentdetails/paymentBudget",
                    type: "Post",
                    dataType: "json",
                    data: {
                        paymentdetailids: paymentdetailids,
                        totalmoney: totalmoney,
                    },
                    success: function (res) {
                        tool.stopPageLoading();
                        fillBudget(res);
                    }
                });


            });

        },
    };
    //填充预结算结果表格
    function fillBudget(tblData) {
//        document.getElementById('alipay').checked = true;
        var text = '';
        var data = tblData.data;
        if (data) {
            text += '</tr><tr role="row" class="odd">';
            text += '<td><b>金额</b></td>';
            text += '<td>' + (data.totalmoney) + '&nbsp;<input type="hidden" id="budget_money" value="' + data.totalmoney + '"/></td>';
            text += '</tr><tr role="row" class="even">';
            text += '<td><b>发票</b></td>';
            // text += '<td>' + data.invoicenumber + '&nbsp;<input type="hidden" id="budget_invoicenumber" value="' + data.invoicenumber + '"/></td>';
            text += '<td id="td_invoicenumber">&nbsp;' + data.invoicenumber + '<input type="hidden" id="budget_invoicenumber" value="' + data.invoicenumber + '"/></td>';
            text += '</tr><tr role="row" class="odd">';
            text += '<td><b>支付方式</b></td>';
            text += '<td><label>'
                + '<input type="radio" value="1" id="alipay" name="paytype" checked/>支付宝'
                + '</label>&nbsp;&nbsp;<label>'
                + '<input type="radio" value="2" disabled="disabled" id="wchatpay" name="paytype"/>微信支付'
                + '</label>&nbsp;&nbsp;<label>'
                + '<input type="radio" value="3" disabled="disabled" id="unionpay" name="paytype" />银联'
                + '</label>';
            text += '</tr>';
        } else {
            text = '<tr><td colspan="2">没有找到相关记录</td></tr>';
        }
        $('#budget_content').html(text);
        // getcode("td_invoicenumber", data.invoicenumber);
    }

    //选择支付方式后继续支付
    $('#btnContinuePay').click(function () {
        var paytype = $('input[name="paytype"]').val();
        var paymentdetailids = "";
        var totalmoney = 0;
        $table.find("input[name='checkitem']:checkbox:checked").each(function () {
            if (paymentdetailids == "") {
                paymentdetailids = $(this).val();
                totalmoney = totalmoney * 1 + $(this).data("money") * 1;
            } else {
                paymentdetailids += "|" + $(this).val();
                totalmoney = totalmoney * 1 + $(this).data("money") * 1;
            }
        });

        $.ajax({
            url: ROOTPAth + '/user/paymentdetails/pay',
            type: 'POST',
            dataType: 'json',
            data: {
                test: '1',
                hospitalid: hospitalid,
                uid: uid,
                uname: uname,
                patientid: patientid,
                patientname: patientname,
                paymentdetailids: paymentdetailids,
                paytype: paytype,
                totalFee: totalmoney,
                invoicenumber:function () {
                    return $("#budget_invoicenumber").val();
                },
                subject: (hospitalname + "-诊间支付测试[" + new Date() + ']')
            },
            // beforeSend: function () {
            //     tool.startPageLoading();
            //     var url = ROOTPAth + '/user/paymentdetails/blank?';
            //     $('#html_con').attr('src', url);
            // },
            success: function (res) {
                tool.stopPageLoading();
                var data = res.data;
                console.log(data);
                // document.getElementById('html_con').contentWindow.document.write(data);
                $("#paymentdetail-list").html(data);
            },
            error: function (err) {
                tool.stopPageLoading();
//    			failLoad("请检查您的网络并刷新页面！", "加载失败");
            }
        })
    });
    // 生成条形码
    function getcode(id, str) {
        var id = id + ""; //转换成字符串
        var barcode = document.getElementById(id),
            str = str + "", //转换成字符串
            options = {
                format: "CODE128",
                displayValue: true,
                fontSize: 14,
                height: 100
            };
        JsBarcode(barcode, str, options);
    }

    Utilitiy.init();
});