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
            '<td colspan="6" style="text-align:center;background-color: white" >',
            '暂无记录',
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
                    url: ROOTPAth + '/user/drugorderdetails/list',
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
                $modal.find('input[name=price]').val(price);
                $modal.find('input[name=amount]').val(amount);
                $modal.find('input[name=old_money]').val(money);
                $modal.find(".j-form-save").hide();
                $modal.find(".j-form-edit").show();
            });
        },
    };


    Utilitiy.init();
});