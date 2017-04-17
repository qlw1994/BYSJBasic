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

    var pagelength = 10; //一页多少条；
    $('body').tooltip({
        selector: '.has-tooltip'
    });


    var listTpl = juicer(
        [
            '{@if total === 0}',
            '<tr>',
            '<td colspan="7" style="text-align:center;background-color: white" >',
            '暂无记录',
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
                    url: ROOTPAth + '/user/hospitalpays/list',
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
                            title: "确定删除该帐号吗？",
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
        },
    };


    Utilitiy.init();
});