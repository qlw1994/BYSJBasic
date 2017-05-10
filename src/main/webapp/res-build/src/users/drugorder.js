define(function (require, exports, module) {
    require("res-build/res/plugin/jquery-validation-1.13.1/dist/jquery.validate.min.js");
    require("res-build/res/plugin/bs-confirmation/bootstrap-confirmation.js");
    require("res-build/res/module/underscore/underscore.js");
    var tool = require("tool");
    var Page = require("page");
    var juicer = require("juicer");
    var pageIndex;
    var $table = $("#datatable_ajax");
    var $drugorderList = $("#drugorder-list");
    var $ModifyForm = $("#vDrugorderModifyForm");
    var $modifyModal = $('#modifyModal');
    var pagelength = 10; //一页多少条；
    $('body').tooltip({
        selector: '.has-tooltip'
    });


    var listTpl = juicer(
        [
            '{@if total === 0}',
            '<tr>',
            '<td colspan="6" style="text-align:center;background-color: white">',
            '暂无记录',
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

            ' <button type="button" class="btn btn-default btn-xs j-disable j-edit" data-toggle="modal" data-target="#modifyModal"  data-id="${item.id}" data-advice="${item.advice}" data-status="${item.status}"><span class="iconfont iconfont-xs">&#xe62d;</span>查看</button>',
            ' <a class="btn btn-default btn-xs"  href="' + ROOTPAth + '/user/drugorderdetails/index?drugorderid=${item.id}" ><span class="iconfont iconfont-xs">&#xe617;</span>药品详情</a>',
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
                    url: ROOTPAth + '/user/drugorders/list',
                    type: 'POST',
                    dataType: 'json',
                    data: {
                        startdate:$("#starttime").val(),
                        enddate:$("#endtime").val(),
                        hospitalid:hospitalid,
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
            $("#search").click(function (e) {
                e.preventDefault();
                pageIndex.current=1;
                $.ajax({
                    url: ROOTPAth + '/user/drugorders/list',
                    type: 'POST',
                    dataType: 'json',
                    data: {
                        hospitalid:hospitalid,
                        patientid: patientid,
                        startdate:function () {
                            return $("#starttime").val();
                        },
                        enddate:function () {
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

        },

    };

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