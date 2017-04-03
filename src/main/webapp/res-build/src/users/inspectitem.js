define(function (require, exports, module) {
    require("res-build/res/plugin/jquery-validation-1.13.1/dist/jquery.validate.min.js");
    require("res-build/res/plugin/bs-confirmation/bootstrap-confirmation.js");
    require("res-build/res/module/underscore/underscore.js");
    var tool = require("tool");
    var Page = require("page");
    var juicer = require("juicer");
    var pageIndex;

    var $table = $("#datatable_ajax");
    var $inspectitemList = $("#inspectitem-list");
    var $InspectitemForm = $('#vInspectitemForm');
    var $ModifyForm = $("#vInspectitemModifyForm");
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
            '<tr role="row" class="odd" data-result="${item.result}">',
            '<tr role="row" class="odd" data-refrange="${item.refrange}">',
            '<tr role="row" class="odd" data-unit="${item.unit}">',
            '<tr role="row" class="odd" data-abnormal="${item.abnormal}">',

            '{@else}',
            '<tr role="row" class="even" data-name="${item.name}">',
            '<tr role="row" class="even" data-result="${item.result}">',
            '<tr role="row" class="even" data-refrange="${item.refrange}">',
            '<tr role="row" class="even" data-unit="${item.unit}">',
            '<tr role="row" class="even" data-abnormal="${item.abnormal}">',
            '{@/if}',
            '    <td>${item.name}</td>',
            '    <td>${item.result}</td>',
            '    <td>${item.refrange}</td>',
            '    <td>${item.unit}</td>',
            '    <td>${item.abnormal}</td>',
            '    <td class=" heading">',

            ' <button type="button" class="btn btn-default btn-xs j-disable j-edit" data-toggle="modal" data-target="#modifyModal"  data-id="${item.id}"  data-name="${item.name}"  data-result="${item.result}" data-refrange="${item.refrange}" data-unit="${item.unit}" data-abnormal="${item.abnormal}"><span class="iconfont iconfont-xs">&#xe62d;</span>查看</button>',

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
                    url: ROOTPAth + '/user/inspectitems/list',
                    type: 'POST',
                    dataType: 'json',
                    data: function () {
                        var data = {
                            inspectionid: inspectionid,
                            length: pagelength
                        };
                        return data;
                    },
                    success: function (res) {
                        var newData = $.extend({}, res);
                        $.each(newData.data, function (i, val) {

                            newData.data[i].currentpage = pageIndex.current;
                        });
                        tool.stopPageLoading();
                        $inspectitemList.find(".page-info-num").text(res.data.length);

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
            $inspectitemList.on("change", ".j-length", function () {
                var $this = $(this);
                pagelength = $this.val();
                var index = $this.get(0).selectedIndex;
                $inspectitemList.find(".j-length").not(this).get(0).selectedIndex = index;
                pageIndex.reset();
            });

            //修改表单初始化
            $modifyModal.on('show.bs.modal', function (event) {
                var $modal = $ModifyForm;
                $modal.find("input").prop("disabled", true);
                $modal.find("input").removeAttr("aria-describedby");
                $modal.find("input").removeAttr("aria-invalid");
                $modal.find("input").removeAttr("aria-required");
                $modal.find("div").removeClass("has-error");
                $modal.find("span").remove();
                var button = $(event.relatedTarget); // Button that triggered the modal
                var id = button.data("id");
                var name = button.data("name");
                var refrange = button.data("refrange");
                var unit = button.data("unit");
                var result = button.data("result");
                var abnormal = button.data("abnormal");
                $modal.find('input[name=id]').val(id);
                $modal.find('input[name=name]').val(name);
                $modal.find('input[name=refrange]').val(refrange);
                $modal.find('input[name=result]').val(result);
                $modal.find('input[name=unit]').val(unit);
                $modal.find('input[name=abnormal]').val(abnormal);

                $modal.find(".j-form-save").hide();
                $modal.find(".j-form-edit").show();
            });
        },
    };
    Utilitiy.init();
});