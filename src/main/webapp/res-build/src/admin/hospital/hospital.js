define(function (require, exports, module) {
    require("res-build/res/plugin/jquery-validation-1.13.1/dist/jquery.validate.min.js");
    require("res-build/res/plugin/bs-confirmation/bootstrap-confirmation.js");
    require("res-build/res/module/underscore/underscore.js");
    var tool = require("tool");
    var Page = require("page");
    var juicer = require("juicer");
    var pageIndex;
    $(function () {
        $.ajax({
            url: ROOTPAth + '/admin/initDate',
            type: 'POST',
            dataType: 'json',
            data: {
                starttime: $("#date1").val(),
                endtime: $("#date2").val(),
            }
        }).done(function (res) {
        }).fail(function (err) {
            $("#ajax_fail").modal("show")
        });
        $.ajax({
            url: ROOTPAth + '/admin/hospitals/reflashHospital',
            type: 'POST',
            dataType: 'json',
        }).done(function (res) {
        }).fail(function (err) {
            $("#ajax_fail").modal("show")
        });
    })
    var $table = $("#datatable_ajax");
    var $hospitalList = $("#hospital-list");
    var $HospitalForm = $('#vHospitalForm');
    var $ModifyForm = $("#vHospitalModifyForm");
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
            '<td colspan="7" style="text-align:center">',
            '暂无记录,请添加',
            '</td>',

            '</tr>',

            '{@else}',
            '{@each data as item,index}',
            '{@if index%2==0}',
            '<tr role="row" class="odd" data-name="${item.name}" data-province="${item.province}" data-city="${item.city}" data-area="${item.area}" data-startdate="${item.startdate}" data-enddate="${item.enddate}">',
            '{@else}',
            '<tr role="row" class="even" data-name="${item.name}" data-provincename="${item.province}" data-cityname="${item.city}" data-area="${item.area}" data-startdate="${item.startdate}" data-enddate="${item.enddate}">',
            '{@/if}',
            '    <td>${item.name}</td>',
            '    <td>${item.province}</td>',
            '    <td>${item.city}</td>',
            '    <td>${item.area}</td>',
            '    <td>${item.startdate}</td>',
            '    <td>${item.enddate}</td>',
            '    <td class=" heading">',

            ' <button type="button" class="btn btn-default btn-xs j-disable j-edit" data-toggle="modal" data-target="#modifyModal"  data-id="${item.id}"  data-name="${item.name}"   data-province="${item.province}" data-city="${item.city}" data-area="${item.area}" data-startdate="${item.startdate}"  data-enddate="${item.enddate}"  data-level="${item.level}" data-address="${item.address}" data-resume="${item.resume}" data-status="${item.status}" ><span class="iconfont iconfont-xs">&#xe62d;</span>查看</button>',

            ' <a class="btn btn-default btn-xs"  href="' + ROOTPAth + '/admin/departments/index?pcode=2&subcode=1&hospitalid=${item.id}&hospitalname=${item.name}" ><span class="iconfont iconfont-xs">&#xe617;</span>科室管理</a>',
            ' <a class="btn btn-default btn-xs"  href="' + ROOTPAth + '/admin/drugs/index?pcode=2&subcode=1&hospitalid=${item.id}&hospitalname=${item.name}" ><span class="iconfont iconfont-xs">&#xe617;</span>药品管理</a>',

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
                    url: ROOTPAth + '/admin/hospitals/list',
                    type: 'POST',
                    dataType: 'json',
                    data: function () {
                        var data = {
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
                        $hospitalList.find(".page-info-num").text(res.data.length);

                        $table.find("tbody").empty().append(listTpl.render(newData));
                        //删除权限
                        $table.find(".j-del").confirmation({
                            title: "确定删除该医院吗？",
                            btnOkLabel: "确定",
                            btnCancelLabel: "取消",
                            onConfirm: function (event, element) {
                                event.preventDefault();
                                self.deleteHospital($(element));
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
            $hospitalList.on("change", ".j-length", function () {
                var $this = $(this);
                pagelength = $this.val();
                var index = $this.get(0).selectedIndex;
                $hospitalList.find(".j-length").not(this).get(0).selectedIndex = index;
                pageIndex.reset();
            });

            //我要编辑
            $ModifyForm.on("click", ".j-form-edit", function (event) {
                var formDom = event.delegateTarget;
                $(this).hide();
                $(formDom).find(".j-form-save").show();
                $(formDom).find("input").prop("disabled", false);
                $(formDom).find("select").prop("disabled", false);
                $(formDom).find("textarea").prop("disabled", false);
            });
            //添加表单初始化
            $addModal.on('show.bs.modal', function (event) {
                $HospitalForm[0].reset();
                $HospitalForm.find("input").removeAttr("aria-describedby");
                $HospitalForm.find("input").removeAttr("aria-invalid");
                $HospitalForm.find("input").removeAttr("aria-required");
                $HospitalForm.find("select").removeAttr("aria-describedby");
                $HospitalForm.find("select").removeAttr("aria-invalid");
                $HospitalForm.find("select").removeAttr("aria-required");
                $HospitalForm.find("textarea").removeAttr("aria-describedby");
                $HospitalForm.find("textarea").removeAttr("aria-invalid");
                $HospitalForm.find("textarea").removeAttr("aria-required");
                $HospitalForm.find("div").removeClass("has-error");
                $HospitalForm.find("span").remove();
            })
            //修改表单初始化
            $modifyModal.on('show.bs.modal', function (event) {
                var $modal = $ModifyForm;
                $modal[0].reset();
                $modal.find("input").prop("disabled",true);
                $modal.find("select").prop("disabled",true);
                $modal.find("textarea").prop("disabled",true);
                $modal.find("input").removeAttr("aria-describedby");
                $modal.find("input").removeAttr("aria-invalid");
                $modal.find("input").removeAttr("aria-required");
                $modal.find("select").removeAttr("aria-describedby");
                $modal.find("select").removeAttr("aria-invalid");
                $modal.find("select").removeAttr("aria-required");
                $modal.find("textarea").removeAttr("aria-describedby");
                $modal.find("textarea").removeAttr("aria-invalid");
                $modal.find("textarea").removeAttr("aria-required");
                $modal.find("div").removeClass("has-error");
                $modal.find("span").remove();

                var button = $(event.relatedTarget); // Button that triggered the modal
                var id = button.data("id");
                var name = button.data("name");
                var city = button.data("city");
                var province = button.data("province");
                var area = button.data("area");
                var level = button.data("level");
                var address = button.data("address");
                var resume = button.data("resume");
                var startdate = button.data("startdate");
                var enddate = button.data("enddate");

                $modal.find('input[name=id]').val(id);
                $modal.find('input[name=name]').val(name);
                $modal.find('select[name=level]').val(level);
                $modal.find('select[name=area]').val( area);
                $modal.find('select[name=province]').val(province);
                $modal.find('select[name=city]').val( city);
                $modal.find('textarea[name=address]').val(address);
                $modal.find('textarea[name=resume]').val(resume);
                $modal.find('input[name=startdate]').val(startdate);
                $modal.find('input[name=enddate]').val(enddate);

                $modal.find(".j-form-save").hide();
                $modal.find(".j-form-edit").show();
            });

            //
            // //修改表单中医院名称验证需要先选择城市
            // $ModifyForm.find("input[name=cityname]").keyup(function (e) {
            //     if (e.keyCode != 40 && e.keyCode != 38) {
            //         self.get_cities_modModel($ModifyForm.find("input[name=cityname]"));
            //     }
            // }).focus(function () {
            //     this.select();
            //     if ($ModifyForm.validate().element($HospitalForm.find("input[name=cityname]"))) {
            //         $ModifyForm.find("input[name=name]").prop("disabled", false)
            //     } else {
            //         $ModifyForm.find("input[name=name]").prop("disabled", true)
            //     }
            // });
            //表单验证-添加用户
            $HospitalForm.validate({
                rules: {
                    name: {
                        required: true,
                        remote: { //自带远程验证存在的方法
                            url: ROOTPAth + '/admin/hospitals/sameName',
                            type: "POST",
                            dataType: "json",
                            data: {
                                name: function () {
                                    return $HospitalForm.find('input[name="name"]').val();
                                },
                                area: function () {
                                    return $HospitalForm.find('select[name="area"]').val();
                                },
                                city: function () {
                                    return $HospitalForm.find('select[name="city"]').val();
                                },
                                province: function () {
                                    return $HospitalForm.find('select[name="province"]').val();
                                }
                            }
                        }
                    },
                    city: "required",
                    province: "required",
                    area: "required",
                    level: "required",
                    address: "required",
                    resume: "required"

                },
                messages: {
                    name: {
                        required: "名称不能为空",
                        remote: "医院名重复"
                    },
                    city: "城市名称不能为空",
                    area: "区/县名称不能为空",
                    province: "省份名称不能为空",
                    level: "请选择医院等级",
                    address: "医院地址不能为空",
                    resume: "医院简介不能为空"

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
                    var savePath = ROOTPAth + '/admin/hospitals/newHospital';


                    $.ajax({
                        type: "POST",
                        url: savePath,
                        data: $HospitalForm.serialize(),
                        beforeSend: function () {
                            tool.startPageLoading();
                        },
                        dataType: "json",
                        success: function (data) {
                            console.log(data);
                            tool.stopPageLoading();
                            if (data.code == 1) {
                                $addModal.modal("hide");
                                $addRoletipModal.find(".dialogtip-msg").html("医院添加成功");
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
            //表单验证-修改用户
            $ModifyForm.validate({
                rules: {
                    name: {
                        required: true,
                        remote: { //自带远程验证存在的方法
                            url: ROOTPAth + '/admin/hospitals/sameName',
                            type: "POST",
                            dataType: "json",
                            data: {
                                name: function () {
                                    return $ModifyForm.find('input[name="name"]').val();
                                },
                                area: function () {
                                    return $ModifyForm.find('select[name="area"]').val();
                                },
                                city: function () {
                                    return $ModifyForm.find('select[name="city"]').val();
                                },
                                province: function () {
                                    return $ModifyForm.find('select[name="province"]').val();
                                }
                            }
                        }
                    },
                    city: "required",
                    province: "required",
                    area: "required",
                    level: "required",
                    address: "required",
                    resume: "required"

                },
                messages: {
                    name: {
                        required: "名称不能为空",
                        remote: "医院名重复"
                    },
                    city: "城市名称不能为空",
                    area: "区/县名称不能为空",
                    province: "省份名称不能为空",
                    level: "请选择医院等级",
                    address: "医院地址不能为空",
                    resume: "医院简介不能为空"

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


                    var updatePath = ROOTPAth + '/admin/hospitals/modHospital';
                    $.post(updatePath, $ModifyForm.serialize(), function (data) {
                        if (data.code === 1) {
                            $('#modifyModal').modal('hide');
                            $addRoletipModal.find(".dialogtip-msg").html("医院修改成功");
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
        deleteHospital: function ($that) {
            var id = $that.data("id");
            var delPath = ROOTPAth + '/admin/hospitals/delHospital/' + id;
            $.ajax({
                url: delPath,
                type: "POST",
                success: function (data) {
                    if (data.code === 1) {
                        pageIndex.reset();
                    } else {
                        $("#ajax_fail").find("h4").html(data.message);
                        $("#ajax_fail").modal("show")
                        $HospitalForm.form();
                    }
                }
            });
        },
    };

    new PCAS("province", "city", "area");
    new PCAS("modprovince", "modcity", "modarea");

    $('#mod_startdate').datepicker({
        dateFormat: "yy-mm-dd",
        selectOtherMonths: true,
        yearRange: "-100:+0",
        changeMonth: true,
        changeYear: true,
        inline: true
    });
    $('#mod_enddate').datepicker({

        dateFormat: "yy-mm-dd",
        selectOtherMonths: true,
        yearRange: "-100:+0",
        changeMonth: true,
        changeYear: true,
        inline: true
    });
    Utilitiy.init();
});