define(function (require, exports, module) {
    require("res-build/res/plugin/jquery-validation-1.13.1/dist/jquery.validate.min.js");
    require("res-build/res/plugin/bs-confirmation/bootstrap-confirmation.js");
    require("res-build/res/module/underscore/underscore.js");
    var tool = require("tool");
    var Page = require("page");
    var juicer = require("juicer");
    var pageIndex;

    var $table = $("#datatable_ajax");
    var $appointmentList = $("#appointment-list");
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
            '<tr role="row" class="odd" data-patientname="${item.patientname}"  data-serialnumber="${item.serialnumber}" data-regfee="${item.regfee}" data-status="${item.status}" data-date="${item.date}" data-timeflag="${item.timeflag}">',
            '{@else}',
            '<tr role="row" class="even" data-patientname="${item.patientname}"  data-serialnumber="${item.serialnumber}" data-regfee="${item.regfee}" data-status="${item.status}" data-date="${item.date}" data-timeflag="${item.timeflag}">',
            '{@/if}',
            '    <td>${item.patientname}</td>',
            '    <td>${item.serialnumber}</td>',
            '    <td>${item.regfee}</td>',


            '{@if item.status==1}',
            '    <td>预约</td>',
            '{@/if}',
            '{@if item.status==2}',
            '    <td>取消</td>',
            '{@/if}',
            '{@if item.status==3}',
            '    <td>爽约</td>',
            '{@/if}',
            '{@if item.status==4}',
            '    <td>已取号</td>',
            '{@/if}',
            '{@if item.status==5}',
            '    <td>未支付</td>',
            '{@/if}',
            '{@if item.status==6}',
            '    <td>已支付</td>',
            '{@/if}',
            '{@if item.status==7}',
            '    <td>诊断完毕</td>',
            '{@/if}',
            '{@if item.type==1}',
            '    <td>专家</td>',
            '{@/if}',
            '{@if item.type==2}',
            '    <td>普通</td>',
            '{@/if}',
            '    <td>${item.date}</td>',
            '{@if item.timeflag==1}',
            '    <td>上午</td>',
            '{@/if}',
            '{@if item.timeflag==2}',
            '    <td>下午</td>',
            '{@/if}',
            '{@if item.timeflag==3}',
            '    <td>晚上</td>',
            '{@/if}',

            '    <td class=" heading">',
            '{@if item.status==1}',
            // ' <button type="button" class="btn btn-danger btn-xs j-disable j-del" data-toggle="confirmation"  data-placement="top" data-id="${item.id}"><span class="iconfont iconfont-xs">&#xe618;</span>删除</button>',
            ' <button class="btn btn-default btn-xs  j-disable j-quereng"  data-id=${item.id} data-status="4" ><span class="iconfont iconfont-xs">&#xe617;</span>确认取号</button>',
            '{@/if}',
            '{@if item.status==4}',
            ' <a class="btn btn-default btn-xs"  href="' + ROOTPAth + '/doctor/checkreports/index?pcode=2&subcode=1&patientid=${item.patientid}&patientname=${item.patientname}&uid=${item.uid}" ><span class="iconfont iconfont-xs">&#xe617;</span>检查报告</a>',
            ' <a class="btn btn-default btn-xs"  href="' + ROOTPAth + '/doctor/inspectionreports/index?pcode=2&subcode=1&patientid=${item.patientid}&patientname=${item.patientname}&uid=${item.uid}" ><span class="iconfont iconfont-xs">&#xe617;</span>检验报告</a>',
            ' <a class="btn btn-default btn-xs"  href="' + ROOTPAth + '/doctor/drugorders/index?pcode=2&subcode=1&patientid=${item.patientid}&patientname=${item.patientname}&uid=${item.uid}" ><span class="iconfont iconfont-xs">&#xe617;</span>开药订单</a>',
            ' <button class="btn btn-default btn-xs j-end" data-id=${item.id} data-status="7" ><span class="iconfont iconfont-xs">&#xe617;</span>诊断完毕</button>',
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
                    url: ROOTPAth + '/doctor/appointments/list',
                    type: 'POST',
                    dataType: 'json',
                    data: function () {
                        var data = {
                            doctorid: doctorid,
                            doctorname: doctorname,
                            length: pagelength
                        };
                        return data;
                    },
                    success: function (res) {
                        var newData = $.extend({}, res);
                        $.each(newData.data, function (i, val) {

                            newData.data[i].currentpage = pageIndex.current;
                            if(newData.data[i].serialnumber==null){
                                newData.data[i].serialnumber="未取号";
                            }
                        });
                        tool.stopPageLoading();
                        $appointmentList.find(".page-info-num").text(res.data.length);

                        $table.find("tbody").empty().append(listTpl.render(newData));
                        //删除权限
                        $table.find(".j-del").confirmation({
                            title: "确定删除该记录吗？",
                            btnOkLabel: "确定",
                            btnCancelLabel: "取消",
                            onConfirm: function (event, element) {
                                event.preventDefault();
                                self.deleteAppointment($(element));
                            }
                        });
                        $table.find(".j-end").confirmation({
                            title: "确定吗？",
                            btnOkLabel: "确定",
                            btnCancelLabel: "取消",
                            onConfirm: function (event, element) {
                                event.preventDefault();
                                self.modAppointment($(element));
                            }
                        });
                        $table.find(".j-quereng").confirmation({
                            title: "确定吗？",
                            btnOkLabel: "确定",
                            btnCancelLabel: "取消",
                            onConfirm: function (event, element) {
                                event.preventDefault();
                                self.modAppointment($(element));
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
            $appointmentList.on("change", ".j-length", function () {
                var $this = $(this);
                pagelength = $this.val();
                var index = $this.get(0).selectedIndex;
                $appointmentList.find(".j-length").not(this).get(0).selectedIndex = index;
                pageIndex.reset();
            });


        },
        modAppointment: function ($that) {
            var id = $that.data("id");
            var status = $that.data("status");
            var Path = ROOTPAth + '/doctor/appointments/modAppointmentStatus/';
            $.ajax({
                url: Path,
                type: "POST",
                data: {
                    id: id,
                    status: status
                },
                success: function (data) {
                    if (data.code == 1) {
                        pageIndex.reset();
                    } else {
                        $("#ajax_fail").find("h4").html(data.message);
                        $("#ajax_fail").modal("show")
                    }
                }
            });

        }
    };
    Utilitiy.init();
});