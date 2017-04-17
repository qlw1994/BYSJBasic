define(function (require, exports, module) {
    require("res-build/res/plugin/jquery-validation-1.13.1/dist/jquery.validate.min.js");
    require("res-build/res/plugin/bs-confirmation/bootstrap-confirmation.js");
    require("res-build/res/module/underscore/underscore.js");
    var tool = require("tool");
    var Page = require("page");
    var juicer = require("juicer");
    var pageIndex;
    var $add_total = $("#add_total");
    var $AddUI_add = $("#add_details");
    var $AddUI_del = $("#del_details");
    var $addOrdermoney = $("#add_orderMoney");
    var $table = $("#datatable_ajax");
    var $hospitalizationList = $("#hospitalization-list");
    var $HospitalizationForm = $('#vHospitalizationForm');
    var $ModifyForm = $("#vHospitalizationModifyForm");
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
            '<tr role="row" class="odd" data-hospitalname="${item.hospitalname}">',
            '<tr role="row" class="odd" data-departmentname="${item.departmentname}">',
            '<tr role="row" class="odd" data-startdate="${item.startdate}">',
            '<tr role="row" class="odd" data-enddate="${item.enddate}">',
            '<tr role="row" class="odd" data-bednumber="${item.bednumber}">',
            '<tr role="row" class="odd" data-money="${item.money}">',
            '{@else}',
            '<tr role="row" class="even" data-hospitalname="${item.hospitalname}">',
            '<tr role="row" class="even" data-departmentname="${item.departmentname}">',
            '<tr role="row" class="even" data-startdate="${item.startdate}">',
            '<tr role="row" class="even" data-enddate="${item.enddate}">',
            '<tr role="row" class="even" data-bednumber="${item.bednumber}">',
            '<tr role="row" class="even" data-money="${item.money}">',
            '{@/if}',
            '    <td>${item.hospitalname}</td>',
            '    <td>${item.departmentname}</td>',
            '    <td>${item.startdate}</td>',
            '    <td>${item.enddate}</td>',
            '    <td>${item.bednumber}</td>',
            '    <td>${item.money}</td>',
            '    <td class=" heading">',
            ' <button type="button" class="btn btn-default btn-xs j-disable j-edit" data-toggle="modal" data-target="#modifyModal"  data-id="${item.id}" data-hospitalname="${item.hospitalname}" data-departmentname="${item.departmentname}"  data-startdate="${item.startdate}" data-enddate="${item.enddate}"  data-bednumber="${item.bednumber}"  data-money="${item.money}"><span class="iconfont iconfont-xs">&#xe62d;</span>修改</button>',
            ' <a class="btn btn-default btn-xs"  href="' + ROOTPAth + '/admin/hospitalpays/index?pcode=2&subcode=2&hospitalizationid=${item.id}" ><span class="iconfont iconfont-xs">&#xe617;</span> 查看详情</a>',
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
                    url: ROOTPAth + '/admin/hospitalizations/list',
                    type: 'POST',
                    dataType: 'json',
                    data: {
                        startdate:$("#add_startdate").val(),
                        enddate:$("#add_enddate").val(),
                        patientid: patientid,
                        length: pagelength

                    },
                    success: function (res) {
                        var newData = $.extend({}, res);
                        $.each(newData.data, function (i, val) {

                            newData.data[i].currentpage = pageIndex.current;
                        });
                        tool.stopPageLoading();
                        $hospitalizationList.find(".page-info-num").text(res.data.length);

                        $table.find("tbody").empty().append(listTpl.render(newData));
                        //删除权限
                        $table.find(".j-del").confirmation({
                            title: "确定删除该订单吗？",
                            btnOkLabel: "确定",
                            btnCancelLabel: "取消",
                            onConfirm: function (event, element) {
                                event.preventDefault();
                                self.deleteHospitalization($(element));
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
            $hospitalizationList.on("change", ".j-length", function () {
                var $this = $(this);
                pagelength = $this.val();
                var index = $this.get(0).selectedIndex;
                $hospitalizationList.find(".j-length").not(this).get(0).selectedIndex = index;
                pageIndex.reset();
            });
            //添加界面关闭,下拉框消失
            $addModal.on("hide.modal", function (event) {
                $addModal.find(".list").hide();
            });
            //我要编辑
            $ModifyForm.on("click", ".j-form-edit", function (event) {
                var formDom = event.delegateTarget;
                $(this).hide();
                $(formDom).find(".j-form-save").show();
                $(formDom).find("input").prop("disabled", false);
                $ModifyForm.find("input[name=hospitalname]").prop("disabled", true);
                $ModifyForm.find("input[name=departmentname]").prop("disabled", true);
                // $(event.relatedTarget)

            });
            //添加表单初始化
            $addModal.on('show.modal', function (event) {
                $HospitalizationForm[0].reset();
                $HospitalizationForm.find("input").removeAttr("aria-describedby");
                $HospitalizationForm.find("input").removeAttr("aria-invalid");
                $HospitalizationForm.find("input").removeAttr("aria-required");
                $HospitalizationForm.find("div").removeClass("has-error");
                $HospitalizationForm.find("span").remove();
                $("#add_hospitalid").val("");
            })
            //修改表单初始化
            $modifyModal.on('show.modal', function (event) {
                var $modal = $ModifyForm;
                $modal.find("input").prop("disabled", true);
                $modal.find("input").removeAttr("aria-describedby");
                $modal.find("input").removeAttr("aria-invalid");
                $modal.find("input").removeAttr("aria-required");
                $modal.find("div").removeClass("has-error");
                $modal.find("span").remove();
                var button = $(event.relatedTarget); // Button that triggered the modal
                var id = button.data("id");
                var hospitalname = button.data("hospitalname");
                var departmentname = button.data("departmentname");
                var startdate = button.data("startdate");
                var enddate = button.data("enddate");
                var bednumber = button.data("bednumber");
                $modal.find('input[name=id]').val(id);
                $modal.find('input[name=hospitalname]').val(hospitalname);
                $modal.find('input[name=departmentname]').val(departmentname);
                $modal.find('input[name=startdate]').val(startdate);
                $modal.find('input[name=enddate]').val(enddate);
                $modal.find('input[name=bednumber]').val(bednumber);
                $modal.find(".j-form-save").hide();
                $modal.find(".j-form-edit").show();
            });
            $("#add_hospitalname").keyup(function (e) {
                if (e.keyCode != 40 && e.keyCode != 38) {
                    get_hospitals_Model($("#add_hospitalname"));
                }
            }).focus(function () {
                this.select();
            });
            $("#add_departmentname").keyup(function (e) {
                if (e.keyCode != 40 && e.keyCode != 38) {
                    get_departments_Model($("#add_departmentname"));
                }
            }).focus(function () {
                this.select();
            });
            jQuery.validator.addMethod("checkCsv", function(value, element) {
                var filepath=$("#hospitalpays").val();
                //获得上传文件名
                var fileArr=filepath.split("\\");
                var fileTArr=fileArr[fileArr.length-1].toLowerCase().split(".");
                var filetype=fileTArr[fileTArr.length-1];
                //切割出后缀文件名
                if(filetype != "csv"){
                    return false;
                }else{
                    return true;
                }
            }, "上传文件格式不适合");
            // 自定义验证自然数方法
            $.validator.addMethod("isPositive", function (value, element) {
                var score = /^[0-9]*$/;
                return score.test(value) && value != 0;
            }, $.validator.format("请输入正确数值!"));
            //表单验证-添加订单
            $HospitalizationForm.validate({
                rules: {
                    file:{
                      required:true,
                        checkCsv:true
                    },
                    hospitalname: {
                        required: true,
                        remote: {
                            url: ROOTPAth + '/admin/hospitals/hasName',
                            type: "POST",
                            dataType: "json",
                            data: {
                                name: function(){return $("#add_hospitalname").val()},
                            }
                        }
                    },
                    departmentname: {
                        required: true,
                        remote: {
                            url: ROOTPAth + '/admin/departments/hasName',
                            type: "POST",
                            dataType: "json",
                            data: {
                                hospitalid:function () {
                                    return $("#add_hospitalid").val()
                                },
                                name: function(){return $("#add_departmentname").val()},
                            }
                        }
                    },
                    bednumber: {
                        required: true,
                        isPositive: true,
                    },
                    startdate: "required",
                    enddate: "required"
                },
                messages: {
                    file:{
                        required:"请选择文件",
                        checkCsv:"请用csv格式文件"
                    },
                    hospitalname: {
                        required: "请输入医院名",
                        remote: "医院名不存在"
                    },
                    departmentname:{
                        required: "请输入科室名",
                        remote: "科室名不存在"
                    },
                    bednumber: {
                        required: "请输入正确数值",
                    },
                    startdate: "请选择入院时间",
                    enddate: "请选择出院时间"
                },
                errorElement: 'span', //default input error message container
                errorClass: 'help-block', // default input error message class
                //focusInvalid: false, // do not focus the last invalid input
                invalidHandler: function (event, validator) { //display error alert on form submit
                    //	                $('.alert-danger', $('.login-form')).show();
                },
                highlight: function (element) { // hightlight error inputs
                    $(element)
                        .closest('.form-group').addClass('has-error'); // set error class to the control group
                },
                success: function (label) {
                    // var strId = label.closest('.form-group') == "undefined" ? "" : label.closest('.form-group').attr("id");
                    // var index = strId.charAt(strId.length - 1);
                    // if (strId == "add_drugName_" + index) {
                    //     $("#add_drugAmount" + index).prop("disabled", false);
                    //     //手动输入医院全名需要查询医院信息
                    //     if ($("#add_drugId_" + index).val() == "" && !label.closest('.form-group').hasClass('has-error')) {
                    //         $.ajax({
                    //             url: ROOTPAth + '/admin/drugs/drugInfoByName',
                    //             type: "POST",
                    //             dataType: "json",
                    //             data: {
                    //                 name: $("#add_drugName" + index).val(),
                    //                 hospitalid: hospitalid
                    //             },
                    //             success: function (data) {
                    //                 $("#add_drugPrice" + index).val(data.price);
                    //                 $("#add_drugFormat" + index).val(data.format);
                    //                 $("#add_drugId_" + index).val(data.id);
                    //             }
                    //         });
                    //     }
                    // }
                    label.closest('.form-group').removeClass('has-error');
                    label.remove();
                },

                errorPlacement: function (error, element) {
                    var strId = element.closest('.form-group').find("input[name=hospitalname]") == "undefined" ? "" : element.closest('.form-group').find("input[name=hospitalname]") .attr("id");
                    if ((strId == "add_hospitalname") && element.closest('.form-group').hasClass('has-error')) {
                        $("#add_hospitalid" ).val("");
                    }
                    error.insertAfter(element);
                },
                submitHandler: function () {
                    var savePath = ROOTPAth + '/admin/hospitalizations/newHospitalization';
                    var formdata = new FormData($($HospitalizationForm)[0]);
                    $.ajax({
                        type: "POST",
                        url: savePath,
                        dataType: "json",
                        data:formdata,
                        cache: false,
                        contentType: false,
                        processData: false,
                        beforeSend: function () {
                            tool.startPageLoading();
                        },

                        success: function (data) {
                            console.log(data);
                            tool.stopPageLoading();
                            if (data.code == 1) {
                                $addModal.modal("hide");
                                $addRoletipModal.find(".dialogtip-msg").html("订单添加成功");
                                $addRoletipModal.modal('show');
                                $add_total.val(1);
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
            //日期搜索
            $("#search").click(function () {
                pageIndex.reset();
            })
            //表单验证-修改订单
            $ModifyForm.validate({
                rules: {
                    // hospitalname: {
                    //     required: true,
                    //     remote: {
                    //         url: ROOTPAth + '/admin/hospitals/hasName',
                    //         type: "POST",
                    //         dataType: "json",
                    //         data: {
                    //             name:function(){return  $("#mod_hospitalname").val()},
                    //         }
                    //     }
                    // },
                    // departmentname: {
                    //     required: true,
                    //     remote: {
                    //         url: ROOTPAth + '/admin/departments/hasName',
                    //         type: "POST",
                    //         dataType: "json",
                    //         data: {
                    //             hospitalid:function () {
                    //               return      $("#add_hospitalid").val()
                    //             },
                    //             name:function(){return  $("#mod_departmentname").val()},
                    //         }
                    //     }
                    // },
                    bednumber: {
                        required: true,
                        isPositive: true,
                    },
                    startdate: "required",
                    enddate: "required"
                },
                messages: {
                    // hospitalname: {
                    //     required: "请输入医院名",
                    //     remote: "医院名不存在"
                    // },
                    bednumber: {
                        required: "请输入正确数值",
                    },
                    startdate: "请选择入院时间",
                    enddate: "请选择出院时间"
                },
                errorElement: 'span', //default input error message container
                errorClass: 'help-block', // default input error message class
                //focusInvalid: false, // do not focus the last invalid input


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
                    // var strId = element.closest('.form-group').find("input[name=hospitalname]") == "undefined" ? "" : element.closest('.form-group').find("input[name=hospitalname]") .attr("id");
                    // if ((strId == "mod_hospitalname") && element.closest('.form-group').hasClass('has-error')) {
                    //     $("#mod_hospitalid" ).val("");
                    // }
                    error.insertAfter(element);
                },
                submitHandler: function () {


                    var updatePath = ROOTPAth + '/admin/hospitalizations/modHospitalization';
                    $.post(updatePath, $ModifyForm.serialize(), function (data) {
                        if (data.code === 1) {
                            $('#modifyModal').modal('hide');
                            $addRoletipModal.find(".dialogtip-msg").html("订单修改成功");
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
        deleteHospitalization: function ($that) {
            var id = $that.data("id");
            var delPath = ROOTPAth + '/admin/hospitalizations/delHospitalization/' + id;
            $.ajax({
                url: delPath,
                type: "POST",
                success: function (data) {
                    if (data.code === 1) {
                        $addRoletipModal.find(".dialogtip-msg").html(data.message);
                        $addRoletipModal.modal('show');
                        pageIndex.reset();
                    } else {
                        $("#ajax_fail").find("h4").html(data.message);
                        $("#ajax_fail").modal("show")
                    }
                }
            });

        },

    };

    function get_hospitals_Model(obj) {
        var t = setTimeout(function () {
            $.ajax({
                url: ROOTPAth + '/admin/hospitals/listLike',
                type: 'POST',
                dataType: 'json',
                data: {
                    name: $(obj).val()
                },
                success: function (data) {
                    data = data.data;
                    var $list = $("#add_hospitals");
                    $list.show();
                    $list.html("");
                    $.each(data, function (index, el) {
                        var html = $("<li data-id='" + el.id + "'>" + el.name + "</li>");
                        $list.append(html);

                    });
                    if ($list.html() == "") {
                        $list.hide();
                    }
                    $($list).find("li").hover(function () {

                        $(this).addClass("esultDivLiHover");
                    }, function () {
                        $(this).removeClass("esultDivLiHover");
                    });
                    $list.mouseleave(function () {
                        $list.hide();
                    });
                    $($list).find("li").click(function (event) {
                        $(obj).val($(this).text());
                        $("#add_hospitalid").val($(this).data("id"));
                        $list.hide();
                        $HospitalizationForm.validate().element($(obj));
                    });
                },
            });
        }, 500);
    }

    function get_departments_Model(obj) {
        var t = setTimeout(function () {
            $.ajax({
                url: ROOTPAth + '/admin/departments/listLike',
                type: 'POST',
                dataType: 'json',
                data: {
                    hospitalid: $("#add_hospitalid").val(),
                    name: $(obj).val()
                },
                success: function (data) {

                    data = data.data;
                    var $list = $("#add_departments");
                    $list.show();
                    $list.html("");
                    $.each(data, function (index, el) {
                        var html = $("<li data-id='" + el.id + "'>" + el.name + "</li>");
                        $list.append(html);

                    });
                    if ($list.html() == "") {
                        $list.hide();
                    }
                    $($list).find("li").hover(function () {

                        $(this).addClass("esultDivLiHover");
                    }, function () {
                        $(this).removeClass("esultDivLiHover");
                    });
                    $list.mouseleave(function () {
                        $list.hide();
                    });
                    $($list).find("li").click(function (event) {
                        $(obj).val($(this).text());
                        $("#add_departmentid").val($(this).data("id"));
                        $list.hide();
                        $HospitalizationForm.validate().element($(obj));
                    });
                },
            });
        }, 500);
    }

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
    $('#add_startdate').datepicker({
        dateFormat: "yy-mm-dd",
        selectOtherMonths: true,
        // yearRange: "-100:+0",
        changeMonth: true,
        changeYear: true,
        inline: true
    });
    $('#add_enddate').datepicker({

        dateFormat: "yy-mm-dd",
        selectOtherMonths: true,
        // yearRange: "-100:+0",
        changeMonth: true,
        changeYear: true,
        inline: true
    });
    $('#mod_starttime').datepicker({
        dateFormat: "yy-mm-dd",
        selectOtherMonths: true,
        // yearRange: "-100:+0",
        changeMonth: true,
        changeYear: true,
        inline: true
    });
    $('#mod_endtime').datepicker({

        dateFormat: "yy-mm-dd",
        selectOtherMonths: true,
        // yearRange: "-100:+0",
        changeMonth: true,
        changeYear: true,
        inline: true
    });
    Utilitiy.init();
});