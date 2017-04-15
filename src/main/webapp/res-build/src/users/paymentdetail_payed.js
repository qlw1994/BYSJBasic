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
    var $ModifyForm = $("#vpaymentdetailModifyForm");
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

            ' <button type="button" class="btn btn-default btn-xs j-disable j-edit" data-toggle="modal" data-target="#modifyModal"  data-id="${item.id}"  data-paynumber="${item.paynumber}"  data-invoicenumber="${item.invoicenumber}" data-paydate="${item.paydate}"><span class="iconfont iconfont-xs">&#xe62d;</span>查看</button>',

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
                                length: pagelength,
                                payed: 1
                            }
                            ;
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
                        });
                        tool.stopPageLoading();
                        $paymentdetailList.find(".page-info-num").text(res.data.length);

                        $table.find("tbody").empty().append(listTpl.render(newData));
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
        },

    };
    Utilitiy.init();
});