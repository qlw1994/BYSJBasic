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
            '<td colspan="4" style="text-align:center;background-color: white">',
            '暂无记录',
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

            ' <button type="button" class="btn btn-default btn-xs j-disable j-edit" data-toggle="modal" data-target="#modifyModal"  data-id="${item.id}" data-auditorid="${item.auditorid}" data-auditorname="${item.auditor.name}"   data-auditoraccount="${item.auditor.account}"  data-examtime="${item.examtime}"  data-inspectname="${item.inspectname}"  data-inspecttime="${item.inspecttime}"  data-date="${item.date}"  data-status="${item.status}"><span class="iconfont iconfont-xs">&#xe62d;</span>查看</button>',
            ' <a class="btn btn-default btn-xs"  href="' + ROOTPAth + '/user/inspectitems/index?inspectionid=${item.id}" ><span class="iconfont iconfont-xs">&#xe617;</span> 查看检验详情</a>',

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
                    url: ROOTPAth + '/user/inspectionreports/list',
                    type: 'POST',
                    dataType: 'json',
                    data: {
                        hospitalid:hospitalid,
                        patientid: patientid,
                        startdate:$("#starttime").val(),
                        enddate:$("#endtime").val(),
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
            //修改表单初始化
            $modifyModal.on('show.modal', function (event) {
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
            
            $("#search").click(function () {
                $.ajax({
                    url: ROOTPAth + '/user/inspectionreports/list',
                    type: 'POST',
                    dataType: 'json',
                    data: {
                        hospitalid:hospitalid,
                        patientid: patientid,
                        startdate:$("#starttime").val(),
                        enddate:$("#endtime").val(),
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